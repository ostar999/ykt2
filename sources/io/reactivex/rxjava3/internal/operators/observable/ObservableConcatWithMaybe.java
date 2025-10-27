package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableConcatWithMaybe<T> extends AbstractObservableWithUpstream<T, T> {
    final MaybeSource<? extends T> other;

    public static final class ConcatWithObserver<T> extends AtomicReference<Disposable> implements Observer<T>, MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -1953724749712440952L;
        final Observer<? super T> downstream;
        boolean inMaybe;
        MaybeSource<? extends T> other;

        public ConcatWithObserver(Observer<? super T> actual, MaybeSource<? extends T> other) {
            this.downstream = actual;
            this.other = other;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.inMaybe) {
                this.downstream.onComplete();
                return;
            }
            this.inMaybe = true;
            DisposableHelper.replace(this, null);
            MaybeSource<? extends T> maybeSource = this.other;
            this.other = null;
            maybeSource.subscribe(this);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (!DisposableHelper.setOnce(this, d3) || this.inMaybe) {
                return;
            }
            this.downstream.onSubscribe(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            this.downstream.onNext(t2);
            this.downstream.onComplete();
        }
    }

    public ObservableConcatWithMaybe(Observable<T> source, MaybeSource<? extends T> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new ConcatWithObserver(observer, this.other));
    }
}
