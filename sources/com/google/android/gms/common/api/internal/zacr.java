package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zacr implements IBinder.DeathRecipient, zacq {
    private final WeakReference<BasePendingResult<?>> zalf;
    private final WeakReference<com.google.android.gms.common.api.zac> zalg;
    private final WeakReference<IBinder> zalh;

    private zacr(BasePendingResult<?> basePendingResult, com.google.android.gms.common.api.zac zacVar, IBinder iBinder) {
        this.zalg = new WeakReference<>(zacVar);
        this.zalf = new WeakReference<>(basePendingResult);
        this.zalh = new WeakReference<>(iBinder);
    }

    private final void zabw() {
        BasePendingResult<?> basePendingResult = this.zalf.get();
        com.google.android.gms.common.api.zac zacVar = this.zalg.get();
        if (zacVar != null && basePendingResult != null) {
            zacVar.remove(basePendingResult.zal().intValue());
        }
        IBinder iBinder = this.zalh.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        zabw();
    }

    @Override // com.google.android.gms.common.api.internal.zacq
    public final void zab(BasePendingResult<?> basePendingResult) {
        zabw();
    }

    public /* synthetic */ zacr(BasePendingResult basePendingResult, com.google.android.gms.common.api.zac zacVar, IBinder iBinder, zaco zacoVar) {
        this(basePendingResult, null, iBinder);
    }
}
