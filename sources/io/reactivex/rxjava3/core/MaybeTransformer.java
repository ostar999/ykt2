package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface MaybeTransformer<Upstream, Downstream> {
    @NonNull
    MaybeSource<Downstream> apply(@NonNull Maybe<Upstream> upstream);
}
