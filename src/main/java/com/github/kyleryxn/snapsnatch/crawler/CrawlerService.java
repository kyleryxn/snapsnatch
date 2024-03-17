package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.HTMLParser;
import com.github.kyleryxn.snapsnatch.crawler.content.RobotsTxtParser;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class CrawlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerService.class);

    private final WebContentReader webContentReader;
    private final RobotsTxtParser robotsTxtParser;
    private final HTMLParser htmlParser;
    private final ConcurrentMap<String, Boolean> visited;
    private final ConcurrentMap<String, Set<Image>> images;
    private String startUrl;
    private boolean crawlFlag;

    @Autowired
    public CrawlerService(WebContentReader webContentReader, RobotsTxtParser robotsTxtParser, HTMLParser htmlParser) {
        this.webContentReader = webContentReader;
        this.robotsTxtParser = robotsTxtParser;
        this.htmlParser = htmlParser;
        this.visited = new ConcurrentHashMap<>();
        this.images = new ConcurrentHashMap<>();
        crawlFlag = true;
    }

    public ConcurrentMap<String, Set<Image>> getImages() {
        return images;
    }

    public ConcurrentMap<String, Boolean> getVisited() {
        return visited;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
        htmlParser.setStartURL(startUrl); // Ensure the parser knows the starting URL
    }

    public void crawl() {
        final ThreadFactory threadFactory = Thread.ofVirtual().name("crawler-", 1).factory();
        readRobotsTxt();

        if (!crawlFlag)
            return;

        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
            crawlPage(executor, startUrl);
        }
    }

    private void readRobotsTxt() {
        String robotsTxtContent = webContentReader.readContent(startUrl + "robots.txt");
        robotsTxtParser.parse(robotsTxtContent);

        Map<String, List<String>> robotsTxt = robotsTxtParser.getRobotsTxt();
        List<String> allUserAgents = robotsTxt.get("*");

        if (allUserAgents != null && allUserAgents.contains("/")) {
            LOGGER.info("Crawling disallowed by {}robots.txt", startUrl);
            crawlFlag = false;
        } else {
            robotsTxt.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals("*"))
                    .flatMap(entry -> entry.getValue().stream())
                    .map(link -> startUrl.substring(0, startUrl.lastIndexOf('/')) + link)
                    .forEach(link -> visited.putIfAbsent(link, true));
        }
    }

    private void crawlPage(ExecutorService executor, String url) {
        LOGGER.info("Visiting: {}", url);

        if (visited.putIfAbsent(url, true) != null) {
            if (visited.get(url)) {
                return;
            }
        }

        String content = webContentReader.readContent(url);
        htmlParser.parse(content);
        Set<String> urls = htmlParser.getLinks();
        Set<Image> imagesOnPage = htmlParser.getImages();
        images.putIfAbsent(url, imagesOnPage);

        for (String link : urls) {
            executor.submit(() -> crawlPage(executor, link));
        }
    }

}