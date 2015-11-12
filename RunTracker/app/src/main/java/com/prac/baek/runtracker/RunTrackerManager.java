package com.prac.baek.runtracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by BAEK on 7/22/2015.
 */
public class RunTrackerManager {
    private static final String TAG = "RunTrackerManager";
    private static final String PREFS_FILE ="runs";
    private static final String PREF_CURRENT_RUN_ID = "RUnTracker.currentRunId";

    public static final String ACTION_LOCATION =
            "com.prac.baek.runtracker.ACTION_LOCATION";

    private static final String TEST_PROVIDER = "TEST_PROVIDER";

    private static RunTrackerManager sRunTrackerManager;
    private Context mAppContext;
    private LocationManager mLocationManager;

    private RunTrackerDBHelper mHelper;
    private SharedPreferences mPrefs;
    private long mCurrentRunId;


    private RunTrackerManager(Context appContext){
        mAppContext = appContext;
        mLocationManager = (LocationManager) mAppContext.getSystemService(Context.LOCATION_SERVICE);
        mHelper = new RunTrackerDBHelper(mAppContext);
        mPrefs = mAppContext.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        mCurrentRunId = mPrefs.getLong(PREF_CURRENT_RUN_ID, -1);
    }

    public static RunTrackerManager get (Context c){
        if(sRunTrackerManager == null){
            sRunTrackerManager = new RunTrackerManager(c.getApplicationContext());
        }
        return sRunTrackerManager;
    }

    private PendingIntent getLocationPendingIntent(boolean shouldCreate){
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(mAppContext,0,broadcast,flags);
    }

    public void startLocationUpdates() {
        String provider = LocationManager.GPS_PROVIDER;
        // if we have the test provider and it's enabled, use it
        if(mLocationManager.getProvider(TEST_PROVIDER) != null && mLocationManager.isProviderEnabled(TEST_PROVIDER))
            provider = TEST_PROVIDER;
        Log.d(TAG, "Using provider " + provider);
        // get the last known location and broadcast it if we have one
        Location lastKnown = mLocationManager.getLastKnownLocation(provider);
        if (lastKnown != null) {
            // reset the time to now
            lastKnown.setTime(System.currentTimeMillis());
            broadcastLocation(lastKnown);
        }

        // start updates from the location manager
        PendingIntent pi = getLocationPendingIntent(true);
        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
    }

    public void stopLocationUpdates() {
        PendingIntent pi = getLocationPendingIntent(false);
        if (pi != null) {
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    private void broadcastLocation(Location location) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
        mAppContext.sendBroadcast(broadcast);
    }

    public boolean isTrackingRun(){
        return getLocationPendingIntent(false) != null;
    }

    public Run startNewRun(){
        Run run = insertRun();
        startTrackingRun(run);
        return run;
    }

    public void startTrackingRun(Run run) {
    // Keep the ID
        mCurrentRunId = run.getId();
    // Store it in shared preferences
        mPrefs.edit().putLong(PREF_CURRENT_RUN_ID, mCurrentRunId).commit();
    // Start location updates
        startLocationUpdates();
    }
    public void stopRun() {
        stopLocationUpdates();
        mCurrentRunId = -1;
        mPrefs.edit().remove(PREF_CURRENT_RUN_ID).commit();
    }
    private Run insertRun() {
        Run run = new Run();
        run.setId(mHelper.insertRun(run));
        return run;
    }

    public void insertLocation(Location loc) {
        if (mCurrentRunId != -1) {
            mHelper.insertLocation(mCurrentRunId, loc);
        } else {
            Log.e(TAG, "Location received with no tracking run; ignoring.");
        }
    }

    public RunTrackerDBHelper.RunCursor queryRuns(){
        return mHelper.queryRuns();
    }

    public Run getRun(long id) {
        Run run = null;
        RunTrackerDBHelper.RunCursor cursor = mHelper.queryRun(id);
        cursor.moveToFirst();
        // if we got a row, get a run
        if (!cursor.isAfterLast())
            run = cursor.getRun();
        cursor.close();
        return run;
    }

    public Location getLastLocationForRun(long runId) {
        Location location = null;
        RunTrackerDBHelper.LocationCursor cursor = mHelper.queryLastLocationForRun(runId);
        cursor.moveToFirst();
        // if we got a row, get a location
        if (!cursor.isAfterLast())
            location = cursor.getLocation();
        cursor.close();
        return location;
    }

}
