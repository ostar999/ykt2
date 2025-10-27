package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.jdk8.MaybeFlattenStreamAsObservable;
import java.util.stream.Stream;

/* loaded from: classes8.dex */
public final class SingleFlattenStreamAsObservable<T, R> extends Observable<R> {
    final Function<? super T, ? extends Stream<? extends R>> mapper;
    final Single<T> source;

    public SingleFlattenStreamAsObservable(Single<T> source, Function<? super T, ? extends Stream<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(@NonNull Observer<? super R> s2) {
        this.source.subscribe(new MaybeFlattenStreamAsObservable.FlattenStreamMultiObserver(s2, this.mapper));
    }
}
