package com.doomsday.base.utils;

import com.doomsday.base.Base;

/**
 * Created by Administrator on 2017/6/30.
 */

public class PxUtil {

    public static int dip2px(float dpValue) {
        final float scale = Base.instance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
