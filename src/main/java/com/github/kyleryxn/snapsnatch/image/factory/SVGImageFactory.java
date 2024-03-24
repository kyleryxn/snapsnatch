package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import com.github.kyleryxn.snapsnatch.image.model.SVGImage;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
class SVGImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    SVGImageFactory(ImageFactoryUtil factoryUtil) {
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

        return new SVGImage(name, isLogo, url);
    }

    @Override
    public String getFactoryType() {
        return getClass().getSimpleName().replace("ImageFactory", "");
    }

}