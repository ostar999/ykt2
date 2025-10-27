package org.apache.commons.compress.compressors.deflate;

/* loaded from: classes9.dex */
public class DeflateParameters {
    private boolean zlibHeader = true;
    private int compressionLevel = -1;

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int i2) {
        if (i2 >= -1 && i2 <= 9) {
            this.compressionLevel = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid Deflate compression level: " + i2);
    }

    public void setWithZlibHeader(boolean z2) {
        this.zlibHeader = z2;
    }

    public boolean withZlibHeader() {
        return this.zlibHeader;
    }
}
