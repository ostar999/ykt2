package com.aliyun.vod.log.core;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes2.dex */
public class LogService {
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public LogService(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }

    public void execute(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public void quit() {
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }
}
