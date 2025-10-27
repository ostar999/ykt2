package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.operators.QueueSubscription;

/* loaded from: classes8.dex */
public abstract class AbstractEmptyQueueFuseable<T> implements QueueSubscription<T>, QueueDisposable<T> {
    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final void clear() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return false;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean isEmpty() {
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(@NonNull T value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final T poll() throws Throwable {
        return null;
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long n2) {
    }

    @Override // io.reactivex.rxjava3.operators.QueueFuseable
    public final int requestFusion(int mode) {
        return mode & 2;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(@NonNull T v12, @NonNull T v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
