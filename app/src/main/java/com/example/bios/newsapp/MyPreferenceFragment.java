package com.example.bios.newsapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment);
        Preference list = findPreference("list");
        list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MainActivity.preferenceChanged = true;
                String stringValue = newValue.toString();
                preference.setSummary(stringValue);
                MainActivity.listValue = stringValue;
                return true;
            }
        });
        Preference monthSelection = findPreference("monthSelection");
        monthSelection.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MainActivity.preferenceChanged = true;
                String stringValue = newValue.toString();
                preference.setSummary(stringValue);
                MainActivity.monthSelectionValue = stringValue;
                return true;
            }
        });
    }
}