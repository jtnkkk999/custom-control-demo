package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by liuzhaoliang on 2018/7/24.
 */

public class LayoutView extends android.support.v7.widget.AppCompatImageView{
    public LayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int size =Math.min(right-left,bottom-top);
        Log.e("~~~onlayout",size+"");
        super.onLayout(changed, left, top, left+size, bottom+size);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        int size =Math.min(r-l,b-t);
        Log.e("~~~layout",size+"");
        super.layout(l, t, r+size, b+size);
    }
}
