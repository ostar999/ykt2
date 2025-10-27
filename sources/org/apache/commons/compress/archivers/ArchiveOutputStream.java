package org.apache.commons.compress.archivers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public abstract class ArchiveOutputStream extends OutputStream {
    static final int BYTE_MASK = 255;
    private final byte[] oneByte = new byte[1];
    private long bytesWritten = 0;

    public boolean canWriteEntryData(ArchiveEntry archiveEntry) {
        return true;
    }

    public abstract void closeArchiveEntry() throws IOException;

    public void count(int i2) {
        count(i2);
    }

    public abstract ArchiveEntry createArchiveEntry(File file, String str) throws IOException;

    public abstract void finish() throws IOException;

    public long getBytesWritten() {
        return this.bytesWritten;
    }

    @Deprecated
    public int getCount() {
        return (int) this.bytesWritten;
    }

    public abstract void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException;

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        byte[] bArr = this.oneByte;
        bArr[0] = (byte) (i2 & 255);
        write(bArr, 0, 1);
    }

    public void count(long j2) {
        if (j2 != -1) {
            this.bytesWritten += j2;
        }
    }
}
