package com.doomsday.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/5.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View contentView;
    private SparseArray<View> mViews;
    private BaseRecyclerViewAdapter adapter;
    private View.OnClickListener clickListener;

    public BaseViewHolder(BaseRecyclerViewAdapter adapter, View itemView) {
        super(itemView);
        contentView = itemView;
        mViews = new SparseArray<>();
        this.adapter = adapter;
    }

    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = contentView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public View getContentView() {
        return contentView;
    }

    public void setText(int id, String str) {
        ((TextView) getView(id)).setText(str);
    }


    public static BaseViewHolder get(Context mContext, BaseRecyclerViewAdapter adapter, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        return new BaseViewHolder(adapter, view);
    }

    public static BaseViewHolder get(View view) {
        return new BaseViewHolder(null, view);
    }

    public void setOnItemChildClickListener(int... ids) {
        if (clickListener == null)
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.onChildClick(v, getAdapterPosition());
                }
            };
        for (int id : ids) if (id != 0) getView(id).setOnClickListener(clickListener);
    }
}
