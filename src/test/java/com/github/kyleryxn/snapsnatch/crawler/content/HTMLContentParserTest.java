package com.github.kyleryxn.snapsnatch.crawler.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("HTMLParser Tests")
class HTMLContentParserTest {
    private HTMLContentParser htmlParser;

    @Autowired
    private ElementExtractor linkExtractor;

    @Autowired
    private ElementExtractor imageExtractor;

    @BeforeEach
    void setUp() {
        htmlParser = new HTMLContentParser(List.of(imageExtractor, linkExtractor));
        htmlParser.setBaseURL("https://example.com");
    }

    @Test
    @DisplayName("Test: Given Content Type, When Getting Content Type, Then Return Correct Content Type")
    void givenContentType_whenGettingContentType_thenReturnCorrectContentType() {
        // When
        Content actual = htmlParser.getContentType();

        // Then
        assertEquals(Content.HTML, actual);
    }

    @Test
    @DisplayName("Test: Given HTML Content, When Parsing Content, Then Return Correct Links Content")
    void givenHTMLContent_whenParsing_thenReturnCorrectLinksContent() {
        // Given
        String content = "<html><body><a href=\"https://example.com/page1\">Example</a></body></html>";

        // When
        htmlParser.parse(content);

        // Then
        assertEquals(2, htmlParser.getLinks().size());
    }

    @Test
    @DisplayName("Test: Given HTML Content, When Parsing Content, Then Return Correct Images Content")
    void givenHTMLContent_whenParsing_thenReturnCorrectImagesContent() {
        // Given
        String content = "<html><body><img src=\"https://example.com/page1\" alt=\"logo\"></body></html>";

        // When
        htmlParser.parse(content);

        // Then
        assertEquals(1, htmlParser.getLinks().size());
    }

}