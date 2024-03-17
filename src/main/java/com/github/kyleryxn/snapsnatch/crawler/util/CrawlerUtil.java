package com.github.kyleryxn.snapsnatch.crawler.util;

import java.util.Set;

public interface CrawlerUtil {

    boolean isValidURL(String url);

    boolean isSameDomain(String baseURL, String compareURL);

    boolean isDuplicate(String newUrl, Set<String> urls);

    String resolveURL(String baseURL, String url);

}
