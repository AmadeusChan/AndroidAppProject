package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.NewsIntroduction;

import java.util.ArrayList;

/**
 * Created by amadeus on 9/7/17.
 */

class CachedNewsListManager {
    final static String TABLE="cached_news_list_table";
    final static String ID_COLUMN="news_id";
    final static String INTRODUCTION_COLUMN="news_introduction";

    static boolean isInCachedNewsList(String ID, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+TABLE+" where "+ID_COLUMN+"=\""+ID+"\"", null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    static boolean add2CachedNewsList(INewsIntroduction newsIntroduction, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues=new ContentValues();
        String introduction=new Gson().toJson(newsIntroduction);
        contentValues.put(ID_COLUMN, newsIntroduction.getID());
        contentValues.put(INTRODUCTION_COLUMN, introduction);
        sqLiteDatabase.insert(TABLE, null, contentValues);
        return true;
    }

    static INewsList getCachedNewsList(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+TABLE, null);
        ArrayList<INewsIntroduction> list=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String ID=cursor.getString(cursor.getColumnIndex(ID_COLUMN));
                String content=cursor.getString(cursor.getColumnIndex(INTRODUCTION_COLUMN));
                INewsIntroduction newsIntroduction=new Gson().fromJson(content, NewsIntroduction.class);
                list.add(newsIntroduction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return new CachedNewsList(list);
    }
}
