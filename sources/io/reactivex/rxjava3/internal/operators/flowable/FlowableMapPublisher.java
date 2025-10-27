package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMap;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableMapPublisher<T, U> extends Flowable<U> {
    final Function<? super T, ? extends U> mapper;
    final Publisher<T> source;

    public FlowableMapPublisher(Publisher<T> source, Function<? super T, ? extends U> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super U> s2) {
        this.source.subscribe(new FlowableMap.MapSubscriber(s2, this.mapper));
    }
}
