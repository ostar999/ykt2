package org.apache.commons.compress.archivers.dump;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
class TapeInputStream extends FilterInputStream {
    private static final int recordSize = 1024;
    private byte[] blockBuffer;
    private int blockSize;
    private long bytesRead;
    private int currBlkIdx;
    private boolean isCompressed;
    private int readOffset;

    /* renamed from: org.apache.commons.compress.archivers.dump.TapeInputStream$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE;

        static {
            int[] iArr = new int[DumpArchiveConstants.COMPRESSION_TYPE.values().length];
            $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE = iArr;
            try {
                iArr[DumpArchiveConstants.COMPRESSION_TYPE.ZLIB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.BZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.LZO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public TapeInputStream(InputStream inputStream) {
        super(inputStream);
        this.blockBuffer = new byte[1024];
        this.currBlkIdx = -1;
        this.blockSize = 1024;
        this.readOffset = 1024;
        this.isCompressed = false;
        this.bytesRead = 0L;
    }

    private boolean readBlock(boolean z2) throws IOException {
        boolean fully;
        if (((FilterInputStream) this).in == null) {
            throw new IOException("input buffer is closed");
        }
        if (!this.isCompressed || this.currBlkIdx == -1) {
            fully = readFully(this.blockBuffer, 0, this.blockSize);
            this.bytesRead += this.blockSize;
        } else {
            if (!readFully(this.blockBuffer, 0, 4)) {
                return false;
            }
            this.bytesRead += 4;
            int iConvert32 = DumpArchiveUtil.convert32(this.blockBuffer, 0);
            if ((iConvert32 & 1) == 1) {
                int i2 = (iConvert32 >> 1) & 7;
                int i3 = (iConvert32 >> 4) & 268435455;
                byte[] bArr = new byte[i3];
                boolean fully2 = readFully(bArr, 0, i3);
                this.bytesRead += i3;
                if (z2) {
                    int i4 = AnonymousClass1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.find(i2 & 3).ordinal()];
                    if (i4 != 1) {
                        if (i4 == 2) {
                            throw new UnsupportedCompressionAlgorithmException("BZLIB2");
                        }
                        if (i4 != 3) {
                            throw new UnsupportedCompressionAlgorithmException();
                        }
                        throw new UnsupportedCompressionAlgorithmException("LZO");
                    }
                    Inflater inflater = new Inflater();
                    try {
                        try {
                            inflater.setInput(bArr, 0, i3);
                            if (inflater.inflate(this.blockBuffer) != this.blockSize) {
                                throw new ShortFileException();
                            }
                        } catch (DataFormatException e2) {
                            throw new DumpArchiveException("bad data", e2);
                        }
                    } finally {
                        inflater.end();
                    }
                } else {
                    Arrays.fill(this.blockBuffer, (byte) 0);
                }
                fully = fully2;
            } else {
                fully = readFully(this.blockBuffer, 0, this.blockSize);
                this.bytesRead += this.blockSize;
            }
        }
        this.currBlkIdx++;
        this.readOffset = 0;
        return fully;
    }

    private boolean readFully(byte[] bArr, int i2, int i3) throws IOException {
        if (IOUtils.readFully(((FilterInputStream) this).in, bArr, i2, i3) >= i3) {
            return true;
        }
        throw new ShortFileException();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int i2 = this.readOffset;
        int i3 = this.blockSize;
        return i2 < i3 ? i3 - i2 : ((FilterInputStream) this).in.available();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (((FilterInputStream) this).in == null || ((FilterInputStream) this).in == System.in) {
            return;
        }
        ((FilterInputStream) this).in.close();
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    public byte[] peek() throws IOException {
        if (this.readOffset == this.blockSize && !readBlock(true)) {
            return null;
        }
        byte[] bArr = new byte[1024];
        System.arraycopy(this.blockBuffer, this.readOffset, bArr, 0, 1024);
        return bArr;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    public byte[] readRecord() throws IOException {
        byte[] bArr = new byte[1024];
        if (-1 != read(bArr, 0, 1024)) {
            return bArr;
        }
        throw new ShortFileException();
    }

    public void resetBlockSize(int i2, boolean z2) throws IOException {
        this.isCompressed = z2;
        int i3 = i2 * 1024;
        this.blockSize = i3;
        byte[] bArr = this.blockBuffer;
        byte[] bArr2 = new byte[i3];
        this.blockBuffer = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, 1024);
        readFully(this.blockBuffer, 1024, this.blockSize - 1024);
        this.currBlkIdx = 0;
        this.readOffset = 1024;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j2) throws IOException {
        long j3 = 0;
        if (j2 % 1024 != 0) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        while (j3 < j2) {
            int i2 = this.readOffset;
            int i3 = this.blockSize;
            if (i2 == i3) {
                if (!readBlock(j2 - j3 < ((long) i3))) {
                    return -1L;
                }
            }
            int i4 = this.readOffset;
            long j4 = j2 - j3;
            long j5 = i4 + j4;
            int i5 = this.blockSize;
            if (j5 > i5) {
                j4 = i5 - i4;
            }
            this.readOffset = (int) (i4 + j4);
            j3 += j4;
        }
        return j3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 % 1024 != 0) {
            throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
        }
        int i4 = 0;
        while (i4 < i3) {
            if (this.readOffset == this.blockSize && !readBlock(true)) {
                return -1;
            }
            int i5 = this.readOffset;
            int i6 = i3 - i4;
            int i7 = i5 + i6;
            int i8 = this.blockSize;
            if (i7 > i8) {
                i6 = i8 - i5;
            }
            System.arraycopy(this.blockBuffer, i5, bArr, i2, i6);
            this.readOffset += i6;
            i4 += i6;
            i2 += i6;
        }
        return i4;
    }
}
