package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.FindCallback;

/* loaded from: classes5.dex */
public class FindExecutor<T> extends AsyncExecutor {
    private FindCallback<T> cb;

    public FindCallback<T> getListener() {
        return this.cb;
    }

    public void listen(FindCallback<T> findCallback) {
        this.cb = findCallback;
        execute();
    }
}
