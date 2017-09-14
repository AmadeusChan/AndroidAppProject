package com.java.a31.androidappproject.news.NewsDetail;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.NewsDetail;
import com.java.a31.androidappproject.models.NewsManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class NewsDetailPresenterTest {

    private Context context;

    private NewsDetailPresenter presenter;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        NewsManager.getInstance(context);

        presenter = new NewsDetailPresenter(new NewsDetailContract.View() {

            private static final String TAG = "View";

            private NewsDetailContract.Presenter mPresenter;

            @Override
            public void onSuccess(INewsDetail newsDetail) {
                Log.d(TAG, "onSuccess");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void setLike() {
                Log.d(TAG, "setLike");
            }

            @Override
            public void setUnLike() {
                Log.d(TAG, "setUnLike");
            }

            @Override
            public void setBan() {
                Log.d(TAG, "setBan");
            }

            @Override
            public void setUnBan() {
                Log.d(TAG, "setUnBan");
            }

            @Override
            public void share(INewsDetail newsDetail) {
                Log.d(TAG, "share");
            }

            @Override
            public void setPresenter(NewsDetailContract.Presenter presenter) {
                mPresenter = presenter;
            }
        });
    }

    @Test
    public void start() throws Exception {
        presenter.start();
    }

    @Test
    public void getResult() throws Exception {
        presenter.getResult(new NewsDetail());
    }

    @Test
    public void loadNewsDetail() throws Exception {
        presenter.loadNewsDetail("201608090432c815a85453c34d8ca43a591258701e9b", false);
    }

    @Test
    public void onLikeButtonClick() throws Exception {
        presenter.onLikeButtonClick();
    }

    @Test
    public void onReadButtonClick() throws Exception {
        presenter.onReadButtonClick();
    }

    @Test
    public void onShareButtonCLick() throws Exception {
        presenter.onShareButtonCLick();
    }

    @Test
    public void stopReading() throws Exception {
        presenter.stopReading();
    }

}