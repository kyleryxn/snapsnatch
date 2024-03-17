package com.github.kyleryxn.snapsnatch.crawler.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CompositeCrawlerUtil implements CrawlerUtil {
    private final URLValidator urlValidator;
    private final URLDomainChecker domainChecker;
    private final URLComparator urlComparator;
    private final URLResolver urlResolver;

    public CompositeCrawlerUtil(URLValidator urlValidator, URLDomainChecker domainChecker, URLComparator urlComparator,
                                URLResolver urlResolver) {
        this.urlValidator = urlValidator;
        this.domainChecker = domainChecker;
        this.urlComparator = urlComparator;
        this.urlResolver = urlResolver;
    }

    @Override
    public boolean isValidURL(String url) {
        return urlValidator.isValidURL(url);
    }

    @Override
    public boolean isSameDomain(String baseURL, String compareURL) {
        return domainChecker.isSameDomain(baseURL, compareURL);
    }

    @Override
    public boolean isDuplicate(String newURL, Set<String> urls) {
        return urlComparator.isDuplicate(urls, newURL);
    }

    @Override
    public String resolveURL(String baseURL, String relativePath) {
        return urlResolver.resolveURL(baseURL, relativePath);
    }

}
