package com.google.android.gms.internal.icing;

import android.util.Log;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zzat extends zzar {
    private final /* synthetic */ zzau zzbn;

    public zzat(zzau zzauVar) {
        this.zzbn = zzauVar;
    }

    @Override // com.google.android.gms.internal.icing.zzar, com.google.android.gms.internal.icing.zzam
    public final void zzb(Status status) {
        if (this.zzbn.zzbp) {
            Log.d("SearchAuth", "ClearTokenImpl success");
        }
        this.zzbn.setResult((zzau) status);
    }
}
