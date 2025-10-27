package net.lingala.zip4j.core;

import cn.hutool.core.text.StrPool;
import com.google.common.primitives.SignedBytes;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.DigitalSignature;
import net.lingala.zip4j.model.EndCentralDirRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
import net.lingala.zip4j.model.Zip64ExtendedInfo;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes9.dex */
public class HeaderReader {
    private RandomAccessFile zip4jRaf;
    private ZipModel zipModel;

    public HeaderReader(RandomAccessFile randomAccessFile) {
        this.zip4jRaf = randomAccessFile;
    }

    private byte[] getLongByteFromIntByte(byte[] bArr) throws ZipException {
        if (bArr == null) {
            throw new ZipException("input parameter is null, cannot expand to 8 bytes");
        }
        if (bArr.length == 4) {
            return new byte[]{bArr[0], bArr[1], bArr[2], bArr[3], 0, 0, 0, 0};
        }
        throw new ZipException("invalid byte length, cannot expand to 8 bytes");
    }

    private AESExtraDataRecord readAESExtraDataRecord(ArrayList arrayList) throws ZipException {
        if (arrayList == null) {
            return null;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ExtraDataRecord extraDataRecord = (ExtraDataRecord) arrayList.get(i2);
            if (extraDataRecord != null && extraDataRecord.getHeader() == 39169) {
                if (extraDataRecord.getData() == null) {
                    throw new ZipException("corrput AES extra data records");
                }
                AESExtraDataRecord aESExtraDataRecord = new AESExtraDataRecord();
                aESExtraDataRecord.setSignature(39169L);
                aESExtraDataRecord.setDataSize(extraDataRecord.getSizeOfData());
                byte[] data = extraDataRecord.getData();
                aESExtraDataRecord.setVersionNumber(Raw.readShortLittleEndian(data, 0));
                byte[] bArr = new byte[2];
                System.arraycopy(data, 2, bArr, 0, 2);
                aESExtraDataRecord.setVendorID(new String(bArr));
                aESExtraDataRecord.setAesStrength(data[4] & 255);
                aESExtraDataRecord.setCompressionMethod(Raw.readShortLittleEndian(data, 5));
                return aESExtraDataRecord;
            }
        }
        return null;
    }

