package com.prac.baek.runtracker;

import android.util.Log;

import java.util.Date;

/**
 * Created by BAEK on 7/22/2015.
 */
public class Run {
    private Date mStartDate;

    private long mId;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public Run(){
        mStartDate = new Date();
        mId = -1;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public int getDurationSeconds(long endMillis) {
        Log.d("Run", Integer.toString(((int)((endMillis - mStartDate.getTime()) / 1000))));
        return (int)((endMillis - mStartDate.getTime()) / 1000);
    }

    public static String formatDuration(int durationSeconds) {
        int seconds = durationSeconds % 60;
        int minutes = ((durationSeconds - seconds) / 60) % 60;
        int hours = (durationSeconds - (minutes * 60) - seconds) / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
