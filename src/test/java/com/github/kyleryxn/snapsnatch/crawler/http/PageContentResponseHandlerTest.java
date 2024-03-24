package com.github.kyleryxn.snapsnatch.crawler.http;

import com.github.kyleryxn.snapsnatch.crawler.content.PageContent;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@DisplayName("PageContentResponseHandler Tests")
class PageContentResponseHandlerTest {

    private AutoCloseable closeable;

    @Autowired
    private ResponseHandler<PageContent> responseHandler;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Test: Given Successful HTTP Response, When Handling Response, Then Return Correct Content")
    void givenSuccessfulHttpResponse_whenHandlingResponse_thenReturnCorrectContent() throws IOException, HttpException {
        // Given
        String expected = "Success Response";
        when(httpResponse.getEntity()).thenReturn(httpEntity);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(expected.getBytes());
        when(httpEntity.getContent()).thenReturn(inputStream);

        // When
        PageContent result = responseHandler.handleResponse(httpResponse);

        // Then
        assertEquals("Success Response", result.content());
    }

    @Test
    @DisplayName("Test: Given Failed HTTP Response, When Handling Response, Then Return EmptyContent")
    void givenFailedHttpResponseThrowsIOException_whenHandlingResponse_thenReturnEmptyString() throws IOException, HttpException {
        // Given
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenThrow(new IOException("IO Exception occurred"));

        // When
        PageContent result = responseHandler.handleResponse(httpResponse);

        // Then
        assertEquals("", result.content());
    }


}