package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzda extends zzdd {
    private final int zzgm;
    private final int zzgn;

    public zzda(byte[] bArr, int i2, int i3) {
        super(bArr);
        zzct.zzb(i2, i2 + i3, bArr.length);
        this.zzgm = i2;
        this.zzgn = i3;
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    public final int size() {
        return this.zzgn;
    }

    @Override // com.google.android.gms.internal.icing.zzdd
    public final int zzaq() {
        return this.zzgm;
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    public final byte zzk(int i2) {
        int size = size();
        if (((size - (i2 + 1)) | i2) >= 0) {
            return this.zzgp[this.zzgm + i2];
        }
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i2);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i2);
        sb2.append(", ");
        sb2.append(size);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    public final byte zzl(int i2) {
        return this.zzgp[this.zzgm + i2];
    }
}
