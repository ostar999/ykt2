package com.google.common.util.concurrent;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.GwtCompatible;
import com.google.j2objc.annotations.ReflectionSupport;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@ReflectionSupport(ReflectionSupport.Level.FULL)
@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {
    private static final Runnable DONE;
    private static final Runnable INTERRUPTING;
    private static final int MAX_BUSY_WAIT_SPINS = 1000;
    private static final Runnable PARKED;

    public static final class DoNothingRunnable implements Runnable {
        private DoNothingRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    static {
        DONE = new DoNothingRunnable();
        INTERRUPTING = new DoNothingRunnable();
        PARKED = new DoNothingRunnable();
    }

    public abstract void afterRanInterruptibly(@NullableDecl T t2, @NullableDecl Throwable th);

    public final void interruptTask() {
        Runnable runnable = get();
        if ((runnable instanceof Thread) && compareAndSet(runnable, INTERRUPTING)) {
            try {
                ((Thread) runnable).interrupt();
            } finally {
                if (getAndSet(DONE) == PARKED) {
                    LockSupport.unpark((Thread) runnable);
                }
            }
        }
    }

    public abstract boolean isDone();

    @Override // java.lang.Runnable
    public final void run() {
        T tRunInterruptibly;
        Thread threadCurrentThread = Thread.currentThread();
        if (compareAndSet(null, threadCurrentThread)) {
            boolean z2 = !isDone();
            if (z2) {
                try {
                    tRunInterruptibly = runInterruptibly();
                } catch (Throwable th) {
                    if (!compareAndSet(threadCurrentThread, DONE)) {
                        Runnable runnable = get();
                        boolean z3 = false;
                        int i2 = 0;
                        while (true) {
                            Runnable runnable2 = INTERRUPTING;
                            if (runnable != runnable2 && runnable != PARKED) {
                                break;
                            }
                            i2++;
                            if (i2 > 1000) {
                                Runnable runnable3 = PARKED;
                                if (runnable == runnable3 || compareAndSet(runnable2, runnable3)) {
                                    z3 = Thread.interrupted() || z3;
                                    LockSupport.park(this);
                                }
                            } else {
                                Thread.yield();
                            }
                            runnable = get();
                        }
                        if (z3) {
                            threadCurrentThread.interrupt();
                        }
                    }
                    if (z2) {
                        afterRanInterruptibly(null, th);
                        return;
                    }
                    return;
                }
            } else {
                tRunInterruptibly = null;
            }
            if (!compareAndSet(threadCurrentThread, DONE)) {
                Runnable runnable4 = get();
                boolean z4 = false;
                int i3 = 0;
                while (true) {
                    Runnable runnable5 = INTERRUPTING;
                    if (runnable4 != runnable5 && runnable4 != PARKED) {
                        break;
                    }
                    i3++;
                    if (i3 > 1000) {
                        Runnable runnable6 = PARKED;
                        if (runnable4 == runnable6 || compareAndSet(runnable5, runnable6)) {
                            z4 = Thread.interrupted() || z4;
                            LockSupport.park(this);
                        }
                    } else {
                        Thread.yield();
                    }
                    runnable4 = get();
                }
                if (z4) {
                    threadCurrentThread.interrupt();
                }
            }
            if (z2) {
                afterRanInterruptibly(tRunInterruptibly, null);
            }
        }
    }

    public abstract T runInterruptibly() throws Exception;

    public abstract String toPendingString();

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        String str;
        Runnable runnable = get();
        if (runnable == DONE) {
            str = "running=[DONE]";
        } else if (runnable == INTERRUPTING) {
            str = "running=[INTERRUPTED]";
        } else if (runnable instanceof Thread) {
            str = "running=[RUNNING ON " + ((Thread) runnable).getName() + StrPool.BRACKET_END;
        } else {
            str = "running=[NOT STARTED YET]";
        }
        return str + ", " + toPendingString();
    }
}
