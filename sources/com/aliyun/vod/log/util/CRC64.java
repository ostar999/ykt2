package com.aliyun.vod.log.util;

/* loaded from: classes2.dex */
public class CRC64 {
    private static final long[] crcTable = new long[256];
    private static final long poly = -3932672073523589310L;
    private long crc = -1;

    static {
        for (int i2 = 0; i2 < crcTable.length; i2++) {
            long j2 = i2;
            for (int i3 = 0; i3 < 8; i3++) {
                j2 = (j2 & 1) == 1 ? (j2 >>> 1) ^ poly : j2 >>> 1;
            }
            crcTable[i2] = j2;
        }
    }

    public long getValue() {
        return ~this.crc;
    }

    public void update(byte b3) {
        long[] jArr = crcTable;
        long j2 = this.crc;
        this.crc = (j2 >>> 8) ^ jArr[(b3 ^ ((int) j2)) & 255];
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        while (i2 < i4) {
            long[] jArr = crcTable;
            int i5 = i2 + 1;
            byte b3 = bArr[i2];
            long j2 = this.crc;
            this.crc = (j2 >>> 8) ^ jArr[(b3 ^ ((int) j2)) & 255];
            i2 = i5;
        }
    }
}
