package com.java.a31.androidappproject.favorite.FavoriteNews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.news.NewsList.NewsListFragment;

/**
 * Created by zwei on 2017/9/7.
 */

public class FavoriteNewsFragment extends NewsListFragment {

    private static final int DEFAULT_CATEGORY = -1;

    public static FavoriteNewsFragment newInstance() {
        Bundle args = new Bundle();
        args.putInt(KEY_CATEGORY, DEFAULT_CATEGORY);

        FavoriteNewsFragment fragment = new FavoriteNewsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences setting = this.getActivity().getSharedPreferences(getString(R.string.text_only_mode), 0);
        boolean isTextOnly = setting.getBoolean(getString(R.string.text_only_key), false);
        new FavoriteNewsPresenter(this,isTextOnly);
    }

}
