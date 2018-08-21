package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/23.
 */

public class CircleView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.dip2px(80);
    private static final float PADDING = Utils.dip2px(10);
    private float size;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        size = RADIUS + PADDING;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float viewLength = size * 2;
        setMeasuredDimension(resolveSizeAndState((int) viewLength, widthMeasureSpec, 0),
                resolveSizeAndState((int) viewLength, heightMeasureSpec, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#FF6F00"));
        canvas.drawCircle(size, size, RADIUS, paint);
    }
}
