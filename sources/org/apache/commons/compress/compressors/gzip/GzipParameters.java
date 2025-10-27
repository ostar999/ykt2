package org.apache.commons.compress.compressors.gzip;

/* loaded from: classes9.dex */
public class GzipParameters {
    private String comment;
    private String filename;
    private long modificationTime;
    private int compressionLevel = -1;
    private int operatingSystem = 255;

    public String getComment() {
        return this.comment;
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public String getFilename() {
        return this.filename;
    }

    public long getModificationTime() {
        return this.modificationTime;
    }

    public int getOperatingSystem() {
        return this.operatingSystem;
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public void setCompressionLevel(int i2) {
        if (i2 >= -1 && i2 <= 9) {
            this.compressionLevel = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid gzip compression level: " + i2);
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public void setModificationTime(long j2) {
        this.modificationTime = j2;
    }

    public void setOperatingSystem(int i2) {
        this.operatingSystem = i2;
    }
}
