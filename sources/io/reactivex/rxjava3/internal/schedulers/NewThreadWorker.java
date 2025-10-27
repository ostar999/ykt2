package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public class NewThreadWorker extends Scheduler.Worker implements Disposable {
    volatile boolean disposed;
    private final ScheduledExecutorService executor;

    public NewThreadWorker(ThreadFactory threadFactory) {
        this.executor = SchedulerPoolFactory.create(threadFactory);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        if (this.disposed) {
            return;
        }
        this.disposed = true;
        this.executor.shutdownNow();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler.Worker
    @NonNull
    public Disposable schedule(@NonNull final Runnable run) {
        return schedule(run, 0L, null);
    }

    @NonNull
    public ScheduledRunnable scheduleActual(final Runnable run, long delayTime, @NonNull TimeUnit unit, @Nullable DisposableContainer parent) {
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(run), parent);
        if (parent != null && !parent.add(scheduledRunnable)) {
            return scheduledRunnable;
        }
        try {
            scheduledRunnable.setFuture(delayTime <= 0 ? this.executor.submit((Callable) scheduledRunnable) : this.executor.schedule((Callable) scheduledRunnable, delayTime, unit));
        } catch (RejectedExecutionException e2) {
            if (parent != null) {
                parent.remove(scheduledRunnable);
            }
            RxJavaPlugins.onError(e2);
        }
        return scheduledRunnable;
    }

    public Disposable scheduleDirect(final Runnable run, long delayTime, TimeUnit unit) {
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(run), true);
        try {
            scheduledDirectTask.setFuture(delayTime <= 0 ? this.executor.submit(scheduledDirectTask) : this.executor.schedule(scheduledDirectTask, delayTime, unit));
            return scheduledDirectTask;
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }

    public Disposable schedulePeriodicallyDirect(Runnable run, long initialDelay, long period, TimeUnit unit) {
        Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(run);
        if (period <= 0) {
            InstantPeriodicTask instantPeriodicTask = new InstantPeriodicTask(runnableOnSchedule, this.executor);
            try {
                instantPeriodicTask.setFirst(initialDelay <= 0 ? this.executor.submit(instantPeriodicTask) : this.executor.schedule(instantPeriodicTask, initialDelay, unit));
                return instantPeriodicTask;
            } catch (RejectedExecutionException e2) {
                RxJavaPlugins.onError(e2);
                return EmptyDisposable.INSTANCE;
            }
        }
        ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(runnableOnSchedule, true);
        try {
            scheduledDirectPeriodicTask.setFuture(this.executor.scheduleAtFixedRate(scheduledDirectPeriodicTask, initialDelay, period, unit));
            return scheduledDirectPeriodicTask;
        } catch (RejectedExecutionException e3) {
            RxJavaPlugins.onError(e3);
            return EmptyDisposable.INSTANCE;
        }
    }

    public void shutdown() {
        if (this.disposed) {
            return;
        }
        this.disposed = true;
        this.executor.shutdown();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler.Worker
    @NonNull
    public Disposable schedule(@NonNull final Runnable action, long delayTime, @NonNull TimeUnit unit) {
        return this.disposed ? EmptyDisposable.INSTANCE : scheduleActual(action, delayTime, unit, null);
    }
}
