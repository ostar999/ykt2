package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class FramedSnappyCompressorInputStream extends CompressorInputStream {
    private static final int COMPRESSED_CHUNK_TYPE = 0;
    static final long MASK_OFFSET = 2726488792L;
    private static final int MAX_SKIPPABLE_TYPE = 253;
    private static final int MAX_UNSKIPPABLE_TYPE = 127;
    private static final int MIN_UNSKIPPABLE_TYPE = 2;
    private static final int PADDING_CHUNK_TYPE = 254;
    private static final int STREAM_IDENTIFIER_TYPE = 255;
    private static final byte[] SZ_SIGNATURE = {-1, 6, 0, 0, 115, 78, 97, 80, 112, 89};
    private static final int UNCOMPRESSED_CHUNK_TYPE = 1;
    private final PureJavaCrc32C checksum;
    private SnappyCompressorInputStream currentCompressedChunk;
    private final FramedSnappyDialect dialect;
    private boolean endReached;
    private long expectedChecksum;
    private final PushbackInputStream in;
    private boolean inUncompressedChunk;
    private final byte[] oneByte;
    private int uncompressedBytesRemaining;

    public FramedSnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, FramedSnappyDialect.STANDARD);
    }

    public static boolean matches(byte[] bArr, int i2) {
        byte[] bArr2 = SZ_SIGNATURE;
        if (i2 < bArr2.length) {
            return false;
        }
        if (bArr.length > bArr2.length) {
            byte[] bArr3 = new byte[bArr2.length];
            System.arraycopy(bArr, 0, bArr3, 0, bArr2.length);
            bArr = bArr3;
        }
        return Arrays.equals(bArr, bArr2);
    }

    private long readCrc() throws IOException {
        int fully = IOUtils.readFully(this.in, new byte[4]);
        count(fully);
        if (fully != 4) {
            throw new IOException("premature end of stream");
        }
        long j2 = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            j2 |= (r1[i2] & 255) << (i2 * 8);
        }
        return j2;
    }

    private void readNextBlock() throws IOException {
        verifyLastChecksumAndReset();
        this.inUncompressedChunk = false;
        int oneByte = readOneByte();
        if (oneByte == -1) {
            this.endReached = true;
            return;
        }
        if (oneByte == 255) {
            this.in.unread(oneByte);
            pushedBackBytes(1L);
            readStreamIdentifier();
            readNextBlock();
            return;
        }
        if (oneByte == 254 || (oneByte > 127 && oneByte <= 253)) {
            skipBlock();
            readNextBlock();
            return;
        }
        if (oneByte >= 2 && oneByte <= 127) {
            throw new IOException("unskippable chunk with type " + oneByte + " (hex " + Integer.toHexString(oneByte) + ") detected.");
        }
        if (oneByte == 1) {
            this.inUncompressedChunk = true;
            this.uncompressedBytesRemaining = readSize() - 4;
            this.expectedChecksum = unmask(readCrc());
        } else {
            if (oneByte != 0) {
                throw new IOException("unknown chunk type " + oneByte + " detected.");
            }
            boolean zUsesChecksumWithCompressedChunks = this.dialect.usesChecksumWithCompressedChunks();
            long size = readSize() - (zUsesChecksumWithCompressedChunks ? 4 : 0);
            if (zUsesChecksumWithCompressedChunks) {
                this.expectedChecksum = unmask(readCrc());
            } else {
                this.expectedChecksum = -1L;
            }
            SnappyCompressorInputStream snappyCompressorInputStream = new SnappyCompressorInputStream(new BoundedInputStream(this.in, size));
            this.currentCompressedChunk = snappyCompressorInputStream;
            count(snappyCompressorInputStream.getBytesRead());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readOnce(byte[] r5, int r6, int r7) throws java.io.IOException {
        /*
            r4 = this;
            boolean r0 = r4.inUncompressedChunk
            r1 = -1
            if (r0 == 0) goto L1f
            int r0 = r4.uncompressedBytesRemaining
            int r7 = java.lang.Math.min(r0, r7)
            if (r7 != 0) goto Le
            return r1
        Le:
            java.io.PushbackInputStream r0 = r4.in
            int r7 = r0.read(r5, r6, r7)
            if (r7 == r1) goto L42
            int r0 = r4.uncompressedBytesRemaining
            int r0 = r0 - r7
            r4.uncompressedBytesRemaining = r0
            r4.count(r7)
            goto L42
        L1f:
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            if (r0 == 0) goto L43
            long r2 = r0.getBytesRead()
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            int r7 = r0.read(r5, r6, r7)
            if (r7 != r1) goto L38
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            r0.close()
            r0 = 0
            r4.currentCompressedChunk = r0
            goto L42
        L38:
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            long r0 = r0.getBytesRead()
            long r0 = r0 - r2
            r4.count(r0)
        L42:
            r1 = r7
        L43:
            if (r1 <= 0) goto L4a
            org.apache.commons.compress.compressors.snappy.PureJavaCrc32C r7 = r4.checksum
            r7.update(r5, r6, r1)
        L4a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream.readOnce(byte[], int, int):int");
    }

    private int readOneByte() throws IOException {
        int i2 = this.in.read();
        if (i2 == -1) {
            return -1;
        }
        count(1);
        return i2 & 255;
    }

    private int readSize() throws IOException {
        int i2 = 0;
        for (int i3 = 0; i3 < 3; i3++) {
            int oneByte = readOneByte();
            if (oneByte == -1) {
                throw new IOException("premature end of stream");
            }
            i2 |= oneByte << (i3 * 8);
        }
        return i2;
    }

    private void readStreamIdentifier() throws IOException {
        byte[] bArr = new byte[10];
        int fully = IOUtils.readFully(this.in, bArr);
        count(fully);
        if (10 != fully || !matches(bArr, 10)) {
            throw new IOException("Not a framed Snappy stream");
        }
    }

    private void skipBlock() throws IOException {
        long size = readSize();
        long jSkip = IOUtils.skip(this.in, size);
        count(jSkip);
        if (jSkip != size) {
            throw new IOException("premature end of stream");
        }
    }

    public static long unmask(long j2) {
        long j3 = (j2 - MASK_OFFSET) & InternalZipConstants.ZIP_64_LIMIT;
        return ((j3 << 15) | (j3 >> 17)) & InternalZipConstants.ZIP_64_LIMIT;
    }

    private void verifyLastChecksumAndReset() throws IOException {
        long j2 = this.expectedChecksum;
        if (j2 >= 0 && j2 != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        this.expectedChecksum = -1L;
        this.checksum.reset();
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.inUncompressedChunk) {
            return Math.min(this.uncompressedBytesRemaining, this.in.available());
        }
        SnappyCompressorInputStream snappyCompressorInputStream = this.currentCompressedChunk;
        if (snappyCompressorInputStream != null) {
            return snappyCompressorInputStream.available();
        }
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        SnappyCompressorInputStream snappyCompressorInputStream = this.currentCompressedChunk;
        if (snappyCompressorInputStream != null) {
            snappyCompressorInputStream.close();
            this.currentCompressedChunk = null;
        }
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    public FramedSnappyCompressorInputStream(InputStream inputStream, FramedSnappyDialect framedSnappyDialect) throws IOException {
        this.oneByte = new byte[1];
        this.expectedChecksum = -1L;
        this.checksum = new PureJavaCrc32C();
        this.in = new PushbackInputStream(inputStream, 1);
        this.dialect = framedSnappyDialect;
        if (framedSnappyDialect.hasStreamIdentifier()) {
            readStreamIdentifier();
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int once = readOnce(bArr, i2, i3);
        if (once != -1) {
            return once;
        }
        readNextBlock();
        if (this.endReached) {
            return -1;
        }
        return readOnce(bArr, i2, i3);
    }
}
