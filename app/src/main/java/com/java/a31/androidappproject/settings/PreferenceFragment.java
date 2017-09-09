package com.java.a31.androidappproject.settings;

import android.content.SharedPreferences;
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
    private static final String TEXT_ONLY_MODE = "no_image_mode";

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

        findPreference(TEXT_ONLY_MODE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue){
                boolean isTextOnly = (boolean) newValue;
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.text_only_mode),0).edit();
                editor.putBoolean(getString(R.string.text_only_key), isTextOnly);
                editor.commit();
                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                getActivity().recreate();
                return true;
            }

        });
    }
}
