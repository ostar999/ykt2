package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;

/* loaded from: classes8.dex */
public final class ObservableSwitchIfEmpty<T> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<? extends T> other;

    public static final class SwitchIfEmptyObserver<T> implements Observer<T> {
        final Observer<? super T> downstream;
        final ObservableSource<? extends T> other;
        boolean empty = true;
        final SequentialDisposable arbiter = new SequentialDisposable();

        public SwitchIfEmptyObserver(Observer<? super T> actual, ObservableSource<? extends T> other) {
            this.downstream = actual;
            this.other = other;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.empty) {
                this.downstream.onComplete();
            } else {
                this.empty = false;
                this.other.subscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.empty) {
                this.empty = false;
            }
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.arbiter.update(d3);
        }
    }

    public ObservableSwitchIfEmpty(ObservableSource<T> source, ObservableSource<? extends T> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        SwitchIfEmptyObserver switchIfEmptyObserver = new SwitchIfEmptyObserver(t2, this.other);
        t2.onSubscribe(switchIfEmptyObserver.arbiter);
        this.source.subscribe(switchIfEmptyObserver);
    }
}
