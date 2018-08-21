package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by liuzhaoliang on 2018/7/23.
 */

public class TagLayout extends ViewGroup {

    Rect[] childBounds;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int usedHeight = 0;
        int usedWidth = 0;
        int count = getChildCount();
        int maxHeight = 0;
        int maxUsedLine = 0;
//        int nowHeight = 0;
        if (childBounds == null) {
            childBounds = new Rect[count];
        } else if (childBounds.length < count) {
            childBounds = Arrays.copyOf(childBounds, count);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childBounds[i];
//            child.measure(widthMeasureSpec,heightMeasureSpec);
            measureChildWithMargins(child, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
            if((usedWidth +child.getMeasuredWidth()) > MeasureSpec.getSize(widthMeasureSpec)){
//            if((child.getMeasuredState() & MEASURED_STATE_TOO_SMALL) != 0){
                usedHeight += maxHeight;
//                nowHeight += maxHeight;
                maxHeight = 0;
                usedWidth = 0;
//                measureChildWithMargins(child, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
            }
            if (rect == null) {
                rect = childBounds[i] = new Rect();
            }
            rect.set(usedWidth, usedHeight, usedWidth +child.getMeasuredWidth(), usedHeight+child.getMeasuredHeight());
            usedWidth += child.getMeasuredWidth();
            maxHeight = Math.max(maxHeight,child.getMeasuredHeight());//布局的高
            maxUsedLine = Math.max(maxUsedLine,usedWidth);//布局的宽
            Log.e("~~~",""+maxHeight+"~~"+usedWidth);

        }
        int width = maxUsedLine;
        int height = maxHeight+usedHeight;
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0),
                resolveSizeAndState(height, heightMeasureSpec, 0));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBound = childBounds[i];
            Log.e("!!!!!",childBound.left+"~"+childBound.top+"~"+childBound.right+"~"+childBound.bottom);
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);//设置子view的尺寸
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
