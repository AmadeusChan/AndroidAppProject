package com.java.a31.androidappproject.models.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.android.volley.Cache;
import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/12/17.
 */
public class ImprovedCachedNewsListTest {
    Context context;
    NewsManager newsManager;
    INewsList newsList;

    @Before
    public void setUp() throws Exception {
        context= InstrumentationRegistry.getTargetContext();
        newsManager=NewsManager.getInstance(context);
        newsList=newsManager.getCachedNewsList(INews.NORMAL_MODE);
    }

    @Test
    public void setFilter() throws Exception {

    }

    @Test
    public void reset() throws Exception {

    }

    @Test
    public void getMore() throws Exception {
        newsList.getMore(50, 1, 10, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                for (INewsIntroduction i: result) {
                    Log.d("improvedCached", i.getTitle());
                }
            }
        });
        Thread.sleep(2000);
    }

    @Test
    public void getMore1() throws Exception {

    }

    @Test
    public void getMore2() throws Exception {

    }

}