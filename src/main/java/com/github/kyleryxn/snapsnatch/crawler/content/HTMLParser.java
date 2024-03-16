package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.crawler.WebParser;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HTMLParser implements WebParser {
    private final Map<String, ElementExtractor> extractors;
    private final LinkExtractor linkExtractor;
    private final ImageExtractor imageExtractor;
    private Set<Image> images;
    private Set<String> links;
    private String startURL;

    public HTMLParser() {
        extractors = new HashMap<>();
        linkExtractor = new LinkExtractor();
        imageExtractor = new ImageExtractor();
    }

    @Autowired
    public HTMLParser(ImageExtractor imageExtractor, LinkExtractor linkExtractor) {
        this.linkExtractor = linkExtractor;
        this.imageExtractor = imageExtractor;
        extractors = new HashMap<>();

        extractors.put("image", this.imageExtractor);
        extractors.put("link", this.linkExtractor);
    }

    public Set<Image> getImages() {
        return images;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setStartURL(String startURL) {
        this.startURL = startURL;
        linkExtractor.setBaseURL(this.startURL);
    }

    @Override
    public void parse(String content) {
        Document document = Jsoup.parse(content, Parser.htmlParser());
        Elements imgTags = document.select("img[src]");
        Elements anchorTags = document.select("a[href]");

        images = extractors.get("image").extractImages(imgTags);
        links = new HashSet<>();
        links.add(startURL);

        for (String link : extractors.get("link").extractLinks(anchorTags)) {
            if (link.startsWith("/")) {
                link = startURL.substring(0, startURL.lastIndexOf("/")) + link;
            }

            if (isSameDomain(link)) {
                links.add(link);
            }
        }
    }

    private boolean isSameDomain(String url) {
        try {
            URI uri = new URI(url);
            URI startURI = new URI(startURL);
            return uri.getHost().equalsIgnoreCase(startURI.getHost());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}