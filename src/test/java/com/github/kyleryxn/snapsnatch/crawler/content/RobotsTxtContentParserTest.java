package com.github.kyleryxn.snapsnatch.crawler.content;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@ActiveProfiles("ci")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("RobotsTxtParser Tests")
class RobotsTxtContentParserTest {
    private AutoCloseable closeable;
    private String content;

    @Autowired
    private RobotsTxtContentParser robotsTxtParser;

    @Mock
    private BufferedReader reader;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        content = "User-agent: *\nDisallow: /private/\n\nUser-agent: bot\nDisallow: /restricted/";
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Nested
    @DisplayName("getContent Method Tests")
    class GetContentType {

        @Test
        @DisplayName("Given RobotsTxt Content, When Getting Type, Then Return Correct Content Type")
        public void givenRobotsTxtContent_whenGettingContent_thenReturnCorrectContent() {
            // When
            Content result = robotsTxtParser.getContentType();

            // Then
            assertEquals(Content.ROBOTS, result);
        }

    }

    @Nested
    @DisplayName("parse Method Tests")
    class Parse {

        @Test
        @DisplayName("Given RobotsTxt Content, When Parsing, Then Return Correct Size of RobotsTxt Map")
        public void givenRobotsTxtContent_whenParsing_thenReturnCorrectSizeOfRobotsTxtMap() throws Exception {
            // Given
            when(reader.readLine())
                    .thenReturn("User-agent: *")
                    .thenReturn("Disallow: /private/")
                    .thenReturn("") // Ensure blank lines are accounted for
                    .thenReturn("User-agent: bot")
                    .thenReturn("Disallow: /restricted/")
                    .thenReturn(null);

            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertEquals(2, robotsTxt.size());
        }

        @Test
        @DisplayName("Given RobotsTxt Content, When Parsing, Then Return Correct Disallowed Paths For All")
        void givenRobotsTxtContent_whenParsing_thenReturnCorrectDisallowedPathsForAll() throws IOException {
            // Given
            when(reader.readLine())
                    .thenReturn("User-agent: *")
                    .thenReturn("Disallow: /private/")
                    .thenReturn("") // Ensure blank lines are accounted for
                    .thenReturn("User-agent: bot")
                    .thenReturn("Disallow: /restricted/")
                    .thenReturn(null);

            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertEquals(List.of("/private/"), robotsTxt.get("*"));
        }

        @Test
        @DisplayName("Given RobotsTxt Content, When Parsing, Then Assert Disallowed Paths For All Not Empty")
        void givenRobotsTxtContent_whenParsing_thenReturnCorrectDisallowedPathsContentNotEmptyForAll() throws IOException {
            // Given
            when(reader.readLine())
                    .thenReturn("User-agent: *")
                    .thenReturn("Disallow: /private/")
                    .thenReturn("") // Ensure blank lines are accounted for
                    .thenReturn("User-agent: bot")
                    .thenReturn("Disallow: /restricted/")
                    .thenReturn(null);

            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertFalse(robotsTxt.get("*").isEmpty());
        }

        @Test
        @DisplayName("Given RobotsTxt Content, When Parsing, Then Return Disallowed Paths For Bot")
        void givenRobotsTxtContent_whenParsing_thenReturnCorrectDisallowedPathsForBot() throws IOException {
            // Given
            when(reader.readLine())
                    .thenReturn("User-agent: *")
                    .thenReturn("Disallow: /private/")
                    .thenReturn("") // Ensure blank lines are accounted for
                    .thenReturn("User-agent: bot")
                    .thenReturn("Disallow: /restricted/")
                    .thenReturn(null);

            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertEquals(List.of("/restricted/"), robotsTxt.get("bot"));
        }

        @Test
        @DisplayName("Given RobotsTxt Content, When Parsing, Then Assert Disallowed Paths For Bot Not Empty")
        void givenRobotsTxtContent_whenParsing_thenReturnCorrectDisallowedPathsContentNotEmptyForBot() throws IOException {
            // Given
            when(reader.readLine())
                    .thenReturn("User-agent: *")
                    .thenReturn("Disallow: /private/")
                    .thenReturn("") // Ensure blank lines are accounted for
                    .thenReturn("User-agent: bot")
                    .thenReturn("Disallow: /restricted/")
                    .thenReturn(null);

            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertFalse(robotsTxt.get("bot").isEmpty());
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Given Null or Empty RobotsTxt Content, When Parsing, Then Return Empty Map")
        @NullAndEmptySource
        void givenNullEmptyRobotsTxtContent_whenParsing_thenReturnCorrectSizeOfRobotsTxtMap(String content) {
            // When
            robotsTxtParser.parse(content);
            Map<String, List<String>> robotsTxt = robotsTxtParser.getAllDirectives();

            // Then
            assertTrue(robotsTxt.isEmpty());
        }

    }

}