package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleFlatMap<T, R> extends Single<R> {
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final SingleSource<? extends T> source;

    public static final class SingleFlatMapCallback<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 3258103020495908596L;
        final SingleObserver<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;

        public static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
            final SingleObserver<? super R> downstream;
            final AtomicReference<Disposable> parent;

            public FlatMapSingleObserver(AtomicReference<Disposable> parent, SingleObserver<? super R> downstream) {
                this.parent = parent;
                this.downstream = downstream;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(final Throwable e2) {
                this.downstream.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(final Disposable d3) {
                DisposableHelper.replace(this.parent, d3);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(final R value) {
                this.downstream.onSuccess(value);
            }
        }

        public SingleFlatMapCallback(SingleObserver<? super R> actual, Function<? super T, ? extends SingleSource<? extends R>> mapper) {
            this.downstream = actual;
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.setOnce(this, d3)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            try {
                SingleSource<? extends R> singleSourceApply = this.mapper.apply(value);
                Objects.requireNonNull(singleSourceApply, "The single returned by the mapper is null");
                SingleSource<? extends R> singleSource = singleSourceApply;
                if (isDisposed()) {
                    return;
                }
                singleSource.subscribe(new FlatMapSingleObserver(this, this.downstream));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleFlatMap(SingleSource<? extends T> source, Function<? super T, ? extends SingleSource<? extends R>> mapper) {
        this.mapper = mapper;
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> downstream) {
        this.source.subscribe(new SingleFlatMapCallback(downstream, this.mapper));
    }
}
