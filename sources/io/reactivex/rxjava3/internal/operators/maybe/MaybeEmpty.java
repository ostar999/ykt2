package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/* loaded from: classes8.dex */
public final class MaybeEmpty extends Maybe<Object> implements ScalarSupplier<Object> {
    public static final MaybeEmpty INSTANCE = new MaybeEmpty();

    @Override // io.reactivex.rxjava3.operators.ScalarSupplier, io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        return null;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super Object> observer) {
        EmptyDisposable.complete(observer);
    }
}
