package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.operators.QueueDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public abstract class BasicIntQueueDisposable<T> extends AtomicInteger implements QueueDisposable<T> {
    private static final long serialVersionUID = -1001730202384742097L;

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(T e2) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean offer(T v12, T v2) {
        throw new UnsupportedOperationException("Should not be called");
    }
}
