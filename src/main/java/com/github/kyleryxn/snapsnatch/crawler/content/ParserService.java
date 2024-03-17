package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ParserService {
    private final HTMLParser htmlParser;
    private final RobotsTxtParser robotsTxtParser;

    @Autowired
    public ParserService(@Qualifier("HTMLParser") Parser htmlParser, @Qualifier("robotsTxtParser") Parser robotsTxtParser) {
        this.htmlParser = (HTMLParser) htmlParser;
        this.robotsTxtParser = (RobotsTxtParser) robotsTxtParser;
    }

    public Set<Image> parseHTMLAndGetImages(String content) {
        htmlParser.parse(content);
        return htmlParser.getImages();
    }

    public Set<String> parseHTMLAndGetLinks(String content, String baseURL) {
        htmlParser.setBaseURL(baseURL);
        htmlParser.parse(content);
        return htmlParser.getLinks();
    }

    public Map<String, List<String>> parseRobotsTxtAndGetDirectives(String content) {
        robotsTxtParser.parse(content);
        return robotsTxtParser.getDirectives();
    }

}