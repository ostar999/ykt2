package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* loaded from: classes3.dex */
final class zag extends zaa {
    private final BaseImplementation.ResultHolder<Status> mResultHolder;

    public zag(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.mResultHolder = resultHolder;
    }

    @Override // com.google.android.gms.common.internal.service.zaa, com.google.android.gms.common.internal.service.zak
    public final void zaj(int i2) throws RemoteException {
        this.mResultHolder.setResult(new Status(i2));
    }
}
