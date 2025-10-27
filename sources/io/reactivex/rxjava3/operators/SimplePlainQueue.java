package io.reactivex.rxjava3.operators;

import io.reactivex.rxjava3.annotations.Nullable;

/* loaded from: classes8.dex */
public interface SimplePlainQueue<T> extends SimpleQueue<T> {
    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    @Nullable
    T poll();
}
