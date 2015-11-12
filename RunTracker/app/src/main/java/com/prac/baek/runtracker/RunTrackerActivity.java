package com.prac.baek.runtracker;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RunTrackerActivity extends SingleFragmentActivity {

    public static final String EXTRA_RUN_ID = "RUN_ID";

    @Override
    protected Fragment createFragment() {
        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if (runId != -1) {
            return RunTrackerFragment.newInstance(runId);
        } else {
            return new RunTrackerFragment();
        }
    }
}