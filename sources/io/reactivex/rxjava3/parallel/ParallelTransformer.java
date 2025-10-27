package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface ParallelTransformer<Upstream, Downstream> {
    @NonNull
    ParallelFlowable<Downstream> apply(@NonNull ParallelFlowable<Upstream> upstream);
}
