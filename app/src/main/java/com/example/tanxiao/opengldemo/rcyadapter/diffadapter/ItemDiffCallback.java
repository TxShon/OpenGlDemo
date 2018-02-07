package com.example.tanxiao.opengldemo.rcyadapter.diffadapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TX on 2017/5/25.
 * Class note:
 */

public class ItemDiffCallback<T> extends DiffUtil.Callback  {

    protected List<T> mOldData, mNewData;
    protected DiffAdapter<T> mBaseRcvAdapter;

    public ItemDiffCallback(DiffAdapter<T> baseRcvAdapter, List<T> oldData, List<T> newData) {
        mBaseRcvAdapter = baseRcvAdapter;
        if (mBaseRcvAdapter == null) {
            throw new IllegalStateException("baseRcvAdapter not allow null");
        }
        this.mOldData = oldData == null ? new ArrayList<T>() : new ArrayList<>(oldData);
        this.mNewData = newData == null ? new ArrayList<T>() : new ArrayList<>(newData);
    }


    @Override
    public int getOldListSize() {
        return mOldData.size();
    }

    @Override
    public int getNewListSize() {
        return mNewData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mBaseRcvAdapter.areItemsTheSame(mOldData,oldItemPosition,mNewData ,newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mBaseRcvAdapter.areContentsTheSame(mOldData,oldItemPosition,mNewData ,newItemPosition);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return mBaseRcvAdapter.getChangePayload(mOldData,oldItemPosition,mNewData ,newItemPosition);
    }

    /**
     * reset diff data
     *
     * @param oldData
     * @param newData
     */
    public void setDiffData(List<T> oldData, List<T> newData) {
        this.mOldData.clear();
        this.mNewData.clear();
        this.mOldData.addAll(oldData);
        this.mNewData.addAll(newData);
    }
}
