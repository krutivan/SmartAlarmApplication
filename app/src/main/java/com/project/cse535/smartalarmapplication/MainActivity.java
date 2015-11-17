package com.project.cse535.smartalarmapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.cse535.smartalarmapplication.SensorandAlarm.GatherSensorData;
import com.project.cse535.smartalarmapplication.SensorandAlarm.RepeatSensorServiceReceiver;
import com.project.cse535.smartalarmapplication.SensorandAlarm.SensorDataReceiver;
import com.project.cse535.smartalarmapplication.datastorage.ContextPreferenceChangeListener;
import com.project.cse535.smartalarmapplication.datastorage.ContextPreferenceManager;
import com.project.cse535.smartalarmapplication.datastorage.SleepCycleManager;
import com.project.cse535.smartalarmapplication.datastorage.UserPreferenceChangeListener;
import com.project.cse535.smartalarmapplication.datastorage.UserPreferencesManager;

public class MainActivity extends AppCompatActivity {
    ContextPreferenceManager mContextPref;
    ContextPreferenceChangeListener mContextPrefChangeListener;
    UserPreferencesManager mUserPref;
    UserPreferenceChangeListener mUserPrefChangeListener;
    SensorDataReceiver sensorReceiver ;
    SleepCycleManager sleepCycleManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContextPref = new ContextPreferenceManager(this);
        mUserPref = new UserPreferencesManager(this);
        mContextPrefChangeListener = new ContextPreferenceChangeListener();
        mUserPrefChangeListener = new UserPreferenceChangeListener();
        mContextPref.registerListener(mContextPrefChangeListener);
        mUserPref.registerListener(mUserPrefChangeListener);
        sleepCycleManager = SleepCycleManager.createInstance(this);
        sensorReceiver = new SensorDataReceiver();
        IntentFilter filter = new IntentFilter(GatherSensorData.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(sensorReceiver, filter);

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorServiceRepeatAlarm();
            }
        });
//        sensorServiceRepeatAlarm();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContextPref.unregisterListener(mContextPrefChangeListener);
        mUserPref.unregisterListener(mUserPrefChangeListener);
    }

    public void viewSleepPattern(View view){
        Intent chartIntent = new Intent(this,SleepChartActivity.class);
        startActivity(chartIntent);
    }
    public void forceSleep(View view){
        mContextPref.setContextPrefs(ContextPreferenceManager.SLEEP_CONTEXT_KEY, false);
        mContextPref.setContextPrefs(ContextPreferenceManager.SLEEP_CONTEXT_KEY, true);
    }

    // Setup a recurring alarm every 15 minutes
    public void sensorServiceRepeatAlarm() {
        Toast.makeText(MainActivity.this, "Scheduling Alarm.", Toast.LENGTH_SHORT).show();
        // Construct an intent that will execute the RepearSensorServiceReceiver
        Intent intent = new Intent(getApplicationContext(), RepeatSensorServiceReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, RepeatSensorServiceReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);
    }
}
