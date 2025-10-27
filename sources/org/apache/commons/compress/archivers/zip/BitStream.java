package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.utils.BitInputStream;

/* loaded from: classes9.dex */
class BitStream extends BitInputStream {
    public BitStream(InputStream inputStream) {
        super(inputStream, ByteOrder.LITTLE_ENDIAN);
    }

    public int nextBit() throws IOException {
        return (int) readBits(1);
    }

    public long nextBits(int i2) throws IOException {
        return readBits(i2);
    }

    public int nextByte() throws IOException {
        return (int) readBits(8);
    }
}