    private void readAndSaveAESExtraDataRecord(FileHeader fileHeader) throws ZipException {
        AESExtraDataRecord aESExtraDataRecord;
        if (fileHeader == null) {
            throw new ZipException("file header is null in reading Zip64 Extended Info");
        }
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0 || (aESExtraDataRecord = readAESExtraDataRecord(fileHeader.getExtraDataRecords())) == null) {
            return;
        }
        fileHeader.setAesExtraDataRecord(aESExtraDataRecord);
        fileHeader.setEncryptionMethod(99);
    }

    private void readAndSaveExtraDataRecord(FileHeader fileHeader) throws ZipException {
        if (this.zip4jRaf == null) {
            throw new ZipException("invalid file handler when trying to read extra data record");
        }
        if (fileHeader == null) {
            throw new ZipException("file header is null");
        }
        int extraFieldLength = fileHeader.getExtraFieldLength();
        if (extraFieldLength <= 0) {
            return;
        }
        fileHeader.setExtraDataRecords(readExtraDataRecords(extraFieldLength));
    }

    private void readAndSaveZip64ExtendedInfo(FileHeader fileHeader) throws ZipException {
        Zip64ExtendedInfo zip64ExtendedInfo;
        if (fileHeader == null) {
            throw new ZipException("file header is null in reading Zip64 Extended Info");
        }
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0 || (zip64ExtendedInfo = readZip64ExtendedInfo(fileHeader.getExtraDataRecords(), fileHeader.getUncompressedSize(), fileHeader.getCompressedSize(), fileHeader.getOffsetLocalHeader(), fileHeader.getDiskNumberStart())) == null) {
            return;
        }
        fileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
        if (zip64ExtendedInfo.getUnCompressedSize() != -1) {
            fileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize());
        }
        if (zip64ExtendedInfo.getCompressedSize() != -1) {
            fileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
        }
        if (zip64ExtendedInfo.getOffsetLocalHeader() != -1) {
            fileHeader.setOffsetLocalHeader(zip64ExtendedInfo.getOffsetLocalHeader());
        }
        if (zip64ExtendedInfo.getDiskNumberStart() != -1) {
            fileHeader.setDiskNumberStart(zip64ExtendedInfo.getDiskNumberStart());
        }
    }

    private CentralDirectory readCentralDirectory() throws ZipException, IOException {
        if (this.zip4jRaf == null) {
            throw new ZipException("random access file was null", 3);
        }
        if (this.zipModel.getEndCentralDirRecord() == null) {
            throw new ZipException("EndCentralRecord was null, maybe a corrupt zip file");
        }
        try {
            CentralDirectory centralDirectory = new CentralDirectory();
            ArrayList arrayList = new ArrayList();
            EndCentralDirRecord endCentralDirRecord = this.zipModel.getEndCentralDirRecord();
            long offsetOfStartOfCentralDir = endCentralDirRecord.getOffsetOfStartOfCentralDir();
            int totNoOfEntriesInCentralDir = endCentralDirRecord.getTotNoOfEntriesInCentralDir();
            if (this.zipModel.isZip64Format()) {
                offsetOfStartOfCentralDir = this.zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo();
                totNoOfEntriesInCentralDir = (int) this.zipModel.getZip64EndCentralDirRecord().getTotNoOfEntriesInCentralDir();
            }
            this.zip4jRaf.seek(offsetOfStartOfCentralDir);
            byte[] bArr = new byte[4];
            byte[] bArr2 = new byte[2];
            for (int i2 = 0; i2 < totNoOfEntriesInCentralDir; i2++) {
                FileHeader fileHeader = new FileHeader();
                readIntoBuff(this.zip4jRaf, bArr);
                int intLittleEndian = Raw.readIntLittleEndian(bArr, 0);
                if (intLittleEndian != InternalZipConstants.CENSIG) {
                    throw new ZipException("Expected central directory entry not found (#" + (i2 + 1) + ")");
                }
                fileHeader.setSignature(intLittleEndian);
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setVersionMadeBy(Raw.readShortLittleEndian(bArr2, 0));
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(bArr2, 0));
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setFileNameUTF8Encoded((Raw.readShortLittleEndian(bArr2, 0) & 2048) != 0);
                byte b3 = bArr2[0];
                if ((b3 & 1) != 0) {
                    fileHeader.setEncrypted(true);
                }
                fileHeader.setGeneralPurposeFlag((byte[]) bArr2.clone());
                fileHeader.setDataDescriptorExists((b3 >> 3) == 1);
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setCompressionMethod(Raw.readShortLittleEndian(bArr2, 0));
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setLastModFileTime(Raw.readIntLittleEndian(bArr, 0));
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setCrc32(Raw.readIntLittleEndian(bArr, 0));
                fileHeader.setCrcBuff((byte[]) bArr.clone());
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setCompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr), 0));
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setUncompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr), 0));
                readIntoBuff(this.zip4jRaf, bArr2);
                int shortLittleEndian = Raw.readShortLittleEndian(bArr2, 0);
                fileHeader.setFileNameLength(shortLittleEndian);
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setExtraFieldLength(Raw.readShortLittleEndian(bArr2, 0));
                readIntoBuff(this.zip4jRaf, bArr2);
                int shortLittleEndian2 = Raw.readShortLittleEndian(bArr2, 0);
                fileHeader.setFileComment(new String(bArr2));
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setDiskNumberStart(Raw.readShortLittleEndian(bArr2, 0));
                readIntoBuff(this.zip4jRaf, bArr2);
                fileHeader.setInternalFileAttr((byte[]) bArr2.clone());
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setExternalFileAttr((byte[]) bArr.clone());
                readIntoBuff(this.zip4jRaf, bArr);
                fileHeader.setOffsetLocalHeader(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr), 0) & InternalZipConstants.ZIP_64_LIMIT);
                if (shortLittleEndian > 0) {
                    byte[] bArr3 = new byte[shortLittleEndian];
                    readIntoBuff(this.zip4jRaf, bArr3);
                    String str = Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getFileNameCharset()) ? new String(bArr3, this.zipModel.getFileNameCharset()) : Zip4jUtil.decodeFileName(bArr3, fileHeader.isFileNameUTF8Encoded());
                    if (str == null) {
                        throw new ZipException("fileName is null when reading central directory");
                    }
                    if (str.indexOf(":" + System.getProperty("file.separator")) >= 0) {
                        str = str.substring(str.indexOf(":" + System.getProperty("file.separator")) + 2);
                    }
                    fileHeader.setFileName(str);
                    fileHeader.setDirectory(str.endsWith("/") || str.endsWith(StrPool.BACKSLASH));
                } else {
                    fileHeader.setFileName(null);
                }
                readAndSaveExtraDataRecord(fileHeader);
                readAndSaveZip64ExtendedInfo(fileHeader);
                readAndSaveAESExtraDataRecord(fileHeader);
                if (shortLittleEndian2 > 0) {
                    byte[] bArr4 = new byte[shortLittleEndian2];
                    readIntoBuff(this.zip4jRaf, bArr4);
                    fileHeader.setFileComment(new String(bArr4));
                }
                arrayList.add(fileHeader);
            }
            centralDirectory.setFileHeaders(arrayList);
            DigitalSignature digitalSignature = new DigitalSignature();
            readIntoBuff(this.zip4jRaf, bArr);
            int intLittleEndian2 = Raw.readIntLittleEndian(bArr, 0);
            if (intLittleEndian2 != InternalZipConstants.DIGSIG) {
                return centralDirectory;
            }
            digitalSignature.setHeaderSignature(intLittleEndian2);
            readIntoBuff(this.zip4jRaf, bArr2);
            int shortLittleEndian3 = Raw.readShortLittleEndian(bArr2, 0);
            digitalSignature.setSizeOfData(shortLittleEndian3);
            if (shortLittleEndian3 > 0) {
                byte[] bArr5 = new byte[shortLittleEndian3];
                readIntoBuff(this.zip4jRaf, bArr5);
                digitalSignature.setSignatureData(new String(bArr5));
            }
            return centralDirectory;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private EndCentralDirRecord readEndOfCentralDirectoryRecord() throws ZipException, IOException {
        RandomAccessFile randomAccessFile = this.zip4jRaf;
        if (randomAccessFile == null) {
            throw new ZipException("random access file was null", 3);
        }
        try {
            byte[] bArr = new byte[4];
            long length = randomAccessFile.length() - 22;
            EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
            int i2 = 0;
            while (true) {
                long j2 = length - 1;
                this.zip4jRaf.seek(length);
                i2++;
                if (Raw.readLeInt(this.zip4jRaf, bArr) == InternalZipConstants.ENDSIG || i2 > 3000) {
                    break;
                }
                length = j2;
            }
            if (Raw.readIntLittleEndian(bArr, 0) != InternalZipConstants.ENDSIG) {
                throw new ZipException("zip headers not found. probably not a zip file");
            }
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[2];
            endCentralDirRecord.setSignature(InternalZipConstants.ENDSIG);
            readIntoBuff(this.zip4jRaf, bArr3);
            endCentralDirRecord.setNoOfThisDisk(Raw.readShortLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            endCentralDirRecord.setNoOfThisDiskStartOfCentralDir(Raw.readShortLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            endCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readShortLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            endCentralDirRecord.setTotNoOfEntriesInCentralDir(Raw.readShortLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            endCentralDirRecord.setSizeOfCentralDir(Raw.readIntLittleEndian(bArr2, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            endCentralDirRecord.setOffsetOfStartOfCentralDir(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr2), 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            int shortLittleEndian = Raw.readShortLittleEndian(bArr3, 0);
            endCentralDirRecord.setCommentLength(shortLittleEndian);
            if (shortLittleEndian > 0) {
                byte[] bArr4 = new byte[shortLittleEndian];
                readIntoBuff(this.zip4jRaf, bArr4);
                endCentralDirRecord.setComment(new String(bArr4));
                endCentralDirRecord.setCommentBytes(bArr4);
            } else {
                endCentralDirRecord.setComment(null);
            }
            if (endCentralDirRecord.getNoOfThisDisk() > 0) {
                this.zipModel.setSplitArchive(true);
            } else {
                this.zipModel.setSplitArchive(false);
            }
            return endCentralDirRecord;
        } catch (IOException e2) {
            throw new ZipException("Probably not a zip file or a corrupted zip file", e2, 4);
        }
    }

    private ArrayList readExtraDataRecords(int i2) throws ZipException, IOException {
        if (i2 <= 0) {
            return null;
        }
        try {
            byte[] bArr = new byte[i2];
            this.zip4jRaf.read(bArr);
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            while (i3 < i2) {
                ExtraDataRecord extraDataRecord = new ExtraDataRecord();
                extraDataRecord.setHeader(Raw.readShortLittleEndian(bArr, i3));
                int i4 = i3 + 2;
                int shortLittleEndian = Raw.readShortLittleEndian(bArr, i4);
                if (shortLittleEndian + 2 > i2) {
                    shortLittleEndian = Raw.readShortBigEndian(bArr, i4);
                    if (shortLittleEndian + 2 > i2) {
                        break;
                    }
                }
                extraDataRecord.setSizeOfData(shortLittleEndian);
                int i5 = i4 + 2;
                if (shortLittleEndian > 0) {
                    byte[] bArr2 = new byte[shortLittleEndian];
                    System.arraycopy(bArr, i5, bArr2, 0, shortLittleEndian);
                    extraDataRecord.setData(bArr2);
                }
                i3 = i5 + shortLittleEndian;
                arrayList.add(extraDataRecord);
            }
            if (arrayList.size() > 0) {
                return arrayList;
            }
            return null;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private byte[] readIntoBuff(RandomAccessFile randomAccessFile, byte[] bArr) throws ZipException {
        try {
            if (randomAccessFile.read(bArr, 0, bArr.length) != -1) {
                return bArr;
            }
            throw new ZipException("unexpected end of file when reading short buff");
        } catch (IOException e2) {
            throw new ZipException("IOException when reading short buff", e2);
        }
    }

    private Zip64EndCentralDirLocator readZip64EndCentralDirLocator() throws ZipException {
        if (this.zip4jRaf == null) {
            throw new ZipException("invalid file handler when trying to read Zip64EndCentralDirLocator");
        }
        try {
            Zip64EndCentralDirLocator zip64EndCentralDirLocator = new Zip64EndCentralDirLocator();
            setFilePointerToReadZip64EndCentralDirLoc();
            byte[] bArr = new byte[4];
            byte[] bArr2 = new byte[8];
            readIntoBuff(this.zip4jRaf, bArr);
            long intLittleEndian = Raw.readIntLittleEndian(bArr, 0);
            if (intLittleEndian != InternalZipConstants.ZIP64ENDCENDIRLOC) {
                this.zipModel.setZip64Format(false);
                return null;
            }
            this.zipModel.setZip64Format(true);
            zip64EndCentralDirLocator.setSignature(intLittleEndian);
            readIntoBuff(this.zip4jRaf, bArr);
            zip64EndCentralDirLocator.setNoOfDiskStartOfZip64EndOfCentralDirRec(Raw.readIntLittleEndian(bArr, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            zip64EndCentralDirLocator.setOffsetZip64EndOfCentralDirRec(Raw.readLongLittleEndian(bArr2, 0));
            readIntoBuff(this.zip4jRaf, bArr);
            zip64EndCentralDirLocator.setTotNumberOfDiscs(Raw.readIntLittleEndian(bArr, 0));
            return zip64EndCentralDirLocator;
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    private Zip64EndCentralDirRecord readZip64EndCentralDirRec() throws ZipException, IOException {
        if (this.zipModel.getZip64EndCentralDirLocator() == null) {
            throw new ZipException("invalid zip64 end of central directory locator");
        }
        long offsetZip64EndOfCentralDirRec = this.zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec();
        if (offsetZip64EndOfCentralDirRec < 0) {
            throw new ZipException("invalid offset for start of end of central directory record");
        }
        try {
            this.zip4jRaf.seek(offsetZip64EndOfCentralDirRec);
            Zip64EndCentralDirRecord zip64EndCentralDirRecord = new Zip64EndCentralDirRecord();
            byte[] bArr = new byte[2];
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[8];
            readIntoBuff(this.zip4jRaf, bArr2);
            long intLittleEndian = Raw.readIntLittleEndian(bArr2, 0);
            if (intLittleEndian != InternalZipConstants.ZIP64ENDCENDIRREC) {
                throw new ZipException("invalid signature for zip64 end of central directory record");
            }
            zip64EndCentralDirRecord.setSignature(intLittleEndian);
            readIntoBuff(this.zip4jRaf, bArr3);
            zip64EndCentralDirRecord.setSizeOfZip64EndCentralDirRec(Raw.readLongLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr);
            zip64EndCentralDirRecord.setVersionMadeBy(Raw.readShortLittleEndian(bArr, 0));
            readIntoBuff(this.zip4jRaf, bArr);
            zip64EndCentralDirRecord.setVersionNeededToExtract(Raw.readShortLittleEndian(bArr, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            zip64EndCentralDirRecord.setNoOfThisDisk(Raw.readIntLittleEndian(bArr2, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            zip64EndCentralDirRecord.setNoOfThisDiskStartOfCentralDir(Raw.readIntLittleEndian(bArr2, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            zip64EndCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readLongLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            zip64EndCentralDirRecord.setTotNoOfEntriesInCentralDir(Raw.readLongLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            zip64EndCentralDirRecord.setSizeOfCentralDir(Raw.readLongLittleEndian(bArr3, 0));
            readIntoBuff(this.zip4jRaf, bArr3);
            zip64EndCentralDirRecord.setOffsetStartCenDirWRTStartDiskNo(Raw.readLongLittleEndian(bArr3, 0));
            long sizeOfZip64EndCentralDirRec = zip64EndCentralDirRecord.getSizeOfZip64EndCentralDirRec() - 44;
            if (sizeOfZip64EndCentralDirRec > 0) {
                byte[] bArr4 = new byte[(int) sizeOfZip64EndCentralDirRec];
                readIntoBuff(this.zip4jRaf, bArr4);
                zip64EndCentralDirRecord.setExtensibleDataSector(bArr4);
            }
            return zip64EndCentralDirRecord;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private Zip64ExtendedInfo readZip64ExtendedInfo(ArrayList arrayList, long j2, long j3, long j4, int i2) throws ZipException {
        int i3;
        boolean z2;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            ExtraDataRecord extraDataRecord = (ExtraDataRecord) arrayList.get(i4);
            if (extraDataRecord != null && extraDataRecord.getHeader() == 1) {
                Zip64ExtendedInfo zip64ExtendedInfo = new Zip64ExtendedInfo();
                byte[] data = extraDataRecord.getData();
                if (extraDataRecord.getSizeOfData() <= 0) {
                    return null;
                }
                byte[] bArr = new byte[8];
                byte[] bArr2 = new byte[4];
                boolean z3 = true;
                if ((j2 & WebSocketProtocol.PAYLOAD_SHORT_MAX) != WebSocketProtocol.PAYLOAD_SHORT_MAX || extraDataRecord.getSizeOfData() <= 0) {
                    i3 = 0;
                    z2 = false;
                } else {
                    System.arraycopy(data, 0, bArr, 0, 8);
                    zip64ExtendedInfo.setUnCompressedSize(Raw.readLongLittleEndian(bArr, 0));
                    i3 = 8;
                    z2 = true;
                }
                if ((j3 & WebSocketProtocol.PAYLOAD_SHORT_MAX) == WebSocketProtocol.PAYLOAD_SHORT_MAX && i3 < extraDataRecord.getSizeOfData()) {
                    System.arraycopy(data, i3, bArr, 0, 8);
                    zip64ExtendedInfo.setCompressedSize(Raw.readLongLittleEndian(bArr, 0));
                    i3 += 8;
                    z2 = true;
                }
                if ((j4 & WebSocketProtocol.PAYLOAD_SHORT_MAX) == WebSocketProtocol.PAYLOAD_SHORT_MAX && i3 < extraDataRecord.getSizeOfData()) {
                    System.arraycopy(data, i3, bArr, 0, 8);
                    zip64ExtendedInfo.setOffsetLocalHeader(Raw.readLongLittleEndian(bArr, 0));
                    i3 += 8;
                    z2 = true;
                }
                if ((i2 & 65535) != 65535 || i3 >= extraDataRecord.getSizeOfData()) {
                    z3 = z2;
                } else {
                    System.arraycopy(data, i3, bArr2, 0, 4);
                    zip64ExtendedInfo.setDiskNumberStart(Raw.readIntLittleEndian(bArr2, 0));
                }
                if (z3) {
                    return zip64ExtendedInfo;
                }
                return null;
            }
        }
        return null;
    }

    private void setFilePointerToReadZip64EndCentralDirLoc() throws ZipException, IOException {
        try {
            byte[] bArr = new byte[4];
            long length = this.zip4jRaf.length() - 22;
            while (true) {
                long j2 = length - 1;
                this.zip4jRaf.seek(length);
                if (Raw.readLeInt(this.zip4jRaf, bArr) == InternalZipConstants.ENDSIG) {
                    RandomAccessFile randomAccessFile = this.zip4jRaf;
                    randomAccessFile.seek(((((randomAccessFile.getFilePointer() - 4) - 4) - 8) - 4) - 4);
                    return;
                }
                length = j2;
            }
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    public ZipModel readAllHeaders() throws ZipException {
        return readAllHeaders(null);
    }

    public LocalFileHeader readLocalFileHeader(FileHeader fileHeader) throws ZipException, IOException {
        if (fileHeader == null || this.zip4jRaf == null) {
            throw new ZipException("invalid read parameters for local header");
        }
        long offsetLocalHeader = fileHeader.getOffsetLocalHeader();
        if (fileHeader.getZip64ExtendedInfo() != null && fileHeader.getZip64ExtendedInfo().getOffsetLocalHeader() > 0) {
            offsetLocalHeader = fileHeader.getOffsetLocalHeader();
        }
        if (offsetLocalHeader < 0) {
            throw new ZipException("invalid local header offset");
        }
        try {
            this.zip4jRaf.seek(offsetLocalHeader);
            LocalFileHeader localFileHeader = new LocalFileHeader();
            byte[] bArr = new byte[2];
            byte[] bArr2 = new byte[4];
            readIntoBuff(this.zip4jRaf, bArr2);
            int intLittleEndian = Raw.readIntLittleEndian(bArr2, 0);
            if (intLittleEndian != InternalZipConstants.LOCSIG) {
                throw new ZipException("invalid local header signature for file: " + fileHeader.getFileName());
            }
            localFileHeader.setSignature(intLittleEndian);
            readIntoBuff(this.zip4jRaf, bArr);
            localFileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(bArr, 0));
            readIntoBuff(this.zip4jRaf, bArr);
            localFileHeader.setFileNameUTF8Encoded((Raw.readShortLittleEndian(bArr, 0) & 2048) != 0);
            byte b3 = bArr[0];
            if ((b3 & 1) != 0) {
                localFileHeader.setEncrypted(true);
            }
            localFileHeader.setGeneralPurposeFlag(bArr);
            String binaryString = Integer.toBinaryString(b3);
            if (binaryString.length() >= 4) {
                localFileHeader.setDataDescriptorExists(binaryString.charAt(3) == '1');
            }
            readIntoBuff(this.zip4jRaf, bArr);
            localFileHeader.setCompressionMethod(Raw.readShortLittleEndian(bArr, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            localFileHeader.setLastModFileTime(Raw.readIntLittleEndian(bArr2, 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            localFileHeader.setCrc32(Raw.readIntLittleEndian(bArr2, 0));
            localFileHeader.setCrcBuff((byte[]) bArr2.clone());
            readIntoBuff(this.zip4jRaf, bArr2);
            localFileHeader.setCompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr2), 0));
            readIntoBuff(this.zip4jRaf, bArr2);
            localFileHeader.setUncompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(bArr2), 0));
            readIntoBuff(this.zip4jRaf, bArr);
            int shortLittleEndian = Raw.readShortLittleEndian(bArr, 0);
            localFileHeader.setFileNameLength(shortLittleEndian);
            readIntoBuff(this.zip4jRaf, bArr);
            localFileHeader.setExtraFieldLength(Raw.readShortLittleEndian(bArr, 0));
            int i2 = 30;
            if (shortLittleEndian > 0) {
                byte[] bArr3 = new byte[shortLittleEndian];
                readIntoBuff(this.zip4jRaf, bArr3);
                String strDecodeFileName = Zip4jUtil.decodeFileName(bArr3, localFileHeader.isFileNameUTF8Encoded());
                if (strDecodeFileName == null) {
                    throw new ZipException("file name is null, cannot assign file name to local file header");
                }
                if (strDecodeFileName.indexOf(":" + System.getProperty("file.separator")) >= 0) {
                    strDecodeFileName = strDecodeFileName.substring(strDecodeFileName.indexOf(":" + System.getProperty("file.separator")) + 2);
                }
                localFileHeader.setFileName(strDecodeFileName);
                i2 = 30 + shortLittleEndian;
            } else {
                localFileHeader.setFileName(null);
            }
            readAndSaveExtraDataRecord(localFileHeader);
            localFileHeader.setOffsetStartOfData(offsetLocalHeader + i2 + r7);
            localFileHeader.setPassword(fileHeader.getPassword());
            readAndSaveZip64ExtendedInfo(localFileHeader);
            readAndSaveAESExtraDataRecord(localFileHeader);
            if (localFileHeader.isEncrypted() && localFileHeader.getEncryptionMethod() != 99) {
                if ((b3 & SignedBytes.MAX_POWER_OF_TWO) == 64) {
                    localFileHeader.setEncryptionMethod(1);
                } else {
                    localFileHeader.setEncryptionMethod(0);
                }
            }
            if (localFileHeader.getCrc32() <= 0) {
                localFileHeader.setCrc32(fileHeader.getCrc32());
                localFileHeader.setCrcBuff(fileHeader.getCrcBuff());
            }
            if (localFileHeader.getCompressedSize() <= 0) {
                localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
            }
            if (localFileHeader.getUncompressedSize() <= 0) {
                localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
            }
            return localFileHeader;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    public ZipModel readAllHeaders(String str) throws ZipException {
        ZipModel zipModel = new ZipModel();
        this.zipModel = zipModel;
        zipModel.setFileNameCharset(str);
        this.zipModel.setEndCentralDirRecord(readEndOfCentralDirectoryRecord());
        this.zipModel.setZip64EndCentralDirLocator(readZip64EndCentralDirLocator());
        if (this.zipModel.isZip64Format()) {
            this.zipModel.setZip64EndCentralDirRecord(readZip64EndCentralDirRec());
            if (this.zipModel.getZip64EndCentralDirRecord() == null || this.zipModel.getZip64EndCentralDirRecord().getNoOfThisDisk() <= 0) {
                this.zipModel.setSplitArchive(false);
            } else {
                this.zipModel.setSplitArchive(true);
            }
        }
        this.zipModel.setCentralDirectory(readCentralDirectory());
        return this.zipModel;
    }

    private void readAndSaveAESExtraDataRecord(LocalFileHeader localFileHeader) throws ZipException {
        AESExtraDataRecord aESExtraDataRecord;
        if (localFileHeader != null) {
            if (localFileHeader.getExtraDataRecords() == null || localFileHeader.getExtraDataRecords().size() <= 0 || (aESExtraDataRecord = readAESExtraDataRecord(localFileHeader.getExtraDataRecords())) == null) {
                return;
            }
            localFileHeader.setAesExtraDataRecord(aESExtraDataRecord);
            localFileHeader.setEncryptionMethod(99);
            return;
        }
        throw new ZipException("file header is null in reading Zip64 Extended Info");
    }

    private void readAndSaveExtraDataRecord(LocalFileHeader localFileHeader) throws ZipException {
        if (this.zip4jRaf == null) {
            throw new ZipException("invalid file handler when trying to read extra data record");
        }
        if (localFileHeader != null) {
            int extraFieldLength = localFileHeader.getExtraFieldLength();
            if (extraFieldLength <= 0) {
                return;
            }
            localFileHeader.setExtraDataRecords(readExtraDataRecords(extraFieldLength));
            return;
        }
        throw new ZipException("file header is null");
    }

    private void readAndSaveZip64ExtendedInfo(LocalFileHeader localFileHeader) throws ZipException {
        Zip64ExtendedInfo zip64ExtendedInfo;
        if (localFileHeader != null) {
            if (localFileHeader.getExtraDataRecords() == null || localFileHeader.getExtraDataRecords().size() <= 0 || (zip64ExtendedInfo = readZip64ExtendedInfo(localFileHeader.getExtraDataRecords(), localFileHeader.getUncompressedSize(), localFileHeader.getCompressedSize(), -1L, -1)) == null) {
                return;
            }
            localFileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
            if (zip64ExtendedInfo.getUnCompressedSize() != -1) {
                localFileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize());
            }
            if (zip64ExtendedInfo.getCompressedSize() != -1) {
                localFileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
                return;
            }
            return;
        }
        throw new ZipException("file header is null in reading Zip64 Extended Info");
    }
}
