package com.java.a31.androidappproject.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/5/17.
 */
public class NewsManagerTest {
    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void getLatestNews() throws Exception {
        Context context= InstrumentationRegistry.getContext();
        INewsList newsList=NewsManager.getInstance(context).getLatestNews(20);
        newsList.getMore(20, 1, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("getResult:", "result!");
                if (result==null) {
                    System.out.println("BOMB!");
                } else
                    for (int i=0; i<result.size(); ++i) {
                        Log.d("title:", result.get(i).getTitle());
                    }
            }
        });
        while (true);

    }

    @Test
    public void getNewsDetails() throws Exception {

    }

    @Test
    public void setAsFavorite() throws Exception {

    }

    @Test
    public void setAsNotFavorite() throws Exception {

    }

    @Test
    public void getFavoriteNews() throws Exception {

    }

    @Test
    public void speakText() throws Exception {

    }

    @Test
    public void getCategoryList() throws Exception {

    }

    @Test
    public void addCategory() throws Exception {

    }

    @Test
    public void deleteCategory() throws Exception {

    }

}