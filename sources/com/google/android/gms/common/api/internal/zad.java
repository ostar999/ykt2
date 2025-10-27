package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.GoogleApiManager;

/* loaded from: classes3.dex */
public final class zad<A extends BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>> extends zac {
    private final A zacp;

    public zad(int i2, A a3) {
        super(i2);
        this.zacp = a3;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull Status status) {
        this.zacp.setFailedResult(status);
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zac(GoogleApiManager.zaa<?> zaaVar) throws DeadObjectException {
        try {
            this.zacp.run(zaaVar.zaad());
        } catch (RuntimeException e2) {
            zaa(e2);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull RuntimeException runtimeException) {
        String simpleName = runtimeException.getClass().getSimpleName();
        String localizedMessage = runtimeException.getLocalizedMessage();
        StringBuilder sb = new StringBuilder(simpleName.length() + 2 + String.valueOf(localizedMessage).length());
        sb.append(simpleName);
        sb.append(": ");
        sb.append(localizedMessage);
        this.zacp.setFailedResult(new Status(10, sb.toString()));
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull zaz zazVar, boolean z2) {
        zazVar.zaa(this.zacp, z2);
    }
}
