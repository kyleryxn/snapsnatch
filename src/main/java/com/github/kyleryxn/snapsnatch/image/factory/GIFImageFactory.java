package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.GIFImage;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
class GIFImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    public GIFImageFactory(ImageFactoryUtil factoryUtil) {
        this.factoryUtil = factoryUtil;
    }

    @Override
    public void setImageTag(Element imgTag) {
        this.imgTag = imgTag;
    }

    @Override
    public Image createImage() {
        String name = factoryUtil.parseDescription(imgTag);
        boolean isLogo = factoryUtil.parseLogo(imgTag);
        String url = factoryUtil.parseURL(imgTag);

        return new GIFImage(name, isLogo, url);
    }

    @Override
    public String getFactoryType() {
        return "GIF";
    }

}