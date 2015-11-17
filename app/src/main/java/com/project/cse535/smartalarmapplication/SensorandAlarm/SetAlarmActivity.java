package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.cse535.smartalarmapplication.R;
import com.project.cse535.smartalarmapplication.datastorage.ContextPreferenceManager;
import com.project.cse535.smartalarmapplication.datastorage.SleepCycleManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SetAlarmActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SetAlarmService::";
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    AlarmManager alarmManager;
    ContextPreferenceManager contextPreferenceManager;
    private PendingIntent pendingIntent;
    Long time;
    SleepCycleManager sleepHistory;
    TextView textView;
    Button btnCancelAlarm;
    Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        sleepHistory = SleepCycleManager.getInstance();
        contextPreferenceManager = ContextPreferenceManager.getInstance();
        textView = (TextView)findViewById(R.id.alarmView);
        btnCancelAlarm = (Button)findViewById(R.id.cancelAlarmButton);
        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone!=null)
                    ringtone.stop();
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(LOG_TAG,"OnStartCommand");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(SetAlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetAlarmActivity.this, 0, myIntent, 0);

        int balance_sleeptime = sleepHistory.getBalanceHours();
        int balance_sleepdays = sleepHistory.getBalanceDays();
        int targetSleep = balance_sleeptime/balance_sleepdays;

        time = new GregorianCalendar().getTimeInMillis()+targetSleep*60*1000;

        sleepHistory.setBalanceDays(balance_sleepdays - 1);
        sleepHistory.setBalanceHours(balance_sleeptime - targetSleep);
        textView.setText("Alarm set for"+time);
        Log.d(LOG_TAG, targetSleep + "mins");
        Toast.makeText(SetAlarmActivity.this, "Today's sleep hours : " + targetSleep, Toast.LENGTH_SHORT).show();
        setAlarm();
        contextPreferenceManager.setAlarmContext(true);
        //stopSelf();
        //return START_NOT_STICKY;
    }

    private void setAlarm(){
        Log.d(LOG_TAG,"setAlarm");
        Calendar calender = Calendar.getInstance();

        calender.set(Calendar.HOUR_OF_DAY, timeHour);
        calender.set(Calendar.MINUTE, timeMinute);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    private class AlarmReceiver extends WakefulBroadcastReceiver {
        static final String LOG_TAG = "SetAlarmAReceiver::";

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.d(LOG_TAG, "onReceive");
            textView.setText("Alarm Ringing!!!! Wake up!!!!");
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(context, uri);
            ringtone.play();
        }
    }
}
