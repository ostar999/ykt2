package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableReduceSeedSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final R seed;
    final ObservableSource<T> source;

    public static final class ReduceSeedObserver<T, R> implements Observer<T>, Disposable {
        final SingleObserver<? super R> downstream;
        final BiFunction<R, ? super T, R> reducer;
        Disposable upstream;
        R value;

        public ReduceSeedObserver(SingleObserver<? super R> actual, BiFunction<R, ? super T, R> reducer, R value) {
            this.downstream = actual;
            this.value = value;
            this.reducer = reducer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            R r2 = this.value;
            if (r2 != null) {
                this.value = null;
                this.downstream.onSuccess(r2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            if (this.value == null) {
                RxJavaPlugins.onError(e2);
            } else {
                this.value = null;
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            R r2 = this.value;
            if (r2 != null) {
                try {
                    R rApply = this.reducer.apply(r2, value);
                    Objects.requireNonNull(rApply, "The reducer returned a null value");
                    this.value = rApply;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableReduceSeedSingle(ObservableSource<T> source, R seed, BiFunction<R, ? super T, R> reducer) {
        this.source = source;
        this.seed = seed;
        this.reducer = reducer;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> observer) {
        this.source.subscribe(new ReduceSeedObserver(observer, this.reducer, this.seed));
    }
}
