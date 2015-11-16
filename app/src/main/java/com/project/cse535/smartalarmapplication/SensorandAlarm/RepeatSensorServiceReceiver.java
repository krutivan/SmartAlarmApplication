package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.project.cse535.smartalarmapplication.R;

public class RepeatSensorServiceReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.project.cse535.smartalarmapplication.SensorandAlarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, GatherSensorData.class);

        context.startService(i);
    }

}
