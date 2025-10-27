package org.apache.commons.compress.archivers.zip;

import com.google.common.base.Ascii;
import java.io.Serializable;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes9.dex */
public final class ZipLong implements Cloneable, Serializable {
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 16711680;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 4278190080L;
    private static final int BYTE_3_SHIFT = 24;
    private static final long serialVersionUID = 1;
    private final long value;
    public static final ZipLong CFH_SIG = new ZipLong(InternalZipConstants.CENSIG);
    public static final ZipLong LFH_SIG = new ZipLong(InternalZipConstants.LOCSIG);
    public static final ZipLong DD_SIG = new ZipLong(134695760);
    static final ZipLong ZIP64_MAGIC = new ZipLong(InternalZipConstants.ZIP_64_LIMIT);
    public static final ZipLong SINGLE_SEGMENT_SPLIT_MARKER = new ZipLong(808471376);
    public static final ZipLong AED_SIG = new ZipLong(InternalZipConstants.ARCEXTDATREC);

    public ZipLong(long j2) {
        this.value = j2;
    }

    public static void putLong(long j2, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (255 & j2);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((65280 & j2) >> 8);
        bArr[i4] = (byte) ((16711680 & j2) >> 16);
        bArr[i4 + 1] = (byte) ((j2 & BYTE_3_MASK) >> 24);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ZipLong) && this.value == ((ZipLong) obj).getValue();
    }

    public byte[] getBytes() {
        return getBytes(this.value);
    }

    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        return (int) this.value;
    }

    public String toString() {
        return "ZipLong value: " + this.value;
    }

    public static byte[] getBytes(long j2) {
        byte[] bArr = new byte[4];
        putLong(j2, bArr, 0);
        return bArr;
    }

    public static long getValue(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] << Ascii.CAN) & BYTE_3_MASK) + ((bArr[i2 + 2] << 16) & BYTE_2_MASK) + ((bArr[i2 + 1] << 8) & 65280) + (bArr[i2] & 255);
    }

    public ZipLong(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipLong(byte[] bArr, int i2) {
        this.value = getValue(bArr, i2);
    }

    public void putLong(byte[] bArr, int i2) {
        putLong(this.value, bArr, i2);
    }

    public static long getValue(byte[] bArr) {
        return getValue(bArr, 0);
    }
}
