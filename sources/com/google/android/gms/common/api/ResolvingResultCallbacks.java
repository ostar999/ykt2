package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public abstract class ResolvingResultCallbacks<R extends Result> extends ResultCallbacks<R> {
    private final Activity mActivity;
    private final int zzao;

    public ResolvingResultCallbacks(@NonNull Activity activity, int i2) {
        this.mActivity = (Activity) Preconditions.checkNotNull(activity, "Activity must not be null");
        this.zzao = i2;
    }

    @Override // com.google.android.gms.common.api.ResultCallbacks
    @KeepForSdk
    public final void onFailure(@NonNull Status status) {
        if (!status.hasResolution()) {
            onUnresolvableFailure(status);
            return;
        }
        try {
            status.startResolutionForResult(this.mActivity, this.zzao);
        } catch (IntentSender.SendIntentException e2) {
            Log.e("ResolvingResultCallback", "Failed to start resolution", e2);
            onUnresolvableFailure(new Status(8));
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallbacks
    public abstract void onSuccess(@NonNull R r2);

    public abstract void onUnresolvableFailure(@NonNull Status status);
}
