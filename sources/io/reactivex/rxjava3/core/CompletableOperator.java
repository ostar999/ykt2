package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface CompletableOperator {
    @NonNull
    CompletableObserver apply(@NonNull CompletableObserver observer) throws Throwable;
}
