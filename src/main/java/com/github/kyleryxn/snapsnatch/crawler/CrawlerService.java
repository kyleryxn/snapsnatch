package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.PageContent;
import com.github.kyleryxn.snapsnatch.crawler.content.ParserService;
import com.github.kyleryxn.snapsnatch.crawler.http.WebContentReader;
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

    public CrawlerService(WebContentReader webContentReader, ParserService parserService, ICrawlStateManager crawlStateManager) {
        this.webContentReader = webContentReader;
        this.parserService = parserService;
        this.crawlStateManager = crawlStateManager;
        images = new ConcurrentHashMap<>();
        baseURL = "";
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
        if (!readRobotsTxt()) {
            LOGGER.info("Crawling disallowed by robots.txt");
            return;
        }

        crawlStateManager.startCrawl();
        final ThreadFactory threadFactory = Thread.ofVirtual().name("crawler-", 1).factory();

        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
            crawlPage(executor, baseURL);
        }

        crawlStateManager.logCrawlStats();
    }

    private boolean readRobotsTxt() {
        String robotsTxtUrl = baseURL + "robots.txt";
        PageContent robotsTxtContent = webContentReader.readContent(robotsTxtUrl);
        Map<String, List<String>> directives = parserService.parseAndGetDirectives(robotsTxtContent.content());
        return directives.getOrDefault("*", List.of()).stream().noneMatch(disallow -> disallow.equals("/"));
    }

    private void crawlPage(ExecutorService executor, String url) {
        if (!crawlStateManager.visitPage(url)) {
            return;
        }

        crawlStateManager.incrementRequestCount();
        PageContent pageContent = webContentReader.readContent(url);

        if (pageContent != null) {
            crawlStateManager.incrementPageCount();
            crawlStateManager.addContentType(pageContent.contentType());
            crawlStateManager.addPageSize(pageContent.content().getBytes().length);

            Set<String> urls = parserService.parseAndGetLinks(pageContent.content(), url);
            Set<Image> imagesOnPage = parserService.parseAndGetImages(pageContent.content());
            images.putIfAbsent(url, imagesOnPage);

            for (String link : urls) {
                executor.submit(() -> crawlPage(executor, link));
            }
        }

    }

}