package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.ParserService;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class CrawlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerService.class);
    private final WebContentReader webContentReader;
    private final ParserService parserService;
    private final ConcurrentMap<String, Boolean> visited;
    private final ConcurrentMap<String, Set<Image>> images;
    private String startUrl;
    private boolean crawlFlag;

    public CrawlerService(WebContentReader webContentReader, ParserService parserService) {
        this.webContentReader = webContentReader;
        this.parserService = parserService;
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
        Map<String, List<String>> robotsTxt = parserService.parseRobotsTxtAndGetDirectives(robotsTxtContent);
        List<String> allUserAgents = robotsTxt.get("*");

        if (allUserAgents != null && allUserAgents.contains("/")) {
            LOGGER.info("Crawling disallowed by robots.txt");
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
        Set<String> urls = parserService.parseHTMLAndGetLinks(content, url);
        Set<Image> imagesOnPage = parserService.parseHTMLAndGetImages(content);
        images.putIfAbsent(url, imagesOnPage);

        for (String link : urls) {
            executor.submit(() -> crawlPage(executor, link));
        }
    }

}