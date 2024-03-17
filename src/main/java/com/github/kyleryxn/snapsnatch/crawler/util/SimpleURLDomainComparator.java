package com.github.kyleryxn.snapsnatch.crawler.util;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
class SimpleURLDomainComparator implements URLDomainChecker {

    @Override
    public boolean isSameDomain(String baseURL, String compareURL) {
        try {
            URI uri = new URI(compareURL);
            URI startURI = new URI(baseURL);
            return uri.getHost().equalsIgnoreCase(startURI.getHost());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}