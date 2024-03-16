package com.github.kyleryxn.snapsnatch.image.factory;

import org.jsoup.nodes.Element;

import java.util.stream.StreamSupport;

public class ImageFactoryUtil {

    public String parseName(Element imgTag) {
        return imgTag.attr("alt").isBlank() ? "None" : imgTag.attr("alt").trim();
    }

    public boolean parseLogo(Element imgTag) {
        return StreamSupport.stream(imgTag.attributes().spliterator(), false) // set to 'true' to enable parallel streams
                .anyMatch(attribute -> {
                    String attributeValue = attribute.getValue().toLowerCase();
                    return attributeValue.contains("logo") || attributeValue.contains("brand");
                });
    }

    public String parseURL(Element imgTag) {
        String url = imgTag.attr("src");

        if (url.startsWith("//")) {
            url = "https:" + url;
        }

        return url.replaceAll("\\s", "%20");
    }

}