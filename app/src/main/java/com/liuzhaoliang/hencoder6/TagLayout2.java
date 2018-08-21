package com.liuzhaoliang.hencoder6;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by liuzhaoliang on 2018/7/25.
 */

public class TagLayout2 extends ViewGroup {

    private Rect[] childBounds;
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> addlist = new ArrayList<>();

    public TagLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxwidth = 0;
        int maxHeight = 0;
        int count = getChildCount();

        int usedWidth = 0;
        int usedHeight = 0;

        if (childBounds == null) {
            childBounds = new Rect[count];
        }
        if (count < getChildCount()) {
            childBounds = Arrays.copyOf(childBounds, getChildCount());
        }
        int i;
        for (i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childbound = childBounds[i];
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, usedHeight);
            Log.e("子控件的尺寸", child.getMeasuredWidth() + "~" + child.getMeasuredHeight());
//            if (child.getHeight()>38) {
//                usedHeight += child.getMeasuredHeight();
//                maxHeight = child.getMeasuredHeight();
//                usedWidth = 0;
//                Log.e("~~~", "~~~~~~~~~~~~~~~~~~~~~");
//                measureChildWithMargins(child, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
//            }
            if (child.getMeasuredWidth() + usedWidth > MeasureSpec.getSize(widthMeasureSpec)
                    && MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
                list.add(i);
                addlist.add((MeasureSpec.getSize(widthMeasureSpec) - usedWidth) / 2);
                usedHeight += child.getMeasuredHeight();
                usedWidth = 0;
//                measureChildWithMargins(child, widthMeasureSpec, usedWidth, heightMeasureSpec, usedHeight);
            }
            if (childbound == null) {
                childbound = childBounds[i] = new Rect();
            }
            childbound.set(usedWidth, usedHeight, usedWidth + child.getMeasuredWidth(), usedHeight + child.getMeasuredHeight());
            usedWidth += child.getMeasuredWidth();
            maxwidth = Math.max(maxwidth, usedWidth);

            maxHeight = child.getMeasuredHeight();
            Log.e("~~~", maxwidth + "~" + maxHeight);
        }
        list.add(i);
        addlist.add((MeasureSpec.getSize(widthMeasureSpec) - usedWidth) / 2);

        int linewidth = maxwidth;
        int lineheight = usedHeight + maxHeight;
        Log.e("!!!", usedWidth + "~~" + lineheight);
        setMeasuredDimension(resolveSizeAndState(linewidth, widthMeasureSpec, 0),
                resolveSizeAndState(lineheight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (list.get(count) != i) {
                view.layout(childBounds[i].left + addlist.get(count), childBounds[i].top, childBounds[i].right + addlist.get(count), childBounds[i].bottom);
            } else {
                count++;
                view.layout(childBounds[i].left + addlist.get(count), childBounds[i].top, childBounds[i].right + addlist.get(count), childBounds[i].bottom);
            }

        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
