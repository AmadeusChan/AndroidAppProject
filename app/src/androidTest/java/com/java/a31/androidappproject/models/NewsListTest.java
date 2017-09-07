package com.java.a31.androidappproject.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/5/17.
 */
public class NewsListTest {
    Context context;
    NewsManager newsManager;
    NewsList newsList;

    @Before
    public void setUp() {
        context=InstrumentationRegistry.getTargetContext();
        newsManager=NewsManager.getInstance(context);
    }

    @Test
    public void getMore() throws Exception {
        Log.d("getMore", "Test getMore()");
        newsList=new NewsList(context, INews.NORMAL_MODE, NewsList.BASIC_URL_FOR_RAW_QUERY);
        newsList.setFilter(new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return false;
            }
        });
        newsList.getMore(1000, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("getMore", "result!");
                if (result==null) {
                    System.out.println("BOMB!");
                } else
                for (int i=0; i<result.size(); ++i) {
                    Log.d("getMore", "title:"+result.get(i).getTitle());
                    Log.d("getMore", "images:"+result.get(i).getImages().toString());
                }
                Log.d("getMore", "total:"+result.size());

                /*
                newsList.reset();
                newsList.getMore(1, new INewsListener<List<INewsIntroduction>>() {
                    @Override
                    public void getResult(List<INewsIntroduction> result) {
                        for (int i=0; i<result.size(); ++i) {
                            Log.d("getMore", "title:"+result.get(i).getTitle());
                            Log.d("getMore", "images:"+result.get(i).getImages().toString());
                        }
                    }
                });
                */
            }
        });
        Thread.sleep(100000);
    }

}