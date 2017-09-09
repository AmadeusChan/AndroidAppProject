package com.java.a31.androidappproject.channel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.java.a31.androidappproject.R;

/**
 * Created by zwei on 2017/9/9.
 */

public class ChannelListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChannelListAdapter() {
        super(R.layout.item_channel);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.channel_name, item);
    }
}
