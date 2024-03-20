package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.DefaultImage;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
class DefaultImageFactory implements ImageFactory {
    private final ImageFactoryUtil imageFactoryUtil;
    private Element imageTag;

    DefaultImageFactory(ImageFactoryUtil imageFactoryUtil) {
        this.imageFactoryUtil = imageFactoryUtil;
    }

    @Override
    public void setImageTag(Element imageTag) {
        this.imageTag = imageTag;
    }

    @Override
    public Image createImage() {
        String description = imageFactoryUtil.parseDescription(imageTag);
        boolean isLogo = imageFactoryUtil.parseLogo(imageTag);
        String url = imageFactoryUtil.parseURL(imageTag);

        return new DefaultImage(description, isLogo, url);
    }

    @Override
    public String getFactoryType() {
        return getClass().getSimpleName();
    }

}
