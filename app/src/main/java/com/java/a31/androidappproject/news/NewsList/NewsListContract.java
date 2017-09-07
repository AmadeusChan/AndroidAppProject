package com.java.a31.androidappproject.news.NewsList;

import com.java.a31.androidappproject.BasePresenter;
import com.java.a31.androidappproject.BaseView;
import com.java.a31.androidappproject.models.INewsIntroduction;

import java.util.List;

/**
 * Created by zwei on 2017/9/7.
 */

public interface NewsListContract {

    interface View extends BaseView<Presenter> {

        void onSuccess(List<INewsIntroduction> newsList);

        void onFailure();

    }

    interface Presenter extends BasePresenter {

        void loadNewsList(int size, int pageNo, int category);

    }

}
