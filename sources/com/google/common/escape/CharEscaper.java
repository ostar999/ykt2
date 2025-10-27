package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public abstract class CharEscaper extends Escaper {
    private static final int DEST_PAD_MULTIPLIER = 2;

    private static char[] growBuffer(char[] cArr, int i2, int i3) {
        if (i3 < 0) {
            throw new AssertionError("Cannot increase internal buffer any further");
        }
        char[] cArr2 = new char[i3];
        if (i2 > 0) {
            System.arraycopy(cArr, 0, cArr2, 0, i2);
        }
        return cArr2;
    }

    @Override // com.google.common.escape.Escaper
    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (escape(str.charAt(i2)) != null) {
                return escapeSlow(str, i2);
            }
        }
        return str;
    }

    public abstract char[] escape(char c3);

    public final String escapeSlow(String str, int i2) {
        int length = str.length();
        char[] cArrCharBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int length2 = cArrCharBufferFromThreadLocal.length;
        int i3 = 0;
        int i4 = 0;
        while (i2 < length) {
            char[] cArrEscape = escape(str.charAt(i2));
            if (cArrEscape != null) {
                int length3 = cArrEscape.length;
                int i5 = i2 - i3;
                int i6 = i4 + i5;
                int i7 = i6 + length3;
                if (length2 < i7) {
                    length2 = ((length - i2) * 2) + i7;
                    cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, i4, length2);
                }
                if (i5 > 0) {
                    str.getChars(i3, i2, cArrCharBufferFromThreadLocal, i4);
                    i4 = i6;
                }
                if (length3 > 0) {
                    System.arraycopy(cArrEscape, 0, cArrCharBufferFromThreadLocal, i4, length3);
                    i4 += length3;
                }
                i3 = i2 + 1;
            }
            i2++;
        }
        int i8 = length - i3;
        if (i8 > 0) {
            int i9 = i8 + i4;
            if (length2 < i9) {
                cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, i4, i9);
            }
            str.getChars(i3, length, cArrCharBufferFromThreadLocal, i4);
            i4 = i9;
        }
        return new String(cArrCharBufferFromThreadLocal, 0, i4);
    }
}
