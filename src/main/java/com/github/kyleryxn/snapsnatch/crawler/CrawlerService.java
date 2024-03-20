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
    private final ICrawlStateManager crawlStateManager;
    private final ConcurrentMap<String, Set<Image>> images;
    private String baseURL;
    private boolean crawlFlag;

    public CrawlerService(WebContentReader webContentReader, ParserService parserService, ICrawlStateManager crawlStateManager) {
        this.webContentReader = webContentReader;
        this.parserService = parserService;
        this.crawlStateManager = crawlStateManager;
        this.images = new ConcurrentHashMap<>();
        crawlFlag = true;
    }

    public ConcurrentMap<String, Set<Image>> getImages() {
        return images;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void crawl() {
        crawlStateManager.startCrawl();

        final ThreadFactory threadFactory = Thread.ofVirtual().name("crawler-", 1).factory();
        readRobotsTxt();

        if (!crawlFlag)
            return;

        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
            crawlPage(executor, baseURL);
        }

        crawlStateManager.logCrawlStats();
    }

    private void readRobotsTxt() {
        String robotsTxtContent = webContentReader.readContent(baseURL + "robots.txt");
        Map<String, List<String>> robotsTxt = parserService.parseAndGetDirectives(robotsTxtContent);
        List<String> allUserAgents = robotsTxt.get("*");

        if (allUserAgents != null && allUserAgents.contains("/")) {
            LOGGER.info("Crawling disallowed by robots.txt");
            crawlFlag = false;
        } else {
            robotsTxt.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals("*"))
                    .flatMap(entry -> entry.getValue().stream())
                    .map(link -> baseURL.substring(0, baseURL.lastIndexOf('/')) + link)
                    .forEach(crawlStateManager::visitPage);
        }
    }

    private void crawlPage(ExecutorService executor, String url) {
        LOGGER.info("Visiting: {}", url);

        if (!crawlStateManager.visitPage(url)) {
            return;
        }

        try {
            // Example: 1000 milliseconds delay for a rate limit of 1 request per second
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        String content = webContentReader.readContent(url);
        crawlStateManager.incrementPageCount();

        Set<String> urls = parserService.parseAndGetLinks(content, url);
        Set<Image> imagesOnPage = parserService.parseAndGetImages(content);
        images.putIfAbsent(url, imagesOnPage);

        for (String link : urls) {
            executor.submit(() -> crawlPage(executor, link));
        }
    }

}