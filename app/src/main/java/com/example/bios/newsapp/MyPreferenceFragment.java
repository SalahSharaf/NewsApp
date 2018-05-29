package com.example.bios.newsapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

public class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        MainActivity.preferenceChanged=true;
        return true;
    }
}