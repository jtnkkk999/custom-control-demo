package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/16.
 */

public class CicleImage extends View{
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int padding = Utils.dip2px(20);
    RectF rectf;
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    public CicleImage(Context context) {
        super(context);
    }

    public CicleImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CicleImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = Math.min(getWidth(), getHeight())/2 - padding;
        rectf = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);

        canvas.drawCircle(getWidth() / 2,getHeight() / 2,radius,paint);

        int save = canvas.saveLayer(0,0,getWidth(),getHeight(),paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth() / 2 ,getHeight() / 2,radius-Utils.dip2px(10),paint);


        paint.setXfermode(xfermode);
        canvas.drawBitmap(getBitmap(radius*2),getWidth() / 2-radius,getHeight() / 2-radius,paint);

        paint.setXfermode(null);
        canvas.restoreToCount(save);
//        canvas.drawBitmap();
    }

    Bitmap getBitmap(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.touxiang,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(),R.drawable.touxiang,options);
    }


}
