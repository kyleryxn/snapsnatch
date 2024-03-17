package com.github.kyleryxn.snapsnatch.image.factory;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
class ImageFactoryUtil {

    public String parseDescription(Element imgTag) {
        return imgTag.attr("alt").isBlank() ? "None" : imgTag.attr("alt").trim();
    }

    public boolean parseLogo(Element imgTag) {
        return StreamSupport.stream(imgTag.attributes().spliterator(), false) // set to 'true' to enable parallel streams
                .anyMatch(attribute -> {
                    String attributeValue = attribute.getValue().toLowerCase();
                    return attributeValue.contains("logo") || attributeValue.contains("brand");
                });
    }

    public String parseURL(Element imageTag) {
        String url = imageTag.attr("src");

        if (url.startsWith("//")) {
            url = "https:" + url;
        }

        return url.replaceAll("\\s", "%20");
    }

}