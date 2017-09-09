package com.java.a31.androidappproject.models;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.java.a31.androidappproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amadeus on 9/5/17.
 */

public class NewsList implements INewsList {
    public static String BASIC_URL_FOR_RAW_QUERY="http://166.111.68.66:2042/news/action/query/latest";
    public static String BASIC_URL_PREFIX_FOR_SEARCH="http://166.111.68.66:2042/news/action/query/search?keyword=";

    private final int PAGE_SIZE=500;
    private final int PAGE_NUM=500;

    private HashSet<String> showedNews=new HashSet<String>();
    private INewsFilter filter=new INewsFilter() {
        @Override
        public boolean accept(INewsIntroduction newsIntroduction) {
            return true;
        }
    };
    private INewsFilter filter0=new INewsFilter() {
        @Override
        public boolean accept(INewsIntroduction newsIntroduction) {
            return true;
        }
    };
    private int currentPageNumber=1;
    private Context context; // attention!!
    private int mode;

    private List<INewsIntroduction> buffer;
    private int sizeNeed;

    private String basicURL;

    NewsList(Context context, int mode, String basicURL) {
        this.context=context;
        this.mode=mode;
        this.basicURL=basicURL;
    }

    void setFilter0(INewsFilter filter0) {
        this.filter0 = filter0;
    }

    private boolean getNetworkState() {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void reset() {
        Log.d("locate", "reset");
        showedNews.clear();
        currentPageNumber=1;
    }

    @Override
    public void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener) {
        getMore(size, pageNo, -1, listener);
    }

    @Override
    public void getMore(int size, int pageNo, int category, final INewsListener<List<INewsIntroduction>> listener) {
        //String url="http://166.111.68.66:2042/news/action/query/latest"+"?pageNo="+pageNo+"&pageSize="+size+"&category="+category;
        String url=basicURL+"?pageNo="+pageNo+"&pageSize="+size+"&category="+category;
        Log.d("url", url);
        //url="http://166.111.68.66:2042/news/action/query/latest?pageNo=1&pageSize=20";
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        ArrayList<INewsIntroduction> list=new ArrayList<INewsIntroduction>();
                        /*
                        NewsIntroduction newsIntroduction=new NewsIntroduction();
                        newsIntroduction.setTitle(response);
                        list.add(newsIntroduction);
                        */
                        try {
                            JSONObject reader=new JSONObject(response);
                            JSONArray array=reader.getJSONArray("list");
                            for (int i=0; i<array.length(); ++i) {
                                JSONObject jo=array.getJSONObject(i);
                                try {
                                    NewsIntroduction news=new NewsIntroduction();
                                    news.setTitle(jo.getString("news_Title"));
                                    news.setClassTag(jo.getString("newsClassTag"));
                                    news.setID(jo.getString("news_ID"));
                                    news.setSource(jo.getString("news_Source"));
                                    news.setTime(jo.getString("news_Time"));
                                    news.setURL(jo.getString("news_URL"));
                                    news.setAuthor(jo.getString("news_Author"));
                                    news.setLanguage(jo.getString("lang_Type"));
                                    news.setVideos(jo.getString("news_Video"));
                                    news.setIntroduction(jo.getString("news_Intro"));

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

                                    news.setFavoriteFlag(false);
                                    news.setReadFlag(false);

                                    if (filter!=null) {
                                        if (filter.accept(news)) {
                                            if (filter0==null || filter0.accept(news)) list.add(news);
                                        }
                                    } else {
                                        if (filter0==null || filter0.accept(news)) list.add(news);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (INewsIntroduction i: list) {
                            //Log.d("cached list", "trying to add "+i.getTitle());
                            try {
                                NewsManager.getInstance().add2CachedNewsList(i);
                            } catch (NewsManagerNotInitializedException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.getResult(list);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void needMore(final INewsListener<List<INewsIntroduction>> listener) {
        Log.d("getMore", "needMore "+sizeNeed+" current_page="+currentPageNumber);
        if (sizeNeed==0 || currentPageNumber>PAGE_NUM) {
            for (INewsIntroduction i: buffer) {
                try {
                    NewsManager.getInstance().add2CachedNewsList(i);
                } catch (NewsManagerNotInitializedException e) {
                    e.printStackTrace();
                }
            }
            listener.getResult(buffer);
        } else {
            getMore(PAGE_SIZE, currentPageNumber, new INewsListener<List<INewsIntroduction>>(){
                @Override
                public void getResult(List<INewsIntroduction> result) {
                    for (INewsIntroduction newsIntroduction: result) {
                        if (filter.accept(newsIntroduction) && filter0.accept(newsIntroduction) && !showedNews.contains(newsIntroduction.getID())) {
                            buffer.add(newsIntroduction);
                            showedNews.add(newsIntroduction.getID());
                            if ((--sizeNeed)==0) {
                                break;
                            }
                        }
                    }
                    if (sizeNeed>0) {
                        ++currentPageNumber;
                    }
                    needMore(listener);
                }
            });
        }
    }

    public void getMore(int size, INewsListener<List<INewsIntroduction>> listener) {
        Log.d("getMore", "getMore(size, listener)");
        buffer=new ArrayList<>();
        sizeNeed=size;
        needMore(listener);
    }

    @Override
    public void setFilter(INewsFilter filter) {
        this.filter=filter;
    }
}
