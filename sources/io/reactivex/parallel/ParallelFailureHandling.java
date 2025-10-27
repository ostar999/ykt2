package io.reactivex.parallel;

import io.reactivex.functions.BiFunction;

/* loaded from: classes8.dex */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    @Override // io.reactivex.functions.BiFunction
    public ParallelFailureHandling apply(Long l2, Throwable th) {
        return this;
    }
}
