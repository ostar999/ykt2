package org.apache.commons.compress.compressors;

import java.io.InputStream;

/* loaded from: classes9.dex */
public abstract class CompressorInputStream extends InputStream {
    private long bytesRead = 0;

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

    public void pushedBackBytes(long j2) {
        this.bytesRead -= j2;
    }

    public void count(long j2) {
        if (j2 != -1) {
            this.bytesRead += j2;
        }
    }
}
