package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsFlowable;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class SingleFlattenStreamAsFlowable<T, R> extends Flowable<R> {
    final Function<? super T, ? extends Stream<? extends R>> mapper;
    final Single<T> source;

    public SingleFlattenStreamAsFlowable(Single<T> source, Function<? super T, ? extends Stream<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super R> s2) {
        this.source.subscribe(new MaybeFlattenStreamAsFlowable.FlattenStreamMultiObserver(s2, this.mapper));
    }
}
