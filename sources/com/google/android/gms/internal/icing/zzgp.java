package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class zzgp {
    private static final zzgp zzof = new zzgp(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzgb;
    private int zzkd;
    private Object[] zzmu;
    private int[] zzog;

    private zzgp() {
        this(0, new int[8], new Object[8], true);
    }

    public static zzgp zza(zzgp zzgpVar, zzgp zzgpVar2) {
        int i2 = zzgpVar.count + zzgpVar2.count;
        int[] iArrCopyOf = Arrays.copyOf(zzgpVar.zzog, i2);
        System.arraycopy(zzgpVar2.zzog, 0, iArrCopyOf, zzgpVar.count, zzgpVar2.count);
        Object[] objArrCopyOf = Arrays.copyOf(zzgpVar.zzmu, i2);
        System.arraycopy(zzgpVar2.zzmu, 0, objArrCopyOf, zzgpVar.count, zzgpVar2.count);
        return new zzgp(i2, iArrCopyOf, objArrCopyOf, true);
    }

    public static zzgp zzdl() {
        return zzof;
    }

    public final boolean equals(Object obj) {
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzgp)) {
            return false;
        }
        zzgp zzgpVar = (zzgp) obj;
        int i2 = this.count;
        if (i2 == zzgpVar.count) {
            int[] iArr = this.zzog;
            int[] iArr2 = zzgpVar.zzog;
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    z2 = true;
                    break;
                }
                if (iArr[i3] != iArr2[i3]) {
                    z2 = false;
                    break;
                }
                i3++;
            }
            if (z2) {
                Object[] objArr = this.zzmu;
                Object[] objArr2 = zzgpVar.zzmu;
                int i4 = this.count;
                int i5 = 0;
                while (true) {
                    if (i5 >= i4) {
                        z3 = true;
                        break;
                    }
                    if (!objArr[i5].equals(objArr2[i5])) {
                        z3 = false;
                        break;
                    }
                    i5++;
                }
                if (z3) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i2 = this.count;
        int i3 = (i2 + R2.attr.bl_checkable_gradient_endColor) * 31;
        int[] iArr = this.zzog;
        int iHashCode = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i3 + i4) * 31;
        Object[] objArr = this.zzmu;
        int i7 = this.count;
        for (int i8 = 0; i8 < i7; i8++) {
            iHashCode = (iHashCode * 31) + objArr[i8].hashCode();
        }
        return i6 + iHashCode;
    }

    public final void zzai() {
        this.zzgb = false;
    }

    public final void zzb(zzhg zzhgVar) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (zzhgVar.zzay() == zzdx.zze.zzkx) {
            for (int i2 = 0; i2 < this.count; i2++) {
                zzb(this.zzog[i2], this.zzmu[i2], zzhgVar);
            }
            return;
        }
        for (int i3 = this.count - 1; i3 >= 0; i3--) {
            zzb(this.zzog[i3], this.zzmu[i3], zzhgVar);
        }
    }

    public final int zzbl() {
        int iZze;
        int i2 = this.zzkd;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.count; i4++) {
            int i5 = this.zzog[i4];
            int i6 = i5 >>> 3;
            int i7 = i5 & 7;
            if (i7 == 0) {
                iZze = zzdk.zze(i6, ((Long) this.zzmu[i4]).longValue());
            } else if (i7 == 1) {
                iZze = zzdk.zzg(i6, ((Long) this.zzmu[i4]).longValue());
            } else if (i7 == 2) {
                iZze = zzdk.zzc(i6, (zzct) this.zzmu[i4]);
            } else if (i7 == 3) {
                iZze = (zzdk.zzs(i6) << 1) + ((zzgp) this.zzmu[i4]).zzbl();
            } else {
                if (i7 != 5) {
                    throw new IllegalStateException(zzeh.zzbz());
                }
                iZze = zzdk.zzj(i6, ((Integer) this.zzmu[i4]).intValue());
            }
            i3 += iZze;
        }
        this.zzkd = i3;
        return i3;
    }

    public final int zzdm() {
        int i2 = this.zzkd;
        if (i2 != -1) {
            return i2;
        }
        int iZzd = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            iZzd += zzdk.zzd(this.zzog[i3] >>> 3, (zzct) this.zzmu[i3]);
        }
        this.zzkd = iZzd;
        return iZzd;
    }

    private zzgp(int i2, int[] iArr, Object[] objArr, boolean z2) {
        this.zzkd = -1;
        this.count = i2;
        this.zzog = iArr;
        this.zzmu = objArr;
        this.zzgb = z2;
    }

    private static void zzb(int i2, Object obj, zzhg zzhgVar) throws IOException {
        int i3 = i2 >>> 3;
        int i4 = i2 & 7;
        if (i4 == 0) {
            zzhgVar.zzi(i3, ((Long) obj).longValue());
            return;
        }
        if (i4 == 1) {
            zzhgVar.zzc(i3, ((Long) obj).longValue());
            return;
        }
        if (i4 == 2) {
            zzhgVar.zza(i3, (zzct) obj);
            return;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                zzhgVar.zzf(i3, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzeh.zzbz());
        }
        if (zzhgVar.zzay() == zzdx.zze.zzkx) {
            zzhgVar.zzab(i3);
            ((zzgp) obj).zzb(zzhgVar);
            zzhgVar.zzac(i3);
        } else {
            zzhgVar.zzac(i3);
            ((zzgp) obj).zzb(zzhgVar);
            zzhgVar.zzab(i3);
        }
    }

    public final void zza(zzhg zzhgVar) throws IOException {
        if (zzhgVar.zzay() == zzdx.zze.zzky) {
            for (int i2 = this.count - 1; i2 >= 0; i2--) {
                zzhgVar.zza(this.zzog[i2] >>> 3, this.zzmu[i2]);
            }
            return;
        }
        for (int i3 = 0; i3 < this.count; i3++) {
            zzhgVar.zza(this.zzog[i3] >>> 3, this.zzmu[i3]);
        }
    }

    public final void zza(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < this.count; i3++) {
            zzfi.zza(sb, i2, String.valueOf(this.zzog[i3] >>> 3), this.zzmu[i3]);
        }
    }
}
