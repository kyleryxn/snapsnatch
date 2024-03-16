package com.github.kyleryxn.snapsnatch.image.model;

public abstract class Image implements Comparable<Image> {

    public abstract String getDescription();

    public abstract boolean isLogo();

    public abstract String getURL();

}