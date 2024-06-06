package com.github.kyleryxn.snapsnatch.crawler.util;

import com.github.kyleryxn.snapsnatch.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CompositeCrawlerUtil Tests")
class CompositeCrawlerUtilTest extends BaseTest {

    @Autowired
    private CompositeCrawlerUtil compositeCrawlerUtil;

    @Nested
    @DisplayName("isValidURL Method Tests")
    class IsValidURL {

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Valid URLs, When Validating Syntax, Then Return Correct Result")
        @ValueSource(strings = {"https://www.example.com/", "https://www.example.com/page1", "https://www.example.com/page2"})
        void givenValidURL_whenValidatingSyntax_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isValidURL(url);

            // Then
            assertTrue(result);
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Invalid URLs, When Validating Syntax, Then Return Correct Result")
        @ValueSource(strings = {"javascript:void()", "data:www.example.com/", "#images",
                "https://www.example.com/page2/text.pdf", "mailto:someone@example.com"})
        void givenInvalidURL_whenValidatingSyntax_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isValidURL(url);

            // Then
            assertFalse(result);
        }

    }

    @Nested
    @DisplayName("isSameDomain Method Tests")
    class IsSameDomain {

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Same Domain URLs, When Validating Domain, Then Return Correct Result")
        @ValueSource(strings = {"https://www.example.com/", "https://www.example.com/page1", "https://www.example.com/page2"})
        void givenSameDomainURL_whenValidatingDomain_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isSameDomain(url, "https://www.example.com/");

            // Then
            assertTrue(result);
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Different Domain URLs, When Validating Domain, Then Return Correct Result")
        @ValueSource(strings = {"https://www.example.com/", "https://www.example.com/page1", "https://www.example.com/page2"})
        void givenDifferentDomainURL_whenValidatingDomain_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isSameDomain(url, "https://www.example2.com/");

            // Then
            assertFalse(result);
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Malformed URL, When Comparing, Then Throw RuntimeException")
        @ValueSource(strings = {"https://finance.yahoo.com/q/h?s=^IXIC"})
        void givenMalformedURL_whenComparing_thenReturnRuntimeException(String compareURL) {
            // Then
            assertThrows(RuntimeException.class, () -> compositeCrawlerUtil.isSameDomain("https://finance.yahoo.com", compareURL));
        }

    }

    @Nested
    @DisplayName("isDuplicate Method Tests")
    class IsDuplicate {
        private Set<String> links;

        @BeforeEach
        void setUp() {
            links = Set.of("https://www.example.com/page1", "https://www.example.com/page2");
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Duplicate URL, When Checking, Then Return Correct Result")
        @ValueSource(strings = {"https://www.example.com/page1", "https://www.example.com/page2"})
        void givenDuplicateURL_whenChecking_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isDuplicate(url, links);

            // Then
            assertTrue(result);
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Duplicate URL, When Checking, Then Return Correct Results")
        @ValueSource(strings = {"https://www.example.com/page1/subpage1", "https://www.example.com/page2/subpage2"})
        void givenUniqueURL_whenChecking_thenReturnCorrectResult(String url) {
            // When
            boolean result = compositeCrawlerUtil.isDuplicate(url, links);

            // Then
            assertFalse(result);
        }

    }

    @Nested
    @DisplayName("resolveURL Method Tests")
    class ResolveURL {

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Relative URL, When Resolving, Then Return Correct Result")
        @ValueSource(strings = {"/page1", "/page2"})
        void givenRelativeURLBaseURLWithSlash_whenResolving_thenReturnCorrectResult(String url) {
            // When
            String result = compositeCrawlerUtil.resolveURL("https://www.example.com/", url);

            // Then
            assertTrue(result.contains("https://www.example.com"));
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Relative URL, When Resolving, Then Return Correct Result")
        @ValueSource(strings = {"/page1", "/page2"})
        void givenRelativeURLBaseURLWithoutSlash_whenResolving_thenReturnCorrectResult(String url) {
            // When
            String result = compositeCrawlerUtil.resolveURL("https://www.example.com", url);

            // Then
            assertTrue(result.contains("https://www.example.com"));
        }

        @ParameterizedTest(name = "Test {index}: {0}")
        @DisplayName("Test: Given Absolute URL, When Resolving, Then Return Correct Result")
        @ValueSource(strings = {"https://www.example.com/page1", "https://www.example.com/page2"})
        void givenAbsoluteURL_whenResolving_thenReturnCorrectResult(String url) {
            // When
            String result = compositeCrawlerUtil.resolveURL("https://www.example.com/", url);

            // Then
            assertTrue(result.contains("https://www.example.com"));
        }

    }

}
