package com.doomsday.base;

import android.app.Application;
import android.os.Handler;

import com.doomsday.base.utils.RtUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by Administrator on 2017/6/23.
 */

public class Base {

    public static Application instance;
    public static Handler handler;
    public static boolean showBody;

    public static void initBase(Application application) {
        instance = application;
        handler = new Handler();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void setBaseUrl(String url) {
        RtUtil.setBaseUrl(url);
    }

    //网络请求打印日志
    public static void showRequestBody(boolean showBody) {
        Base.showBody = showBody;
    }

}
