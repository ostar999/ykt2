package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;

/* loaded from: classes8.dex */
final class RunnableDisposable extends ReferenceDisposable<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    public RunnableDisposable(Runnable value) {
        super(value);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }

    @Override // io.reactivex.rxjava3.disposables.ReferenceDisposable
    public void onDisposed(@NonNull Runnable value) {
        value.run();
    }
}
