package net.lingala.zip4j.model;

import java.util.ArrayList;

/* loaded from: classes9.dex */
public class LocalFileHeader {
    private AESExtraDataRecord aesExtraDataRecord;
    private long compressedSize;
    private int compressionMethod;
    private byte[] crcBuff;
    private boolean dataDescriptorExists;
    private ArrayList extraDataRecords;
    private byte[] extraField;
    private int extraFieldLength;
    private String fileName;
    private int fileNameLength;
    private boolean fileNameUTF8Encoded;
    private byte[] generalPurposeFlag;
    private boolean isEncrypted;
    private int lastModFileTime;
    private long offsetStartOfData;
    private char[] password;
    private int signature;
    private int versionNeededToExtract;
    private Zip64ExtendedInfo zip64ExtendedInfo;
    private int encryptionMethod = -1;
    private boolean writeComprSizeInZip64ExtraRecord = false;
    private long crc32 = 0;
    private long uncompressedSize = 0;

    public AESExtraDataRecord getAesExtraDataRecord() {
        return this.aesExtraDataRecord;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public int getCompressionMethod() {
        return this.compressionMethod;
    }

    public long getCrc32() {
        return this.crc32;
    }

    public byte[] getCrcBuff() {
        return this.crcBuff;
    }

    public int getEncryptionMethod() {
        return this.encryptionMethod;
    }

    public ArrayList getExtraDataRecords() {
        return this.extraDataRecords;
    }

    public byte[] getExtraField() {
        return this.extraField;
    }

    public int getExtraFieldLength() {
        return this.extraFieldLength;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getFileNameLength() {
        return this.fileNameLength;
    }

    public byte[] getGeneralPurposeFlag() {
        return this.generalPurposeFlag;
    }

    public int getLastModFileTime() {
        return this.lastModFileTime;
    }

    public long getOffsetStartOfData() {
        return this.offsetStartOfData;
    }

    public char[] getPassword() {
        return this.password;
    }

    public int getSignature() {
        return this.signature;
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public int getVersionNeededToExtract() {
        return this.versionNeededToExtract;
    }

    public Zip64ExtendedInfo getZip64ExtendedInfo() {
        return this.zip64ExtendedInfo;
    }

    public boolean isDataDescriptorExists() {
        return this.dataDescriptorExists;
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public boolean isFileNameUTF8Encoded() {
        return this.fileNameUTF8Encoded;
    }

    public boolean isWriteComprSizeInZip64ExtraRecord() {
        return this.writeComprSizeInZip64ExtraRecord;
    }

    public void setAesExtraDataRecord(AESExtraDataRecord aESExtraDataRecord) {
        this.aesExtraDataRecord = aESExtraDataRecord;
    }

    public void setCompressedSize(long j2) {
        this.compressedSize = j2;
    }

    public void setCompressionMethod(int i2) {
        this.compressionMethod = i2;
    }

    public void setCrc32(long j2) {
        this.crc32 = j2;
    }

    public void setCrcBuff(byte[] bArr) {
        this.crcBuff = bArr;
    }

    public void setDataDescriptorExists(boolean z2) {
        this.dataDescriptorExists = z2;
    }

    public void setEncrypted(boolean z2) {
        this.isEncrypted = z2;
    }

    public void setEncryptionMethod(int i2) {
        this.encryptionMethod = i2;
    }

    public void setExtraDataRecords(ArrayList arrayList) {
        this.extraDataRecords = arrayList;
    }

    public void setExtraField(byte[] bArr) {
        this.extraField = bArr;
    }

    public void setExtraFieldLength(int i2) {
        this.extraFieldLength = i2;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setFileNameLength(int i2) {
        this.fileNameLength = i2;
    }

    public void setFileNameUTF8Encoded(boolean z2) {
        this.fileNameUTF8Encoded = z2;
    }

    public void setGeneralPurposeFlag(byte[] bArr) {
        this.generalPurposeFlag = bArr;
    }

    public void setLastModFileTime(int i2) {
        this.lastModFileTime = i2;
    }

    public void setOffsetStartOfData(long j2) {
        this.offsetStartOfData = j2;
    }

    public void setPassword(char[] cArr) {
        this.password = cArr;
    }

    public void setSignature(int i2) {
        this.signature = i2;
    }

    public void setUncompressedSize(long j2) {
        this.uncompressedSize = j2;
    }

    public void setVersionNeededToExtract(int i2) {
        this.versionNeededToExtract = i2;
    }

    public void setWriteComprSizeInZip64ExtraRecord(boolean z2) {
        this.writeComprSizeInZip64ExtraRecord = z2;
    }

    public void setZip64ExtendedInfo(Zip64ExtendedInfo zip64ExtendedInfo) {
        this.zip64ExtendedInfo = zip64ExtendedInfo;
    }
}
