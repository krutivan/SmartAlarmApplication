package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.project.cse535.smartalarmapplication.R;

/**
 * Created by tyagi on 11/16/15.
 */
public class SensorDataReceiver extends BroadcastReceiver {

    static final String LOG_TAG = "SensorDataReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");
//            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);

            Bundle results = intent.getExtras();
            float accelValue = results.getFloat("accelVal");
            float lightValue = results.getFloat("lightVal");



//            Toast.makeText(MainActivity.this, accelValue + " " + lightValue, Toast.LENGTH_SHORT).show();
            if(accelValue < .05 && lightValue <= 3 ){
                Log.d(LOG_TAG,"setting alarm");

                Intent setalarm = new Intent(context, SetAlarmService.class);
                context.startService(setalarm);
            }

        }

}
