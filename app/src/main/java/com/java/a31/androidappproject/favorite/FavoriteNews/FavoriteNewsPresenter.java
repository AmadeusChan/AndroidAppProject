package com.java.a31.androidappproject.favorite.FavoriteNews;

import android.util.Log;

import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.java.a31.androidappproject.news.NewsList.NewsListContract;
import com.java.a31.androidappproject.news.NewsList.NewsListPresenter;

/**
 * Created by zwei on 2017/9/7.
 */

public class FavoriteNewsPresenter extends NewsListPresenter {

    private static final String TAG = "FavoriteNewsPresenter";

    public FavoriteNewsPresenter(NewsListContract.View view, boolean mode) {
        super(view, mode);

        try {
            mNewsList = NewsManager.getInstance().getFavoriteNews();
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "FavoriteNewsPresenter", e);
        }
    }

    @Override
    public void loadNewsList(int size, int pageNo, int category) {
        mNewsList.getMore(size, pageNo, this);
    }
}
