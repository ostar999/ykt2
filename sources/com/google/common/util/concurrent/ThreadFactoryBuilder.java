package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.lang.Thread;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@CanIgnoreReturnValue
@GwtIncompatible
/* loaded from: classes4.dex */
public final class ThreadFactoryBuilder {
    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    private static ThreadFactory doBuild(ThreadFactoryBuilder threadFactoryBuilder) {
        final String str = threadFactoryBuilder.nameFormat;
        final Boolean bool = threadFactoryBuilder.daemon;
        final Integer num = threadFactoryBuilder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = threadFactoryBuilder.uncaughtExceptionHandler;
        ThreadFactory threadFactoryDefaultThreadFactory = threadFactoryBuilder.backingThreadFactory;
        if (threadFactoryDefaultThreadFactory == null) {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        final ThreadFactory threadFactory = threadFactoryDefaultThreadFactory;
        final AtomicLong atomicLong = str != null ? new AtomicLong(0L) : null;
        return new ThreadFactory() { // from class: com.google.common.util.concurrent.ThreadFactoryBuilder.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread threadNewThread = threadFactory.newThread(runnable);
                String str2 = str;
                if (str2 != null) {
                    threadNewThread.setName(ThreadFactoryBuilder.format(str2, Long.valueOf(atomicLong.getAndIncrement())));
                }
                Boolean bool2 = bool;
                if (bool2 != null) {
                    threadNewThread.setDaemon(bool2.booleanValue());
                }
                Integer num2 = num;
                if (num2 != null) {
                    threadNewThread.setPriority(num2.intValue());
                }
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = uncaughtExceptionHandler;
                if (uncaughtExceptionHandler2 != null) {
                    threadNewThread.setUncaughtExceptionHandler(uncaughtExceptionHandler2);
                }
                return threadNewThread;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String format(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }

    @CheckReturnValue
    public ThreadFactory build() {
        return doBuild(this);
    }

    public ThreadFactoryBuilder setDaemon(boolean z2) {
        this.daemon = Boolean.valueOf(z2);
        return this;
    }

    public ThreadFactoryBuilder setNameFormat(String str) {
        format(str, 0);
        this.nameFormat = str;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int i2) {
        Preconditions.checkArgument(i2 >= 1, "Thread priority (%s) must be >= %s", i2, 1);
        Preconditions.checkArgument(i2 <= 10, "Thread priority (%s) must be <= %s", i2, 10);
        this.priority = Integer.valueOf(i2);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.backingThreadFactory = (ThreadFactory) Preconditions.checkNotNull(threadFactory);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }
}
