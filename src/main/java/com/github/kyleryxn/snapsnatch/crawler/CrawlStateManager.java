package com.github.kyleryxn.snapsnatch.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class CrawlStateManager implements ICrawlStateManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlStateManager.class);
    private final ConcurrentMap<String, Boolean> visited;
    private final AtomicInteger pageCount;
    private long startTime;

    CrawlStateManager() {
        this.visited = new ConcurrentHashMap<>();
        this.pageCount = new AtomicInteger(0);
    }

    @Override
    public void startCrawl() {
        startTime = System.currentTimeMillis();
        pageCount.set(0);
    }

    @Override
    public boolean visitPage(String url) {
        return visited.putIfAbsent(url, true) == null;
    }

    @Override
    public void incrementPageCount() {
        pageCount.incrementAndGet();
    }

    @Override
    public void logCrawlStats() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double seconds = duration / 1000.0;
        int totalPages = pageCount.get();

        if (seconds > 0) {
            double pagesPerSecond = totalPages / seconds;
            String pagesPerSecondFormatted = String.format("%.2f", pagesPerSecond);
            LOGGER.info("Crawled {} pages in {} seconds ({} pages/second)", totalPages, seconds, pagesPerSecondFormatted);
        }
    }

}
