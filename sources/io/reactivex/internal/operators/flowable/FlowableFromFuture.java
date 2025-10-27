package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromFuture<T> extends Flowable<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public FlowableFromFuture(Future<? extends T> future, long j2, TimeUnit timeUnit) {
        this.future = future;
        this.timeout = j2;
        this.unit = timeUnit;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(subscriber);
        subscriber.onSubscribe(deferredScalarSubscription);
        try {
            TimeUnit timeUnit = this.unit;
            T t2 = timeUnit != null ? this.future.get(this.timeout, timeUnit) : this.future.get();
            if (t2 == null) {
                subscriber.onError(new NullPointerException("The future returned null"));
            } else {
                deferredScalarSubscription.complete(t2);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (deferredScalarSubscription.isCancelled()) {
                return;
            }
            subscriber.onError(th);
        }
    }
}
