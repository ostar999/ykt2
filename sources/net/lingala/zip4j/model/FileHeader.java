package net.lingala.zip4j.model;

import java.util.ArrayList;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.unzip.Unzip;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class FileHeader {
    private AESExtraDataRecord aesExtraDataRecord;
    private long compressedSize;
    private int compressionMethod;
    private byte[] crcBuff;
    private boolean dataDescriptorExists;
    private int diskNumberStart;
    private byte[] externalFileAttr;
    private ArrayList extraDataRecords;
    private int extraFieldLength;
    private String fileComment;
    private int fileCommentLength;
    private String fileName;
    private int fileNameLength;
    private boolean fileNameUTF8Encoded;
    private byte[] generalPurposeFlag;
    private byte[] internalFileAttr;
    private boolean isDirectory;
    private boolean isEncrypted;
    private int lastModFileTime;
    private long offsetLocalHeader;
    private char[] password;
    private int signature;
    private int versionMadeBy;
    private int versionNeededToExtract;
    private Zip64ExtendedInfo zip64ExtendedInfo;
    private int encryptionMethod = -1;
    private long crc32 = 0;
    private long uncompressedSize = 0;

    public void extractFile(ZipModel zipModel, String str, ProgressMonitor progressMonitor, boolean z2) throws ZipException {
        extractFile(zipModel, str, null, progressMonitor, z2);
    }

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
        return this.crc32 & InternalZipConstants.ZIP_64_LIMIT;
    }

    public byte[] getCrcBuff() {
        return this.crcBuff;
    }

    public int getDiskNumberStart() {
        return this.diskNumberStart;
    }

    public int getEncryptionMethod() {
        return this.encryptionMethod;
    }

    public byte[] getExternalFileAttr() {
        return this.externalFileAttr;
    }

    public ArrayList getExtraDataRecords() {
        return this.extraDataRecords;
    }

    public int getExtraFieldLength() {
        return this.extraFieldLength;
    }

    public String getFileComment() {
        return this.fileComment;
    }

    public int getFileCommentLength() {
        return this.fileCommentLength;
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

    public byte[] getInternalFileAttr() {
        return this.internalFileAttr;
    }

    public int getLastModFileTime() {
        return this.lastModFileTime;
    }

    public long getOffsetLocalHeader() {
        return this.offsetLocalHeader;
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

    public int getVersionMadeBy() {
        return this.versionMadeBy;
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

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public boolean isFileNameUTF8Encoded() {
        return this.fileNameUTF8Encoded;
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

    public void setDirectory(boolean z2) {
        this.isDirectory = z2;
    }

    public void setDiskNumberStart(int i2) {
        this.diskNumberStart = i2;
    }

    public void setEncrypted(boolean z2) {
        this.isEncrypted = z2;
    }

    public void setEncryptionMethod(int i2) {
        this.encryptionMethod = i2;
    }

    public void setExternalFileAttr(byte[] bArr) {
        this.externalFileAttr = bArr;
    }

    public void setExtraDataRecords(ArrayList arrayList) {
        this.extraDataRecords = arrayList;
    }

    public void setExtraFieldLength(int i2) {
        this.extraFieldLength = i2;
    }

    public void setFileComment(String str) {
        this.fileComment = str;
    }

    public void setFileCommentLength(int i2) {
        this.fileCommentLength = i2;
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

    public void setInternalFileAttr(byte[] bArr) {
        this.internalFileAttr = bArr;
    }

    public void setLastModFileTime(int i2) {
        this.lastModFileTime = i2;
    }

    public void setOffsetLocalHeader(long j2) {
        this.offsetLocalHeader = j2;
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

    public void setVersionMadeBy(int i2) {
        this.versionMadeBy = i2;
    }

    public void setVersionNeededToExtract(int i2) {
        this.versionNeededToExtract = i2;
    }

    public void setZip64ExtendedInfo(Zip64ExtendedInfo zip64ExtendedInfo) {
        this.zip64ExtendedInfo = zip64ExtendedInfo;
    }

    public void extractFile(ZipModel zipModel, String str, UnzipParameters unzipParameters, ProgressMonitor progressMonitor, boolean z2) throws ZipException {
        extractFile(zipModel, str, unzipParameters, null, progressMonitor, z2);
    }

    public void extractFile(ZipModel zipModel, String str, UnzipParameters unzipParameters, String str2, ProgressMonitor progressMonitor, boolean z2) throws ZipException {
        if (zipModel != null) {
            if (Zip4jUtil.checkOutputFolder(str)) {
                new Unzip(zipModel).extractFile(this, str, unzipParameters, str2, progressMonitor, z2);
                return;
            }
            throw new ZipException("Invalid output path");
        }
        throw new ZipException("input zipModel is null");
    }
}
