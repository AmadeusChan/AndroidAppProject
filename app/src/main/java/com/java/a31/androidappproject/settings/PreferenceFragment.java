package com.java.a31.androidappproject.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.java.a31.androidappproject.R;

/**
 * Created by zwei on 2017/9/6.
 */

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference);
    }
}
