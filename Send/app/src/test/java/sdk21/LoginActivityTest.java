package sdk21;

import android.content.Intent;
import com.image.send.BuildConfig;
import com.image.send.R;
import com.image.send.login.LoginActivity;
import com.image.send.login.RegisterActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by BAEK on 9/3/2015.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginActivityTest {

    @Test
    public void clicking_Link_To_Register_Screen() {
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.findViewById(R.id.btnLinkToRegisterScreen).performClick();

        Intent expectedIntent = new Intent(activity, RegisterActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}