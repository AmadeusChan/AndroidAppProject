package com.java.a31.androidappproject.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsDetailWithImages extends INewsDetail {
    List<Bitmap> getImages();
}
