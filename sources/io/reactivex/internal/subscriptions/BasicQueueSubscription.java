package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes8.dex */
public abstract class BasicQueueSubscription<T> extends AtomicLong implements QueueSubscription<T> {
    private static final long serialVersionUID = -6671519529404341862L;

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(T t2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(T t2, T t3) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
