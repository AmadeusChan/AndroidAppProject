package com.java.a31.androidappproject.main;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.favorite.FavoriteFragment;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.news.NewsFragment;
import com.java.a31.androidappproject.settings.SettingsFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.container)
    FrameLayout mFrameLayout;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    private NewsFragment mNewsFragment;

    private FavoriteFragment mFavoriteFragment;

    private SettingsFragment mSettingsFragment;

    private static final String KEY_NAVIGATION_ID = "NAVIGATION_ID";

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize NewsManager::context
        NewsManager.getInstance(getApplicationContext());

        new MainPresenter(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return mPresenter.switchNavigation(item.getItemId());
            }
        });

        initFragments(savedInstanceState);

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(KEY_NAVIGATION_ID, R.id.navigation_home);
            mPresenter.switchNavigation(id);
        } else {
            mPresenter.switchNavigation(R.id.navigation_home);
        }
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mNewsFragment = NewsFragment.newInstance();
            mFavoriteFragment = FavoriteFragment.newInstance();
            mSettingsFragment = SettingsFragment.newInstance();
        } else {
            mNewsFragment = (NewsFragment) fm.getFragment(savedInstanceState, NewsFragment.class.getSimpleName());
            mFavoriteFragment = (FavoriteFragment) fm.getFragment(savedInstanceState, FavoriteFragment.class.getSimpleName());
            mSettingsFragment = (SettingsFragment) fm.getFragment(savedInstanceState, SettingsFragment.class.getSimpleName());
        }

        if (!mNewsFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mNewsFragment, NewsFragment.class.getSimpleName())
                    .commit();
        }

        if (!mFavoriteFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mFavoriteFragment, FavoriteFragment.class.getSimpleName())
                    .commit();

        }

        if (!mSettingsFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mSettingsFragment, SettingsFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NAVIGATION_ID, mBottomNavigationView.getSelectedItemId());
        FragmentManager fm = getSupportFragmentManager();
        if (mNewsFragment.isAdded()) {
            fm.putFragment(outState, NewsFragment.class.getSimpleName(), mNewsFragment);
        }
        if (mFavoriteFragment.isAdded()) {
            fm.putFragment(outState, FavoriteFragment.class.getSimpleName(), mFavoriteFragment);
        }
        if (mSettingsFragment.isAdded()) {
            fm.putFragment(outState, SettingsFragment.class.getSimpleName(), mSettingsFragment);
        }
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void switchToHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(mNewsFragment)
                .hide(mFavoriteFragment)
                .hide(mSettingsFragment)
                .commit();
    }

    @Override
    public void switchToFavorite() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(mFavoriteFragment)
                .hide(mNewsFragment)
                .hide(mSettingsFragment)
                .commit();
    }

    @Override
    public void switchToSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(mSettingsFragment)
                .hide(mNewsFragment)
                .hide(mFavoriteFragment)
                .commit();
    }

}
