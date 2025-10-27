package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFromSupplier<T> extends Flowable<T> implements Supplier<T> {
    final Supplier<? extends T> supplier;

    public FlowableFromSupplier(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Throwable {
        T t2 = this.supplier.get();
        Objects.requireNonNull(t2, "The supplier returned a null value");
        return t2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(s2);
        s2.onSubscribe(deferredScalarSubscription);
        try {
            T t2 = this.supplier.get();
            Objects.requireNonNull(t2, "The supplier returned a null value");
            deferredScalarSubscription.complete(t2);
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
