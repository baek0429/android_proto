package com.prac.baek.runtracker;

import android.app.Fragment;

/**
 * Created by BAEK on 7/23/2015.
 */
public class RunListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new RunListFragment();
    }
}
