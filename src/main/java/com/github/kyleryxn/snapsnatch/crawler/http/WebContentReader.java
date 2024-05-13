package com.github.kyleryxn.snapsnatch.crawler.http;

import com.github.kyleryxn.snapsnatch.crawler.content.PageContent;
import com.github.kyleryxn.snapsnatch.util.ContentReader;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebContentReader implements ContentReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContentReader.class);
    private final CloseableHttpClient httpClient;
    private final ResponseHandler<PageContent> responseHandler;

    public WebContentReader(CloseableHttpClient httpClient, ResponseHandler<PageContent> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    @Override
    public PageContent readContent(String url) {
        PageContent content = new PageContent("", "");

        try {
            HttpGet httpGet = new HttpGet(url);
            content = httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            LOGGER.error("Exception fetching URL {}: {}", url, e.getMessage());
        }

        return content;
    }

}