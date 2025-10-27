package org.apache.commons.compress.compressors.lzw;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.BitInputStream;

/* loaded from: classes9.dex */
public abstract class LZWInputStream extends CompressorInputStream {
    protected static final int DEFAULT_CODE_SIZE = 9;
    protected static final int UNUSED_PREFIX = -1;
    private byte[] characters;
    protected final BitInputStream in;
    private byte[] outputStack;
    private int outputStackLocation;
    private int[] prefixes;
    private byte previousCodeFirstChar;
    private int tableSize;
    private final byte[] oneByte = new byte[1];
    private int clearCode = -1;
    private int codeSize = 9;
    private int previousCode = -1;

    public LZWInputStream(InputStream inputStream, ByteOrder byteOrder) {
        this.in = new BitInputStream(inputStream, byteOrder);
    }

    private int readFromStack(byte[] bArr, int i2, int i3) {
        int length = this.outputStack.length - this.outputStackLocation;
        if (length <= 0) {
            return 0;
        }
        int iMin = Math.min(length, i3);
        System.arraycopy(this.outputStack, this.outputStackLocation, bArr, i2, iMin);
        this.outputStackLocation += iMin;
        return iMin;
    }

    public abstract int addEntry(int i2, byte b3) throws IOException;

    public int addEntry(int i2, byte b3, int i3) {
        int i4 = this.tableSize;
        if (i4 >= i3) {
            return -1;
        }
        this.prefixes[i4] = i2;
        this.characters[i4] = b3;
        this.tableSize = i4 + 1;
        return i4;
    }

    public int addRepeatOfPreviousCode() throws IOException {
        int i2 = this.previousCode;
        if (i2 != -1) {
            return addEntry(i2, this.previousCodeFirstChar);
        }
        throw new IOException("The first code can't be a reference to its preceding code");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public abstract int decompressNextSymbol() throws IOException;

    public int expandCodeToOutputStack(int i2, boolean z2) throws IOException {
        int i3 = i2;
        while (i3 >= 0) {
            byte[] bArr = this.outputStack;
            int i4 = this.outputStackLocation - 1;
            this.outputStackLocation = i4;
            bArr[i4] = this.characters[i3];
            i3 = this.prefixes[i3];
        }
        int i5 = this.previousCode;
        if (i5 != -1 && !z2) {
            addEntry(i5, this.outputStack[this.outputStackLocation]);
        }
        this.previousCode = i2;
        byte[] bArr2 = this.outputStack;
        int i6 = this.outputStackLocation;
        this.previousCodeFirstChar = bArr2[i6];
        return i6;
    }

    public int getClearCode() {
        return this.clearCode;
    }

    public int getCodeSize() {
        return this.codeSize;
    }

    public int getPrefix(int i2) {
        return this.prefixes[i2];
    }

    public int getPrefixesLength() {
        return this.prefixes.length;
    }

    public int getTableSize() {
        return this.tableSize;
    }

    public void incrementCodeSize() {
        this.codeSize++;
    }

    public void initializeTables(int i2) {
        int i3 = 1 << i2;
        this.prefixes = new int[i3];
        this.characters = new byte[i3];
        this.outputStack = new byte[i3];
        this.outputStackLocation = i3;
        for (int i4 = 0; i4 < 256; i4++) {
            this.prefixes[i4] = -1;
            this.characters[i4] = (byte) i4;
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i2 = read(this.oneByte);
        return i2 < 0 ? i2 : this.oneByte[0] & 255;
    }

    public int readNextCode() throws IOException {
        int i2 = this.codeSize;
        if (i2 <= 31) {
            return (int) this.in.readBits(i2);
        }
        throw new IllegalArgumentException("code size must not be bigger than 31");
    }

    public void resetCodeSize() {
        setCodeSize(9);
    }

    public void resetPreviousCode() {
        this.previousCode = -1;
    }

    public void setClearCode(int i2) {
        this.clearCode = 1 << (i2 - 1);
    }

    public void setCodeSize(int i2) {
        this.codeSize = i2;
    }

    public void setPrefix(int i2, int i3) {
        this.prefixes[i2] = i3;
    }

    public void setTableSize(int i2) {
        this.tableSize = i2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int fromStack = readFromStack(bArr, i2, i3);
        while (true) {
            int i4 = i3 - fromStack;
            if (i4 > 0) {
                int iDecompressNextSymbol = decompressNextSymbol();
                if (iDecompressNextSymbol < 0) {
                    if (fromStack <= 0) {
                        return iDecompressNextSymbol;
                    }
                    count(fromStack);
                    return fromStack;
                }
                fromStack += readFromStack(bArr, i2 + fromStack, i4);
            } else {
                count(fromStack);
                return fromStack;
            }
        }
    }
}
