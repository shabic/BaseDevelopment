package com.doomsday.base.utils;

import com.doomsday.base.data.BaseData;
import com.doomsday.base.listener.BaseListener;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/18.
 */

public class SuccessConsumer<T extends BaseData> implements Consumer<T> {

    BaseListener listener;

    public SuccessConsumer(BaseListener listener) {
        this.listener = listener;
    }

    @Override

    public void accept(@NonNull T t) throws Exception {

        if ("success".equals(t.getStatus()) && t.getCode() == 1000) {
            if (listener != null) listener.onSuccess();
        } else {
            if (listener != null) listener.onFialure(t.getErrmsg());
        }
    }
}
