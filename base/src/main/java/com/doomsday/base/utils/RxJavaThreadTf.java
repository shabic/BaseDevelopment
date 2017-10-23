package com.doomsday.base.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/26.
 */

public class RxJavaThreadTf<T> implements ObservableTransformer {
    @Override
    public ObservableSource<T> apply(@NonNull Observable upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
