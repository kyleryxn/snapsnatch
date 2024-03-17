package com.github.kyleryxn.snapsnatch.crawler.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Content Tests")
class ContentTest {
    private Content htmlContent;
    private Content robotsContent;

    @BeforeEach
    void setUp() {
        htmlContent = Content.HTML;
        robotsContent = Content.ROBOTS;
    }

    @Test
    @DisplayName("Test: Given Content Type, When Getting Content Type, Then Return Correct Content Type")
    void givenContentType_whenGettingContentType_thenReturnCorrectContentType() {
        // When
        String htmlType = htmlContent.getContentType();
        String robotsType = robotsContent.getContentType();

        // Then
        assertAll(
                () -> assertEquals("HTML", htmlType),
                () -> assertEquals("ROBOTS", robotsType)
        );
    }

    @Test
    @DisplayName("Test: Given Content Type, When Getting Content Type, Then Return Correct Content Type")
    void givenContentType_whenFindingContentType_thenReturnCorrectContentType() {
        // When
        Content htmlType = Content.findByContentType("HTML");
        Content robotsType = Content.findByContentType("ROBOTS");

        // Then
        assertAll(
                () -> assertEquals(Content.HTML, htmlType),
                () -> assertEquals(Content.ROBOTS, robotsType)
        );
    }

}