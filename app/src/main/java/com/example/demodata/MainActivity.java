package com.example.demodata;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "Yooooo";
    JSONArray JA;
    private static final int JOB_ID = 101;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    List<String> data = new ArrayList<String>();
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        boolean firstStart = pref.getBoolean("firstStart",true);
        if(firstStart) {
            onFirstStart();
        }

        else {
            Log.v(TAG,"Called after 1st time");
            FacultyDbHelper facultyDbHelper = new FacultyDbHelper(this);
            Cursor cursor = facultyDbHelper.getNames();
            if(cursor.getCount() !=0) {
                while(cursor.moveToNext()) {
//                Log.v(TAG,cu.getString(0));
                    data.add(cursor.getString(0));
                }
            }
            for(int i = 0;i < data.size();i++) {
                Log.v(TAG, String.valueOf(data.get(i)));
            }
        }
    }

    private void onFirstStart() {
        final DownloadTask downloadTask = new DownloadTask(this);
        Log.v(TAG,"Called 1st time");
        String faculty="";
        try {
            data = downloadTask.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v(TAG,faculty);
        for(int i = 0;i < data.size();i++) {
            Log.v(TAG, String.valueOf(data.get(i)));
        }

        ComponentName componentName = new ComponentName(this,FacultyJobScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,componentName);
        builder.setPeriodic(60000);
        builder.setPersisted(true);
        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
}
