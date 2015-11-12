package com.sh.baek.shootingheart;

import android.app.Fragment;
import android.util.Log;


public class MyHeartActivity extends SingleFragmentActivity {
    private static final String TAG = "MyHeartActivity";

    public Fragment createFragment(){
        Log.d(TAG, "-----------------------------------------------------------------------");
        Log.d(TAG, "createFragment was called");
        return new MyHeartFragment();
    }

    @Override
    protected void settingUI() {
        isActionBar = false;
        isStatusBar = true;
    }
}