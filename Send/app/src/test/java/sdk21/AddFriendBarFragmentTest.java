/*
 * Copyright (c) 2015. <Chungseok Baek>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package sdk21;

import android.widget.EditText;

import com.image.send.BuildConfig;
import com.image.send.FriendAddActivity;
import com.image.send.R;
import com.image.send.friend.subfragment.FriendAddBarFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.FragmentTestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AddFriendBarFragmentTest {
    @Before
    public void setup() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        FriendAddBarFragment fragment = new FriendAddBarFragment();
        FragmentTestUtil.startFragment(fragment, FriendAddActivity.class);
        assertNotNull(fragment);
    }


    // I do not know how to create appropriate test case for the icon button clicked...
    @Test
    public void addButtonClicked() {
        FriendAddBarFragment fragment = new FriendAddBarFragment();
        FragmentTestUtil.startFragment(fragment, FriendAddActivity.class);
        EditText v = (EditText) fragment.getView().findViewById(R.id.friend_add_bar);
    }
}