package org.apache.commons.compress.archivers.zip;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.compress.parallel.FileBasedScatterGatherBackingStore;
import org.apache.commons.compress.parallel.ScatterGatherBackingStore;
import org.apache.commons.compress.utils.BoundedInputStream;

/* loaded from: classes9.dex */
public class ScatterZipOutputStream implements Closeable {
    private final ScatterGatherBackingStore backingStore;
    private final Queue<CompressedEntry> items = new ConcurrentLinkedQueue();
    private final StreamCompressor streamCompressor;

    public static class CompressedEntry {
        final long compressedSize;
        final long crc;
        final long size;
        final ZipArchiveEntryRequest zipArchiveEntryRequest;

        public CompressedEntry(ZipArchiveEntryRequest zipArchiveEntryRequest, long j2, long j3, long j4) {
            this.zipArchiveEntryRequest = zipArchiveEntryRequest;
            this.crc = j2;
            this.compressedSize = j3;
            this.size = j4;
        }

        public ZipArchiveEntry transferToArchiveEntry() {
            ZipArchiveEntry zipArchiveEntry = this.zipArchiveEntryRequest.getZipArchiveEntry();
            zipArchiveEntry.setCompressedSize(this.compressedSize);
            zipArchiveEntry.setSize(this.size);
            zipArchiveEntry.setCrc(this.crc);
            zipArchiveEntry.setMethod(this.zipArchiveEntryRequest.getMethod());
            return zipArchiveEntry;
        }
    }

    public ScatterZipOutputStream(ScatterGatherBackingStore scatterGatherBackingStore, StreamCompressor streamCompressor) {
        this.backingStore = scatterGatherBackingStore;
        this.streamCompressor = streamCompressor;
    }

    public static ScatterZipOutputStream fileBased(File file) throws FileNotFoundException {
        return fileBased(file, -1);
    }

    public void addArchiveEntry(ZipArchiveEntryRequest zipArchiveEntryRequest) throws IOException {
        InputStream payloadStream = zipArchiveEntryRequest.getPayloadStream();
        try {
            this.streamCompressor.deflate(payloadStream, zipArchiveEntryRequest.getMethod());
            payloadStream.close();
            this.items.add(new CompressedEntry(zipArchiveEntryRequest, this.streamCompressor.getCrc32(), this.streamCompressor.getBytesWrittenForLastEntry(), this.streamCompressor.getBytesRead()));
        } catch (Throwable th) {
            payloadStream.close();
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.backingStore.close();
    }

    public void writeTo(ZipArchiveOutputStream zipArchiveOutputStream) throws IOException {
        this.backingStore.closeForWriting();
        InputStream inputStream = this.backingStore.getInputStream();
        for (CompressedEntry compressedEntry : this.items) {
            BoundedInputStream boundedInputStream = new BoundedInputStream(inputStream, compressedEntry.compressedSize);
            zipArchiveOutputStream.addRawArchiveEntry(compressedEntry.transferToArchiveEntry(), boundedInputStream);
            boundedInputStream.close();
        }
        inputStream.close();
    }

    public static ScatterZipOutputStream fileBased(File file, int i2) throws FileNotFoundException {
        FileBasedScatterGatherBackingStore fileBasedScatterGatherBackingStore = new FileBasedScatterGatherBackingStore(file);
        return new ScatterZipOutputStream(fileBasedScatterGatherBackingStore, StreamCompressor.create(i2, fileBasedScatterGatherBackingStore));
    }
}
