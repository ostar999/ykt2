package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.util.PlatformVersion;

/* loaded from: classes3.dex */
public abstract class zac {
    private final int type;

    public zac(int i2) {
        this.type = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Status zaa(RemoteException remoteException) {
        StringBuilder sb = new StringBuilder();
        if (PlatformVersion.isAtLeastIceCreamSandwichMR1() && (remoteException instanceof TransactionTooLargeException)) {
            sb.append("TransactionTooLargeException: ");
        }
        sb.append(remoteException.getLocalizedMessage());
        return new Status(8, sb.toString());
    }

    public abstract void zaa(@NonNull Status status);

    public abstract void zaa(@NonNull zaz zazVar, boolean z2);

    public abstract void zaa(@NonNull RuntimeException runtimeException);

    public abstract void zac(GoogleApiManager.zaa<?> zaaVar) throws DeadObjectException;
}
