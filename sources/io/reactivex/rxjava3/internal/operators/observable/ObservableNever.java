package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;

/* loaded from: classes8.dex */
public final class ObservableNever extends Observable<Object> {
    public static final Observable<Object> INSTANCE = new ObservableNever();

    private ObservableNever() {
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Object> o2) {
        o2.onSubscribe(EmptyDisposable.NEVER);
    }
}
