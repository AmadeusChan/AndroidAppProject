package com.java.a31.androidappproject.search.SearchResult;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.news.NewsList.NewsListFragment;

/**
 * Created by zwei on 2017/9/9.
 */

public class SearchResultFragment extends NewsListFragment {

    private static final int DEFAULT_CATEGORY = -1;

    private static final String KEY_QUERY = "KEY_QUERY";

    public static SearchResultFragment newInstance(String query) {
        Bundle args = new Bundle();

        args.putInt(KEY_CATEGORY, DEFAULT_CATEGORY);
        args.putString(KEY_QUERY, query);

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String query = getArguments().getString(KEY_QUERY);
        SharedPreferences setting = this.getActivity().getSharedPreferences(getString(R.string.text_only_mode), 0);
        boolean isTextOnly = setting.getBoolean(getString(R.string.text_only_key), false);
        new SearchResultPresenter(this, query, isTextOnly);
    }

}
