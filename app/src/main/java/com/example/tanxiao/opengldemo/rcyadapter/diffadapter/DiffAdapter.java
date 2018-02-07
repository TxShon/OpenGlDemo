package com.example.tanxiao.opengldemo.rcyadapter.diffadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;

import com.example.tanxiao.opengldemo.rcyadapter.base.BaseViewHolder;
import com.example.tanxiao.opengldemo.rcyadapter.base.RcvMultiItemTypeSupport;
import com.example.tanxiao.opengldemo.rcyadapter.quickadapter.QuickRcvAdapter;

import java.util.List;

/**
 * Created by TX on 2017/6/8.
 * Class note:
 */

public abstract class DiffAdapter<T> extends QuickRcvAdapter<T> {

    private ItemDiffCallback<T> diffCallback;

    public DiffAdapter(List<T> data, int layoutResId) {
        super(data, layoutResId);
    }

    public DiffAdapter(List<T> data, RcvMultiItemTypeSupport<T> multiItemSupport) {
        super(data, multiItemSupport);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBaseSmartRefresh(holder, position, payloads);
        }
    }

    /**
     * @param holder
     * @param position
     * @param payloads
     */
    private void onBaseSmartRefresh(BaseViewHolder<T> holder, int position, List<Object> payloads) {
        holder.setAssociatedObject(mData.get(position));
        onSmartRefresh(holder, mData.get(position), position, payloads);
    }

    /**
     * for oldItem.equals(newItem) is always false, you'd better use a unique identification of item to
     * make out that are the two items the same
     *
     * @param mOldData
     * @param oldItemPosition
     * @param mNewData
     * @param newItemPosition @return return true as the default ,to let diffUtil continue execute to {@link #areContentsTheSame(List, int, List, int)}
     */
    public boolean areItemsTheSame(List<T> mOldData, int oldItemPosition, List<T> mNewData, int newItemPosition) {
        return areItemsTheSame(mOldData.get(oldItemPosition), mNewData.get(newItemPosition));
    }

    /**
     * @param mOldData
     * @param oldItemPosition
     * @param mNewData
     * @param newItemPosition @return
     */
    public boolean areContentsTheSame(List<T> mOldData, int oldItemPosition, List<T> mNewData, int newItemPosition) {
        return areContentsTheSame(mOldData.get(oldItemPosition), mNewData.get(newItemPosition));
    }

    /**
     * @param mOldData
     * @param oldItemPosition
     * @param mNewData
     * @param newItemPosition @return
     */
    public Object getChangePayload(List<T> mOldData, int oldItemPosition, List<T> mNewData, int newItemPosition) {
        return getChangePayload(mOldData.get(oldItemPosition), mNewData.get(newItemPosition));
    }


    /**
     * @param oldItem
     * @param NewItem
     * @return
     */
    protected boolean areContentsTheSame(T oldItem, T NewItem) {
        return false;
    }

    /**
     * @param oldItem
     * @param NewItem
     * @return
     */
    protected boolean areItemsTheSame(T oldItem, T NewItem) {
        return false;
    }


    /**
     * @param oldItem
     * @param NewItem
     * @return
     */
    protected Object getChangePayload(T oldItem, T NewItem) {
        return null;
    }


    /**
     * update specific child view
     *
     * @param holder
     * @param position
     * @param payloads
     */
    protected void onSmartRefresh(BaseViewHolder<T> holder, T item, int position, List<Object> payloads) {

    }

    /**
     * V2.0
     * refresh recycleView
     *
     * @param data
     */
    public void fillData(List<T> data) {
       // this.mData = clone(data);
        notifyDataSetChanged();
    }

    /**
     * V2.0
     * refresh recycleView
     *
     * @param data
     */
    public void refresh(List<T> data) {
        refresh(data, false);
    }

    /**
     * V2.0
     * refresh recycleView
     * <p>
     * use this method to refresh recycleView smartly
     * note: for avoid foreign and internal collection hold the references to the same object,
     * you must call {@link #fillData(List)} for first filling adapter, and never mixedLy use
     * method for updating adapter such as {@link #replaceAll(List)},that not annotated with V2.0
     *
     * @param data        newest data
     * @param detectMoves True if DiffUtil should try to detect moved items, false otherwise.
     *                    数据有移位的情况下设置为true,其他情况关闭该探测功能可提高运算效率
     */
    public void refresh(@NonNull List<T> data, final boolean detectMoves) {
        if (diffCallback == null) {
            diffCallback = new ItemDiffCallback<>(this, mData, data);
        } else {
            diffCallback.setDiffData(mData, data);
        }
        // TODO: 2017/5/25 优化: 数量较大时将运算过程放入子线程
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, detectMoves);

        mData.clear();
        mData.addAll(data);

        result.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
//                LogManager.getLogger().local("onInserted: " + "position==" + position + "----count==" + count);
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
//                LogManager.getLogger().local("onRemoved: " + "position==" + position + "----count==" + count);
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
//                LogManager.getLogger().local("onMoved: " + "fromPosition==" + fromPosition + "----toPosition==" + toPosition);
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
//                LogManager.getLogger().local("onChanged: " + "position==" + position + "----count==" + count + "----payload==" + payload.toString());
                notifyItemRangeChanged(position, count, payload);
            }
        });
    }

  /*  @Override
    public void replaceAll(List<T> data) {
        mData = clone(data);
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void addData(int position, List<T> data) {
        mData.addAll(position, data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size() - position);//调用该方法更新，否则有bug
    }*/

    /**
     * clone new data to mData
     *
     * @param data
     * @return
     */
    /*private List<T> clone(List<T> data) {
        ArrayList<T> cloneData = new ArrayList<>();
        Cloner cloner = new Cloner();
        for (T t : data) {
            cloneData.add(cloner.deepClone(t));
        }
        mData.clear();
        return cloneData;
    }*/
}
