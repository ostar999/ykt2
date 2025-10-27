package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSingle;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class FlowableSwitchMapSinglePublisher<T, R> extends Flowable<R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final Publisher<T> source;

    public FlowableSwitchMapSinglePublisher(Publisher<T> source, Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors) {
        this.source = source;
        this.mapper = mapper;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe(new FlowableSwitchMapSingle.SwitchMapSingleSubscriber(s2, this.mapper, this.delayErrors));
    }
}
