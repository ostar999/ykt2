package com.jakewharton.rxbinding2;

import io.reactivex.Observable;
import io.reactivex.Observer;

/* loaded from: classes4.dex */
public abstract class InitialValueObservable<T> extends Observable<T> {

    public final class Skipped extends Observable<T> {
        public Skipped() {
        }

        @Override // io.reactivex.Observable
        public void subscribeActual(Observer<? super T> observer) {
            InitialValueObservable.this.subscribeListener(observer);
        }
    }

    public abstract T getInitialValue();

    public final Observable<T> skipInitialValue() {
        return new Skipped();
    }

    @Override // io.reactivex.Observable
    public final void subscribeActual(Observer<? super T> observer) {
        subscribeListener(observer);
        observer.onNext(getInitialValue());
    }

    public abstract void subscribeListener(Observer<? super T> observer);
}
