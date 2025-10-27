package com.google.android.gms.internal.icing;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzbj extends ContentObserver {
    public zzbj(zzbh zzbhVar, Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        zzbq.zzt();
    }
}
