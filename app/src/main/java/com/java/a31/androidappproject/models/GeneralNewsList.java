package com.java.a31.androidappproject.models;

import android.content.Context;
import android.util.Log;

import com.java.a31.androidappproject.models.database.CachedNewsList;

import java.util.HashSet;
import java.util.List;

/**
 * Created by amadeus on 9/8/17.
 */

public class GeneralNewsList implements INewsList {
    private HashSet<String> showedNews;
    private NewsList onlineList;
    private INewsList offlineList;
    private int mode;
    private Context context;
    //private INewsFilter filter0;
    private NewsManager newsManager;

    GeneralNewsList(int mode, Context context) {
        this.mode=mode;
        this.context=context;
        showedNews=new HashSet<>();
        /*
        filter0=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return !showedNews.contains(newsIntroduction.getID());
            }
        };
        */
        try {
            this.newsManager=NewsManager.getInstance();
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
        }
        reset();
    }

    /*
    public void addFilter0(final INewsFilter filter) {
        INewsFilter mergedFilter=new INewsFilter() {
            INewsFilter filterX=filter0;
            INewsFilter filterY=filter;
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return filterX.accept(newsIntroduction) && filterY.accept(newsIntroduction);
            }
        };
        filter0=mergedFilter;
        reset();
    }
    */

    @Override
    public void reset() {
        onlineList=new NewsList(context, mode, NewsList.BASIC_URL_FOR_RAW_QUERY);
        try {
            Log.d("keyword", "x");
            offlineList=NewsManager.getInstance().getCachedNewsList(mode);
            Log.d("keyword", "y");
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
        }
        showedNews.clear();
    }

    @Override
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        if (newsManager.isConnectToInternet()) {
            onlineList.getMore(size, listener);
        } else {
            offlineList.getMore(size, listener);
        }
    }

    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        if (newsManager.isConnectToInternet()) {
            onlineList.getMore(size, pageNo, category, listener);
        } else {
            offlineList.getMore(size, pageNo, category, listener);
        }
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        getMore(size, pageNo ,-1, listener);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        onlineList.setFilter(filter);
        offlineList.setFilter(filter);
    }
}
