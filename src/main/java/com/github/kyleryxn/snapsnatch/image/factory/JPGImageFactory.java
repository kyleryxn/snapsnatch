package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import com.github.kyleryxn.snapsnatch.image.model.JPGImage;
import org.jsoup.nodes.Element;

public class JPGImageFactory implements ImageFactory {
    private final ImageFactoryUtil factoryUtil;
    private Element imgTag;

    public JPGImageFactory() {
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

        return new JPGImage(name, isLogo, url);
    }

}