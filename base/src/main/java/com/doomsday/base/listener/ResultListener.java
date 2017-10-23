package com.doomsday.base.listener;

/**
 * Created by Administrator on 2016/12/21.
 */

public abstract class ResultListener<T> implements BaseListener {

    public abstract void onSuccess(T result);

    @Override
    public void onSuccess() {

    }
}
