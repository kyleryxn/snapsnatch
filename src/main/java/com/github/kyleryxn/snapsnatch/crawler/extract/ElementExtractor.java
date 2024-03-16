package com.github.kyleryxn.snapsnatch.crawler.extract;

import com.github.kyleryxn.snapsnatch.model.Image;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public interface ElementExtractor {

    default Set<Image> extractImages(Elements imgTags) {
        return new HashSet<>();
    }

    default Set<String> extractLinks(Elements anchorTags) {
        return new HashSet<>();
    }

}