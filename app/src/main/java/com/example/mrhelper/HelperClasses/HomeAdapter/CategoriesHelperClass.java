package com.example.mrhelper.HelperClasses.HomeAdapter;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    int image;
    String title;
    GradientDrawable gradient;

    public CategoriesHelperClass(GradientDrawable gradient, int image, String title) {
        this.gradient = gradient;
        this.image = image;
        this.title = title;
    }

    public GradientDrawable getGradient() {
        return gradient;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
