package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsList {
    void reset();
    List<INewsIntroduction> getMore(int size);
    void setFilter(INewsFilter filter);
}
