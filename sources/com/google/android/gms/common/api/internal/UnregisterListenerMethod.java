package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class UnregisterListenerMethod<A extends Api.AnyClient, L> {
    private final ListenerHolder.ListenerKey<L> zajo;

    @KeepForSdk
    public UnregisterListenerMethod(ListenerHolder.ListenerKey<L> listenerKey) {
        this.zajo = listenerKey;
    }

    @KeepForSdk
    public ListenerHolder.ListenerKey<L> getListenerKey() {
        return this.zajo;
    }

    @KeepForSdk
    public abstract void unregisterListener(A a3, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException;
}
