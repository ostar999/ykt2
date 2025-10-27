package cn.hutool.core.thread;

import cn.hutool.core.exceptions.UtilException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.Thread;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public class SyncFinisher implements Closeable {
    private CountDownLatch endLatch;
    private Thread.UncaughtExceptionHandler exceptionHandler;
    private ExecutorService executorService;
    private boolean isBeginAtSameTime;
    private final int threadSize;
    private final CountDownLatch beginLatch = new CountDownLatch(1);
    private final Set<Worker> workers = new LinkedHashSet();

    public abstract class Worker implements Runnable {
        public Worker() {
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            if (SyncFinisher.this.isBeginAtSameTime) {
                try {
                    SyncFinisher.this.beginLatch.await();
                } catch (InterruptedException e2) {
                    throw new UtilException(e2);
                }
            }
            try {
                work();
            } finally {
                SyncFinisher.this.endLatch.countDown();
            }
        }

        public abstract void work();
    }

    public SyncFinisher(int i2) {
        this.threadSize = i2;
    }

    private ExecutorService buildExecutor() {
        return ExecutorBuilder.create().setCorePoolSize(this.threadSize).setThreadFactory(new NamedThreadFactory("hutool-", null, false, this.exceptionHandler)).build();
    }

    public SyncFinisher addRepeatWorker(final Runnable runnable) {
        for (int i2 = 0; i2 < this.threadSize; i2++) {
            addWorker(new Worker() { // from class: cn.hutool.core.thread.SyncFinisher.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // cn.hutool.core.thread.SyncFinisher.Worker
                public void work() {
                    runnable.run();
                }
            });
        }
        return this;
    }

    public SyncFinisher addWorker(final Runnable runnable) {
        return addWorker(new Worker() { // from class: cn.hutool.core.thread.SyncFinisher.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // cn.hutool.core.thread.SyncFinisher.Worker
            public void work() {
                runnable.run();
            }
        });
    }

    public void clearWorker() {
        this.workers.clear();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        stop();
    }

    public long count() {
        return this.endLatch.getCount();
    }

    public SyncFinisher setBeginAtSameTime(boolean z2) {
        this.isBeginAtSameTime = z2;
        return this;
    }

    public SyncFinisher setExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.exceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public void start() throws InterruptedException {
        start(true);
    }

    public void stop() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
            this.executorService = null;
        }
        clearWorker();
    }

    public void stopNow() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdownNow();
            this.executorService = null;
        }
        clearWorker();
    }

    public synchronized SyncFinisher addWorker(Worker worker) {
        this.workers.add(worker);
        return this;
    }

    public void start(boolean z2) throws InterruptedException {
        this.endLatch = new CountDownLatch(this.workers.size());
        ExecutorService executorService = this.executorService;
        if (executorService == null || executorService.isShutdown()) {
            this.executorService = buildExecutor();
        }
        for (Worker worker : this.workers) {
            if (this.exceptionHandler != null) {
                this.executorService.execute(worker);
            } else {
                this.executorService.submit(worker);
            }
        }
        this.beginLatch.countDown();
        if (z2) {
            try {
                this.endLatch.await();
            } catch (InterruptedException e2) {
                throw new UtilException(e2);
            }
        }
    }
}
