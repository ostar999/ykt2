package com.google.android.gms.internal.icing;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;

/* loaded from: classes3.dex */
final class zzgv {
    private static final zzgx zzpg;

    static {
        zzpg = (!(zzgs.zzdn() && zzgs.zzdo()) || zzcs.zzal()) ? new zzgw() : new zzgy();
    }

    public static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        int i3 = 0;
        while (i3 < length && charSequence.charAt(i3) < 128) {
            i3++;
        }
        int i4 = length;
        while (true) {
            if (i3 >= length) {
                break;
            }
            char cCharAt = charSequence.charAt(i3);
            if (cCharAt < 2048) {
                i4 += (127 - cCharAt) >>> 31;
                i3++;
            } else {
                int length2 = charSequence.length();
                while (i3 < length2) {
                    char cCharAt2 = charSequence.charAt(i3);
                    if (cCharAt2 < 2048) {
                        i2 += (127 - cCharAt2) >>> 31;
                    } else {
                        i2 += 2;
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i3) < 65536) {
                                throw new zzgz(i3, length2);
                            }
                            i3++;
                        }
                    }
                    i3++;
                }
                i4 += i2;
            }
        }
        if (i4 >= length) {
            return i4;
        }
        long j2 = i4 + IjkMediaMeta.AV_CH_WIDE_RIGHT;
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(j2);
        throw new IllegalArgumentException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzal(int i2) {
        if (i2 > -12) {
            return -1;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzc(int i2, int i3, int i4) {
        if (i2 > -12 || i3 > -65 || i4 > -65) {
            return -1;
        }
        return (i2 ^ (i3 << 8)) ^ (i4 << 16);
    }

    public static boolean zzc(byte[] bArr, int i2, int i3) {
        return zzpg.zzc(bArr, i2, i3);
    }

    public static boolean zzd(byte[] bArr) {
        return zzpg.zzc(bArr, 0, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzo(int i2, int i3) {
        if (i2 > -12 || i3 > -65) {
            return -1;
        }
        return i2 ^ (i3 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzd(byte[] bArr, int i2, int i3) {
        byte b3 = bArr[i2 - 1];
        int i4 = i3 - i2;
        if (i4 == 0) {
            return zzal(b3);
        }
        if (i4 == 1) {
            return zzo(b3, bArr[i2]);
        }
        if (i4 == 2) {
            return zzc(b3, bArr[i2], bArr[i2 + 1]);
        }
        throw new AssertionError();
    }

    public static int zza(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        return zzpg.zzb(charSequence, bArr, i2, i3);
    }
}
