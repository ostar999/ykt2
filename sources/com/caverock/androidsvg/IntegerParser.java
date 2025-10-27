package com.caverock.androidsvg;

import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes2.dex */
class IntegerParser {
    private int pos;
    private long value;

    public IntegerParser(long j2, int i2) {
        this.value = j2;
        this.pos = i2;
    }

    public static IntegerParser parseHex(String str, int i2, int i3) {
        long j2;
        int i4;
        if (i2 >= i3) {
            return null;
        }
        long j3 = 0;
        int i5 = i2;
        while (i5 < i3) {
            char cCharAt = str.charAt(i5);
            if (cCharAt < '0' || cCharAt > '9') {
                if (cCharAt >= 'A' && cCharAt <= 'F') {
                    j2 = j3 * 16;
                    i4 = cCharAt - 'A';
                } else {
                    if (cCharAt < 'a' || cCharAt > 'f') {
                        break;
                    }
                    j2 = j3 * 16;
                    i4 = cCharAt - 'a';
                }
                j3 = j2 + i4 + 10;
            } else {
                j3 = (j3 * 16) + (cCharAt - '0');
            }
            if (j3 > InternalZipConstants.ZIP_64_LIMIT) {
                return null;
            }
            i5++;
        }
        if (i5 == i2) {
            return null;
        }
        return new IntegerParser(j3, i5);
    }

    public static IntegerParser parseInt(String str, int i2, int i3, boolean z2) {
        if (i2 >= i3) {
            return null;
        }
        boolean z3 = false;
        if (z2) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '+') {
                i2++;
            } else if (cCharAt == '-') {
                z3 = true;
                i2++;
            }
        }
        long j2 = 0;
        int i4 = i2;
        while (i4 < i3) {
            char cCharAt2 = str.charAt(i4);
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                break;
            }
            if (z3) {
                j2 = (j2 * 10) - (cCharAt2 - '0');
                if (j2 < -2147483648L) {
                    return null;
                }
            } else {
                j2 = (j2 * 10) + (cCharAt2 - '0');
                if (j2 > 2147483647L) {
                    return null;
                }
            }
            i4++;
        }
        if (i4 == i2) {
            return null;
        }
        return new IntegerParser(j2, i4);
    }

    public int getEndPos() {
        return this.pos;
    }

    public int value() {
        return (int) this.value;
    }
}
