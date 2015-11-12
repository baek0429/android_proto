package com.sh.baek.shootingheart.tests;

import com.sh.baek.shootingheart.BuildConfig;
import com.sh.baek.shootingheart.FriendsListActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by BAEK on 8/1/2015.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FriendsListActivityTest {
    @Test
    public void friendsListNotNull(){
        FriendsListActivity mActivity = Robolectric.setupActivity(FriendsListActivity.class);
        mActivity.getActionBar();
    }
}
