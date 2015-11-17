package com.project.cse535.smartalarmapplication.datastorage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;

/**
 * Created by Kruti on 11/15/2015.
 */
public class UserPreferencesManager {
    Context mContext;
    SharedPreferences mUserPreferences;

    public UserPreferencesManager(Context context){
        mUserPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
    }

    public void registerListener(UserPreferenceChangeListener listener){
        mUserPreferences.registerOnSharedPreferenceChangeListener(listener);
        listener.setmContext(mContext);
    }

    public void unregisterListener(UserPreferenceChangeListener listener){
        mUserPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean getIfEnabled(){
        return mUserPreferences.getBoolean("enable_automatic_checkbox",true);
    }
    public int getTargetHours(){

        String hours= mUserPreferences.getString("sleep_target_hours", "14");
        return Integer.parseInt(hours);
    }
    public int getTargetCycle(){
        String cycle= mUserPreferences.getString("sleep_target_cycle", "7");
        return Integer.parseInt(cycle);
    }
}
