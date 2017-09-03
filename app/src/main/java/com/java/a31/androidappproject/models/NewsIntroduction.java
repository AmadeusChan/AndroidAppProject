package com.java.a31.androidappproject.models;

/**
 * Created by amadeus on 9/3/17.
 */

public class NewsIntroduction implements INewsIntroduction{
    private String classTag, ID, source, title, time, URL, author, language, videos, introduction;
    private boolean isReadFlag;

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

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setReadFlag(boolean readFlag) {
        isReadFlag = readFlag;
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

    @Override
    public boolean isRead() {
        return isReadFlag;
    }
}
