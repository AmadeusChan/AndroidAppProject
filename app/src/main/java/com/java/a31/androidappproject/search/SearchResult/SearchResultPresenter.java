package com.java.a31.androidappproject.search.SearchResult;

import android.util.Log;

import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.java.a31.androidappproject.news.NewsList.NewsListContract;
import com.java.a31.androidappproject.news.NewsList.NewsListPresenter;

/**
 * Created by zwei on 2017/9/9.
 */

public class SearchResultPresenter extends NewsListPresenter {

    private static final String TAG = "SearchResultPresenter";

    public SearchResultPresenter(NewsListContract.View view, String query) {
        super(view);

        try {
            mNewsList = NewsManager.getInstance().searchNews(query, INews.NORMAL_MODE);
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "SearchResultPresenter", e);
        }
    }

    @Override
    public void loadNewsList(int size, int pageNo, int category) {
        mNewsList.getMore(size, pageNo, this);
    }
}
