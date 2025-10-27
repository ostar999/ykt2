package com.google.android.gms.tasks;

import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public abstract class CancellationToken {
    public abstract boolean isCancellationRequested();

    public abstract CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener);
}
