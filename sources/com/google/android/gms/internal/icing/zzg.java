package com.google.android.gms.internal.icing;

import android.accounts.Account;
import com.google.android.gms.common.internal.ShowFirstParty;
import java.util.ArrayList;
import java.util.List;

@ShowFirstParty
/* loaded from: classes3.dex */
public final class zzg {
    private Account account;
    private List<zzk> zzi;
    private String zzj;
    private boolean zzk;

    public final zzg zza(zzk zzkVar) {
        if (this.zzi == null && zzkVar != null) {
            this.zzi = new ArrayList();
        }
        if (zzkVar != null) {
            this.zzi.add(zzkVar);
        }
        return this;
    }

    public final zzh zzb() {
        String str = this.zzj;
        boolean z2 = this.zzk;
        Account account = this.account;
        List<zzk> list = this.zzi;
        return new zzh(str, z2, account, list != null ? (zzk[]) list.toArray(new zzk[list.size()]) : null);
    }

    public final zzg zza(String str) {
        this.zzj = str;
        return this;
    }

    public final zzg zza(boolean z2) {
        this.zzk = true;
        return this;
    }

    public final zzg zza(Account account) {
        this.account = account;
        return this;
    }
}
