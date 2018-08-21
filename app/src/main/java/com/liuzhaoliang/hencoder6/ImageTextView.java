package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/17.
 */

public class ImageTextView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //    TextPaint textPain = new TextPaint(paint);
    String st = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    int ImageWidth = Utils.dip2px(150);
    StaticLayout stackLayout;
    float[] measureTextwidth = new float[1];
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(Utils.dip2px(20));
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        stackLayout = new StaticLayout(st,textPain,getMeasuredWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        stackLayout.draw(canvas);
        int start = 0;
        float y = -fontMetrics.top;
        int totalwidth = 0;
        if ((y + fontMetrics.top) > Utils.dip2px(100) && (y + fontMetrics.top) <= Utils.dip2px(100) + ImageWidth ||
                (y + fontMetrics.top) > Utils.dip2px(100) && y < Utils.dip2px(100) + ImageWidth) {
            totalwidth = getWidth() - ImageWidth;
        } else {
            totalwidth = getWidth();
        }
        int count = paint.breakText(st, 0, st.length(), true, totalwidth, measureTextwidth);
        while (count > 0) {
            canvas.drawText(st, start, start + count, 0, y, paint);
            y += paint.getFontSpacing();
            start += count;
            if ((y + fontMetrics.top) > Utils.dip2px(100) && (y + fontMetrics.top) <= Utils.dip2px(100) + ImageWidth ||
                    (y + fontMetrics.top) > Utils.dip2px(100) && y < Utils.dip2px(100) + ImageWidth) {
                totalwidth = getWidth() - ImageWidth;
            } else {
                totalwidth = getWidth();
            }
            count = paint.breakText(st, start, st.length(), true, totalwidth, measureTextwidth);
        }

        canvas.drawBitmap(getBitmap(ImageWidth), getWidth() - ImageWidth, Utils.dip2px(100), paint);
    }

    Bitmap getBitmap(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.touxiang, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.touxiang, options);
    }
}
