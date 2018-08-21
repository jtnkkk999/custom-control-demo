package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhaoliang on 2018/7/18.
 */

public class Animation extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bt;
    Camera camera;
    private static final int IMAGE_WIDTH = Utils.dip2px(250);
    private static final int IMAGEC_CLIP_WIDTH = Utils.dip2px(100);


    public int getCornerY() {
        return OFFSET_Y_CORNER;
    }

    public void setCornerY(int OFFSET_Y_CORNER) {
        this.OFFSET_Y_CORNER = OFFSET_Y_CORNER;
        invalidate();
    }

    private int OFFSET_Y_CORNER = 0;

    public int getCornerZ() {
        return OFFSET_Z_CORNER;
    }

    public void setCornerZ(int OFFSET_Z_CORNER) {
        this.OFFSET_Z_CORNER = OFFSET_Z_CORNER;
        invalidate();
    }

    private int OFFSET_Z_CORNER = 0;

    public int getCornerN() {
        return OFFSET_N_CORNER;
    }

    public void setCornerN(int OFFSET_N_CORNER) {
        this.OFFSET_N_CORNER = OFFSET_N_CORNER;
        invalidate();
    }

    private int OFFSET_N_CORNER = 0;

    public Animation(Context context) {
        super(context);
    }

    public Animation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Animation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bt = getBitmap(IMAGE_WIDTH);
        camera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        //底图片的绘制
//        canvas.save();
//        canvas.clipRect((getWidth() - IMAGE_WIDTH) / 2, (getHeight() - IMAGE_WIDTH) / 2, (getWidth() - IMAGE_WIDTH) / 2 + IMAGE_WIDTH / 2, (getHeight() - IMAGE_WIDTH) / 2 + IMAGE_WIDTH);
//        canvas.drawBitmap(bt, (getWidth() - IMAGE_WIDTH) / 2, (getHeight() - IMAGE_WIDTH) / 2, paint);
//        canvas.restore();
//        //动画图片的绘制
//        canvas.save();
//        camera.save();
//        canvas.clipRect((getWidth() - IMAGE_WIDTH) / 2 + IMAGE_WIDTH / 2, (getHeight() - IMAGE_WIDTH) / 2 - IMAGEC_CLIP_WIDTH, (getWidth() - IMAGE_WIDTH) / 2 + IMAGE_WIDTH + IMAGEC_CLIP_WIDTH, (getHeight() - IMAGE_WIDTH) / 2 + IMAGE_WIDTH + IMAGEC_CLIP_WIDTH);
//        camera.setLocation(0, 0, getCamareZ());
//        camera.rotateY(OFFSET_Y_CORNER);
//        canvas.translate(getWidth() / 2, getHeight() / 2);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-getWidth() / 2, -getHeight() / 2);
//        camera.restore();
//
//        canvas.drawBitmap(bt, (getWidth() - IMAGE_WIDTH) / 2, (getHeight() - IMAGE_WIDTH) / 2, paint);
//        canvas.restore();

        //----------------------------
        canvas.save();
        camera.save();
        camera.setLocation(0, 0, getCamareZ());
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-OFFSET_Z_CORNER);//0-270
        canvas.clipRect(- IMAGE_WIDTH / 2-IMAGEC_CLIP_WIDTH,  - IMAGE_WIDTH / 2-IMAGEC_CLIP_WIDTH, 0,  IMAGE_WIDTH/2+IMAGEC_CLIP_WIDTH);
        camera.rotateY(OFFSET_N_CORNER);
        camera.applyToCanvas(canvas);
        canvas.rotate(OFFSET_Z_CORNER);//0-270
        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        camera.restore();

        canvas.drawBitmap(bt, (getWidth() - IMAGE_WIDTH) / 2, (getHeight() - IMAGE_WIDTH) / 2, paint);
        canvas.restore();

        canvas.save();
        camera.save();
        camera.setLocation(0, 0, getCamareZ());
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-OFFSET_Z_CORNER);
        canvas.clipRect( 0,  - IMAGE_WIDTH / 2-IMAGEC_CLIP_WIDTH, IMAGE_WIDTH / 2+IMAGEC_CLIP_WIDTH,  IMAGE_WIDTH/2+IMAGEC_CLIP_WIDTH);
        camera.rotateY(OFFSET_Y_CORNER);
        camera.applyToCanvas(canvas);
        canvas.rotate(OFFSET_Z_CORNER);//0-270
        canvas.translate(-getWidth() / 2, -getHeight() / 2);

        camera.restore();

        canvas.drawBitmap(bt, (getWidth() - IMAGE_WIDTH) / 2, (getHeight() - IMAGE_WIDTH) / 2, paint);
        canvas.restore();
    }

    float getCamareZ() {
        return -6 * Resources.getSystem().getDisplayMetrics().density;
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
