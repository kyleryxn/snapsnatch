package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.GIFImage;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;

public class GIFImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    public GIFImageFactory() {
        this.factoryUtil = new ImageFactoryUtil();
    }

    @Override
    public void setTag(Element imgTag) {
        this.imgTag = imgTag;
    }

    @Override
    public Image createImage() {
        String name = factoryUtil.parseName(imgTag);
        boolean isLogo = factoryUtil.parseLogo(imgTag);
        String url = factoryUtil.parseURL(imgTag);

        return new GIFImage(name, isLogo, url);
    }

}