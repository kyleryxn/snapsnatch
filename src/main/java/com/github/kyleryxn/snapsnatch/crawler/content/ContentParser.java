package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;

import java.util.*;

public interface ContentParser {

    Content getContentType();

    void parse(String content);

    default void setBaseURL(String baseURL) {
    }

    default Set<Image> getImages() {
        return new HashSet<>();
    }

    default Set<String> getLinks() {
        return new HashSet<>();
    }

    default Map<String, List<String>> getAllDirectives() {
        return new HashMap<>();
    }

    default List<String> getDirectives(String header) {
        return new ArrayList<>();
    }

}