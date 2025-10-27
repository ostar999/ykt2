package net.lingala.zip4j.util;

import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import net.lingala.zip4j.core.HeaderWriter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.SplitOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;

/* loaded from: classes9.dex */
public class ArchiveMaintainer {
    private long calculateTotalWorkForMergeOp(ZipModel zipModel) throws ZipException {
        long fileLengh = 0;
        if (zipModel.isSplitArchive()) {
            int noOfThisDisk = zipModel.getEndCentralDirRecord().getNoOfThisDisk();
            String zipFile = zipModel.getZipFile();
            for (int i2 = 0; i2 <= noOfThisDisk; i2++) {
                fileLengh += Zip4jUtil.getFileLengh(new File(zipModel.getEndCentralDirRecord().getNoOfThisDisk() == 0 ? zipModel.getZipFile() : zipFile.substring(0, zipFile.lastIndexOf(StrPool.DOT)) + ".z01"));
            }
        }
        return fileLengh;
    }

    private long calculateTotalWorkForRemoveOp(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
        return Zip4jUtil.getFileLengh(new File(zipModel.getZipFile())) - fileHeader.getCompressedSize();
    }

    private void copyFile(RandomAccessFile randomAccessFile, OutputStream outputStream, long j2, long j3, ProgressMonitor progressMonitor) throws ZipException, IOException {
        if (randomAccessFile == null || outputStream == null) {
            throw new ZipException("input or output stream is null, cannot copy file");
        }
        long j4 = 0;
        if (j2 < 0) {
            throw new ZipException("starting offset is negative, cannot copy file");
        }
        if (j3 < 0) {
            throw new ZipException("end offset is negative, cannot copy file");
        }
        if (j2 > j3) {
            throw new ZipException("start offset is greater than end offset, cannot copy file");
        }
        if (j2 == j3) {
            return;
        }
        if (progressMonitor.isCancelAllTasks()) {
            progressMonitor.setResult(3);
            progressMonitor.setState(0);
            return;
        }
        try {
            randomAccessFile.seek(j2);
            long j5 = j3 - j2;
            byte[] bArr = j5 < 4096 ? new byte[(int) j5] : new byte[4096];
            while (true) {
                int i2 = randomAccessFile.read(bArr);
                if (i2 == -1) {
                    return;
                }
                outputStream.write(bArr, 0, i2);
                long j6 = i2;
                progressMonitor.updateWorkCompleted(j6);
                if (progressMonitor.isCancelAllTasks()) {
                    progressMonitor.setResult(3);
                    return;
                }
                j4 += j6;
                if (j4 == j5) {
                    return;
                }
                if (bArr.length + j4 > j5) {
                    bArr = new byte[(int) (j5 - j4)];
                }
            }
        } catch (IOException e2) {
            throw new ZipException(e2);
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private RandomAccessFile createFileHandler(ZipModel zipModel, String str) throws ZipException {
        if (zipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getZipFile())) {
            throw new ZipException("input parameter is null in getFilePointer, cannot create file handler to remove file");
        }
        try {
            return new RandomAccessFile(new File(zipModel.getZipFile()), str);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        }
    }

    private RandomAccessFile createSplitZipFileHandler(ZipModel zipModel, int i2) throws ZipException {
        String zipFile;
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot create split file handler");
        }
        if (i2 < 0) {
            throw new ZipException("invlaid part number, cannot create split file handler");
        }
        try {
            String zipFile2 = zipModel.getZipFile();
            if (i2 == zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
                zipFile = zipModel.getZipFile();
            } else if (i2 >= 9) {
                zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z" + (i2 + 1);
            } else {
                zipFile = zipFile2.substring(0, zipFile2.lastIndexOf(StrPool.DOT)) + ".z0" + (i2 + 1);
            }
            File file = new File(zipFile);
            if (Zip4jUtil.checkFileExists(file)) {
                return new RandomAccessFile(file, "r");
            }
            throw new ZipException("split file does not exist: " + zipFile);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:137:0x017e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0179 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x00bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0086 A[Catch: all -> 0x0075, Exception -> 0x0078, IOException -> 0x007b, TRY_LEAVE, TryCatch #22 {IOException -> 0x007b, Exception -> 0x0078, all -> 0x0075, blocks: (B:15:0x003a, B:17:0x0040, B:19:0x004a, B:21:0x0058, B:33:0x0086), top: B:147:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00cc A[Catch: all -> 0x00e0, Exception -> 0x00e8, IOException -> 0x00f0, TRY_ENTER, TRY_LEAVE, TryCatch #5 {IOException -> 0x00f0, blocks: (B:37:0x00ab, B:39:0x00bc, B:44:0x00cc), top: B:130:0x00ab }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initMergeSplitZipFile(net.lingala.zip4j.model.ZipModel r24, java.io.File r25, net.lingala.zip4j.progress.ProgressMonitor r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.util.ArchiveMaintainer.initMergeSplitZipFile(net.lingala.zip4j.model.ZipModel, java.io.File, net.lingala.zip4j.progress.ProgressMonitor):void");
    }

    private OutputStream prepareOutputStreamForMerge(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("outFile is null, cannot create outputstream");
        }
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e2) {
            throw new ZipException(e2);
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void restoreFileName(File file, String str) throws ZipException {
        if (!file.delete()) {
            throw new ZipException("cannot delete old zip file");
        }
        if (!new File(str).renameTo(file)) {
            throw new ZipException("cannot rename modified zip file");
        }
    }

    private void updateSplitEndCentralDirectory(ZipModel zipModel) throws ZipException {
        try {
            if (zipModel == null) {
                throw new ZipException("zip model is null - cannot update end of central directory for split zip model");
            }
            if (zipModel.getCentralDirectory() == null) {
                throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model");
            }
            zipModel.getEndCentralDirRecord().setNoOfThisDisk(0);
            zipModel.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
            zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDir(zipModel.getCentralDirectory().getFileHeaders().size());
            zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(zipModel.getCentralDirectory().getFileHeaders().size());
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void updateSplitFileHeader(ZipModel zipModel, ArrayList arrayList, boolean z2) throws ZipException {
        try {
            if (zipModel.getCentralDirectory() == null) {
                throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model");
            }
            int size = zipModel.getCentralDirectory().getFileHeaders().size();
            int i2 = z2 ? 4 : 0;
            for (int i3 = 0; i3 < size; i3++) {
                long jLongValue = 0;
                for (int i4 = 0; i4 < ((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(i3)).getDiskNumberStart(); i4++) {
                    jLongValue += ((Long) arrayList.get(i4)).longValue();
                }
                ((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(i3)).setOffsetLocalHeader((((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(i3)).getOffsetLocalHeader() + jLongValue) - i2);
                ((FileHeader) zipModel.getCentralDirectory().getFileHeaders().get(i3)).setDiskNumberStart(0);
            }
        } catch (ZipException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ZipException(e3);
        }
    }

    private void updateSplitZip64EndCentralDirLocator(ZipModel zipModel, ArrayList arrayList) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot update split Zip64 end of central directory locator");
        }
        if (zipModel.getZip64EndCentralDirLocator() == null) {
            return;
        }
        zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
        long jLongValue = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            jLongValue += ((Long) arrayList.get(i2)).longValue();
        }
        zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec() + jLongValue);
        zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
    }

    private void updateSplitZip64EndCentralDirRec(ZipModel zipModel, ArrayList arrayList) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot update split Zip64 end of central directory record");
        }
        if (zipModel.getZip64EndCentralDirRecord() == null) {
            return;
        }
        zipModel.getZip64EndCentralDirRecord().setNoOfThisDisk(0);
        zipModel.getZip64EndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
        zipModel.getZip64EndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(zipModel.getEndCentralDirRecord().getTotNoOfEntriesInCentralDir());
        long jLongValue = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            jLongValue += ((Long) arrayList.get(i2)).longValue();
        }
        zipModel.getZip64EndCentralDirRecord().setOffsetStartCenDirWRTStartDiskNo(zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo() + jLongValue);
    }

