package com.liuzhaoliang.hencoder6.class12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.liuzhaoliang.hencoder6.R;
import com.liuzhaoliang.hencoder6.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created 18/7/31 10:25
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class ScrollTextView extends View {

    private static final String TEXT = "周杰伦最帅";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private List<Item> items = new ArrayList<>();
    private static final int OFFSET = Utils.dip2px(5);
    private int bgColor;
    private int textColor;

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setTextSize(Utils.dip2px(18));
        bgColor = getResources().getColor(R.color.colorPrimary);
        textColor = getResources().getColor(R.color.white);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!items.isEmpty()){

            for (Item item : items){
                RectF mRectF = item.mRectF;
                int alpha = 255;
                int measuredHeight = getMeasuredHeight();
                if (mRectF.bottom < measuredHeight * 3 / 4){
                    alpha = (int) (255 * ( 1- ( measuredHeight - mRectF.bottom )/measuredHeight * 1.0f));
                }
                mPaint.setColor(bgColor);
                mPaint.setAlpha(alpha);
                canvas.drawRoundRect(mRectF,5,5,mPaint);
                canvas.drawBitmap(mBitmap,OFFSET+mRectF.left,mRectF.top + OFFSET,mPaint);
                mPaint.setColor(textColor);
                mPaint.setAlpha(alpha);
                canvas.drawText(TEXT,OFFSET+mBitmap.getWidth()+OFFSET,mRectF.bottom - OFFSET,mPaint);

                mRectF.top -= 2;
                mRectF.bottom -= 2;

                if (mRectF.bottom <= 0){
                    mRectF.top = measuredHeight ;
                    mRectF.bottom = measuredHeight + mBitmap.getHeight() + OFFSET*2;
                }
            }

            invalidate();

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mBitmap = Utils.getBitmap(getResources(), Utils.dip2px(18));

        int measuredHeight = getMeasuredHeight();

        int itemHeight = mBitmap.getHeight() + OFFSET*2;

        int count = measuredHeight / itemHeight;

        int bottom = measuredHeight;
        for (int i = 0 ; i < count ; i ++){
            Item item = new Item();
            item.bgColor = bgColor;
            item.text = TEXT;
            RectF rectF = new RectF();
            rectF.left = 0;
            rectF.right = OFFSET + mBitmap.getWidth() + OFFSET + mPaint.measureText(item.text)+OFFSET;
            rectF.top = bottom - mBitmap.getHeight() - OFFSET*2;
            rectF.bottom = bottom;
            item.mRectF = rectF;
            items.add(item);
            bottom += itemHeight + OFFSET;

        }

        invalidate();

    }


    private class Item{
        public int bgColor;
        public String text;
        public RectF mRectF;
    }

}
