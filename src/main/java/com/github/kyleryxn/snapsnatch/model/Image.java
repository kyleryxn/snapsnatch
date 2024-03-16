package com.github.kyleryxn.snapsnatch.model;

public abstract class Image implements Comparable<Image> {

    public abstract String getName();

    public abstract boolean isLogo();

    public abstract String getUrl();

    public abstract String getType();



}