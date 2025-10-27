package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;

/* loaded from: classes9.dex */
public final class ZipShort implements Cloneable, Serializable {
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final long serialVersionUID = 1;
    private final int value;

    public ZipShort(int i2) {
        this.value = i2;
    }

    public static void putShort(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 & 255);
        bArr[i3 + 1] = (byte) ((i2 & 65280) >> 8);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ZipShort) && this.value == ((ZipShort) obj).getValue();
    }

    public byte[] getBytes() {
        int i2 = this.value;
        return new byte[]{(byte) (i2 & 255), (byte) ((i2 & 65280) >> 8)};
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        return "ZipShort value: " + this.value;
    }

    public static byte[] getBytes(int i2) {
        byte[] bArr = new byte[2];
        putShort(i2, bArr, 0);
        return bArr;
    }

    public static int getValue(byte[] bArr, int i2) {
        return ((bArr[i2 + 1] << 8) & 65280) + (bArr[i2] & 255);
    }

    public ZipShort(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipShort(byte[] bArr, int i2) {
        this.value = getValue(bArr, i2);
    }

    public static int getValue(byte[] bArr) {
        return getValue(bArr, 0);
    }
}
