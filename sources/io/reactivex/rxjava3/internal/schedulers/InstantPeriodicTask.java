package io.reactivex.rxjava3.internal.schedulers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
final class InstantPeriodicTask implements Callable<Void>, Disposable {
    static final FutureTask<Void> CANCELLED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    final ExecutorService executor;
    final AtomicReference<Future<?>> first = new AtomicReference<>();
    final AtomicReference<Future<?>> rest = new AtomicReference<>();
    Thread runner;
    final Runnable task;

    public InstantPeriodicTask(Runnable task, ExecutorService executor) {
        this.task = task;
        this.executor = executor;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        AtomicReference<Future<?>> atomicReference = this.first;
        FutureTask<Void> futureTask = CANCELLED;
        Future<?> andSet = atomicReference.getAndSet(futureTask);
        if (andSet != null && andSet != futureTask) {
            andSet.cancel(this.runner != Thread.currentThread());
        }
        Future<?> andSet2 = this.rest.getAndSet(futureTask);
        if (andSet2 == null || andSet2 == futureTask) {
            return;
        }
        andSet2.cancel(this.runner != Thread.currentThread());
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.first.get() == CANCELLED;
    }

    public void setFirst(Future<?> f2) {
        Future<?> future;
        do {
            future = this.first.get();
            if (future == CANCELLED) {
                f2.cancel(this.runner != Thread.currentThread());
                return;
            }
        } while (!j.a(this.first, future, f2));
    }

    public void setRest(Future<?> f2) {
        Future<?> future;
        do {
            future = this.rest.get();
            if (future == CANCELLED) {
                f2.cancel(this.runner != Thread.currentThread());
                return;
            }
        } while (!j.a(this.rest, future, f2));
    }

    @Override // java.util.concurrent.Callable
    public Void call() {
        this.runner = Thread.currentThread();
        try {
            this.task.run();
            this.runner = null;
            setRest(this.executor.submit(this));
            return null;
        } catch (Throwable th) {
            this.runner = null;
            RxJavaPlugins.onError(th);
            throw th;
        }
    }
}
