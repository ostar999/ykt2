package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.operators.QueueDisposable;

/* loaded from: classes8.dex */
public abstract class BasicQueueDisposable<T> implements QueueDisposable<T> {
    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(T e2) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(T v12, T v2) {
        throw new UnsupportedOperationException("Should not be called");
    }
}
