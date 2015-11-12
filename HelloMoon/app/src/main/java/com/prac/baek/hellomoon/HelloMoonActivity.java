package com.prac.baek.hellomoon;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


public class HelloMoonActivity extends FragmentActivity implements HelloMoonFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_moon);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //blank
    }
}
