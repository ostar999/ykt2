package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class ObservableOnErrorReturn<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends T> valueSupplier;

    public static final class OnErrorReturnObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> downstream;
        Disposable upstream;
        final Function<? super Throwable, ? extends T> valueSupplier;

        public OnErrorReturnObserver(Observer<? super T> actual, Function<? super Throwable, ? extends T> valueSupplier) {
            this.downstream = actual;
            this.valueSupplier = valueSupplier;
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
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            try {
                T tApply = this.valueSupplier.apply(t2);
                if (tApply != null) {
                    this.downstream.onNext(tApply);
                    this.downstream.onComplete();
                } else {
                    NullPointerException nullPointerException = new NullPointerException("The supplied value is null");
                    nullPointerException.initCause(t2);
                    this.downstream.onError(nullPointerException);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(t2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableOnErrorReturn(ObservableSource<T> source, Function<? super Throwable, ? extends T> valueSupplier) {
        super(source);
        this.valueSupplier = valueSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        this.source.subscribe(new OnErrorReturnObserver(t2, this.valueSupplier));
    }
}
