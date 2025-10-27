package cn.hutool.core.thread;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.util.ObjectUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ExecutorBuilder implements Builder<ThreadPoolExecutor> {
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;
    private static final long serialVersionUID = 1;
    private Boolean allowCoreThreadTimeOut;
    private int corePoolSize;
    private RejectedExecutionHandler handler;
    private ThreadFactory threadFactory;
    private BlockingQueue<Runnable> workQueue;
    private int maxPoolSize = Integer.MAX_VALUE;
    private long keepAliveTime = TimeUnit.SECONDS.toNanos(60);

    public static ExecutorBuilder create() {
        return new ExecutorBuilder();
    }

    public ExecutorService buildFinalizable() {
        return new FinalizableDelegatedExecutorService(build());
    }

    public ExecutorBuilder setAllowCoreThreadTimeOut(boolean z2) {
        this.allowCoreThreadTimeOut = Boolean.valueOf(z2);
        return this;
    }

    public ExecutorBuilder setCorePoolSize(int i2) {
        this.corePoolSize = i2;
        return this;
    }

    public ExecutorBuilder setHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.handler = rejectedExecutionHandler;
        return this;
    }

    public ExecutorBuilder setKeepAliveTime(long j2, TimeUnit timeUnit) {
        return setKeepAliveTime(timeUnit.toNanos(j2));
    }

    public ExecutorBuilder setMaxPoolSize(int i2) {
        this.maxPoolSize = i2;
        return this;
    }

    public ExecutorBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public ExecutorBuilder setWorkQueue(BlockingQueue<Runnable> blockingQueue) {
        this.workQueue = blockingQueue;
        return this;
    }

    public ExecutorBuilder useArrayBlockingQueue(int i2) {
        return setWorkQueue(new ArrayBlockingQueue(i2));
    }

    public ExecutorBuilder useSynchronousQueue() {
        return useSynchronousQueue(false);
    }

    @Override // cn.hutool.core.builder.Builder
    public ThreadPoolExecutor build() {
        return build(this);
    }

    public ExecutorBuilder setKeepAliveTime(long j2) {
        this.keepAliveTime = j2;
        return this;
    }

    public ExecutorBuilder useSynchronousQueue(boolean z2) {
        return setWorkQueue(new SynchronousQueue(z2));
    }

    private static ThreadPoolExecutor build(ExecutorBuilder executorBuilder) {
        int i2 = executorBuilder.corePoolSize;
        int i3 = executorBuilder.maxPoolSize;
        long j2 = executorBuilder.keepAliveTime;
        BlockingQueue synchronousQueue = executorBuilder.workQueue;
        if (synchronousQueue == null) {
            synchronousQueue = i2 <= 0 ? new SynchronousQueue() : new LinkedBlockingQueue(1024);
        }
        BlockingQueue blockingQueue = synchronousQueue;
        ThreadFactory threadFactoryDefaultThreadFactory = executorBuilder.threadFactory;
        if (threadFactoryDefaultThreadFactory == null) {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i2, i3, j2, TimeUnit.NANOSECONDS, blockingQueue, threadFactoryDefaultThreadFactory, (RejectedExecutionHandler) ObjectUtil.defaultIfNull(executorBuilder.handler, RejectPolicy.ABORT.getValue()));
        Boolean bool = executorBuilder.allowCoreThreadTimeOut;
        if (bool != null) {
            threadPoolExecutor.allowCoreThreadTimeOut(bool.booleanValue());
        }
        return threadPoolExecutor;
    }
}
