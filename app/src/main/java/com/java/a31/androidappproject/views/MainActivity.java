package com.java.a31.androidappproject.views;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;
import com.java.a31.androidappproject.models.NewsDetail;
import com.java.a31.androidappproject.models.NewsList;
import com.java.a31.androidappproject.models.NewsManager;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        Context context=getApplicationContext();
        //NewsManager.getInstance(context);
        NewsManager newsManager=NewsManager.getInstance(getApplicationContext());
        newsManager.deleteCategory("国际");
        newsManager.addCategory("娱乐");
        newsManager.deleteCategory("财经");
        TextView tv=(TextView) findViewById(R.id.text_view);
        List<String> list=NewsManager.getInstance(context).getCategoryList();
        String text="";
        for (String str: list) {
            Log.d("locate", str);
            text=text+str+"\n";
        }
        tv.setText(text);
        */

    }

}
