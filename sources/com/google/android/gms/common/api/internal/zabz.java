package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zabz extends UnregisterListenerMethod<Object, Object> {
    private final /* synthetic */ RegistrationMethods.Builder zakk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zabz(RegistrationMethods.Builder builder, ListenerHolder.ListenerKey listenerKey) {
        super(listenerKey);
        this.zakk = builder;
    }

    @Override // com.google.android.gms.common.api.internal.UnregisterListenerMethod
    public final void unregisterListener(Object obj, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException {
        this.zakk.zakf.accept(obj, taskCompletionSource);
    }
}
