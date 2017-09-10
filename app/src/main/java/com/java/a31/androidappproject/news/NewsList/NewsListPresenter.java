package com.java.a31.androidappproject.news.NewsList;

import android.util.Log;

import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

import java.util.List;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsListPresenter implements NewsListContract.Presenter, INewsListener<List<INewsIntroduction>> {

    private static final String TAG = "NewsListPresenter";

    private NewsListContract.View mView;

    protected INewsList mNewsList;

    public NewsListPresenter(NewsListContract.View view, boolean isTextOnly) {
        mView = view;
        mView.setPresenter(this);

        try {
            if (isTextOnly)
                mNewsList = NewsManager.getInstance().getLatestNews(INews.TEXT_ONLY_MODE);
            else
                mNewsList = NewsManager.getInstance().getLatestNews(INews.NORMAL_MODE);
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "NewsListPresenter", e);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNewsList(int size, int pageNo, int category) {
        mNewsList.getMore(size, pageNo, category, this);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        mNewsList.setFilter(filter);
    }

    @Override
    public void getResult(List<INewsIntroduction> newsList) {
        if (newsList.size() > 0) {
            mView.onSuccess(newsList);
        } else {
            mView.onFailure();
        }
    }

}
