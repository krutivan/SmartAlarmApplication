package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by tyagi on 11/17/15.
 */
public class StopAlarmReceiver extends WakefulBroadcastReceiver {
    static final String LOG_TAG = "SetAlarmAReceiver::";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive");
//        ContextPreferenceManager.getInstance().setAlarmContext(false);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.stop();
//        ringtone.stop
    }
}
