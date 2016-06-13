package com.vvsai.rxjava.uitest;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.vvsai.rxjava.R;

/**
 * Created by lychee on 2016/6/13.
 */
public class SupportSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_supportsettings);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }
}
