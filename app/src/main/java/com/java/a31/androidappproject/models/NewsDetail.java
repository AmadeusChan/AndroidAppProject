package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public class NewsDetail extends NewsIntroduction implements INewsDetail {
    private List<String> persons;
    private List<String> locations;
    private List<String> keyWords;
    private String Content;

    public void setPersons(List<String> persons) {
        this.persons = persons;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public List<String> getPersons() {
        return persons;
    }

    @Override
    public List<String> getLocations() {
        return locations;
    }

    @Override
    public List<String> getKeyWords() {
        return keyWords;
    }

    @Override
    public String getContent() {
        return Content;
    }

}
