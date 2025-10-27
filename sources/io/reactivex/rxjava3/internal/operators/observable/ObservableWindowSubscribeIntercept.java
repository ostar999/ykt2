package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes8.dex */
final class ObservableWindowSubscribeIntercept<T> extends Observable<T> {
    final AtomicBoolean once = new AtomicBoolean();
    final Subject<T> window;

    public ObservableWindowSubscribeIntercept(Subject<T> source) {
        this.window = source;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> s2) {
        this.window.subscribe(s2);
        this.once.set(true);
    }

    public boolean tryAbandon() {
        return !this.once.get() && this.once.compareAndSet(false, true);
    }
}
