package org.apache.commons.compress.archivers;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public abstract class ArchiveInputStream extends InputStream {
    private static final int BYTE_MASK = 255;
    private final byte[] SINGLE = new byte[1];
    private long bytesRead = 0;

    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return true;
    }

    public void count(int i2) {
        count(i2);
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    @Deprecated
    public int getCount() {
        return (int) this.bytesRead;
    }

    public abstract ArchiveEntry getNextEntry() throws IOException;

    public void pushedBackBytes(long j2) {
        this.bytesRead -= j2;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.SINGLE, 0, 1) == -1) {
            return -1;
        }
        return this.SINGLE[0] & 255;
    }

    public void count(long j2) {
        if (j2 != -1) {
            this.bytesRead += j2;
        }
    }
}
