package com.github.kyleryxn.snapsnatch.website.model;

import com.github.kyleryxn.snapsnatch.image.model.Image;

import java.util.List;


public class Website {
    private Long id;
    private String name;
    private String url;
    private List<Image> images;

    public Website() {
        this.name = null;
        this.url = null;
    }

    public Website(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
