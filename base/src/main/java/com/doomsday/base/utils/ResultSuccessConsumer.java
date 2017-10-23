package com.doomsday.base.utils;

import com.doomsday.base.data.BackModel;
import com.doomsday.base.listener.ResultListener;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/18.
 */

public class ResultSuccessConsumer<T> implements Consumer<BackModel<T>> {

    ResultListener<T> listener;

    public ResultSuccessConsumer(ResultListener<T> listener) {
        this.listener = listener;
    }


    @Override
    public void accept(@NonNull BackModel<T> t) throws Exception {
        if ("success".equals(t.getStatus()) && t.getCode() == 1000) {
            if (listener != null) listener.onSuccess(t.getResult());
        } else {
            if (listener != null) listener.onFialure(t.getErrmsg());
        }
    }
}
