package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 * INewsManager接口的目的是提供一套供其他组件访问数据(即新闻内容或新闻列表)的规范，并且将项目中除了models包之外的其他部分与网络访问，数据库访问，本地缓存等关于数据访问的操作隔离开来
 * 要求实现该接口的类只能有一个实例，保证不会在访问数据库，读写文件等过程中造成冲突
 */

public interface INewsManager {

    INewsList getLatestNews(int mode);
    INewsDetail getNewsDetails(String ID, int mode);

    void setAsFavorite(String ID);
    void setAsNotFavorite(String ID);
    INewsList getFavoriteNews();

    void speakText(String text);

    List<String> getCategoryList();
    void addCategory(String category);
    void deleteCategory(String category);

}
