package com.example.tanxiao.opengldemo.rcyadapter.quickholderadapter;

import android.view.ViewGroup;

import com.example.tanxiao.opengldemo.rcyadapter.base.RcvMultiItemTypeSupport;


/**
 * Created by TX on 2017/3/3.
 * ClassNote:
 */
public abstract class RcvMultiHolder<T> implements RcvMultiItemTypeSupport<T> {

    public abstract BaseMultiItemHolder<T> getItemHolder(ViewGroup parent, int viewType);
}
