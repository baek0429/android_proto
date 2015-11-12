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

package com.image.send.friend.model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.image.send.R;
import com.image.send.friend.model.FriendContent;
import com.image.send.helper.SQLiteHandler;

/**
 * add friend bar view model that can be added to anywhere (with functionality)
 * Created by BAEK on 9/9/2015.
 */
public class AddFriendBarView extends EditText {
    private static final String TAG = "AddFriendBarV";

    public AddFriendBarView(Context context) {
        this(context, null);
    }

    public AddFriendBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setSingleLine(true);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (getRight() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String f_name = getText().toString();
                        Toast.makeText(getContext(), f_name + " " + getContext().getResources().getString(R.string.toast_add_friend) ,Toast.LENGTH_SHORT).show();

                        // add friend to local db.
                        SQLiteHandler db = new SQLiteHandler(getContext());
                        db.addFriend(f_name);
                        db.close();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
