package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ContentParser {

    Content getContentType();

    void parse(String content);

    default void setBaseURL(String baseURL) {
    }

    default Set<Image> getImages() {
        return null;
    }

    default Set<String> getLinks() {
        return null;
    }

    default Map<String, List<String>> getDirectives() {
        return null;
    }

}