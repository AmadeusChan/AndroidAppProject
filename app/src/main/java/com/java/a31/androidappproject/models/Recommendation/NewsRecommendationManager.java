package com.java.a31.androidappproject.models.Recommendation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amadeus on 9/12/17.
 */

class Pair implements Comparable<Pair> {
    private String str;
    private int cnt;

    Pair(String str, int cnt) {
        this.str=str;
        this.cnt=cnt;
    }

    public String getStr() {
        return str;
    }

    @Override
    public int compareTo(@NonNull Pair pair) {
        if (this.cnt<pair.cnt) return -1;
        if (this.cnt>pair.cnt) return 1;
        return 0;
    }
}

public class NewsRecommendationManager {

    public static String getRecommendedKeywords(int mode) throws NewsManagerNotInitializedException {
        NewsManager newsManager=NewsManager.getInstance();
        List<String> keywords=newsManager.getReadKeywords();
        List<Pair> pairs=new ArrayList<>();
        int cnt=0;
        Collections.shuffle(keywords);
        Collections.sort(keywords);
        for (int i=0; i<keywords.size(); ++i) {
            ++cnt;
            if (i==keywords.size()-1 || !keywords.get(i).equals(keywords.get(i+1))) {
                pairs.add(new Pair(keywords.get(i), cnt));
                cnt=0;
            }
        }
        Log.d("keyword", keywords.toString());
        Collections.shuffle(pairs);
        Collections.sort(pairs);
        String query="";
        for (int i=0; i<8 && i<pairs.size(); ++i) {
            query+=pairs.get(i).getStr();
        }
        Log.d("keyword", "The final query is: " + query);
        return query;
        /*
        if (query.equals("")) return newsManager.getLatestNews(mode);
        return new RecommendedNewsList(query, mode);
        */
        //return query;
        //return newsManager.searchNews(query, mode);
    }

    public static INewsList getRecommendedNewsList(int mode, Context context) throws NewsManagerNotInitializedException {
        String query=getRecommendedKeywords(mode);
        //if (query.equals("")) return NewsManager.getInstance().getLatestNews(mode);
        return new RecommendedNewsList(query, mode, context);
    }

}
