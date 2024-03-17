package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.jsoup.parser.Parser.htmlParser;

@Component
class HTMLParser implements Parser {
    private final Map<String, ElementExtractor> extractors;
    private Set<Image> images;
    private Set<String> links;
    private String baseURL;

    @Autowired
    public HTMLParser(List<ElementExtractor> extractorsList) {
        this.extractors = extractorsList.stream()
                .collect(Collectors.toMap(ElementExtractor::getExtractorType, Function.identity()));
        images = new HashSet<>();
        links = new HashSet<>();
    }

    public Set<Image> getImages() {
        return images;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        extractors.get("LinkExtractor").setBaseURL(this.baseURL);
    }

    @Override
    public String getContentType() {
        return Content.HTML.getContentType();
    }

    @Override
    public void parse(String content) {
        Document document = Jsoup.parse(content, htmlParser());
        Elements imageTags = document.select("img[src]");
        Elements anchorTags = document.select("a[href]");

        images.addAll(extractors.get("ImageExtractor").extractImages(imageTags));
        links = new HashSet<>();
        links.add(baseURL);

        for (String link : extractors.get("LinkExtractor").extractLinks(anchorTags)) {
            if (link.startsWith("/")) {
                link = baseURL.substring(0, baseURL.lastIndexOf("/")) + link;
            }

            if (isSameDomain(link)) {
                links.add(link);
            }
        }
    }

    private boolean isSameDomain(String url) {
        try {
            URI uri = new URI(url);
            URI startURI = new URI(baseURL);
            return uri.getHost().equalsIgnoreCase(startURI.getHost());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}