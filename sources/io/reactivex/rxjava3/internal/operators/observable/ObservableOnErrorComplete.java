package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class ObservableOnErrorComplete<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super Throwable> predicate;

    public static final class OnErrorCompleteObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> downstream;
        final Predicate<? super Throwable> predicate;
        Disposable upstream;

        public OnErrorCompleteObserver(Observer<? super T> actual, Predicate<? super Throwable> predicate) {
            this.downstream = actual;
            this.predicate = predicate;
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
            try {
                if (this.predicate.test(e2)) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(e2);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            this.downstream.onNext(value);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableOnErrorComplete(ObservableSource<T> source, Predicate<? super Throwable> predicate) {
        super(source);
        this.predicate = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new OnErrorCompleteObserver(observer, this.predicate));
    }
}
