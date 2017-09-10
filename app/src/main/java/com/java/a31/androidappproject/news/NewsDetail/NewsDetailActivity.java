package com.java.a31.androidappproject.news.NewsDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.models.INewsDetail;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.LiveFolders.INTENT;

/**
 * Created by zwei on 2017/9/7.
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract.View {

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

    private MenuItem mShareButton;

    private MenuItem mStarButton;

    private MenuItem mReadButton;

    private static final String TAG = "NewsDetailActivity";

    private static final String KEY_NEWS_ID = "NEWS_ID";

    private String mNewsId;

    private NewsDetailContract.Presenter mPresenter;

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
            imageList.remove(0);
        } else {
            newsImageView.setVisibility(View.GONE);
        }
        if (imageList.size() > 0){
            URLImageParser p = new URLImageParser(newsContentView, this);

            String htmlText = "<img src='" + imageList.get(0) + "'>" + newsDetail.getContent().replaceAll("。 +", "。\n");
            Spanned htmlSpan = Html.fromHtml(htmlText, p, null);
            newsContentView.setText(htmlSpan);
        } else{
            newsContentView.setText(newsDetail.getContent().replaceAll(" 　　", "\n 　　"));
        }
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

    // TODO: rewrite this function later
    @Override
    public void share(final INewsDetail newsDetail) {
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

}

class URLDrawable extends BitmapDrawable {
    // the drawable that you need to set, you could set the initial drawing
    // with the loading image if you need to
    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        // override the draw to facilitate refresh function later
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}

class URLImageParser implements Html.ImageGetter {
    Context c;
    View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                URL Url = new URL(urlString);
                Drawable drawable = Drawable.createFromStream(Url.openStream(), "");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

    }
}