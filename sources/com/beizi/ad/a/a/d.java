package com.beizi.ad.a.a;

import android.text.TextUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class d implements ThreadFactory {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f3694a = new AtomicInteger(1);

    /* renamed from: b, reason: collision with root package name */
    private final ThreadGroup f3695b;

    /* renamed from: c, reason: collision with root package name */
    private final AtomicInteger f3696c = new AtomicInteger(1);

    /* renamed from: d, reason: collision with root package name */
    private final String f3697d;

    /* renamed from: e, reason: collision with root package name */
    private final int f3698e;

    public d(int i2, String str) {
        this.f3698e = i2;
        SecurityManager securityManager = System.getSecurityManager();
        this.f3695b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (TextUtils.isEmpty(str)) {
            this.f3697d = "BeiZi-adsdk-background-" + f3694a.getAndIncrement() + "-thread-";
            return;
        }
        this.f3697d = str + f3694a.getAndIncrement() + "-thread-";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f3695b, runnable, this.f3697d + this.f3696c.getAndIncrement(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (this.f3698e == 1) {
            thread.setPriority(1);
        } else if (thread.getPriority() != 5) {
            thread.setPriority(3);
        } else {
            thread.setPriority(5);
        }
        return thread;
    }
}
