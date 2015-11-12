package com.prac.baek.runtracker;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by BAEK on 7/22/2015.
 */
public class RunTrackerFragment extends Fragment {

    private static final String TAG = "RunTrackerFragment";
    private static final String ARG_RUN_ID = "RUN_ID";


    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {

        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
            if (isVisible())
                updateUI();
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };

    private Location mLastLocation;
    private Run mRun;
    private Button mStartButton, mStopButton;
    private TextView mStartedTextView, mLatitudeTextView, mLongitudeTextView, mAltitudeTextView,
            mDurationTextView;
    private RunTrackerManager mRunTrackerManager;

    public static RunTrackerFragment newInstance(long runId){
        Bundle args = new Bundle();
        args.putLong(ARG_RUN_ID, runId);
        RunTrackerFragment rf = new RunTrackerFragment();
        rf.setArguments(args);
        return rf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunTrackerManager = mRunTrackerManager.get(getActivity());

        // check for a Run ID as an argument, and find the run
        Bundle args = getArguments();
        if (args != null) {
            long runId = args.getLong(ARG_RUN_ID, -1);
            if (runId != -1) {
                mRun = mRunTrackerManager.getRun(runId);
                mLastLocation = mRunTrackerManager.getLastLocationForRun(runId);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View view = inflater.inflate(R.layout.run_tracker_fragment, container, false);

        mStartedTextView = (TextView) view.findViewById(R.id.run_startedTextView);
        mLatitudeTextView = (TextView) view.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView) view.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView) view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView) view.findViewById(R.id.run_durationTextView);
        mStartButton = (Button) view.findViewById(R.id.run_startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRun == null) {
                    mRun = mRunTrackerManager.startNewRun();
                } else {
                    mRunTrackerManager.startTrackingRun(mRun);
                }
                updateUI();
            }
        });
        mStopButton = (Button) view.findViewById(R.id.run_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunTrackerManager.stopRun();
                Log.d(TAG, " stop button was clicked");
                updateUI();
            }
        });
        updateUI();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        try {
            getActivity().registerReceiver(mLocationReceiver,
                    new IntentFilter(RunTrackerManager.ACTION_LOCATION));
        }catch (Exception e){
            Log.e(TAG,"register receiver error!");
        }
    }

    @Override
    public void onStop(){
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }


    private void updateUI(){
        boolean started = mRunTrackerManager.isTrackingRun();

        if(mRun != null){
            mStartedTextView.setText(mRun.getStartDate().toString());
            Log.d(TAG,"mrun was not null");
        }
        int durationSeconds = 0;
        if(mLastLocation != null){
            Log.d(TAG,"mLastLocation was not null");
            durationSeconds = mRun.getDurationSeconds(mLastLocation.getTime());
            mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
        }
        mDurationTextView.setText(Run.formatDuration(durationSeconds));

        mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);
    }
}
