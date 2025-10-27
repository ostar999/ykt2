package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.functions.BiFunction;

/* loaded from: classes8.dex */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    @Override // io.reactivex.rxjava3.functions.BiFunction
    public ParallelFailureHandling apply(Long t12, Throwable t2) {
        return this;
    }
}
