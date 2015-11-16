package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.SharedPreferences.*;

/**
 * Created by Kruti on 11/16/2015.
 */
public class UserPreferenceChangeListener implements OnSharedPreferenceChangeListener {

    Context mContext;

    public void setmContext(Context context){
        mContext = context;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SleepCycleManager sleepHistory = new SleepCycleManager(mContext);
        UserPreferencesManager userPrefs = new UserPreferencesManager(mContext);
        sleepHistory.setBalanceHours(userPrefs.getTargetHours());
        sleepHistory.setBalanceDays(userPrefs.getTargetCycle());
    }
}
