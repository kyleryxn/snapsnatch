package com.github.kyleryxn.snapsnatch.model;

public class PNGImage extends Image {
    private final String name;
    private final boolean isLogo;
    private final String url;

    public PNGImage(String name, boolean isLogo, String url) {

        this.name = name;
        this.isLogo = isLogo;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLogo() {
        return isLogo;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getType() {
        return "PNG";
    }

    @Override
    public String toString() {
        return "PNGImage{" +
                "name='" + name + '\'' +
                ", isLogo=" + isLogo +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(Image o) {
        return name.compareTo(o.getName());
    }

}