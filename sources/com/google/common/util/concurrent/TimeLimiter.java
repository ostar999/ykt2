package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public interface TimeLimiter {
    @CanIgnoreReturnValue
    <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long j2, TimeUnit timeUnit) throws ExecutionException, TimeoutException;

    @CanIgnoreReturnValue
    <T> T callWithTimeout(Callable<T> callable, long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException;

    <T> T newProxy(T t2, Class<T> cls, long j2, TimeUnit timeUnit);

    void runUninterruptiblyWithTimeout(Runnable runnable, long j2, TimeUnit timeUnit) throws TimeoutException;

    void runWithTimeout(Runnable runnable, long j2, TimeUnit timeUnit) throws InterruptedException, TimeoutException;
}
