package com.hackgt2015project.ui;

/**
 * Created by jonathanrobins on 9/26/15.
 */

public class RowItem {
    private int image;
    private String text;

    public RowItem(int mainImage, String mainText) {
        this.image = mainImage;
        this.text = mainText;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}