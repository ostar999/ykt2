package net.lingala.zip4j.util;

import java.io.DataInput;
import java.io.IOException;
import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public class Raw {
    public static byte bitArrayToByte(int[] iArr) throws ZipException {
        if (iArr == null) {
            throw new ZipException("bit array is null, cannot calculate byte from bits");
        }
        if (iArr.length != 8) {
            throw new ZipException("invalid bit array length, cannot calculate byte");
        }
        if (!checkBits(iArr)) {
            throw new ZipException("invalid bits provided, bits contain other values than 0 or 1");
        }
        int iPow = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iPow = (int) (iPow + (Math.pow(2.0d, i2) * iArr[i2]));
        }
        return (byte) iPow;
    }

    private static boolean checkBits(int[] iArr) {
        for (int i2 : iArr) {
            if (i2 != 0 && i2 != 1) {
                return false;
            }
        }
        return true;
    }

    public static byte[] convertCharArrayToByteArray(char[] cArr) {
        cArr.getClass();
        byte[] bArr = new byte[cArr.length];
        for (int i2 = 0; i2 < cArr.length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static void prepareBuffAESIVBytes(byte[] bArr, int i2, int i3) {
        bArr[0] = (byte) i2;
        bArr[1] = (byte) (i2 >> 8);
        bArr[2] = (byte) (i2 >> 16);
        bArr[3] = (byte) (i2 >> 24);
        bArr[4] = 0;
        bArr[5] = 0;
        bArr[6] = 0;
        bArr[7] = 0;
        bArr[8] = 0;
        bArr[9] = 0;
        bArr[10] = 0;
        bArr[11] = 0;
        bArr[12] = 0;
        bArr[13] = 0;
        bArr[14] = 0;
        bArr[15] = 0;
    }

    public static int readIntLittleEndian(byte[] bArr, int i2) {
        return ((((bArr[i2 + 3] & 255) << 8) | (bArr[i2 + 2] & 255)) << 16) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8);
    }

    public static int readLeInt(DataInput dataInput, byte[] bArr) throws ZipException, IOException {
        try {
            dataInput.readFully(bArr, 0, 4);
            return (bArr[0] & 255) | ((bArr[1] & 255) << 8) | ((((bArr[3] & 255) << 8) | (bArr[2] & 255)) << 16);
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    public static long readLongLittleEndian(byte[] bArr, int i2) {
        return (bArr[i2] & 255) | (((((((((((((((bArr[i2 + 7] & 255) | 0) << 8) | (bArr[i2 + 6] & 255)) << 8) | (bArr[i2 + 5] & 255)) << 8) | (bArr[i2 + 4] & 255)) << 8) | (bArr[i2 + 3] & 255)) << 8) | (bArr[i2 + 2] & 255)) << 8) | (bArr[i2 + 1] & 255)) << 8);
    }

    public static final short readShortBigEndian(byte[] bArr, int i2) {
        return (short) ((bArr[i2 + 1] & 255) | ((short) (((short) ((bArr[i2] & 255) | 0)) << 8)));
    }

    public static int readShortLittleEndian(byte[] bArr, int i2) {
        return ((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255);
    }

    public static byte[] toByteArray(int i2) {
        return new byte[]{(byte) i2, (byte) (i2 >> 8), (byte) (i2 >> 16), (byte) (i2 >> 24)};
    }

    public static byte[] toByteArray(int i2, int i3) {
        byte[] bArr = new byte[i3];
        byte[] byteArray = toByteArray(i2);
        for (int i4 = 0; i4 < byteArray.length && i4 < i3; i4++) {
            bArr[i4] = byteArray[i4];
        }
        return bArr;
    }

    public static final void writeIntLittleEndian(byte[] bArr, int i2, int i3) {
        bArr[i2 + 3] = (byte) (i3 >>> 24);
        bArr[i2 + 2] = (byte) (i3 >>> 16);
        bArr[i2 + 1] = (byte) (i3 >>> 8);
        bArr[i2] = (byte) (i3 & 255);
    }

    public static void writeLongLittleEndian(byte[] bArr, int i2, long j2) {
        bArr[i2 + 7] = (byte) (j2 >>> 56);
        bArr[i2 + 6] = (byte) (j2 >>> 48);
        bArr[i2 + 5] = (byte) (j2 >>> 40);
        bArr[i2 + 4] = (byte) (j2 >>> 32);
        bArr[i2 + 3] = (byte) (j2 >>> 24);
        bArr[i2 + 2] = (byte) (j2 >>> 16);
        bArr[i2 + 1] = (byte) (j2 >>> 8);
        bArr[i2] = (byte) (j2 & 255);
    }

    public static final void writeShortLittleEndian(byte[] bArr, int i2, short s2) {
        bArr[i2 + 1] = (byte) (s2 >>> 8);
        bArr[i2] = (byte) (s2 & 255);
    }
}
