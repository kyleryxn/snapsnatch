package com.github.kyleryxn.snapsnatch.crawler.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SimpleURLValidator implements URLValidator {
    private final List<String> INVALID_EXTENSIONS = List.of("#", "javascript:", "mailto:", "data:", "ftp:", ".pdf", ".xml");

    @Override
    public boolean isValidURL(String url) {
        for (String ext : INVALID_EXTENSIONS) {
            if (url.startsWith(ext) || url.endsWith(ext)) {
                return false;
            }
        }

        return true;
    }

}