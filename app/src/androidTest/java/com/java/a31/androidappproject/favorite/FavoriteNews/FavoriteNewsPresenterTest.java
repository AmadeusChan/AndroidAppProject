package com.java.a31.androidappproject.favorite.FavoriteNews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.java.a31.androidappproject.channel.ChannelPresenter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.news.NewsList.NewsListContract;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class FavoriteNewsPresenterTest {

    private Context context;
    private ChannelPresenter channelPresenter;
    FavoriteNewsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        NewsManager.getInstance(context);

        presenter = new FavoriteNewsPresenter(new NewsListContract.View() {
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
    public void loadNewsList() throws Exception {
        presenter.loadNewsList(20, 1, -1);
    }

}