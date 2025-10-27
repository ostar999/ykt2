package org.apache.commons.compress.compressors.z;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.compressors.lzw.LZWInputStream;

/* loaded from: classes9.dex */
public class ZCompressorInputStream extends LZWInputStream {
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead;

    public ZCompressorInputStream(InputStream inputStream) throws IOException {
        super(inputStream, ByteOrder.LITTLE_ENDIAN);
        this.totalCodesRead = 0L;
        int bits = (int) this.in.readBits(8);
        int bits2 = (int) this.in.readBits(8);
        int bits3 = (int) this.in.readBits(8);
        if (bits != 31 || bits2 != 157 || bits3 < 0) {
            throw new IOException("Input is not in .Z format");
        }
        boolean z2 = (bits3 & 128) != 0;
        this.blockMode = z2;
        int i2 = bits3 & 31;
        this.maxCodeSize = i2;
        if (z2) {
            setClearCode(9);
        }
        initializeTables(i2);
        clearEntries();
    }

    private void clearEntries() {
        setTableSize((this.blockMode ? 1 : 0) + 256);
    }

    public static boolean matches(byte[] bArr, int i2) {
        return i2 > 3 && bArr[0] == 31 && bArr[1] == -99;
    }

    private void reAlignReading() throws IOException {
        long j2 = 8 - (this.totalCodesRead % 8);
        if (j2 == 8) {
            j2 = 0;
        }
        for (long j3 = 0; j3 < j2; j3++) {
            readNextCode();
        }
        this.in.clearBitCache();
    }

    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    public int addEntry(int i2, byte b3) throws IOException {
        int codeSize = 1 << getCodeSize();
        int iAddEntry = addEntry(i2, b3, codeSize);
        if (getTableSize() == codeSize && getCodeSize() < this.maxCodeSize) {
            reAlignReading();
            incrementCodeSize();
        }
        return iAddEntry;
    }

    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    public int decompressNextSymbol() throws IOException {
        int nextCode = readNextCode();
        if (nextCode < 0) {
            return -1;
        }
        boolean z2 = false;
        if (this.blockMode && nextCode == getClearCode()) {
            clearEntries();
            reAlignReading();
            resetCodeSize();
            resetPreviousCode();
            return 0;
        }
        if (nextCode == getTableSize()) {
            addRepeatOfPreviousCode();
            z2 = true;
        } else if (nextCode > getTableSize()) {
            throw new IOException(String.format("Invalid %d bit code 0x%x", Integer.valueOf(getCodeSize()), Integer.valueOf(nextCode)));
        }
        return expandCodeToOutputStack(nextCode, z2);
    }

    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    public int readNextCode() throws IOException {
        int nextCode = super.readNextCode();
        if (nextCode >= 0) {
            this.totalCodesRead++;
        }
        return nextCode;
    }
}
