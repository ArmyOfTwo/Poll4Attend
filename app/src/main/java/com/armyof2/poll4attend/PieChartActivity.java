package com.armyof2.poll4attend;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.ArrayList;

import static com.armyof2.poll4attend.MainActivity.BUNK_NO;
import static com.armyof2.poll4attend.MainActivity.BUNK_UNDEC;
import static com.armyof2.poll4attend.MainActivity.BUNK_YES;
import static com.armyof2.poll4attend.MainActivity.BUNK_YES80;
import static com.armyof2.poll4attend.MainActivity.addDataSet;
import static com.armyof2.poll4attend.MainActivity.i;
import static com.armyof2.poll4attend.MainActivity.j;
import static com.armyof2.poll4attend.MainActivity.k;
import static com.armyof2.poll4attend.MainActivity.l;
import static com.armyof2.poll4attend.MainActivity.pieChart;
import static com.armyof2.poll4attend.MainActivity.x;
import static com.armyof2.poll4attend.MainActivity.yData;


public class PieChartActivity extends Job {

    String yes = i;
    String no = j;
    String undec = l;
    String yes80 = k;
    ArrayList<String> bunkingYes = BUNK_YES;
    ArrayList<String> bunkingNo = BUNK_NO;
    ArrayList<String> bunkingYes80 = BUNK_YES80;
    ArrayList<String> bunkingUndec = BUNK_UNDEC;
    public static final String TAG = "my_job_tag";
    Intent intent;

    //pie chart references/variables



    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        if(x <= 40) {
            //update pie chart values
            Log.d("TAG", "Pie Chart Values Updated");

            yData = new int[]{Integer.parseInt(i), Integer.parseInt(j), Integer.parseInt(k), Integer.parseInt(l)};
            addDataSet(pieChart, yData);

            scheduleJob();
            x++;
        }
            return Result.SUCCESS;
    }

    public static void scheduleJob() {
        new JobRequest.Builder(PieChartActivity.TAG)
                .setExecutionWindow(2_000L, 40_000L) //Every 2 seconds for 40 seconds
                .startNow()
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }
}
