package cn.hutool.core.thread;

import cn.hutool.core.text.CharSequenceUtil;
import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class NamedThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final Thread.UncaughtExceptionHandler handler;
    private final boolean isDaemon;
    private final String prefix;
    private final AtomicInteger threadNumber;

    public NamedThreadFactory(String str, boolean z2) {
        this(str, null, z2);
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.group, runnable, CharSequenceUtil.format("{}{}", this.prefix, Integer.valueOf(this.threadNumber.getAndIncrement())));
        if (thread.isDaemon()) {
            if (!this.isDaemon) {
                thread.setDaemon(false);
            }
        } else if (this.isDaemon) {
            thread.setDaemon(true);
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.handler;
        if (uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        if (5 != thread.getPriority()) {
            thread.setPriority(5);
        }
        return thread;
    }

    public NamedThreadFactory(String str, ThreadGroup threadGroup, boolean z2) {
        this(str, threadGroup, z2, null);
    }

    public NamedThreadFactory(String str, ThreadGroup threadGroup, boolean z2, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.threadNumber = new AtomicInteger(1);
        this.prefix = CharSequenceUtil.isBlank(str) ? "Hutool" : str;
        this.group = threadGroup == null ? ThreadUtil.currentThreadGroup() : threadGroup;
        this.isDaemon = z2;
        this.handler = uncaughtExceptionHandler;
    }
}
