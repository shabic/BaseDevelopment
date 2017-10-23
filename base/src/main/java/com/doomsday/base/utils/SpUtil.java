package com.doomsday.base.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.doomsday.base.Base;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SpUtil {

    public static SharedPreferences getInstance() {
        return Base.instance.getSharedPreferences(Base.instance.getPackageName(), Activity.MODE_PRIVATE);
    }
}
