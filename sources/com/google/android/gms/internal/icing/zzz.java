package com.google.android.gms.internal.icing;

import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;

@ShowFirstParty
@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzz {
    private zzi zzaj;
    private zzh zzan;
    private long zzak = -1;
    private int zzal = -1;
    private int zzap = -1;
    private boolean zzao = false;
    private int zzaq = 0;

    public final zzz zza(zzi zziVar) {
        this.zzaj = zziVar;
        return this;
    }

    public final zzz zzb(int i2) {
        this.zzal = i2;
        return this;
    }

    public final zzz zzc(int i2) {
        this.zzaq = i2;
        return this;
    }

    public final zzz zzd(boolean z2) {
        this.zzao = z2;
        return this;
    }

    public final zzz zza(long j2) {
        this.zzak = j2;
        return this;
    }

    public final zzw zzd() {
        return new zzw(this.zzaj, this.zzak, this.zzal, null, this.zzan, this.zzao, this.zzap, this.zzaq, null);
    }

    public final zzz zza(zzh zzhVar) {
        this.zzan = zzhVar;
        return this;
    }
}
