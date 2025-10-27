package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes8.dex */
public final class ScheduledDirectTask extends AbstractDirectTask implements Callable<Void> {
    private static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectTask(Runnable runnable, boolean interruptOnCancel) {
        super(runnable, interruptOnCancel);
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.AbstractDirectTask, io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.AbstractDirectTask, java.util.concurrent.atomic.AtomicReference
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // java.util.concurrent.Callable
    public Void call() {
        this.runner = Thread.currentThread();
        try {
            try {
                this.runnable.run();
                return null;
            } finally {
                lazySet(AbstractDirectTask.FINISHED);
                this.runner = null;
            }
        } catch (Throwable th) {
            RxJavaPlugins.onError(th);
            throw th;
        }
    }
}
