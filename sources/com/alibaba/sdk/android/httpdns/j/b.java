package com.alibaba.sdk.android.httpdns.j;

import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class b {
    private static int index;

    public static ExecutorService b() {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 10, 30L, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.j.b.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "httpdns" + b.c());
                thread.setPriority(4);
                thread.setUncaughtExceptionHandler(new com.alibaba.sdk.android.httpdns.c.a());
                return thread;
            }
        }, new ThreadPoolExecutor.AbortPolicy());
        return new ExecutorService() { // from class: com.alibaba.sdk.android.httpdns.j.b.2
            @Override // java.util.concurrent.ExecutorService
            public boolean awaitTermination(long j2, TimeUnit timeUnit) {
                return threadPoolExecutor.awaitTermination(j2, timeUnit);
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                try {
                    threadPoolExecutor.execute(runnable);
                } catch (Exception e2) {
                    HttpDnsLog.e("too many request ?", e2);
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) {
                try {
                    return threadPoolExecutor.invokeAll(collection);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j2, TimeUnit timeUnit) {
                try {
                    return threadPoolExecutor.invokeAll(collection, j2, timeUnit);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> T invokeAny(Collection<? extends Callable<T>> collection) {
                try {
                    return (T) threadPoolExecutor.invokeAny(collection);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j2, TimeUnit timeUnit) {
                try {
                    return (T) threadPoolExecutor.invokeAny(collection, j2, timeUnit);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public boolean isShutdown() {
                return threadPoolExecutor.isShutdown();
            }

            @Override // java.util.concurrent.ExecutorService
            public boolean isTerminated() {
                return threadPoolExecutor.isTerminated();
            }

            @Override // java.util.concurrent.ExecutorService
            public void shutdown() {
                threadPoolExecutor.shutdown();
            }

            @Override // java.util.concurrent.ExecutorService
            public List<Runnable> shutdownNow() {
                return threadPoolExecutor.shutdownNow();
            }

            @Override // java.util.concurrent.ExecutorService
            public Future<?> submit(Runnable runnable) {
                try {
                    return threadPoolExecutor.submit(runnable);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> Future<T> submit(Runnable runnable, T t2) {
                try {
                    return threadPoolExecutor.submit(runnable, t2);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }

            @Override // java.util.concurrent.ExecutorService
            public <T> Future<T> submit(Callable<T> callable) {
                try {
                    return threadPoolExecutor.submit(callable);
                } catch (RejectedExecutionException e2) {
                    HttpDnsLog.e("too many request ?", e2);
                    throw e2;
                }
            }
        };
    }

    public static /* synthetic */ int c() {
        int i2 = index;
        index = i2 + 1;
        return i2;
    }
}
