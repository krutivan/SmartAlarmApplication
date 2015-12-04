package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

import com.project.cse535.smartalarmapplication.MainActivity;
import com.project.cse535.smartalarmapplication.SensorandAlarm.SetAlarmActivity;
import com.project.cse535.smartalarmapplication.SensorandAlarm.SetAlarmService;
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
        Log.d("ContextListener", "CONTEXT CHNG");
        //if illumination context changed and is made true
        if(key.equals(ContextPreferenceManager.ILLUMINATION_CONTEXT_KEY) && (sharedPreferences.getBoolean(ContextPreferenceManager.ILLUMINATION_CONTEXT_KEY, false))==true){
            //checks if motion also true, then only sleep context set
           if (sharedPreferences.getBoolean(ContextPreferenceManager.MOTION_CONTEXT_KEY, false)==true){
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putBoolean(ContextPreferenceManager.SLEEP_CONTEXT_KEY, true);
               editor.commit();
               Log.d("ContextListener", "illumination sleep set true");
           }
        }

        //if illumination context changed and is made true
        else if(key.equals(ContextPreferenceManager.MOTION_CONTEXT_KEY) && (sharedPreferences.getBoolean(ContextPreferenceManager.MOTION_CONTEXT_KEY, false))==true){
            //checks if motion also true, then only sleep context set
            if (sharedPreferences.getBoolean(ContextPreferenceManager.ILLUMINATION_CONTEXT_KEY, false)==true){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ContextPreferenceManager.SLEEP_CONTEXT_KEY, true);
                editor.commit();
                Log.d("ContextListener", ",motion sleep set true");
            }
        }
        else if(key.equals(ContextPreferenceManager.SLEEP_CONTEXT_KEY) && (sharedPreferences.getBoolean(ContextPreferenceManager.SLEEP_CONTEXT_KEY,false))==true){
            Log.d("ContextListener", ",sleep context set");
            Intent startSleepService = new Intent(mContext,SetAlarmService.class);
            mContext.startService(startSleepService);
//            Intent startAlarmIntent = new Intent(mContext, SetAlarmActivity.class);
//            mContext.startActivity(startAlarmIntent);
        }
        else if(key.equals(ContextPreferenceManager.ALARM_CONTEXT) && (sharedPreferences.getBoolean(ContextPreferenceManager.ALARM_CONTEXT,false)==true)){

        }


    }

}
