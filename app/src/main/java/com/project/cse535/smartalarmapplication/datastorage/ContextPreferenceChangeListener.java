package com.project.cse535.smartalarmapplication.datastorage;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.project.cse535.smartalarmapplication.sleep.OnSleepService;

/**
 * Created by Kruti on 11/15/2015.
 */
public class ContextPreferenceChangeListener implements OnSharedPreferenceChangeListener{

    Context mContext;

    public void setmContext(Context context){
        mContext = context;
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(ContextPreferenceManager.SLEEP_CONTEXT_KEY) && (sharedPreferences.getBoolean(ContextPreferenceManager.SLEEP_CONTEXT_KEY,false))==true){

            Intent startSleepService = new Intent(mContext,OnSleepService.class);
            mContext.startService(startSleepService);
        }

    }

}
