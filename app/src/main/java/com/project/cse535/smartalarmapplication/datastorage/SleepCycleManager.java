package com.project.cse535.smartalarmapplication.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kruti on 11/16/2015.
 */
public class SleepCycleManager {

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

    public SleepCycleManager(Context _context) {
        this._context = _context;
        pref=_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
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
        editor.putInt(BALANCE_DAYS,days);
        editor.commit();
    }

    public int getBalanceDays(){
        return pref.getInt(BALANCE_DAYS,1);
    }

    public void setBalanceHours(int hours){
        editor.putInt(BALANCE_HOURS,hours);
        editor.commit();
    }

    public int getBalanceHours(){
        return pref.getInt(BALANCE_HOURS,8);
    }

}