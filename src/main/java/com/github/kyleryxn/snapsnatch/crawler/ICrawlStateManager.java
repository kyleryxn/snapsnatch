package com.github.kyleryxn.snapsnatch.crawler;

public interface ICrawlStateManager {

    void startCrawl();

    boolean visitPage(String url);

    void incrementPageCount();

    void incrementRequestCount();

    void addPageSize(long size);

    void addContentType(String contentType);

    void logCrawlStats();

}
