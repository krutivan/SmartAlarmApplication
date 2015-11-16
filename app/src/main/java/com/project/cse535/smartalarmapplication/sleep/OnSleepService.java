package com.project.cse535.smartalarmapplication.sleep;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;

public class OnSleepService extends Service {
    private class SleepAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
//            Uri uri = Uri.parse("content://com.android.deskclock /alarm");
//            ContentResolver r = getContentResolver();
//            Cursor c = r.query(uri, null, null, null, null);
            return null;
        }
    }
    public OnSleepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public void onCreate(){
        SleepAsyncTask sleepTask = new SleepAsyncTask();
        sleepTask.execute();
    }
}
