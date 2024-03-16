package com.github.kyleryxn.snapsnatch.image.factory;

import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.jsoup.nodes.Element;

public interface ImageFactory {

    void setTag(Element imgTag);

    Image createImage();

}