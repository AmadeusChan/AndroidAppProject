package com.java.a31.androidappproject.channel;

import android.util.Log;

import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.java.a31.androidappproject.models.database.MyDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zwei on 2017/9/9.
 */

public class ChannelPresenter implements ChannelContract.Presenter {

    private ChannelContract.View mView;

    private NewsManager mNewsManager;

    private static final String TAG = "ChannelPresenter";

    ArrayList<String> mine;

    ArrayList<String> more;

    public ChannelPresenter(ChannelContract.View view) {
        mView = view;
        mView.setPresenter(this);

        try {
            mNewsManager = NewsManager.getInstance();
        } catch (NewsManagerNotInitializedException e) {
            Log.d(TAG, "ChannelPresenter", e);
        }
    }

    @Override
    public void start() {
        mine = new ArrayList<String>(mNewsManager.getCategoryList());
        more = new ArrayList<String>(Arrays.asList(MyDBHelper.categoryList));
        more.removeAll(mine);

        mView.setMimeChannel(mine);
        mView.setMoreChannel(more);
    }

    @Override
    public void addChannel(String channel) {
        mNewsManager.addCategory(channel);
        mView.addChannel(channel);
    }

    @Override
    public void removeChannel(String channel) {
        mNewsManager.deleteCategory(channel);
        mView.removeChannel(channel);
    }

}
