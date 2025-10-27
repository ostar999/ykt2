package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class Zip64EndCentralDirLocator {
    private int noOfDiskStartOfZip64EndOfCentralDirRec;
    private long offsetZip64EndOfCentralDirRec;
    private long signature;
    private int totNumberOfDiscs;

    public int getNoOfDiskStartOfZip64EndOfCentralDirRec() {
        return this.noOfDiskStartOfZip64EndOfCentralDirRec;
    }

    public long getOffsetZip64EndOfCentralDirRec() {
        return this.offsetZip64EndOfCentralDirRec;
    }

    public long getSignature() {
        return this.signature;
    }

    public int getTotNumberOfDiscs() {
        return this.totNumberOfDiscs;
    }

    public void setNoOfDiskStartOfZip64EndOfCentralDirRec(int i2) {
        this.noOfDiskStartOfZip64EndOfCentralDirRec = i2;
    }

    public void setOffsetZip64EndOfCentralDirRec(long j2) {
        this.offsetZip64EndOfCentralDirRec = j2;
    }

    public void setSignature(long j2) {
        this.signature = j2;
    }

    public void setTotNumberOfDiscs(int i2) {
        this.totNumberOfDiscs = i2;
    }
}
