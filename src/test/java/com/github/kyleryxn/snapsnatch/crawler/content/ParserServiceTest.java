package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("ParserService Tests")
class ParserServiceTest {

    @Autowired
    private ParserService parserService;

    @Autowired
    private Parser HTMLParser;

    @Autowired
    private Parser robotsTxtParser;

    @Test
    @DisplayName("Test: Given HTML Content, When Parsing Images, Then Return Correct Images")
    void givenHTMLContent_whenParsingImages_thenReturnCorrectImages() {
        // Given
        String content = "<html><body><img src=\"https://example.com/image.jpg\"></body></html>";

        // When
        Set<Image> images = parserService.parseHTMLAndGetImages(content);

        // Then
        assertTrue(images.stream().anyMatch(image -> image.getURL().equals("https://example.com/image.jpg")));
    }

    @Test
    @DisplayName("Test: Given HTML Content, When Parsing Links, Then Return Correct Links")
    void givenHTMLContent_whenParsingLinks_thenReturnCorrectLinks() {
        // Given
        String content = "<html><body><a href=\"https://example.com/page1\"></body></html>";

        // When
        Set<String> urls = parserService.parseHTMLAndGetLinks(content, "https://example.com");

        // Then
        assertTrue(urls.contains("https://example.com/page1"));
    }

    @Test
    @DisplayName("Test: Given Text Content, When Parsing Robots Txt, Then Return Correct Directives")
    void givenStringContent_whenParsingRobotsTxt_thenReturnCorrectDirectives() {
        // Given
        String content = "User-agent: *\nDisallow: /private/\n\nUser-agent: bot\nDisallow: /restricted/";

        // When
        Map<String, List<String>> directives = parserService.parseRobotsTxtAndGetDirectives(content);

        // Then
        assertEquals(2, directives.size());
    }

}