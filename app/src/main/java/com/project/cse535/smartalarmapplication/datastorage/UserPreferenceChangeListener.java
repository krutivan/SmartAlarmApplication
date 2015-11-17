package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        SleepCycleManager sleepHistory = SleepCycleManager.getInstance();
        UserPreferencesManager userPrefs = new UserPreferencesManager(mContext);
        if(key.equals("enable_automatic_checkbox")) {
            boolean b=userPrefs.getIfEnabled();
            Log.d("checkbox value",b+"");
        }
        else if(key.equals("sleep_target_hours"))
            sleepHistory.setBalanceHours(userPrefs.getTargetHours());
        else {
            sleepHistory.setBalanceDays(userPrefs.getTargetCycle());
            Log.d("checkbox value", userPrefs.getTargetCycle()+"");
        }
    }
}
