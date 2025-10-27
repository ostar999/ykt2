package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zaca extends RegisterListenerMethod<Object, Object> {
    private final /* synthetic */ RegistrationMethods.Builder zakk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaca(RegistrationMethods.Builder builder, ListenerHolder listenerHolder, Feature[] featureArr, boolean z2) {
        super(listenerHolder, featureArr, z2);
        this.zakk = builder;
    }

    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    public final void registerListener(Object obj, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zakk.zake.accept(obj, taskCompletionSource);
    }
}
