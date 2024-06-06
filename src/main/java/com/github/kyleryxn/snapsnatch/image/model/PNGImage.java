package com.github.kyleryxn.snapsnatch.image.model;

public class PNGImage extends Image {

    private final String description;
    private final boolean isLogo;
    private final String url;

    public PNGImage(String description, boolean isLogo, String url) {
        this.description = description;
        this.isLogo = isLogo;
        this.url = url;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isLogo() {
        return isLogo;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String toString() {
        return "PNGImage{" +
                ", description='" + description + '\'' +
                ", isLogo=" + isLogo +
                ", url='" + url + '\'' +
                '}';
    }

}