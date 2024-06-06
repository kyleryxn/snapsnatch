package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("ci")
@DisplayName("DefaultImageFactory Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultImageFactoryTest {

    @Autowired
    private DefaultImageFactory imageFactory;
    private Attributes attributes;
    private Element element;

    @BeforeAll
    void setUp() {
        attributes = new Attributes();
        attributes.add("src", "https://example.com/image1.wbep");
        attributes.add("alt", "Image1");
        element = new Element(Tag.valueOf("img"), "", attributes);
    }

    @Nested
    @DisplayName("createImage Method Tests")
    class CreateImage {

        @Test
        @DisplayName("Test: Given a Jsoup Element, When Creating Image, Then Image Factory Should Correctly Set")
        void givenImageTag_whenCreatingImage_thenImageDescriptionShouldBeSet() {
            // When
            imageFactory.setImageTag(element);
            Image result = imageFactory.createImage();

            // Then
            assertEquals("Image1", result.getDescription());
        }

    }

    @Nested
    @DisplayName("getFactoryType Method Tests")
    class GetFactoryType {

        @Test
        @DisplayName("Test: Given a ImageFactory Type, When Retrieving, Then Return Correct Image Type")
        void givenFactoryType_whenRetrieving_thenReturnCorrectType() {
            assertEquals("Default", imageFactory.getFactoryType());
        }

    }

}
