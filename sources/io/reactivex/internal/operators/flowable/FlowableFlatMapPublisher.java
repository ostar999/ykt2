package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFlatMapPublisher<T, U> extends Flowable<U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;
    final Publisher<T> source;

    public FlowableFlatMapPublisher(Publisher<T> publisher, Function<? super T, ? extends Publisher<? extends U>> function, boolean z2, int i2, int i3) {
        this.source = publisher;
        this.mapper = function;
        this.delayErrors = z2;
        this.maxConcurrency = i2;
        this.bufferSize = i3;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe(FlowableFlatMap.subscribe(subscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }
}
