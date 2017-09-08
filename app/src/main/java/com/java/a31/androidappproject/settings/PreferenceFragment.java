package com.java.a31.androidappproject.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.java.a31.androidappproject.R;

/**
 * Created by zwei on 2017/9/6.
 */

public class PreferenceFragment extends PreferenceFragmentCompat {

    private static final String KEY_NIGHT_MODE = "night_mode";

    public static PreferenceFragment newInstance() {
        return new PreferenceFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference);

        findPreference(KEY_NIGHT_MODE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                getActivity().recreate();
                return true;
            }
        });
    }
}
