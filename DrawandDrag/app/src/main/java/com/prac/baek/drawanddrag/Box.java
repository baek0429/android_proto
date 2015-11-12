package com.prac.baek.drawanddrag;

import android.graphics.PointF;

/**
 * Created by BAEK on 7/21/2015.
 */
public class Box {
    private PointF mOrigin;
    private PointF mCurrent;

    public Box(PointF origin){
        mOrigin = mCurrent = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getCurrent() {
        return mCurrent;
    }
}
