package com.google.android.gms.internal.icing;

import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
public final class zzeb {
    public static final byte[] zzla;
    private static final ByteBuffer zzlb;
    private static final zzdf zzlc;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    static {
        byte[] bArr = new byte[0];
        zzla = bArr;
        zzlb = ByteBuffer.wrap(bArr);
        zzlc = zzdf.zza(bArr, 0, bArr.length, false);
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int iZza = zza(length, bArr, 0, length);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }

    public static <T> T zza(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static boolean zzd(byte[] bArr) {
        return zzgv.zzd(bArr);
    }

    public static String zze(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static boolean zzf(zzfh zzfhVar) {
        if (!(zzfhVar instanceof zzcn)) {
            return false;
        }
        return false;
    }

    public static int zzg(boolean z2) {
        return z2 ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag;
    }

    public static int zzk(long j2) {
        return (int) (j2 ^ (j2 >>> 32));
    }

    public static int zza(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = i3; i5 < i3 + i4; i5++) {
            i2 = (i2 * 31) + bArr[i5];
        }
        return i2;
    }

    public static Object zza(Object obj, Object obj2) {
        return ((zzfh) obj).zzbq().zza((zzfh) obj2).zzbw();
    }
}
