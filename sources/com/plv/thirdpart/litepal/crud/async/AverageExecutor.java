package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.AverageCallback;

/* loaded from: classes5.dex */
public class AverageExecutor extends AsyncExecutor {
    private AverageCallback cb;

    public AverageCallback getListener() {
        return this.cb;
    }

    public void listen(AverageCallback averageCallback) {
        this.cb = averageCallback;
        execute();
    }
}
