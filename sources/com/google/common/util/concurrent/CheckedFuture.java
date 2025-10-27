package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.TimeUnit;

@Beta
@GwtCompatible
@Deprecated
@CanIgnoreReturnValue
/* loaded from: classes4.dex */
public interface CheckedFuture<V, X extends Exception> extends ListenableFuture<V> {
    V checkedGet() throws Exception;

    V checkedGet(long j2, TimeUnit timeUnit) throws Exception;
}
