package net.lingala.zip4j.unzip;

import java.io.File;
import java.util.ArrayList;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class Unzip {
    private ZipModel zipModel;

    public Unzip(ZipModel zipModel) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("ZipModel is null");
        }
        this.zipModel = zipModel;
    }

    private long calculateTotalWork(ArrayList arrayList) throws ZipException {
        if (arrayList == null) {
            throw new ZipException("fileHeaders is null, cannot calculate total work");
        }
        long compressedSize = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            FileHeader fileHeader = (FileHeader) arrayList.get(i2);
            compressedSize += (fileHeader.getZip64ExtendedInfo() == null || fileHeader.getZip64ExtendedInfo().getUnCompressedSize() <= 0) ? fileHeader.getCompressedSize() : fileHeader.getZip64ExtendedInfo().getCompressedSize();
        }
        return compressedSize;
    }

    private void checkOutputDirectoryStructure(FileHeader fileHeader, String str, String str2) throws ZipException {
        if (fileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("Cannot check output directory structure...one of the parameters was null");
        }
        String fileName = fileHeader.getFileName();
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str2)) {
            str2 = fileName;
        }
        if (Zip4jUtil.isStringNotNullAndNotEmpty(str2)) {
            try {
                File file = new File(new File(str + str2).getParent());
                if (file.exists()) {
                    return;
                }
                file.mkdirs();
            } catch (Exception e2) {
                throw new ZipException(e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initExtractAll(ArrayList arrayList, UnzipParameters unzipParameters, ProgressMonitor progressMonitor, String str) throws ZipException {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            initExtractFile((FileHeader) arrayList.get(i2), str, unzipParameters, null, progressMonitor);
            if (progressMonitor.isCancelAllTasks()) {
                progressMonitor.setResult(3);
                progressMonitor.setState(0);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initExtractFile(FileHeader fileHeader, String str, UnzipParameters unzipParameters, String str2, ProgressMonitor progressMonitor) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("fileHeader is null");
        }
        try {
            progressMonitor.setFileName(fileHeader.getFileName());
            String str3 = InternalZipConstants.FILE_SEPARATOR;
            if (!str.endsWith(str3)) {
                str = str + str3;
            }
            if (!fileHeader.isDirectory()) {
                checkOutputDirectoryStructure(fileHeader, str, str2);
                try {
                    new UnzipEngine(this.zipModel, fileHeader).unzipFile(progressMonitor, str, str2, unzipParameters);
                    return;
                } catch (Exception e2) {
                    progressMonitor.endProgressMonitorError(e2);
                    throw new ZipException(e2);
                }
            }
            try {
                String fileName = fileHeader.getFileName();
                if (Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
                    File file = new File(str + fileName);
                    if (file.exists()) {
                        return;
                    }
                    file.mkdirs();
                }
            } catch (Exception e3) {
                progressMonitor.endProgressMonitorError(e3);
                throw new ZipException(e3);
            }
        } catch (ZipException e4) {
            progressMonitor.endProgressMonitorError(e4);
            throw e4;
        } catch (Exception e5) {
            progressMonitor.endProgressMonitorError(e5);
            throw new ZipException(e5);
        }
    }

    public void extractAll(final UnzipParameters unzipParameters, final String str, final ProgressMonitor progressMonitor, boolean z2) throws ZipException {
        CentralDirectory centralDirectory = this.zipModel.getCentralDirectory();
        if (centralDirectory == null || centralDirectory.getFileHeaders() == null) {
            throw new ZipException("invalid central directory in zipModel");
        }
        final ArrayList fileHeaders = centralDirectory.getFileHeaders();
        progressMonitor.setCurrentOperation(1);
        progressMonitor.setTotalWork(calculateTotalWork(fileHeaders));
        progressMonitor.setState(1);
        if (z2) {
            new Thread(InternalZipConstants.THREAD_NAME) { // from class: net.lingala.zip4j.unzip.Unzip.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Unzip.this.initExtractAll(fileHeaders, unzipParameters, progressMonitor, str);
                        progressMonitor.endProgressMonitorSuccess();
                    } catch (ZipException unused) {
                    }
                }
            }.start();
        } else {
            initExtractAll(fileHeaders, unzipParameters, progressMonitor, str);
        }
    }

    public void extractFile(final FileHeader fileHeader, final String str, final UnzipParameters unzipParameters, final String str2, final ProgressMonitor progressMonitor, boolean z2) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("fileHeader is null");
        }
        progressMonitor.setCurrentOperation(1);
        progressMonitor.setTotalWork(fileHeader.getCompressedSize());
        progressMonitor.setState(1);
        progressMonitor.setPercentDone(0);
        progressMonitor.setFileName(fileHeader.getFileName());
        if (z2) {
            new Thread(InternalZipConstants.THREAD_NAME) { // from class: net.lingala.zip4j.unzip.Unzip.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Unzip.this.initExtractFile(fileHeader, str, unzipParameters, str2, progressMonitor);
                        progressMonitor.endProgressMonitorSuccess();
                    } catch (ZipException unused) {
                    }
                }
            }.start();
        } else {
            initExtractFile(fileHeader, str, unzipParameters, str2, progressMonitor);
            progressMonitor.endProgressMonitorSuccess();
        }
    }

    public ZipInputStream getInputStream(FileHeader fileHeader) throws ZipException {
        return new UnzipEngine(this.zipModel, fileHeader).getInputStream();
    }
}
