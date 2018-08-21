package com.liuzhaoliang.hencoder6.class12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.liuzhaoliang.hencoder6.Utils;

/**
 * Created by liuzhaoliang on 2018/7/30.
 */

public class MultiTouchView2 extends View {
    Paint paint;
    private static final float IMAGESIZE = Utils.dip2px(300);
    private Bitmap bitmap;

    private float offSetX;
    private float offSetY;
    private float oldOffsetSetX;
    private float oldOffsetSetY;
    private float downX;
    private float downY;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX = 0;
        float sumY = 0;
        for (int i = 0; i < event.getPointerCount(); i++) {
            sumX += event.getX(i);
            sumY += event.getY(i);
        }
        float focuesX = sumX / event.getPointerCount();
        float focuesY = sumY / event.getPointerCount();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = focuesX;
                downY = focuesY;
                oldOffsetSetX = offSetX;
                oldOffsetSetY = offSetY;
//                Log.e("MOVE",offSetX+"-"+offSetY);
                break;
            case MotionEvent.ACTION_MOVE:
                offSetX = oldOffsetSetX + focuesX - downX;
                offSetY = oldOffsetSetY + focuesY - downY;
                Log.e("MOVE", focuesX + "-" + downX + "===" + offSetX + "-" + offSetY);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int remainPoint = event.getActionIndex() == 0 ? 1 : 0;
                downX = event.getX(remainPoint);
                downY = event.getY(remainPoint);
                oldOffsetSetX = offSetX;
                oldOffsetSetY = offSetY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = focuesX;
                downY = focuesY;
                oldOffsetSetX = offSetX;
                oldOffsetSetY = offSetY;
                break;

        }
        return true;
    }

    {
        bitmap = Utils.getBitmap(getResources(), (int) IMAGESIZE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offSetX, offSetY);
        canvas.drawBitmap(bitmap, (getWidth() - IMAGESIZE) / 2, (getHeight() - IMAGESIZE) / 2, paint);
    }
}
