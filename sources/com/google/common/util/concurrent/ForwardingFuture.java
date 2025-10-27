package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@CanIgnoreReturnValue
@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ForwardingFuture<V> extends ForwardingObject implements Future<V> {

    public static abstract class SimpleForwardingFuture<V> extends ForwardingFuture<V> {
        private final Future<V> delegate;

        public SimpleForwardingFuture(Future<V> future) {
            this.delegate = (Future) Preconditions.checkNotNull(future);
        }

        @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final Future<V> delegate() {
            return this.delegate;
        }
    }

    public boolean cancel(boolean z2) {
        return delegate().cancel(z2);
    }

    @Override // com.google.common.collect.ForwardingObject
    public abstract Future<? extends V> delegate();

    @Override // java.util.concurrent.Future
    public V get() throws ExecutionException, InterruptedException {
        return delegate().get();
    }

    public boolean isCancelled() {
        return delegate().isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return delegate().isDone();
    }

    @Override // java.util.concurrent.Future
    public V get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return delegate().get(j2, timeUnit);
    }
}
