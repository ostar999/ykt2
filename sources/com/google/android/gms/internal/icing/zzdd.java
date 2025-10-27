package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
class zzdd extends zzde {
    protected final byte[] zzgp;

    public zzdd(byte[] bArr) {
        bArr.getClass();
        this.zzgp = bArr;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzct) || size() != ((zzct) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzdd)) {
            return obj.equals(this);
        }
        zzdd zzddVar = (zzdd) obj;
        int iZzap = zzap();
        int iZzap2 = zzddVar.zzap();
        if (iZzap == 0 || iZzap2 == 0 || iZzap == iZzap2) {
            return zza(zzddVar, 0, size());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public int size() {
        return this.zzgp.length;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final zzct zza(int i2, int i3) {
        int iZzb = zzct.zzb(0, i3, size());
        return iZzb == 0 ? zzct.zzgi : new zzda(this.zzgp, zzaq(), iZzb);
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final boolean zzao() {
        int iZzaq = zzaq();
        return zzgv.zzc(this.zzgp, iZzaq, size() + iZzaq);
    }

    public int zzaq() {
        return 0;
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public byte zzk(int i2) {
        return this.zzgp[i2];
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public byte zzl(int i2) {
        return this.zzgp[i2];
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final void zza(zzcu zzcuVar) throws IOException {
        zzcuVar.zza(this.zzgp, zzaq(), size());
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final String zza(Charset charset) {
        return new String(this.zzgp, zzaq(), size(), charset);
    }

    @Override // com.google.android.gms.internal.icing.zzde
    public final boolean zza(zzct zzctVar, int i2, int i3) {
        if (i3 <= zzctVar.size()) {
            if (i3 <= zzctVar.size()) {
                if (zzctVar instanceof zzdd) {
                    zzdd zzddVar = (zzdd) zzctVar;
                    byte[] bArr = this.zzgp;
                    byte[] bArr2 = zzddVar.zzgp;
                    int iZzaq = zzaq() + i3;
                    int iZzaq2 = zzaq();
                    int iZzaq3 = zzddVar.zzaq();
                    while (iZzaq2 < iZzaq) {
                        if (bArr[iZzaq2] != bArr2[iZzaq3]) {
                            return false;
                        }
                        iZzaq2++;
                        iZzaq3++;
                    }
                    return true;
                }
                return zzctVar.zza(0, i3).equals(zza(0, i3));
            }
            int size = zzctVar.size();
            StringBuilder sb = new StringBuilder(59);
            sb.append("Ran off end of other: 0, ");
            sb.append(i3);
            sb.append(", ");
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        }
        int size2 = size();
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Length too large: ");
        sb2.append(i3);
        sb2.append(size2);
        throw new IllegalArgumentException(sb2.toString());
    }

    @Override // com.google.android.gms.internal.icing.zzct
    public final int zza(int i2, int i3, int i4) {
        return zzeb.zza(i2, this.zzgp, zzaq(), i4);
    }
}
