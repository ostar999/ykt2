package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableFlatMapMaybePublisher<T, R> extends Flowable<R> {
    final boolean delayErrors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final int maxConcurrency;
    final Publisher<T> source;

    public FlowableFlatMapMaybePublisher(Publisher<T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayError, int maxConcurrency) {
        this.source = source;
        this.mapper = mapper;
        this.delayErrors = delayError;
        this.maxConcurrency = maxConcurrency;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe(new FlowableFlatMapMaybe.FlatMapMaybeSubscriber(s2, this.mapper, this.delayErrors, this.maxConcurrency));
    }
}
