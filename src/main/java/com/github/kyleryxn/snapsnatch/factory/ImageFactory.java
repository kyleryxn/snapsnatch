package com.github.kyleryxn.snapsnatch.factory;

import com.github.kyleryxn.snapsnatch.model.Image;
import org.jsoup.nodes.Element;

public interface ImageFactory {

    void setTag(Element imgTag);

    Image createImage();

}