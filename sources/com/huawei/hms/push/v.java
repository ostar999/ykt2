package com.huawei.hms.push;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class v {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f8052a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public static ThreadPoolExecutor f8053b = new ThreadPoolExecutor(1, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public static ThreadPoolExecutor a() {
        ThreadPoolExecutor threadPoolExecutor;
        synchronized (f8052a) {
            threadPoolExecutor = f8053b;
        }
        return threadPoolExecutor;
    }
}
