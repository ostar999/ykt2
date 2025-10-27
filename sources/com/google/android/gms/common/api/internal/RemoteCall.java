package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* loaded from: classes3.dex */
public interface RemoteCall<T, U> {
    @KeepForSdk
    void accept(T t2, U u2) throws RemoteException;
}
