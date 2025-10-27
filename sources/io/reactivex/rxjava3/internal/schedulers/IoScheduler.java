package io.reactivex.rxjava3.internal.schedulers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class IoScheduler extends Scheduler {
    static final RxThreadFactory EVICTOR_THREAD_FACTORY;
    private static final String EVICTOR_THREAD_NAME_PREFIX = "RxCachedWorkerPoolEvictor";
    public static final long KEEP_ALIVE_TIME_DEFAULT = 60;
    private static final String KEY_IO_PRIORITY = "rx3.io-priority";
    private static final String KEY_SCHEDULED_RELEASE = "rx3.io-scheduled-release";
    static final CachedWorkerPool NONE;
    static final ThreadWorker SHUTDOWN_THREAD_WORKER;
    static boolean USE_SCHEDULED_RELEASE = false;
    static final RxThreadFactory WORKER_THREAD_FACTORY;
    private static final String WORKER_THREAD_NAME_PREFIX = "RxCachedThreadScheduler";
    final AtomicReference<CachedWorkerPool> pool;
    final ThreadFactory threadFactory;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    private static final String KEY_KEEP_ALIVE_TIME = "rx3.io-keep-alive-time";
    private static final long KEEP_ALIVE_TIME = Long.getLong(KEY_KEEP_ALIVE_TIME, 60).longValue();

    public static final class CachedWorkerPool implements Runnable {
        final CompositeDisposable allWorkers;
        private final ScheduledExecutorService evictorService;
        private final Future<?> evictorTask;
        private final ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue;
        private final long keepAliveTime;
        private final ThreadFactory threadFactory;

        public CachedWorkerPool(long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool;
            ScheduledFuture<?> scheduledFutureScheduleWithFixedDelay;
            long nanos = unit != null ? unit.toNanos(keepAliveTime) : 0L;
            this.keepAliveTime = nanos;
            this.expiringWorkerQueue = new ConcurrentLinkedQueue<>();
            this.allWorkers = new CompositeDisposable();
            this.threadFactory = threadFactory;
            if (unit != null) {
                scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, IoScheduler.EVICTOR_THREAD_FACTORY);
                scheduledFutureScheduleWithFixedDelay = scheduledExecutorServiceNewScheduledThreadPool.scheduleWithFixedDelay(this, nanos, nanos, TimeUnit.NANOSECONDS);
            } else {
                scheduledExecutorServiceNewScheduledThreadPool = null;
                scheduledFutureScheduleWithFixedDelay = null;
            }
            this.evictorService = scheduledExecutorServiceNewScheduledThreadPool;
            this.evictorTask = scheduledFutureScheduleWithFixedDelay;
        }

        public static void evictExpiredWorkers(ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue, CompositeDisposable allWorkers) {
            if (expiringWorkerQueue.isEmpty()) {
                return;
            }
            long jNow = now();
            Iterator<ThreadWorker> it = expiringWorkerQueue.iterator();
            while (it.hasNext()) {
                ThreadWorker next = it.next();
                if (next.getExpirationTime() > jNow) {
                    return;
                }
                if (expiringWorkerQueue.remove(next)) {
                    allWorkers.remove(next);
                }
            }
        }

        public static long now() {
            return System.nanoTime();
        }

        public ThreadWorker get() {
            if (this.allWorkers.isDisposed()) {
                return IoScheduler.SHUTDOWN_THREAD_WORKER;
            }
            while (!this.expiringWorkerQueue.isEmpty()) {
                ThreadWorker threadWorkerPoll = this.expiringWorkerQueue.poll();
                if (threadWorkerPoll != null) {
                    return threadWorkerPoll;
                }
            }
            ThreadWorker threadWorker = new ThreadWorker(this.threadFactory);
            this.allWorkers.add(threadWorker);
            return threadWorker;
        }

        public void release(ThreadWorker threadWorker) {
            threadWorker.setExpirationTime(now() + this.keepAliveTime);
            this.expiringWorkerQueue.offer(threadWorker);
        }

        @Override // java.lang.Runnable
        public void run() {
            evictExpiredWorkers(this.expiringWorkerQueue, this.allWorkers);
        }

        public void shutdown() {
            this.allWorkers.dispose();
            Future<?> future = this.evictorTask;
            if (future != null) {
                future.cancel(true);
            }
            ScheduledExecutorService scheduledExecutorService = this.evictorService;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    public static final class EventLoopWorker extends Scheduler.Worker implements Runnable {
        private final CachedWorkerPool pool;
        private final ThreadWorker threadWorker;
        final AtomicBoolean once = new AtomicBoolean();
        private final CompositeDisposable tasks = new CompositeDisposable();

        public EventLoopWorker(CachedWorkerPool pool) {
            this.pool = pool;
            this.threadWorker = pool.get();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.once.compareAndSet(false, true)) {
                this.tasks.dispose();
                if (IoScheduler.USE_SCHEDULED_RELEASE) {
                    this.threadWorker.scheduleActual(this, 0L, TimeUnit.NANOSECONDS, null);
                } else {
                    this.pool.release(this.threadWorker);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.once.get();
        }

        @Override // java.lang.Runnable
        public void run() {
            this.pool.release(this.threadWorker);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable action, long delayTime, @NonNull TimeUnit unit) {
            return this.tasks.isDisposed() ? EmptyDisposable.INSTANCE : this.threadWorker.scheduleActual(action, delayTime, unit, this.tasks);
        }
    }

    public static final class ThreadWorker extends NewThreadWorker {
        long expirationTime;

        public ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
            this.expirationTime = 0L;
        }

        public long getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
        }
    }

    static {
        ThreadWorker threadWorker = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
        SHUTDOWN_THREAD_WORKER = threadWorker;
        threadWorker.dispose();
        int iMax = Math.max(1, Math.min(10, Integer.getInteger(KEY_IO_PRIORITY, 5).intValue()));
        RxThreadFactory rxThreadFactory = new RxThreadFactory(WORKER_THREAD_NAME_PREFIX, iMax);
        WORKER_THREAD_FACTORY = rxThreadFactory;
        EVICTOR_THREAD_FACTORY = new RxThreadFactory(EVICTOR_THREAD_NAME_PREFIX, iMax);
        USE_SCHEDULED_RELEASE = Boolean.getBoolean(KEY_SCHEDULED_RELEASE);
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(0L, null, rxThreadFactory);
        NONE = cachedWorkerPool;
        cachedWorkerPool.shutdown();
    }

    public IoScheduler() {
        this(WORKER_THREAD_FACTORY);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get());
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        AtomicReference<CachedWorkerPool> atomicReference = this.pool;
        CachedWorkerPool cachedWorkerPool = NONE;
        CachedWorkerPool andSet = atomicReference.getAndSet(cachedWorkerPool);
        if (andSet != cachedWorkerPool) {
            andSet.shutdown();
        }
    }

    public int size() {
        return this.pool.get().allWorkers.size();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, this.threadFactory);
        if (j.a(this.pool, NONE, cachedWorkerPool)) {
            return;
        }
        cachedWorkerPool.shutdown();
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.pool = new AtomicReference<>(NONE);
        start();
    }
}
