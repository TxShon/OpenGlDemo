package com.example.tanxiao.opengldemo.rcyadapter.quickholderadapter;

import android.view.View;

import com.example.tanxiao.opengldemo.rcyadapter.base.BaseViewHolder;


/**
 * Created by TX on 2017/3/3.
 * ClassNote:
 * show different itemView
 */
public abstract class BaseMultiItemHolder<T> extends BaseViewHolder<T> {


    public BaseMultiItemHolder(View view) {
        super(view);
    }

    protected abstract void BindData(T item);
}
