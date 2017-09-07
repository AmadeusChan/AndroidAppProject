package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsDetail;
import com.java.a31.androidappproject.models.NewsManager;

import java.util.ArrayList;

/**
 * Created by amadeus on 9/6/17.
 */

class FavoriteNewsManager {

    static boolean isInFavoriteList(String ID, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+MyDBHelper.FAVORITE_LIST_TABLE_NAME+
                " where "+MyDBHelper.FAVORITE_COLUMN_NEWS_ID+"=\""+ID+"\"", null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    static boolean addFavoriteNews(INewsDetail newsDetail, SQLiteDatabase sqLiteDatabase) {
        String content= new Gson().toJson(newsDetail);
        String ID=newsDetail.getID();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MyDBHelper.FAVORITE_COLUMN_NEWS_ID, ID);
        contentValues.put(MyDBHelper.FAVORITE_COLUMN_NEWS_CONTENT, content);
        sqLiteDatabase.insert(MyDBHelper.FAVORITE_LIST_TABLE_NAME, null, contentValues);
        return true;
    }

    static boolean deleteFavoriteNews(String ID, SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(MyDBHelper.FAVORITE_LIST_TABLE_NAME, MyDBHelper.FAVORITE_COLUMN_NEWS_ID+"=\""+ID+"\"", null)>0;
    }

    static INewsList getFavoriteNewsList(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+MyDBHelper.FAVORITE_LIST_TABLE_NAME, null);
        ArrayList<INewsDetail> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String ID=cursor.getString(cursor.getColumnIndex(MyDBHelper.FAVORITE_COLUMN_NEWS_ID));
                String content=cursor.getString(cursor.getColumnIndex(MyDBHelper.FAVORITE_COLUMN_NEWS_CONTENT));
                NewsDetail newsDetail=new Gson().fromJson(content, NewsDetail.class);
                String introduction=newsDetail.getContent();
                if (introduction.length()>38) {
                    introduction=introduction.substring(0, 38)+"...";
                }
                newsDetail.setIntroduction(introduction);
                list.add(newsDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return new FavoriteNewsList(list);
    }

    static INewsDetail getFavoriteNewsDetails(String ID, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+MyDBHelper.FAVORITE_LIST_TABLE_NAME+
                " where "+MyDBHelper.FAVORITE_COLUMN_NEWS_ID+"=\""+ID+"\"", null);
        INewsDetail newsDetail=null;
        if (cursor.moveToFirst()) {
            String content=cursor.getString(cursor.getColumnIndex(MyDBHelper.FAVORITE_COLUMN_NEWS_CONTENT));
            newsDetail=new Gson().fromJson(content, NewsDetail.class);
        }
        cursor.close();
        return newsDetail;
    }
}
