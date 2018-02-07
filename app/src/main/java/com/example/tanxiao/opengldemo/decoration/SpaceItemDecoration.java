package com.example.tanxiao.opengldemo.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TX on 2017/7/29.
 * Class note:
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    private int space;
    private int type;

    public SpaceItemDecoration(int type, int space) {
        this.type = type;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (type == LEFT) {
            outRect.left = space;
        } else if (type == TOP) {
            outRect.top = space;
        } else if (type == BOTTOM) {
            outRect.bottom = space;
        } else if (type == RIGHT) {
            outRect.right = space;
        }
    }
}
