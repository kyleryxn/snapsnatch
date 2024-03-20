package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.jsoup.parser.Parser.htmlParser;

@Component
class HTMLContentParser implements ContentParser {
    private final Map<String, ElementExtractor> extractors;
    private final Set<Image> images;
    private final Set<String> links;

    HTMLContentParser(List<ElementExtractor> extractorsList) {
        this.extractors = extractorsList.stream()
                .collect(Collectors.toMap(ElementExtractor::getExtractorType, Function.identity()));
        images = new HashSet<>();
        links = new HashSet<>();
    }

    @Override
    public void setBaseURL(String baseURL) {
        extractors.get("LinkExtractor").setBaseURL(baseURL);
    }

    @Override
    public Set<Image> getImages() {
        return images;
    }

    @Override
    public Set<String> getLinks() {
        return links;
    }

    @Override
    public Content getContentType() {
        return Content.HTML;
    }

    @Override
    public void parse(String content) {
        Document document = Jsoup.parse(content, htmlParser());
        Elements imageTags = document.select("img[src]");
        Elements anchorTags = document.select("a[href]");

        images.addAll(extractors.get("ImageExtractor").extractImages(imageTags));
        links.addAll(extractors.get("LinkExtractor").extractLinks(anchorTags));
    }

}