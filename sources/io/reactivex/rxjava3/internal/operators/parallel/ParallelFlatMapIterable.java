package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlattenIterable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class ParallelFlatMapIterable<T, R> extends ParallelFlowable<R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final ParallelFlowable<T> source;

    public ParallelFlatMapIterable(ParallelFlowable<T> source, Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super R>[] subscribers) {
        Subscriber<?>[] subscriberArrOnSubscribe = RxJavaPlugins.onSubscribe(this, subscribers);
        if (validate(subscriberArrOnSubscribe)) {
            int length = subscriberArrOnSubscribe.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                subscriberArr[i2] = FlowableFlattenIterable.subscribe(subscriberArrOnSubscribe[i2], this.mapper, this.prefetch);
            }
            this.source.subscribe(subscriberArr);
        }
    }
}
