package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.BaseTest;
import com.github.kyleryxn.snapsnatch.image.ImageService;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ImageExtractor Tests")
class ImageExtractorTest extends BaseTest {
    private ImageExtractor imageExtractor;

    @Autowired
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        imageExtractor = new ImageExtractor(imageService);
    }

    @Test
    @DisplayName("Test: Given a ImageExtractor, When Instantiated, Then Processor Is Initialized")
    void givenClassName_whenExtracting_thenReturnCorrectExtractorType() {
        // When
        String actual = imageExtractor.getExtractorType();

        // Then
        assertEquals("ImageExtractor", actual);
    }

    @Test
    @DisplayName("Test: Given Jsoup Elements Tag, When Extracting Images, Then Return Correct Images Set")
    void givenElementsTag_whenExtractingImages_thenReturnCorrectImagesSet() {
        // Given
        Elements elements = new Elements();
        elements.add(new Element(Tag.valueOf("img"), "").attr("src", "https://example.com/page1/testimage1.png"));
        elements.add(new Element(Tag.valueOf("img"), "").attr("src", "https://example.com/page1/testimage2.png"));

        // When
        Set<Image> images = imageExtractor.extractImages(elements);

        // Then
        assertEquals(2, images.size());
    }

}