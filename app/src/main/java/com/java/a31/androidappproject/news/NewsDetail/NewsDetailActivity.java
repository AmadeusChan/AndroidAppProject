package com.java.a31.androidappproject.news.NewsDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract.View {

    @BindView(R.id.detail_title)
    TextView newsTitleView;

    @BindView(R.id.detail_content)
    TextView newsContentView;

    @BindView(R.id.detail_image)
    ImageView newsImageView;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private static final String TAG = "NewsDetailActivity";

    private static final String KEY_NEWS_ID = "NEWS_ID";

    private String mNewsId;

    private NewsDetailContract.Presenter mPresenter;

    private INewsDetail mNewsDetail;

    public static void start(Context context, String newsId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS_ID, newsId);
        context.startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull NewsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mNewsId = getIntent().getStringExtra(KEY_NEWS_ID);

        new NewsDetailPresenter(this);

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter.loadNewsDetail(mNewsId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.share:
                return true;
            case R.id.star:
                try {
                    NewsManager.getInstance().setAsFavorite(mNewsDetail);
                } catch (NewsManagerNotInitializedException e) {
                    Log.e(TAG, "onOptionsItemSelected", e);
                }
                return true;
            case  R.id.read:
                try {
                    NewsManager.getInstance().speakText(mNewsDetail.getContent());
                } catch (NewsManagerNotInitializedException e) {
                    Log.e(TAG, "onOptionsItemSelected", e);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(INewsDetail newsDetail) {
        mNewsDetail = newsDetail;

        mCollapsingToolbarLayout.setTitle(mNewsDetail.getTitle());
        // TODO: accurate news content formation
        newsContentView.setText(mNewsDetail.getContent().replaceAll(" 　　", "\n 　　"));

        List<String> imageList = mNewsDetail.getImages();
        if (imageList.size() > 0) {
            Glide.with(this).load(imageList.get(0)).into(newsImageView);
        } else {
            newsImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }

}
