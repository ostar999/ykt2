package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
public interface SingleConverter<T, R> {
    @NonNull
    R apply(@NonNull Single<T> single);
}
