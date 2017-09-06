package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/6/17.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="NewsDatabase.db";

    // about category list
    public static final String CATEGORY_LIST_TABLE_NAME="category_list_table";
    public static final String CATEGORY_COLUMN_NAME="category_name";
    public static final String []categoryList={"科技", "教育", "军事", "国内", "社会", "文化", "汽车", "国际", "体育", "财经", "健康", "娱乐"};

    // about news list set as favorite
    public static final String FAVORITE_LIST_TABLE_NAME="favorite_list_table";
    public static final String FAVORITE_COLUMN_NEWS_ID="news_id";
    public static final String FAVORITE_COLUMN_NEWS_CONTENT="news_content";

    private Context context;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+CATEGORY_LIST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+FAVORITE_LIST_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private boolean isValidCategory(String categoryName) {
        for (int i=0; i<categoryList.length; ++i) {
            if (categoryList[i].equals(categoryName)) {
                return  true;
            }
        }
        return false;
    }

    private boolean isCategoryInTable(String categoryName) {
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

}
