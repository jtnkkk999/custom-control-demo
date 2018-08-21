package com.liuzhaoliang.hencoder6;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.OverScroller;

/**
 * Created by liuzhaoliang on 2018/7/28.
 */

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int BITMAP_SIZE = Utils.dip2px(150);
    private static final int SCALE_OVER_FACTOR = 2;

    private float bigScale = 3;
    private float smalScale = 1;

    private float offsetX;
    private float offsetY;
    private float originalOffsetX;
    private float originalOffsetY;
    private float image_height;
    private float image_width;
    GestureDetector gestureDetector;
    OverScroller overScroller;

    Bitmap bitmap;
    private boolean isBig;
    private float scale;
    private float scalingFraction;
    ObjectAnimator animator;

    public float getScalingFraction() {
        return scalingFraction;
    }

    public void setScalingFraction(float scalingFraction) {
        this.scalingFraction = scalingFraction;
        invalidate();
    }

    ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "ScalingFraction", 0, 1);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(!isBig){
                        offsetX = 0;
                        offsetY = 0;
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return animator;
    }

    public ScalableImageView(Context context) {
        super(context);
        init(context);
    }

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        image_width = BITMAP_SIZE;
        bitmap = Utils.getBitmap(getResources(), (int) image_width);
        image_height = bitmap.getHeight();
        Log.e("初始化", "初始化");
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        overScroller = new OverScroller(context);

    }


    //了解下这个方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        offsetX = (getWidth() - image_width) / 2;
//        offsetY = (getHeight() - image_height) / 2;
//        Log.e("测量2", offsetX + "--" + offsetY);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        originalOffsetX = (getWidth() - image_width) / 2;
        originalOffsetY = (getHeight() - image_height) / 2;
        if (image_width / image_height > (float) getWidth() / getHeight()) {
            smalScale = getWidth() / image_width;
            bigScale = getHeight() / image_height * SCALE_OVER_FACTOR;

        } else {
            smalScale = getHeight() / image_height;
            bigScale = getWidth() / image_width * SCALE_OVER_FACTOR;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        scale = isBig? bigScale:smalScale;
        canvas.translate(offsetX*scalingFraction, offsetY*scalingFraction);
        scale = smalScale + (bigScale - smalScale) * scalingFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.translate(originalOffsetX, originalOffsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        Log.e("宽高", "~~~~~" + offsetX + "");
//        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {//不支持双击的时候等于单击
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isBig) {
            offsetX -= distanceX;
            offsetX = Math.min(offsetX, (image_width * bigScale - getWidth()) / 2);
            offsetX = Math.max(offsetX, -(image_width * bigScale - getWidth()) / 2);
            offsetY -= distanceY;
            offsetY = Math.min(offsetY, (image_height * bigScale - getHeight()) / 2);
            offsetY = Math.max(offsetY, -(image_height * bigScale - getHeight()) / 2);
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //滑动过程中的路径移动效果
        overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY, (int) -(image_width * bigScale - getWidth()) / 2,
                (int) (image_width*bigScale - getWidth())/2,(int)-(image_height*bigScale - getHeight())/2,(int)(image_height*bigScale - getHeight())/2
        ,100,100);

        postOnAnimation(this);
        return false;
    }

    //DoubleListener
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件
        //我确认你就是要单机不是双击，也就是当第一次点击抬起后，在300ms没有发生点击
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {//双击事件
        //第一次抬起到第二次按下的间隔小于300ms时触发事件
        Log.e("双击事件开始",e.getX()+"~"+e.getY());
        isBig = !isBig;
        if (isBig) {
            offsetX = getWidth()/2 - e.getX();
            offsetY = getHeight()/2 - e.getY();
            getAnimator().start();
        } else {
            getAnimator().reverse();
        }
//        invalidate();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
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
}
