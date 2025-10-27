package com.alibaba.sdk.android.emas;

/* loaded from: classes2.dex */
public interface Cache<T> {
    void add(T t2);

    void clear();

    T get();

    boolean remove(T t2);
}
