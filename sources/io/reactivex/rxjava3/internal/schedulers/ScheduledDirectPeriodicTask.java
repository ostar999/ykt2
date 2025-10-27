package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class ScheduledDirectPeriodicTask extends AbstractDirectTask implements Runnable {
    private static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectPeriodicTask(Runnable runnable, boolean interruptOnCancel) {
        super(runnable, interruptOnCancel);
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.AbstractDirectTask, io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    @Override // java.lang.Runnable
    public void run() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            this.runner = null;
        } catch (Throwable th) {
            dispose();
            this.runner = null;
            RxJavaPlugins.onError(th);
            throw th;
        }
    }

    @Override // io.reactivex.rxjava3.internal.schedulers.AbstractDirectTask, java.util.concurrent.atomic.AtomicReference
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
