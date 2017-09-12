package com.java.a31.androidappproject.models.database;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsIntroduction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/12/17.
 */

public class ImprovedCachedNewsList implements INewsList {

    private Cursor cursor;
    private INewsFilter filter;
    private int count;
    private int mode;

    ImprovedCachedNewsList(int mode, Cursor cursor) {
        this.cursor=cursor;
        this.mode=mode;
        filter=new INewsFilter() {
            @Override
            public boolean accept(INewsIntroduction newsIntroduction) {
                return true;
            }
        };
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter = filter;
    }

    @Override
    public void reset() {
        cursor.moveToFirst();
        count=0;
    }

    @Override
    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        ArrayList<INewsIntroduction> list=new ArrayList<>();
        int i=size;
        if (!cursor.moveToPosition(count)) {
            listener.getResult(list);
            return ;
        }
        Log.d("locate", ""+i);
        while (true) {
            if (i==0) break;
            String content=cursor.getString(cursor.getColumnIndex(CachedNewsListManager.INTRODUCTION_COLUMN));
            NewsIntroduction newsIntroduction=new Gson().fromJson(content, NewsIntroduction.class);
            if (mode==INews.TEXT_ONLY_MODE) newsIntroduction.setImages(new ArrayList<String>());
            if (filter.accept(newsIntroduction) ) {
                i--;
                list.add(newsIntroduction);
            }
            ++count;
            Log.d("keyword", ""+i);
            if (!cursor.moveToNext()) break;
        }
        listener.getResult(list);
    }

    @Override
    public void getMore(int size, int pageNo, int category, INewsListener<List<INewsIntroduction>> listener) {
        List<INewsIntroduction> list=new ArrayList<>();
        int cnt=0;
        if (!cursor.moveToFirst()) {
            listener.getResult(list);
            return ;
        }
        while (true) {
            //Log.d("keyword", "to getMore");
            String content=cursor.getString(cursor.getColumnIndex(CachedNewsListManager.INTRODUCTION_COLUMN));
            NewsIntroduction newsIntroduction=new Gson().fromJson(content, NewsIntroduction.class);
            if (mode==INews.TEXT_ONLY_MODE) newsIntroduction.setImages(new ArrayList<String>());

            if (category!=-1 && !MyDBHelper.categoryList[category-1].equals(newsIntroduction.getClassTag())) {
                if (!cursor.moveToNext()) break;
                continue;
            }
            if (filter.accept(newsIntroduction)) {
                if (cnt>=(pageNo-1)*size && cnt<pageNo*size) list.add(newsIntroduction);
                if ((++cnt)>=pageNo*size) break;
            }
            Log.d("keyword", ""+cnt);
            if (!cursor.moveToNext()) break;
        }
        listener.getResult(list);

        count=0;
        cursor.moveToFirst();
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        getMore(size, pageNo, -1, listener);
    }

    @Override
    protected void finalize() throws Throwable {
        cursor.close();
        super.finalize();
    }
}
