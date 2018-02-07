package com.example.tanxiao.opengldemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TX on 2017/12/1.
 * Class note:
 */

public class SpaceColorItemDecoration extends RecyclerView.ItemDecoration {

    protected final Drawable mDivider;
    protected final int mSize;
    protected final int mOrientation;

    public static final int DRAW_BELOW = 0;
    public static final int DRAW_OVER = 1;

    @IntDef({DRAW_BELOW, DRAW_OVER})
    protected @interface DrawMode {
    }

    private int mMode = DRAW_BELOW;

    public SpaceColorItemDecoration(Context context, @ColorRes int color, int size, int orientation, @DrawMode int mode) {
        mDivider = new ColorDrawable(context.getResources().getColor(color));
        mSize = size;
        mOrientation = orientation;
        mMode = mode;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mMode == DRAW_BELOW) {
            draw(c, parent);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (mMode == DRAW_OVER) {
            draw(c, parent);
        }
    }

    private void draw(Canvas c, RecyclerView parent) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, mSize, 0);
        } else {
            outRect.set(0, 0, 0, mSize);
        }
    }


    protected void drawVertical(Canvas c, RecyclerView parent) {
        int left;
        int right;
        int top;
        int bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mSize;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        int top;
        int bottom;
        int left;
        int right;
        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + params.rightMargin;
            right = left + mSize;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
