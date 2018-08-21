package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/16.
 */

public class RunCircle extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int padding = Utils.dip2px(20);
    Rect rect = new Rect();
    public RunCircle(Context context) {
        super(context);
    }

    public RunCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RunCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(getWidth(), getHeight())/2 - padding;
        paint.setStrokeWidth(40);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);

        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2-radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius,-90,225,false,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Utils.dip2px(80));
        paint.setTextAlign(Paint.Align.CENTER);
        //字符集设置
//        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"");
//        paint.setTypeface(typeface);
        String text="abcd";
        paint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText("abcd",getWidth()/2,getHeight()/2+rect.height()/2,paint);
        Log.e("~~~",rect.height()+"~~~"+(rect.top+rect.bottom)+"~"+rect.top+"~"+rect.bottom);

    }
}
