package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class ObservableTake<T> extends AbstractObservableWithUpstream<T, T> {
    final long limit;

    public static final class TakeObserver<T> implements Observer<T>, Disposable {
        boolean done;
        final Observer<? super T> downstream;
        long remaining;
        Disposable upstream;

        public TakeObserver(Observer<? super T> actual, long limit) {
            this.downstream = actual;
            this.remaining = limit;
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
            if (this.done) {
                return;
            }
            this.done = true;
            this.upstream.dispose();
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            this.upstream.dispose();
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            long j2 = this.remaining;
            long j3 = j2 - 1;
            this.remaining = j3;
            if (j2 > 0) {
                boolean z2 = j3 == 0;
                this.downstream.onNext(t2);
                if (z2) {
                    onComplete();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                if (this.remaining != 0) {
                    this.downstream.onSubscribe(this);
                    return;
                }
                this.done = true;
                d3.dispose();
                EmptyDisposable.complete(this.downstream);
            }
        }
    }

    public ObservableTake(ObservableSource<T> source, long limit) {
        super(source);
        this.limit = limit;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeObserver(observer, this.limit));
    }
}
