package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes8.dex */
public interface ObservableConverter<T, R> {
    @NonNull
    R apply(@NonNull Observable<T> observable);
}
