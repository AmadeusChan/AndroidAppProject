package com.java.a31.androidappproject.models.database;

import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/7/17.
 */

public class FavoriteNewsList implements INewsList {
    List<INewsDetail> newsList;
    int count;
    INewsFilter filter;

    FavoriteNewsList(List<INewsDetail> newsList) {
        this.newsList=newsList;
        count=0;
        filter=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        };
    }

    @Override
    public void reset() {
        count=0;
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        List<INewsIntroduction> list=new ArrayList<>();
        for (int i=(pageNo-1)*size; i<pageNo*size; ++i) {
            if (i>=newsList.size()) break;
            list.add(newsList.get(i));
            count=i;
        }
        listener.getResult(list);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter = filter;
    }
}
