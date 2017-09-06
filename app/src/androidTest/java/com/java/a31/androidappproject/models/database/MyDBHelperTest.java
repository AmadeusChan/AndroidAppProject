package com.java.a31.androidappproject.models.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.java.a31.androidappproject.models.INews;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.test.BuildConfig;

import org.junit.Before;
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

    @Before
    public void setUp() {
        Log.d("locate", "setting up");
        context=InstrumentationRegistry.getTargetContext();
        myDBHelper=new MyDBHelper(context);
    }

    /*
    @Test
    public void insertCategory() throws Exception {
        Log.d("locate", "testing insertCategory()");
        myDBHelper.insertCategory("国际");

    }

    @Test
    public void deleteCategory() throws Exception {
        Log.d("locate", "testing deleteCategory()");
        myDBHelper.deleteCategory("国际");

    }

    @Test
    public void getCategoryList() throws Exception {
        Log.d("locate", "testing getCategoryList");
        List<String> list=myDBHelper.getCategoryList();
        for (String i: list) {
            Log.d("locate", i);
        }

    }
    */

    @Test
    public void testFavorite() throws Exception {
        myDBHelper.deleteFavoriteNews("20160913041337970c2ada674069830bd2677e2f4816");

        INewsList newsList=NewsManager.getInstance(context).getFavoriteNews();
        newsList.getMore(1000, 1, new INewsListener<List<INewsIntroduction>>() {
            @Override
            public void getResult(List<INewsIntroduction> result) {
                for (int i=0; i<result.size(); ++i) {
                    Log.d("favorite", result.get(i).getID());
                    Log.d("favorite", result.get(i).getTitle());
                    Log.d("favorite", result.get(i).getIntroduction());
                    Log.d("favorite", result.get(i).getImages().toString());
                }
            }
        });

        NewsManager.getInstance(context).getNewsDetails("2016091304131732ac05cc9d4c41bd93856dfa85509c", INews.NORMAL_MODE, new INewsListener<INewsDetail>(){
            @Override
            public void getResult(INewsDetail result) {
                Log.d("favorite", result.getID()+" "+result.getTitle());
                NewsManager.getInstance(context).setAsFavorite(result);
            }
        });

        for (int i=0; i<100000000; ++i);
        while (true);
    }


}