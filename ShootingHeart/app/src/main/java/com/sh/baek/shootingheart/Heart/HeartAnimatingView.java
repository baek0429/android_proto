package com.sh.baek.shootingheart.Heart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by BAEK on 7/30/2015.
 */
public class HeartAnimatingView extends View {

    private static final String TAG = "HeartAnimatingView";
    private BitmapLab mBitmapLab;
    private static int sParentWidth;
    private static int sParentHeight;
    private Canvas mCanvas;

    public HeartAnimatingView(Context context) {
        super(context);
    }

    public HeartAnimatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmapLab = BitmapLab.getInstance();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        sParentWidth = MeasureSpec.getSize(widthMeasureSpec);
        sParentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(sParentWidth, sParentHeight);

        mBitmapLab.setBitmap(Bitmap.createBitmap(sParentWidth, sParentHeight, Bitmap.Config.ARGB_8888));
//        mBitmap = Bitmap.createBitmap(sParentWidth, sParentHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapLab.getBitmap());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) throw new NullPointerException("Canvas is null");
        super.onDraw(canvas);
        Log.d(TAG, "onDraw was called");

        canvas.drawBitmap(mBitmapLab.getBitmap(), 0, 0, mBitmapLab.getPaint());
    }
}
