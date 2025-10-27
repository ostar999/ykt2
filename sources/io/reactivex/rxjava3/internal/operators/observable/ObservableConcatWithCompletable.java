package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableConcatWithCompletable<T> extends AbstractObservableWithUpstream<T, T> {
    final CompletableSource other;

    public static final class ConcatWithObserver<T> extends AtomicReference<Disposable> implements Observer<T>, CompletableObserver, Disposable {
        private static final long serialVersionUID = -1953724749712440952L;
        final Observer<? super T> downstream;
        boolean inCompletable;
        CompletableSource other;

        public ConcatWithObserver(Observer<? super T> actual, CompletableSource other) {
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
            if (this.inCompletable) {
                this.downstream.onComplete();
                return;
            }
            this.inCompletable = true;
            DisposableHelper.replace(this, null);
            CompletableSource completableSource = this.other;
            this.other = null;
            completableSource.subscribe(this);
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
            if (!DisposableHelper.setOnce(this, d3) || this.inCompletable) {
                return;
            }
            this.downstream.onSubscribe(this);
        }
    }

    public ObservableConcatWithCompletable(Observable<T> source, CompletableSource other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new ConcatWithObserver(observer, this.other));
    }
}
