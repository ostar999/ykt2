package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromFuture<T> extends Flowable<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public FlowableFromFuture(Future<? extends T> future, long timeout, TimeUnit unit) {
        this.future = future;
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(s2);
        s2.onSubscribe(deferredScalarSubscription);
        try {
            TimeUnit timeUnit = this.unit;
            T t2 = timeUnit != null ? this.future.get(this.timeout, timeUnit) : this.future.get();
            if (t2 == null) {
                s2.onError(ExceptionHelper.createNullPointerException("The future returned a null value."));
            } else {
                deferredScalarSubscription.complete(t2);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (deferredScalarSubscription.isCancelled()) {
                return;
            }
            s2.onError(th);
        }
    }
}
