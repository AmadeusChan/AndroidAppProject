package com.java.a31.androidappproject.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.java.a31.androidappproject.R;
import com.java.a31.androidappproject.search.SearchResult.SearchResultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zwei on 2017/9/9.
 */

public class SearchActivity extends AppCompatActivity {

    private static final String KEY_QUERY = "KEY_QUERY";

    public static void start(Context context, String query) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(KEY_QUERY, query);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        String query = getIntent().getStringExtra(KEY_QUERY);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.result_container, SearchResultFragment.newInstance(query))
                .commit();
    }
}
