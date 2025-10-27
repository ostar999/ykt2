package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zau implements zabs {
    private final /* synthetic */ zaq zaet;

    private zau(zaq zaqVar) {
        this.zaet = zaqVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void zab(@Nullable Bundle bundle) {
        this.zaet.zaer.lock();
        try {
            this.zaet.zaep = ConnectionResult.RESULT_SUCCESS;
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void zac(@NonNull ConnectionResult connectionResult) {
        this.zaet.zaer.lock();
        try {
            this.zaet.zaep = connectionResult;
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }

    public /* synthetic */ zau(zaq zaqVar, zat zatVar) {
        this(zaqVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    public final void zab(int i2, boolean z2) {
        this.zaet.zaer.lock();
        try {
            if (this.zaet.zaeq) {
                this.zaet.zaeq = false;
                this.zaet.zaa(i2, z2);
            } else {
                this.zaet.zaeq = true;
                this.zaet.zaei.onConnectionSuspended(i2);
            }
        } finally {
            this.zaet.zaer.unlock();
        }
    }
}
