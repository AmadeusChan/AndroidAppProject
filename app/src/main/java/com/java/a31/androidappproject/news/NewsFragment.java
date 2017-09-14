package com.java.a31.androidappproject.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.channel.ChannelActivity;
import com.java.a31.androidappproject.models.NewsDetail;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.java.a31.androidappproject.models.database.MyDBHelper;
import com.java.a31.androidappproject.news.NewsDetail.NewsDetailActivity;
import com.java.a31.androidappproject.news.NewsList.NewsListFragment;
import com.java.a31.androidappproject.search.SearchActivity;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsFragment extends Fragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private static final String TAG = "NewsFragment";

    private NewsFragmentStatePagerAdapter mPagerAdapter;

    static public NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        mSearchView.setNavigationIcon(R.drawable.ic_search_black_24dp);
        mSearchView.setVoice(false);
        mSearchView.setOnQueryTextListener(this);

        mPagerAdapter = new NewsFragmentStatePagerAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPagerAdapter.onResume();
        mPagerAdapter.notifyDataSetChanged();
    }

    public static class NewsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private static final String TAG = "PagerAdapter";

        private static final String DEFAULT = "热点";

        private static final String RECOMMAND = "推荐";

        private static final Map<String, Integer> nameToCategory;

        private NewsManager mNewsManager;

        private List<String> names;

        static {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put(DEFAULT, -1);
            map.put(RECOMMAND, 0);
            for (int i = 0; i < MyDBHelper.categoryList.length; i++) {
                map.put(MyDBHelper.categoryList[i], i + 1);
            }
            nameToCategory = Collections.unmodifiableMap(map);
        }

        public NewsFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
            try {
                mNewsManager = NewsManager.getInstance();
            } catch (NewsManagerNotInitializedException e) {
                Log.e(TAG, "NewsFragmentStatePagerAdapter", e);
            }
            onResume();
        }

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem " + position);
            return NewsListFragment.newInstance(nameToCategory.get(names.get(position)));
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return names.get(position);
        }

        public void onResume() {
            names = new ArrayList<String>();
            names.add(DEFAULT);
            names.add(RECOMMAND);
            names.addAll(mNewsManager.getCategoryList());
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.close(false);
        SearchActivity.start(getContext(), URLEncoder.encode(query));
        return true;
    }

    @OnClick(R.id.add_channel)
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ChannelActivity.class);
        startActivity(intent);
    }
}
