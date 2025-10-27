package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.TimeUnit;

@Beta
@GwtIncompatible
@Deprecated
/* loaded from: classes4.dex */
public abstract class ForwardingCheckedFuture<V, X extends Exception> extends ForwardingListenableFuture<V> implements CheckedFuture<V, X> {
    @Override // com.google.common.util.concurrent.CheckedFuture
    @CanIgnoreReturnValue
    public V checkedGet() throws Exception {
        return delegate().checkedGet();
    }

    @Override // com.google.common.util.concurrent.ForwardingListenableFuture, com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
    public abstract CheckedFuture<V, X> delegate();

    @Override // com.google.common.util.concurrent.CheckedFuture
    @CanIgnoreReturnValue
    public V checkedGet(long j2, TimeUnit timeUnit) throws Exception {
        return delegate().checkedGet(j2, timeUnit);
    }

    @Beta
    @Deprecated
    public static abstract class SimpleForwardingCheckedFuture<V, X extends Exception> extends ForwardingCheckedFuture<V, X> {
        private final CheckedFuture<V, X> delegate;

        public SimpleForwardingCheckedFuture(CheckedFuture<V, X> checkedFuture) {
            this.delegate = (CheckedFuture) Preconditions.checkNotNull(checkedFuture);
        }

        @Override // com.google.common.util.concurrent.ForwardingCheckedFuture, com.google.common.util.concurrent.ForwardingListenableFuture, com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final CheckedFuture<V, X> delegate() {
            return this.delegate;
        }
    }
}
