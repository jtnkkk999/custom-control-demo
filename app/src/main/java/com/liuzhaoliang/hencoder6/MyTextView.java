package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : hongqian.better
 * time   : 2018/7/22
 * desc   :
 **/
public class MyTextView extends View {

    Paint paint = new Paint();

    Path path = new Path();
    Path cell = new Path();
    PathMeasure measure = new PathMeasure();


    public MyTextView(Context context) {
        super(context);
    }


    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    {
        paint.setColor(Color.parseColor("#FF0000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        cell.addRect(0, 0, 20, 60, Path.Direction.CCW);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#e2d5d5"));
        path.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2-200, Path.Direction.CCW);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2-200,paint);
        canvas.drawPath(path,paint);
        measure.setPath(path, false);
        float v = measure.getLength() / 20;
        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(cell, v, 0, PathDashPathEffect.Style.ROTATE);


        paint.setPathEffect(pathDashPathEffect);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2-100,paint);
    }
}
