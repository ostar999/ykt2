package com.umeng.analytics.pro;

import com.umeng.analytics.AnalyticsConfig;
import java.lang.Thread;

/* loaded from: classes6.dex */
public class p implements Thread.UncaughtExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f22916a;

    /* renamed from: b, reason: collision with root package name */
    private t f22917b;

    public p() {
        if (Thread.getDefaultUncaughtExceptionHandler() == this) {
            return;
        }
        this.f22916a = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void a(t tVar) {
        this.f22917b = tVar;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        a(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f22916a;
        if (uncaughtExceptionHandler == null || uncaughtExceptionHandler == Thread.getDefaultUncaughtExceptionHandler()) {
            return;
        }
        this.f22916a.uncaughtException(thread, th);
    }

    private void a(Throwable th) {
        if (AnalyticsConfig.CATCH_EXCEPTION) {
            this.f22917b.a(th);
        } else {
            this.f22917b.a(null);
        }
    }
}
