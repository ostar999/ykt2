package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.internal.operators.mixed.MaterializeSingleObserver;

/* loaded from: classes8.dex */
public final class MaybeMaterialize<T> extends Single<Notification<T>> {
    final Maybe<T> source;

    public MaybeMaterialize(Maybe<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super Notification<T>> observer) {
        this.source.subscribe(new MaterializeSingleObserver(observer));
    }
}
