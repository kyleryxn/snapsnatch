package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.crawler.util.URLProcessor;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class LinkExtractor implements ElementExtractor {
    private final URLProcessor processor;
    private String baseURL;

    public LinkExtractor() {
        this.processor = new URLProcessor();
    }

    public LinkExtractor(String baseURL) {
        this.processor = new URLProcessor();
        this.baseURL = baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return baseURL;
    }

    @Override
    public Set<String> extractLinks(Elements anchorTags) {
        Predicate<String> validURL = processor::isValidURL;
        Set<String> links = new HashSet<>();

        anchorTags.stream()
                .map(tag -> tag.attr("href").trim().replaceAll("\\s", "%20"))
                .map(this::resolveURL)
                .filter(validURL)
                .filter(url -> !url.isBlank())
                .forEach(url -> {
                    if (!isDuplicate(links, url)) {
                        links.add(url);
                    }
                });

        return links;
    }

    private String resolveURL(String url) {
        if (url.startsWith("/")) {
            if (baseURL.endsWith("/")) {
                return baseURL.substring(0, baseURL.length() - 1) + url;
            } else {
                return baseURL + url;
            }
        }

        return url;
    }

    private boolean isDuplicate(Set<String> urls, String newUrl) {
        for (String url : urls) {
            if (url.equals(newUrl) || url.equals(newUrl + "/") || newUrl.equals(url + "/")) {
                return true;
            }
        }

        return false;
    }

}