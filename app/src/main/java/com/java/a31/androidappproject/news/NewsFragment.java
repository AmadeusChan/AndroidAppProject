package com.java.a31.androidappproject.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.news.NewsList.NewsListFragment;
import com.lapism.searchview.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    // TODO: Clean up this shit later.
    private static final int[] categories = {
            R.string.latest,
            R.string.technology,
            R.string.education,
            R.string.military,
            R.string.domestic,
            R.string.society,
            R.string.culture,
            R.string.car,
            R.string.international,
            R.string.sport,
            R.string.economy,
            R.string.health,
            R.string.entertainment};

    private static final int[] positionToCategory = {
            -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
    };

    static public NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        mViewPager.setAdapter(new NewsFragmentStatePagerAdapter(getChildFragmentManager(), view.getContext()));

        for (int category : categories) {
            mTabLayout.addTab(mTabLayout.newTab().setText(category));
        }
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    public static class NewsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;

        public NewsFragmentStatePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public int getCount() {
            return categories.length;
        }

        @Override
        public Fragment getItem(int position) {
            return NewsListFragment.newInstance(positionToCategory[position]);
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return mContext.getResources().getString(categories[position]);
        }
    }
}
