package com.java.a31.androidappproject.models.sharing;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.java.a31.androidappproject.main.MainActivity;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.sharing.weibo.Share2Weibo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by amadeus on 9/9/17.
 */
public class Share2WeiboTest {
    Context context;
    Activity activity;
    NewsManager newsManager;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void share() throws Exception {
        String image="https://cnet4.cbsistatic.com/img/QJcTT2ab-sYWwOGrxJc0MXSt3UI=/2011/10/27/a66dfbb7-fdc7-11e2-8c7c-d4ae52e62bcc/android-wallpaper5_2560x1600_1.jpg";
        String introduction="This is GITHUB.";
        String url="https://github.com";
        Share2Weibo.share(activity, url, introduction, image);
        Thread.sleep(1000);
    }

}