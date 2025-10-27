package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class SingleMap<T, R> extends Single<R> {
    final Function<? super T, ? extends R> mapper;
    final SingleSource<? extends T> source;

    public static final class MapSingleObserver<T, R> implements SingleObserver<T> {
        final Function<? super T, ? extends R> mapper;

        /* renamed from: t, reason: collision with root package name */
        final SingleObserver<? super R> f27366t;

        public MapSingleObserver(SingleObserver<? super R> t2, Function<? super T, ? extends R> mapper) {
            this.f27366t = t2;
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.f27366t.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.f27366t.onSubscribe(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            try {
                R rApply = this.mapper.apply(value);
                Objects.requireNonNull(rApply, "The mapper function returned a null value.");
                this.f27366t.onSuccess(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }
    }

    public SingleMap(SingleSource<? extends T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super R> t2) {
        this.source.subscribe(new MapSingleObserver(t2, this.mapper));
    }
}
