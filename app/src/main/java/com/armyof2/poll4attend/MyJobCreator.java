package com.armyof2.poll4attend;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class MyJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case PieChartActivity.TAG:
                return new PieChartActivity();
            default:
                return null;
        }
    }
}
