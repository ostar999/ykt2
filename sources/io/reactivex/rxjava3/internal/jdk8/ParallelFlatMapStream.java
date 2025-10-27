package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class ParallelFlatMapStream<T, R> extends ParallelFlowable<R> {
    final Function<? super T, ? extends Stream<? extends R>> mapper;
    final int prefetch;
    final ParallelFlowable<T> source;

    public ParallelFlatMapStream(ParallelFlowable<T> source, Function<? super T, ? extends Stream<? extends R>> mapper, int prefetch) {
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
        if (validate(subscribers)) {
            int length = subscribers.length;
            Subscriber<? super T>[] subscriberArr = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                subscriberArr[i2] = FlowableFlatMapStream.subscribe(subscribers[i2], this.mapper, this.prefetch);
            }
            this.source.subscribe(subscriberArr);
        }
    }
}
