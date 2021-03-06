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
    private List<INewsIntroduction> buffer;
    Context context;
    //private INewsFilter filter0;

    RecommendedNewsList(String keywords, int mode, Context context) {
        Log.d("location", "RecommendedNewsList begin");
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
        Log.d("location", "RecommendedNewsList end");
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter = filter;
    }

    @Override
    public void reset() {
        Log.d("location", "reset begin");
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
        Log.d("keyword", "a");
        latestNewsList=(GeneralNewsList) newsManager.getLatestNews(mode);
        Log.d("keyword", "b");
        Log.d("keyword", "reset finished");
        /*
        keywordNewsList.setFilter0(filter0);
        latestNewsList.addFilter0(filter0);
        */
        Log.d("location", "reset end");
    }

    @Override
    public void getMore(final int size, final INewsListener<List<INewsIntroduction>> listener) {
        Log.d("location", "getMore0 begin");
        Log.d("test", "locate getMore");
        if (newsManager.isConnectToInternet()) {
            keywordNewsList.getMore(size, new INewsListener<List<INewsIntroduction>>() {
                @Override
                public void getResult(List<INewsIntroduction> result) {
                    if (result.size()==size) {
                        listener.getResult(result);
                    } else {
                        int re=size-result.size();
                        buffer=result;
                        latestNewsList.getMore(re, new INewsListener<List<INewsIntroduction>>() {
                            @Override
                            public void getResult(List<INewsIntroduction> result) {
                                buffer.addAll(result);
                                listener.getResult(buffer);
                            }
                        });
                    }
                }
            });
            //keywordNewsList.getMore(size, listener);
        } else {
            latestNewsList.getMore(size, listener);
        }
        Log.d("test", "locate getMore finished");
        Log.d("location", "getMore0 end");
    }

    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("location", "getMore1 begin");
        Log.d("keyword", "getMore size="+size+" pageNo="+pageNo);
        if (pageNo==1) reset();
        if (newsManager.isConnectToInternet()) {
            keywordNewsList.getMore(size, pageNo, category, listener);
        } else {
            latestNewsList.getMore(size, pageNo, category, listener);
        }
        Log.d("location", "getMore1 end");
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("location", "getMore2 begin");
        getMore(size, pageNo, -1, listener);
        Log.d("location", "getMore2 end");
    }

}
