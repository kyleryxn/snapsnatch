package com.github.kyleryxn.snapsnatch.crawler.util;

import java.util.List;

public class URLProcessor {
    private static final List<String> INVALID_EXTENSIONS = List.of("#", "javascript:", "mailto:", "data:", "ftp:", ".pdf", ".xml");

    public boolean isValidURL(String url) {
        for (String ext : INVALID_EXTENSIONS) {
            if (url.startsWith(ext) || url.endsWith(ext)) {
                return false;
            }
        }

        return true;
    }

}