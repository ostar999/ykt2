package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public interface AsyncFunction<I, O> {
    ListenableFuture<O> apply(@NullableDecl I i2) throws Exception;
}
