package com.google.android.gms.internal.icing;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzgw extends zzgx {
    @Override // com.google.android.gms.internal.icing.zzgx
    public final int zzb(int i2, byte[] bArr, int i3, int i4) {
        while (i3 < i4 && bArr[i3] >= 0) {
            i3++;
        }
        if (i3 >= i4) {
            return 0;
        }
        while (i3 < i4) {
            int i5 = i3 + 1;
            byte b3 = bArr[i3];
            if (b3 < 0) {
                if (b3 < -32) {
                    if (i5 >= i4) {
                        return b3;
                    }
                    if (b3 >= -62) {
                        i3 = i5 + 1;
                        if (bArr[i5] > -65) {
                        }
                    }
                    return -1;
                }
                if (b3 >= -16) {
                    if (i5 >= i4 - 2) {
                        return zzgv.zzd(bArr, i5, i4);
                    }
                    int i6 = i5 + 1;
                    byte b4 = bArr[i5];
                    if (b4 <= -65 && (((b3 << Ascii.FS) + (b4 + 112)) >> 30) == 0) {
                        int i7 = i6 + 1;
                        if (bArr[i6] <= -65) {
                            i5 = i7 + 1;
                            if (bArr[i7] > -65) {
                            }
                        }
                    }
                    return -1;
                }
                if (i5 >= i4 - 1) {
                    return zzgv.zzd(bArr, i5, i4);
                }
                int i8 = i5 + 1;
                byte b5 = bArr[i5];
                if (b5 <= -65 && ((b3 != -32 || b5 >= -96) && (b3 != -19 || b5 < -96))) {
                    i3 = i8 + 1;
                    if (bArr[i8] > -65) {
                    }
                }
                return -1;
            }
            i3 = i5;
        }
        return 0;
    }

    @Override // com.google.android.gms.internal.icing.zzgx
    public final int zzb(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        char cCharAt;
        int length = charSequence.length();
        int i7 = i3 + i2;
        int i8 = 0;
        while (i8 < length && (i6 = i8 + i2) < i7 && (cCharAt = charSequence.charAt(i8)) < 128) {
            bArr[i6] = (byte) cCharAt;
            i8++;
        }
        if (i8 == length) {
            return i2 + length;
        }
        int i9 = i2 + i8;
        while (i8 < length) {
            char cCharAt2 = charSequence.charAt(i8);
            if (cCharAt2 >= 128 || i9 >= i7) {
                if (cCharAt2 < 2048 && i9 <= i7 - 2) {
                    int i10 = i9 + 1;
                    bArr[i9] = (byte) ((cCharAt2 >>> 6) | 960);
                    i9 = i10 + 1;
                    bArr[i10] = (byte) ((cCharAt2 & '?') | 128);
                } else {
                    if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i9 > i7 - 3) {
                        if (i9 <= i7 - 4) {
                            int i11 = i8 + 1;
                            if (i11 != charSequence.length()) {
                                char cCharAt3 = charSequence.charAt(i11);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    int i12 = i9 + 1;
                                    bArr[i9] = (byte) ((codePoint >>> 18) | 240);
                                    int i13 = i12 + 1;
                                    bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                                    int i14 = i13 + 1;
                                    bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                                    i9 = i14 + 1;
                                    bArr[i14] = (byte) ((codePoint & 63) | 128);
                                    i8 = i11;
                                } else {
                                    i8 = i11;
                                }
                            }
                            throw new zzgz(i8 - 1, length);
                        }
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i5 = i8 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i5)))) {
                            throw new zzgz(i8, length);
                        }
                        StringBuilder sb = new StringBuilder(37);
                        sb.append("Failed writing ");
                        sb.append(cCharAt2);
                        sb.append(" at index ");
                        sb.append(i9);
                        throw new ArrayIndexOutOfBoundsException(sb.toString());
                    }
                    int i15 = i9 + 1;
                    bArr[i9] = (byte) ((cCharAt2 >>> '\f') | 480);
                    int i16 = i15 + 1;
                    bArr[i15] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                    i4 = i16 + 1;
                    bArr[i16] = (byte) ((cCharAt2 & '?') | 128);
                }
                i8++;
            } else {
                i4 = i9 + 1;
                bArr[i9] = (byte) cCharAt2;
            }
            i9 = i4;
            i8++;
        }
        return i9;
    }
}
