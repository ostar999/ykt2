package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.NoSuchElementException;

/* loaded from: classes8.dex */
public final class ObservableLastSingle<T> extends Single<T> {
    final T defaultItem;
    final ObservableSource<T> source;

    public static final class LastObserver<T> implements Observer<T>, Disposable {
        final T defaultItem;
        final SingleObserver<? super T> downstream;
        T item;
        Disposable upstream;

        public LastObserver(SingleObserver<? super T> actual, T defaultItem) {
            this.downstream = actual;
            this.defaultItem = defaultItem;
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
            if (t2 != null) {
                this.item = null;
                this.downstream.onSuccess(t2);
                return;
            }
            T t3 = this.defaultItem;
            if (t3 != null) {
                this.downstream.onSuccess(t3);
            } else {
                this.downstream.onError(new NoSuchElementException());
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

    public ObservableLastSingle(ObservableSource<T> source, T defaultItem) {
        this.source = source;
        this.defaultItem = defaultItem;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.source.subscribe(new LastObserver(observer, this.defaultItem));
    }
}
