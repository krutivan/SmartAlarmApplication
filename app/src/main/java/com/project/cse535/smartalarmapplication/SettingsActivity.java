package com.project.cse535.smartalarmapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

/**
 * Created by Kruti on 11/15/2015.
 */
public class SettingsActivity extends PreferenceActivity{
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //general
        PreferenceCategory fakeHeader;
        addPreferencesFromResource(R.xml.pref_general);

        fakeHeader=new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_sleep_target);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_sleep_target);
    }
}
