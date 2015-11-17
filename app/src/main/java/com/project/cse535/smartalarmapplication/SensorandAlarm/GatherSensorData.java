package com.project.cse535.smartalarmapplication.SensorandAlarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.project.cse535.smartalarmapplication.datastorage.ContextPreferenceManager;

public class GatherSensorData extends Service implements SensorEventListener{

    private static final String LOG_TAG = "GatherSensorData::";
    public static final String ACTION = "com.project.cse535.smartalarmapplication.SensorandAlarm.GatherSensorData";

    int count=0;

    public GatherSensorData() {
    }

    private SensorManager sensorManager;    // this instance of SensorManager class will be used to get a reference to the sensor service.
    private Sensor mSensor,lSensor;         // this instance of Sensor class is used to get the sensor we want to use.
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    float l;
    private float mAccel_avg;
    private float light_avg;

    Intent in = new Intent(ACTION); // create an intent to broadcast.
    Bundle bundle = new Bundle();

    ContextPreferenceManager contextprefs ;
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    //    // if sensor value is changes, change the values in the respective textview.
    public void onSensorChanged(SensorEvent event){
//        Log.d(LOG_TAG, "onSensorChanged.");

//        in.putExtra("resultCode",Activity.RESULT_OK);
        /* check sensor type */
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
//            Log.d(LOG_TAG, "onSensorChanged,accelerometer");
            count+=1;
//            Log.d(LOG_TAG,"count :"+count);

            mGravity = event.values.clone();
            // assign directions
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];

            float x1=event.values[0];
            float y1=event.values[1];
            float z1=event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);      // we calculate the length of the event because these values are independent of the co-ordinate system.
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel*0.9f + delta;

            mAccel_avg +=mAccel;
//            if(mAccel >= 0)
//            {
//                mAccel_avg +=mAccel;
//                bundle.putFloat("accelVal", mAccel);
////                in.putExtra("accelVal",Float.toString(mAccel));
//            }
        }
        if(event.sensor.getType()==Sensor.TYPE_LIGHT)
        {
            l = event.values[0];
            light_avg += l;
//            bundle.putFloat("lightVal", l);
//            in.putExtra("lightVal",Float.toString(l));
        }

//        Log.d(LOG_TAG, "mAccel : " + Float.toString(mAccel));
//        Log.d(LOG_TAG,"lightsensor : "+Float.toString(l));

        if(count == 50) {
            mAccel_avg = mAccel_avg/50;
            light_avg = light_avg/50;
            Log.d(LOG_TAG, "mAccel_avg : " + Float.toString(mAccel_avg));
            Log.d(LOG_TAG,"lightsensor_avg : "+Float.toString(light_avg));
//            bundle.putFloat("accelVal", mAccel_avg);
//            bundle.putFloat("lightVal", light_avg);
//
//            in.putExtras(bundle);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(in);
            if(mAccel_avg < 0.5){
                contextprefs.setContextPrefs(ContextPreferenceManager.MOTION_CONTEXT_KEY, true);
//                Log.d(LOG_TAG,"motionsensor_avg_set : true");
            }
            else {
                contextprefs.setContextPrefs(ContextPreferenceManager.MOTION_CONTEXT_KEY, false);
//                Log.d(LOG_TAG, "motionsensor_avg_set : false");
            }

            if(light_avg <3) {
//                Log.d(LOG_TAG,"lightsensor_avg_set : true");
                contextprefs.setContextPrefs(ContextPreferenceManager.ILLUMINATION_CONTEXT_KEY, true);
            }
            else {
//                Log.d(LOG_TAG,"lightsensor_avg_set : false");
                contextprefs.setContextPrefs(ContextPreferenceManager.ILLUMINATION_CONTEXT_KEY, false);
            }
            stopSelf();
        }

    }


    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        Log.d("MyService", "onCreate");
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);          // get an instance of the SensorManager class, lets us access sensors.
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    // get Accelerometer sensor from the list of sensors.
        lSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);            // get light sensor from the list of sensors.
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        contextprefs = ContextPreferenceManager.getInstance();

    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("MyService", "onStartCommand");

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.d( LOG_TAG, "onDestroy" );
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}