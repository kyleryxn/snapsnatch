package com.github.kyleryxn.snapsnatch.image;

import com.github.kyleryxn.snapsnatch.image.factory.ImageFactory;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final Map<String, ImageFactory> imageFactories;

    public ImageService(List<ImageFactory> imageFactories) {
        this.imageFactories = imageFactories.stream()
                .collect(Collectors.toMap(ImageFactory::getFactoryType, Function.identity()));
    }

    public Image processImage(Element imageTag) {
        String imageType = getImageType(imageTag);
        ImageFactory factory = imageFactories.getOrDefault(imageType, defaultFactory());
        factory.setImageTag(imageTag);

        return factory.createImage();
    }

    private String getImageType(Element imageTag) {
        String url = imageTag.attr("src");
        int queryIndex = url.lastIndexOf('?');

        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }

        return url.substring(url.lastIndexOf('.') + 1);
    }

    private ImageFactory defaultFactory() {
        return imageFactories.get("DEFAULT");
    }

}
