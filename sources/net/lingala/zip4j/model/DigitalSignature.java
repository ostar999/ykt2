package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class DigitalSignature {
    private int headerSignature;
    private String signatureData;
    private int sizeOfData;

    public int getHeaderSignature() {
        return this.headerSignature;
    }

    public String getSignatureData() {
        return this.signatureData;
    }

    public int getSizeOfData() {
        return this.sizeOfData;
    }

    public void setHeaderSignature(int i2) {
        this.headerSignature = i2;
    }

    public void setSignatureData(String str) {
        this.signatureData = str;
    }

    public void setSizeOfData(int i2) {
        this.sizeOfData = i2;
    }
}
