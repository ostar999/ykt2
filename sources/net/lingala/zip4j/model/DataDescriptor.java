package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class DataDescriptor {
    private int compressedSize;
    private String crc32;
    private int uncompressedSize;

    public int getCompressedSize() {
        return this.compressedSize;
    }

    public String getCrc32() {
        return this.crc32;
    }

    public int getUncompressedSize() {
        return this.uncompressedSize;
    }

    public void setCompressedSize(int i2) {
        this.compressedSize = i2;
    }

    public void setCrc32(String str) {
        this.crc32 = str;
    }

    public void setUncompressedSize(int i2) {
        this.uncompressedSize = i2;
    }
}
