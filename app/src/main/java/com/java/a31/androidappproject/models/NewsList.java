package com.java.a31.androidappproject.models;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.java.a31.androidappproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by amadeus on 9/5/17.
 */

public class NewsList implements INewsList {
    private final int PAGE_SIZE=100;

    private HashSet<String> showedNews=new HashSet<String>();
    private INewsFilter filter=null;
    private int currentPageNumber=1;
    private Context context;

    private boolean getNetworkState() {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void reset() {
        showedNews.clear();
        currentPageNumber=1;
    }

    @Override
    public void getMore(int size, int pageNo, final INewsListener<List<INewsIntroduction>> listener) {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, R.string.get_latest_news_api+"?pageSize="+size+"?pageNo"+pageNo, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        // to parse JSONObject into list of news introductions
                        ArrayList<INewsIntroduction> list=new ArrayList<INewsIntroduction>();
                        try {
                            JSONArray ja=response.getJSONArray("list");
                            int len=ja.length();
                            for (int i=0; i<len; ++i) {
                                NewsIntroduction newsIntroduction=new NewsIntroduction();
                                try {
                                    JSONObject j=ja.getJSONObject(i);
                                    newsIntroduction.setClassTag(j.getString("newsClassTag"));
                                    newsIntroduction.setID("news_ID");
                                    newsIntroduction.setSource("news_Source");
                                    newsIntroduction.setTitle("news_Title");
                                    newsIntroduction.setTime("news_Time");
                                    newsIntroduction.setURL("news_URL");
                                    newsIntroduction.setAuthor("news_Author");
                                    newsIntroduction.setLanguage("lang_Type");
                                    newsIntroduction.setVideos("news_Video");
                                    newsIntroduction.setIntroduction("news_Intro");
                                    // to be complete
                                    newsIntroduction.setReadFlag(false);
                                    newsIntroduction.setFavoriteFlag(false);
                                    newsIntroduction.setImages(null);
                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }
                                list.add(newsIntroduction);
                            }
                        } catch (JSONException e) {
                            list=null;
                        }

                        // to call the listener
                        listener.getResult(list);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.getResult(null);
                    }
                }
        );
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter=filter;
    }
}
