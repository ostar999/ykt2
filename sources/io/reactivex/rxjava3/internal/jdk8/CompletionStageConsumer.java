package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class CompletionStageConsumer<T> extends CompletableFuture<T> implements MaybeObserver<T>, SingleObserver<T>, CompletableObserver {
    final T defaultItem;
    final boolean hasDefault;
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    public CompletionStageConsumer(boolean hasDefault, T defaultItem) {
        this.hasDefault = hasDefault;
        this.defaultItem = defaultItem;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancelUpstream();
        return super.cancel(mayInterruptIfRunning);
    }

    public void cancelUpstream() {
        DisposableHelper.dispose(this.upstream);
    }

    public void clear() {
        this.upstream.lazySet(DisposableHelper.DISPOSED);
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean complete(T value) {
        cancelUpstream();
        return super.complete(value);
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean completeExceptionally(Throwable ex) {
        cancelUpstream();
        return super.completeExceptionally(ex);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (this.hasDefault) {
            complete(this.defaultItem);
        } else {
            completeExceptionally(new NoSuchElementException("The source was empty"));
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable t2) {
        clear();
        if (completeExceptionally(t2)) {
            return;
        }
        RxJavaPlugins.onError(t2);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable d3) {
        DisposableHelper.setOnce(this.upstream, d3);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(@NonNull T t2) {
        clear();
        complete(t2);
    }
}
