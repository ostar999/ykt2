package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface SingleTransformer<Upstream, Downstream> {
    @NonNull
    SingleSource<Downstream> apply(@NonNull Single<Upstream> upstream);
}
