package com.google.common.base;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public final class Utf8 {
    private Utf8() {
    }

    public static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int iEncodedLengthGeneral = length;
        while (true) {
            if (i2 < length) {
                char cCharAt = charSequence.charAt(i2);
                if (cCharAt >= 2048) {
                    iEncodedLengthGeneral += encodedLengthGeneral(charSequence, i2);
                    break;
                }
                iEncodedLengthGeneral += (127 - cCharAt) >>> 31;
                i2++;
            } else {
                break;
            }
        }
        if (iEncodedLengthGeneral >= length) {
            return iEncodedLengthGeneral;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (iEncodedLengthGeneral + IjkMediaMeta.AV_CH_WIDE_RIGHT));
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 2048) {
                i3 += (127 - cCharAt) >>> 31;
            } else {
                i3 += 2;
                if (55296 <= cCharAt && cCharAt <= 57343) {
                    if (Character.codePointAt(charSequence, i2) == cCharAt) {
                        throw new IllegalArgumentException(unpairedSurrogateMsg(i2));
                    }
                    i2++;
                }
            }
            i2++;
        }
        return i3;
    }

    public static boolean isWellFormed(byte[] bArr) {
        return isWellFormed(bArr, 0, bArr.length);
    }

    private static boolean isWellFormedSlowPath(byte[] bArr, int i2, int i3) {
        byte b3;
        while (i2 < i3) {
            int i4 = i2 + 1;
            byte b4 = bArr[i2];
            if (b4 < 0) {
                if (b4 < -32) {
                    if (i4 != i3 && b4 >= -62) {
                        i2 = i4 + 1;
                        if (bArr[i4] > -65) {
                        }
                    }
                    return false;
                }
                if (b4 < -16) {
                    int i5 = i4 + 1;
                    if (i5 < i3 && (b3 = bArr[i4]) <= -65 && ((b4 != -32 || b3 >= -96) && (b4 != -19 || -96 > b3))) {
                        i2 = i5 + 1;
                        if (bArr[i5] > -65) {
                        }
                    }
                    return false;
                }
                if (i4 + 2 >= i3) {
                    return false;
                }
                int i6 = i4 + 1;
                byte b5 = bArr[i4];
                if (b5 <= -65 && (((b4 << Ascii.FS) + (b5 + 112)) >> 30) == 0) {
                    int i7 = i6 + 1;
                    if (bArr[i6] <= -65) {
                        i4 = i7 + 1;
                        if (bArr[i7] > -65) {
                        }
                    }
                }
                return false;
            }
            i2 = i4;
        }
        return true;
    }

    private static String unpairedSurrogateMsg(int i2) {
        return "Unpaired surrogate at index " + i2;
    }

    public static boolean isWellFormed(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        Preconditions.checkPositionIndexes(i2, i4, bArr.length);
        while (i2 < i4) {
            if (bArr[i2] < 0) {
                return isWellFormedSlowPath(bArr, i2, i4);
            }
            i2++;
        }
        return true;
    }
}
