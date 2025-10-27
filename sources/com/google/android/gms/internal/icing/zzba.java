package com.google.android.gms.internal.icing;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzba extends ContentObserver {
    public zzba(Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        zzax.zzbv.set(true);
    }
}
