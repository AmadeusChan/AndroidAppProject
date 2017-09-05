package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsList {
    void reset();
    void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener);
    void setFilter(INewsFilter filter);
}
