package com.java.a31.androidappproject.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public class NewsIntroduction implements INewsIntroduction{
    private String classTag, ID, source, title, time, URL, author, language, videos, introduction;
    private boolean isReadFlag, isFavoriteFlag;
    private List<String> images;

    public void setClassTag(String classTag) {
        this.classTag = classTag;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setReadFlag(boolean readFlag) {
        isReadFlag = readFlag;
    }

    public void setFavoriteFlag(boolean favoriteFlag) {
        isFavoriteFlag = favoriteFlag;
    }

    @Override
    public String getClassTag() {
        return classTag;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getVideos() {
        return videos;
    }

    @Override
    public String getIntroduction() {
        return introduction;
    }

    /***
     * 判断当前新闻是否读过，是否读过的标准为是否使用NewsManager.getNewsDetails获取过该新闻的详细信息
     * @return true表示读过，false表示没有读过
     */
    @Override
    public boolean isRead() {
        try {
            NewsManager newsManager=NewsManager.getInstance();
            return newsManager.isReadNews(ID);
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断当前新闻是否被收藏
     * @return true表示被收藏，false表示没有
     */
    @Override
    public boolean isFavorite() {
        try {
            NewsManager newsManager=NewsManager.getInstance();
            return newsManager.isFavoriteNews(ID);
        } catch (NewsManagerNotInitializedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回当前新闻涉及的图片的url，建议使用Android-Universal-Image-Loader(https://github.com/nostra13/Android-Universal-Image-Loader)将图片加载到imageView中
     * @return
     */
    @Override
    public List<String> getImages() {
        return images;
    }
}
