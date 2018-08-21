package com.liuzhaoliang.hencoder6;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.zip.CheckedOutputStream;

/**
 * Created by liuzhaoliang on 2018/7/20.
 */

public class MyEditetext extends android.support.v7.widget.AppCompatEditText {
    private static final int TEXT_SIZE = Utils.dip2px(10);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator animator;
    private boolean isShow = false;
    private boolean MyUseLable = true;

    private float lableAlpha = 0;
    private int slectColor;
    private int leftBitMapID;

    public float getLableAlpha() {
        return lableAlpha;
    }

    public void setLableAlpha(float lableAlpha) {
        this.lableAlpha = lableAlpha;
        invalidate();
    }


    public MyEditetext(Context context) {
        super(context);
    }

    public MyEditetext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyEditetext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typeAray = context.obtainStyledAttributes(attrs, R.styleable.MyEditetext);
        boolean isUse = typeAray.getBoolean(R.styleable.MyEditetext_useModle, true);
        this.MyUseLable = isUse;
        slectColor = typeAray.getColor(R.styleable.MyEditetext_foucusColor, Color.parseColor("#ff0000"));
        leftBitMapID = typeAray.getResourceId(R.styleable.MyEditetext_LeftBitMap, R.drawable.touxiang);
        typeAray.recycle();
        if (MyUseLable) {
            paint.setTextSize(TEXT_SIZE);
            setPadding(getPaddingLeft() + Utils.dip2px(50), getPaddingTop() + TEXT_SIZE, getPaddingRight(), getPaddingBottom());
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0 && !isShow) {
                        getAnimator().start();
                        isShow = true;
                    } else if (s.length() == 0 && isShow) {
                        getAnimator().reverse();
                        isShow = false;
                    }
                }
            });
        }

    }

    {
        setBackground(null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (MyUseLable) {
            String hint = (String) getHint();
            float alpha = getAlpha();
            paint.setAlpha((int) (lableAlpha * 0xff));
            float offsetY = (1 - lableAlpha) * Utils.dip2px(16);
            canvas.drawText(hint, Utils.dip2px(4) + Utils.dip2px(50), TEXT_SIZE + Utils.dip2px(5) + offsetY, paint);
            paint.setAlpha((int) alpha);
        }


        if (hasFocus()) {
            paint.setColor(slectColor);
            paint.setStrokeWidth(Utils.dip2px(2));
        } else {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(Utils.dip2px(0.75f));
        }
        if (MyUseLable) {
            canvas.drawLine(Utils.dip2px(5) + Utils.dip2px(50), getBottom() - Utils.dip2px(10), getWidth() - Utils.dip2px(5), getBottom() - Utils.dip2px(10), paint);
        }else{
            canvas.drawLine(Utils.dip2px(5), getBottom() - Utils.dip2px(10), getWidth() - Utils.dip2px(5), getBottom() - Utils.dip2px(10), paint);
        }
        //颜色复原
        paint.setColor(Color.BLACK);

        if (MyUseLable && leftBitMapID != 0) {
            canvas.drawBitmap(getBitmap(Utils.dip2px(45), leftBitMapID), Utils.dip2px(5), 0, paint);
        }
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MyEditetext.this, "lableAlpha", 0, 1);
        }
        return animator;
    }

    Bitmap getBitmap(int width, int bitmapID) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), bitmapID, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), bitmapID, options);
    }

}
