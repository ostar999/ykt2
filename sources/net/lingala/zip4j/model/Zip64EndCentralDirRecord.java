package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class Zip64EndCentralDirRecord {
    private byte[] extensibleDataSector;
    private int noOfThisDisk;
    private int noOfThisDiskStartOfCentralDir;
    private long offsetStartCenDirWRTStartDiskNo;
    private long signature;
    private long sizeOfCentralDir;
    private long sizeOfZip64EndCentralDirRec;
    private long totNoOfEntriesInCentralDir;
    private long totNoOfEntriesInCentralDirOnThisDisk;
    private int versionMadeBy;
    private int versionNeededToExtract;

    public byte[] getExtensibleDataSector() {
        return this.extensibleDataSector;
    }

    public int getNoOfThisDisk() {
        return this.noOfThisDisk;
    }

    public int getNoOfThisDiskStartOfCentralDir() {
        return this.noOfThisDiskStartOfCentralDir;
    }

    public long getOffsetStartCenDirWRTStartDiskNo() {
        return this.offsetStartCenDirWRTStartDiskNo;
    }

    public long getSignature() {
        return this.signature;
    }

    public long getSizeOfCentralDir() {
        return this.sizeOfCentralDir;
    }

    public long getSizeOfZip64EndCentralDirRec() {
        return this.sizeOfZip64EndCentralDirRec;
    }

    public long getTotNoOfEntriesInCentralDir() {
        return this.totNoOfEntriesInCentralDir;
    }

    public long getTotNoOfEntriesInCentralDirOnThisDisk() {
        return this.totNoOfEntriesInCentralDirOnThisDisk;
    }

    public int getVersionMadeBy() {
        return this.versionMadeBy;
    }

    public int getVersionNeededToExtract() {
        return this.versionNeededToExtract;
    }

    public void setExtensibleDataSector(byte[] bArr) {
        this.extensibleDataSector = bArr;
    }

    public void setNoOfThisDisk(int i2) {
        this.noOfThisDisk = i2;
    }

    public void setNoOfThisDiskStartOfCentralDir(int i2) {
        this.noOfThisDiskStartOfCentralDir = i2;
    }

    public void setOffsetStartCenDirWRTStartDiskNo(long j2) {
        this.offsetStartCenDirWRTStartDiskNo = j2;
    }

    public void setSignature(long j2) {
        this.signature = j2;
    }

    public void setSizeOfCentralDir(long j2) {
        this.sizeOfCentralDir = j2;
    }

    public void setSizeOfZip64EndCentralDirRec(long j2) {
        this.sizeOfZip64EndCentralDirRec = j2;
    }

    public void setTotNoOfEntriesInCentralDir(long j2) {
        this.totNoOfEntriesInCentralDir = j2;
    }

    public void setTotNoOfEntriesInCentralDirOnThisDisk(long j2) {
        this.totNoOfEntriesInCentralDirOnThisDisk = j2;
    }

    public void setVersionMadeBy(int i2) {
        this.versionMadeBy = i2;
    }

    public void setVersionNeededToExtract(int i2) {
        this.versionNeededToExtract = i2;
    }
}
