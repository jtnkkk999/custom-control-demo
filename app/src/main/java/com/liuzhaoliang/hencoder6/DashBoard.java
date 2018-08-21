package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/16.
 */

public class DashBoard extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int padding = Utils.dip2px(40);
    private int weight = getWidth();
    private int height = getHeight();
    //    private final int sideLength = 300;
    private Path path = new Path();
    private Path path2 = new Path();
    private PathEffect effect;
    private PathMeasure measure = new PathMeasure();
    private Path ArcPath = new Path();
    private Path ArcPath2 = new Path();
    private double mValue = 0;
    private int endX = -150;
    private int endY = 150;
    private final int length = 200;

    public DashBoard(Context context) {
        super(context);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dip2px(4));

        path.addRect(0, 0, Utils.dip2px(3), Utils.dip2px(7), Path.Direction.CW);

        path2.addRect(0,0,Utils.dip2px(6), Utils.dip2px(14), Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawLine(0,0,500,500,paint);

        int radius = Math.min(getWidth(), getHeight()) / 2 - padding;//
        canvas.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, -225, 270, false, paint);

        ArcPath.addArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, -225, 270);
        measure.setPath(ArcPath, false);
        effect = new PathDashPathEffect(path, (measure.getLength() - Utils.dip2px(3)) / 20, 0, PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, -225, 270, false, paint);
        paint.setPathEffect(null);


        ArcPath2.addArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, -225, 270);
        measure.setPath(ArcPath2,false);
        effect = new PathDashPathEffect(path2, (measure.getLength() - Utils.dip2px(6)) / 4, 0, PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, -225, 270, false, paint);
        paint.setPathEffect(null);



        //绘制圆心
        paint.setStrokeWidth(Utils.dip2px(10));
        canvas.drawPoint(getWidth() / 2, getHeight() / 2, paint);
        //绘制指针
        float preRadius = 135;
        Double nowRadius = 0.0;
//        mValue = 13;
        if (mValue == 0) {
            nowRadius = 135.0;
        } else {
            nowRadius = 135 + (mValue / 20.0) * 270;
//            if (nowRadius >= 360) {
//                nowRadius = nowRadius - 360;
//            }
            Log.e("!!!!", nowRadius + "~");
        }
        getpoint(nowRadius);
        paint.setStrokeWidth(5);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(0, 0, endX, endY, paint);

    }

    public void setValue(Double value) {
        mValue = value;
    }

    public void getpoint(Double Radius) {
        if (Radius == 180) {
            endX = -150;
            endY = 0;
            return;
        }
        if (Radius == 270) {
            endX = 0;
            endY = -150;
            return;
        }
        if (Radius == 0) {
            endX = 150;
            endY = 0;
            return;
        }
        if (Radius == 360) {
            endX = 150;
            endY = 0;
            return;
        }

        double radians = Math.toRadians(Radius);

        Log.e("~~~", Radius + "");
        if (Radius > 0 && Radius < 90) {
            endX = (int) -(length * Math.cos(radians));
            endY = (int) (length * Math.sin(radians));
            Log.e("~~~1", endX + "~~" + endY);
        }
        if (Radius > 90 && Radius < 180) {
            endX = (int) (length * Math.cos(radians));
            endY = (int) (length * Math.sin(radians));
            Log.e("~~~2", Math.cos(radians) + "~~" + endY);
        }
        if (Radius > 180 && Radius < 270) {
            endX = (int) (length * Math.cos(radians));
            endY = (int) (length * Math.sin(radians));
            Log.e("~~~3", endX + "~~" + endY);
        }
        if (Radius > 270 && Radius < 360) {
            endX = (int) (length * Math.cos(radians));
            endY = (int) (length * Math.sin(radians));
            Log.e("~~~4", endX + "~~" + endY);
        }
        if (Radius > 360) {
            Log.e("!!!!", Radius + "~");
            Radius = Radius - 360;
            radians = Math.toRadians(Radius);
            endX = (int) (length * Math.cos(radians));
            endY = (int) (length * Math.sin(radians));
            Log.e("~~~5", endX + "~~" + endY + "~~" + Radius);
        }
    }
}
