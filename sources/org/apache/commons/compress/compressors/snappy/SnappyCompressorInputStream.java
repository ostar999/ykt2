package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class SnappyCompressorInputStream extends CompressorInputStream {
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    private static final int TAG_MASK = 3;
    private final int blockSize;
    private final byte[] decompressBuf;
    private boolean endReached;
    private final InputStream in;
    private final byte[] oneByte;
    private int readIndex;
    private final int size;
    private int uncompressedBytesRemaining;
    private int writeIndex;

    public SnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, 32768);
    }

    private boolean expandCopy(long j2, int i2) throws IOException {
        if (j2 > this.blockSize) {
            throw new IOException("Offset is larger than block size");
        }
        int i3 = (int) j2;
        if (i3 == 1) {
            byte b3 = this.decompressBuf[this.writeIndex - 1];
            for (int i4 = 0; i4 < i2; i4++) {
                byte[] bArr = this.decompressBuf;
                int i5 = this.writeIndex;
                this.writeIndex = i5 + 1;
                bArr[i5] = b3;
            }
        } else if (i2 < i3) {
            byte[] bArr2 = this.decompressBuf;
            int i6 = this.writeIndex;
            System.arraycopy(bArr2, i6 - i3, bArr2, i6, i2);
            this.writeIndex += i2;
        } else {
            int i7 = i2 / i3;
            int i8 = i2 - (i3 * i7);
            while (true) {
                int i9 = i7 - 1;
                if (i7 == 0) {
                    break;
                }
                byte[] bArr3 = this.decompressBuf;
                int i10 = this.writeIndex;
                System.arraycopy(bArr3, i10 - i3, bArr3, i10, i3);
                this.writeIndex += i3;
                i7 = i9;
            }
            if (i8 > 0) {
                byte[] bArr4 = this.decompressBuf;
                int i11 = this.writeIndex;
                System.arraycopy(bArr4, i11 - i3, bArr4, i11, i8);
                this.writeIndex += i8;
            }
        }
        return this.writeIndex >= this.blockSize * 2;
    }

    private boolean expandLiteral(int i2) throws IOException {
        int fully = IOUtils.readFully(this.in, this.decompressBuf, this.writeIndex, i2);
        count(fully);
        if (i2 != fully) {
            throw new IOException("Premature end of stream");
        }
        int i3 = this.writeIndex + i2;
        this.writeIndex = i3;
        return i3 >= this.blockSize * 2;
    }

    private void fill(int i2) throws IOException {
        int literalLength;
        int i3 = this.uncompressedBytesRemaining;
        if (i3 == 0) {
            this.endReached = true;
        }
        int iMin = Math.min(i2, i3);
        while (iMin > 0) {
            int oneByte = readOneByte();
            int i4 = oneByte & 3;
            if (i4 == 0) {
                literalLength = readLiteralLength(oneByte);
                if (expandLiteral(literalLength)) {
                    return;
                }
            } else if (i4 == 1) {
                int i5 = ((oneByte >> 2) & 7) + 4;
                if (expandCopy(((oneByte & 224) << 3) | readOneByte(), i5)) {
                    return;
                } else {
                    literalLength = i5;
                }
            } else if (i4 == 2) {
                literalLength = (oneByte >> 2) + 1;
                if (expandCopy(readOneByte() | (readOneByte() << 8), literalLength)) {
                    return;
                }
            } else if (i4 != 3) {
                literalLength = 0;
            } else {
                literalLength = (oneByte >> 2) + 1;
                if (expandCopy(readOneByte() | (readOneByte() << 8) | (readOneByte() << 16) | (readOneByte() << 24), literalLength)) {
                    return;
                }
            }
            iMin -= literalLength;
            this.uncompressedBytesRemaining -= literalLength;
        }
    }

    private int readLiteralLength(int i2) throws IOException {
        int oneByte;
        int oneByte2;
        int oneByte3 = i2 >> 2;
        switch (oneByte3) {
            case 60:
                oneByte3 = readOneByte();
                break;
            case 61:
                oneByte = readOneByte();
                oneByte2 = readOneByte() << 8;
                oneByte3 = oneByte | oneByte2;
                break;
            case 62:
                oneByte = readOneByte() | (readOneByte() << 8);
                oneByte2 = readOneByte() << 16;
                oneByte3 = oneByte | oneByte2;
                break;
            case 63:
                oneByte3 = (int) (readOneByte() | (readOneByte() << 8) | (readOneByte() << 16) | (readOneByte() << 24));
                break;
        }
        return oneByte3 + 1;
    }

    private int readOneByte() throws IOException {
        int i2 = this.in.read();
        if (i2 == -1) {
            throw new IOException("Premature end of stream");
        }
        count(1);
        return i2 & 255;
    }

    private long readSize() throws IOException {
        int i2 = 0;
        long j2 = 0;
        while (true) {
            int i3 = i2 + 1;
            j2 |= (r3 & 127) << (i2 * 7);
            if ((readOneByte() & 128) == 0) {
                return j2;
            }
            i2 = i3;
        }
    }

    private void slideBuffer() {
        byte[] bArr = this.decompressBuf;
        int i2 = this.blockSize;
        System.arraycopy(bArr, i2, bArr, 0, i2 * 2);
        int i3 = this.writeIndex;
        int i4 = this.blockSize;
        this.writeIndex = i3 - i4;
        this.readIndex -= i4;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.writeIndex - this.readIndex;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public int getSize() {
        return this.size;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    public SnappyCompressorInputStream(InputStream inputStream, int i2) throws IOException {
        this.oneByte = new byte[1];
        this.endReached = false;
        this.in = inputStream;
        this.blockSize = i2;
        this.decompressBuf = new byte[i2 * 3];
        this.readIndex = 0;
        this.writeIndex = 0;
        int size = (int) readSize();
        this.size = size;
        this.uncompressedBytesRemaining = size;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this.endReached) {
            return -1;
        }
        int iAvailable = available();
        if (i3 > iAvailable) {
            fill(i3 - iAvailable);
        }
        int iMin = Math.min(i3, available());
        if (iMin == 0 && i3 > 0) {
            return -1;
        }
        System.arraycopy(this.decompressBuf, this.readIndex, bArr, i2, iMin);
        int i4 = this.readIndex + iMin;
        this.readIndex = i4;
        if (i4 > this.blockSize) {
            slideBuffer();
        }
        return iMin;
    }
}
