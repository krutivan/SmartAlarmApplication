package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.project.cse535.smartalarmapplication.MainActivity;
import com.project.cse535.smartalarmapplication.datastorage.ContextPreferenceManager;
import com.project.cse535.smartalarmapplication.datastorage.SleepCycleManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class SetAlarmService extends Service {
    public SetAlarmService() {
    }
    private static final String LOG_TAG = "SetAlarmService::";
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    AlarmManager alarmManager;
    ContextPreferenceManager contextPreferenceManager;
    private PendingIntent pendingIntent;
    Long time;
    SleepCycleManager sleepHistory;

    public void onCreate(){
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        sleepHistory = SleepCycleManager.getInstance();
        contextPreferenceManager = ContextPreferenceManager.getInstance();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
/*
* Get calender events.
* */
        // determine which fields we want in our events,
        String[] EVENT_PROJECTION = new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Instances.BEGIN, CalendarContract.Instances.END, CalendarContract.Events.ALL_DAY};

        // retrieve the ContentResolver
        ContentResolver resolver = getContentResolver();

        // use the uri given by Instances, but in a way that we can add information to the URI
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        long beginTime = new GregorianCalendar().getTimeInMillis() + 60*1000;
        long endTime = new GregorianCalendar().getTimeInMillis() + 24*60*60*1000;

        // add the begin and end times to the URI to use these to limit the list to events between them
        ContentUris.appendId(eventsUriBuilder, beginTime);
        ContentUris.appendId(eventsUriBuilder, endTime);

        // build the finished URI
        Uri eventUri = eventsUriBuilder.build();

        // filter the selection, like before
        String selection = "((" + CalendarContract.Events.CALENDAR_ID + " = ?))";
        String[] selectionArgs = new String[]{""};
//        String[] selectionArgs = new String[]{"" + cal.getCalId()};

        // resolve the query, this time also including a sort option
        Cursor eventCursor = resolver.query(eventUri, EVENT_PROJECTION, null, null, CalendarContract.Instances.BEGIN + " ASC");
        long currentTime = new GregorianCalendar().getTimeInMillis();

        long min = Long.MAX_VALUE;

        Log.d("calenderEvents::count", "" + eventCursor.getCount());
        if(eventCursor.getCount() >=1){
            while(eventCursor.moveToNext()){
                Log.d("calenderEvents::Title",""+eventCursor.getString(0));    // title
                Log.d("calenderEvents::Start",""+eventCursor.getLong(2));      // start
                Log.d("calenderEvents::End",""+eventCursor.getLong(3));      // time

                if(eventCursor.getLong(2) < min && currentTime < eventCursor.getLong(2))
                    min = eventCursor.getLong(2);
                long time = System.currentTimeMillis();

//                Date date = new Date(eventCursor.getLong(2));
//                Log.d("date",""+date);
            }
        }
/*
* End of calender events.
* */
        Log.d(LOG_TAG,"OnStartCommand");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(SetAlarmService.this, SetAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetAlarmService.this, 0, myIntent, 0);

        int balance_sleeptime = sleepHistory.getBalanceHours();
        int balance_sleepdays = sleepHistory.getBalanceDays();

        int targetSleep = balance_sleeptime/balance_sleepdays;
        Date date = new Date(min);
//        int minHours = (int)(min/(60 * 60 * 1000)); //converting min to hours
        int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(min-currentTime);
        if(targetSleep > minutes)
            targetSleep = minutes;

        time = new GregorianCalendar().getTimeInMillis()+targetSleep*60*1000;
        Date alarmDate = new Date(time);
        Log.d(LOG_TAG,"Alarm set for : "+alarmDate);

        sleepHistory.setBalanceDays(balance_sleepdays - 1);
        sleepHistory.setBalanceHours(balance_sleeptime - targetSleep);

        Log.d(LOG_TAG, targetSleep + "mins");
        Toast.makeText(SetAlarmService.this, "Today's sleep hours : "+targetSleep, Toast.LENGTH_SHORT).show();
        setAlarm();
        contextPreferenceManager.setAlarmContext(true);



        stopSelf();
        return START_NOT_STICKY;
    }

    private void setAlarm(){
        Log.d(LOG_TAG, "setAlarm");
        Calendar calender = Calendar.getInstance();

        calender.set(Calendar.HOUR_OF_DAY, timeHour);
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
