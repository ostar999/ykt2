package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzdh extends zzdf {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzgt;
    private int zzgu;
    private int zzgv;
    private int zzgw;

    private zzdh(byte[] bArr, int i2, int i3, boolean z2) {
        super();
        this.zzgw = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i3 + i2;
        this.pos = i2;
        this.zzgv = i2;
        this.zzgt = z2;
    }

    @Override // com.google.android.gms.internal.icing.zzdf
    public final int zzat() {
        return this.pos - this.zzgv;
    }

    @Override // com.google.android.gms.internal.icing.zzdf
    public final int zzn(int i2) throws zzeh {
        if (i2 < 0) {
            throw new zzeh("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int iZzat = i2 + zzat();
        int i3 = this.zzgw;
        if (iZzat > i3) {
            throw new zzeh("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        this.zzgw = iZzat;
        int i4 = this.limit + this.zzgu;
        this.limit = i4;
        int i5 = i4 - this.zzgv;
        if (i5 > iZzat) {
            int i6 = i5 - iZzat;
            this.zzgu = i6;
            this.limit = i4 - i6;
        } else {
            this.zzgu = 0;
        }
        return i3;
    }
}
