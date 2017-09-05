package com.java.a31.androidappproject.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsIntroduction extends INews {
    String getClassTag();
    String getID();
    String getSource();
    String getTitle();
    String getTime();
    String getURL();
    String getAuthor();
    String getLanguage();
    String getVideos();
    String getIntroduction();
    boolean isRead();
    boolean isFavorite();
    List<Bitmap> getImages();
}
