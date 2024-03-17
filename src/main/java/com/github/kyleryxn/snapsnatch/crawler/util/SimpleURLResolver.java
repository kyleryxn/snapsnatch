package com.github.kyleryxn.snapsnatch.crawler.util;

import org.springframework.stereotype.Component;

@Component
public class SimpleURLResolver implements URLResolver {

    @Override
    public String resolveURL(String baseURL, String url) {
        if (url.startsWith("/")) {
            if (baseURL.endsWith("/")) {
                return baseURL.substring(0, baseURL.length() - 1) + url;
            } else {
                return baseURL + url;
            }
        }

        return url;
    }

}
