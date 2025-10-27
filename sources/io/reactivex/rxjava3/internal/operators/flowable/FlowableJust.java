package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.ScalarSubscription;
import io.reactivex.rxjava3.operators.ScalarSupplier;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableJust<T> extends Flowable<T> implements ScalarSupplier<T> {
    private final T value;

    public FlowableJust(final T value) {
        this.value = value;
    }

    @Override // io.reactivex.rxjava3.operators.ScalarSupplier, io.reactivex.rxjava3.functions.Supplier
    public T get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        s2.onSubscribe(new ScalarSubscription(s2, this.value));
    }
}
