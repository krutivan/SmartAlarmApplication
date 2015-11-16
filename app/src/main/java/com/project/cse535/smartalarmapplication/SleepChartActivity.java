package com.project.cse535.smartalarmapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.cse535.smartalarmapplication.datastorage.SleepCycleManager;

import java.util.ArrayList;

public class SleepChartActivity extends AppCompatActivity {

    BarDataSet dataset;
    ArrayList<String> labels;
    SleepCycleManager sleepHistory;

    protected void createDataSet(){
        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.MON), 0));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.TUE), 1));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.WED), 2));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.THU), 3));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.FRI), 4));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.SAT), 5));
        entries.add(new BarEntry(sleepHistory.getHours(SleepCycleManager.SUN), 6));
        dataset = new BarDataSet(entries, "Day of week");
        dataset.setColors(ColorTemplate.LIBERTY_COLORS);
        labels = new ArrayList<String>();

        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thu");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sleepHistory = new SleepCycleManager(this);
        sleepHistory.initRandomHistory();
        createDataSet();
        setContentView(R.layout.activity_sleep_chart);
        BarChart chart = (BarChart)findViewById(R.id.chart1);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("Hours slept");
        chart.animateY(2000);

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
}
