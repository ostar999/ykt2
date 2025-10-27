package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class zzct implements Serializable, Iterable<Byte> {
    public static final zzct zzgi = new zzdd(zzeb.zzla);
    private static final zzcz zzgj;
    private static final Comparator<zzct> zzgk;
    private int zzef = 0;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        zzcw zzcwVar = null;
        zzgj = zzcs.zzal() ? new zzdg(zzcwVar) : new zzcx(zzcwVar);
        zzgk = new zzcv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zza(byte b3) {
        return b3 & 255;
    }

    public static int zzb(int i2, int i3, int i4) {
        int i5 = i3 - i2;
        if ((i2 | i3 | i5 | (i4 - i3)) >= 0) {
            return i5;
        }
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i2);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i3 < i2) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(i3);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i3);
        sb3.append(" >= ");
        sb3.append(i4);
        throw new IndexOutOfBoundsException(sb3.toString());
    }

    public static zzdb zzm(int i2) {
        return new zzdb(i2, null);
    }

    public static zzct zzp(String str) {
        return new zzdd(str.getBytes(zzeb.UTF_8));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZza = this.zzef;
        if (iZza == 0) {
            int size = size();
            iZza = zza(size, 0, size);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzef = iZza;
        }
        return iZza;
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzcw(this);
    }

    public abstract int size();

    public final String toString() {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.toHexString(System.identityHashCode(this));
        objArr[1] = Integer.valueOf(size());
        objArr[2] = size() <= 50 ? zzgi.zzd(this) : String.valueOf(zzgi.zzd(zza(0, 47))).concat("...");
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    public abstract int zza(int i2, int i3, int i4);

    public abstract zzct zza(int i2, int i3);

    public abstract String zza(Charset charset);

    public abstract void zza(zzcu zzcuVar) throws IOException;

    public final String zzan() {
        return size() == 0 ? "" : zza(zzeb.UTF_8);
    }

    public abstract boolean zzao();

    public final int zzap() {
        return this.zzef;
    }

    public abstract byte zzk(int i2);

    public abstract byte zzl(int i2);
}
