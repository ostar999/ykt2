package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class AESExtraDataRecord {
    private long signature = -1;
    private int dataSize = -1;
    private int versionNumber = -1;
    private String vendorID = null;
    private int aesStrength = -1;
    private int compressionMethod = -1;

    public int getAesStrength() {
        return this.aesStrength;
    }

    public int getCompressionMethod() {
        return this.compressionMethod;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public long getSignature() {
        return this.signature;
    }

    public String getVendorID() {
        return this.vendorID;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public void setAesStrength(int i2) {
        this.aesStrength = i2;
    }

    public void setCompressionMethod(int i2) {
        this.compressionMethod = i2;
    }

    public void setDataSize(int i2) {
        this.dataSize = i2;
    }

    public void setSignature(long j2) {
        this.signature = j2;
    }

    public void setVendorID(String str) {
        this.vendorID = str;
    }

    public void setVersionNumber(int i2) {
        this.versionNumber = i2;
    }
}
