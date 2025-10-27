package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class ExtraDataRecord {
    private byte[] data;
    private long header;
    private int sizeOfData;

    public byte[] getData() {
        return this.data;
    }

    public long getHeader() {
        return this.header;
    }

    public int getSizeOfData() {
        return this.sizeOfData;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public void setHeader(long j2) {
        this.header = j2;
    }

    public void setSizeOfData(int i2) {
        this.sizeOfData = i2;
    }
}
