package com.sh.baek.shootingheart.tests;

import android.content.Intent;
import android.os.Build;

import com.sh.baek.shootingheart.BuildConfig;
import com.sh.baek.shootingheart.FriendsListActivity;
import com.sh.baek.shootingheart.MyHeartActivity;
import com.sh.baek.shootingheart.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by BAEK on 8/1/2015.
*/

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MyHeartActivityTest  {
    @Test
    public void clickBtn_shouldStartFriendsListActivity(){
        MyHeartActivity mActivity = Robolectric.setupActivity(MyHeartActivity.class);
        mActivity.findViewById(R.id.btn_friendsListFragment).performClick();

        Intent expectedIntent = new Intent(mActivity, FriendsListActivity.class);
        assertThat(shadowOf(mActivity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}
