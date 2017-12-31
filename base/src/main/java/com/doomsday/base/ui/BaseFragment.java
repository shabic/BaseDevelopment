package com.doomsday.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doomsday.base.utils.PresenterUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/19.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseUi {

    public P mPresenter;
    public Activity mContext;
    private View.OnClickListener clickListener;
    protected SparseArray<View> mViews = new SparseArray<View>();
    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mPresenter == null) initPresenter();
        if (view == null) {
            view = inflater.inflate(PresenterUtils.getLayoutId(this), container, false);
            if (useEventBus()) EventBus.getDefault().register(this);
            clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    BaseFragment.this.onClick(view);
                }
            };
            initView();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.destroy();
        mPresenter = null;
        if (useEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    protected boolean useEventBus() {
        return false;
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) return;
        for (int id : ids) get(id).setOnClickListener(listener == null ? clickListener : listener);
    }

    public <T extends View> T get(int id) {
        T view = (T) mViews.get(id);
        if (view == null) mViews.put(id, view = (T) this.view.findViewById(id));
        return view;
    }

    public <T extends View> T getContentView() {
        return (T) view;
    }

    public void setText(int id, String str) {
        ((TextView) get(id)).setText(str);
    }

    @Override
    public void initPresenter() {
        mPresenter = PresenterUtils.initPresenter(this);
        if (mPresenter != null)
            mPresenter.mView = this;
    }
}
