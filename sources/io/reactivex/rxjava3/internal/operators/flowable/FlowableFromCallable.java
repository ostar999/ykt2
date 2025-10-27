package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromCallable<T> extends Flowable<T> implements Supplier<T> {
    final Callable<? extends T> callable;

    public FlowableFromCallable(Callable<? extends T> callable) {
        this.callable = callable;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Exception {
        T tCall = this.callable.call();
        Objects.requireNonNull(tCall, "The callable returned a null value");
        return tCall;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(s2);
        s2.onSubscribe(deferredScalarSubscription);
        try {
            T tCall = this.callable.call();
            Objects.requireNonNull(tCall, "The callable returned a null value");
            deferredScalarSubscription.complete(tCall);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (deferredScalarSubscription.isCancelled()) {
                RxJavaPlugins.onError(th);
            } else {
                s2.onError(th);
            }
        }
    }
}
