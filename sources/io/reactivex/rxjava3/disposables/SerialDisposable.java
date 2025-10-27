package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SerialDisposable implements Disposable {
    final AtomicReference<Disposable> resource;

    public SerialDisposable() {
        this.resource = new AtomicReference<>();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this.resource);
    }

    @Nullable
    public Disposable get() {
        Disposable disposable = this.resource.get();
        return disposable == DisposableHelper.DISPOSED ? b.a() : disposable;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return DisposableHelper.isDisposed(this.resource.get());
    }

    public boolean replace(@Nullable Disposable next) {
        return DisposableHelper.replace(this.resource, next);
    }

    public boolean set(@Nullable Disposable next) {
        return DisposableHelper.set(this.resource, next);
    }

    public SerialDisposable(@Nullable Disposable initialDisposable) {
        this.resource = new AtomicReference<>(initialDisposable);
    }
}
