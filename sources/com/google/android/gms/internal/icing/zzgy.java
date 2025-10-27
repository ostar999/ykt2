package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzgy extends zzgx {
    private static int zza(byte[] bArr, int i2, long j2, int i3) {
        if (i3 == 0) {
            return zzgv.zzal(i2);
        }
        if (i3 == 1) {
            return zzgv.zzo(i2, zzgs.zza(bArr, j2));
        }
        if (i3 == 2) {
            return zzgv.zzc(i2, zzgs.zza(bArr, j2), zzgs.zza(bArr, j2 + 1));
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008f, code lost:
    
        return -1;
     */
    @Override // com.google.android.gms.internal.icing.zzgx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzb(int r18, byte[] r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzgy.zzb(int, byte[], int, int):int");
    }

    @Override // com.google.android.gms.internal.icing.zzgx
    public final int zzb(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        char c3;
        long j2;
        long j3;
        long j4;
        char c4;
        int i4;
        char cCharAt;
        long j5 = i2;
        long j6 = i3 + j5;
        int length = charSequence.length();
        if (length > i3 || bArr.length - i3 < i2) {
            char cCharAt2 = charSequence.charAt(length - 1);
            StringBuilder sb = new StringBuilder(37);
            sb.append("Failed writing ");
            sb.append(cCharAt2);
            sb.append(" at index ");
            sb.append(i2 + i3);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        int i5 = 0;
        while (true) {
            c3 = 128;
            j2 = 1;
            if (i5 >= length || (cCharAt = charSequence.charAt(i5)) >= 128) {
                break;
            }
            zzgs.zza(bArr, j5, (byte) cCharAt);
            i5++;
            j5 = 1 + j5;
        }
        if (i5 == length) {
            return (int) j5;
        }
        while (i5 < length) {
            char cCharAt3 = charSequence.charAt(i5);
            if (cCharAt3 < c3 && j5 < j6) {
                long j7 = j5 + j2;
                zzgs.zza(bArr, j5, (byte) cCharAt3);
                j4 = j2;
                j3 = j7;
                c4 = c3;
            } else if (cCharAt3 < 2048 && j5 <= j6 - 2) {
                long j8 = j5 + j2;
                zzgs.zza(bArr, j5, (byte) ((cCharAt3 >>> 6) | 960));
                long j9 = j8 + j2;
                zzgs.zza(bArr, j8, (byte) ((cCharAt3 & '?') | 128));
                long j10 = j2;
                c4 = 128;
                j3 = j9;
                j4 = j10;
            } else {
                if ((cCharAt3 >= 55296 && 57343 >= cCharAt3) || j5 > j6 - 3) {
                    if (j5 <= j6 - 4) {
                        int i6 = i5 + 1;
                        if (i6 != length) {
                            char cCharAt4 = charSequence.charAt(i6);
                            if (Character.isSurrogatePair(cCharAt3, cCharAt4)) {
                                int codePoint = Character.toCodePoint(cCharAt3, cCharAt4);
                                long j11 = j5 + 1;
                                zzgs.zza(bArr, j5, (byte) ((codePoint >>> 18) | 240));
                                long j12 = j11 + 1;
                                c4 = 128;
                                zzgs.zza(bArr, j11, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j13 = j12 + 1;
                                zzgs.zza(bArr, j12, (byte) (((codePoint >>> 6) & 63) | 128));
                                j4 = 1;
                                j3 = j13 + 1;
                                zzgs.zza(bArr, j13, (byte) ((codePoint & 63) | 128));
                                i5 = i6;
                            } else {
                                i5 = i6;
                            }
                        }
                        throw new zzgz(i5 - 1, length);
                    }
                    if (55296 <= cCharAt3 && cCharAt3 <= 57343 && ((i4 = i5 + 1) == length || !Character.isSurrogatePair(cCharAt3, charSequence.charAt(i4)))) {
                        throw new zzgz(i5, length);
                    }
                    StringBuilder sb2 = new StringBuilder(46);
                    sb2.append("Failed writing ");
                    sb2.append(cCharAt3);
                    sb2.append(" at index ");
                    sb2.append(j5);
                    throw new ArrayIndexOutOfBoundsException(sb2.toString());
                }
                long j14 = j5 + j2;
                zzgs.zza(bArr, j5, (byte) ((cCharAt3 >>> '\f') | 480));
                long j15 = j14 + j2;
                zzgs.zza(bArr, j14, (byte) (((cCharAt3 >>> 6) & 63) | 128));
                zzgs.zza(bArr, j15, (byte) ((cCharAt3 & '?') | 128));
                j3 = j15 + 1;
                j4 = 1;
                c4 = 128;
            }
            i5++;
            c3 = c4;
            long j16 = j4;
            j5 = j3;
            j2 = j16;
        }
        return (int) j5;
    }
}
