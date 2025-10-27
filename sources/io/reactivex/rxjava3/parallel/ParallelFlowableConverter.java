package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface ParallelFlowableConverter<T, R> {
    @NonNull
    R apply(@NonNull ParallelFlowable<T> upstream);
}
