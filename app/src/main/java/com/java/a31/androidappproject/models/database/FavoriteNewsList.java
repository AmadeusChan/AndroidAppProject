package com.java.a31.androidappproject.models.database;

import android.util.Log;

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
    private List<INewsDetail> newsList;
    private int count;
    private INewsFilter filter, filter0;

    FavoriteNewsList(List<INewsDetail> newsList) {
        this.newsList=newsList;
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
    }

    @Override
    public void reset() {
        count=0;
    }

    /*
    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        // this method has bug
        Log.d("locate", "size="+size);
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
        Log.d("locate", "size="+size);
        for (int i=(pageNo-1)*size; i<pageNo*size; ++i) {
            if (i>=newsList.size()) break;
            if (!filter.accept(newsList.get(i))) continue;
            list.add(newsList.get(i));
            count=i;
        }
        listener.getResult(list);
    }
    */

    @Override
    public void getMore(int size, int pageNo, final int category, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("locate", "size="+size+" pageNo="+pageNo+"category="+category);
        List<INewsIntroduction> list=new ArrayList<>();
        int cnt=0;
        int len=newsList.size();
        for (int i=0; i<len; ++i) {
            INewsIntroduction newsIntroduction=newsList.get(i);
            if (category!=-1 && !MyDBHelper.categoryList[category+1].equals(newsIntroduction.getClassTag())) continue;
            if (filter.accept(newsIntroduction) && filter0.accept(newsIntroduction)) {
                if (cnt>=(pageNo-1)*size && cnt<pageNo*size) list.add(newsIntroduction);
                if ((++cnt)>=pageNo*size) break;
            }
        }
        /*
        for (int i=(pageNo-1)*size; i<pageNo*size; ++i) {
            if (i>=newsList.size()) break;
            if (!MyDBHelper.categoryList[category+1].equals(newsList.get(i).getClassTag()) && category!=-1) continue;
            if (!filter.accept(newsList.get(i))) continue;
            list.add(newsList.get(i));
            count=i;
        }
        */
        listener.getResult(list);
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        getMore(size, pageNo, -1, listener);
        /*
        List<INewsIntroduction> list=new ArrayList<>();
        for (int i=(pageNo-1)*size; i<pageNo*size; ++i) {
            if (i>=newsList.size()) break;
            list.add(newsList.get(i));
            count=i;
        }
        listener.getResult(list);
        */
    }

    @Override
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("locate", "size="+size);
        ArrayList<INewsIntroduction> list=new ArrayList<>();
        while (true) {
            if (count>=newsList.size() || size==0) break;
            INewsIntroduction newsIntroduction=newsList.get(count);
            if (filter.accept(newsIntroduction) && filter0.accept(newsIntroduction)) {
                size--;
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
