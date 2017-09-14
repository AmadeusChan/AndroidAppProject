package com.java.a31.androidappproject.news.NewsList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsFilter;
import com.java.a31.androidappproject.models.INewsIntroduction;
import com.java.a31.androidappproject.news.NewsDetail.NewsDetailActivity;
import com.java.a31.androidappproject.news.NewsListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsListFragment extends Fragment implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    protected static final String KEY_CATEGORY = "CATEGORY";

    private NewsListContract.Presenter mPresenter;

    private static final int PAGE_SIZE = 20;
    private int mCategory;
    private int mPage;

    private NewsListAdapter mNewsListAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    public static NewsListFragment newInstance(int category) {
        Bundle args = new Bundle();
        args.putInt(KEY_CATEGORY, category);

        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences setting = this.getActivity().getSharedPreferences(getString(R.string.text_only_mode), 0);
        boolean isTextOnly = setting.getBoolean(getString(R.string.text_only_key), false);
        new NewsListPresenter(this, isTextOnly);
        mCategory = getArguments().getInt(KEY_CATEGORY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mNewsListAdapter = new NewsListAdapter();
        mNewsListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mNewsListAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mNewsListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation()));

        onRefresh();

        return view;
    }

    @Override
    public void setPresenter(@NonNull NewsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void enableLoadMore() {
        mNewsListAdapter.setEnableLoadMore(true);
    }

    private void disableLoadMore() {
        mNewsListAdapter.setEnableLoadMore(false);
    }

    private void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void enableRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    private void disableRefresh() {
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onSuccess(List<INewsIntroduction> newsList) {
        if (mPage == 1) {
            hideProgress();

            mNewsListAdapter.setNewData(newsList);
            enableLoadMore();
        } else {
            mNewsListAdapter.addData(newsList);
            mNewsListAdapter.loadMoreComplete();

            enableRefresh();
        }
        mPage++;
    }

    @Override
    public void onFailure() {
        if (mPage == 1) {
            hideProgress();

            mNewsListAdapter.setNewData(new ArrayList<INewsIntroduction>());

            enableLoadMore();
        } else {
            enableRefresh();

            mNewsListAdapter.loadMoreFail();
        }
    }

    @Override
    public void onRefresh() {
        mPage = 1;

        showProgress();
        disableLoadMore();

        mPresenter.loadNewsList(PAGE_SIZE, mPage, mCategory);
    }

    @Override
    public void onLoadMoreRequested() {
        disableRefresh();

        mPresenter.loadNewsList(PAGE_SIZE, mPage, mCategory);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        INewsIntroduction newsIntroduction = (INewsIntroduction) adapter.getItem(position);
        NewsDetailActivity.start(view.getContext(), newsIntroduction.getID());

        TextView textView = (TextView) view.findViewById(R.id.news_title);
        textView.setTextColor(view.getResources().getColor(R.color.colorSecondaryText));
    }

}
