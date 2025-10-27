package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/* loaded from: classes8.dex */
public final class MaybeJust<T> extends Maybe<T> implements ScalarSupplier<T> {
    final T value;

    public MaybeJust(T value) {
        this.value = value;
    }

    @Override // io.reactivex.rxjava3.operators.ScalarSupplier, io.reactivex.rxjava3.functions.Supplier
    public T get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        maybeObserver.onSubscribe(b.a());
        maybeObserver.onSuccess(this.value);
    }
}
