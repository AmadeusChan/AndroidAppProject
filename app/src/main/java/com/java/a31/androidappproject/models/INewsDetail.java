package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsDetail extends INewsIntroduction {
    List<String> getPersons();
    List<String> getLocations();
    List<String> getKeyWords();
    String getContent();
}
