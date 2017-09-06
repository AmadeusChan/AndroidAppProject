package com.java.a31.androidappproject.models.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.java.a31.androidappproject.test.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.*;

/**
 * Created by amadeus on 9/6/17.
 */
@RunWith(AndroidJUnit4.class)
public class MyDBHelperTest {
    Context context;
    MyDBHelper myDBHelper;

    public void setUp() {
        Log.d("locate", "setting up");
        context=InstrumentationRegistry.getTargetContext();
        myDBHelper=new MyDBHelper(context);
    }
    @Test
    public void insertCategory() throws Exception {
        Log.d("locate", "testing insertCategory()");


    }

    @Test
    public void deleteCategory() throws Exception {
        Log.d("locate", "testing deleteCategory()");

    }

    @Test
    public void getCategoryList() throws Exception {
        Log.d("locate", "tsting getCategoryList");
        List<String> list=myDBHelper.getCategoryList();
        for (String i: list) {
            Log.d("locate", i);
        }

    }

}