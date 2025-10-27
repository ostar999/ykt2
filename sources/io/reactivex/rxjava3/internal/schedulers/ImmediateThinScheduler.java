package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class ImmediateThinScheduler extends Scheduler {
    static final Disposable DISPOSED;
    public static final Scheduler INSTANCE = new ImmediateThinScheduler();
    static final Scheduler.Worker WORKER = new ImmediateThinWorker();

    static {
        Disposable disposableB = b.b();
        DISPOSED = disposableB;
        disposableB.dispose();
    }

    private ImmediateThinScheduler() {
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return WORKER;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run) {
        run.run();
        return DISPOSED;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable run, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
    }

    public static final class ImmediateThinWorker extends Scheduler.Worker {
        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable run) {
            run.run();
            return ImmediateThinScheduler.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedulePeriodically(@NonNull Runnable run, long initialDelay, long period, TimeUnit unit) {
            throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
    }
}
