package com.example.tanxiao.opengldemo.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TX on 2017/8/12.
 * Class note:
 */

public class GridCenterDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpace;
    private int verticalSpace;

    public GridCenterDecoration(int verticalSpace, int horizontalSpace) {
        this.verticalSpace = verticalSpace;
        this.horizontalSpace = horizontalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalSpace;
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (childAdapterPosition % 2 == 0) {
            outRect.left = 0;
            outRect.right = horizontalSpace / 2;
        } else {
            outRect.left = horizontalSpace / 2;
            outRect.right = 0;
        }
    }
}
