package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class Zip64ExtendedInfo {
    private int header;
    private int size;
    private long compressedSize = -1;
    private long unCompressedSize = -1;
    private long offsetLocalHeader = -1;
    private int diskNumberStart = -1;

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public int getDiskNumberStart() {
        return this.diskNumberStart;
    }

    public int getHeader() {
        return this.header;
    }

    public long getOffsetLocalHeader() {
        return this.offsetLocalHeader;
    }

    public int getSize() {
        return this.size;
    }

    public long getUnCompressedSize() {
        return this.unCompressedSize;
    }

    public void setCompressedSize(long j2) {
        this.compressedSize = j2;
    }

    public void setDiskNumberStart(int i2) {
        this.diskNumberStart = i2;
    }

    public void setHeader(int i2) {
        this.header = i2;
    }

    public void setOffsetLocalHeader(long j2) {
        this.offsetLocalHeader = j2;
    }

    public void setSize(int i2) {
        this.size = i2;
    }

    public void setUnCompressedSize(long j2) {
        this.unCompressedSize = j2;
    }
}
