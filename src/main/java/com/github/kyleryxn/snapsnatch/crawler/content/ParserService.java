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
    private final Map<Content, ContentParser> parsers;

    public ParserService(List<ContentParser> parsersList) {
        parsers = parsersList.stream()
                .collect(Collectors.toMap(ContentParser::getContentType, Function.identity()));
    }

    public Set<Image> parseAndGetImages(String content) {
        ContentParser parser = parsers.get(Content.HTML);
        parser.parse(content);

        return parser.getImages();
    }

    public Set<String> parseAndGetLinks(String content, String baseURL) {
        ContentParser parser = parsers.get(Content.HTML);
        parser.setBaseURL(baseURL);
        parser.parse(content);

        return parser.getLinks();
    }

    public Map<String, List<String>> parseAndGetDirectives(String content) {
        ContentParser robotsTxtParser = parsers.get(Content.ROBOTS);
        robotsTxtParser.parse(content);

        return robotsTxtParser.getDirectives();
    }

}
