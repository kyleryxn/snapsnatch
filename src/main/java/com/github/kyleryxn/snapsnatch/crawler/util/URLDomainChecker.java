package com.github.kyleryxn.snapsnatch.crawler.util;

public interface URLDomainChecker {

    boolean isSameDomain(String baseURL, String compareURL);

}
