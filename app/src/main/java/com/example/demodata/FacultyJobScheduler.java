package com.example.demodata;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FacultyJobScheduler extends JobService {
    final static String TAG = "Job Scheduler";
    private DownloadTask downloadTask;
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.v(TAG,"Called from Scheduler");
        downloadTask = new DownloadTask(this) {
            @Override
            protected void onPreExecute() {
                jobFinished(params,false);
            }
        };
        try {
            final ArrayList arrayList = downloadTask.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        downloadTask.cancel(true);
        return true;
    }
}
