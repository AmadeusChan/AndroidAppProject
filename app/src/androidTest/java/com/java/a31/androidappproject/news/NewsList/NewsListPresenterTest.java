package com.java.a31.androidappproject.news.NewsList;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.NewsManager;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class NewsListPresenterTest {

    private Context context;

    private NewsListPresenter presenter;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        NewsManager.getInstance(context);

        presenter = new NewsListPresenter(new NewsListContract.View() {

            private static final String TAG = "View";

            private NewsListContract.Presenter mPresenter;

            @Override
            public void onSuccess(List<INewsIntroduction> newsList) {
                Log.d(TAG, "onSuccess");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void setPresenter(NewsListContract.Presenter presenter) {
                mPresenter = presenter;
            }
        }, false);
    }

    @Test
    public void start() throws Exception {
        presenter.start();
    }

    @Test
    public void loadNewsList() throws Exception {
        presenter.loadNewsList(20, 1, -1);
    }

    @Test
    public void setFilter() throws Exception {
        presenter.setFilter(new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        });
    }

    @Test
    public void getResult() throws Exception {
        presenter.getResult(new ArrayList<INewsIntroduction>());
    }

}