package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

/* loaded from: classes3.dex */
public class zabt extends zak {
    private TaskCompletionSource<Void> zajs;

    private zabt(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zajs = new TaskCompletionSource<>();
        this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
    }

    public static zabt zac(Activity activity) {
        LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
        zabt zabtVar = (zabt) fragment.getCallbackOrNull("GmsAvailabilityHelper", zabt.class);
        if (zabtVar == null) {
            return new zabt(fragment);
        }
        if (zabtVar.zajs.getTask().isComplete()) {
            zabtVar.zajs = new TaskCompletionSource<>();
        }
        return zabtVar;
    }

    public final Task<Void> getTask() {
        return this.zajs.getTask();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onDestroy() {
        super.onDestroy();
        this.zajs.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zaa(ConnectionResult connectionResult, int i2) {
        this.zajs.setException(ApiExceptionUtil.fromStatus(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zam() {
        Activity lifecycleActivity = this.mLifecycleFragment.getLifecycleActivity();
        if (lifecycleActivity == null) {
            this.zajs.trySetException(new ApiException(new Status(8)));
            return;
        }
        int iIsGooglePlayServicesAvailable = this.zace.isGooglePlayServicesAvailable(lifecycleActivity);
        if (iIsGooglePlayServicesAvailable == 0) {
            this.zajs.trySetResult(null);
        } else {
            if (this.zajs.getTask().isComplete()) {
                return;
            }
            zab(new ConnectionResult(iIsGooglePlayServicesAvailable, null), 0);
        }
    }
}
