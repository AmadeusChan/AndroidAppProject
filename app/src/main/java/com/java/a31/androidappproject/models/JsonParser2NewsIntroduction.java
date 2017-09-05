package com.java.a31.androidappproject.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amadeus on 9/5/17.
 */

class JsonParser2NewsIntroduction {
    static void parseJson2NewsIntroduction(JSONObject jo, NewsIntroduction news, int mode) throws JSONException{
        news.setTitle(jo.getString("news_Title"));
        news.setClassTag(jo.getString("newsClassTag"));
        news.setID(jo.getString("news_ID"));
        news.setSource(jo.getString("news_Source"));
        news.setTime(jo.getString("news_Time"));
        news.setURL(jo.getString("news_URL"));
        news.setAuthor(jo.getString("news_Author"));
        news.setLanguage(jo.getString("lang_Type"));
        news.setVideos(jo.getString("news_Video"));

        String str=jo.getString("news_Pictures");
        Log.d("get", str);
        if (mode==INews.TEXT_ONLY_MODE) {
            news.setImages(new ArrayList<String>());
        } else {
            ArrayList<String> images=new ArrayList<String>();
            Pattern p=Pattern.compile("http.*?(jpg|jpeg|png|gif)");
            Matcher matcher=p.matcher(str);
            while (matcher.find()) {
                images.add(matcher.group());
            }
            news.setImages(images);
        }
    }
}
