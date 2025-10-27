package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zaai extends zabd {
    private final /* synthetic */ zaaf zafy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaai(zaaf zaafVar, zabb zabbVar) {
        super(zabbVar);
        this.zafy = zaafVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    public final void zaal() {
        this.zafy.onConnectionSuspended(1);
    }
}
