package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.CountCallback;

/* loaded from: classes5.dex */
public class CountExecutor extends AsyncExecutor {
    private CountCallback cb;

    public CountCallback getListener() {
        return this.cb;
    }

    public void listen(CountCallback countCallback) {
        this.cb = countCallback;
        execute();
    }
}
