package com.github.kyleryxn.snapsnatch.crawler;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@DisplayName("WebContentReader Tests")
class WebContentReaderTest {
    private AutoCloseable closeable;

    @Autowired
    private WebContentReader contentReader;

    @MockBean
    @Autowired
    private CloseableHttpClient mockHttpClient;

    @Mock
    private ClassicHttpResponse mockResponse;

    @Mock
    private HttpEntity mockEntity;

    @BeforeEach
    void setUp() throws IOException {
        closeable = openMocks(this);

        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream("Expected Content".getBytes()));
        when(mockResponse.getEntity()).thenReturn(mockEntity);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Test: Given Http Client, When Reading Page, Then Return Correct Content")
    void givenHttpClient_whenReadingWebContent_thenReturnCorrectContent() throws IOException {
        // Given
        String expected = "Expected Content";
        setupHttpClientToReturnResponse();

        // When
        String actual = contentReader.readContent("https://example.com");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Given Http Client, When Reading Content Fails, Then Throw IOException")
    public void givenHttpClient_whenReadWebContentFails_thenContentIsEmpty() throws IOException {
        // Given
        when(mockHttpClient.execute(any(HttpGet.class), ArgumentMatchers.<HttpClientResponseHandler<String>>any()))
                .thenThrow(new IOException("Failed to execute"));

        // When
        String content = contentReader.readContent("https://example.com");

        // Then
        assertTrue(content.isEmpty());
    }

    void setupHttpClientToReturnResponse() throws IOException {
        when(mockHttpClient.execute(any(HttpGet.class), any(HttpClientResponseHandler.class)))
                .thenAnswer(invocation -> {
                    HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                    return handler.handleResponse(mockResponse);
                });
    }

}
