package com.github.kyleryxn.snapsnatch.image.model;

public class GIFImage extends Image {
    private final String description;
    private final boolean isLogo;
    private final String url;

    public GIFImage(String description, boolean isLogo, String url) {
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
    public int hashCode() {
        return getURL() != null ? getURL().hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        GIFImage img = (GIFImage) obj;

        return getURL() != null ? getURL().equalsIgnoreCase(img.getURL()) : img.getURL() == null;
    }

    @Override
    public String toString() {
        return "GIFImage{" +
                "name='" + description + '\'' +
                ", isLogo=" + isLogo +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(Image o) {
        return description.compareTo(o.getDescription());
    }

}