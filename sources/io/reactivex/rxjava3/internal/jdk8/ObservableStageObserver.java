package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
abstract class ObservableStageObserver<T> extends CompletableFuture<T> implements Observer<T> {
    final AtomicReference<Disposable> upstream = new AtomicReference<>();
    T value;

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public final boolean cancel(boolean mayInterruptIfRunning) {
        disposeUpstream();
        return super.cancel(mayInterruptIfRunning);
    }

    public final void clear() {
        this.value = null;
        this.upstream.lazySet(DisposableHelper.DISPOSED);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean complete(T value) {
        disposeUpstream();
        return super.complete(value);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean completeExceptionally(Throwable ex) {
        disposeUpstream();
        return super.completeExceptionally(ex);
    }

    public final void disposeUpstream() {
        DisposableHelper.dispose(this.upstream);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onError(Throwable t2) {
        clear();
        if (completeExceptionally(t2)) {
            return;
        }
        RxJavaPlugins.onError(t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(@NonNull Disposable d3) {
        DisposableHelper.setOnce(this.upstream, d3);
    }
}
