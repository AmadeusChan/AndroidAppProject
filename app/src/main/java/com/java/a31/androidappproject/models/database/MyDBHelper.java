package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/6/17.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="NewsDatabase.db";
    public static final int VERSION=6;

    // about category list
    public static final String CATEGORY_LIST_TABLE_NAME="category_list_table";
    public static final String CATEGORY_COLUMN_NAME="category_name";
    public static final String []categoryList={"科技", "教育", "军事", "国内", "社会", "文化", "汽车", "国际", "体育", "财经", "健康", "娱乐"};

    // about news list set as favorite
    public static final String FAVORITE_LIST_TABLE_NAME="favorite_list_table";
    public static final String FAVORITE_COLUMN_NEWS_ID="news_id";
    public static final String FAVORITE_COLUMN_NEWS_CONTENT="news_content";

    // about read news
    public static final String READ_NEWS_TABLE="read_news_table";
    public static final String READ_NEWS_ID_COLUMN="news_id";

    private Context context;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
        Log.d("locate", "constructor");
    }

    private void onInit(SQLiteDatabase sqLiteDatabase) {
        // initialize the category table
        String []list=categoryList;
        for (String category: list) {
            ContentValues contentValues=new ContentValues();
            contentValues.put(CATEGORY_COLUMN_NAME, category);
            sqLiteDatabase.insert(CATEGORY_LIST_TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("go", "On create!");
        sqLiteDatabase.execSQL(
                "create table if not exists "+CATEGORY_LIST_TABLE_NAME
                        +" (" + CATEGORY_COLUMN_NAME+ " text)"
        );
        onInit(sqLiteDatabase);

        sqLiteDatabase.execSQL(
                "create table if not exists "+FAVORITE_LIST_TABLE_NAME
                        +" ("+FAVORITE_COLUMN_NEWS_ID+" text, "+FAVORITE_COLUMN_NEWS_CONTENT+" text)"
        );

        sqLiteDatabase.execSQL(
                "create table if not exists "+READ_NEWS_TABLE+
                        " ("+READ_NEWS_ID_COLUMN+" text)"
        );

        sqLiteDatabase.execSQL(
                "create table if not exists "+CachedNewsListManager.TABLE+" ("+CachedNewsListManager.ID_COLUMN+" text, "+
                        CachedNewsListManager.INTRODUCTION_COLUMN+" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+CATEGORY_LIST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+FAVORITE_LIST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+READ_NEWS_TABLE);
        sqLiteDatabase.execSQL("drop table if exists "+CachedNewsListManager.TABLE);
        onCreate(sqLiteDatabase);
    }

    // these following methods are about news category
    private boolean isValidCategory(String categoryName) {
        for (int i=0; i<categoryList.length; ++i) {
            if (categoryList[i].equals(categoryName)) {
                return  true;
            }
        }
        return false;
    }

    public boolean isCategoryInTable(String categoryName) {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor=sqLiteDatabase.rawQuery("select * from "+CATEGORY_LIST_TABLE_NAME+" where "+CATEGORY_COLUMN_NAME+"=\""+categoryName+"\"", null);
        } catch (Exception e) {
            Log.d("locate", "cursor exception!");
            return false;
        }
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean insertCategory(String categoryName) {
        Log.d("locate", "insertCategory");
        if (!isValidCategory(categoryName)) return false;
        if (isCategoryInTable(categoryName)) return false;

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, categoryName);
        sqLiteDatabase.insert(CATEGORY_LIST_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean deleteCategory(String categoryName) {
        if (!isValidCategory(categoryName)) return false;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(CATEGORY_LIST_TABLE_NAME, CATEGORY_COLUMN_NAME+"=?", new String[]{categoryName})>0;
    }

    public List<String> getCategoryList() {
        Log.d("locate", "getCategoryList()");
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+CATEGORY_LIST_TABLE_NAME, null);
        ArrayList<String> list=new ArrayList<String>();
        String str;
        Log.d("locate", "getCursor");
        if ((cursor.moveToFirst())) {
            do {
                str=cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME));
                list.add(str);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    // these following methods are concerning the news set as favorite
    public boolean isInFavoriteList(String ID) {
        return FavoriteNewsManager.isInFavoriteList(ID, this.getReadableDatabase());
    }

    public boolean insertFavoriteNews(INewsDetail newsDetail) {
        if (FavoriteNewsManager.isInFavoriteList(newsDetail.getID(), this.getReadableDatabase())) return false;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return FavoriteNewsManager.addFavoriteNews(newsDetail, sqLiteDatabase);
    }

    public boolean deleteFavoriteNews(String ID) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return FavoriteNewsManager.deleteFavoriteNews(ID, sqLiteDatabase);
    }

    public INewsList getFavoriteNewsList() {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return FavoriteNewsManager.getFavoriteNewsList(sqLiteDatabase);
    }

    public INewsDetail getFavoriteNewsDetails(String ID) {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return FavoriteNewsManager.getFavoriteNewsDetails(ID, sqLiteDatabase);
    }

    // these methods are about news that have been read
    public boolean insertReadNews(String ID) {
        if (ReadNewsManager.isReadNews(ID, this.getReadableDatabase())) return false;
        return ReadNewsManager.addReadNews(ID, this.getWritableDatabase());
    }

    public boolean isReadNews(String ID) {
        return ReadNewsManager.isReadNews(ID, this.getReadableDatabase());
    }

    // these following methods are about cached news list
    public boolean isInCachedNewsList(String ID) {
        return CachedNewsListManager.isInCachedNewsList(ID, this.getReadableDatabase());
    }

    public boolean add2CachedNewsList(INewsIntroduction newsIntroduction) {
        //Log.d("cached list", "add2CachedNewsList "+newsIntroduction.getID());
        if (isInCachedNewsList(newsIntroduction.getID())) return false;
        return CachedNewsListManager.add2CachedNewsList(newsIntroduction, this.getWritableDatabase());
    }

    public INewsList getCachedNewsList() {
        return CachedNewsListManager.getCachedNewsList(this.getReadableDatabase());
    }

    public INewsList getCachedNewsList(int mode) {
        return CachedNewsListManager.getCachedNewsList(mode, this.getReadableDatabase());
    }

}
