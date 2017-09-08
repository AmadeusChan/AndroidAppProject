package com.java.a31.androidappproject.news.NewsSearch;

/**
 * Created by yi__c on 2017/9/7.
 */
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.models.INewsList;
import com.java.a31.androidappproject.models.INewsListener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.lapism.searchview.SearchView;

public class NewsSearchPresenter extends LinearLayout implements NewsSearchContract.Presenter, INewsListener<List<INewsIntroduction>> {
    /**
     * 输入框
     */
    private SearchView mSearchView;

    private static final String TAG = "NewsSearchPresenter";

    private NewsManager mNewsManager;

    protected NewsSearchContract.View mView;

    protected INewsList mNewsList;

    public NewsSearchPresenter(@NonNull NewsSearchContract.View view, Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.activity_search, this, true);
        /***找出控件*/
        mView = view;
        mView.setPresenter(this);
        try {
            mNewsManager = NewsManager.getInstance();
        } catch (NewsManagerNotInitializedException e) {
            Log.e(TAG, "NewsSearchPresenter", e);
        }

        mSearchView = (SearchView) findViewById(R.id.search_view);
        if (mSearchView != null) {
            mSearchView.setVersionMargins(SearchView.VersionMargins.TOOLBAR_SMALL);
            mSearchView.setHint(R.string.search);
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mNewsList = mNewsManager.searchNews(query, R.string.latest);
                    mSearchView.close(false);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void loadNewsList(int size, int pageNo, int category) {
        mNewsList.getMore(size, pageNo, category, this);
    }

    @Override
    public void getResult(List<INewsIntroduction> newsList) {
        if (newsList.size() > 0) {
            mView.onSuccess(newsList);
        } else {
            mView.onFailure();
        }
    }
}
