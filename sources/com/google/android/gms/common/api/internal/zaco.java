package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zaco implements zacq {
    private final /* synthetic */ zacp zala;

    public zaco(zacp zacpVar) {
        this.zala = zacpVar;
    }

    @Override // com.google.android.gms.common.api.internal.zacq
    public final void zab(BasePendingResult<?> basePendingResult) {
        this.zala.zald.remove(basePendingResult);
        basePendingResult.zal();
    }
}
