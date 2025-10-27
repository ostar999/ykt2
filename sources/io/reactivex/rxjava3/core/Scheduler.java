package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.schedulers.NewThreadWorker;
import io.reactivex.rxjava3.internal.schedulers.SchedulerWhen;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public abstract class Scheduler {
    static boolean IS_DRIFT_USE_NANOTIME = Boolean.getBoolean("rx3.scheduler.use-nanotime");
    static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = computeClockDrift(Long.getLong("rx3.scheduler.drift-tolerance", 15).longValue(), System.getProperty("rx3.scheduler.drift-tolerance-unit", "minutes"));

    public static final class DisposeTask implements Disposable, Runnable, SchedulerRunnableIntrospection {

        @NonNull
        final Runnable decoratedRun;

        @Nullable
        Thread runner;

        /* renamed from: w, reason: collision with root package name */
        @NonNull
        final Worker f27325w;

        public DisposeTask(@NonNull Runnable decoratedRun, @NonNull Worker w2) {
            this.decoratedRun = decoratedRun;
            this.f27325w = w2;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.runner == Thread.currentThread()) {
                Worker worker = this.f27325w;
                if (worker instanceof NewThreadWorker) {
                    ((NewThreadWorker) worker).shutdown();
                    return;
                }
            }
            this.f27325w.dispose();
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.decoratedRun;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.f27325w.isDisposed();
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runner = Thread.currentThread();
            try {
                this.decoratedRun.run();
            } finally {
            }
        }
    }

    public static final class PeriodicDirectTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
        volatile boolean disposed;

        @NonNull
        final Runnable run;

        @NonNull
        final Worker worker;

        public PeriodicDirectTask(@NonNull Runnable run, @NonNull Worker worker) {
            this.run = run;
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.run;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.disposed) {
                return;
            }
            try {
                this.run.run();
            } catch (Throwable th) {
                dispose();
                RxJavaPlugins.onError(th);
                throw th;
            }
        }
    }

    public static abstract class Worker implements Disposable {

        public final class PeriodicTask implements Runnable, SchedulerRunnableIntrospection {
            long count;

            @NonNull
            final Runnable decoratedRun;
            long lastNowNanoseconds;
            final long periodInNanoseconds;

            @NonNull
            final SequentialDisposable sd;
            long startInNanoseconds;

            public PeriodicTask(long firstStartInNanoseconds, @NonNull Runnable decoratedRun, long firstNowNanoseconds, @NonNull SequentialDisposable sd, long periodInNanoseconds) {
                this.decoratedRun = decoratedRun;
                this.sd = sd;
                this.periodInNanoseconds = periodInNanoseconds;
                this.lastNowNanoseconds = firstNowNanoseconds;
                this.startInNanoseconds = firstStartInNanoseconds;
            }

            @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
            public Runnable getWrappedRunnable() {
                return this.decoratedRun;
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r12 = this;
                    java.lang.Runnable r0 = r12.decoratedRun
                    r0.run()
                    io.reactivex.rxjava3.internal.disposables.SequentialDisposable r0 = r12.sd
                    boolean r0 = r0.isDisposed()
                    if (r0 != 0) goto L51
                    io.reactivex.rxjava3.core.Scheduler$Worker r0 = io.reactivex.rxjava3.core.Scheduler.Worker.this
                    java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.NANOSECONDS
                    long r2 = r0.now(r1)
                    long r4 = io.reactivex.rxjava3.core.Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS
                    long r6 = r2 + r4
                    long r8 = r12.lastNowNanoseconds
                    int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                    r6 = 1
                    if (r0 < 0) goto L34
                    long r10 = r12.periodInNanoseconds
                    long r8 = r8 + r10
                    long r8 = r8 + r4
                    int r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
                    if (r0 < 0) goto L2a
                    goto L34
                L2a:
                    long r4 = r12.startInNanoseconds
                    long r8 = r12.count
                    long r8 = r8 + r6
                    r12.count = r8
                    long r8 = r8 * r10
                    long r4 = r4 + r8
                    goto L43
                L34:
                    long r4 = r12.periodInNanoseconds
                    long r8 = r2 + r4
                    long r10 = r12.count
                    long r10 = r10 + r6
                    r12.count = r10
                    long r4 = r4 * r10
                    long r4 = r8 - r4
                    r12.startInNanoseconds = r4
                    r4 = r8
                L43:
                    r12.lastNowNanoseconds = r2
                    long r4 = r4 - r2
                    io.reactivex.rxjava3.internal.disposables.SequentialDisposable r0 = r12.sd
                    io.reactivex.rxjava3.core.Scheduler$Worker r2 = io.reactivex.rxjava3.core.Scheduler.Worker.this
                    io.reactivex.rxjava3.disposables.Disposable r1 = r2.schedule(r12, r4, r1)
                    r0.replace(r1)
                L51:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.core.Scheduler.Worker.PeriodicTask.run():void");
            }
        }

        public long now(@NonNull TimeUnit unit) {
            return Scheduler.computeNow(unit);
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable run) {
            return schedule(run, 0L, TimeUnit.NANOSECONDS);
        }

        @NonNull
        public abstract Disposable schedule(@NonNull Runnable run, long delay, @NonNull TimeUnit unit);

        @NonNull
        public Disposable schedulePeriodically(@NonNull Runnable run, final long initialDelay, final long period, @NonNull final TimeUnit unit) {
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(run);
            long nanos = unit.toNanos(period);
            long jNow = now(TimeUnit.NANOSECONDS);
            Disposable disposableSchedule = schedule(new PeriodicTask(jNow + unit.toNanos(initialDelay), runnableOnSchedule, jNow, sequentialDisposable2, nanos), initialDelay, unit);
            if (disposableSchedule == EmptyDisposable.INSTANCE) {
                return disposableSchedule;
            }
            sequentialDisposable.replace(disposableSchedule);
            return sequentialDisposable2;
        }
    }

    public static long clockDriftTolerance() {
        return CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
    }

    public static long computeClockDrift(long time, String timeUnit) {
        return "seconds".equalsIgnoreCase(timeUnit) ? TimeUnit.SECONDS.toNanos(time) : "milliseconds".equalsIgnoreCase(timeUnit) ? TimeUnit.MILLISECONDS.toNanos(time) : TimeUnit.MINUTES.toNanos(time);
    }

    public static long computeNow(TimeUnit unit) {
        return !IS_DRIFT_USE_NANOTIME ? unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS) : unit.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @NonNull
    public abstract Worker createWorker();

    public long now(@NonNull TimeUnit unit) {
        return computeNow(unit);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run) {
        return scheduleDirect(run, 0L, TimeUnit.NANOSECONDS);
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable run, long initialDelay, long period, @NonNull TimeUnit unit) {
        Worker workerCreateWorker = createWorker();
        PeriodicDirectTask periodicDirectTask = new PeriodicDirectTask(RxJavaPlugins.onSchedule(run), workerCreateWorker);
        Disposable disposableSchedulePeriodically = workerCreateWorker.schedulePeriodically(periodicDirectTask, initialDelay, period, unit);
        return disposableSchedulePeriodically == EmptyDisposable.INSTANCE ? disposableSchedulePeriodically : periodicDirectTask;
    }

    public void shutdown() {
    }

    public void start() {
    }

    @NonNull
    public <S extends Scheduler & Disposable> S when(@NonNull Function<Flowable<Flowable<Completable>>, Completable> combine) {
        Objects.requireNonNull(combine, "combine is null");
        return new SchedulerWhen(combine, this);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
        Worker workerCreateWorker = createWorker();
        DisposeTask disposeTask = new DisposeTask(RxJavaPlugins.onSchedule(run), workerCreateWorker);
        workerCreateWorker.schedule(disposeTask, delay, unit);
        return disposeTask;
    }
}
