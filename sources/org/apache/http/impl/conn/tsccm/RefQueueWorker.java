package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;

@Deprecated
/* loaded from: classes9.dex */
public class RefQueueWorker implements Runnable {
    protected final RefQueueHandler refHandler;
    protected final ReferenceQueue<?> refQueue;
    protected volatile Thread workerThread;

    public RefQueueWorker(ReferenceQueue<?> referenceQueue, RefQueueHandler refQueueHandler) {
        if (referenceQueue == null) {
            throw new IllegalArgumentException("Queue must not be null.");
        }
        if (refQueueHandler == null) {
            throw new IllegalArgumentException("Handler must not be null.");
        }
        this.refQueue = referenceQueue;
        this.refHandler = refQueueHandler;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        if (this.workerThread == null) {
            this.workerThread = Thread.currentThread();
        }
        while (this.workerThread == Thread.currentThread()) {
            try {
                this.refHandler.handleReference(this.refQueue.remove());
            } catch (InterruptedException unused) {
            }
        }
    }

    public void shutdown() {
        Thread thread = this.workerThread;
        if (thread != null) {
            this.workerThread = null;
            thread.interrupt();
        }
    }

    public String toString() {
        return "RefQueueWorker::" + this.workerThread;
    }
}
