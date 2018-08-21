package com.liuzhaoliang.hencoder6.class12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.liuzhaoliang.hencoder6.Utils;

/**
 * Created by liuzhaoliang on 2018/8/2.
 */

public class MoveTitleView extends View implements Runnable {
    Paint paint;
    private Paint.FontMetrics metrics;
    private float dx;
    private float[] moveSize;
    private int totleHeight;
    private int count;
    private float totleWidth;
    private String text = "我很帅";
    private float textWidth;

    public MoveTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Utils.dip2px(20));
        metrics = paint.getFontMetrics();
        dx = metrics.ascent - Utils.dip2px(10);
        textWidth = paint.measureText(text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("measure1", MeasureSpec.getSize(widthMeasureSpec) + "");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totleHeight = MeasureSpec.getSize(heightMeasureSpec);
        totleWidth = MeasureSpec.getSize(widthMeasureSpec);
        count = (int) (totleHeight / -dx);
        if ((moveSize == null || moveSize.length == 0) && count > 0) {
            moveSize = new float[count + 1];
        }

        Log.e("~~~", getPaddingLeft() + "~~" + getPaddingTop());
        setMeasuredDimension((int) textWidth + getPaddingLeft() + getPaddingRight(), getMeasuredHeight() + getPaddingBottom() + getPaddingTop());

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e("layout", right + "");
        super.onLayout(changed, left, top, (int) totleWidth, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.e("count", count + "");
        for (int i = 1; i <= count + 1; i++) {
            if (-dx * i + moveSize[i] < -dx) {
                int alph = (int) ((-dx * i + moveSize[i]) / -dx * 255);
//                Log.e("alph",alph+"");
                paint.setAlpha(alph);
            }
            canvas.drawText("我很帅", getPaddingLeft(), -dx * i + moveSize[i], paint);
            paint.setAlpha(255);
        }
        postOnAnimation(this);
    }

    @Override
    public void run() {
//        Log.e("~~~~", "~~~~" + moveSize[1]);
        for (int i = 1; i <= count + 1; i++) {
            if (-dx * i + moveSize[i] < 0) {
                moveSize[i] += -dx * (count + 1);
//                Log.e("超出后回退", "!!" + moveSize[i]);
            } else {
                moveSize[i] -= 0.5;
            }
        }
        invalidate();
//        postOnAnimation(this);
    }
}
