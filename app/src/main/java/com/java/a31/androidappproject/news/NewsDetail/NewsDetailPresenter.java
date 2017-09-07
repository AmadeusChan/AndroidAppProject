package com.java.a31.androidappproject.news.NewsDetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter, INewsListener<INewsDetail> {

    private static final String TAG = "NewsDetailPresenter";

    private NewsDetailContract.View mView;

    public NewsDetailPresenter(@NonNull NewsDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getResult(INewsDetail newsDetail) {
        mView.onSuccess(newsDetail);
    }

    @Override
    public void loadNewsDetail(String newsId) {
        try {
            NewsManager.getInstance().getNewsDetails(newsId, INews.NORMAL_MODE, this);
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "loadNewsDetail", e);
        }
    }

}
