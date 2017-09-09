package com.java.a31.androidappproject.models.database;

import android.util.Log;

import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

import java.util.List;

/**
 * Created by amadeus on 9/9/17.
 */

public class ImprovedFavoriteNewsList implements INewsList {

    private FavoriteNewsList favoriteNewsList;

    public ImprovedFavoriteNewsList(FavoriteNewsList favoriteNewsList) {
        this.favoriteNewsList=favoriteNewsList;
    }

    @Override
    public void reset() {
        Log.d("favorite", "Improved reset");
        try {
            favoriteNewsList=NewsManager.getInstance().getSimpleFavoriteNews();
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        favoriteNewsList.getMore(size, listener);
    }

    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        if (pageNo==1) reset();
        favoriteNewsList.getMore(size, pageNo, category, listener);
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        if (pageNo==1) reset();
        favoriteNewsList.getMore(size, pageNo, listener);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        favoriteNewsList.setFilter(filter);
    }
}
