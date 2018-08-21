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

public class MyTouchLayout extends View{
    Paint paint;
    Bitmap bitmap;

    private float offsetX;
    private float offsetY;
    private float oldOffsetX;
    private float oldOffsetY;
    private static final float IMGAGE_SIZE = Utils.dip2px(300);
    private float downX;
    private float downY;
    private int pointIndex;

    public MyTouchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Utils.getBitmap(getResources(), (int) IMGAGE_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX,offsetY);
        canvas.drawBitmap(bitmap,(getWidth()-IMGAGE_SIZE)/2,(getHeight()-IMGAGE_SIZE)/2,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                pointIndex = event.getActionIndex();
                downX = event.getX();
                downY = event.getY();
                oldOffsetX = offsetX;
                oldOffsetY = offsetY;
                Log.e("~~~~",event.getActionIndex()+"~");
                Log.e("ACTION_DOWN",downX+"-"+downY+"~"+oldOffsetX+"-"+oldOffsetY);
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = oldOffsetX + event.getX(pointIndex) - downX;
                offsetY = oldOffsetY + event.getY(pointIndex) - downY;
                invalidate();
                Log.e("ACTION_MOVE","移动差值为："+(event.getX(pointIndex) - downX)+"-"+(event.getY(pointIndex) - downY));
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                Log.e("ACTION_UP",downX+"-"+downY+"~"+oldOffsetX+"-"+oldOffsetY);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                pointIndex = event.getActionIndex();
                downX = event.getX(pointIndex);
                downY = event.getY(pointIndex);
                oldOffsetX = offsetX;
                oldOffsetY = offsetY;
                Log.e("~~~~",event.getActionIndex()+"~");
                Log.e("ACTION_POINTER_DOWN",downX+"-"+downY+"~"+oldOffsetX+"-"+oldOffsetY);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointIndex = 0;
                int remain = event.getActionIndex()==0?1:0;
                downX = event.getX(remain);
                downY = event.getY(remain);
                oldOffsetX = offsetX ;
                oldOffsetY = offsetY ;
                break;
        }
        return true;
    }
}
