package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by amadeus on 9/7/17.
 */

class ReadNewsManager {

    static boolean addReadNews(String ID, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(MyDBHelper.READ_NEWS_ID_COLUMN, ID);
        sqLiteDatabase.insert(MyDBHelper.READ_NEWS_TABLE, null, contentValues);
        return true;
    }

    static boolean isReadNews(String ID, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+MyDBHelper.READ_NEWS_TABLE+" where "+MyDBHelper.READ_NEWS_ID_COLUMN+"=\""+ID+"\"", null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

}
