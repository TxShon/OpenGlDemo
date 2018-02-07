package com.example.tanxiao.opengldemo.rcyadapter.quickadapter;


import com.example.tanxiao.opengldemo.rcyadapter.base.RcvMultiItemTypeSupport;

/**
 * Created by TX on 2017/3/1.
 * ClassNote:
 */
public abstract class RcvMultiItemType<T> implements RcvMultiItemTypeSupport<T> {

    public abstract int getLayoutId(int viewType);
}
