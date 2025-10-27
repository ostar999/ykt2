package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zacj extends TaskApiCall<Object, Object> {
    private final /* synthetic */ TaskApiCall.Builder zakq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zacj(TaskApiCall.Builder builder, Feature[] featureArr, boolean z2) {
        super(featureArr, z2);
        this.zakq = builder;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final void doExecute(Object obj, TaskCompletionSource<Object> taskCompletionSource) throws RemoteException {
        this.zakq.zakp.accept(obj, taskCompletionSource);
    }
}
