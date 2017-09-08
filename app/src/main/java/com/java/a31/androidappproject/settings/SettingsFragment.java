package com.java.a31.androidappproject.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.a31.androidappproject.R;

/**
 * Created by zwei on 2017/9/7.
 */

public class SettingsFragment extends Fragment {

    static public SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_container, PreferenceFragment.newInstance())
                .commit();

        return view;
    }
}
