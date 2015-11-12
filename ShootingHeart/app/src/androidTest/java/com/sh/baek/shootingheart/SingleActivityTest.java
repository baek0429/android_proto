package com.sh.baek.shootingheart;

import android.app.Activity;
import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by BAEK on 7/31/2015.
 * It tests whether there exists activity and fragment in the extending classes.
 * setUp() assign each element to protected variables, which can be accessed from extending classes.
 */
public class SingleActivityTest<T extends SingleFragmentActivity> extends ActivityInstrumentationTestCase2<T> {

    protected T mActivity = null;
    protected Fragment mFragment = null;

    public SingleActivityTest(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        mActivity = getActivity();
        mFragment = mActivity.createFragment();
    }

    public void testGetActivity(){
        assertNotNull(mActivity);
    }

    public void testCreateFragment(){
        assertNotNull(mFragment);
    }
}
