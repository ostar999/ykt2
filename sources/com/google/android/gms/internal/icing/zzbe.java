package com.google.android.gms.internal.icing;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzbe extends ContentObserver {
    private final /* synthetic */ zzbc zzcq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzbe(zzbc zzbcVar, Handler handler) {
        super(null);
        this.zzcq = zzbcVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        this.zzcq.zzn();
    }
}
