package com.doomsday.base.adapter;

/**
 * Created by Administrator on 2017/8/25.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);
}