package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class ObservableLastMaybe<T> extends Maybe<T> {
    final ObservableSource<T> source;

    public static final class LastObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> downstream;
        T item;
        Disposable upstream;

        public LastObserver(MaybeObserver<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream == DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.upstream = DisposableHelper.DISPOSED;
            T t2 = this.item;
            if (t2 == null) {
                this.downstream.onComplete();
            } else {
                this.item = null;
                this.downstream.onSuccess(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.upstream = DisposableHelper.DISPOSED;
            this.item = null;
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.item = t2;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableLastMaybe(ObservableSource<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new LastObserver(observer));
    }
}