    private void updateSplitZipModel(ZipModel zipModel, ArrayList arrayList, boolean z2) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot update split zip model");
        }
        zipModel.setSplitArchive(false);
        updateSplitFileHeader(zipModel, arrayList, z2);
        updateSplitEndCentralDirectory(zipModel);
        if (zipModel.isZip64Format()) {
            updateSplitZip64EndCentralDirLocator(zipModel, arrayList);
            updateSplitZip64EndCentralDirRec(zipModel, arrayList);
        }
    }

    public void initProgressMonitorForMergeOp(ZipModel zipModel, ProgressMonitor progressMonitor) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot calculate total work for merge op");
        }
        progressMonitor.setCurrentOperation(4);
        progressMonitor.setFileName(zipModel.getZipFile());
        progressMonitor.setTotalWork(calculateTotalWorkForMergeOp(zipModel));
        progressMonitor.setState(1);
    }

    public void initProgressMonitorForRemoveOp(ZipModel zipModel, FileHeader fileHeader, ProgressMonitor progressMonitor) throws ZipException {
        if (zipModel == null || fileHeader == null || progressMonitor == null) {
            throw new ZipException("one of the input parameters is null, cannot calculate total work");
        }
        progressMonitor.setCurrentOperation(2);
        progressMonitor.setFileName(fileHeader.getFileName());
        progressMonitor.setTotalWork(calculateTotalWorkForRemoveOp(zipModel, fileHeader));
        progressMonitor.setState(1);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x01c6: MOVE (r8 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]) (LINE:455), block:B:89:0x01c4 */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x01c9: MOVE (r8 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]) (LINE:458), block:B:91:0x01c9 */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x01cc: MOVE (r8 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]) (LINE:461), block:B:93:0x01cc */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x041e: MOVE (r2 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]) (LINE:1055), block:B:230:0x041e */
    public java.util.HashMap initRemoveZipFile(net.lingala.zip4j.model.ZipModel r32, net.lingala.zip4j.model.FileHeader r33, net.lingala.zip4j.progress.ProgressMonitor r34) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1097
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.util.ArchiveMaintainer.initRemoveZipFile(net.lingala.zip4j.model.ZipModel, net.lingala.zip4j.model.FileHeader, net.lingala.zip4j.progress.ProgressMonitor):java.util.HashMap");
    }

    public void mergeSplitZipFiles(final ZipModel zipModel, final File file, final ProgressMonitor progressMonitor, boolean z2) throws Throwable {
        if (z2) {
            new Thread(InternalZipConstants.THREAD_NAME) { // from class: net.lingala.zip4j.util.ArchiveMaintainer.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() throws Throwable {
                    try {
                        ArchiveMaintainer.this.initMergeSplitZipFile(zipModel, file, progressMonitor);
                    } catch (ZipException unused) {
                    }
                }
            }.start();
        } else {
            initMergeSplitZipFile(zipModel, file, progressMonitor);
        }
    }

    public HashMap removeZipFile(final ZipModel zipModel, final FileHeader fileHeader, final ProgressMonitor progressMonitor, boolean z2) throws Throwable {
        if (z2) {
            new Thread(InternalZipConstants.THREAD_NAME) { // from class: net.lingala.zip4j.util.ArchiveMaintainer.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() throws Throwable {
                    try {
                        ArchiveMaintainer.this.initRemoveZipFile(zipModel, fileHeader, progressMonitor);
                        progressMonitor.endProgressMonitorSuccess();
                    } catch (ZipException unused) {
                    }
                }
            }.start();
            return null;
        }
        HashMap mapInitRemoveZipFile = initRemoveZipFile(zipModel, fileHeader, progressMonitor);
        progressMonitor.endProgressMonitorSuccess();
        return mapInitRemoveZipFile;
    }

    public void setComment(ZipModel zipModel, String str) throws Throwable {
        if (str == null) {
            throw new ZipException("comment is null, cannot update Zip file with comment");
        }
        if (zipModel == null) {
            throw new ZipException("zipModel is null, cannot update Zip file with comment");
        }
        byte[] bytes = str.getBytes();
        int length = str.length();
        if (Zip4jUtil.isSupportedCharset(InternalZipConstants.CHARSET_COMMENTS_DEFAULT)) {
            try {
                String str2 = new String(str.getBytes(InternalZipConstants.CHARSET_COMMENTS_DEFAULT), InternalZipConstants.CHARSET_COMMENTS_DEFAULT);
                byte[] bytes2 = str2.getBytes(InternalZipConstants.CHARSET_COMMENTS_DEFAULT);
                length = str2.length();
                str = str2;
                bytes = bytes2;
            } catch (UnsupportedEncodingException unused) {
                bytes = str.getBytes();
                length = str.length();
            }
        }
        if (length > 65535) {
            throw new ZipException("comment length exceeds maximum length");
        }
        zipModel.getEndCentralDirRecord().setComment(str);
        zipModel.getEndCentralDirRecord().setCommentBytes(bytes);
        zipModel.getEndCentralDirRecord().setCommentLength(length);
        SplitOutputStream splitOutputStream = null;
        try {
            try {
                HeaderWriter headerWriter = new HeaderWriter();
                SplitOutputStream splitOutputStream2 = new SplitOutputStream(zipModel.getZipFile());
                try {
                    if (zipModel.isZip64Format()) {
                        splitOutputStream2.seek(zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo());
                    } else {
                        splitOutputStream2.seek(zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
                    }
                    headerWriter.finalizeZipFileWithoutValidations(zipModel, splitOutputStream2);
                    try {
                        splitOutputStream2.close();
                    } catch (IOException unused2) {
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    throw new ZipException(e);
                } catch (IOException e3) {
                    e = e3;
                    throw new ZipException(e);
                } catch (Throwable th) {
                    th = th;
                    splitOutputStream = splitOutputStream2;
                    if (splitOutputStream != null) {
                        try {
                            splitOutputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
