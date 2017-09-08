package com.java.a31.androidappproject.news.NewsDetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsDetail;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter, INewsListener<INewsDetail> {

    private static final String TAG = "NewsDetailPresenter";

    private NewsManager mNewsManager;

    private INewsDetail mNewsDetail;

    private NewsDetailContract.View mView;

    public NewsDetailPresenter(@NonNull NewsDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
        try {
            mNewsManager = NewsManager.getInstance();
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "NewsDetailPresenter", e);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void getResult(INewsDetail newsDetail) {
        mNewsDetail = newsDetail;
        if (mNewsDetail == null) {
            mView.onFailure();
        } else {
            mView.onSuccess(mNewsDetail);
            if (mNewsDetail.isFavorite()) {
                mView.setLike();
            } else {
                mView.setUnLike();
            }
        }
    }

    @Override
    public void loadNewsDetail(String newsId) {
        mNewsManager.getNewsDetails(newsId, INews.NORMAL_MODE, this);
    }

    @Override
    public void onLikeButtonClick() {
        if (mNewsDetail == null) {
            return;
        }
        if (mNewsDetail.isFavorite()) {
            mNewsManager.setAsNotFavorite(mNewsDetail);
            mView.setUnLike();
        } else {
            mNewsManager.setAsFavorite(mNewsDetail);
            mView.setLike();
        }
    }

    @Override
    public void onReadButtonClick() {
        if (mNewsDetail == null) {
            return;
        }
        mNewsManager.speakText(mNewsDetail.getTitle());
        mNewsManager.speakText(mNewsDetail.getAuthor());
        mNewsManager.speakText(mNewsDetail.getContent());
    }

    @Override
    public void onShareButtonCLick() {
        if (mNewsDetail == null) {
            return;
        }
        mView.share();
    }

}
