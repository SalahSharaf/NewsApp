package com.example.bios.newsapp;

import android.preference.PreferenceActivity;

import java.util.List;

public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.activity,target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }
}
