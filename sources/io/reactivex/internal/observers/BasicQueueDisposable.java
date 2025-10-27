package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.QueueDisposable;

/* loaded from: classes8.dex */
public abstract class BasicQueueDisposable<T> implements QueueDisposable<T> {
    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(T t2) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(T t2, T t3) {
        throw new UnsupportedOperationException("Should not be called");
    }
}
