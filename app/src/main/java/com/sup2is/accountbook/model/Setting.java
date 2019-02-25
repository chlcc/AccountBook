package com.sup2is.accountbook.model;

public class Setting {

    private int iconResource;
    private String title;

    public Setting(int iconResource, String title) {
        this.iconResource = iconResource;
        this.title = title;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getTitle() {
        return title;
    }
}
