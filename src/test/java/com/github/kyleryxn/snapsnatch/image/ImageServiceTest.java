package com.github.kyleryxn.snapsnatch.image;

import com.github.kyleryxn.snapsnatch.BaseTest;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ImageService Tests")
class ImageServiceTest extends BaseTest {
    private Attributes attributes;
    private Element element;

    @Autowired
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        attributes = new Attributes();
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element, When Processing Image, Then Return Correct Image Description")
    @ValueSource(strings = {"Test Image"})
    void givenImageTag_whenProcessingImage_thenReturnCorrectDescription(String attribute) {
        // Given
        attributes.add("alt", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertEquals("Test Image", image.getDescription());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element, When Processing Image, Then Return Default Image Description")
    @ValueSource(strings = {"https://example.com/image-1"})
    void givenImageTag_whenProcessingImage_thenReturnDefaultDescription(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertEquals("None", image.getDescription());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element Without Query Parameters, When Processing Image, Then Return Correct Image URL")
    @ValueSource(strings = {"https://example.com/image-1.svg"})
    void givenImageTagWithoutQueryParameters_whenProcessingImage_thenReturnCorrectURL(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertEquals("https://example.com/image-1.svg", image.getURL());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element with Query Parameters, When Processing Image, Then Return Correct Image URL")
    @ValueSource(strings = {"https://example.com/image-1.svg?size=20px"})
    void givenImageTagWithQueryParameters_whenProcessingImage_thenReturnCorrectURL(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertEquals("https://example.com/image-1.svg?size=20px", image.getURL());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element with Query Parameters, When Processing Image, Then Return Correct Image URL")
    @ValueSource(strings = {"url_with_malformed_extension."})
    void givenImageTagWithMalformedURL_whenProcessingImage_thenReturnCorrectURL(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertEquals("url_with_malformed_extension.", image.getURL());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element, When Processing Image, Then Return Image is a Logo")
    @ValueSource(strings = "https://example.com/image-1-logo.svg?size=20px")
    void givenImageTag_whenProcessingImage_thenReturnIsLogo(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertTrue(image.isLogo());
    }

    @ParameterizedTest(name = "Test {index}: {0}")
    @DisplayName("Test: Given a Jsoup Element, When Processing Image, Then Return Image is Not a Logo")
    @ValueSource(strings = "https://example.com/image-1.svg?size=20px")
    void givenImageTag_whenProcessingImage_thenReturnNotLogo(String attribute) {
        // Given
        attributes.add("src", attribute);
        element = new Element(Tag.valueOf("img"), "", attributes);

        // When
        Image image = imageService.processImage(element);

        // Then
        assertFalse(image.isLogo());
    }

}