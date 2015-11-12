package com.prac.baek.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by BAEK on 7/23/2015.
 */
public class TrackingLocationReceiver extends LocationReceiver{
    @Override
    protected void onLocationReceived(Context c, Location loc){
        RunTrackerManager.get(c).insertLocation(loc);
    }
}
