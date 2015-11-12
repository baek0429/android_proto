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

import com.image.send.BuildConfig;
import com.image.send.MainActivity;
import com.image.send.friend.model.FriendContent;
import com.image.send.friend.FriendsListFragment;

import org.json.JSONException;
import org.json.JSONObject;
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
public class FriendsListFragmentTest {

    @Before
    public void setup() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        FriendsListFragment fragment = new FriendsListFragment();
        FragmentTestUtil.startFragment(fragment, MainActivity.class);
        assertNotNull(fragment);
    }

    @Test
    public void jsonTest() throws JSONException {
        JSONObject obj = null;

        try {
            obj = new JSONObject("{success: true, friend1: { name: hello }, friend2: {name: hello2}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int i = 1;
        if (obj != null) {
            while (obj.has("friend"+i)) {
                JSONObject fObj = obj.getJSONObject("friend"+i);
                String f_name = fObj.getString("name");
                FriendContent.addFriendInstance(f_name);
                i++;
            }
        }

        assertEquals(i-1,FriendContent.ITEMS.size());
    }
}
