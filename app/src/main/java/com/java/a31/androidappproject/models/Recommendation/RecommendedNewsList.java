package com.java.a31.androidappproject.models.Recommendation;

import android.content.Context;
import android.util.Log;

import com.java.a31.androidappproject.models.GeneralNewsList;
import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsList;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

import java.util.HashSet;
import java.util.List;

/**
 * Created by amadeus on 9/12/17.
 */

public class RecommendedNewsList implements INewsList {

    private NewsList keywordNewsList;
    private GeneralNewsList latestNewsList;
    private INewsFilter filter;
    private String keywords;
    private int mode;
    private NewsManager newsManager;
    Context context;
    //private INewsFilter filter0;

    RecommendedNewsList(String keywords, int mode, Context context) {
        this.keywords=keywords;
        this.mode=mode;
        this.context=context;
        try {
            newsManager=NewsManager.getInstance();
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
        }
        filter=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        };
        /*
        filter0=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return !showedNews.contains(newsIntroduction.getID());
            }
        };
        */
        reset();
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter = filter;
    }

    @Override
    public void reset() {
        Log.d("keyword", "reset");
        //showedNews.clear();
        try {
            keywords=NewsRecommendationManager.getRecommendedKeywords(mode);
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
        }
        if (keywords.equals("")) {
            keywordNewsList=new NewsList(context, mode, NewsList.BASIC_URL_FOR_RAW_QUERY);
        } else {
            keywordNewsList=(NewsList) newsManager.searchNews(keywords, mode);
        }
        latestNewsList=(GeneralNewsList) newsManager.getLatestNews(mode);
        /*
        keywordNewsList.setFilter0(filter0);
        latestNewsList.addFilter0(filter0);
        */
    }

    @Override
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("keyword", "locate getMore");
        if (newsManager.isConnectToInternet()) {
            keywordNewsList.getMore(size, listener);
        } else {
            latestNewsList.getMore(size, listener);
        }
    }

    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("keyword", "getMore size="+size+" pageNo="+pageNo);
        if (pageNo==1) reset();
        if (newsManager.isConnectToInternet()) {
            keywordNewsList.getMore(size, pageNo, category, listener);
        } else {
            latestNewsList.getMore(size, listener);
        }
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        getMore(size, pageNo, -1, listener);
    }

}
