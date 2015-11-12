package com.sh.baek.shootingheart.Heart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BAEK on 7/29/2015.
 */
public class HeartDrawingView extends View {

    private static final String TAG = "Heart.HeartDrawingView";

    private Canvas mCanvas;
    private static int sParentWidth;
    private static int sParentHeight;

    private Paint mPathPaint;
    private Paint mBackgroundPaint;

    private Path mPath;

    private BitmapLab mBitmapLab;

//    private Paint mBitmapPaint;
//    private Bitmap mBitmap;

    public HeartDrawingView(Context context) {
        super(context);
    }

    public HeartDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.d(TAG, "HeartDrawingView was created with constructor");

        mPathPaint = new Paint();
        mPathPaint.setColor(0x22ff0000);
        mPathPaint.setDither(true);
        mPathPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(20);


        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
        mPath = new Path();

        mBitmapLab = BitmapLab.getInstance();
//        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        sParentWidth = MeasureSpec.getSize(widthMeasureSpec);
        sParentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(sParentWidth, sParentHeight);

        //only to defeat unittest failure
        if (sParentWidth <= 0)
            sParentWidth = 1;
        if (sParentHeight <= 0)
            sParentHeight = 1;

        mBitmapLab.setBitmap(Bitmap.createBitmap(sParentWidth, sParentHeight, Bitmap.Config.ARGB_8888));
//        mBitmap = Bitmap.createBitmap(sParentWidth, sParentHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapLab.getBitmap());

    }

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas == null) throw new NullPointerException("Canvas is null");
        super.onDraw(canvas);
        Log.d(TAG, "onDraw was called");

        canvas.drawBitmap(mBitmapLab.getBitmap(), 0, 0, mBitmapLab.getPaint());
        canvas.drawPath(mPath,mPathPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        PointF curr= new PointF(event.getX(),event.getY());

        Log.i(TAG, "Received event at x ="
                 + curr.x +", y="+ curr.y +":");
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, " ACTION_DOWN");
                touchStart(curr.x,curr.y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, " ACTION_MOVE");
                touchMove(curr.x,curr.y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, " ACTION_UP");
                touchUp();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, " ACTION_CANCEL");
                break;
        }
        return true;
    }

    private float mX;
    private float mY;

    private static final float TOUCH_TOLERANCE = 4;

    private void touchStart(float x, float y){
        mPath.reset();
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX,mY, (x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp(){
        mPath.lineTo(mX,mY);
        mCanvas.drawPath(mPath, mPathPaint);
        mPath.reset();
    }
}
