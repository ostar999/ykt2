package com.alibaba.sdk.android.httpdns.f;

import java.util.concurrent.ThreadFactory;

/* loaded from: classes2.dex */
final class a {

    /* renamed from: a, reason: collision with root package name */
    private final ThreadFactory f2775a = new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.f.a.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "report_thread");
            thread.setDaemon(false);
            thread.setUncaughtExceptionHandler(new com.alibaba.sdk.android.httpdns.c.a());
            return thread;
        }
    };
}
