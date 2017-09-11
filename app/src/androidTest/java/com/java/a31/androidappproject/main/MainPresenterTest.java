package com.java.a31.androidappproject.main;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class MainPresenterTest {

    MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(new MainContract.View() {

            private static final String TAG = "View";

            private MainContract.Presenter mPresenter;

            @Override
            public void switchToHome() {
                Log.d(TAG, "switchToHome");
            }

            @Override
            public void switchToFavorite() {
                Log.d(TAG, "switchToFavorite");
            }

            @Override
            public void switchToSettings() {
                Log.d(TAG, "switchToSettings");
            }

            @Override
            public void setPresenter(MainContract.Presenter presenter) {
                mPresenter = presenter;
            }
        });
    }

    @Test
    public void start() throws Exception {
        presenter.start();
    }

    @Test
    public void switchNavigation() throws Exception {
        assertEquals(presenter.switchNavigation(0), false);
    }

}