package com.java.a31.androidappproject.main;

import android.support.annotation.NonNull;

import com.java.a31.androidappproject.R;

/**
 * Created by zwei on 2017/9/7.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private int mId;

    public MainPresenter(@NonNull MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean switchNavigation(int id) {
        if (mId == id) {
            return false;
        } else {
            mId = id;
        }

        switch (id) {
            case R.id.navigation_home:
                mView.switchToHome();
                return true;
            case R.id.navigation_favorite:
                mView.switchToFavorite();
                return true;
            case R.id.navigation_settings:
                mView.switchToSettings();
                return true;
            default:
                return false;
        }
    }

}
