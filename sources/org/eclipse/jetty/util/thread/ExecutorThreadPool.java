package org.eclipse.jetty.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ExecutorThreadPool extends AbstractLifeCycle implements ThreadPool, LifeCycle {
    private static final Logger LOG = Log.getLogger((Class<?>) ExecutorThreadPool.class);
    private final ExecutorService _executor;

    public ExecutorThreadPool(ExecutorService executorService) {
        this._executor = executorService;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean dispatch(Runnable runnable) {
        try {
            this._executor.execute(runnable);
            return true;
        } catch (RejectedExecutionException e2) {
            LOG.warn(e2);
            return false;
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        this._executor.shutdownNow();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getIdleThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return -1;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        return threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public int getThreads() {
        ExecutorService executorService = this._executor;
        if (executorService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executorService).getPoolSize();
        }
        return -1;
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public boolean isLowOnThreads() {
        ExecutorService executorService = this._executor;
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return false;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        return threadPoolExecutor.getPoolSize() == threadPoolExecutor.getMaximumPoolSize() && threadPoolExecutor.getQueue().size() >= threadPoolExecutor.getPoolSize() - threadPoolExecutor.getActiveCount();
    }

    @Override // org.eclipse.jetty.util.thread.ThreadPool
    public void join() throws InterruptedException {
        this._executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    public ExecutorThreadPool() {
        this(new ThreadPoolExecutor(256, 256, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue()));
    }

    public ExecutorThreadPool(int i2) {
        ThreadPoolExecutor threadPoolExecutor;
        if (i2 < 0) {
            threadPoolExecutor = new ThreadPoolExecutor(256, 256, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        } else {
            threadPoolExecutor = i2 == 0 ? new ThreadPoolExecutor(32, 256, 60L, TimeUnit.SECONDS, new SynchronousQueue()) : new ThreadPoolExecutor(32, 256, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(i2));
        }
        this(threadPoolExecutor);
    }

    public ExecutorThreadPool(int i2, int i3, long j2) {
        this(i2, i3, j2, TimeUnit.MILLISECONDS);
    }

    public ExecutorThreadPool(int i2, int i3, long j2, TimeUnit timeUnit) {
        this(i2, i3, j2, timeUnit, new LinkedBlockingQueue());
    }

    public ExecutorThreadPool(int i2, int i3, long j2, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this(new ThreadPoolExecutor(i2, i3, j2, timeUnit, blockingQueue));
    }
}
