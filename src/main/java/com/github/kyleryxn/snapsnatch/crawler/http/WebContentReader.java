package com.github.kyleryxn.snapsnatch.crawler.http;

import com.github.kyleryxn.snapsnatch.util.ContentReader;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebContentReader implements ContentReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContentReader.class);
    private final CloseableHttpClient httpClient;
    private final ResponseHandler<String> responseHandler;

    @Autowired
    public WebContentReader(CloseableHttpClient httpClient, ResponseHandler<String> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    @Override
    public String readContent(String url) {
        String content = "";

        try {
            HttpGet httpGet = new HttpGet(url);
            content = httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return content;
    }

}