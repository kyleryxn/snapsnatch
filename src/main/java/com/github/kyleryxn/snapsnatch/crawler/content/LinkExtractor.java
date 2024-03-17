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

    public LinkExtractor(CompositeCrawlerUtil crawlerUtil) {
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

        anchorTags.stream()
                .map(tag -> tag.attr("href").trim().replaceAll("\\s", "%20"))
                .map(url -> crawlerUtil.resolveURL(baseURL, url))
                .filter(crawlerUtil::isValidURL)
                .filter(url -> !url.isBlank())
                .forEach(url -> {
                    if (!crawlerUtil.isDuplicate(url, links)) {
                        links.add(url);
                    }
                });

        return links;
    }

}