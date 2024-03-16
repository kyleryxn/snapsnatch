package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import com.github.kyleryxn.snapsnatch.image.model.PNGImage;
import org.jsoup.nodes.Element;

public class PNGImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    public PNGImageFactory() {
        this.factoryUtil = new ImageFactoryUtil();
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

        return new PNGImage(name, isLogo, url);
    }

}