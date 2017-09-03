package com.java.a31.androidappproject.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public class NewsDetailWithImages extends NewsDetail implements INewsDetailWithImages {
    List<Bitmap> images;

    public void setImages(List<Bitmap> images) {
        this.images = images;
    }

    @Override
    public List<Bitmap> getImages() {
        return images;
    }
}
