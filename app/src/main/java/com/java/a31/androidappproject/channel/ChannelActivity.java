package com.java.a31.androidappproject.channel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zwei on 2017/9/9.
 */

public class ChannelActivity extends AppCompatActivity implements ChannelContract.View {

    private ChannelContract.Presenter mPresenter;

    @Override
    public void setPresenter(@NonNull ChannelContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
