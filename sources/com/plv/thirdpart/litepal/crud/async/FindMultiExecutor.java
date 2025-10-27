package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.FindMultiCallback;

/* loaded from: classes5.dex */
public class FindMultiExecutor<T> extends AsyncExecutor {
    private FindMultiCallback<T> cb;

    public FindMultiCallback<T> getListener() {
        return this.cb;
    }

    public void listen(FindMultiCallback<T> findMultiCallback) {
        this.cb = findMultiCallback;
        execute();
    }
}
