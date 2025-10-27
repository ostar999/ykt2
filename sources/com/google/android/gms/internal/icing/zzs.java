package com.google.android.gms.internal.icing;

import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;

@ShowFirstParty
@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzs {
    private final String name;
    private String zzaa;
    private boolean zzab;
    private boolean zzac;
    private String zzae;
    private int weight = 1;
    private final List<zzm> zzad = new ArrayList();

    public zzs(String str) {
        this.name = str;
    }

    public final zzs zzb(boolean z2) {
        this.zzab = true;
        return this;
    }

    public final zzs zzc(String str) {
        this.zzaa = str;
        return this;
    }

    public final zzs zzd(String str) {
        this.zzae = str;
        return this;
    }

    public final zzs zzc(boolean z2) {
        this.zzac = true;
        return this;
    }

    public final zzt zzc() {
        String str = this.name;
        String str2 = this.zzaa;
        boolean z2 = this.zzab;
        int i2 = this.weight;
        boolean z3 = this.zzac;
        List<zzm> list = this.zzad;
        return new zzt(str, str2, z2, i2, z3, null, (zzm[]) list.toArray(new zzm[list.size()]), this.zzae, null);
    }
}
