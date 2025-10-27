package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface CompletableOnSubscribe {
    void subscribe(@NonNull CompletableEmitter emitter) throws Throwable;
}
