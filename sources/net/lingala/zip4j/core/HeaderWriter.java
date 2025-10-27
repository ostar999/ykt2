package net.lingala.zip4j.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.SplitOutputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class HeaderWriter {
    private final int ZIP64_EXTRA_BUF = 50;

    private byte[] byteArrayListToByteArray(List list) throws ZipException {
        if (list == null) {
            throw new ZipException("input byte array list is null, cannot conver to byte array");
        }
        if (list.size() <= 0) {
            return null;
        }
        byte[] bArr = new byte[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            bArr[i2] = Byte.parseByte((String) list.get(i2));
        }
        return bArr;
    }

    private void copyByteArrayToArrayList(byte[] bArr, List list) throws ZipException {
        if (list == null || bArr == null) {
            throw new ZipException("one of the input parameters is null, cannot copy byte array to array list");
        }
        for (byte b3 : bArr) {
            list.add(Byte.toString(b3));
        }
    }

    private int countNumberOfFileHeaderEntriesOnDisk(ArrayList arrayList, int i2) throws ZipException {
        if (arrayList == null) {
            throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
        }
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            if (((FileHeader) arrayList.get(i4)).getDiskNumberStart() == i2) {
                i3++;
            }
        }
        return i3;
    }

    private void processHeaderData(ZipModel zipModel, OutputStream outputStream) throws ZipException {
        int currSplitFileCounter;
        try {
            if (outputStream instanceof SplitOutputStream) {
                zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(((SplitOutputStream) outputStream).getFilePointer());
                currSplitFileCounter = ((SplitOutputStream) outputStream).getCurrSplitFileCounter();
            } else {
                currSplitFileCounter = 0;
            }
            if (zipModel.isZip64Format()) {
                if (zipModel.getZip64EndCentralDirRecord() == null) {
                    zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
                }
                if (zipModel.getZip64EndCentralDirLocator() == null) {
                    zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
                }
                zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(currSplitFileCounter);
                zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(currSplitFileCounter + 1);
            }
            zipModel.getEndCentralDirRecord().setNoOfThisDisk(currSplitFileCounter);
            zipModel.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(currSplitFileCounter);
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private void updateCompressedSizeInLocalFileHeader(SplitOutputStream splitOutputStream, LocalFileHeader localFileHeader, long j2, long j3, byte[] bArr, boolean z2) throws ZipException {
        if (splitOutputStream == null) {
            throw new ZipException("invalid output stream, cannot update compressed size for local file header");
        }
        try {
            if (!localFileHeader.isWriteComprSizeInZip64ExtraRecord()) {
                splitOutputStream.seek(j2 + j3);
                splitOutputStream.write(bArr);
            } else {
                if (bArr.length != 8) {
                    throw new ZipException("attempting to write a non 8-byte compressed size block for a zip64 file");
                }
                long fileNameLength = j2 + j3 + 4 + 4 + 2 + 2 + localFileHeader.getFileNameLength() + 2 + 2 + 8;
                if (j3 == 22) {
                    fileNameLength += 8;
                }
                splitOutputStream.seek(fileNameLength);
                splitOutputStream.write(bArr);
            }
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private int writeCentralDirectory(ZipModel zipModel, OutputStream outputStream, List list) throws ZipException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot write central directory");
        }
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return 0;
        }
        int iWriteFileHeader = 0;
        for (int i2 = 0; i2 < zipModel.getCentralDirectory().getFileHeaders().size(); i2++) {
            iWriteFileHeader += writeFileHeader(zipModel, (FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(i2), outputStream, list);
        }
        return iWriteFileHeader;
    }

    private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, OutputStream outputStream, int i2, long j2, List list) throws ZipException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("zip model or output stream is null, cannot write end of central directory record");
        }
        try {
            byte[] bArr = new byte[2];
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[8];
            Raw.writeIntLittleEndian(bArr2, 0, (int) zipModel.getEndCentralDirRecord().getSignature());
            copyByteArrayToArrayList(bArr2, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) zipModel.getEndCentralDirRecord().getNoOfThisDisk());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) zipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
            copyByteArrayToArrayList(bArr, list);
            if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null) {
                throw new ZipException("invalid central directory/file headers, cannot write end of central directory record");
            }
            int size = zipModel.getCentralDirectory().getFileHeaders().size();
            Raw.writeShortLittleEndian(bArr, 0, (short) (zipModel.isSplitArchive() ? countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndCentralDirRecord().getNoOfThisDisk()) : size));
            copyByteArrayToArrayList(bArr, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) size);
            copyByteArrayToArrayList(bArr, list);
            Raw.writeIntLittleEndian(bArr2, 0, i2);
            copyByteArrayToArrayList(bArr2, list);
            if (j2 > InternalZipConstants.ZIP_64_LIMIT) {
                Raw.writeLongLittleEndian(bArr3, 0, InternalZipConstants.ZIP_64_LIMIT);
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, list);
            } else {
                Raw.writeLongLittleEndian(bArr3, 0, j2);
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, list);
            }
            int commentLength = zipModel.getEndCentralDirRecord().getComment() != null ? zipModel.getEndCentralDirRecord().getCommentLength() : 0;
            Raw.writeShortLittleEndian(bArr, 0, (short) commentLength);
            copyByteArrayToArrayList(bArr, list);
            if (commentLength > 0) {
                copyByteArrayToArrayList(zipModel.getEndCentralDirRecord().getCommentBytes(), list);
            }
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    private int writeFileHeader(ZipModel zipModel, FileHeader fileHeader, OutputStream outputStream, List list) throws ZipException, UnsupportedEncodingException {
        boolean z2;
        boolean z3;
        int i2;
        int encodedStringLength;
        if (fileHeader == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot write local file header");
        }
        try {
            byte[] bArr = new byte[2];
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[8];
            byte[] bArr4 = {0, 0};
            byte[] bArr5 = {0, 0, 0, 0};
            Raw.writeIntLittleEndian(bArr2, 0, fileHeader.getSignature());
            copyByteArrayToArrayList(bArr2, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) fileHeader.getVersionMadeBy());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) fileHeader.getVersionNeededToExtract());
            copyByteArrayToArrayList(bArr, list);
            copyByteArrayToArrayList(fileHeader.getGeneralPurposeFlag(), list);
            Raw.writeShortLittleEndian(bArr, 0, (short) fileHeader.getCompressionMethod());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeIntLittleEndian(bArr2, 0, fileHeader.getLastModFileTime());
            copyByteArrayToArrayList(bArr2, list);
            Raw.writeIntLittleEndian(bArr2, 0, (int) fileHeader.getCrc32());
            copyByteArrayToArrayList(bArr2, list);
            if (fileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT || fileHeader.getUncompressedSize() + 50 >= InternalZipConstants.ZIP_64_LIMIT) {
                Raw.writeLongLittleEndian(bArr3, 0, InternalZipConstants.ZIP_64_LIMIT);
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, list);
                copyByteArrayToArrayList(bArr2, list);
                z2 = true;
            } else {
                Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getCompressedSize());
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, list);
                Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getUncompressedSize());
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, list);
                z2 = false;
            }
            Raw.writeShortLittleEndian(bArr, 0, (short) fileHeader.getFileNameLength());
            copyByteArrayToArrayList(bArr, list);
            byte[] bArr6 = new byte[4];
            if (fileHeader.getOffsetLocalHeader() > InternalZipConstants.ZIP_64_LIMIT) {
                Raw.writeLongLittleEndian(bArr3, 0, InternalZipConstants.ZIP_64_LIMIT);
                System.arraycopy(bArr3, 0, bArr6, 0, 4);
                z3 = true;
            } else {
                Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getOffsetLocalHeader());
                System.arraycopy(bArr3, 0, bArr6, 0, 4);
                z3 = false;
            }
            if (z2 || z3) {
                i2 = z2 ? 20 : 4;
                if (z3) {
                    i2 += 8;
                }
            } else {
                i2 = 0;
            }
            if (fileHeader.getAesExtraDataRecord() != null) {
                i2 += 11;
            }
            Raw.writeShortLittleEndian(bArr, 0, (short) i2);
            copyByteArrayToArrayList(bArr, list);
            copyByteArrayToArrayList(bArr4, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) fileHeader.getDiskNumberStart());
            copyByteArrayToArrayList(bArr, list);
            copyByteArrayToArrayList(bArr4, list);
            if (fileHeader.getExternalFileAttr() != null) {
                copyByteArrayToArrayList(fileHeader.getExternalFileAttr(), list);
            } else {
                copyByteArrayToArrayList(bArr5, list);
            }
            copyByteArrayToArrayList(bArr6, list);
            if (Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getFileNameCharset())) {
                byte[] bytes = fileHeader.getFileName().getBytes(zipModel.getFileNameCharset());
                copyByteArrayToArrayList(bytes, list);
                encodedStringLength = bytes.length;
            } else {
                copyByteArrayToArrayList(Zip4jUtil.convertCharset(fileHeader.getFileName()), list);
                encodedStringLength = Zip4jUtil.getEncodedStringLength(fileHeader.getFileName());
            }
            int i3 = 46 + encodedStringLength;
            if (z2 || z3) {
                zipModel.setZip64Format(true);
                Raw.writeShortLittleEndian(bArr, 0, (short) 1);
                copyByteArrayToArrayList(bArr, list);
                int i4 = i3 + 2;
                int i5 = z2 ? 16 : 0;
                if (z3) {
                    i5 += 8;
                }
                Raw.writeShortLittleEndian(bArr, 0, (short) i5);
                copyByteArrayToArrayList(bArr, list);
                i3 = i4 + 2;
                if (z2) {
                    Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getUncompressedSize());
                    copyByteArrayToArrayList(bArr3, list);
                    Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getCompressedSize());
                    copyByteArrayToArrayList(bArr3, list);
                    i3 = i3 + 8 + 8;
                }
                if (z3) {
                    Raw.writeLongLittleEndian(bArr3, 0, fileHeader.getOffsetLocalHeader());
                    copyByteArrayToArrayList(bArr3, list);
                    i3 += 8;
                }
            }
            if (fileHeader.getAesExtraDataRecord() == null) {
                return i3;
            }
            AESExtraDataRecord aesExtraDataRecord = fileHeader.getAesExtraDataRecord();
            Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getSignature());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getDataSize());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getVersionNumber());
            copyByteArrayToArrayList(bArr, list);
            copyByteArrayToArrayList(aesExtraDataRecord.getVendorID().getBytes(), list);
            copyByteArrayToArrayList(new byte[]{(byte) aesExtraDataRecord.getAesStrength()}, list);
            Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getCompressionMethod());
            copyByteArrayToArrayList(bArr, list);
            return i3 + 11;
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    private void writeZip64EndOfCentralDirectoryLocator(ZipModel zipModel, OutputStream outputStream, List list) throws ZipException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory locator");
        }
        try {
            byte[] bArr = new byte[4];
            byte[] bArr2 = new byte[8];
            Raw.writeIntLittleEndian(bArr, 0, 117853008);
            copyByteArrayToArrayList(bArr, list);
            Raw.writeIntLittleEndian(bArr, 0, zipModel.getZip64EndCentralDirLocator().getNoOfDiskStartOfZip64EndOfCentralDirRec());
            copyByteArrayToArrayList(bArr, list);
            Raw.writeLongLittleEndian(bArr2, 0, zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec());
            copyByteArrayToArrayList(bArr2, list);
            Raw.writeIntLittleEndian(bArr, 0, zipModel.getZip64EndCentralDirLocator().getTotNumberOfDiscs());
            copyByteArrayToArrayList(bArr, list);
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void writeZip64EndOfCentralDirectoryRecord(ZipModel zipModel, OutputStream outputStream, int i2, long j2, List list) throws ZipException {
        int i3;
        if (zipModel == null || outputStream == null) {
            throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory record");
        }
        try {
            byte[] bArr = new byte[2];
            byte[] bArr2 = {0, 0};
            byte[] bArr3 = new byte[4];
            byte[] bArr4 = new byte[8];
            Raw.writeIntLittleEndian(bArr3, 0, 101075792);
            copyByteArrayToArrayList(bArr3, list);
            Raw.writeLongLittleEndian(bArr4, 0, 44L);
            copyByteArrayToArrayList(bArr4, list);
            if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
                copyByteArrayToArrayList(bArr2, list);
                copyByteArrayToArrayList(bArr2, list);
            } else {
                Raw.writeShortLittleEndian(bArr, 0, (short) ((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionMadeBy());
                copyByteArrayToArrayList(bArr, list);
                Raw.writeShortLittleEndian(bArr, 0, (short) ((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionNeededToExtract());
                copyByteArrayToArrayList(bArr, list);
            }
            Raw.writeIntLittleEndian(bArr3, 0, zipModel.getEndCentralDirRecord().getNoOfThisDisk());
            copyByteArrayToArrayList(bArr3, list);
            Raw.writeIntLittleEndian(bArr3, 0, zipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
            copyByteArrayToArrayList(bArr3, list);
            if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null) {
                throw new ZipException("invalid central directory/file headers, cannot write end of central directory record");
            }
            int size = zipModel.getCentralDirectory().getFileHeaders().size();
            if (zipModel.isSplitArchive()) {
                countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndCentralDirRecord().getNoOfThisDisk());
                i3 = 0;
            } else {
                i3 = size;
            }
            Raw.writeLongLittleEndian(bArr4, 0, i3);
            copyByteArrayToArrayList(bArr4, list);
            Raw.writeLongLittleEndian(bArr4, 0, size);
            copyByteArrayToArrayList(bArr4, list);
            Raw.writeLongLittleEndian(bArr4, 0, i2);
            copyByteArrayToArrayList(bArr4, list);
            Raw.writeLongLittleEndian(bArr4, 0, j2);
            copyByteArrayToArrayList(bArr4, list);
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void writeZipHeaderBytes(ZipModel zipModel, OutputStream outputStream, byte[] bArr) throws ZipException, IOException {
        if (bArr == null) {
            throw new ZipException("invalid buff to write as zip headers");
        }
        try {
            if ((outputStream instanceof SplitOutputStream) && ((SplitOutputStream) outputStream).checkBuffSizeAndStartNextSplitFile(bArr.length)) {
                finalizeZipFile(zipModel, outputStream);
            } else {
                outputStream.write(bArr);
            }
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    public void finalizeZipFile(ZipModel zipModel, OutputStream outputStream) throws ZipException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file");
        }
        try {
            processHeaderData(zipModel, outputStream);
            long offsetOfStartOfCentralDir = zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
            ArrayList arrayList = new ArrayList();
            int iWriteCentralDirectory = writeCentralDirectory(zipModel, outputStream, arrayList);
            if (zipModel.isZip64Format()) {
                if (zipModel.getZip64EndCentralDirRecord() == null) {
                    zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
                }
                if (zipModel.getZip64EndCentralDirLocator() == null) {
                    zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
                }
                zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(iWriteCentralDirectory + offsetOfStartOfCentralDir);
                if (outputStream instanceof SplitOutputStream) {
                    zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(((SplitOutputStream) outputStream).getCurrSplitFileCounter());
                    zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(((SplitOutputStream) outputStream).getCurrSplitFileCounter() + 1);
                } else {
                    zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
                    zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
                }
                writeZip64EndOfCentralDirectoryRecord(zipModel, outputStream, iWriteCentralDirectory, offsetOfStartOfCentralDir, arrayList);
                writeZip64EndOfCentralDirectoryLocator(zipModel, outputStream, arrayList);
            }
            writeEndOfCentralDirectoryRecord(zipModel, outputStream, iWriteCentralDirectory, offsetOfStartOfCentralDir, arrayList);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayListToByteArray(arrayList));
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    public void finalizeZipFileWithoutValidations(ZipModel zipModel, OutputStream outputStream) throws ZipException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file without validations");
        }
        try {
            ArrayList arrayList = new ArrayList();
            long offsetOfStartOfCentralDir = zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
            int iWriteCentralDirectory = writeCentralDirectory(zipModel, outputStream, arrayList);
            if (zipModel.isZip64Format()) {
                if (zipModel.getZip64EndCentralDirRecord() == null) {
                    zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
                }
                if (zipModel.getZip64EndCentralDirLocator() == null) {
                    zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
                }
                zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(iWriteCentralDirectory + offsetOfStartOfCentralDir);
                writeZip64EndOfCentralDirectoryRecord(zipModel, outputStream, iWriteCentralDirectory, offsetOfStartOfCentralDir, arrayList);
                writeZip64EndOfCentralDirectoryLocator(zipModel, outputStream, arrayList);
            }
            writeEndOfCentralDirectoryRecord(zipModel, outputStream, iWriteCentralDirectory, offsetOfStartOfCentralDir, arrayList);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayListToByteArray(arrayList));
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    public void updateLocalFileHeader(LocalFileHeader localFileHeader, long j2, int i2, ZipModel zipModel, byte[] bArr, int i3, SplitOutputStream splitOutputStream) throws ZipException {
        boolean z2;
        SplitOutputStream splitOutputStream2;
        String str;
        if (localFileHeader == null || j2 < 0 || zipModel == null) {
            throw new ZipException("invalid input parameters, cannot update local file header");
        }
        try {
            if (i3 != splitOutputStream.getCurrSplitFileCounter()) {
                File file = new File(zipModel.getZipFile());
                String parent = file.getParent();
                String zipFileNameWithoutExt = Zip4jUtil.getZipFileNameWithoutExt(file.getName());
                String str2 = parent + System.getProperty("file.separator");
                z2 = true;
                if (i3 < 9) {
                    str = str2 + zipFileNameWithoutExt + ".z0" + (i3 + 1);
                } else {
                    str = str2 + zipFileNameWithoutExt + ".z" + (i3 + 1);
                }
                splitOutputStream2 = new SplitOutputStream(new File(str));
            } else {
                z2 = false;
                splitOutputStream2 = splitOutputStream;
            }
            boolean z3 = z2;
            long filePointer = splitOutputStream2.getFilePointer();
            if (i2 == 14) {
                splitOutputStream2.seek(j2 + i2);
                splitOutputStream2.write(bArr);
            } else if (i2 == 18 || i2 == 22) {
                updateCompressedSizeInLocalFileHeader(splitOutputStream2, localFileHeader, j2, i2, bArr, zipModel.isZip64Format());
            }
            if (z3) {
                splitOutputStream2.close();
            } else {
                splitOutputStream.seek(filePointer);
            }
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    public int writeExtendedLocalHeader(LocalFileHeader localFileHeader, OutputStream outputStream) throws ZipException, IOException {
        if (localFileHeader == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot write extended local header");
        }
        ArrayList arrayList = new ArrayList();
        byte[] bArr = new byte[4];
        Raw.writeIntLittleEndian(bArr, 0, 134695760);
        copyByteArrayToArrayList(bArr, arrayList);
        Raw.writeIntLittleEndian(bArr, 0, (int) localFileHeader.getCrc32());
        copyByteArrayToArrayList(bArr, arrayList);
        long compressedSize = localFileHeader.getCompressedSize();
        if (compressedSize >= 2147483647L) {
            compressedSize = 2147483647L;
        }
        Raw.writeIntLittleEndian(bArr, 0, (int) compressedSize);
        copyByteArrayToArrayList(bArr, arrayList);
        long uncompressedSize = localFileHeader.getUncompressedSize();
        Raw.writeIntLittleEndian(bArr, 0, (int) (uncompressedSize < 2147483647L ? uncompressedSize : 2147483647L));
        copyByteArrayToArrayList(bArr, arrayList);
        byte[] bArrByteArrayListToByteArray = byteArrayListToByteArray(arrayList);
        outputStream.write(bArrByteArrayListToByteArray);
        return bArrByteArrayListToByteArray.length;
    }

    public int writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream) throws ZipException, IOException {
        boolean z2;
        if (localFileHeader == null) {
            throw new ZipException("input parameters are null, cannot write local file header");
        }
        try {
            ArrayList arrayList = new ArrayList();
            byte[] bArr = new byte[2];
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[8];
            byte[] bArr4 = {0, 0, 0, 0, 0, 0, 0, 0};
            Raw.writeIntLittleEndian(bArr2, 0, localFileHeader.getSignature());
            copyByteArrayToArrayList(bArr2, arrayList);
            Raw.writeShortLittleEndian(bArr, 0, (short) localFileHeader.getVersionNeededToExtract());
            copyByteArrayToArrayList(bArr, arrayList);
            copyByteArrayToArrayList(localFileHeader.getGeneralPurposeFlag(), arrayList);
            Raw.writeShortLittleEndian(bArr, 0, (short) localFileHeader.getCompressionMethod());
            copyByteArrayToArrayList(bArr, arrayList);
            Raw.writeIntLittleEndian(bArr2, 0, localFileHeader.getLastModFileTime());
            copyByteArrayToArrayList(bArr2, arrayList);
            Raw.writeIntLittleEndian(bArr2, 0, (int) localFileHeader.getCrc32());
            copyByteArrayToArrayList(bArr2, arrayList);
            if (localFileHeader.getUncompressedSize() + 50 >= InternalZipConstants.ZIP_64_LIMIT) {
                Raw.writeLongLittleEndian(bArr3, 0, InternalZipConstants.ZIP_64_LIMIT);
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, arrayList);
                copyByteArrayToArrayList(bArr2, arrayList);
                zipModel.setZip64Format(true);
                localFileHeader.setWriteComprSizeInZip64ExtraRecord(true);
                z2 = true;
            } else {
                Raw.writeLongLittleEndian(bArr3, 0, localFileHeader.getCompressedSize());
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, arrayList);
                Raw.writeLongLittleEndian(bArr3, 0, localFileHeader.getUncompressedSize());
                System.arraycopy(bArr3, 0, bArr2, 0, 4);
                copyByteArrayToArrayList(bArr2, arrayList);
                localFileHeader.setWriteComprSizeInZip64ExtraRecord(false);
                z2 = false;
            }
            Raw.writeShortLittleEndian(bArr, 0, (short) localFileHeader.getFileNameLength());
            copyByteArrayToArrayList(bArr, arrayList);
            int i2 = z2 ? 20 : 0;
            if (localFileHeader.getAesExtraDataRecord() != null) {
                i2 += 11;
            }
            Raw.writeShortLittleEndian(bArr, 0, (short) i2);
            copyByteArrayToArrayList(bArr, arrayList);
            if (Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getFileNameCharset())) {
                copyByteArrayToArrayList(localFileHeader.getFileName().getBytes(zipModel.getFileNameCharset()), arrayList);
            } else {
                copyByteArrayToArrayList(Zip4jUtil.convertCharset(localFileHeader.getFileName()), arrayList);
            }
            if (z2) {
                Raw.writeShortLittleEndian(bArr, 0, (short) 1);
                copyByteArrayToArrayList(bArr, arrayList);
                Raw.writeShortLittleEndian(bArr, 0, (short) 16);
                copyByteArrayToArrayList(bArr, arrayList);
                Raw.writeLongLittleEndian(bArr3, 0, localFileHeader.getUncompressedSize());
                copyByteArrayToArrayList(bArr3, arrayList);
                copyByteArrayToArrayList(bArr4, arrayList);
            }
            if (localFileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
                Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getSignature());
                copyByteArrayToArrayList(bArr, arrayList);
                Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getDataSize());
                copyByteArrayToArrayList(bArr, arrayList);
                Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getVersionNumber());
                copyByteArrayToArrayList(bArr, arrayList);
                copyByteArrayToArrayList(aesExtraDataRecord.getVendorID().getBytes(), arrayList);
                copyByteArrayToArrayList(new byte[]{(byte) aesExtraDataRecord.getAesStrength()}, arrayList);
                Raw.writeShortLittleEndian(bArr, 0, (short) aesExtraDataRecord.getCompressionMethod());
                copyByteArrayToArrayList(bArr, arrayList);
            }
            byte[] bArrByteArrayListToByteArray = byteArrayListToByteArray(arrayList);
            outputStream.write(bArrByteArrayListToByteArray);
            return bArrByteArrayListToByteArray.length;
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }
}
