package com.doomsday;

import android.app.Application;

import com.doomsday.base.Base;

/**
 * Created by 11076 on 2017/11/12.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Base.initBase(this);
    }
}
