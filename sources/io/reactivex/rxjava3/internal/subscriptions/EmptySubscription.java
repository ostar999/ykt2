package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.operators.QueueSubscription;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public enum EmptySubscription implements QueueSubscription<Object> {
    INSTANCE;

    public static void complete(Subscriber<?> s2) {
        s2.onSubscribe(INSTANCE);
        s2.onComplete();
    }

    public static void error(Throwable e2, Subscriber<?> s2) {
        s2.onSubscribe(INSTANCE);
        s2.onError(e2);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    @Nullable
    public Object poll() {
        return null;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        SubscriptionHelper.validate(n2);
    }

    @Override // io.reactivex.rxjava3.operators.QueueFuseable
    public int requestFusion(int mode) {
        return mode & 2;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "EmptySubscription";
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object v12, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
