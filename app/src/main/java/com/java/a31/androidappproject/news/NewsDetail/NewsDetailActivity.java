package com.java.a31.androidappproject.news.NewsDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsDetail;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.models.NewsManagerNotInitializedException;
import com.java.a31.androidappproject.models.sharing.wechat.Share2Wechat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.LiveFolders.INTENT;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract.View {

    @BindView(R.id.detail_layout)
    LinearLayout newsLayout;

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

 //   public static final String KEY_BANNED = "BANNED";

    private MenuItem mShareButton;

    private MenuItem mStarButton;

    private MenuItem mReadButton;

 //   private MenuItem mBanItem;

    private static final String TAG = "NewsDetailActivity";

    private static final String KEY_NEWS_ID = "NEWS_ID";

    private  static final String template = "<a href=http://www.baike.com/wiki/%s>%s</a>";

    private String mNewsId;

    private NewsDetailContract.Presenter mPresenter;

 //   private boolean isBanned = false;

 //   private SharedPreferences mSharedPreferences;

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

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mSharedPreferences = getSharedPreferences(KEY_BANNED, MODE_PRIVATE);
    }

    @Override
    protected void onStop() {
        mPresenter.stopReading();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);

        mStarButton = menu.findItem(R.id.star);
        mShareButton = menu.findItem(R.id.share);
        mReadButton = menu.findItem(R.id.read);
//        mBanItem = menu.findItem(R.id.ban);

        // onCreateOptionsMenu is called after onCreate
        new NewsDetailPresenter(this);
        SharedPreferences setting = getSharedPreferences(getString(R.string.text_only_mode), 0);
        boolean isTextOnly = setting.getBoolean(getString(R.string.text_only_key), false);
        mPresenter.loadNewsDetail(mNewsId, isTextOnly);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.share:
                mPresenter.onShareButtonCLick();
                return true;
            case R.id.star:
                mPresenter.onLikeButtonClick();
                return true;
            case R.id.read:
                mPresenter.onReadButtonClick();
                return true;
//            case R.id.ban:
//                if (isBanned) {
//                    setUnBan();
//                    isBanned = false;
//                } else {
//                    setBan();
//                    isBanned = true;
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(INewsDetail newsDetail) {
        // TODO: accurate news content formation
        mCollapsingToolbarLayout.setTitle(newsDetail.getTitle());

        List<String> imageList = newsDetail.getImages();
        if (imageList.size() > 0) {
            Glide.with(this)
                    .load(imageList.get(0))
                    .into(newsImageView);
        } else {
            newsImageView.setVisibility(View.GONE);
        }

        if (imageList.size() > 0){
            for (int i = 1, pos = 0; i < imageList.size(); i++, pos++) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this)
                        .load(imageList.get(i))
                        .into(imageView);
                newsLayout.addView(imageView, pos);
            }
//            int pos = 0;
//            for (String url:imageList){
//                ImageView imageView = new ImageView(this);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                Glide.with(this)
//                        .load(url)
//                        .into(imageView);
//                newsLayout.addView(imageView,pos++);
//            }
        }

        Log.d(TAG, newsDetail.getID());

        String content = newsDetail.getContent().replaceAll("[ 　]{2,}", "<br>&emsp;&emsp;");

        for (String keyword : newsDetail.getKeyWords()) {
            content = content.replaceAll(keyword, String.format(template, keyword, keyword));
        }

        for (String location : newsDetail.getLocations()) {
            content = content.replaceAll(location, String.format(template, location, location));
        }

        for (String person : newsDetail.getPersons()) {
            content = content.replaceAll(person, String.format(template, person, person));
        }

        newsContentView.setText(Html.fromHtml(content));

        newsContentView.setMovementMethod(LinkMovementMethod.getInstance());

