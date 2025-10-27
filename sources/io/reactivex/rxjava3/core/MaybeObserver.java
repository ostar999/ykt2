package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes8.dex */
public interface MaybeObserver<T> {
    void onComplete();

    void onError(@NonNull Throwable e2);

    void onSubscribe(@NonNull Disposable d3);

    void onSuccess(@NonNull T t2);
}
