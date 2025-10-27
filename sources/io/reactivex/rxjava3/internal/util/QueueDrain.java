package io.reactivex.rxjava3.internal.util;

import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public interface QueueDrain<T, U> {
    boolean accept(Subscriber<? super U> a3, T v2);

    boolean cancelled();

    boolean done();

    boolean enter();

    Throwable error();

    int leave(int m2);

    long produced(long n2);

    long requested();
}
