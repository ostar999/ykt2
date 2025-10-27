package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;

/* loaded from: classes9.dex */
public final class ZipEightByteInteger implements Serializable {
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 16711680;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 4278190080L;
    private static final int BYTE_3_SHIFT = 24;
    private static final int BYTE_4 = 4;
    private static final long BYTE_4_MASK = 1095216660480L;
    private static final int BYTE_4_SHIFT = 32;
    private static final int BYTE_5 = 5;
    private static final long BYTE_5_MASK = 280375465082880L;
    private static final int BYTE_5_SHIFT = 40;
    private static final int BYTE_6 = 6;
    private static final long BYTE_6_MASK = 71776119061217280L;
    private static final int BYTE_6_SHIFT = 48;
    private static final int BYTE_7 = 7;
    private static final long BYTE_7_MASK = 9151314442816847872L;
    private static final int BYTE_7_SHIFT = 56;
    private static final byte LEFTMOST_BIT = -128;
    private static final int LEFTMOST_BIT_SHIFT = 63;
    public static final ZipEightByteInteger ZERO = new ZipEightByteInteger(0);
    private static final long serialVersionUID = 1;
    private final BigInteger value;

    public ZipEightByteInteger(long j2) {
        this(BigInteger.valueOf(j2));
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ZipEightByteInteger)) {
            return false;
        }
        return this.value.equals(((ZipEightByteInteger) obj).getValue());
    }

    public byte[] getBytes() {
        return getBytes(this.value);
    }

    public long getLongValue() {
        return this.value.longValue();
    }

    public BigInteger getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public String toString() {
        return "ZipEightByteInteger value: " + this.value;
    }

    public ZipEightByteInteger(BigInteger bigInteger) {
        this.value = bigInteger;
    }

    public static byte[] getBytes(long j2) {
        return getBytes(BigInteger.valueOf(j2));
    }

    public static long getLongValue(byte[] bArr, int i2) {
        return getValue(bArr, i2).longValue();
    }

    public static BigInteger getValue(byte[] bArr, int i2) {
        int i3 = i2 + 7;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(((bArr[i3] << 56) & BYTE_7_MASK) + ((bArr[i2 + 6] << 48) & BYTE_6_MASK) + ((bArr[i2 + 5] << 40) & BYTE_5_MASK) + ((bArr[i2 + 4] << 32) & BYTE_4_MASK) + ((bArr[i2 + 3] << 24) & BYTE_3_MASK) + ((bArr[i2 + 2] << 16) & 16711680) + ((bArr[i2 + 1] << 8) & 65280) + (bArr[i2] & 255));
        return (bArr[i3] & (-128)) == -128 ? bigIntegerValueOf.setBit(63) : bigIntegerValueOf;
    }

    public static byte[] getBytes(BigInteger bigInteger) {
        long jLongValue = bigInteger.longValue();
        byte[] bArr = {(byte) (255 & jLongValue), (byte) ((65280 & jLongValue) >> 8), (byte) ((16711680 & jLongValue) >> 16), (byte) ((BYTE_3_MASK & jLongValue) >> 24), (byte) ((BYTE_4_MASK & jLongValue) >> 32), (byte) ((BYTE_5_MASK & jLongValue) >> 40), (byte) ((BYTE_6_MASK & jLongValue) >> 48), (byte) ((jLongValue & BYTE_7_MASK) >> 56)};
        if (bigInteger.testBit(63)) {
            bArr[7] = (byte) (bArr[7] | (-128));
        }
        return bArr;
    }

    public static long getLongValue(byte[] bArr) {
        return getLongValue(bArr, 0);
    }

    public ZipEightByteInteger(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipEightByteInteger(byte[] bArr, int i2) {
        this.value = getValue(bArr, i2);
    }

    public static BigInteger getValue(byte[] bArr) {
        return getValue(bArr, 0);
    }
}
