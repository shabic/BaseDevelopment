package com.doomsday;

import android.view.View;

import com.doomsday.base.annotation.ContentView;
import com.doomsday.base.ui.BaseActivity;
import com.doomsday.base.utils.ToastUtil;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @Override
    public void initView() {
    }

    @Override
    public void xxx() {
        ToastUtil.show("xxxx");
    }

    public void test(View view) {
        mPresenter.test();
    }
}