package com.doomsday;

import android.view.View;

import com.doomsday.base.annotation.ContentView;
import com.doomsday.base.ui.BaseActivity;
import com.doomsday.base.utils.ToastUtil;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    public void initView() {


    }

    @Override
    protected boolean autoHideInputMethodWindow() {
        return true;
    }

    public void test(View view) {
        ToastUtil.show("test");
    }
}