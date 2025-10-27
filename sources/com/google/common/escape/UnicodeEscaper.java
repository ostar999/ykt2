package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    public static int codePointAt(CharSequence charSequence, int i2, int i3) {
        Preconditions.checkNotNull(charSequence);
        if (i2 >= i3) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        int i4 = i2 + 1;
        char cCharAt = charSequence.charAt(i2);
        if (cCharAt < 55296 || cCharAt > 57343) {
            return cCharAt;
        }
        if (cCharAt > 56319) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected low surrogate character '");
            sb.append(cCharAt);
            sb.append("' with value ");
            sb.append((int) cCharAt);
            sb.append(" at index ");
            sb.append(i4 - 1);
            sb.append(" in '");
            sb.append((Object) charSequence);
            sb.append("'");
            throw new IllegalArgumentException(sb.toString());
        }
        if (i4 == i3) {
            return -cCharAt;
        }
        char cCharAt2 = charSequence.charAt(i4);
        if (Character.isLowSurrogate(cCharAt2)) {
            return Character.toCodePoint(cCharAt, cCharAt2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + cCharAt2 + "' with value " + ((int) cCharAt2) + " at index " + i4 + " in '" + ((Object) charSequence) + "'");
    }

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
        int iNextEscapeIndex = nextEscapeIndex(str, 0, length);
        return iNextEscapeIndex == length ? str : escapeSlow(str, iNextEscapeIndex);
    }

    public abstract char[] escape(int i2);

    public final String escapeSlow(String str, int i2) {
        int length = str.length();
        char[] cArrCharBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int i3 = 0;
        int length2 = 0;
        while (i2 < length) {
            int iCodePointAt = codePointAt(str, i2, length);
            if (iCodePointAt < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] cArrEscape = escape(iCodePointAt);
            int i4 = (Character.isSupplementaryCodePoint(iCodePointAt) ? 2 : 1) + i2;
            if (cArrEscape != null) {
                int i5 = i2 - i3;
                int i6 = length2 + i5;
                int length3 = cArrEscape.length + i6;
                if (cArrCharBufferFromThreadLocal.length < length3) {
                    cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, length2, length3 + (length - i2) + 32);
                }
                if (i5 > 0) {
                    str.getChars(i3, i2, cArrCharBufferFromThreadLocal, length2);
                    length2 = i6;
                }
                if (cArrEscape.length > 0) {
                    System.arraycopy(cArrEscape, 0, cArrCharBufferFromThreadLocal, length2, cArrEscape.length);
                    length2 += cArrEscape.length;
                }
                i3 = i4;
            }
            i2 = nextEscapeIndex(str, i4, length);
        }
        int i7 = length - i3;
        if (i7 > 0) {
            int i8 = i7 + length2;
            if (cArrCharBufferFromThreadLocal.length < i8) {
                cArrCharBufferFromThreadLocal = growBuffer(cArrCharBufferFromThreadLocal, length2, i8);
            }
            str.getChars(i3, length, cArrCharBufferFromThreadLocal, length2);
            length2 = i8;
        }
        return new String(cArrCharBufferFromThreadLocal, 0, length2);
    }

    public int nextEscapeIndex(CharSequence charSequence, int i2, int i3) {
        while (i2 < i3) {
            int iCodePointAt = codePointAt(charSequence, i2, i3);
            if (iCodePointAt < 0 || escape(iCodePointAt) != null) {
                break;
            }
            i2 += Character.isSupplementaryCodePoint(iCodePointAt) ? 2 : 1;
        }
        return i2;
    }
}
