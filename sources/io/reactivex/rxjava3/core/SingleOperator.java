package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface SingleOperator<Downstream, Upstream> {
    @NonNull
    SingleObserver<? super Upstream> apply(@NonNull SingleObserver<? super Downstream> observer) throws Throwable;
}
