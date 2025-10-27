package com.arialyy.aria.core.group;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
final class SimpleSubRetryQueue {
    private static volatile SimpleSubRetryQueue INSTANCE;
    private ExecutorService pool = new ThreadPoolExecutor(5, 100, 60, TimeUnit.SECONDS, new SynchronousQueue());

    private SimpleSubRetryQueue() {
    }

    public static synchronized SimpleSubRetryQueue getInstance() {
        if (INSTANCE == null) {
            synchronized (SimpleSubRetryQueue.class) {
                INSTANCE = new SimpleSubRetryQueue();
            }
        }
        return INSTANCE;
    }

    public void offer(AbsSubDLoadUtil absSubDLoadUtil) {
        this.pool.submit(absSubDLoadUtil.getLoader());
    }
}
