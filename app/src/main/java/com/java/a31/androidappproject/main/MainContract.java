package com.java.a31.androidappproject.main;

import com.java.a31.androidappproject.BasePresenter;
import com.java.a31.androidappproject.BaseView;

/**
 * Created by zwei on 2017/9/7.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void switchToHome();

        void switchToFavorite();

        void switchToSettings();

    }

    interface Presenter extends BasePresenter {

        boolean switchNavigation(int id);

    }

}