//        Set<String> banned = mSharedPreferences.getStringSet(KEY_BANNED, new HashSet<String>());
//        for (String keyword : newsDetail.getKeyWords()) {
//            if (banned.contains(keyword)) {
//                isBanned = true;
//                setBan();
//                break;
//            }
//        }
//        if (banned.contains(newsDetail.getTitle())) {
//            isBanned = true;
//            setBan();
//        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void setLike() {
        mStarButton.setIcon(R.drawable.ic_star_yellow_24dp);
    }

    @Override
    public void setUnLike() {
        mStarButton.setIcon(R.drawable.ic_star_white_24dp);
    }

//    @Override
//    public void setBan() {
//        mBanItem.setIcon(R.drawable.ic_not_interested_red_24dp);
//
//        INewsDetail newsDetail = mPresenter.getNewsDetail();
//
//        if (newsDetail != null && newsDetail.getKeyWords().size() > 1) {
//            Set<String> banned = mSharedPreferences.getStringSet(KEY_BANNED, new HashSet<String>());
//            banned.add(newsDetail.getKeyWords().get(0));
//            banned.add(newsDetail.getTitle());
//            mSharedPreferences.edit().putStringSet(KEY_BANNED, banned).apply();
//        }
//    }
//
//    @Override
//    public void setUnBan() {
//        mBanItem.setIcon(R.drawable.ic_not_interested_white_24dp);
//
//        INewsDetail newsDetail = mPresenter.getNewsDetail();
//
//        if (newsDetail != null && newsDetail.getKeyWords().size() > 1) {
//            Set<String> banned = mSharedPreferences.getStringSet(KEY_BANNED, new HashSet<String>());
//            banned.remove(newsDetail.getKeyWords().get(0));
//            banned.remove(newsDetail.getTitle());
//            mSharedPreferences.edit().putStringSet(KEY_BANNED, banned).apply();
//        }
//    }

    // TODO: rewrite this function later
    @Override
    public void share(final INewsDetail newsDetail) {

        final BottomSheetDialog dialog = new BottomSheetDialog(this);

        View view = getLayoutInflater().inflate(R.layout.actions_details_sheet, null);

        AppCompatTextView wechat = view.findViewById(R.id.share_to_wechat);
        AppCompatTextView weibo = view.findViewById(R.id.share_to_weibo);
        AppCompatTextView timeline = view.findViewById(R.id.share_to_timeline);
        AppCompatTextView more = view.findViewById(R.id.more_options);

        final NewsManager instance = NewsManager.getInstance(getApplicationContext());

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String image = null;
                if (newsDetail.getImages().size() > 0) {
                    image = newsDetail.getImages().get(0);
                }
                instance.share2Weibo(NewsDetailActivity.this, newsDetail.getURL(), newsDetail.getIntroduction(), image);
            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String image = null;
                if (newsDetail.getImages().size() > 0) {
                    image = newsDetail.getImages().get(0);
                }
                instance.share2Wechat(newsDetail.getURL(), newsDetail.getIntroduction(), image, Share2Wechat.SHARE_TO_FRIEND);
            }
        });

        timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String image = null;
                if (newsDetail.getImages().size() > 0) {
                    image = newsDetail.getImages().get(0);
                    Log.d(TAG, image);
                }
                Log.d(TAG, newsDetail.getIntroduction());
                instance.share2Wechat(newsDetail.getURL(), newsDetail.getIntroduction(), image, Share2Wechat.SHARE_TO_TIMELINE);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                final Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                if (newsDetail.getImages().size() > 0) {
                    intent.setType("image/*").putExtra("Kdescription", newsDetail.getTitle());

                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
                    ImageLoader.getInstance().init(config);
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.loadImage(newsDetail.getImages().get(0), new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Log.d(TAG, imageUri);

                            try {
                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                                FileOutputStream out = new FileOutputStream(file);
                                loadedImage.compress(Bitmap.CompressFormat.PNG, 90, out);
                                out.close();
                                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(NewsDetailActivity.this, "com.java.a31", file));
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    intent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, newsDetail.getTitle());
                    startActivity(intent);
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }
}
