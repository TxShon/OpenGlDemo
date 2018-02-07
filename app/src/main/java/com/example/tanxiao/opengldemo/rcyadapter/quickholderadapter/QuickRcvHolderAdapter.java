package com.example.tanxiao.opengldemo.rcyadapter.quickholderadapter;

import android.content.res.Resources;
import android.view.ViewGroup;

import com.example.tanxiao.opengldemo.rcyadapter.base.BaseRcvAdapter;
import com.example.tanxiao.opengldemo.rcyadapter.base.RcvMultiItemTypeSupport;

import java.util.List;


/**
 * Created by TX on 2017/3/3.
 * ClassNote:
 * QuickRcvHolderAdapter use to handle the situation that item contains different type
 */
public class QuickRcvHolderAdapter<T> extends BaseRcvAdapter<T, BaseMultiItemHolder<T>> {


    public QuickRcvHolderAdapter(List<T> data) {
        super(data);
    }

    public QuickRcvHolderAdapter(List<T> data, RcvMultiItemTypeSupport<T> multiItemSupport) {
        super(data, multiItemSupport);
    }

    @Override
    protected BaseMultiItemHolder<T> getHolder(ViewGroup parent, int viewType) {
        BaseMultiItemHolder<T> holder = null;
        if (mMultiItemSupport != null && mMultiItemSupport instanceof RcvMultiHolder) {
            holder = ((RcvMultiHolder<T>) mMultiItemSupport).getItemHolder(parent, viewType);
        }
        if (holder == null) {
            holder = getSingleHolder(parent);
            if (holder == null) {
                throw new Resources.NotFoundException("not find holder ,use RcvMultiHolder and check your implement of RcvMultiHolder.getItemHolder(ViewGroup parent, int viewType)");
            }
        }
        return holder;
    }

    /**
     * 单条目Holder
     *
     * @param parent
     * @return
     */
    protected BaseMultiItemHolder<T> getSingleHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void convert(BaseMultiItemHolder<T> holder, T item) {
        holder.BindData(item);
    }
}
