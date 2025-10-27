package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.operators.QueueSubscription;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class ScalarSubscription<T> extends AtomicInteger implements QueueSubscription<T> {
    static final int CANCELLED = 2;
    static final int NO_REQUEST = 0;
    static final int REQUESTED = 1;
    private static final long serialVersionUID = -3830916580126663321L;
    final Subscriber<? super T> subscriber;
    final T value;

    public ScalarSubscription(Subscriber<? super T> subscriber, T value) {
        this.subscriber = subscriber;
        this.value = value;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        lazySet(2);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        lazySet(1);
    }

    public boolean isCancelled() {
        return get() == 2;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return get() != 0;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(T e2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    @Nullable
    public T poll() {
        if (get() != 0) {
            return null;
        }
        lazySet(1);
        return this.value;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j2) {
        if (SubscriptionHelper.validate(j2) && compareAndSet(0, 1)) {
            Subscriber<? super T> subscriber = this.subscriber;
            subscriber.onNext(this.value);
            if (get() != 2) {
                subscriber.onComplete();
            }
        }
    }

    @Override // io.reactivex.rxjava3.operators.QueueFuseable
    public int requestFusion(int mode) {
        return mode & 1;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(T v12, T v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
