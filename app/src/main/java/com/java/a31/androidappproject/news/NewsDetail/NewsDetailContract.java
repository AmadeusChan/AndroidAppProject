package com.java.a31.androidappproject.news.NewsDetail;

import com.java.a31.androidappproject.BasePresenter;
import com.java.a31.androidappproject.BaseView;
import com.java.a31.androidappproject.models.INewsDetail;

/**
 * Created by zwei on 2017/9/7.
 */

public interface NewsDetailContract {

    interface View extends BaseView<Presenter> {

        void onSuccess(INewsDetail newsDetail);

        void onFailure();

    }

    interface Presenter extends BasePresenter {

        void loadNewsDetail(String newsId);

    }

}
