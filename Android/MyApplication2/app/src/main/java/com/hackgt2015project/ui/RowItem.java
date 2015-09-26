package com.hackgt2015project.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by jonathanrobins on 9/26/15.
 */

public class RowItem {
    private Bitmap image;
    private String text;

    public RowItem(Bitmap mainImage, String mainText) {
        this.image = mainImage;
        this.text = mainText;
    }

    public RowItem(Bitmap image){
        this.image = image;
    }

    public RowItem(){
    }

    public RowItem(String mainText){
        this.text = mainText;
    }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
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