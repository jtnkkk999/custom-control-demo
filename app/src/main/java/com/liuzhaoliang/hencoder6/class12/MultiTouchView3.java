package com.liuzhaoliang.hencoder6.class12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/8/2.
 */

public class MultiTouchView3 extends View{
    Paint paint;
    float points[][];
    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
