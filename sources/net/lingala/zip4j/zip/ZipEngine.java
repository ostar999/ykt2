package net.lingala.zip4j.zip;

import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.SplitOutputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.EndCentralDirRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.ArchiveMaintainer;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class ZipEngine {
    private ZipModel zipModel;

    public ZipEngine(ZipModel zipModel) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null in ZipEngine constructor");
        }
        this.zipModel = zipModel;
    }

    private long calculateTotalWork(ArrayList arrayList, ZipParameters zipParameters) throws ZipException {
        if (arrayList == null) {
            throw new ZipException("file list is null, cannot calculate total work");
        }
        long fileLengh = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if ((arrayList.get(i2) instanceof File) && ((File) arrayList.get(i2)).exists()) {
                fileLengh += (zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod() == 0) ? Zip4jUtil.getFileLengh((File) arrayList.get(i2)) * 2 : Zip4jUtil.getFileLengh((File) arrayList.get(i2));
                if (this.zipModel.getCentralDirectory() != null && this.zipModel.getCentralDirectory().getFileHeaders() != null && this.zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
                    FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, Zip4jUtil.getRelativeFileName(((File) arrayList.get(i2)).getAbsolutePath(), zipParameters.getRootFolderInZip(), zipParameters.getDefaultFolderPath()));
                    if (fileHeader != null) {
                        fileLengh += Zip4jUtil.getFileLengh(new File(this.zipModel.getZipFile())) - fileHeader.getCompressedSize();
                    }
                }
            }
        }
        return fileLengh;
    }

    private void checkParameters(ZipParameters zipParameters) throws ZipException {
        if (zipParameters == null) {
            throw new ZipException("cannot validate zip parameters");
        }
        if (zipParameters.getCompressionMethod() != 0 && zipParameters.getCompressionMethod() != 8) {
            throw new ZipException("unsupported compression type");
        }
        if (zipParameters.getCompressionMethod() == 8 && zipParameters.getCompressionLevel() < 0 && zipParameters.getCompressionLevel() > 9) {
            throw new ZipException("invalid compression level. compression level dor deflate should be in the range of 0-9");
        }
        if (!zipParameters.isEncryptFiles()) {
            zipParameters.setAesKeyStrength(-1);
            zipParameters.setEncryptionMethod(-1);
        } else {
            if (zipParameters.getEncryptionMethod() != 0 && zipParameters.getEncryptionMethod() != 99) {
                throw new ZipException("unsupported encryption method");
            }
            if (zipParameters.getPassword() == null || zipParameters.getPassword().length <= 0) {
                throw new ZipException("input password is empty or null");
            }
        }
    }

    private EndCentralDirRecord createEndOfCentralDirectoryRecord() {
        EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
        endCentralDirRecord.setSignature(InternalZipConstants.ENDSIG);
        endCentralDirRecord.setNoOfThisDisk(0);
        endCentralDirRecord.setTotNoOfEntriesInCentralDir(0);
        endCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(0);
        endCentralDirRecord.setOffsetOfStartOfCentralDir(0L);
        return endCentralDirRecord;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0127, code lost:
    
        r14.setResult(3);
        r14.setState(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x012d, code lost:
    
        r5.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0184 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initAddFiles(java.util.ArrayList r12, net.lingala.zip4j.model.ZipParameters r13, net.lingala.zip4j.progress.ProgressMonitor r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.zip.ZipEngine.initAddFiles(java.util.ArrayList, net.lingala.zip4j.model.ZipParameters, net.lingala.zip4j.progress.ProgressMonitor):void");
    }

    private RandomAccessFile prepareFileOutputStream() throws ZipException {
        String zipFile = this.zipModel.getZipFile();
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(zipFile)) {
            throw new ZipException("invalid output path");
        }
        try {
            File file = new File(zipFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        }
    }

    private void removeFilesIfExists(ArrayList arrayList, ZipParameters zipParameters, ProgressMonitor progressMonitor) throws ZipException, IOException {
        ZipModel zipModel = this.zipModel;
        if (zipModel == null || zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null || this.zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return;
        }
        RandomAccessFile randomAccessFilePrepareFileOutputStream = null;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            try {
                try {
                    FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, Zip4jUtil.getRelativeFileName(((File) arrayList.get(i2)).getAbsolutePath(), zipParameters.getRootFolderInZip(), zipParameters.getDefaultFolderPath()));
                    if (fileHeader != null) {
                        if (randomAccessFilePrepareFileOutputStream != null) {
                            randomAccessFilePrepareFileOutputStream.close();
                            randomAccessFilePrepareFileOutputStream = null;
                        }
                        ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
                        progressMonitor.setCurrentOperation(2);
                        HashMap mapInitRemoveZipFile = archiveMaintainer.initRemoveZipFile(this.zipModel, fileHeader, progressMonitor);
                        if (progressMonitor.isCancelAllTasks()) {
                            progressMonitor.setResult(3);
                            progressMonitor.setState(0);
                            if (randomAccessFilePrepareFileOutputStream != null) {
                                try {
                                    randomAccessFilePrepareFileOutputStream.close();
                                    return;
                                } catch (IOException unused) {
                                    return;
                                }
                            }
                            return;
                        }
                        progressMonitor.setCurrentOperation(0);
                        if (randomAccessFilePrepareFileOutputStream == null) {
                            randomAccessFilePrepareFileOutputStream = prepareFileOutputStream();
                            if (mapInitRemoveZipFile != null && mapInitRemoveZipFile.get(InternalZipConstants.OFFSET_CENTRAL_DIR) != null) {
                                try {
                                    long j2 = Long.parseLong((String) mapInitRemoveZipFile.get(InternalZipConstants.OFFSET_CENTRAL_DIR));
                                    if (j2 >= 0) {
                                        randomAccessFilePrepareFileOutputStream.seek(j2);
                                    }
                                } catch (NumberFormatException unused2) {
                                    throw new ZipException("NumberFormatException while parsing offset central directory. Cannot update already existing file header");
                                } catch (Exception unused3) {
                                    throw new ZipException("Error while parsing offset central directory. Cannot update already existing file header");
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (IOException e2) {
                    throw new ZipException(e2);
                }
            } catch (Throwable th) {
                if (randomAccessFilePrepareFileOutputStream != null) {
                    try {
                        randomAccessFilePrepareFileOutputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        }
        if (randomAccessFilePrepareFileOutputStream != null) {
            try {
                randomAccessFilePrepareFileOutputStream.close();
            } catch (IOException unused5) {
            }
        }
    }

    public void addFiles(final ArrayList arrayList, final ZipParameters zipParameters, final ProgressMonitor progressMonitor, boolean z2) throws Throwable {
        if (arrayList == null || zipParameters == null) {
            throw new ZipException("one of the input parameters is null when adding files");
        }
        if (arrayList.size() <= 0) {
            throw new ZipException("no files to add");
        }
        progressMonitor.setCurrentOperation(0);
        progressMonitor.setState(1);
        progressMonitor.setResult(1);
        if (!z2) {
            initAddFiles(arrayList, zipParameters, progressMonitor);
            return;
        }
        progressMonitor.setTotalWork(calculateTotalWork(arrayList, zipParameters));
        progressMonitor.setFileName(((File) arrayList.get(0)).getAbsolutePath());
        new Thread(InternalZipConstants.THREAD_NAME) { // from class: net.lingala.zip4j.zip.ZipEngine.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                try {
                    ZipEngine.this.initAddFiles(arrayList, zipParameters, progressMonitor);
                } catch (ZipException unused) {
                }
            }
        }.start();
    }

    public void addFolderToZip(File file, ZipParameters zipParameters, ProgressMonitor progressMonitor, boolean z2) throws Throwable {
        String absolutePath;
        if (file == null || zipParameters == null) {
            throw new ZipException("one of the input parameters is null, cannot add folder to zip");
        }
        if (!Zip4jUtil.checkFileExists(file.getAbsolutePath())) {
            throw new ZipException("input folder does not exist");
        }
        if (!file.isDirectory()) {
            throw new ZipException("input file is not a folder, user addFileToZip method to add files");
        }
        if (!Zip4jUtil.checkFileReadAccess(file.getAbsolutePath())) {
            throw new ZipException("cannot read folder: " + file.getAbsolutePath());
        }
        if (zipParameters.isIncludeRootFolder()) {
            absolutePath = "";
            if (file.getAbsolutePath() != null) {
                if (file.getAbsoluteFile().getParentFile() != null) {
                    absolutePath = file.getAbsoluteFile().getParentFile().getAbsolutePath();
                }
            } else if (file.getParentFile() != null) {
                absolutePath = file.getParentFile().getAbsolutePath();
            }
        } else {
            absolutePath = file.getAbsolutePath();
        }
        zipParameters.setDefaultFolderPath(absolutePath);
        ArrayList filesInDirectoryRec = Zip4jUtil.getFilesInDirectoryRec(file, zipParameters.isReadHiddenFiles());
        if (zipParameters.isIncludeRootFolder()) {
            if (filesInDirectoryRec == null) {
                filesInDirectoryRec = new ArrayList();
            }
            filesInDirectoryRec.add(file);
        }
        addFiles(filesInDirectoryRec, zipParameters, progressMonitor, z2);
    }

    public void addStreamToZip(InputStream inputStream, ZipParameters zipParameters) throws Throwable {
        if (inputStream == null || zipParameters == null) {
            throw new ZipException("one of the input parameters is null, cannot add stream to zip");
        }
        ZipOutputStream zipOutputStream = null;
        try {
            try {
                checkParameters(zipParameters);
                boolean zCheckFileExists = Zip4jUtil.checkFileExists(this.zipModel.getZipFile());
                SplitOutputStream splitOutputStream = new SplitOutputStream(new File(this.zipModel.getZipFile()), this.zipModel.getSplitLength());
                ZipOutputStream zipOutputStream2 = new ZipOutputStream(splitOutputStream, this.zipModel);
                if (zCheckFileExists) {
                    try {
                        if (this.zipModel.getEndCentralDirRecord() == null) {
                            throw new ZipException("invalid end of central directory record");
                        }
                        splitOutputStream.seek(this.zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
                    } catch (ZipException e2) {
                        throw e2;
                    } catch (Exception e3) {
                        e = e3;
                        throw new ZipException(e);
                    } catch (Throwable th) {
                        th = th;
                        zipOutputStream = zipOutputStream2;
                        if (zipOutputStream != null) {
                            try {
                                zipOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                }
                byte[] bArr = new byte[4096];
                zipOutputStream2.putNextEntry(null, zipParameters);
                if (!zipParameters.getFileNameInZip().endsWith("/") && !zipParameters.getFileNameInZip().endsWith(StrPool.BACKSLASH)) {
                    while (true) {
                        int i2 = inputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            zipOutputStream2.write(bArr, 0, i2);
                        }
                    }
                }
                zipOutputStream2.closeEntry();
                zipOutputStream2.finish();
                try {
                    zipOutputStream2.close();
                } catch (IOException unused2) {
                }
            } catch (ZipException e4) {
                throw e4;
            } catch (Exception e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
