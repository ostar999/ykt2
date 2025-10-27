package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes8.dex */
public final class ObservableIgnoreElements<T> extends AbstractObservableWithUpstream<T, T> {

    public static final class IgnoreObservable<T> implements Observer<T>, Disposable {
        final Observer<? super T> downstream;
        Disposable upstream;

        public IgnoreObservable(Observer<? super T> t2) {
            this.downstream = t2;
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
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T v2) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.upstream = d3;
            this.downstream.onSubscribe(this);
        }
    }

    public ObservableIgnoreElements(ObservableSource<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(final Observer<? super T> t2) {
        this.source.subscribe(new IgnoreObservable(t2));
    }
}
