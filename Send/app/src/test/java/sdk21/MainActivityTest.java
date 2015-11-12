package sdk21;

import android.util.Log;

import com.image.send.BuildConfig;
import com.image.send.MainActivity;
import com.image.send.login.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by BAEK on 9/3/2015.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private static final String TAG = "MAINTEST";
    private  MainActivity activity;

    @Before
    public void setUp(){
        ShadowLog.stream = System.out;
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void logTest(){
        ShadowLog.d(TAG, activity.databaseList()[0]);
    }

}
