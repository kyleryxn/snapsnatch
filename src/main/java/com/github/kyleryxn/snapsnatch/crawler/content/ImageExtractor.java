package com.github.kyleryxn.snapsnatch.crawler.content;

import com.github.kyleryxn.snapsnatch.image.ImageService;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class ImageExtractor implements ElementExtractor {
    private final ImageService imageService;

    ImageExtractor(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public String getExtractorType() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<Image> extractImages(Elements imageTags) {
        return imageTags.stream()
                .map(imageService::processImage)
                .collect(Collectors.toSet());
    }

}