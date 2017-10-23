package com.doomsday.base.utils;

import android.widget.Toast;

import com.doomsday.base.Base;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ToastUtil {

    private static Toast toast;

    public static void show(final String msg) {
        Base.handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null)
                    toast = Toast.makeText(Base.instance, "", Toast.LENGTH_SHORT);
                toast.setText(msg);
                toast.show();
            }
        });
    }
}