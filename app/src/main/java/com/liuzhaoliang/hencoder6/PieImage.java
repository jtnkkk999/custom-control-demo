package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/16.
 */

public class PieImage extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int padding = Utils.dip2px(20);

    RectF rectf;

    public PieImage(Context context) {
        super(context);
    }

    public PieImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(getWidth(), getHeight())/2 - padding;
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        rectf = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
        double radians = Math.toRadians(360-45+45/2);
        Log.e("!!!!",radians+"```````"+(360-45+45/2));
        rectf.offset((float)(Utils.dip2px(10)*Math.cos(radians)),(float)(Utils.dip2px(10)*Math.sin(radians)));
        canvas.drawArc(rectf, -45, 45,true,paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, 0, 180,true,paint);

        paint.setColor(Color.BLUE);
        canvas.drawArc(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius, 180, 135,true,paint);


    }
}
