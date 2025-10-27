package io.reactivex.rxjava3.internal.schedulers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ComputationScheduler extends Scheduler implements SchedulerMultiWorkerSupport {
    private static final String KEY_COMPUTATION_PRIORITY = "rx3.computation-priority";
    static final String KEY_MAX_THREADS = "rx3.computation-threads";
    static final int MAX_THREADS = cap(Runtime.getRuntime().availableProcessors(), Integer.getInteger(KEY_MAX_THREADS, 0).intValue());
    static final FixedSchedulerPool NONE;
    static final PoolWorker SHUTDOWN_WORKER;
    static final RxThreadFactory THREAD_FACTORY;
    private static final String THREAD_NAME_PREFIX = "RxComputationThreadPool";
    final AtomicReference<FixedSchedulerPool> pool;
    final ThreadFactory threadFactory;

    public static final class FixedSchedulerPool implements SchedulerMultiWorkerSupport {
        final int cores;
        final PoolWorker[] eventLoops;

        /* renamed from: n, reason: collision with root package name */
        long f27367n;

        public FixedSchedulerPool(int maxThreads, ThreadFactory threadFactory) {
            this.cores = maxThreads;
            this.eventLoops = new PoolWorker[maxThreads];
            for (int i2 = 0; i2 < maxThreads; i2++) {
                this.eventLoops[i2] = new PoolWorker(threadFactory);
            }
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport
        public void createWorkers(int number, SchedulerMultiWorkerSupport.WorkerCallback callback) {
            int i2 = this.cores;
            if (i2 == 0) {
                for (int i3 = 0; i3 < number; i3++) {
                    callback.onWorker(i3, ComputationScheduler.SHUTDOWN_WORKER);
                }
                return;
            }
            int i4 = ((int) this.f27367n) % i2;
            for (int i5 = 0; i5 < number; i5++) {
                callback.onWorker(i5, new EventLoopWorker(this.eventLoops[i4]));
                i4++;
                if (i4 == i2) {
                    i4 = 0;
                }
            }
            this.f27367n = i4;
        }

        public PoolWorker getEventLoop() {
            int i2 = this.cores;
            if (i2 == 0) {
                return ComputationScheduler.SHUTDOWN_WORKER;
            }
            PoolWorker[] poolWorkerArr = this.eventLoops;
            long j2 = this.f27367n;
            this.f27367n = 1 + j2;
            return poolWorkerArr[(int) (j2 % i2)];
        }

        public void shutdown() {
            for (PoolWorker poolWorker : this.eventLoops) {
                poolWorker.dispose();
            }
        }
    }

    public static final class PoolWorker extends NewThreadWorker {
        public PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

    static {
        PoolWorker poolWorker = new PoolWorker(new RxThreadFactory("RxComputationShutdown"));
        SHUTDOWN_WORKER = poolWorker;
        poolWorker.dispose();
        RxThreadFactory rxThreadFactory = new RxThreadFactory(THREAD_NAME_PREFIX, Math.max(1, Math.min(10, Integer.getInteger(KEY_COMPUTATION_PRIORITY, 5).intValue())), true);
        THREAD_FACTORY = rxThreadFactory;
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(0, rxThreadFactory);
        NONE = fixedSchedulerPool;
        fixedSchedulerPool.shutdown();
    }

    public ComputationScheduler() {
        this(THREAD_FACTORY);
    }

    public static int cap(int cpuCount, int paramThreads) {
        return (paramThreads <= 0 || paramThreads > cpuCount) ? cpuCount : paramThreads;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get().getEventLoop());
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport
    public void createWorkers(int number, SchedulerMultiWorkerSupport.WorkerCallback callback) {
        ObjectHelper.verifyPositive(number, "number > 0 required");
        this.pool.get().createWorkers(number, callback);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run, long delay, TimeUnit unit) {
        return this.pool.get().getEventLoop().scheduleDirect(run, delay, unit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable run, long initialDelay, long period, TimeUnit unit) {
        return this.pool.get().getEventLoop().schedulePeriodicallyDirect(run, initialDelay, period, unit);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        AtomicReference<FixedSchedulerPool> atomicReference = this.pool;
        FixedSchedulerPool fixedSchedulerPool = NONE;
        FixedSchedulerPool andSet = atomicReference.getAndSet(fixedSchedulerPool);
        if (andSet != fixedSchedulerPool) {
            andSet.shutdown();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(MAX_THREADS, this.threadFactory);
        if (j.a(this.pool, NONE, fixedSchedulerPool)) {
            return;
        }
        fixedSchedulerPool.shutdown();
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.pool = new AtomicReference<>(NONE);
        start();
    }

    public static final class EventLoopWorker extends Scheduler.Worker {
        private final ListCompositeDisposable both;
        volatile boolean disposed;
        private final PoolWorker poolWorker;
        private final ListCompositeDisposable serial;
        private final CompositeDisposable timed;

        public EventLoopWorker(PoolWorker poolWorker) {
            this.poolWorker = poolWorker;
            ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
            this.serial = listCompositeDisposable;
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            this.timed = compositeDisposable;
            ListCompositeDisposable listCompositeDisposable2 = new ListCompositeDisposable();
            this.both = listCompositeDisposable2;
            listCompositeDisposable2.add(listCompositeDisposable);
            listCompositeDisposable2.add(compositeDisposable);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.both.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable action) {
            return this.disposed ? EmptyDisposable.INSTANCE : this.poolWorker.scheduleActual(action, 0L, TimeUnit.MILLISECONDS, this.serial);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable action, long delayTime, @NonNull TimeUnit unit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            return this.poolWorker.scheduleActual(action, delayTime, unit, this.timed);
        }
    }
}
