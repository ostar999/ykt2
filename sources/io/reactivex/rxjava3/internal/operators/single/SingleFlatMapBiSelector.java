package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleFlatMapBiSelector<T, U, R> extends Single<R> {
    final Function<? super T, ? extends SingleSource<? extends U>> mapper;
    final BiFunction<? super T, ? super U, ? extends R> resultSelector;
    final SingleSource<T> source;

    public static final class FlatMapBiMainObserver<T, U, R> implements SingleObserver<T>, Disposable {
        final InnerObserver<T, U, R> inner;
        final Function<? super T, ? extends SingleSource<? extends U>> mapper;

        public static final class InnerObserver<T, U, R> extends AtomicReference<Disposable> implements SingleObserver<U> {
            private static final long serialVersionUID = -2897979525538174559L;
            final SingleObserver<? super R> downstream;
            final BiFunction<? super T, ? super U, ? extends R> resultSelector;
            T value;

            public InnerObserver(SingleObserver<? super R> actual, BiFunction<? super T, ? super U, ? extends R> resultSelector) {
                this.downstream = actual;
                this.resultSelector = resultSelector;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.downstream.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(U value) {
                T t2 = this.value;
                this.value = null;
                try {
                    R rApply = this.resultSelector.apply(t2, value);
                    Objects.requireNonNull(rApply, "The resultSelector returned a null value");
                    this.downstream.onSuccess(rApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        public FlatMapBiMainObserver(SingleObserver<? super R> actual, Function<? super T, ? extends SingleSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> resultSelector) {
            this.inner = new InnerObserver<>(actual, resultSelector);
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.inner);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.inner.get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.inner.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.setOnce(this.inner, d3)) {
                this.inner.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            try {
                SingleSource<? extends U> singleSourceApply = this.mapper.apply(t2);
                Objects.requireNonNull(singleSourceApply, "The mapper returned a null MaybeSource");
                SingleSource<? extends U> singleSource = singleSourceApply;
                if (DisposableHelper.replace(this.inner, null)) {
                    InnerObserver<T, U, R> innerObserver = this.inner;
                    innerObserver.value = t2;
                    singleSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.inner.downstream.onError(th);
            }
        }
    }

    public SingleFlatMapBiSelector(SingleSource<T> source, Function<? super T, ? extends SingleSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> resultSelector) {
        this.source = source;
        this.mapper = mapper;
        this.resultSelector = resultSelector;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> observer) {
        this.source.subscribe(new FlatMapBiMainObserver(observer, this.mapper, this.resultSelector));
    }
}
