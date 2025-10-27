package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface ObservableTransformer<Upstream, Downstream> {
    @NonNull
    ObservableSource<Downstream> apply(@NonNull Observable<Upstream> upstream);
}
