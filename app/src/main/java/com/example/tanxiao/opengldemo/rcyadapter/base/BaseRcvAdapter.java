package com.example.tanxiao.opengldemo.rcyadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by TX on 2016/11/14.
 * ClassNote:
 * BaseAdapter for RecyclerView
 */

public abstract class BaseRcvAdapter<T, K extends BaseViewHolder<T>> extends RecyclerView.Adapter<K> {

    protected int layoutResId;
    protected List<T> mData;

    protected RcvMultiItemTypeSupport<T> mMultiItemSupport;


    public BaseRcvAdapter(int layoutResId) {
        this(null, layoutResId);
    }

    public BaseRcvAdapter(List<T> data) {
        this(data, 0);
    }

    public BaseRcvAdapter(List<T> data, int layoutResId) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.layoutResId = layoutResId;
    }

    public BaseRcvAdapter(List<T> data, RcvMultiItemTypeSupport<T> multiItemSupport) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mMultiItemSupport = multiItemSupport;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        holder.setAssociatedObject(mData.get(position));
        baseConvert(holder, position, mData);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemSupport != null) {
            return mMultiItemSupport.getItemViewType(position, mData.get(position));
        } else {
            return super.getItemViewType(position);
        }
    }

    protected abstract K getHolder(ViewGroup parent, int viewType);


    /**
     * 需要在holder内对原数据(mData)做更改，重写该方法
     * <p>
     * 仅用在需要在holder内部更改原数据(mData),如:item中有list,并需要做更改
     *
     * @param holder
     * @param position
     * @param data
     */
    protected void baseConvert(K holder, int position, List<T> data) {
        convert(holder, data.get(position));
    }

    /**
     * 不需要在holder内对原数据(mData)做更改，重写该方法
     * <p>
     * 一般使用这个方法
     *
     * @param holder
     * @param item
     */
    protected void convert(K holder, T item) {

    }


    /**
     * 获取适配器数据
     *
     * @return
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * add new data
     *
     * @param data
     */
    public void addData(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * add new data
     *
     * @param data
     */
    public void addData(int position, List<T> data) {
        mData.addAll(position, data);
        notifyDataSetChanged();
    }

    /**
     * replace all data
     *
     * @param data
     */
    public void replaceAll(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }


    /**
     * clear all data
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * insert  a item associated with the specified position of adapter
     *
     * @param item
     */
    public void insert(T item) {
        insert(getItemCount(), item);
    }


    /**
     * insert  a item associated with the specified position of adapter
     *
     * @param position
     * @param item
     */
    public void insert(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mData.size() - position);//调用该方法更新，否则有bug
    }

    /**
     * insert  items associated with the specified position of adapter
     *
     * @param data
     */
    public void insert(List<T> data) {
        insert(getItemCount(), data);
    }

    /**
     * insert  items associated with the specified position of adapter
     *
     * @param position
     * @param data
     */
    public void insert(int position, List<T> data) {
        mData.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
        notifyItemRangeChanged(position, mData.size() - position + data.size());//调用该方法更新，否则有bug
    }


    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size() - position);//调用该方法更新，否则有bug
    }

    /**
     * change a item associated with the specified position of adapter
     *
     * @param position
     * @param item
     */
    public void change(int position, T item) {
        mData.add(position, item);
        mData.remove(position + 1);
        notifyItemChanged(position);
    }

    /**
     * add new datas in to certain location
     *
     * @param position
     * @hide
     */
    private void changeRange(int position, List<T> data) {
        if (0 <= position && position < mData.size()) {
            mData.addAll(position, data);
            // TODO: 2016/12/23 待验证.........
            notifyItemRangeChanged(position, mData.size() - position - data.size());
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }
}
