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
        Log.d("go: ", "getNewsDetails()");
        Context context=InstrumentationRegistry.getContext();
        String ID="201609130413080e91293fb5402b80437a65970fcb7d";
        int mode=INews.NORMAL_MODE;
        NewsManager.getInstance(context).getNewsDetails(ID, mode, new INewsListener<INewsDetail>() {
            @Override
            public void getResult(INewsDetail result) {
                Log.d("go: ","result got!");
                if (result!=null) {
                    Log.d("persons: ", result.getPersons().toString());
                    Log.d("locations: ", result.getLocations().toString());
                    Log.d("keyWords: ", result.getKeyWords().toString());
                    Log.d("images: ", result.getImages().toString());
                    Log.d("Content: ", result.getContent());
                }
            }
        });
        while (true);
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