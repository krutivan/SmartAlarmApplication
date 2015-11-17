package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kruti on 11/15/2015.
 */
public class ContextPreferenceManager {
    private static ContextPreferenceManager contextPreferenceManager;
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ContextPrefs";

    //Keys
    public static final String ILLUMINATION_CONTEXT_KEY = "illumination";
    public static final String MOTION_CONTEXT_KEY = "motion";
    public static final String SLEEP_CONTEXT_KEY = "sleep";
    public static final String ALARM_CONTEXT = "alarm";

    public static ContextPreferenceManager createInstance(Context _context){
        contextPreferenceManager = new ContextPreferenceManager(_context);
        return contextPreferenceManager;
    }
    public static ContextPreferenceManager getInstance(){
        return contextPreferenceManager;
    }
    private ContextPreferenceManager(Context _context) {
        this._context = _context;
        pref=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        setContextPrefs(ILLUMINATION_CONTEXT_KEY,false);
        setContextPrefs(MOTION_CONTEXT_KEY,false);
        setContextPrefs(SLEEP_CONTEXT_KEY,false);
        setAlarmContext(false);
    }

    public void registerListener(ContextPreferenceChangeListener listener){
        pref.registerOnSharedPreferenceChangeListener(listener);
        listener.setmContext(_context);

    }

    public void unregisterListener(ContextPreferenceChangeListener listener){
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void setContextPrefs(String key, Boolean value){
        editor.putBoolean(key,value);
        editor.commit();
        Log.d("ContextPrefManager", key);
    }

    public Boolean getContextPrefs(String key){
        if (key.equals(SLEEP_CONTEXT_KEY))
            return pref.getBoolean(SLEEP_CONTEXT_KEY,false);
        else
            return pref.getBoolean(key,false);
    }

    public void setAlarmContext(Boolean alarm){
        editor.putBoolean(ALARM_CONTEXT, alarm);
        editor.commit();
    }

    public boolean getAlarmContext(){
        return pref.getBoolean(ALARM_CONTEXT,false);
    }
}
