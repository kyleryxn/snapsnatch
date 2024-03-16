package com.github.kyleryxn.snapsnatch.factory;

import com.github.kyleryxn.snapsnatch.model.Image;
import com.github.kyleryxn.snapsnatch.model.JPEGImage;
import org.jsoup.nodes.Element;

public class JPEGImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    public JPEGImageFactory() {
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

        return new JPEGImage(name, isLogo, url);
    }

}