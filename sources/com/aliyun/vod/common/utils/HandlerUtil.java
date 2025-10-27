package com.aliyun.vod.common.utils;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes2.dex */
public class HandlerUtil {

    /* renamed from: com.aliyun.vod.common.utils.HandlerUtil$1, reason: invalid class name */
    public static class AnonymousClass1 implements Runnable {
        final /* synthetic */ Looper val$looper;

        public AnonymousClass1(Looper looper) {
            this.val$looper = looper;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.val$looper.quit();
        }
    }

    public static void quitSafely(Handler handler) {
        quitSafely18(handler.getLooper());
    }

    @TargetApi(18)
    private static void quitSafely18(Looper looper) {
        looper.quitSafely();
    }

    @TargetApi(18)
    private static void quitSafely18(HandlerThread handlerThread) {
        handlerThread.quitSafely();
    }

    public static void quitSafely(HandlerThread handlerThread) {
        quitSafely18(handlerThread);
    }
}
