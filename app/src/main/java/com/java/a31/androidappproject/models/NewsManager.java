package com.java.a31.androidappproject.models;

import android.content.Context;

import java.util.List;

/**
 * Created by amadeus on 9/5/17.
 * 负责数据操作的类，使用方法为调用getInstance(Context)获得实例，然后调用类中相应的方法即可
 */

public class NewsManager {
    private static NewsManager instance=null;
    private Context context;

    private NewsManager(Context context) {
        this.context=context;
    }

    /**
     * 该类的功能是返回一个NewsManager类的实例
     */
    public static synchronized NewsManager getInstance(Context context) {
        if (instance==null) {
            return instance=new NewsManager(context);
        } else {
            return instance;
        }
    }

    /**
     * 表示获取一个新闻列表的方法
     * @param mode mode=INews.TEXT_ONLY表示文字模式，即调用INewsList中的借口返回的数据中不包含图片;mode=INews.NORMAL_MODE表示正常模式
     * @return 返回值为一个INewsList接口，表示某一个新闻列表，具体方法可见该类的注释
     */
    public INewsList getLatestNews(int mode) {
        return new NewsList(context, mode);
    }

    /**
     * 获取已知ID的新闻的详细信息的方法
     * 注意：如果出现所给ID不正确等错误则将null传给listener.getResult()
     * 使用方法举例说明如下:
     * NewsManager.getInstance(context).getNewsDetails(ID, mode, new INewsListener<INewsDetail>() {
     *       @Override
     *       public void getResult(INewsDetail result) {
     *          // Instructions to update your UI
     *       }
     *   });
     *
     * @param ID 要查询详细信息的新闻ID
     * @param mode mode=INews.TEXT_ONLY表示文字模式；mode=INews.NORMAL_MODE表示普通模式（带图片）
     * @param listener 在获取新闻详细信息的操作完成之后，将数据以INewsDetail接口的信息传递给listener.getResult方法执行
     */
    public void getNewsDetails(String ID, int mode, INewsListener<INewsDetail> listener) {
        NewsDetailFetcherFromInternet.fetchDetail(ID, context, mode, listener);
    }

    public void setAsFavorite(String ID) {

    }

    public void setAsNotFavorite(String ID) {

    }

    public INewsList getFavoriteNews() {
        return null;
    }

    public void speakText(String text) {

    }

    public List<String> getCategoryList() {
        return null;
    }

    public void addCategory(String category) {

    }

    public void deleteCategory(String category) {

    }

}
