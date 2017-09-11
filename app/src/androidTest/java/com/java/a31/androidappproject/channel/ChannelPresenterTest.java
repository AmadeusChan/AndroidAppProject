package com.java.a31.androidappproject.channel;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.java.a31.androidappproject.models.NewsManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zwei on 2017/9/11.
 */
public class ChannelPresenterTest {

    private Context context;
    private ChannelPresenter channelPresenter;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        NewsManager.getInstance(context);

        ChannelContract.View view = new ChannelContract.View() {
            private ChannelContract.Presenter mPresenter;

            @Override
            public void setMimeChannel(List<String> channelList) {
                System.out.print("setMimeChannel");
                for (String channel : channelList) {
                    System.out.print(channel);
                }
            }

            @Override
            public void setMoreChannel(List<String> channelList) {
                System.out.print("setMoreChannel");
                for (String channel : channelList) {
                    System.out.println(channel);
                }
            }

            @Override
            public void addChannel(String channel) {
                System.out.println("addChannel");
                System.out.println(channel);
            }

            @Override
            public void removeChannel(String channel) {
                System.out.println("removeChannel");
                System.out.println(channel);
            }

            @Override
            public void setPresenter(ChannelContract.Presenter presenter) {
                mPresenter = presenter;
            }
        };

        channelPresenter = new ChannelPresenter(view);
    }

    @Test
    public void start() throws Exception {
        channelPresenter.start();
    }

    @Test
    public void addChannel() throws Exception {
        channelPresenter.addChannel("CCTV-1");
    }

    @Test
    public void removeChannel() throws Exception {
        channelPresenter.removeChannel("CCTV-1");
    }

}