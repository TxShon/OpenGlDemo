package com.example.tanxiao.opengldemo.rcyadapter.base;

public interface RcvMultiItemTypeSupport<T> {

    int getItemViewType(int position, T t);

}