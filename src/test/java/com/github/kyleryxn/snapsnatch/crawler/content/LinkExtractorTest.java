package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.BaseTest;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("LinkExtractor Tests")
class LinkExtractorTest extends BaseTest {

    @Autowired
    private LinkExtractor linkExtractor;

    @Test
    @DisplayName("Test: Given a LinkExtractor, When Instantiated, Then Processor Is Initialized")
    void givenClassName_whenExtracting_thenReturnCorrectExtractorType() {
        // When
        String actual = linkExtractor.getExtractorType();

        // Then
        assertEquals("LinkExtractor", actual);
    }

    @Test
    @DisplayName("Test: Given Jsoup Elements Tag, When Extracting Links, Then Return Correct Links Set")
    void givenElementsTag_whenExtractingLinks_thenReturnCorrectLinksSet() {
        // Given
        Elements elements = new Elements();
        elements.add(new Element(Tag.valueOf("a"), "").attr("href", "https://example.com/page1"));
        elements.add(new Element(Tag.valueOf("a"), "").attr("href", "https://example.com/page2"));

        // When
        linkExtractor.setBaseURL("https://example.com");
        Set<String> links = linkExtractor.extractLinks(elements);

        // Then
        assertEquals(3, links.size());
    }

}