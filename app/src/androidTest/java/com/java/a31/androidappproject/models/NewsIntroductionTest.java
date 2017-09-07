package com.java.a31.androidappproject.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/7/17.
 */
public class NewsIntroductionTest {
    NewsManager newsManager;
    Context context;

    @Before
    public void setUp() throws Exception {
        context= InstrumentationRegistry.getTargetContext();
        newsManager=NewsManager.getInstance(context);
    }

    @Test
    public void isRead() throws Exception {
        INewsList newsList=newsManager.getLatestNews(INews.NORMAL_MODE);
        newsList.getMore(10, 1, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                INewsIntroduction newsIntroduction=result.get(2);
                Log.d("isRead", newsIntroduction.getTitle());
                Log.d("isRead", ""+newsIntroduction.isRead());
            }
        });


    }

    @Test
    public void isFavorite() throws Exception {
        String ID="201609130413037a073b2cdb4768aff90eff46f2665b";
        newsManager.getNewsDetails(ID, INews.NORMAL_MODE, new INewsListener<INewsDetail>(){
            @Override
            public void getResult(INewsDetail result) {
                Log.d("isFavorite", result.getTitle());
                Log.d("isFavorite", ""+result.isFavorite());
            }
        });
        Thread.sleep(1000);
    }

}