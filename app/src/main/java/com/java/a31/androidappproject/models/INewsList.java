package com.java.a31.androidappproject.models;

import java.util.List;

/**
 * Created by amadeus on 9/3/17.
 */

public interface INewsList {
    /**
     * 刷新列表
     */
    void reset();

    /**
     * 获取更多新闻简介，以List<INewsIntroduction>的形式传给listener.getResult方法
     *
     * 使用该方法的例子：
     *
     * newsList.getMore(20, 1, new INewsListener<List<INewsIntroduction>>() {
     *      @Override
     *      public void getResult(List<INewsIntroduction> result) {
     *          // instructions to update your UI
     *      }
     *  });
     *
     * @param size  返回的新闻数目
     * @param pageNo 返回的新闻按时间倒叙排序的下标为pageNo*(size-1)+1至pageNo*size
     * @param listener 获取的数据将传给listener.getResult方法
     */
    void getMore(int size, int pageNo, INewsListener<List<INewsIntroduction>> listener);

    /**
     * 给新闻列表设置过滤器，使得调用getMore()方法时只返回使得filter.accept()返回值为true的新闻，默认返回所有新闻
     * @param filter
     */
    void getMore(int size, int pageNo, int category, final INewsListener<List<INewsIntroduction>> listener);

    void setFilter(INewsFilter filter);
}
