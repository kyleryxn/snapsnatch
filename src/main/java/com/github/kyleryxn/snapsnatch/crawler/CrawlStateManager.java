package com.github.kyleryxn.snapsnatch.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
class CrawlStateManager implements ICrawlStateManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlStateManager.class);
    private final ConcurrentMap<String, Boolean> visited;
    private final ConcurrentMap<String, AtomicInteger> contentTypeDistribution;
    private final AtomicInteger pageCount;
    private final AtomicInteger requestCount;
    private final AtomicLong totalPageSize;
    private long startTime;

    CrawlStateManager() {
        visited = new ConcurrentHashMap<>();
        contentTypeDistribution = new ConcurrentHashMap<>();
        pageCount = new AtomicInteger(0);
        requestCount = new AtomicInteger(0);
        totalPageSize = new AtomicLong(0);
    }

    @Override
    public void startCrawl() {
        startTime = System.currentTimeMillis();
        pageCount.set(0);
        requestCount.set(0);
        totalPageSize.set(0);
        visited.clear();
        contentTypeDistribution.clear();
    }

    // This method is used to visit a page and add it to the visited map
    // If the page has already been visited, the method will return false
    @Override
    public boolean visitPage(String url) {
        return visited.putIfAbsent(url, true) == null;
    }

    @Override
    public void incrementPageCount() {
        pageCount.incrementAndGet();
    }

    @Override
    public void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    @Override
    public void addPageSize(long size) {
        totalPageSize.addAndGet(size);
    }

    @Override
    public void addContentType(String contentType) {
        contentTypeDistribution.computeIfAbsent(contentType, k -> new AtomicInteger(0)).incrementAndGet();
    }

    @Override
    public void logCrawlStats() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double seconds = duration / 1000.0;
        int totalPages = pageCount.get();

        if (seconds > 0) {
            double pagesPerSecond = totalPages / seconds;
            LOGGER.info("Crawled {} pages in {} seconds ({} pages/second)", totalPages, seconds, pagesPerSecond);
        }

        // Additional logging for metrics
        LOGGER.info("Total Requests: {}", requestCount.get());
        contentTypeDistribution.forEach((type, count) -> LOGGER.info("Content-Type: {} Count: {}", type, count.get()));
        if (pageCount.get() > 0) {
            long averagePageSize = totalPageSize.get() / pageCount.get();
            LOGGER.info("Average Page Size: {} bytes", averagePageSize);
        }
    }

}
