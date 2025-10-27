package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.Observer;

/* loaded from: classes8.dex */
public interface ObservableQueueDrain<T, U> {
    void accept(Observer<? super U> a3, T v2);

    boolean cancelled();

    boolean done();

    boolean enter();

    Throwable error();

    int leave(int m2);
}
