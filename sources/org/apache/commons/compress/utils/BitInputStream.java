package org.apache.commons.compress.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

/* loaded from: classes9.dex */
public class BitInputStream implements Closeable {
    private static final long[] MASKS = new long[64];
    private static final int MAXIMUM_CACHE_SIZE = 63;
    private long bitsCached = 0;
    private int bitsCachedSize = 0;
    private final ByteOrder byteOrder;
    private final InputStream in;

    static {
        for (int i2 = 1; i2 <= 63; i2++) {
            long[] jArr = MASKS;
            jArr[i2] = (jArr[i2 - 1] << 1) + 1;
        }
    }

    public BitInputStream(InputStream inputStream, ByteOrder byteOrder) {
        this.in = inputStream;
        this.byteOrder = byteOrder;
    }

    public void clearBitCache() {
        this.bitsCached = 0L;
        this.bitsCachedSize = 0;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public long readBits(int i2) throws IOException {
        long j2;
        if (i2 < 0 || i2 > 63) {
            throw new IllegalArgumentException("count must not be negative or greater than 63");
        }
        while (true) {
            int i3 = this.bitsCachedSize;
            if (i3 >= i2) {
                if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
                    long j3 = this.bitsCached;
                    j2 = j3 & MASKS[i2];
                    this.bitsCached = j3 >>> i2;
                } else {
                    j2 = (this.bitsCached >> (i3 - i2)) & MASKS[i2];
                }
                this.bitsCachedSize = i3 - i2;
                return j2;
            }
            long j4 = this.in.read();
            if (j4 < 0) {
                return j4;
            }
            if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.bitsCached = (j4 << this.bitsCachedSize) | this.bitsCached;
            } else {
                this.bitsCached = j4 | (this.bitsCached << 8);
            }
            this.bitsCachedSize += 8;
        }
    }
}
