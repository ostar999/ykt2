package org.bouncycastle.util;

import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.util.Vector;
import okio.Utf8;

/* loaded from: classes9.dex */
public final class Strings {
    public static String fromUTF8ByteArray(byte[] bArr) {
        char c3;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            i4++;
            byte b3 = bArr[i3];
            if ((b3 & 240) == 240) {
                i4++;
                i3 += 4;
            } else {
                i3 = (b3 & 224) == 224 ? i3 + 3 : (b3 & 192) == 192 ? i3 + 2 : i3 + 1;
            }
        }
        char[] cArr = new char[i4];
        int i5 = 0;
        while (i2 < bArr.length) {
            byte b4 = bArr[i2];
            if ((b4 & 240) == 240) {
                int i6 = (((((b4 & 3) << 18) | ((bArr[i2 + 1] & Utf8.REPLACEMENT_BYTE) << 12)) | ((bArr[i2 + 2] & Utf8.REPLACEMENT_BYTE) << 6)) | (bArr[i2 + 3] & Utf8.REPLACEMENT_BYTE)) - 65536;
                char c4 = (char) ((i6 >> 10) | 55296);
                c3 = (char) ((i6 & 1023) | Utf8.LOG_SURROGATE_HEADER);
                cArr[i5] = c4;
                i2 += 4;
                i5++;
            } else if ((b4 & 224) == 224) {
                c3 = (char) (((b4 & 15) << 12) | ((bArr[i2 + 1] & Utf8.REPLACEMENT_BYTE) << 6) | (bArr[i2 + 2] & Utf8.REPLACEMENT_BYTE));
                i2 += 3;
            } else if ((b4 & 208) == 208 || (b4 & 192) == 192) {
                int i7 = (b4 & Ascii.US) << 6;
                byte b5 = bArr[i2 + 1];
                c3 = (char) (i7 | (b5 & Utf8.REPLACEMENT_BYTE));
                i2 += 2;
            } else {
                c3 = (char) (b4 & 255);
                i2++;
            }
            cArr[i5] = c3;
            i5++;
        }
        return new String(cArr);
    }

    public static String[] split(String str, char c3) {
        int i2;
        Vector vector = new Vector();
        boolean z2 = true;
        while (true) {
            if (!z2) {
                break;
            }
            int iIndexOf = str.indexOf(c3);
            if (iIndexOf > 0) {
                vector.addElement(str.substring(0, iIndexOf));
                str = str.substring(iIndexOf + 1);
            } else {
                vector.addElement(str);
                z2 = false;
            }
        }
        int size = vector.size();
        String[] strArr = new String[size];
        for (i2 = 0; i2 != size; i2++) {
            strArr[i2] = (String) vector.elementAt(i2);
        }
        return strArr;
    }

    public static byte[] toByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) str.charAt(i2);
        }
        return bArr;
    }

    public static byte[] toByteArray(char[] cArr) {
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static String toLowerCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c3 = charArray[i2];
            if ('A' <= c3 && 'Z' >= c3) {
                charArray[i2] = (char) ((c3 - 'A') + 97);
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }

    public static byte[] toUTF8ByteArray(String str) {
        return toUTF8ByteArray(str.toCharArray());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [int] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static byte[] toUTF8ByteArray(char[] cArr) {
        int i2;
        char c3;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i3 = 0;
        while (i3 < cArr.length) {
            char c4 = cArr[i3];
            char c5 = c4;
            if (c4 >= 128) {
                if (c4 < 2048) {
                    i2 = (c4 >> 6) | 192;
                } else if (c4 < 55296 || c4 > 57343) {
                    byteArrayOutputStream.write((c4 >> '\f') | 224);
                    i2 = ((c4 >> 6) & 63) | 128;
                } else {
                    i3++;
                    if (i3 >= cArr.length) {
                        throw new IllegalStateException("invalid UTF-16 codepoint");
                    }
                    char c6 = cArr[i3];
                    if (c4 > 56319) {
                        throw new IllegalStateException("invalid UTF-16 codepoint");
                    }
                    ?? r2 = (((c4 & 1023) << 10) | (c6 & 1023)) + 65536;
                    byteArrayOutputStream.write((r2 >> 18) | 240);
                    byteArrayOutputStream.write(((r2 >> 12) & 63) | 128);
                    byteArrayOutputStream.write(((r2 >> 6) & 63) | 128);
                    c3 = r2;
                    c5 = (c3 & '?') | 128;
                }
                byteArrayOutputStream.write(i2);
                c3 = c4;
                c5 = (c3 & '?') | 128;
            }
            byteArrayOutputStream.write(c5);
            i3++;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String toUpperCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c3 = charArray[i2];
            if ('a' <= c3 && 'z' >= c3) {
                charArray[i2] = (char) ((c3 - 'a') + 65);
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }
}
