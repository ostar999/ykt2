package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class ObservableDelaySubscriptionOther<T, U> extends Observable<T> {
    final ObservableSource<? extends T> main;
    final ObservableSource<U> other;

    public final class DelayObserver implements Observer<U> {
        final Observer<? super T> child;
        boolean done;
        final SequentialDisposable serial;

        public final class OnComplete implements Observer<T> {
            public OnComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                DelayObserver.this.child.onComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable e2) {
                DelayObserver.this.child.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(T value) {
                DelayObserver.this.child.onNext(value);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable d3) {
                DelayObserver.this.serial.update(d3);
            }
        }

        public DelayObserver(SequentialDisposable serial, Observer<? super T> child) {
            this.serial = serial;
            this.child = child;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            ObservableDelaySubscriptionOther.this.main.subscribe(new OnComplete());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
            } else {
                this.done = true;
                this.child.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(U t2) {
            onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.serial.update(d3);
        }
    }

    public ObservableDelaySubscriptionOther(ObservableSource<? extends T> main, ObservableSource<U> other) {
        this.main = main;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(final Observer<? super T> child) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        child.onSubscribe(sequentialDisposable);
        this.other.subscribe(new DelayObserver(sequentialDisposable, child));
    }
}
