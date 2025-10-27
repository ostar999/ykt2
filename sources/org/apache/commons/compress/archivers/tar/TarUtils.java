package org.apache.commons.compress.archivers.tar;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

/* loaded from: classes9.dex */
public class TarUtils {
    private static final int BYTE_MASK = 255;
    static final ZipEncoding DEFAULT_ENCODING = ZipEncodingHelper.getZipEncoding(null);
    static final ZipEncoding FALLBACK_ENCODING = new ZipEncoding() { // from class: org.apache.commons.compress.archivers.tar.TarUtils.1
        @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
        public boolean canEncode(String str) {
            return true;
        }

        @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
        public String decode(byte[] bArr) {
            StringBuilder sb = new StringBuilder(bArr.length);
            for (byte b3 : bArr) {
                if (b3 == 0) {
                    break;
                }
                sb.append((char) (b3 & 255));
            }
            return sb.toString();
        }

        @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
        public ByteBuffer encode(String str) {
            int length = str.length();
            byte[] bArr = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                bArr[i2] = (byte) str.charAt(i2);
            }
            return ByteBuffer.wrap(bArr);
        }
    };

    private TarUtils() {
    }

    public static long computeCheckSum(byte[] bArr) {
        long j2 = 0;
        for (byte b3 : bArr) {
            j2 += b3 & 255;
        }
        return j2;
    }

    private static String exceptionMessage(byte[] bArr, int i2, int i3, int i4, byte b3) {
        return "Invalid byte " + ((int) b3) + " at offset " + (i4 - i2) + " in '" + new String(bArr, i2, i3).replaceAll("\u0000", "{NUL}") + "' len=" + i3;
    }

    private static void formatBigIntegerBinary(long j2, byte[] bArr, int i2, int i3, boolean z2) {
        byte[] byteArray = BigInteger.valueOf(j2).toByteArray();
        int length = byteArray.length;
        int i4 = (i3 + i2) - length;
        System.arraycopy(byteArray, 0, bArr, i4, length);
        byte b3 = (byte) (z2 ? 255 : 0);
        while (true) {
            i2++;
            if (i2 >= i4) {
                return;
            } else {
                bArr[i2] = b3;
            }
        }
    }

    public static int formatCheckSumOctalBytes(long j2, byte[] bArr, int i2, int i3) {
        int i4 = i3 - 2;
        formatUnsignedOctalString(j2, bArr, i2, i4);
        bArr[i4 + i2] = 0;
        bArr[i4 + 1 + i2] = 32;
        return i2 + i3;
    }

    private static void formatLongBinary(long j2, byte[] bArr, int i2, int i3, boolean z2) {
        long j3 = 1 << ((i3 - 1) * 8);
        long jAbs = Math.abs(j2);
        if (jAbs >= j3) {
            throw new IllegalArgumentException("Value " + j2 + " is too large for " + i3 + " byte field.");
        }
        if (z2) {
            jAbs = ((jAbs ^ (j3 - 1)) | (255 << r0)) + 1;
        }
        for (int i4 = (i3 + i2) - 1; i4 >= i2; i4--) {
            bArr[i4] = (byte) jAbs;
            jAbs >>= 8;
        }
    }

    public static int formatLongOctalBytes(long j2, byte[] bArr, int i2, int i3) {
        int i4 = i3 - 1;
        formatUnsignedOctalString(j2, bArr, i2, i4);
        bArr[i4 + i2] = 32;
        return i2 + i3;
    }

    public static int formatLongOctalOrBinaryBytes(long j2, byte[] bArr, int i2, int i3) {
        long j3 = i3 == 8 ? TarConstants.MAXID : TarConstants.MAXSIZE;
        boolean z2 = j2 < 0;
        if (!z2 && j2 <= j3) {
            return formatLongOctalBytes(j2, bArr, i2, i3);
        }
        if (i3 < 9) {
            formatLongBinary(j2, bArr, i2, i3, z2);
        }
        formatBigIntegerBinary(j2, bArr, i2, i3, z2);
        bArr[i2] = (byte) (z2 ? 255 : 128);
        return i2 + i3;
    }

    public static int formatNameBytes(String str, byte[] bArr, int i2, int i3) {
        try {
            try {
                return formatNameBytes(str, bArr, i2, i3, DEFAULT_ENCODING);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (IOException unused) {
            return formatNameBytes(str, bArr, i2, i3, FALLBACK_ENCODING);
        }
    }

    public static int formatOctalBytes(long j2, byte[] bArr, int i2, int i3) {
        int i4 = i3 - 2;
        formatUnsignedOctalString(j2, bArr, i2, i4);
        bArr[i4 + i2] = 32;
        bArr[i4 + 1 + i2] = 0;
        return i2 + i3;
    }

    public static void formatUnsignedOctalString(long j2, byte[] bArr, int i2, int i3) {
        int i4;
        int i5 = i3 - 1;
        if (j2 == 0) {
            i4 = i5 - 1;
            bArr[i5 + i2] = TarConstants.LF_NORMAL;
        } else {
            long j3 = j2;
            while (i5 >= 0 && j3 != 0) {
                bArr[i2 + i5] = (byte) (((byte) (7 & j3)) + TarConstants.LF_NORMAL);
                j3 >>>= 3;
                i5--;
            }
            if (j3 != 0) {
                throw new IllegalArgumentException(j2 + "=" + Long.toOctalString(j2) + " will not fit in octal number buffer of length " + i3);
            }
            i4 = i5;
        }
        while (i4 >= 0) {
            bArr[i2 + i4] = TarConstants.LF_NORMAL;
            i4--;
        }
    }

    private static long parseBinaryBigInteger(byte[] bArr, int i2, int i3, boolean z2) {
        int i4 = i3 - 1;
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, i2 + 1, bArr2, 0, i4);
        BigInteger bigInteger = new BigInteger(bArr2);
        if (z2) {
            bigInteger = bigInteger.add(BigInteger.valueOf(-1L)).not();
        }
        if (bigInteger.bitLength() <= 63) {
            long jLongValue = bigInteger.longValue();
            return z2 ? -jLongValue : jLongValue;
        }
        throw new IllegalArgumentException("At offset " + i2 + ", " + i3 + " byte binary number exceeds maximum signed long value");
    }

    private static long parseBinaryLong(byte[] bArr, int i2, int i3, boolean z2) {
        if (i3 < 9) {
            long jPow = 0;
            for (int i4 = 1; i4 < i3; i4++) {
                jPow = (jPow << 8) + (bArr[i2 + i4] & 255);
            }
            if (z2) {
                jPow = (jPow - 1) ^ (((long) Math.pow(2.0d, (i3 - 1) * 8)) - 1);
            }
            return z2 ? -jPow : jPow;
        }
        throw new IllegalArgumentException("At offset " + i2 + ", " + i3 + " byte binary number exceeds maximum signed long value");
    }

    public static boolean parseBoolean(byte[] bArr, int i2) {
        return bArr[i2] == 1;
    }

    public static String parseName(byte[] bArr, int i2, int i3) {
        try {
            try {
                return parseName(bArr, i2, i3, DEFAULT_ENCODING);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (IOException unused) {
            return parseName(bArr, i2, i3, FALLBACK_ENCODING);
        }
    }

    public static long parseOctal(byte[] bArr, int i2, int i3) {
        int i4 = i2 + i3;
        if (i3 < 2) {
            throw new IllegalArgumentException("Length " + i3 + " must be at least 2");
        }
        long j2 = 0;
        if (bArr[i2] == 0) {
            return 0L;
        }
        int i5 = i2;
        while (i5 < i4 && bArr[i5] == 32) {
            i5++;
        }
        byte b3 = bArr[i4 - 1];
        while (i5 < i4 && (b3 == 0 || b3 == 32)) {
            i4--;
            b3 = bArr[i4 - 1];
        }
        while (i5 < i4) {
            byte b4 = bArr[i5];
            if (b4 < 48 || b4 > 55) {
                throw new IllegalArgumentException(exceptionMessage(bArr, i2, i3, i5, b4));
            }
            j2 = (j2 << 3) + (b4 - 48);
            i5++;
        }
        return j2;
    }

    public static long parseOctalOrBinary(byte[] bArr, int i2, int i3) {
        byte b3 = bArr[i2];
        if ((b3 & 128) == 0) {
            return parseOctal(bArr, i2, i3);
        }
        boolean z2 = b3 == -1;
        return i3 < 9 ? parseBinaryLong(bArr, i2, i3, z2) : parseBinaryBigInteger(bArr, i2, i3, z2);
    }

    public static boolean verifyCheckSum(byte[] bArr) {
        long octal = parseOctal(bArr, 148, 8);
        long j2 = 0;
        long j3 = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            if (148 <= i2 && i2 < 156) {
                b3 = 32;
            }
            j2 += b3 & 255;
            j3 += b3;
        }
        return octal == j2 || octal == j3;
    }

    public static int formatNameBytes(String str, byte[] bArr, int i2, int i3, ZipEncoding zipEncoding) throws IOException {
        int length = str.length();
        ByteBuffer byteBufferEncode = zipEncoding.encode(str);
        while (byteBufferEncode.limit() > i3 && length > 0) {
            length--;
            byteBufferEncode = zipEncoding.encode(str.substring(0, length));
        }
        int iLimit = byteBufferEncode.limit() - byteBufferEncode.position();
        System.arraycopy(byteBufferEncode.array(), byteBufferEncode.arrayOffset(), bArr, i2, iLimit);
        while (iLimit < i3) {
            bArr[i2 + iLimit] = 0;
            iLimit++;
        }
        return i2 + i3;
    }

    public static String parseName(byte[] bArr, int i2, int i3, ZipEncoding zipEncoding) throws IOException {
        while (i3 > 0 && bArr[(i2 + i3) - 1] == 0) {
            i3--;
        }
        if (i3 <= 0) {
            return "";
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return zipEncoding.decode(bArr2);
    }
}
