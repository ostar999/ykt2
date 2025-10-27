package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.UpdateOrDeleteCallback;

/* loaded from: classes5.dex */
public class UpdateOrDeleteExecutor extends AsyncExecutor {
    private UpdateOrDeleteCallback cb;

    public UpdateOrDeleteCallback getListener() {
        return this.cb;
    }

    public void listen(UpdateOrDeleteCallback updateOrDeleteCallback) {
        this.cb = updateOrDeleteCallback;
        execute();
    }
}
