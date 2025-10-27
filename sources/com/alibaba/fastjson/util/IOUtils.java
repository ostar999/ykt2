package com.alibaba.fastjson.util;

import cn.hutool.core.text.CharPool;
import com.alibaba.fastjson.JSONException;
import com.google.common.base.Ascii;
import com.yikaobang.yixue.R2;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import okio.Utf8;

/* loaded from: classes2.dex */
public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final String FASTJSON_COMPATIBLEWITHFIELDNAME = "fastjson.compatibleWithFieldName";
    public static final String FASTJSON_COMPATIBLEWITHJAVABEAN = "fastjson.compatibleWithJavaBean";
    public static final String FASTJSON_PROPERTIES = "fastjson.properties";
    public static final int[] IA;
    static final char[] digits;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];

    static {
        char c3 = 0;
        while (true) {
            boolean[] zArr = firstIdentifierFlags;
            if (c3 >= zArr.length) {
                break;
            }
            if (c3 >= 'A' && c3 <= 'Z') {
                zArr[c3] = true;
            } else if (c3 >= 'a' && c3 <= 'z') {
                zArr[c3] = true;
            } else if (c3 == '_' || c3 == '$') {
                zArr[c3] = true;
            }
            c3 = (char) (c3 + 1);
        }
        char c4 = 0;
        while (true) {
            boolean[] zArr2 = identifierFlags;
            if (c4 < zArr2.length) {
                if (c4 >= 'A' && c4 <= 'Z') {
                    zArr2[c4] = true;
                } else if (c4 >= 'a' && c4 <= 'z') {
                    zArr2[c4] = true;
                } else if (c4 == '_') {
                    zArr2[c4] = true;
                } else if (c4 >= '0' && c4 <= '9') {
                    zArr2[c4] = true;
                }
                c4 = (char) (c4 + 1);
            } else {
                try {
                    break;
                } catch (Throwable unused) {
                }
            }
        }
        loadPropertiesFromFile();
        byte[] bArr = new byte[161];
        specicalFlags_doubleQuotes = bArr;
        byte[] bArr2 = new byte[161];
        specicalFlags_singleQuotes = bArr2;
        specicalFlags_doubleQuotesFlags = new boolean[161];
        specicalFlags_singleQuotesFlags = new boolean[161];
        replaceChars = new char[93];
        bArr[0] = 4;
        bArr[1] = 4;
        bArr[2] = 4;
        bArr[3] = 4;
        bArr[4] = 4;
        bArr[5] = 4;
        bArr[6] = 4;
        bArr[7] = 4;
        bArr[8] = 1;
        bArr[9] = 1;
        bArr[10] = 1;
        bArr[11] = 4;
        bArr[12] = 1;
        bArr[13] = 1;
        bArr[34] = 1;
        bArr[92] = 1;
        bArr2[0] = 4;
        bArr2[1] = 4;
        bArr2[2] = 4;
        bArr2[3] = 4;
        bArr2[4] = 4;
        bArr2[5] = 4;
        bArr2[6] = 4;
        bArr2[7] = 4;
        bArr2[8] = 1;
        bArr2[9] = 1;
        bArr2[10] = 1;
        bArr2[11] = 4;
        bArr2[12] = 1;
        bArr2[13] = 1;
        bArr2[92] = 1;
        bArr2[39] = 1;
        for (int i2 = 14; i2 <= 31; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 127; i3 < 160; i3++) {
            specicalFlags_doubleQuotes[i3] = 4;
            specicalFlags_singleQuotes[i3] = 4;
        }
        for (int i4 = 0; i4 < 161; i4++) {
            specicalFlags_doubleQuotesFlags[i4] = specicalFlags_doubleQuotes[i4] != 0;
            specicalFlags_singleQuotesFlags[i4] = specicalFlags_singleQuotes[i4] != 0;
        }
        char[] cArr = replaceChars;
        cArr[0] = '0';
        cArr[1] = '1';
        cArr[2] = '2';
        cArr[3] = '3';
        cArr[4] = '4';
        cArr[5] = '5';
        cArr[6] = '6';
        cArr[7] = '7';
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[11] = 'v';
        cArr[12] = 'f';
        cArr[13] = 'r';
        cArr[34] = '\"';
        cArr[39] = CharPool.SINGLE_QUOTE;
        cArr[47] = '/';
        cArr[92] = '\\';
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, R2.drawable.homepage_shangcheng_press, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int length = charArray.length;
        for (int i5 = 0; i5 < length; i5++) {
            IA[CA[i5]] = i5;
        }
        IA[61] = 0;
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) throws CharacterCodingException {
        try {
            CoderResult coderResultDecode = charsetDecoder.decode(byteBuffer, charBuffer, true);
            if (!coderResultDecode.isUnderflow()) {
                coderResultDecode.throwException();
            }
            CoderResult coderResultFlush = charsetDecoder.flush(charBuffer);
            if (coderResultFlush.isUnderflow()) {
                return;
            }
            coderResultFlush.throwException();
        } catch (CharacterCodingException e2) {
            throw new JSONException("utf8 decode error, " + e2.getMessage(), e2);
        }
    }

    public static byte[] decodeBase64(char[] cArr, int i2, int i3) {
        int i4;
        int i5 = 0;
        if (i3 == 0) {
            return new byte[0];
        }
        int i6 = (i2 + i3) - 1;
        while (i2 < i6 && IA[cArr[i2]] < 0) {
            i2++;
        }
        while (i6 > 0 && IA[cArr[i6]] < 0) {
            i6--;
        }
        int i7 = cArr[i6] == '=' ? cArr[i6 + (-1)] == '=' ? 2 : 1 : 0;
        int i8 = (i6 - i2) + 1;
        if (i3 > 76) {
            i4 = (cArr[76] == '\r' ? i8 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i9 = (((i8 - i4) * 6) >> 3) - i7;
        byte[] bArr = new byte[i9];
        int i10 = (i9 / 3) * 3;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i10) {
            int[] iArr = IA;
            int i13 = i2 + 1;
            int i14 = i13 + 1;
            int i15 = (iArr[cArr[i2]] << 18) | (iArr[cArr[i13]] << 12);
            int i16 = i14 + 1;
            int i17 = i15 | (iArr[cArr[i14]] << 6);
            int i18 = i16 + 1;
            int i19 = i17 | iArr[cArr[i16]];
            int i20 = i11 + 1;
            bArr[i11] = (byte) (i19 >> 16);
            int i21 = i20 + 1;
            bArr[i20] = (byte) (i19 >> 8);
            int i22 = i21 + 1;
            bArr[i21] = (byte) i19;
            if (i4 > 0 && (i12 = i12 + 1) == 19) {
                i18 += 2;
                i12 = 0;
            }
            i2 = i18;
            i11 = i22;
        }
        if (i11 < i9) {
            int i23 = 0;
            while (i2 <= i6 - i7) {
                i5 |= IA[cArr[i2]] << (18 - (i23 * 6));
                i23++;
                i2++;
            }
            int i24 = 16;
            while (i11 < i9) {
                bArr[i11] = (byte) (i5 >> i24);
                i24 -= 8;
                i11++;
            }
        }
        return bArr;
    }

    public static int decodeUTF8(byte[] bArr, int i2, int i3, char[] cArr) {
        int i4 = i2 + i3;
        int iMin = Math.min(i3, cArr.length);
        int i5 = 0;
        while (i5 < iMin) {
            byte b3 = bArr[i2];
            if (b3 < 0) {
                break;
            }
            i2++;
            cArr[i5] = (char) b3;
            i5++;
        }
        while (i2 < i4) {
            int i6 = i2 + 1;
            byte b4 = bArr[i2];
            if (b4 >= 0) {
                cArr[i5] = (char) b4;
                i2 = i6;
                i5++;
            } else {
                if ((b4 >> 5) != -2 || (b4 & Ascii.RS) == 0) {
                    if ((b4 >> 4) == -2) {
                        int i7 = i6 + 1;
                        if (i7 < i4) {
                            byte b5 = bArr[i6];
                            int i8 = i7 + 1;
                            byte b6 = bArr[i7];
                            if ((b4 != -32 || (b5 & 224) != 128) && (b5 & 192) == 128 && (b6 & 192) == 128) {
                                char c3 = (char) (((b4 << 12) ^ (b5 << 6)) ^ ((-123008) ^ b6));
                                if (c3 >= 55296 && c3 < 57344) {
                                    return -1;
                                }
                                cArr[i5] = c3;
                                i5++;
                                i2 = i8;
                            }
                        }
                        return -1;
                    }
                    if ((b4 >> 3) == -2 && i6 + 2 < i4) {
                        int i9 = i6 + 1;
                        byte b7 = bArr[i6];
                        int i10 = i9 + 1;
                        byte b8 = bArr[i9];
                        int i11 = i10 + 1;
                        byte b9 = bArr[i10];
                        int i12 = (((b4 << Ascii.DC2) ^ (b7 << 12)) ^ (b8 << 6)) ^ (3678080 ^ b9);
                        if ((b7 & 192) == 128 && (b8 & 192) == 128 && (b9 & 192) == 128 && i12 >= 65536 && i12 < 1114112) {
                            int i13 = i5 + 1;
                            cArr[i5] = (char) ((i12 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                            i5 = i13 + 1;
                            cArr[i13] = (char) ((i12 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                            i2 = i11;
                        }
                    }
                    return -1;
                }
                if (i6 >= i4) {
                    return -1;
                }
                int i14 = i6 + 1;
                byte b10 = bArr[i6];
                if ((b10 & 192) != 128) {
                    return -1;
                }
                cArr[i5] = (char) (((b4 << 6) ^ b10) ^ 3968);
                i2 = i14;
                i5++;
            }
        }
        return i5;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0083  */
    /* JADX WARN: Type inference failed for: r10v16, types: [int] */
    /* JADX WARN: Type inference failed for: r10v25, types: [int] */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r10v3, types: [char, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encodeUTF8(char[] r9, int r10, int r11, byte[] r12) {
        /*
            Method dump skipped, instructions count: 199
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.IOUtils.encodeUTF8(char[], int, int, byte[]):int");
    }

    public static boolean firstIdentifier(char c3) {
        boolean[] zArr = firstIdentifierFlags;
        return c3 < zArr.length && zArr[c3];
    }

    public static void getChars(long j2, int i2, char[] cArr) {
        char c3;
        if (j2 < 0) {
            j2 = -j2;
            c3 = CharPool.DASHED;
        } else {
            c3 = 0;
        }
        while (j2 > 2147483647L) {
            long j3 = j2 / 100;
            int i3 = (int) (j2 - (((j3 << 6) + (j3 << 5)) + (j3 << 2)));
            int i4 = i2 - 1;
            cArr[i4] = DigitOnes[i3];
            i2 = i4 - 1;
            cArr[i2] = DigitTens[i3];
            j2 = j3;
        }
        int i5 = (int) j2;
        while (i5 >= 65536) {
            int i6 = i5 / 100;
            int i7 = i5 - (((i6 << 6) + (i6 << 5)) + (i6 << 2));
            int i8 = i2 - 1;
            cArr[i8] = DigitOnes[i7];
            i2 = i8 - 1;
            cArr[i2] = DigitTens[i7];
            i5 = i6;
        }
        while (true) {
            int i9 = (52429 * i5) >>> 19;
            i2--;
            cArr[i2] = digits[i5 - ((i9 << 3) + (i9 << 1))];
            if (i9 == 0) {
                break;
            } else {
                i5 = i9;
            }
        }
        if (c3 != 0) {
            cArr[i2 - 1] = c3;
        }
    }

    public static String getStringProperty(String str) {
        String property;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        return property == null ? DEFAULT_PROPERTIES.getProperty(str) : property;
    }

    public static boolean isIdent(char c3) {
        boolean[] zArr = identifierFlags;
        return c3 < zArr.length && zArr[c3];
    }

    public static boolean isValidJsonpQueryParam(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != '.' && !isIdent(cCharAt)) {
                return false;
            }
        }
        return true;
    }

    public static void loadPropertiesFromFile() throws IOException {
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.alibaba.fastjson.util.IOUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                return contextClassLoader != null ? contextClassLoader.getResourceAsStream(IOUtils.FASTJSON_PROPERTIES) : ClassLoader.getSystemResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
            }
        });
        if (inputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(inputStream);
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static String readAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            char[] cArr = new char[2048];
            while (true) {
                int i2 = reader.read(cArr, 0, 2048);
                if (i2 < 0) {
                    return sb.toString();
                }
                sb.append(cArr, 0, i2);
            }
        } catch (Exception e2) {
            throw new JSONException("read string from reader error", e2);
        }
    }

    public static int stringSize(int i2) {
        int i3 = 0;
        while (i2 > sizeTable[i3]) {
            i3++;
        }
        return i3 + 1;
    }

    public static int stringSize(long j2) {
        long j3 = 10;
        for (int i2 = 1; i2 < 19; i2++) {
            if (j2 < j3) {
                return i2;
            }
            j3 *= 10;
        }
        return 19;
    }

    public static void getChars(int i2, int i3, char[] cArr) {
        char c3;
        if (i2 < 0) {
            i2 = -i2;
            c3 = CharPool.DASHED;
        } else {
            c3 = 0;
        }
        while (i2 >= 65536) {
            int i4 = i2 / 100;
            int i5 = i2 - (((i4 << 6) + (i4 << 5)) + (i4 << 2));
            int i6 = i3 - 1;
            cArr[i6] = DigitOnes[i5];
            i3 = i6 - 1;
            cArr[i3] = DigitTens[i5];
            i2 = i4;
        }
        while (true) {
            int i7 = (52429 * i2) >>> 19;
            i3--;
            cArr[i3] = digits[i2 - ((i7 << 3) + (i7 << 1))];
            if (i7 == 0) {
                break;
            } else {
                i2 = i7;
            }
        }
        if (c3 != 0) {
            cArr[i3 - 1] = c3;
        }
    }

    public static byte[] decodeBase64(String str, int i2, int i3) {
        int i4;
        int i5 = 0;
        if (i3 == 0) {
            return new byte[0];
        }
        int i6 = (i2 + i3) - 1;
        while (i2 < i6 && IA[str.charAt(i2)] < 0) {
            i2++;
        }
        while (i6 > 0 && IA[str.charAt(i6)] < 0) {
            i6--;
        }
        int i7 = str.charAt(i6) == '=' ? str.charAt(i6 + (-1)) == '=' ? 2 : 1 : 0;
        int i8 = (i6 - i2) + 1;
        if (i3 > 76) {
            i4 = (str.charAt(76) == '\r' ? i8 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i9 = (((i8 - i4) * 6) >> 3) - i7;
        byte[] bArr = new byte[i9];
        int i10 = (i9 / 3) * 3;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i10) {
            int[] iArr = IA;
            int i13 = i2 + 1;
            int i14 = i13 + 1;
            int i15 = (iArr[str.charAt(i2)] << 18) | (iArr[str.charAt(i13)] << 12);
            int i16 = i14 + 1;
            int i17 = i15 | (iArr[str.charAt(i14)] << 6);
            int i18 = i16 + 1;
            int i19 = i17 | iArr[str.charAt(i16)];
            int i20 = i11 + 1;
            bArr[i11] = (byte) (i19 >> 16);
            int i21 = i20 + 1;
            bArr[i20] = (byte) (i19 >> 8);
            int i22 = i21 + 1;
            bArr[i21] = (byte) i19;
            if (i4 > 0 && (i12 = i12 + 1) == 19) {
                i18 += 2;
                i12 = 0;
            }
            i2 = i18;
            i11 = i22;
        }
        if (i11 < i9) {
            int i23 = 0;
            while (i2 <= i6 - i7) {
                i5 |= IA[str.charAt(i2)] << (18 - (i23 * 6));
                i23++;
                i2++;
            }
            int i24 = 16;
            while (i11 < i9) {
                bArr[i11] = (byte) (i5 >> i24);
                i24 -= 8;
                i11++;
            }
        }
        return bArr;
    }

    public static void getChars(byte b3, int i2, char[] cArr) {
        char c3;
        int i3;
        if (b3 < 0) {
            c3 = CharPool.DASHED;
            i3 = -b3;
        } else {
            c3 = 0;
            i3 = b3;
        }
        while (true) {
            int i4 = (52429 * i3) >>> 19;
            i2--;
            cArr[i2] = digits[i3 - ((i4 << 3) + (i4 << 1))];
            if (i4 == 0) {
                break;
            } else {
                i3 = i4;
            }
        }
        if (c3 != 0) {
            cArr[i2 - 1] = c3;
        }
    }

    public static byte[] decodeBase64(String str) {
        int i2;
        int length = str.length();
        int i3 = 0;
        if (length == 0) {
            return new byte[0];
        }
        int i4 = length - 1;
        int i5 = 0;
        while (i5 < i4 && IA[str.charAt(i5) & 255] < 0) {
            i5++;
        }
        while (i4 > 0 && IA[str.charAt(i4) & 255] < 0) {
            i4--;
        }
        int i6 = str.charAt(i4) == '=' ? str.charAt(i4 + (-1)) == '=' ? 2 : 1 : 0;
        int i7 = (i4 - i5) + 1;
        if (length > 76) {
            i2 = (str.charAt(76) == '\r' ? i7 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i8 = (((i7 - i2) * 6) >> 3) - i6;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = 0;
        int i11 = 0;
        while (i10 < i9) {
            int[] iArr = IA;
            int i12 = i5 + 1;
            int i13 = i12 + 1;
            int i14 = (iArr[str.charAt(i5)] << 18) | (iArr[str.charAt(i12)] << 12);
            int i15 = i13 + 1;
            int i16 = i14 | (iArr[str.charAt(i13)] << 6);
            int i17 = i15 + 1;
            int i18 = i16 | iArr[str.charAt(i15)];
            int i19 = i10 + 1;
            bArr[i10] = (byte) (i18 >> 16);
            int i20 = i19 + 1;
            bArr[i19] = (byte) (i18 >> 8);
            int i21 = i20 + 1;
            bArr[i20] = (byte) i18;
            if (i2 > 0 && (i11 = i11 + 1) == 19) {
                i17 += 2;
                i11 = 0;
            }
            i5 = i17;
            i10 = i21;
        }
        if (i10 < i8) {
            int i22 = 0;
            while (i5 <= i4 - i6) {
                i3 |= IA[str.charAt(i5)] << (18 - (i22 * 6));
                i22++;
                i5++;
            }
            int i23 = 16;
            while (i10 < i8) {
                bArr[i10] = (byte) (i3 >> i23);
                i23 -= 8;
                i10++;
            }
        }
        return bArr;
    }
}
