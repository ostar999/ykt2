package com.aliyun.sls.android.producer.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ThreadUtils {
    private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    private ThreadUtils() {
    }

    public static Executor cachedExecutors() {
        return CACHED_THREAD_POOL;
    }

    public static void exec(Runnable r2) {
        CACHED_THREAD_POOL.execute(r2);
    }
}
