package com.liuzhaoliang.hencoder6;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by liuzhaoliang on 2018/7/29.
 */

public class ScalableImage2 extends View
        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private static final float BITMAP_SIAZE = Utils.dip2px(150);
    private static final int SCALE_OVER_FACTOR = 2;
    private Bitmap bitmap;
    private Paint paint;

    private float offsetX;
    private float offsetY;
    private int oldOffsetX;
    private int oldOffsetY;
    private float imageWidth;
    private float imageHeight;
    private float smallScale;
    private float bigScale;
    private GestureDetector gestureDetector;
    private boolean isBig;
//    private float scaling;
    private ObjectAnimator objectAnimator;
    private OverScroller overScroller;
    private float currentScale;
    private ScaleGestureDetector scaleGestureDetector;
    private float oldCurrentScale;

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    public ScalableImage2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    {
        bitmap = Utils.getBitmap(getResources(), (int) BITMAP_SIAZE);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);

        overScroller = new OverScroller(context);
        scaleGestureDetector = new ScaleGestureDetector(context,new MyScalListener());
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        oldOffsetX = (int) ((getWidth() - imageWidth) / 2);
        oldOffsetY = (int) ((getHeight() - imageHeight) / 2);
        if (imageWidth / imageHeight > (float) getWidth() / getHeight()) {
            smallScale = getWidth() / imageWidth;
            bigScale = getHeight() / imageHeight * SCALE_OVER_FACTOR;

        } else {
            smallScale = getHeight() / imageHeight;
            bigScale = getWidth() / imageWidth * SCALE_OVER_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float scaling = (currentScale-smallScale)/(bigScale-smallScale);
        canvas.translate(offsetX * scaling, offsetY * scaling);
//        float scaleSize = smallScale + (bigScale - smallScale) * scaling;
//        Log.e("~~~", "" + scaleSize);
        canvas.scale(currentScale, currentScale, getWidth() / 2, getHeight() / 2);
        canvas.translate(oldOffsetX, oldOffsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount() == 1){
            return gestureDetector.onTouchEvent(event);
        }else{
            return scaleGestureDetector.onTouchEvent(event);
        }


    }

    //手势监听
    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("gestureDetector", "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.e("gestureDetector", "onshowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e("gestureDetector", "onsingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e("gestureDetector", "onScroll");
        if (isBig) {
            offsetX -= distanceX;
            offsetX = Math.min(offsetX, (imageWidth * bigScale - getWidth()) / 2);
            offsetX = Math.max(offsetX, -(imageWidth * bigScale - getWidth()) / 2);
            offsetY -= distanceY;
            offsetY = Math.min(offsetY, (imageHeight * bigScale - getHeight()) / 2);
            offsetY = Math.max(offsetY, -(imageHeight * bigScale - getHeight()) / 2);
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.e("gestureDetector", "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("gestureDetector", "onFling");
        overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY, (int) -(imageWidth * bigScale - getWidth()) / 2,
                (int) (imageWidth * bigScale - getWidth()) / 2, (int) -(imageHeight * bigScale - getHeight()) / 2, (int) (imageHeight * bigScale - getHeight()) / 2
                , 100, 100);
        postOnAnimation(this);
        return false;
    }

    //doubleListener
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.e("gestureDetectorDouble", "onSingleTapConfirmed");

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.e("gestureDetectorDouble", "onDoubleTap");
        isBig = !isBig;
        if (isBig) {
            offsetX = getWidth() / 2 - e.getX();
            offsetY = getHeight() / 2 - e.getY();
            getAnimator().start();
        } else {
            getAnimator().reverse();
        }
        return false;
    }

    ObjectAnimator getAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "CurrentScale", smallScale, bigScale);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isBig) {
                        offsetX = 0;
                        offsetY = 0;
                    }
                }
            });
        }
        return objectAnimator;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.e("gestureDetectorDouble", "onDoubleTapEvent");
        return false;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }

    private class MyScalListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            currentScale = oldCurrentScale * detector.getScaleFactor();
            if(currentScale<=smallScale){
                currentScale = smallScale;
                isBig = false;
            }else if(currentScale>bigScale){
                currentScale = bigScale;
                isBig = true;
            }else{
                isBig = true;
            }


//            offsetX = getWidth()/2 - detector.getCurrentSpanX();
//            offsetY = getHeight()/2 - detector.getCurrentSpanY();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            oldCurrentScale = currentScale;

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
