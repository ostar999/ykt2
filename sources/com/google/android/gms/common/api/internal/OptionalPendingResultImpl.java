package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

@KeepForSdk
/* loaded from: classes3.dex */
public final class OptionalPendingResultImpl<R extends Result> extends OptionalPendingResult<R> {
    private final BasePendingResult<R> zajt;

    public OptionalPendingResultImpl(PendingResult<R> pendingResult) {
        this.zajt = (BasePendingResult) pendingResult;
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        this.zajt.addStatusListener(statusListener);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await() {
        return this.zajt.await();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void cancel() {
        this.zajt.cancel();
    }

    @Override // com.google.android.gms.common.api.OptionalPendingResult
    public final R get() {
        if (isDone()) {
            return await(0L, TimeUnit.MILLISECONDS);
        }
        throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final boolean isCanceled() {
        return this.zajt.isCanceled();
    }

    @Override // com.google.android.gms.common.api.OptionalPendingResult
    public final boolean isDone() {
        return this.zajt.isReady();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        this.zajt.setResultCallback(resultCallback);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        return this.zajt.then(resultTransform);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final Integer zal() {
        return this.zajt.zal();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await(long j2, TimeUnit timeUnit) {
        return this.zajt.await(j2, timeUnit);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j2, TimeUnit timeUnit) {
        this.zajt.setResultCallback(resultCallback, j2, timeUnit);
    }
}
