package com.java.a31.androidappproject.news;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsIntroduction;

import java.util.List;

/**
 * Created by zwei on 2017/9/5.
 */

public class NewsListAdapter extends BaseQuickAdapter<INewsIntroduction, BaseViewHolder> {
    public NewsListAdapter() {
        super(R.layout.item_news);
    }

    @Override
    protected void convert(BaseViewHolder helper, INewsIntroduction item) {
        helper.setText(R.id.news_title, item.getTitle());

        List<String> imageList = item.getImages();
        if (imageList.size() > 0) {
            ImageView imageView = helper.getView(R.id.news_image);
            Glide.with(helper.itemView).load(imageList.get(0)).into(imageView);
        }
    }
}
