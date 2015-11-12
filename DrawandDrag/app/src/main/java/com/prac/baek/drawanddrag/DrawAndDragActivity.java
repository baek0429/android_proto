package com.prac.baek.drawanddrag;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class DrawAndDragActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DrawAndDragFragment();
    }
}
