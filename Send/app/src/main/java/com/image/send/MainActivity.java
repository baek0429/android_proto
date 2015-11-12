package com.image.send;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import com.image.send.account.SettingsFragment;
import com.image.send.camera.CameraFragment;
import com.image.send.friend.FriendsListFragment;
import com.image.send.helper.SQLiteHandler;
import com.image.send.helper.SessionManager;
import com.image.send.login.LoginActivity;
import com.image.send.mainboard.ImageBoardFragment;

import java.util.HashMap;

public class MainActivity extends Activity implements FriendsListFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, ImageBoardFragment.OnFragmentInteractionListener, CameraFragment.OnFragmentInteractionListener{

    private static String TAG = "MainActivity";
    private SessionManager mSessionManager;
    private SQLiteHandler db;
    private ViewPager mViewPager;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get local DB;
        db = new SQLiteHandler(getApplicationContext());
        //get session manager to modify shared preference
        mSessionManager = new SessionManager(getApplicationContext());
        //return to the login activity
        if (!mSessionManager.isLoggedIn()) {
            logoutUser();
        }

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // parse friend list from the local db.
        db.parseFriends();

        //ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));

        // "pos" as a argument, default page setting.
        if(getIntent().hasExtra("pos")){
            int pos = getIntent().getExtras().getInt("pos");
            Log.d(TAG,""+pos);
            mViewPager.setCurrentItem(pos);
        }

    }

    /**
     * abstract function of FriendListFragment implemented in the main activity.
     * parse friends from the remote db and save it in the local db.
     */

    /**
     * add friend button clicked from the fragment.
     */
    @Override
    public void onAddFriendButtonClicked(Context context) {
        // TODO: create an activity that search friend and add friend.
        Intent intent = new Intent(context,FriendAddActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * simple function to deliver the logout request from the fragment to the main activity function
     */
    @Override
    public void onLogOutButtonClicked() {
        logoutUser();
    }


    /**
     * get UserDetails of the application user.
     * @return applicationUser HashMap
     */
    @Override
    public HashMap<String, String> getUserDP() {
        return db.getUserDetails();
    }

    @Override
    public void setCurrentItem(int pos) {
        mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }


    /**
     * MyPageAdapter chooses which fragment the main activity shows.
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return new SettingsFragment();
                case 1:
                    return new CameraFragment();
                case 3:
                    return new ImageBoardFragment();
                case 2:
                    return new FriendsListFragment();
                default:
                    return new CameraFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    /**
     * logout and delete the user session from the local db.
     * Friend Content reset.
     */
    private void logoutUser() {
        mSessionManager.setLogin(false);

        // delete from db
        db.clearUsers();

        // delete friends list from local db
        db.clearFriends();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
