package com.liuzhaoliang.dog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

import com.liuzhaoliang.hencoder6.R;
import com.liuzhaoliang.hencoder6.Utils;

import java.util.Random;

/**
 * Created by liuzhaoliang on 2018/8/20.
 */

public class singleDog extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int count = 30;
    Random ran = new Random();
    Bitmap bt;
    Bitmap bt1;
    private int top = 0;
    private int ranx;
    private Context mContext;
    private int mHeight;
    private int mWidth;

//    int[] ranx = new int[count];
//    int[] roll = new int[count];

    public float getCornerScale() {
        return cornerScale;
    }

    public void setCornerScale(float cornerScale) {
        this.cornerScale = cornerScale;
        invalidate();
    }

    private Float cornerScale = 0f;
    private int corner;
    private int cornerEnd = 0;
    private int[] cornerArray = new int[30];
    private int[] width = new int[30];
    private int[] height = new int[30];

    public int getTopLocation() {
        return top;
    }

    public void setTopLocation(int top) {
        this.top = top;
        invalidate();
    }

    public singleDog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    {
        bt = Utils.getBitmap(getResources(), Utils.dip2px(40));
        bt1 = Utils.getBitmap(getResources(), Utils.dip2px(40));
        top = -bt.getHeight();
        for (int i = 0; i < count; i++) {

        }

        corner = ran.nextInt(180);
//        cornerEnd = ;

        for (int i = 0; i < 30; i++) {
            cornerArray[i] = ran.nextInt(360);
            width[i] = ran.nextInt(Resources.getSystem().getDisplayMetrics().widthPixels - bt.getWidth()) + bt.getWidth();
            height[i] = ran.nextInt(1000);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        ranx = ran.nextInt(mWidth - bt.getWidth()) + bt.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 30; i++) {
            //这块代码从下往上看
            canvas.save();
            canvas.translate(width[i], top - height[i]);//将图片移动回本来应在的地方。
            canvas.rotate(cornerArray[i]+cornerArray[i]*cornerScale, 0, 0);//30张图片进行随机旋转初始角度cornerArray[i],corner属性动画变换的角度。
            canvas.translate(-bt.getWidth() / 2, -bt.getHeight() / 2 - (top - height[i]));//将所有图片移动到屏幕左上角，以图片为中心轴。
            canvas.drawBitmap(bt, 0, top - height[i], paint);//将图片画出来，也就是动画开始执行时的高度。（障眼法，在手机屏幕上方的某个随机位置，我们看不到）
                                                                        //top进行属性动画的top，height[i]数组，就是30个单身狗的随机高度。
            canvas.restore();
        }
    }

    public void startAnimation() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "TopLocation", 0, (int) (getHeight() * 2 + bt.getHeight()));
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        set.play(animator);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "CornerScale", 0, 1);
        animator1.setDuration(5000);
        set.play(animator1);
        set.start();
    }
}
