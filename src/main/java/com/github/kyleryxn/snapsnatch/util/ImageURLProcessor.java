package com.github.kyleryxn.snapsnatch.util;

public class ImageURLProcessor {
    private String url;

    public ImageURLProcessor() {
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getImageExtension() {
        int queryIndex = url.lastIndexOf('?');

        // Remove any query parameters if present
        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }

        return url.substring(url.lastIndexOf('.') + 1);
    }

}