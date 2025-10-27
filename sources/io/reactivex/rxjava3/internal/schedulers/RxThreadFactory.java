package io.reactivex.rxjava3.internal.schedulers;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.annotations.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes8.dex */
public final class RxThreadFactory extends AtomicLong implements ThreadFactory {
    private static final long serialVersionUID = -7789753024099756196L;
    final boolean nonBlocking;
    final String prefix;
    final int priority;

    public static final class RxCustomThread extends Thread implements NonBlockingThread {
        public RxCustomThread(Runnable run, String name) {
            super(run, name);
        }
    }

    public RxThreadFactory(String prefix) {
        this(prefix, 5, false);
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(@NonNull Runnable r2) {
        String str = this.prefix + CharPool.DASHED + incrementAndGet();
        Thread rxCustomThread = this.nonBlocking ? new RxCustomThread(r2, str) : new Thread(r2, str);
        rxCustomThread.setPriority(this.priority);
        rxCustomThread.setDaemon(true);
        return rxCustomThread;
    }

    @Override // java.util.concurrent.atomic.AtomicLong
    public String toString() {
        return "RxThreadFactory[" + this.prefix + StrPool.BRACKET_END;
    }

    public RxThreadFactory(String prefix, int priority) {
        this(prefix, priority, false);
    }

    public RxThreadFactory(String prefix, int priority, boolean nonBlocking) {
        this.prefix = prefix;
        this.priority = priority;
        this.nonBlocking = nonBlocking;
    }
}
