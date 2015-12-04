package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kruti on 11/16/2015.
 */
public class SleepCycleManager {

    static SleepCycleManager sleepCycleManager = null;
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SleepHistory";

    //Keys

    public static final String MON = "mon";
    public static final String TUE = "tue";
    public static final String WED = "wed";
    public static final String THU = "thu";
    public static final String FRI = "fri";
    public static final String SAT = "sat";
    public static final String SUN = "sun";

    public static final String BALANCE_HOURS = "balance_hours";
    public static final String BALANCE_DAYS = "balance_days";

    private SleepCycleManager(Context _context) {
        this._context = _context;
        pref=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        /*setBalanceDays(7);
        setBalanceHours(7);*/
    }

    public static SleepCycleManager createInstance(Context _context){
        sleepCycleManager = new SleepCycleManager(_context);
        return sleepCycleManager;
    }
    public static SleepCycleManager getInstance(){
        return sleepCycleManager;
    }

    public void initRandomHistory(){
        setHours(MON,8);
        setHours(TUE,9);
        setHours(WED,10);
        setHours(THU,5);
        setHours(FRI,11);
        setHours(SAT,7);
        setHours(SUN,6);
    }

    public int getHours(String day){
        return pref.getInt(day,8);
    }
    public void setHours(String day, int hours){
        editor.putInt(day,hours);
        editor.commit();
    }
    public void setBalanceDays(int days){
        if(days>0)
            editor.putInt(BALANCE_DAYS,days);
        else
            editor.putInt(BALANCE_DAYS,8);

        editor.commit();
    }

    public int getBalanceDays(){
        return pref.getInt(BALANCE_DAYS,1);
    }

    public void setBalanceHours(int hours){
        if(hours>0)
            editor.putInt(BALANCE_HOURS,hours);
        else
            editor.putInt(BALANCE_HOURS,8);
//        editor.putLong(BALANCE_HOURS,hours);
        editor.commit();
    }

    public int getBalanceHours(){
        return pref.getInt(BALANCE_HOURS,8);
    }

}
