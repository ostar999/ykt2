package com.nirvana.tools.requestqueue;

import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public interface TimeoutCallable<T> extends Callable<T> {
    T onTimeout();
}
