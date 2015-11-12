package com.sh.baek.shootingheart.Heart;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by BAEK on 7/30/2015.
 * Singleton class needed to save Bitmap data along the lifecycle.
 */

public class BitmapLab {
    private static BitmapLab mBitmapLab = null;
    private Bitmap mBitmap;
    private Paint mPaint;
    protected BitmapLab(){
        // only to defeat the instantiation
    }
    public static BitmapLab getInstance(){
        if(mBitmapLab == null){
            mBitmapLab = new BitmapLab();
        }
        return mBitmapLab;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }
}
