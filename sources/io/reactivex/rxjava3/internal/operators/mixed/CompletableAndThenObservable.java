package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class CompletableAndThenObservable<R> extends Observable<R> {
    final ObservableSource<? extends R> other;
    final CompletableSource source;

    public static final class AndThenObservableObserver<R> extends AtomicReference<Disposable> implements Observer<R>, CompletableObserver, Disposable {
        private static final long serialVersionUID = -8948264376121066672L;
        final Observer<? super R> downstream;
        ObservableSource<? extends R> other;

        public AndThenObservableObserver(Observer<? super R> downstream, ObservableSource<? extends R> other) {
            this.other = other;
            this.downstream = downstream;
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
            ObservableSource<? extends R> observableSource = this.other;
            if (observableSource == null) {
                this.downstream.onComplete();
            } else {
                this.other = null;
                observableSource.subscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(R t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.replace(this, d3);
        }
    }

    public CompletableAndThenObservable(CompletableSource source, ObservableSource<? extends R> other) {
        this.source = source;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        AndThenObservableObserver andThenObservableObserver = new AndThenObservableObserver(observer, this.other);
        observer.onSubscribe(andThenObservableObserver);
        this.source.subscribe(andThenObservableObserver);
    }
}
