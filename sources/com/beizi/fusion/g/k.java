package com.beizi.fusion.g;

import android.text.TextUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class k implements ThreadFactory {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f5217a = new AtomicInteger(1);

    /* renamed from: b, reason: collision with root package name */
    private final ThreadGroup f5218b;

    /* renamed from: c, reason: collision with root package name */
    private final AtomicInteger f5219c = new AtomicInteger(1);

    /* renamed from: d, reason: collision with root package name */
    private final String f5220d;

    /* renamed from: e, reason: collision with root package name */
    private final int f5221e;

    public k(int i2, String str) {
        this.f5221e = i2;
        SecurityManager securityManager = System.getSecurityManager();
        this.f5218b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (TextUtils.isEmpty(str)) {
            this.f5220d = "afBg-" + f5217a.getAndIncrement() + "Td-";
            return;
        }
        this.f5220d = str + f5217a.getAndIncrement() + "Td-";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f5218b, runnable, this.f5220d + this.f5219c.getAndIncrement(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (this.f5221e == 1) {
            thread.setPriority(1);
        } else if (thread.getPriority() != 5) {
            thread.setPriority(3);
        } else {
            thread.setPriority(5);
        }
        return thread;
    }
}
