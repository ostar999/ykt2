package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

/* loaded from: classes8.dex */
public interface Emitter<T> {
    void onComplete();

    void onError(@NonNull Throwable error);

    void onNext(@NonNull T value);
}
