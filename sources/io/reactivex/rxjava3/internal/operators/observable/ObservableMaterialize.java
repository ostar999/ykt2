package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class ObservableMaterialize<T> extends AbstractObservableWithUpstream<T, Notification<T>> {

    public static final class MaterializeObserver<T> implements Observer<T>, Disposable {
        final Observer<? super Notification<T>> downstream;
        Disposable upstream;

        public MaterializeObserver(Observer<? super Notification<T>> downstream) {
            this.downstream = downstream;
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
            this.downstream.onNext(Notification.createOnComplete());
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.downstream.onNext(Notification.createOnError(t2));
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.downstream.onNext(Notification.createOnNext(t2));
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableMaterialize(ObservableSource<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Notification<T>> t2) {
        this.source.subscribe(new MaterializeObserver(t2));
    }
}
