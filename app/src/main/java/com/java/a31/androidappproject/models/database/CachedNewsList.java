package com.java.a31.androidappproject.models.database;

import android.util.Log;

import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/7/17.
 */

public class CachedNewsList implements INewsList {
    private List<INewsIntroduction> newsList;
    private int count;
    private INewsFilter filter, filter0;

    CachedNewsList(List<INewsIntroduction> list) {
        this.newsList=list;
        count=0;
        filter=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        };
        filter0=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        };
        //Log.d("cached list", "LIST SIZE "+list.size());
    }

    @Override
    public void reset() {
        count=0;
    }

    @Override
    public void getMore(int size, int pageNo, final int category, INewsListener<List<INewsIntroduction>> listener) {
        List<INewsIntroduction> list=new ArrayList<>();
        for (int i=(pageNo-1)*size; i<pageNo*size; ++i) {
            if (i>=newsList.size()) break;
            if (!MyDBHelper.categoryList[category+1].equals(newsList.get(i).getClassTag())) continue;
            if (!filter.accept(newsList.get(i))) continue;
            list.add(newsList.get(i));
            count=i;
        }
        listener.getResult(list);
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
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        ArrayList<INewsIntroduction> list=new ArrayList<>();
        int i=size;
        while (true) {
            if (count>=newsList.size() || i==0) break;
            INewsIntroduction newsIntroduction=newsList.get(count);
            if (filter.accept(newsIntroduction) && filter0.accept(newsIntroduction)) {
                i--;
                list.add(newsIntroduction);
            }
            ++count;
        }
        listener.getResult(list);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter = filter;
    }

}
