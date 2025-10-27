package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class EndCentralDirRecord {
    private String comment;
    private byte[] commentBytes;
    private int commentLength;
    private int noOfThisDisk;
    private int noOfThisDiskStartOfCentralDir;
    private long offsetOfStartOfCentralDir;
    private long signature;
    private int sizeOfCentralDir;
    private int totNoOfEntriesInCentralDir;
    private int totNoOfEntriesInCentralDirOnThisDisk;

    public String getComment() {
        return this.comment;
    }

    public byte[] getCommentBytes() {
        return this.commentBytes;
    }

    public int getCommentLength() {
        return this.commentLength;
    }

    public int getNoOfThisDisk() {
        return this.noOfThisDisk;
    }

    public int getNoOfThisDiskStartOfCentralDir() {
        return this.noOfThisDiskStartOfCentralDir;
    }

    public long getOffsetOfStartOfCentralDir() {
        return this.offsetOfStartOfCentralDir;
    }

    public long getSignature() {
        return this.signature;
    }

    public int getSizeOfCentralDir() {
        return this.sizeOfCentralDir;
    }

    public int getTotNoOfEntriesInCentralDir() {
        return this.totNoOfEntriesInCentralDir;
    }

    public int getTotNoOfEntriesInCentralDirOnThisDisk() {
        return this.totNoOfEntriesInCentralDirOnThisDisk;
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public void setCommentBytes(byte[] bArr) {
        this.commentBytes = bArr;
    }

    public void setCommentLength(int i2) {
        this.commentLength = i2;
    }

    public void setNoOfThisDisk(int i2) {
        this.noOfThisDisk = i2;
    }

    public void setNoOfThisDiskStartOfCentralDir(int i2) {
        this.noOfThisDiskStartOfCentralDir = i2;
    }

    public void setOffsetOfStartOfCentralDir(long j2) {
        this.offsetOfStartOfCentralDir = j2;
    }

    public void setSignature(long j2) {
        this.signature = j2;
    }

    public void setSizeOfCentralDir(int i2) {
        this.sizeOfCentralDir = i2;
    }

    public void setTotNoOfEntriesInCentralDir(int i2) {
        this.totNoOfEntriesInCentralDir = i2;
    }

    public void setTotNoOfEntriesInCentralDirOnThisDisk(int i2) {
        this.totNoOfEntriesInCentralDirOnThisDisk = i2;
    }
}
