package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.http.HttpResponseHandler;
import com.github.kyleryxn.snapsnatch.util.Reader;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebContentReader implements Reader {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContentReader.class);

    private final CloseableHttpClient httpClient;

    @Autowired
    public WebContentReader(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String readContent(String url) {
        HttpResponseHandler responseHandler = new HttpResponseHandler();
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