package com.umeng.socialize.handler;

import com.umeng.socialize.common.QueuedWork;

/* loaded from: classes6.dex */
public class QueuedWorkWrapper {
    public void onError(Runnable runnable) {
        QueuedWork.runInMain(runnable);
    }
}
