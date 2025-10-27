package com.aliyun.svideo.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ThreadUtils {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final int MAXIMUM_POOL_SIZE;
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE;
    private static final ThreadFactory THREAD_FACTORY;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = ThreadUtils.class.getName();

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        int iMax = Math.max(2, Math.min(iAvailableProcessors - 1, 4));
        CORE_POOL_SIZE = iMax;
        int i2 = (iAvailableProcessors * 2) + 1;
        MAXIMUM_POOL_SIZE = i2;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        POOL_WORK_QUEUE = linkedBlockingQueue;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.aliyun.svideo.common.utils.ThreadUtils.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "ThreadUtils #" + this.mCount.getAndIncrement());
            }
        };
        THREAD_FACTORY = threadFactory;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(iMax, i2, 30L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    public static void runOnSubThread(Runnable runnable) {
        ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTOR;
        if (threadPoolExecutor.getQueue().size() == 128 || threadPoolExecutor.isShutdown()) {
            Log.e(TAG, "线程池爆满警告，请查看是否开启了过多的耗时线程");
        } else {
            threadPoolExecutor.execute(runnable);
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0L);
    }

    public static void runOnUiThread(Runnable runnable, long j2) {
        sMainHandler.postDelayed(runnable, j2);
    }
}
