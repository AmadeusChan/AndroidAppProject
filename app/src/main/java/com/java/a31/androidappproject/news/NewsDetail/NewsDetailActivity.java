package com.java.a31.androidappproject.news.NewsDetail;

import android.support.annotation.NonNull;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailActivity implements NewsDetailContract.View {

    private NewsDetailContract.Presenter mPresenter;

    @Override
    public void setPresenter(@NonNull NewsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
