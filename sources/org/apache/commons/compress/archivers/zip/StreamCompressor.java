package org.apache.commons.compress.archivers.zip;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.apache.commons.compress.parallel.ScatterGatherBackingStore;

/* loaded from: classes9.dex */
public abstract class StreamCompressor implements Closeable {
    private static final int DEFLATER_BLOCK_SIZE = 8192;
    private static final int bufferSize = 4096;
    private final Deflater def;
    private final CRC32 crc = new CRC32();
    private long writtenToOutputStreamForLastEntry = 0;
    private long sourcePayloadLength = 0;
    private long totalWrittenToOutputStream = 0;
    private final byte[] outputBuffer = new byte[4096];
    private final byte[] readerBuf = new byte[4096];

    public static final class DataOutputCompressor extends StreamCompressor {
        private final DataOutput raf;

        public DataOutputCompressor(Deflater deflater, DataOutput dataOutput) {
            super(deflater);
            this.raf = dataOutput;
        }

        @Override // org.apache.commons.compress.archivers.zip.StreamCompressor
        public final void writeOut(byte[] bArr, int i2, int i3) throws IOException {
            this.raf.write(bArr, i2, i3);
        }
    }

    public static final class OutputStreamCompressor extends StreamCompressor {
        private final OutputStream os;

        public OutputStreamCompressor(Deflater deflater, OutputStream outputStream) {
            super(deflater);
            this.os = outputStream;
        }

        @Override // org.apache.commons.compress.archivers.zip.StreamCompressor
        public final void writeOut(byte[] bArr, int i2, int i3) throws IOException {
            this.os.write(bArr, i2, i3);
        }
    }

    public static final class ScatterGatherBackingStoreCompressor extends StreamCompressor {
        private final ScatterGatherBackingStore bs;

        public ScatterGatherBackingStoreCompressor(Deflater deflater, ScatterGatherBackingStore scatterGatherBackingStore) {
            super(deflater);
            this.bs = scatterGatherBackingStore;
        }

        @Override // org.apache.commons.compress.archivers.zip.StreamCompressor
        public final void writeOut(byte[] bArr, int i2, int i3) throws IOException {
            this.bs.writeOut(bArr, i2, i3);
        }
    }

    public StreamCompressor(Deflater deflater) {
        this.def = deflater;
    }

    public static StreamCompressor create(OutputStream outputStream, Deflater deflater) {
        return new OutputStreamCompressor(deflater, outputStream);
    }

    private void deflateUntilInputIsNeeded() throws IOException {
        while (!this.def.needsInput()) {
            deflate();
        }
    }

    private void writeDeflated(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 <= 0 || this.def.finished()) {
            return;
        }
        if (i3 <= 8192) {
            this.def.setInput(bArr, i2, i3);
            deflateUntilInputIsNeeded();
            return;
        }
        int i4 = i3 / 8192;
        for (int i5 = 0; i5 < i4; i5++) {
            this.def.setInput(bArr, (i5 * 8192) + i2, 8192);
            deflateUntilInputIsNeeded();
        }
        int i6 = i4 * 8192;
        if (i6 < i3) {
            this.def.setInput(bArr, i2 + i6, i3 - i6);
            deflateUntilInputIsNeeded();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.def.end();
    }

    public void deflate(InputStream inputStream, int i2) throws IOException {
        reset();
        while (true) {
            byte[] bArr = this.readerBuf;
            int i3 = inputStream.read(bArr, 0, bArr.length);
            if (i3 < 0) {
                break;
            } else {
                write(this.readerBuf, 0, i3, i2);
            }
        }
        if (i2 == 8) {
            flushDeflater();
        }
    }

    public void flushDeflater() throws IOException {
        this.def.finish();
        while (!this.def.finished()) {
            deflate();
        }
    }

    public long getBytesRead() {
        return this.sourcePayloadLength;
    }

    public long getBytesWrittenForLastEntry() {
        return this.writtenToOutputStreamForLastEntry;
    }

    public long getCrc32() {
        return this.crc.getValue();
    }

    public long getTotalBytesWritten() {
        return this.totalWrittenToOutputStream;
    }

    public void reset() {
        this.crc.reset();
        this.def.reset();
        this.sourcePayloadLength = 0L;
        this.writtenToOutputStreamForLastEntry = 0L;
    }

    public long write(byte[] bArr, int i2, int i3, int i4) throws IOException {
        long j2 = this.writtenToOutputStreamForLastEntry;
        this.crc.update(bArr, i2, i3);
        if (i4 == 8) {
            writeDeflated(bArr, i2, i3);
        } else {
            writeCounted(bArr, i2, i3);
        }
        this.sourcePayloadLength += i3;
        return this.writtenToOutputStreamForLastEntry - j2;
    }

    public void writeCounted(byte[] bArr) throws IOException {
        writeCounted(bArr, 0, bArr.length);
    }

    public abstract void writeOut(byte[] bArr, int i2, int i3) throws IOException;

    public static StreamCompressor create(OutputStream outputStream) {
        return create(outputStream, new Deflater(-1, true));
    }

    public void writeCounted(byte[] bArr, int i2, int i3) throws IOException {
        writeOut(bArr, i2, i3);
        long j2 = i3;
        this.writtenToOutputStreamForLastEntry += j2;
        this.totalWrittenToOutputStream += j2;
    }

    public static StreamCompressor create(DataOutput dataOutput, Deflater deflater) {
        return new DataOutputCompressor(deflater, dataOutput);
    }

    public static StreamCompressor create(int i2, ScatterGatherBackingStore scatterGatherBackingStore) {
        return new ScatterGatherBackingStoreCompressor(new Deflater(i2, true), scatterGatherBackingStore);
    }

    public void deflate() throws IOException {
        Deflater deflater = this.def;
        byte[] bArr = this.outputBuffer;
        int iDeflate = deflater.deflate(bArr, 0, bArr.length);
        if (iDeflate > 0) {
            writeCounted(this.outputBuffer, 0, iDeflate);
        }
    }

    public static StreamCompressor create(ScatterGatherBackingStore scatterGatherBackingStore) {
        return create(-1, scatterGatherBackingStore);
    }
}
