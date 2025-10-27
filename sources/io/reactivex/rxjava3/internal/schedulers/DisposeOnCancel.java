package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
final class DisposeOnCancel implements Future<Object> {
    final Disposable upstream;

    public DisposeOnCancel(Disposable d3) {
        this.upstream = d3;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.upstream.dispose();
        return false;
    }

    @Override // java.util.concurrent.Future
    public Object get() {
        return null;
    }

    @Override // java.util.concurrent.Future
    public Object get(long timeout, @NonNull TimeUnit unit) {
        return null;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return false;
    }
}
