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

    void getMore(int size, int pageNo, int category, final INewsListener<List<INewsIntroduction>> listener);

    void getMore(int size, INewsListener<List<INewsIntroduction>> listener);

    /**
     * 给当前新闻列表设置filter
     * 注意：能被该filter接受的新闻应该在新闻库中占有足够高的比例，否则有可能导致在调用getMore()方法的时候遍历了整个在线新闻库
     * 都找不到可以返回的新闻(比如说在accept方法中直接return false会导致getMore()方法遍历整个在线新闻库)，这将耗费大量的时间，
     *
     * @param filter 要设置的filter
     */
    void setFilter(INewsFilter filter);
}
