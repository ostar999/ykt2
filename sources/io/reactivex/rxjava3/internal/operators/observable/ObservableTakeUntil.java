package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableTakeUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<? extends U> other;

    public static final class TakeUntilMainObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1418547743690811973L;
        final Observer<? super T> downstream;
        final AtomicReference<Disposable> upstream = new AtomicReference<>();
        final TakeUntilMainObserver<T, U>.OtherObserver otherObserver = new OtherObserver();
        final AtomicThrowable error = new AtomicThrowable();

        public final class OtherObserver extends AtomicReference<Disposable> implements Observer<U> {
            private static final long serialVersionUID = -8693423678067375039L;

            public OtherObserver() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                TakeUntilMainObserver.this.otherComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable e2) {
                TakeUntilMainObserver.this.otherError(e2);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(U t2) {
                DisposableHelper.dispose(this);
                TakeUntilMainObserver.this.otherComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }
        }

        public TakeUntilMainObserver(Observer<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this.otherObserver);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onError(this.downstream, e2, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            HalfSerializer.onNext(this.downstream, t2, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this.upstream, d3);
        }

        public void otherComplete() {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        public void otherError(Throwable e2) {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onError(this.downstream, e2, this, this.error);
        }
    }

    public ObservableTakeUntil(ObservableSource<T> source, ObservableSource<? extends U> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> child) {
        TakeUntilMainObserver takeUntilMainObserver = new TakeUntilMainObserver(child);
        child.onSubscribe(takeUntilMainObserver);
        this.other.subscribe(takeUntilMainObserver.otherObserver);
        this.source.subscribe(takeUntilMainObserver);
    }
}
