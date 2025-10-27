package org.apache.commons.compress.utils;

import cn.hutool.core.text.CharPool;
import com.yikaobang.yixue.R2;
import java.io.UnsupportedEncodingException;
import java.lang.Character;
import java.util.Arrays;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
public class ArchiveUtils {
    private static final int MAX_SANITIZED_NAME_LENGTH = 255;

    private ArchiveUtils() {
    }

    public static boolean isArrayZero(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (bArr[i3] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEqual(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5, boolean z2) {
        int i6 = i3 < i5 ? i3 : i5;
        for (int i7 = 0; i7 < i6; i7++) {
            if (bArr[i2 + i7] != bArr2[i4 + i7]) {
                return false;
            }
        }
        if (i3 == i5) {
            return true;
        }
        if (!z2) {
            return false;
        }
        if (i3 > i5) {
            while (i5 < i3) {
                if (bArr[i2 + i5] != 0) {
                    return false;
                }
                i5++;
            }
        } else {
            while (i3 < i5) {
                if (bArr2[i4 + i3] != 0) {
                    return false;
                }
                i3++;
            }
        }
        return true;
    }

    public static boolean isEqualWithNull(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        return isEqual(bArr, i2, i3, bArr2, i4, i5, true);
    }

    public static boolean matchAsciiBuffer(String str, byte[] bArr, int i2, int i3) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("US-ASCII");
            return isEqual(bytes, 0, bytes.length, bArr, i2, i3, false);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String sanitize(String str) {
        Character.UnicodeBlock unicodeBlockOf;
        char[] charArray = str.toCharArray();
        char[] cArrCopyOf = charArray.length <= 255 ? charArray : Arrays.copyOf(charArray, 255);
        if (charArray.length > 255) {
            for (int i2 = R2.attr.actionModeShareDrawable; i2 < 255; i2++) {
                cArrCopyOf[i2] = '.';
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c3 : cArrCopyOf) {
            if (Character.isISOControl(c3) || (unicodeBlockOf = Character.UnicodeBlock.of(c3)) == null || unicodeBlockOf == Character.UnicodeBlock.SPECIALS) {
                sb.append('?');
            } else {
                sb.append(c3);
            }
        }
        return sb.toString();
    }

    public static byte[] toAsciiBytes(String str) {
        try {
            return str.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String toAsciiString(byte[] bArr) {
        try {
            return new String(bArr, "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String toString(ArchiveEntry archiveEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append(archiveEntry.isDirectory() ? 'd' : CharPool.DASHED);
        String string = Long.toString(archiveEntry.getSize());
        sb.append(' ');
        for (int i2 = 7; i2 > string.length(); i2--) {
            sb.append(' ');
        }
        sb.append(string);
        sb.append(' ');
        sb.append(archiveEntry.getName());
        return sb.toString();
    }

    public static String toAsciiString(byte[] bArr, int i2, int i3) {
        try {
            return new String(bArr, i2, i3, "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static boolean isEqual(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        return isEqual(bArr, i2, i3, bArr2, i4, i5, false);
    }

    public static boolean matchAsciiBuffer(String str, byte[] bArr) {
        return matchAsciiBuffer(str, bArr, 0, bArr.length);
    }

    public static boolean isEqual(byte[] bArr, byte[] bArr2) {
        return isEqual(bArr, 0, bArr.length, bArr2, 0, bArr2.length, false);
    }

    public static boolean isEqual(byte[] bArr, byte[] bArr2, boolean z2) {
        return isEqual(bArr, 0, bArr.length, bArr2, 0, bArr2.length, z2);
    }
}
