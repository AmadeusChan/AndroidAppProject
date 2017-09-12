package com.java.a31.androidappproject.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.java.a31.androidappproject.models.database.MyDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.java.a31.androidappproject.models.INews.TEXT_ONLY_MODE;

/**
 * Created by amadeus on 9/5/17.
 */

class NewsDetailFetcherFromInternet {
    static void fetchDetail(String ID, Context context, int mode, final INewsListener<INewsDetail> listener) {
        String url="http://166.111.68.66:2042/news/action/query/detail?newsId="+ID;
        final int finalMode = mode;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONObject reader=new JSONObject(response);
                    NewsDetail detail=new NewsDetail();

                    Log.d("Tag", "A");

                    JsonParser2NewsIntroduction.parseJson2NewsIntroduction(reader, detail, finalMode);

                    ArrayList<String> persons=new ArrayList<String>();
                    JSONArray array=reader.getJSONArray("persons");
                    for (int i=0; i<array.length(); ++i) {
                        persons.add(array.getJSONObject(i).getString("word"));
                    }
                    detail.setPersons(persons);

                    ArrayList<String> locations=new ArrayList<String>();
                    array=reader.getJSONArray("locations");
                    for (int i=0; i<array.length(); ++i) {
                        locations.add(array.getJSONObject(i).getString("word"));
                    }
                    detail.setLocations(locations);

                    ArrayList<String> keyWords=new ArrayList<String>();
                    array=reader.getJSONArray("Keywords");
                    for (int i=0; i<array.length(); ++i) {
                        keyWords.add(array.getJSONObject(i).getString("word"));
                        //Log.d("keyword", array.getJSONObject(i).getString("word"));
                    }
                    detail.setKeyWords(keyWords);

                    for (int i=0; i<10 && i<keyWords.size(); ++i) {
                        try {
                            NewsManager.getInstance().addReadKeyword(keyWords.get(i));
                        } catch (NewsManagerNotInitializedException e) {
                            e.printStackTrace();
                        }
                    }

                    String content=reader.getString("news_Content");
                    detail.setContent(content);

                    listener.getResult(detail);

                } catch (JSONException e) {
                    Log.d("Exception:", "JSONException");
                    e.printStackTrace();
                    listener.getResult(null);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(null);
            }
        });
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);
    }
}
