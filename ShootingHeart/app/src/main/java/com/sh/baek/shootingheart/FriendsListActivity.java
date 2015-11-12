package com.sh.baek.shootingheart;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by BAEK on 7/30/2015.
 */
public class FriendsListActivity extends SingleFragmentActivity implements FriendsListFragment.OnFragmentInteractionListener{

    private static final String TAG = "shootingHeart.FriendsListActivity";

    @Override
    protected Fragment createFragment() {
        return new FriendsListFragment();
    }

    @Override
    protected void settingUI() {
        isActionBar = true;
        isStatusBar = true;
    }

    @Override
    public void onFragmentInteraction(String id) {
    }
}
