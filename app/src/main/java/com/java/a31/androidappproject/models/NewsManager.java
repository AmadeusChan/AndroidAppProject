package com.java.a31.androidappproject.models;

import android.content.Context;

import java.util.List;

/**
 * Created by amadeus on 9/5/17.
 */

public class NewsManager implements INewsManager {
    private static NewsManager instance=null;
    private Context context;

    private NewsManager(Context context) {
        this.context=context;
    }

    public static synchronized NewsManager getInstance(Context context) {
        if (instance==null) {
            return instance=new NewsManager(context);
        } else {
            return instance;
        }
    }

    @Override
    public INewsList getLatestNews(int mode) {
        return null;
    }

    @Override
    public INewsDetail getNewsDetails(String ID, int mode) {
        return null;
    }

    @Override
    public void setAsFavorite(String ID) {

    }

    @Override
    public void setAsNotFavorite(String ID) {

    }

    @Override
    public INewsList getFavoriteNews() {
        return null;
    }

    @Override
    public void speakText(String text) {

    }

    @Override
    public List<String> getCategoryList() {
        return null;
    }

    @Override
    public void addCategory(String category) {

    }

    @Override
    public void deleteCategory(String category) {

    }

}
