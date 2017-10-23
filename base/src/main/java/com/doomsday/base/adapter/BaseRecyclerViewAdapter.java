package com.doomsday.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas;
    private int layoutId;
    protected Context mContext;
    public OnChildViewClickListener childClickListener;
    private OnItemClickListener itemClickListener;

    private View mHeadView, mFootView;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOT = 2;

    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.layoutId = getLayoutId();
        this.mDatas = data == null ? new ArrayList<T>() : data;
        mContext = context;
    }

    protected abstract int getLayoutId();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                viewHolder = BaseViewHolder.get(mHeadView);
                break;
            case TYPE_NORMAL:
                viewHolder = BaseViewHolder.get(mContext, this, parent, layoutId);
                View contentView = viewHolder.getContentView();
                contentView.setTag(viewHolder);
                contentView.setOnClickListener(onClickListener);
                break;
            case TYPE_FOOT:
                viewHolder = BaseViewHolder.get(mFootView);
                break;

        }
        return viewHolder;
    }

    protected View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            if (itemClickListener != null)
                itemClickListener.onClick(getItemPosition(viewHolder), mDatas.get(getItemPosition(viewHolder)));
        }
    };

    //获取默认item的position（去除了header）
    private int getItemPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - getHeadCount();
    }

    private int getItemPosition(int position) {
        return position - getHeadCount();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_FOOT:
                break;
            default:
                convert(holder, mDatas.get(getItemPosition(position)));
                break;
        }
    }

    public abstract void convert(BaseViewHolder holder, T data);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + getHeadCount() + getFootCount();
    }

    public void setNewData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public void addNewDate(List<T> data) {
        if (mDatas == null) mDatas = new ArrayList<>();
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void addDate(T data) {
        this.mDatas.add(data);
        notifyItemChanged(mDatas.size());
    }

    public void onChildClick(View v, int position) {
        if (childClickListener != null)
            childClickListener.onClick(v, position - getHeadCount(), mDatas.get(position - getHeadCount()));
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public T getItem(int position) {
        return mDatas.get(position - getHeadCount());
    }

    public int getHeadCount() {
        return mHeadView == null ? 0 : 1;
    }

    public int getFootCount() {
        return mFootView == null ? 0 : 1;
    }

    public int getDataSize() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void removeFoot() {
        if (mFootView == null) return;
        mFootView = null;
        notifyItemRemoved(getItemCount());
    }

    public void removeHead() {
        if (mHeadView == null) return;
        mHeadView = null;
        remove(0);
        notifyItemRemoved(0);
    }

    public interface OnChildViewClickListener<T> {
        void onClick(View view, int position, T data);
    }

    public interface OnItemClickListener<T> {
        void onClick(int position, T data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public void setOnChildViewClickListener(OnChildViewClickListener listener) {
        childClickListener = listener;
    }

    public void addHeader(View headView) {
        this.mHeadView = headView;
        notifyItemChanged(0);
    }

    public void addFooter(View footView) {
        this.mFootView = footView;
        notifyItemChanged(getAllItemCount() - 1);
    }

    int getAllItemCount() {
        return getHeadCount() + getDataSize() + getFootCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && getHeadCount() > 0) return TYPE_HEADER;
        if (getFootCount() > 0 && position == getAllItemCount() - 1)
            return TYPE_FOOT;
        return TYPE_NORMAL;
    }

}
