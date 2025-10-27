package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class ArchiveExtraDataRecord {
    private String extraFieldData;
    private int extraFieldLength;
    private int signature;

    public String getExtraFieldData() {
        return this.extraFieldData;
    }

    public int getExtraFieldLength() {
        return this.extraFieldLength;
    }

    public int getSignature() {
        return this.signature;
    }

    public void setExtraFieldData(String str) {
        this.extraFieldData = str;
    }

    public void setExtraFieldLength(int i2) {
        this.extraFieldLength = i2;
    }

    public void setSignature(int i2) {
        this.signature = i2;
    }
}
