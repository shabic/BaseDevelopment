package com.doomsday.base.utils;

import com.doomsday.base.listener.BaseListener;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/18.
 */

public class ErrorConsumer implements Consumer<Throwable> {

    BaseListener listener;

    public ErrorConsumer(BaseListener listener) {
        this.listener = listener;
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            if (listener != null) listener.onFialure("网络超时");
        } else if (throwable instanceof JsonSyntaxException) {
            if (listener != null) listener.onFialure("json转换异常");
        } else {
            if (listener != null) listener.onFialure("网络异常");
        }
    }
}
