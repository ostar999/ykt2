package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes8.dex */
public final class CompletableFromObservable<T> extends Completable {
    final ObservableSource<T> observable;

    public static final class CompletableFromObservableObserver<T> implements Observer<T> {
        final CompletableObserver co;

        public CompletableFromObservableObserver(CompletableObserver co) {
            this.co = co;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.co.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            this.co.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.co.onSubscribe(d3);
        }
    }

    public CompletableFromObservable(ObservableSource<T> observable) {
        this.observable = observable;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.observable.subscribe(new CompletableFromObservableObserver(observer));
    }
}
