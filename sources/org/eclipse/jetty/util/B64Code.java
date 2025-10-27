package org.eclipse.jetty.util;

import com.yikaobang.yixue.R2;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import okio.Utf8;

/* loaded from: classes9.dex */
public class B64Code {
    static final char __pad = '=';
    static final char[] __rfc1421alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    static final byte[] __rfc1421nibbles = new byte[256];

    static {
        for (int i2 = 0; i2 < 256; i2++) {
            __rfc1421nibbles[i2] = -1;
        }
        for (byte b3 = 0; b3 < 64; b3 = (byte) (b3 + 1)) {
            __rfc1421nibbles[(byte) __rfc1421alphabet[b3]] = b3;
        }
        __rfc1421nibbles[61] = 0;
    }

    public static String decode(String str, String str2) throws UnsupportedEncodingException {
        byte[] bArrDecode = decode(str);
        return str2 == null ? new String(bArrDecode) : new String(bArrDecode, str2);
    }

    public static String encode(String str) {
        try {
            return encode(str, (String) null);
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    public static String encode(String str, String str2) throws UnsupportedEncodingException {
        byte[] bytes;
        if (str2 == null) {
            bytes = str.getBytes("ISO-8859-1");
        } else {
            bytes = str.getBytes(str2);
        }
        return new String(encode(bytes));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r11v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /* JADX WARN: Type inference failed for: r1v8, types: [int] */
    /* JADX WARN: Type inference failed for: r8v1, types: [int] */
    /* JADX WARN: Type inference failed for: r9v0, types: [int] */
    public static byte[] decode(char[] cArr) {
        byte b3;
        ?? r11;
        byte b4;
        if (cArr == null) {
            return null;
        }
        int length = cArr.length;
        if (length % 4 == 0) {
            byte b5 = 1;
            int i2 = length - 1;
            while (i2 >= 0 && cArr[i2] == '=') {
                i2--;
            }
            int i3 = 0;
            if (i2 < 0) {
                return new byte[0];
            }
            int i4 = ((i2 + 1) * 3) / 4;
            byte[] bArr = new byte[i4];
            int i5 = (i4 / 3) * 3;
            byte b6 = 0;
            while (i3 < i5) {
                try {
                    byte[] bArr2 = __rfc1421nibbles;
                    ?? r8 = b6 + 1;
                    try {
                        byte b7 = bArr2[cArr[b6]];
                        ?? r9 = r8 + 1;
                        try {
                            byte b8 = bArr2[cArr[r8]];
                            ?? r10 = r9 + 1;
                            try {
                                b3 = bArr2[cArr[r9]];
                                r11 = r10 + 1;
                            } catch (IndexOutOfBoundsException unused) {
                                b6 = r10;
                            }
                            try {
                                b4 = bArr2[cArr[r10]];
                                if (b7 >= 0 && b8 >= 0 && b3 >= 0 && b4 >= 0) {
                                    int i6 = i3 + 1;
                                    bArr[i3] = (byte) ((b7 << 2) | (b8 >>> 4));
                                    int i7 = i6 + 1;
                                    bArr[i6] = (byte) ((b8 << 4) | (b3 >>> 2));
                                    bArr[i7] = (byte) ((b3 << 6) | b4);
                                    i3 = i7 + 1;
                                    b6 = r11;
                                } else {
                                    throw new IllegalArgumentException("Not B64 encoded");
                                }
                            } catch (IndexOutOfBoundsException unused2) {
                                b6 = r11;
                                throw new IllegalArgumentException("char " + ((int) b6) + " was not B64 encoded");
                            }
                        } catch (IndexOutOfBoundsException unused3) {
                            b6 = r9;
                        }
                    } catch (IndexOutOfBoundsException unused4) {
                        b6 = r8;
                    }
                } catch (IndexOutOfBoundsException unused5) {
                }
            }
            if (i4 != i3) {
                int i8 = i4 % 3;
                try {
                    try {
                        if (i8 == 1) {
                            byte[] bArr3 = __rfc1421nibbles;
                            ?? r12 = b6 + 1;
                            byte b9 = bArr3[cArr[b6]];
                            int i9 = r12 + 1;
                            byte b10 = bArr3[cArr[r12]];
                            if (b9 >= 0 && b10 >= 0) {
                                bArr[i3] = (byte) ((b10 >>> 4) | (b9 << 2));
                                b5 = r12;
                            } else {
                                throw new IllegalArgumentException("Not B64 encoded");
                            }
                        } else if (i8 == 2) {
                            byte[] bArr4 = __rfc1421nibbles;
                            int i10 = b6 + 1;
                            byte b11 = bArr4[cArr[b6]];
                            int i11 = i10 + 1;
                            byte b12 = bArr4[cArr[i10]];
                            int i12 = i11 + 1;
                            byte b13 = bArr4[cArr[i11]];
                            if (b11 >= 0 && b12 >= 0 && b13 >= 0) {
                                bArr[i3] = (byte) ((b11 << 2) | (b12 >>> 4));
                                ?? r13 = b12 << 4;
                                bArr[i3 + 1] = (byte) ((b13 >>> 2) | r13);
                                b5 = r13;
                            } else {
                                throw new IllegalArgumentException("Not B64 encoded");
                            }
                        }
                    } catch (IndexOutOfBoundsException unused6) {
                        b6 = b4;
                        throw new IllegalArgumentException("char " + ((int) b6) + " was not B64 encoded");
                    }
                } catch (IndexOutOfBoundsException unused7) {
                    b6 = b5;
                    throw new IllegalArgumentException("char " + ((int) b6) + " was not B64 encoded");
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException("Input block size is not 4");
    }

    public static char[] encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        char[] cArr = new char[((length + 2) / 3) * 4];
        int i2 = (length / 3) * 3;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i3 + 1;
            byte b3 = bArr[i3];
            int i6 = i5 + 1;
            byte b4 = bArr[i5];
            int i7 = i6 + 1;
            byte b5 = bArr[i6];
            int i8 = i4 + 1;
            char[] cArr2 = __rfc1421alphabet;
            cArr[i4] = cArr2[(b3 >>> 2) & 63];
            int i9 = i8 + 1;
            cArr[i8] = cArr2[((b3 << 4) & 63) | ((b4 >>> 4) & 15)];
            int i10 = i9 + 1;
            cArr[i9] = cArr2[((b4 << 2) & 63) | ((b5 >>> 6) & 3)];
            i4 = i10 + 1;
            cArr[i10] = cArr2[b5 & Utf8.REPLACEMENT_BYTE];
            i3 = i7;
        }
        if (length != i3) {
            int i11 = length % 3;
            if (i11 == 1) {
                byte b6 = bArr[i3];
                int i12 = i4 + 1;
                char[] cArr3 = __rfc1421alphabet;
                cArr[i4] = cArr3[(b6 >>> 2) & 63];
                int i13 = i12 + 1;
                cArr[i12] = cArr3[(b6 << 4) & 63];
                cArr[i13] = __pad;
                cArr[i13 + 1] = __pad;
            } else if (i11 == 2) {
                int i14 = i3 + 1;
                byte b7 = bArr[i3];
                byte b8 = bArr[i14];
                int i15 = i4 + 1;
                char[] cArr4 = __rfc1421alphabet;
                cArr[i4] = cArr4[(b7 >>> 2) & 63];
                int i16 = i15 + 1;
                cArr[i15] = cArr4[((b7 << 4) & 63) | ((b8 >>> 4) & 15)];
                cArr[i16] = cArr4[(b8 << 2) & 63];
                cArr[i16 + 1] = __pad;
            }
        }
        return cArr;
    }

    public static char[] encode(byte[] bArr, boolean z2) {
        if (bArr == null) {
            return null;
        }
        if (!z2) {
            return encode(bArr);
        }
        int length = bArr.length;
        int i2 = ((length + 2) / 3) * 4;
        char[] cArr = new char[i2 + ((i2 / 76) * 2) + 2];
        int i3 = (length / 3) * 3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < i3) {
            int i7 = i4 + 1;
            byte b3 = bArr[i4];
            int i8 = i7 + 1;
            byte b4 = bArr[i7];
            int i9 = i8 + 1;
            byte b5 = bArr[i8];
            int i10 = i5 + 1;
            char[] cArr2 = __rfc1421alphabet;
            cArr[i5] = cArr2[(b3 >>> 2) & 63];
            int i11 = i10 + 1;
            cArr[i10] = cArr2[((b3 << 4) & 63) | ((b4 >>> 4) & 15)];
            int i12 = i11 + 1;
            cArr[i11] = cArr2[((b4 << 2) & 63) | ((b5 >>> 6) & 3)];
            i5 = i12 + 1;
            cArr[i12] = cArr2[b5 & Utf8.REPLACEMENT_BYTE];
            i6 += 4;
            if (i6 % 76 == 0) {
                int i13 = i5 + 1;
                cArr[i5] = '\r';
                i5 = i13 + 1;
                cArr[i13] = '\n';
            }
            i4 = i9;
        }
        if (length != i4) {
            int i14 = length % 3;
            if (i14 == 1) {
                byte b6 = bArr[i4];
                int i15 = i5 + 1;
                char[] cArr3 = __rfc1421alphabet;
                cArr[i5] = cArr3[(b6 >>> 2) & 63];
                int i16 = i15 + 1;
                cArr[i15] = cArr3[(b6 << 4) & 63];
                int i17 = i16 + 1;
                cArr[i16] = __pad;
                i5 = i17 + 1;
                cArr[i17] = __pad;
            } else if (i14 == 2) {
                int i18 = i4 + 1;
                byte b7 = bArr[i4];
                byte b8 = bArr[i18];
                int i19 = i5 + 1;
                char[] cArr4 = __rfc1421alphabet;
                cArr[i5] = cArr4[(b7 >>> 2) & 63];
                int i20 = i19 + 1;
                cArr[i19] = cArr4[((b7 << 4) & 63) | ((b8 >>> 4) & 15)];
                int i21 = i20 + 1;
                cArr[i20] = cArr4[(b8 << 2) & 63];
                i5 = i21 + 1;
                cArr[i21] = __pad;
            }
        }
        cArr[i5] = '\r';
        cArr[i5 + 1] = '\n';
        return cArr;
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((str.length() * 4) / 3);
        decode(str, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void decode(String str, ByteArrayOutputStream byteArrayOutputStream) {
        if (str == null) {
            return;
        }
        if (byteArrayOutputStream != null) {
            byte[] bArr = new byte[4];
            int i2 = 0;
            int i3 = 0;
            while (i2 < str.length()) {
                int i4 = i2 + 1;
                char cCharAt = str.charAt(i2);
                if (cCharAt == '=') {
                    return;
                }
                if (!Character.isWhitespace(cCharAt)) {
                    byte b3 = __rfc1421nibbles[cCharAt];
                    if (b3 >= 0) {
                        int i5 = i3 + 1;
                        bArr[i3] = b3;
                        if (i5 == 2) {
                            byteArrayOutputStream.write((bArr[1] >>> 4) | (bArr[0] << 2));
                        } else if (i5 == 3) {
                            byteArrayOutputStream.write((bArr[1] << 4) | (bArr[2] >>> 2));
                        } else if (i5 == 4) {
                            byteArrayOutputStream.write((bArr[2] << 6) | bArr[3]);
                            i3 = 0;
                        }
                        i3 = i5;
                    } else {
                        throw new IllegalArgumentException("Not B64 encoded");
                    }
                }
                i2 = i4;
            }
            return;
        }
        throw new IllegalArgumentException("No outputstream for decoded bytes");
    }

    public static void encode(int i2, Appendable appendable) throws IOException {
        char[] cArr = __rfc1421alphabet;
        appendable.append(cArr[(((-67108864) & i2) >> 26) & 63]);
        appendable.append(cArr[((66060288 & i2) >> 20) & 63]);
        appendable.append(cArr[((1032192 & i2) >> 14) & 63]);
        appendable.append(cArr[((i2 & R2.id.line_view) >> 8) & 63]);
        appendable.append(cArr[((i2 & R2.attr.actionModeShareDrawable) >> 2) & 63]);
        appendable.append(cArr[((i2 & 3) << 4) & 63]);
        appendable.append(__pad);
    }

    public static void encode(long j2, Appendable appendable) throws IOException {
        int i2 = (int) ((j2 >> 32) & (-4));
        char[] cArr = __rfc1421alphabet;
        appendable.append(cArr[(((-67108864) & i2) >> 26) & 63]);
        appendable.append(cArr[((66060288 & i2) >> 20) & 63]);
        appendable.append(cArr[((1032192 & i2) >> 14) & 63]);
        appendable.append(cArr[((i2 & R2.id.line_view) >> 8) & 63]);
        appendable.append(cArr[((i2 & R2.attr.actionModeShareDrawable) >> 2) & 63]);
        appendable.append(cArr[(((i2 & 3) << 4) + (((int) (j2 >> 28)) & 15)) & 63]);
        int i3 = ((int) j2) & 268435455;
        appendable.append(cArr[((264241152 & i3) >> 22) & 63]);
        appendable.append(cArr[((4128768 & i3) >> 16) & 63]);
        appendable.append(cArr[((64512 & i3) >> 10) & 63]);
        appendable.append(cArr[((i3 & 1008) >> 4) & 63]);
        appendable.append(cArr[((i3 & 15) << 2) & 63]);
    }
}
