package com.java.a31.androidappproject.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadeus on 9/12/17.
 */

class ReadKeywordManager {

    static String TABLE="read_keyword_table";
    static String NAME="keyword";

    static void addReadKeyword(String keyword, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME, keyword);
        sqLiteDatabase.insert(TABLE, null, contentValues);
    }

    static List<String> getReadKeywords(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+TABLE, null);
        ArrayList<String> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(
                        cursor.getString(cursor.getColumnIndex(NAME))
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

}
