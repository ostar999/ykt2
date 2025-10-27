package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* loaded from: classes3.dex */
final class zzfv implements zzff {
    private final int flags;
    private final String info;
    private final zzfh zzmn;
    private final Object[] zzmu;

    public zzfv(zzfh zzfhVar, String str, Object[] objArr) {
        this.zzmn = zzfhVar;
        this.info = str;
        this.zzmu = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.flags = cCharAt;
            return;
        }
        int i2 = cCharAt & 8191;
        int i3 = 13;
        int i4 = 1;
        while (true) {
            int i5 = i4 + 1;
            char cCharAt2 = str.charAt(i4);
            if (cCharAt2 < 55296) {
                this.flags = i2 | (cCharAt2 << i3);
                return;
            } else {
                i2 |= (cCharAt2 & 8191) << i3;
                i3 += 13;
                i4 = i5;
            }
        }
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final int zzco() {
        return (this.flags & 1) == 1 ? zzdx.zze.zzku : zzdx.zze.zzkv;
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final boolean zzcp() {
        return (this.flags & 2) == 2;
    }

    @Override // com.google.android.gms.internal.icing.zzff
    public final zzfh zzcq() {
        return this.zzmn;
    }

    public final String zzcw() {
        return this.info;
    }

    public final Object[] zzcx() {
        return this.zzmu;
    }
}
