package com.github.kyleryxn.snapsnatch.crawler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class StringHttpResponseHandler implements HttpClientResponseHandler<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringHttpResponseHandler.class);

    @Override
    public String handleResponse(ClassicHttpResponse httpResponse) {
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (HttpException | IOException e) {
            LOGGER.error(e.getMessage());
        }

        return "";
    }

}