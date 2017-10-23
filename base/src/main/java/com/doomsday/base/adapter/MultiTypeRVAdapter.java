package com.doomsday.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public abstract class MultiTypeRVAdapter<T> extends BaseRecyclerViewAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiTypeRVAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = super.getItemViewType(position);
        if (itemViewType == TYPE_HEADER || itemViewType == TYPE_FOOT)
            return itemViewType;
        position -= getHeadCount();
        itemViewType = mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return itemViewType;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        if (baseViewHolder != null)
            return baseViewHolder;
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        BaseViewHolder holder = BaseViewHolder.get(mContext, this, parent, layoutId);
        View contentView = holder.getContentView();
        contentView.setTag(holder);
        contentView.setOnClickListener(onClickListener);
        return holder;
    }
}
