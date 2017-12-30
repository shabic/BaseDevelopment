package com.doomsday.base.ui;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * Created by shabi on 1/3/17.
 */

public class BasePresenter<V> implements IPresenter {

    private ArrayList<Disposable> subs;

    public V mView;

    @Override
    public void destroy() {
        if (subs == null) return;
        for (Disposable disposable : subs)
            if (!disposable.isDisposed()) disposable.dispose();
        subs.clear();
        subs = null;
    }

    protected void addTask(Disposable disposable) {
        if (subs == null) subs = new ArrayList<>();
        if (subs != null && !subs.contains(disposable)) subs.add(disposable);
    }

    protected void clear(Disposable disposable) {
        if (subs != null) subs.remove(disposable);
    }
}
