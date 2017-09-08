package com.java.a31.androidappproject.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.favorite.FavoriteFragment;
import com.java.a31.androidappproject.models.NewsManager;
import com.java.a31.androidappproject.news.NewsFragment;
import com.java.a31.androidappproject.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.container)
    FrameLayout mFrameLayout;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    private static final String KEY_NAVIGATION_ID = "NAVIGATION_ID";

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new MainPresenter(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return mPresenter.switchNavigation(item.getItemId());
            }
        });

        // Initialize NewsManager::context
        NewsManager.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(KEY_NAVIGATION_ID, R.id.navigation_home);
            mPresenter.switchNavigation(id);
        } else {
            mPresenter.switchNavigation(R.id.navigation_home);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NAVIGATION_ID, mBottomNavigationView.getSelectedItemId());
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void switchToHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, NewsFragment.newInstance())
                .commit();
    }

    @Override
    public void switchToFavorite() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, FavoriteFragment.newInstance())
                .commit();
    }

    @Override
    public void switchToSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commit();
    }

}
