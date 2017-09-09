package com.java.a31.androidappproject.channel;

import com.java.a31.androidappproject.BasePresenter;
import com.java.a31.androidappproject.BaseView;

import java.util.List;

/**
 * Created by zwei on 2017/9/9.
 */

public interface ChannelContract {

    interface View extends BaseView<Presenter> {

        void setMimeChannel(List<String> channelList);

        void setMoreChannel(List<String> channelList);

        void addChannel(String channel);

        void removeChannel(String channel);

    }

    interface Presenter extends BasePresenter {

        void addChannel(String channel);

        void removeChannel(String channel);

    }

}
