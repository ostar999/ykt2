package com.psychiatrygarden.utils;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes6.dex */
public final class AsyncHandler {
    private static final Handler sHandler;
    private static final HandlerThread sHandlerThread;

    static {
        HandlerThread handlerThread = new HandlerThread("AsyncHandler");
        sHandlerThread = handlerThread;
        handlerThread.start();
        sHandler = new Handler(handlerThread.getLooper());
    }

    private AsyncHandler() {
    }

    public static void clear() {
        sHandler.removeCallbacksAndMessages(null);
    }

    public static void post(Runnable r2) {
        sHandler.post(r2);
    }

    public static void postDelayed(Runnable r2, int delayMillis) {
        sHandler.postDelayed(r2, delayMillis);
    }

    public static void remove(Runnable r2) {
        sHandler.removeCallbacks(r2);
    }
}
