package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.ThreadFactoryBuilder;
import android.util.Log;

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
 * Created by amadeus on 9/7/17.
 */
public class CachedNewsListManagerTest {
    Context context;
    NewsManager newsManager;

    @Before
    public void setUp() throws Exception {
        context= InstrumentationRegistry.getTargetContext();
        newsManager=NewsManager.getInstance(context);
    }

    @Test
    public void isInCachedNewsList() throws Exception {
        INewsList newsList=newsManager.getLatestNews(INews.NORMAL_MODE);
        newsList.getMore(1200, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("cached list", "got result!");
                INewsList newsList1=newsManager.getCachedNewsList();
                newsList1.getMore(1000, new INewsListener<List<INewsIntroduction>>() {
                    @Override
                    public void getResult(List<INewsIntroduction> result) {
                        Log.d("cached list", "size: "+result.size());
                        for (INewsIntroduction i: result) {
                            Log.d("cached list", i.getTitle());
                        }
                    }
                });
            }
        });
        Thread.sleep(1000);
        while (true);

    }

    @Test
    public void add2CachedNewsList() throws Exception {

    }

    @Test
    public void getCachedNewsList() throws Exception {
        INewsList newsList1=newsManager.getCachedNewsList();
        newsList1.getMore(10000, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("cached list", "size: "+result.size());
                /*
                for (INewsIntroduction i: result) {
                    Log.d("cached list", i.getTitle()+": "+i.getIntroduction());
                }
                Log.d("cached list", "THE SIZE IS "+result.size());
                */
            }
        });
        Thread.sleep(1000);
    }

}