package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface MaybeOnSubscribe<T> {
    void subscribe(@NonNull MaybeEmitter<T> emitter) throws Throwable;
}
