package com.plv.thirdpart.litepal.crud.async;

import com.plv.thirdpart.litepal.crud.callback.SaveCallback;

/* loaded from: classes5.dex */
public class SaveExecutor extends AsyncExecutor {
    private SaveCallback cb;

    public SaveCallback getListener() {
        return this.cb;
    }

    public void listen(SaveCallback saveCallback) {
        this.cb = saveCallback;
        execute();
    }
}
