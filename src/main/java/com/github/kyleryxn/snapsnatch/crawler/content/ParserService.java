package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ParserService {
    private final ContentParser robotsTxtParser;
    private final ContentParser htmlParser;

    public ParserService(List<ContentParser> parsersList) {
        Map<Content, ContentParser> parsers = parsersList.stream()
                .collect(Collectors.toMap(ContentParser::getContentType, Function.identity()));
        htmlParser = parsers.get(Content.HTML);
        robotsTxtParser = parsers.get(Content.ROBOTS);
    }

    public Set<Image> parseAndGetImages(String content) {
        htmlParser.parse(content);
        return htmlParser.getImages();
    }

    public Set<String> parseAndGetLinks(String content, String baseURL) {
        htmlParser.setBaseURL(baseURL);
        htmlParser.parse(content);

        return htmlParser.getLinks();
    }

    public Map<String, List<String>> parseAndGetDirectives(String content) {
        robotsTxtParser.parse(content);
        return robotsTxtParser.getDirectives();
    }

}
