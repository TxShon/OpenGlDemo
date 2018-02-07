package com.example.tanxiao.opengldemo.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by TX on 2017/12/1.
 * Class note:
 */

public class StaggerGridCenterDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public StaggerGridCenterDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        outRect.left = 0;
        outRect.bottom = 0;

        if (spanIndex == 0) {
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
        }
        if (position > 1) {
            outRect.top = space;
        }
    }
}
