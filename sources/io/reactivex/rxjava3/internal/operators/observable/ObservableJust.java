package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/* loaded from: classes8.dex */
public final class ObservableJust<T> extends Observable<T> implements ScalarSupplier<T> {
    private final T value;

    public ObservableJust(final T value) {
        this.value = value;
    }

    @Override // io.reactivex.rxjava3.operators.ScalarSupplier, io.reactivex.rxjava3.functions.Supplier
    public T get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        ObservableScalarXMap.ScalarDisposable scalarDisposable = new ObservableScalarXMap.ScalarDisposable(observer, this.value);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }
}
