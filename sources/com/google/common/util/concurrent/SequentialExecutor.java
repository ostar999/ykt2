package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;

@GwtIncompatible
/* loaded from: classes4.dex */
final class SequentialExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    private final Executor executor;

    @GuardedBy("queue")
    private final Deque<Runnable> queue = new ArrayDeque();

    @GuardedBy("queue")
    private WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    @GuardedBy("queue")
    private long workerRunCount = 0;
    private final QueueWorker worker = new QueueWorker();

    public final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
        
            r1 = r1 | java.lang.Thread.interrupted();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:
        
            r3.run();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0055, code lost:
        
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0056, code lost:
        
            com.google.common.util.concurrent.SequentialExecutor.log.log(java.util.logging.Level.SEVERE, "Exception while executing runnable " + r3, (java.lang.Throwable) r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void workOnQueue() {
            /*
                r8 = this;
                r0 = 0
                r1 = r0
            L2:
                com.google.common.util.concurrent.SequentialExecutor r2 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L74
                java.util.Deque r2 = com.google.common.util.concurrent.SequentialExecutor.access$100(r2)     // Catch: java.lang.Throwable -> L74
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L74
                if (r0 != 0) goto L2b
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = com.google.common.util.concurrent.SequentialExecutor.access$200(r0)     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch: java.lang.Throwable -> L71
                if (r0 != r3) goto L20
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L71
                if (r1 == 0) goto L1f
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L1f:
                return
            L20:
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor.access$308(r0)     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor.access$202(r0, r3)     // Catch: java.lang.Throwable -> L71
                r0 = 1
            L2b:
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L71
                java.util.Deque r3 = com.google.common.util.concurrent.SequentialExecutor.access$100(r3)     // Catch: java.lang.Throwable -> L71
                java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L71
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch: java.lang.Throwable -> L71
                if (r3 != 0) goto L4b
                com.google.common.util.concurrent.SequentialExecutor r0 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch: java.lang.Throwable -> L71
                com.google.common.util.concurrent.SequentialExecutor.access$202(r0, r3)     // Catch: java.lang.Throwable -> L71
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L71
                if (r1 == 0) goto L4a
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L4a:
                return
            L4b:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L71
                boolean r2 = java.lang.Thread.interrupted()     // Catch: java.lang.Throwable -> L74
                r1 = r1 | r2
                r3.run()     // Catch: java.lang.RuntimeException -> L55 java.lang.Throwable -> L74
                goto L2
            L55:
                r2 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.SequentialExecutor.access$400()     // Catch: java.lang.Throwable -> L74
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L74
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74
                r6.<init>()     // Catch: java.lang.Throwable -> L74
                java.lang.String r7 = "Exception while executing runnable "
                r6.append(r7)     // Catch: java.lang.Throwable -> L74
                r6.append(r3)     // Catch: java.lang.Throwable -> L74
                java.lang.String r3 = r6.toString()     // Catch: java.lang.Throwable -> L74
                r4.log(r5, r3, r2)     // Catch: java.lang.Throwable -> L74
                goto L2
            L71:
                r0 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L71
                throw r0     // Catch: java.lang.Throwable -> L74
            L74:
                r0 = move-exception
                if (r1 == 0) goto L7e
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L7e:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                workOnQueue();
            } catch (Error e2) {
                synchronized (SequentialExecutor.this.queue) {
                    SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e2;
                }
            }
        }
    }

    public enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    public SequentialExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    public static /* synthetic */ long access$308(SequentialExecutor sequentialExecutor) {
        long j2 = sequentialExecutor.workerRunCount;
        sequentialExecutor.workerRunCount = 1 + j2;
        return j2;
    }

    @Override // java.util.concurrent.Executor
    public void execute(final Runnable runnable) {
        WorkerRunningState workerRunningState;
        Preconditions.checkNotNull(runnable);
        synchronized (this.queue) {
            WorkerRunningState workerRunningState2 = this.workerRunningState;
            if (workerRunningState2 != WorkerRunningState.RUNNING && workerRunningState2 != (workerRunningState = WorkerRunningState.QUEUED)) {
                long j2 = this.workerRunCount;
                Runnable runnable2 = new Runnable() { // from class: com.google.common.util.concurrent.SequentialExecutor.1
                    @Override // java.lang.Runnable
                    public void run() {
                        runnable.run();
                    }
                };
                this.queue.add(runnable2);
                WorkerRunningState workerRunningState3 = WorkerRunningState.QUEUING;
                this.workerRunningState = workerRunningState3;
                try {
                    this.executor.execute(this.worker);
                    if (this.workerRunningState != workerRunningState3) {
                        return;
                    }
                    synchronized (this.queue) {
                        if (this.workerRunCount == j2 && this.workerRunningState == workerRunningState3) {
                            this.workerRunningState = workerRunningState;
                        }
                    }
                    return;
                } catch (Error | RuntimeException e2) {
                    synchronized (this.queue) {
                        WorkerRunningState workerRunningState4 = this.workerRunningState;
                        if ((workerRunningState4 != WorkerRunningState.IDLE && workerRunningState4 != WorkerRunningState.QUEUING) || !this.queue.removeLastOccurrence(runnable2)) {
                            z = false;
                        }
                        if (!(e2 instanceof RejectedExecutionException) || z) {
                            throw e2;
                        }
                    }
                    return;
                }
            }
            this.queue.add(runnable);
        }
    }
}
