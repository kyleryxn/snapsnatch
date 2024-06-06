package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.BaseTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("ImageFactoryUtil Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageFactoryUtilTest extends BaseTest {
    private String tag;
    private Element element;

    @Autowired
    private ImageFactoryUtil factoryUtil;

    @BeforeAll
    void setUp() {
        tag = "<img src=\"https://example.com/image1.gif\" alt=\"Image1\">";
        element = Jsoup.parseBodyFragment(tag).body().child(0);
    }

    @Nested
    @DisplayName("parseName Method Tests")
    class ParseName {

        @DisplayName("Test: Return Correct Image Name")
        @Test
        void givenImageTag_whenParsingName_thenReturnCorrectName() {
            String expected = "Image1";
            String actual = factoryUtil.parseDescription(element);

            assertEquals(expected, actual);
        }

    }

    @Nested
    @DisplayName("parseLogo Method Tests")
    class ParseLogo {

        @DisplayName("Test: Return Correct Image Logo Status")
        @Test
        void givenImageTag_whenParsingLogo_thenReturnCorrectLogoStatus() {
            boolean actual = factoryUtil.parseLogo(element);

            assertFalse(actual);
        }

    }

    @Nested
    @DisplayName("parseURL Method Tests")
    class ParseURL {

        @DisplayName("Test: Return Correct Image URL")
        @Test
        void givenImageTag_whenParsingURL_thenReturnCorrectURL() {
            String expected = "https://example.com/image1.gif";
            String actual = factoryUtil.parseURL(element);

            assertEquals(expected, actual);
        }

        @DisplayName("Test: Return Correct Image URL")
        @Test
        void givenImageTagWithMissingProtocol_whenParsingURL_thenReturnCorrectURL() {
            String expected = "https://example.com/image1.gif";

            String missingHTTPTag = "<img src=\"//example.com/image1.gif\" alt=\"Image1\">";
            Element element = Jsoup.parseBodyFragment(missingHTTPTag).body().child(0);
            String actual = factoryUtil.parseURL(element);

            assertEquals(expected, actual);
        }

    }

}