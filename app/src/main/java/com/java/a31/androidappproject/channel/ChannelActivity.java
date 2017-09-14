package com.java.a31.androidappproject.channel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.java.a31.androidappproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/9.
 */

public class ChannelActivity extends AppCompatActivity implements ChannelContract.View {

    @BindView(R.id.channel_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.channel_mine)
    RecyclerView channelMine;

    @BindView(R.id.channel_more)
    RecyclerView channelMore;

    private ChannelContract.Presenter mPresenter;

    private ChannelListAdapter channelMineAdapter;

    private ChannelListAdapter channelMoreAdapter;

    private BaseQuickAdapter.OnItemClickListener channelMineListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            String channel = (String) adapter.getItem(position);
            mPresenter.removeChannel(channel);
            adapter.remove(position);
        }
    };

    private BaseQuickAdapter.OnItemClickListener channelMoreListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            String channel = (String) adapter.getItem(position);
            mPresenter.addChannel(channel);
            adapter.remove(position);
        }
    };

    @Override
    public void setPresenter(@NonNull ChannelContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.bind(this);

        channelMineAdapter = new ChannelListAdapter();
        initChannel(channelMine, channelMineAdapter);
        channelMineAdapter.setOnItemClickListener(channelMineListener);

        channelMoreAdapter = new ChannelListAdapter();
        initChannel(channelMore, channelMoreAdapter);
        channelMoreAdapter.setOnItemClickListener(channelMoreListener);

        new ChannelPresenter(this);
        mPresenter.start();
    }

    private void initChannel(RecyclerView recyclerView, ChannelListAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void setMimeChannel(List<String> channelList) {
        channelMineAdapter.setNewData(channelList);
    }

    @Override
    public void setMoreChannel(List<String> channelList) {
        channelMoreAdapter.setNewData(channelList);
    }

    @Override
    public void addChannel(String channel) {
        channelMineAdapter.addData(channel);
    }

    @Override
    public void removeChannel(String channel) {
        channelMoreAdapter.addData(channel);
    }

}
