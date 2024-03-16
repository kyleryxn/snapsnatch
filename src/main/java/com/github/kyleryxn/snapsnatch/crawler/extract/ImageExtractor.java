package com.github.kyleryxn.snapsnatch.crawler.extract;

import com.github.kyleryxn.snapsnatch.factory.*;
import com.github.kyleryxn.snapsnatch.model.Image;
import com.github.kyleryxn.snapsnatch.util.ImageURLProcessor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class ImageExtractor implements ElementExtractor {
    private final Map<String, Supplier<ImageFactory>> factories;
    private final ImageURLProcessor processor;

    public ImageExtractor() {
        this.factories = new HashMap<>();
        this.processor = new ImageURLProcessor();

        factories.put("gif", GIFImageFactory::new);
        factories.put("jpeg", JPEGImageFactory::new);
        factories.put("jpg", JPEGImageFactory::new);
        factories.put("png", PNGImageFactory::new);
        factories.put("svg", SVGImageFactory::new);
        factories.put("webp", WEBPImageFactory::new);
    }

    @Override
    public Set<Image> extractImages(Elements elements) {
        Set<Image> images;

        images = elements.stream()
                .map(tag -> {
                    String url = tag.attr("src");
                    processor.setURL(url);
                    String extension = processor.getImageExtension();

                    return processTag(extension, tag);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return images;
    }

    private Image processTag(String extension, Element tag) {
        Supplier<ImageFactory> factorySupplier = factories.get(extension);

        if (factorySupplier != null) {
            ImageFactory factory = factorySupplier.get();
            factory.setTag(tag);
            return factory.createImage();
        } else {
            return null;
        }
    }

}