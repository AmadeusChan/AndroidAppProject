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
public class NewsListTest {
    @Test
    public void getMore() throws Exception {
        Log.d("Hello", "Test getMore()");
        Context context=InstrumentationRegistry.getContext();
        INewsList newsList=new NewsList(context, INews.NORMAL_MODE);
        newsList.getMore(20, 1, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("getResult:", "result!");
                if (result==null) {
                    System.out.println("BOMB!");
                } else
                for (int i=0; i<result.size(); ++i) {
                    Log.d("title:", result.get(i).getTitle());
                    Log.d("images", result.get(i).getImages().toString());
                }
            }
        });
        while (true);
    }

}