package com.github.kyleryxn.snapsnatch.crawler.http;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;

public interface ResponseHandler<T> extends HttpClientResponseHandler<T> {

    @Override
    default T handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {
        return null;
    }

}
