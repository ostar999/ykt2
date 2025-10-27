package io.reactivex.rxjava3.flowables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;

/* loaded from: classes8.dex */
public abstract class GroupedFlowable<K, T> extends Flowable<T> {
    final K key;

    public GroupedFlowable(@Nullable K key) {
        this.key = key;
    }

    @Nullable
    public K getKey() {
        return this.key;
    }
}
