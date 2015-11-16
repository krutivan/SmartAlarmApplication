package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SetAlarmService extends Service {
    public SetAlarmService() {
    }
    private static final String LOG_TAG = "SetAlarmService::";
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Long time;

    public void onCreate(){
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG,"OnStartCommand");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(SetAlarmService.this, SetAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetAlarmService.this, 0, myIntent, 0);


        time = new GregorianCalendar().getTimeInMillis()+2*60*1000;
        Toast.makeText(SetAlarmService.this, "Setting alarm for " + time, Toast.LENGTH_SHORT).show();

        setAlarm();

        stopSelf();
        return START_NOT_STICKY;
    }

    private void setAlarm(){
        Log.d(LOG_TAG,"setAlarm");
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY,timeHour);
        calender.set(Calendar.MINUTE, timeMinute);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
