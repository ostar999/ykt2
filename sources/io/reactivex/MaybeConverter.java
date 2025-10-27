package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
public interface MaybeConverter<T, R> {
    @NonNull
    R apply(@NonNull Maybe<T> maybe);
}
