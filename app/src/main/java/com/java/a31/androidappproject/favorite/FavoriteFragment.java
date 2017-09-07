package com.java.a31.androidappproject.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.favorite.FavoriteNews.FavoriteNewsFragment;

import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/7.
 */

public class FavoriteFragment extends Fragment {

    static public FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.favorite_container, FavoriteNewsFragment.newInstance())
                .commit();

        return view;
    }

}
