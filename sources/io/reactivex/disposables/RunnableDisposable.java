package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
final class RunnableDisposable extends ReferenceDisposable<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    public RunnableDisposable(Runnable runnable) {
        super(runnable);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }

    @Override // io.reactivex.disposables.ReferenceDisposable
    public void onDisposed(@NonNull Runnable runnable) {
        runnable.run();
    }
}
