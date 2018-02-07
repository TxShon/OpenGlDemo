package com.example.tanxiao.opengldemo.rcyadapter.quickadapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanxiao.opengldemo.rcyadapter.base.BaseRcvAdapter;
import com.example.tanxiao.opengldemo.rcyadapter.base.BaseViewHolder;
import com.example.tanxiao.opengldemo.rcyadapter.base.RcvMultiItemTypeSupport;

import java.util.List;

/**
 * Created by TX on 2016/11/14.
 * ClassNote:
 * QuickAdapter for RecyclerView,support multiItem
 */

public abstract class QuickRcvAdapter<T> extends BaseRcvAdapter<T, BaseViewHolder<T>> {

    public QuickRcvAdapter(int layoutResId) {
        super(layoutResId);
    }

    public QuickRcvAdapter(List<T> data, int layoutResId) {
        super(data, layoutResId);
    }

    public QuickRcvAdapter(List<T> data, RcvMultiItemTypeSupport<T> multiItemSupport) {
        super(data, multiItemSupport);
    }


    @Override
    protected BaseViewHolder<T> getHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (mMultiItemSupport != null && mMultiItemSupport instanceof RcvMultiItemType) {
            layoutResId = ((RcvMultiItemType) mMultiItemSupport).getLayoutId(viewType);
        }
        if (layoutResId == 0) {
            throw new Resources.NotFoundException("not find layoutResId ,check layoutResId");
        }
        /*----bug: match_parent of childView attr Invalid*/
        //itemView = View.inflate(parent.getContext(), layoutResId, null);
        itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        if (itemView == null) {
            throw new Resources.NotFoundException("not find itemView ,check layoutResId");
        }
        return new BaseViewHolder<T>(itemView);
    }
}
