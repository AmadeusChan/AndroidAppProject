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
public class NewsManagerTest {

    @Before
    public void setUp() {
        NewsManager.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void getLatestNews() throws Exception {
        Context context= InstrumentationRegistry.getContext();
        INewsList newsList=NewsManager.getInstance(context).getLatestNews(INews.NORMAL_MODE);
        newsList.getMore(50, 1, 12, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                Log.d("getResult:", "result!");
                Log.d("getResult:", ""+result.size());
                if (result==null) {
                    System.out.println("BOMB!");
                } else
                    for (int i=0; i<result.size(); ++i) {
                        Log.d("title:", result.get(i).getTitle());
                    }
            }
        });
        Thread.sleep(1000);

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
        Context context=InstrumentationRegistry.getContext();
        String text="你好，世界";
        NewsManager.getInstance(context).speakText(text);
        text="据俄罗斯新闻频道8月7日消息，德国《世界报》评论俄罗斯在里约奥运会获得其首枚金牌时称，俄罗斯柔道选手夺得金牌与俄罗斯总统弗拉基米尔·普京密切相关。 　　文章称，俄首枚金牌得主、柔道运动员穆德拉诺夫不止一次与俄罗斯总统弗拉基米尔·普京一起训练。柔道正是普京喜欢的体育运动，因此此次夺冠对俄罗斯来说非常重要。报道还提到，普京作为国际柔道联合会荣誉主席，已获柔道8段。";
        NewsManager.getInstance(context).speakText(text);
        while (true);
    }

    @Test
    public void getCategoryList() throws Exception {
        Context context=InstrumentationRegistry.getContext();
        //NewsManager.getInstance(context);
        List<String> list=NewsManager.getInstance(context).getCategoryList();
        for (String str: list) {
            Log.d("category", str);
        }
    }

    @Test
    public void addCategory() throws Exception {

    }

    @Test
    public void deleteCategory() throws Exception {

    }

}