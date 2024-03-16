package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.model.Image;
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