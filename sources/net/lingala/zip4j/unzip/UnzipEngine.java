package net.lingala.zip4j.unzip;

import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.zip.CRC32;
import net.lingala.zip4j.core.HeaderReader;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.crypto.IDecrypter;
import net.lingala.zip4j.crypto.StandardDecrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.InflaterInputStream;
import net.lingala.zip4j.io.PartInputStream;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class UnzipEngine {
    private CRC32 crc;
    private int currSplitFileCounter = 0;
    private IDecrypter decrypter;
    private FileHeader fileHeader;
    private LocalFileHeader localFileHeader;
    private ZipModel zipModel;

    public UnzipEngine(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
        if (zipModel == null || fileHeader == null) {
            throw new ZipException("Invalid parameters passed to StoreUnzip. One or more of the parameters were null");
        }
        this.zipModel = zipModel;
        this.fileHeader = fileHeader;
        this.crc = new CRC32();
    }

    private int calculateAESSaltLength(AESExtraDataRecord aESExtraDataRecord) throws ZipException {
        if (aESExtraDataRecord == null) {
            throw new ZipException("unable to determine salt length: AESExtraDataRecord is null");
        }
        int aesStrength = aESExtraDataRecord.getAesStrength();
        if (aesStrength == 1) {
            return 8;
        }
        if (aesStrength == 2) {
            return 12;
        }
        if (aesStrength == 3) {
            return 16;
        }
        throw new ZipException("unable to determine salt length: invalid aes key strength");
    }

    private boolean checkLocalHeader() throws ZipException, IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                RandomAccessFile randomAccessFileCheckSplitFile = checkSplitFile();
                if (randomAccessFileCheckSplitFile == null) {
                    randomAccessFileCheckSplitFile = new RandomAccessFile(new File(this.zipModel.getZipFile()), "r");
                }
                LocalFileHeader localFileHeader = new HeaderReader(randomAccessFileCheckSplitFile).readLocalFileHeader(this.fileHeader);
                this.localFileHeader = localFileHeader;
                if (localFileHeader == null) {
                    throw new ZipException("error reading local file header. Is this a valid zip file?");
                }
                if (localFileHeader.getCompressionMethod() != this.fileHeader.getCompressionMethod()) {
                    try {
                        randomAccessFileCheckSplitFile.close();
                        return false;
                    } catch (IOException | Exception unused) {
                        return false;
                    }
                }
                try {
                    randomAccessFileCheckSplitFile.close();
                    return true;
                } catch (IOException | Exception unused2) {
                    return true;
                }
            } catch (FileNotFoundException e2) {
                throw new ZipException(e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    randomAccessFile.close();
                } catch (IOException | Exception unused3) {
                }
            }
            throw th;
        }
    }

    private RandomAccessFile checkSplitFile() throws ZipException, IOException {
        String zipFile;
        if (!this.zipModel.isSplitArchive()) {
            return null;
        }
        int diskNumberStart = this.fileHeader.getDiskNumberStart();
        int i2 = diskNumberStart + 1;
        this.currSplitFileCounter = i2;
        String zipFile2 = this.zipModel.getZipFile();
        if (diskNumberStart == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
            zipFile = this.zipModel.getZipFile();
        } else if (diskNumberStart >= 9) {
            zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z" + i2;
        } else {
            zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z0" + i2;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(zipFile, "r");
            if (this.currSplitFileCounter == 1) {
                randomAccessFile.read(new byte[4]);
                if (Raw.readIntLittleEndian(r0, 0) != 134695760) {
                    throw new ZipException("invalid first part split file signature");
                }
            }
            return randomAccessFile;
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        } catch (IOException e3) {
            throw new ZipException(e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void closeStreams(java.io.InputStream r3, java.io.OutputStream r4) throws net.lingala.zip4j.exception.ZipException, java.io.IOException {
        /*
            r2 = this;
            if (r3 == 0) goto L36
            r3.close()     // Catch: java.lang.Throwable -> L6 java.io.IOException -> L8
            goto L36
        L6:
            r3 = move-exception
            goto L30
        L8:
            r3 = move-exception
            java.lang.String r0 = r3.getMessage()     // Catch: java.lang.Throwable -> L6
            boolean r0 = net.lingala.zip4j.util.Zip4jUtil.isStringNotNullAndNotEmpty(r0)     // Catch: java.lang.Throwable -> L6
            if (r0 == 0) goto L2a
            java.lang.String r0 = r3.getMessage()     // Catch: java.lang.Throwable -> L6
            java.lang.String r1 = " - Wrong Password?"
            int r0 = r0.indexOf(r1)     // Catch: java.lang.Throwable -> L6
            if (r0 >= 0) goto L20
            goto L2a
        L20:
            net.lingala.zip4j.exception.ZipException r0 = new net.lingala.zip4j.exception.ZipException     // Catch: java.lang.Throwable -> L6
            java.lang.String r3 = r3.getMessage()     // Catch: java.lang.Throwable -> L6
            r0.<init>(r3)     // Catch: java.lang.Throwable -> L6
            throw r0     // Catch: java.lang.Throwable -> L6
        L2a:
            if (r4 == 0) goto L39
        L2c:
            r4.close()     // Catch: java.io.IOException -> L39
            goto L39
        L30:
            if (r4 == 0) goto L35
            r4.close()     // Catch: java.io.IOException -> L35
        L35:
            throw r3
        L36:
            if (r4 == 0) goto L39
            goto L2c
        L39:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.unzip.UnzipEngine.closeStreams(java.io.InputStream, java.io.OutputStream):void");
    }

    private RandomAccessFile createFileHandler(String str) throws ZipException {
        ZipModel zipModel = this.zipModel;
        if (zipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getZipFile())) {
            throw new ZipException("input parameter is null in getFilePointer");
        }
        try {
            return this.zipModel.isSplitArchive() ? checkSplitFile() : new RandomAccessFile(new File(this.zipModel.getZipFile()), str);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private byte[] getAESPasswordVerifier(RandomAccessFile randomAccessFile) throws ZipException, IOException {
        try {
            byte[] bArr = new byte[2];
            randomAccessFile.read(bArr);
            return bArr;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private byte[] getAESSalt(RandomAccessFile randomAccessFile) throws ZipException, IOException {
        if (this.localFileHeader.getAesExtraDataRecord() == null) {
            return null;
        }
        try {
            byte[] bArr = new byte[calculateAESSaltLength(this.localFileHeader.getAesExtraDataRecord())];
            randomAccessFile.seek(this.localFileHeader.getOffsetStartOfData());
            randomAccessFile.read(bArr);
            return bArr;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private String getOutputFileNameWithPath(String str, String str2) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str2)) {
            str2 = this.fileHeader.getFileName();
        }
        return str + System.getProperty("file.separator") + str2;
    }

    private FileOutputStream getOutputStream(String str, String str2) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("invalid output path");
        }
        try {
            File file = new File(getOutputFileNameWithPath(str, str2));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            return new FileOutputStream(file);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        }
    }

    private byte[] getStandardDecrypterHeaderBytes(RandomAccessFile randomAccessFile) throws ZipException, IOException {
        try {
            byte[] bArr = new byte[12];
            randomAccessFile.seek(this.localFileHeader.getOffsetStartOfData());
            randomAccessFile.read(bArr, 0, 12);
            return bArr;
        } catch (IOException e2) {
            throw new ZipException(e2);
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void init(RandomAccessFile randomAccessFile) throws ZipException {
        if (this.localFileHeader == null) {
            throw new ZipException("local file header is null, cannot initialize input stream");
        }
        try {
            initDecrypter(randomAccessFile);
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void initDecrypter(RandomAccessFile randomAccessFile) throws ZipException {
        LocalFileHeader localFileHeader = this.localFileHeader;
        if (localFileHeader == null) {
            throw new ZipException("local file header is null, cannot init decrypter");
        }
        if (localFileHeader.isEncrypted()) {
            if (this.localFileHeader.getEncryptionMethod() == 0) {
                this.decrypter = new StandardDecrypter(this.fileHeader, getStandardDecrypterHeaderBytes(randomAccessFile));
            } else {
                if (this.localFileHeader.getEncryptionMethod() != 99) {
                    throw new ZipException("unsupported encryption method");
                }
                this.decrypter = new AESDecrypter(this.localFileHeader, getAESSalt(randomAccessFile), getAESPasswordVerifier(randomAccessFile));
            }
        }
    }

    public void checkCRC() throws ZipException {
        FileHeader fileHeader = this.fileHeader;
        if (fileHeader != null) {
            if (fileHeader.getEncryptionMethod() != 99) {
                if ((this.crc.getValue() & InternalZipConstants.ZIP_64_LIMIT) != this.fileHeader.getCrc32()) {
                    String str = "invalid CRC for file: " + this.fileHeader.getFileName();
                    if (this.localFileHeader.isEncrypted() && this.localFileHeader.getEncryptionMethod() == 0) {
                        str = str + " - Wrong Password?";
                    }
                    throw new ZipException(str);
                }
                return;
            }
            IDecrypter iDecrypter = this.decrypter;
            if (iDecrypter == null || !(iDecrypter instanceof AESDecrypter)) {
                return;
            }
            byte[] calculatedAuthenticationBytes = ((AESDecrypter) iDecrypter).getCalculatedAuthenticationBytes();
            byte[] storedMac = ((AESDecrypter) this.decrypter).getStoredMac();
            byte[] bArr = new byte[10];
            if (storedMac == null) {
                throw new ZipException("CRC (MAC) check failed for " + this.fileHeader.getFileName());
            }
            System.arraycopy(calculatedAuthenticationBytes, 0, bArr, 0, 10);
            if (Arrays.equals(bArr, storedMac)) {
                return;
            }
            throw new ZipException("invalid CRC (MAC) for file: " + this.fileHeader.getFileName());
        }
    }

    public IDecrypter getDecrypter() {
        return this.decrypter;
    }

    public FileHeader getFileHeader() {
        return this.fileHeader;
    }

    public ZipInputStream getInputStream() throws ZipException, IOException {
        long saltLength;
        if (this.fileHeader == null) {
            throw new ZipException("file header is null, cannot get inputstream");
        }
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFileCreateFileHandler = createFileHandler("r");
            if (!checkLocalHeader()) {
                throw new ZipException("local header and file header do not match");
            }
            init(randomAccessFileCreateFileHandler);
            long compressedSize = this.localFileHeader.getCompressedSize();
            long offsetStartOfData = this.localFileHeader.getOffsetStartOfData();
            if (this.localFileHeader.isEncrypted()) {
                if (this.localFileHeader.getEncryptionMethod() == 99) {
                    if (!(this.decrypter instanceof AESDecrypter)) {
                        throw new ZipException("invalid decryptor when trying to calculate compressed size for AES encrypted file: " + this.fileHeader.getFileName());
                    }
                    compressedSize -= (((AESDecrypter) r5).getSaltLength() + ((AESDecrypter) this.decrypter).getPasswordVerifierLength()) + 10;
                    saltLength = ((AESDecrypter) this.decrypter).getSaltLength() + ((AESDecrypter) this.decrypter).getPasswordVerifierLength();
                } else if (this.localFileHeader.getEncryptionMethod() == 0) {
                    saltLength = 12;
                    compressedSize -= 12;
                }
                offsetStartOfData += saltLength;
            }
            long j2 = compressedSize;
            long j3 = offsetStartOfData;
            int compressionMethod = this.fileHeader.getCompressionMethod();
            if (this.fileHeader.getEncryptionMethod() == 99) {
                if (this.fileHeader.getAesExtraDataRecord() == null) {
                    throw new ZipException("AESExtraDataRecord does not exist for AES encrypted file: " + this.fileHeader.getFileName());
                }
                compressionMethod = this.fileHeader.getAesExtraDataRecord().getCompressionMethod();
            }
            randomAccessFileCreateFileHandler.seek(j3);
            if (compressionMethod == 0) {
                return new ZipInputStream(new PartInputStream(randomAccessFileCreateFileHandler, j3, j2, this));
            }
            if (compressionMethod == 8) {
                return new ZipInputStream(new InflaterInputStream(randomAccessFileCreateFileHandler, j3, j2, this));
            }
            throw new ZipException("compression type not supported");
        } catch (ZipException e2) {
            if (0 != 0) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused) {
                }
            }
            throw e2;
        } catch (Exception e3) {
            if (0 != 0) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused2) {
                }
            }
            throw new ZipException(e3);
        }
    }

    public LocalFileHeader getLocalFileHeader() {
        return this.localFileHeader;
    }

    public ZipModel getZipModel() {
        return this.zipModel;
    }

    public RandomAccessFile startNextSplitFile() throws IOException {
        String zipFile;
        String zipFile2 = this.zipModel.getZipFile();
        if (this.currSplitFileCounter == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
            zipFile = this.zipModel.getZipFile();
        } else if (this.currSplitFileCounter >= 9) {
            zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z" + (this.currSplitFileCounter + 1);
        } else {
            zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z0" + (this.currSplitFileCounter + 1);
        }
        this.currSplitFileCounter++;
        try {
            if (Zip4jUtil.checkFileExists(zipFile)) {
                return new RandomAccessFile(zipFile, "r");
            }
            throw new IOException("zip split file does not exist: " + zipFile);
        } catch (ZipException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [net.lingala.zip4j.unzip.UnzipEngine] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v6 */
    public void unzipFile(ProgressMonitor progressMonitor, String str, String str2, UnzipParameters unzipParameters) throws ZipException {
        byte[] bArr;
        ZipInputStream inputStream;
        if (this.zipModel == null || this.fileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("Invalid parameters passed during unzipping file. One or more of the parameters were null");
        }
        ZipInputStream zipInputStream = null;
        try {
            try {
                bArr = new byte[4096];
                inputStream = getInputStream();
            } catch (IOException e2) {
                e = e2;
            } catch (Exception e3) {
                e = e3;
            } catch (Throwable th) {
                th = th;
                str = 0;
            }
            try {
                FileOutputStream outputStream = getOutputStream(str, str2);
                do {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        closeStreams(inputStream, outputStream);
                        UnzipUtil.applyFileAttributes(this.fileHeader, new File(getOutputFileNameWithPath(str, str2)), unzipParameters);
                        closeStreams(inputStream, outputStream);
                        return;
                    }
                    outputStream.write(bArr, 0, i2);
                    progressMonitor.updateWorkCompleted(i2);
                } while (!progressMonitor.isCancelAllTasks());
                progressMonitor.setResult(3);
                progressMonitor.setState(0);
                closeStreams(inputStream, outputStream);
            } catch (IOException e4) {
                e = e4;
                throw new ZipException(e);
            } catch (Exception e5) {
                e = e5;
                throw new ZipException(e);
            } catch (Throwable th2) {
                th = th2;
                str = 0;
                zipInputStream = inputStream;
                closeStreams(zipInputStream, str);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void updateCRC(int i2) {
        this.crc.update(i2);
    }

    public void updateCRC(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            this.crc.update(bArr, i2, i3);
        }
    }
}
