package com.doomsday.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.doomsday.base.utils.PresenterUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/22.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseUi {

    public Context mContext;
    public Activity mActivity;
    public P mPresenter;
    protected SparseArray<View> mViews = new SparseArray<View>();
    private View.OnClickListener clickListener;
    private InputMethodManager inputMethodManager;
    private boolean hide = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        int layoutId = PresenterUtils.getLayoutId(this);
        if (layoutId > 0)
            setContentView(layoutId);
        if (useEventBus()) EventBus.getDefault().register(this);
        if (autoHideInputMethodWindow() && inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            hide = true;
        }
        mContext = mActivity = this;

        initPresenter();
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.this.onClick(view);
            }
        };
        if (this != null) {
            if (isDestroyed()) return;
            initView();
        }
    }

    public void onClick(View view) {

    }

    //如果需要自动隐藏输入框在子类返回true
    protected boolean autoHideInputMethodWindow() {
        return false;
    }

    protected boolean useEventBus() {
        return false;
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) return;
        for (int id : ids)
            get(id).setOnClickListener(listener == null ? clickListener : listener);
    }

    public <V extends View> V get(int id) {

        if (mViews != null) {
            V view = (V) mViews.get(id);
            if (view == null) {
                view = (V) findViewById(id);
                mViews.put(id, view);
            }
            return view;
        }
        return null;
    }

    public void setText(int id, String str) {
        ((TextView) get(id)).setText(str == null ? "" : str);
    }

    @Override
    protected void onDestroy() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.destroy();
        mPresenter = null;
        mViews.clear();
        mViews = null;
        if (hide) hideInputMethodWindow();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            Window window = getWindow();
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && hide) hideInputMethodWindow();
        return super.onTouchEvent(event);
    }

    public void hideInputMethodWindow() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null && inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void initPresenter() {
        mPresenter = PresenterUtils.initPresenter(this);
    }
}