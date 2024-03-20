package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.crawler.util.CompositeCrawlerUtil;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
class LinkExtractor implements ElementExtractor {
    private final CompositeCrawlerUtil crawlerUtil;
    private String baseURL;

    LinkExtractor(CompositeCrawlerUtil crawlerUtil) {
        this.crawlerUtil = crawlerUtil;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public String getExtractorType() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<String> extractLinks(Elements anchorTags) {
        Set<String> links = new HashSet<>();
        links.add(baseURL);

        anchorTags.stream()
                .map(tag -> tag.attr("href").trim().replaceAll("\\s", "%20"))
                .map(url -> crawlerUtil.resolveURL(baseURL, url))
                .filter(url -> !url.isBlank() && crawlerUtil.isValidURL(url))
                .filter(url -> crawlerUtil.isSameDomain(baseURL, url))
                .forEach(links::add);

        return links;
    }

}