package com.java.a31.androidappproject.main;

/**
 * Created by yi__c on 2017/9/7.
 */
import com.java.a31.androidappproject.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchView extends LinearLayout implements TextWatcher, View.OnClickListener {
    /**
     * 输入框
     */
    private EditText et_search;


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.activity_search, this, true);
        /***找出控件*/
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(this);
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
}

