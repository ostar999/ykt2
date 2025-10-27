package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes8.dex */
public interface SingleSource<T> {
    void subscribe(@NonNull SingleObserver<? super T> observer);
}
