package cn.hutool.core.thread;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class ThreadFactoryBuilder implements Builder<ThreadFactory> {
    private static final long serialVersionUID = 1;
    private ThreadFactory backingThreadFactory;
    private Boolean daemon;
    private String namePrefix;
    private Integer priority;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static ThreadFactoryBuilder create() {
        return new ThreadFactoryBuilder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Thread lambda$build$0(ThreadFactory threadFactory, String str, AtomicLong atomicLong, Boolean bool, Integer num, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Runnable runnable) {
        Thread threadNewThread = threadFactory.newThread(runnable);
        if (str != null) {
            threadNewThread.setName(str + atomicLong.getAndIncrement());
        }
        if (bool != null) {
            threadNewThread.setDaemon(bool.booleanValue());
        }
        if (num != null) {
            threadNewThread.setPriority(num.intValue());
        }
        if (uncaughtExceptionHandler != null) {
            threadNewThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        return threadNewThread;
    }

    public ThreadFactoryBuilder setDaemon(boolean z2) {
        this.daemon = Boolean.valueOf(z2);
        return this;
    }

    public ThreadFactoryBuilder setNamePrefix(String str) {
        this.namePrefix = str;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Thread priority ({}) must be >= {}", Integer.valueOf(i2), 1));
        }
        if (i2 > 10) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Thread priority ({}) must be <= {}", Integer.valueOf(i2), 10));
        }
        this.priority = Integer.valueOf(i2);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.backingThreadFactory = threadFactory;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    @Override // cn.hutool.core.builder.Builder
    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder threadFactoryBuilder) {
        ThreadFactory threadFactoryDefaultThreadFactory = threadFactoryBuilder.backingThreadFactory;
        if (threadFactoryDefaultThreadFactory == null) {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        final ThreadFactory threadFactory = threadFactoryDefaultThreadFactory;
        final String str = threadFactoryBuilder.namePrefix;
        final Boolean bool = threadFactoryBuilder.daemon;
        final Integer num = threadFactoryBuilder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = threadFactoryBuilder.uncaughtExceptionHandler;
        final AtomicLong atomicLong = str == null ? null : new AtomicLong();
        return new ThreadFactory() { // from class: cn.hutool.core.thread.d
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ThreadFactoryBuilder.lambda$build$0(threadFactory, str, atomicLong, bool, num, uncaughtExceptionHandler, runnable);
            }
        };
    }
}
