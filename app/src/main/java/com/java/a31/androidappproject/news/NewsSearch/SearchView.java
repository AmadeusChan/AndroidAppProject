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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class SearchView extends LinearLayout implements TextWatcher, View.OnClickListener, NewsSearchContract.Presenter, INewsListener<List<INewsIntroduction>> {
    /**
     * 输入框
     */
    private EditText et_search;

    protected NewsSearchContract.View mView;

    protected INewsList mNewsList;

    public SearchView(@NonNull NewsSearchContract.View view, Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.activity_search, this, true);
        /***找出控件*/
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(this);
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /****
     * 对用户输入文字的监听
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {
        /**获取输入文字**/
        String input = et_search.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        et_search.setText("");
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

