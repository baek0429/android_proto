package com.sh.baek.shootingheart;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageButton;

/**
 * Created by BAEK on 7/31/2015.
 */
public class MyHeartActivityTest extends SingleActivityTest<MyHeartActivity> {

    public MyHeartActivityTest(){
        super(MyHeartActivity.class);
    }

    private ImageButton mImageButton;

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        setActivityInitialTouchMode(true);
        mImageButton = (ImageButton) mFragment.getActivity().findViewById(R.id.btn_friendsListFragment);
    }

    public void testUIItemIsNotNull (){
        assertNotNull(mImageButton);
    }

}
