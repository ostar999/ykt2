package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
public final class ExecutionList {
    private static final Logger log = Logger.getLogger(ExecutionList.class.getName());

    @GuardedBy("this")
    private boolean executed;

    @NullableDecl
    @GuardedBy("this")
    private RunnableExecutorPair runnables;

    public static final class RunnableExecutorPair {
        final Executor executor;

        @NullableDecl
        RunnableExecutorPair next;
        final Runnable runnable;

        public RunnableExecutorPair(Runnable runnable, Executor executor, RunnableExecutorPair runnableExecutorPair) {
            this.runnable = runnable;
            this.executor = executor;
            this.next = runnableExecutorPair;
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (this.executed) {
                executeListener(runnable, executor);
            } else {
                this.runnables = new RunnableExecutorPair(runnable, executor, this.runnables);
            }
        }
    }

    public void execute() {
        synchronized (this) {
            if (this.executed) {
                return;
            }
            this.executed = true;
            RunnableExecutorPair runnableExecutorPair = this.runnables;
            RunnableExecutorPair runnableExecutorPair2 = null;
            this.runnables = null;
            while (runnableExecutorPair != null) {
                RunnableExecutorPair runnableExecutorPair3 = runnableExecutorPair.next;
                runnableExecutorPair.next = runnableExecutorPair2;
                runnableExecutorPair2 = runnableExecutorPair;
                runnableExecutorPair = runnableExecutorPair3;
            }
            while (runnableExecutorPair2 != null) {
                executeListener(runnableExecutorPair2.runnable, runnableExecutorPair2.executor);
                runnableExecutorPair2 = runnableExecutorPair2.next;
            }
        }
    }
}
