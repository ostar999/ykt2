package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {

    /* renamed from: n, reason: collision with root package name */
    final long f27362n;

    public static final class SkipObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> downstream;
        long remaining;
        Disposable upstream;

        public SkipObserver(Observer<? super T> actual, long n2) {
            this.downstream = actual;
            this.remaining = n2;
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
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            long j2 = this.remaining;
            if (j2 != 0) {
                this.remaining = j2 - 1;
            } else {
                this.downstream.onNext(t2);
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

    public ObservableSkip(ObservableSource<T> source, long n2) {
        super(source);
        this.f27362n = n2;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SkipObserver(observer, this.f27362n));
    }
}
