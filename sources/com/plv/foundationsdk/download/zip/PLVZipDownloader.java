package com.plv.foundationsdk.download.zip;

import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.CharsetUtil;
import com.plv.foundationsdk.download.IPLVDownloader;
import com.plv.foundationsdk.download.PLVDownloadEvent;
import com.plv.foundationsdk.download.PLVDownloaderErrorReason;
import com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener;
import com.plv.foundationsdk.download.utils.PLVDownloadDirUtil;
import com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils;
import com.plv.foundationsdk.download.utils.PLVDownloaderSDKUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import net.lingala.zip4j.core.ZipFile;

/* loaded from: classes4.dex */
public class PLVZipDownloader implements IPLVDownloader {
    private static final int DOWNLOAD_ZIP_FAIL = 2;
    private static final int DOWNLOAD_ZIP_STOP = 3;
    private static final int DOWNLOAD_ZIP_SUCCESS = 1;
    private static final String TAG = "PLVZipDownloader";
    private static final int UNZIP_FILE_RESULT_FAIL = 2;
    private static final int UNZIP_FILE_RESULT_STOP = 3;
    private static final int UNZIP_FILE_RESULT_SUCCESS = 1;
    private final PLVZipMultimedia multimedia;
    private FutureTask task;
    private IPLVDownloaderSDKListener listener = null;
    private IPLVDownloaderUnzipListener unzipListener = null;
    private volatile boolean isStop = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadZipResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UnzipFileResult {
    }

    public PLVZipDownloader(@NonNull PLVZipMultimedia pLVZipMultimedia) {
        this.multimedia = pLVZipMultimedia;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doDownload(File file) throws Throwable {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        int iDownloadZip = 3;
        for (int i2 = 0; i2 < 3; i2++) {
            arrayList.clear();
            arrayList2.clear();
            iDownloadZip = downloadZip(file, arrayList, arrayList2);
            if (iDownloadZip != 2) {
                if (iDownloadZip == 3 || iDownloadZip == 1) {
                    break;
                }
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(700L);
                } catch (InterruptedException e2) {
                    PLVCommonLog.d(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1));
                    return false;
                }
            }
        }
        if (iDownloadZip != 2) {
            return iDownloadZip != 3;
        }
        PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_ZIP_ERROR, PLVDownloaderErrorReason.ZIP_DOWNLOAD_ERROR, arrayList, arrayList2, this.listener);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doUnzip(File file) throws Throwable {
        PLVCommonLog.d(TAG, "开始解压：" + file);
        if (!file.exists()) {
            PLVCommonLog.e(TAG, file + "不存在");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        int iUnzipFileApache = unzipFileApache(file, new File(this.multimedia.getFileDir()), arrayList);
        if (iUnzipFileApache == 2) {
            iUnzipFileApache = unzipFile(file, new File(this.multimedia.getFileDir()), arrayList);
        }
        if (iUnzipFileApache == 2) {
            iUnzipFileApache = unzipFile4j(file, new File(this.multimedia.getFileDir()), arrayList);
        }
        if (iUnzipFileApache != 2) {
            return iUnzipFileApache != 3;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("fileLength=" + file.length());
        arrayList2.add(this.multimedia.toString());
        arrayList2.add(String.valueOf(System.currentTimeMillis()));
        PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_ZIP_ERROR, PLVDownloaderErrorReason.UNZIP_FILE_ERROR, arrayList, arrayList2, this.listener);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0238, code lost:
    
        r17 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x023c, code lost:
    
        if (r12 == r6) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x023e, code lost:
    
        r0 = "下载长度不正确，下载的长度:" + r12 + "，需要下载的长度:" + r6;
        com.plv.foundationsdk.log.PLVCommonLog.d(com.plv.foundationsdk.download.zip.PLVZipDownloader.TAG, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x025b, code lost:
    
        if (r29 == null) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x025d, code lost:
    
        r29.add(r0);
        r29.add(r4.getAbsolutePath());
        r29.add("下载的长度:" + r12);
        r29.add("需要下载的长度:" + r6);
        r29.add(java.lang.String.valueOf(java.lang.System.currentTimeMillis()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0299, code lost:
    
        r17.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x029c, code lost:
    
        r3 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x029e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x029f, code lost:
    
        r3 = -1;
        com.plv.foundationsdk.log.PLVCommonLog.d(com.plv.foundationsdk.download.zip.PLVZipDownloader.TAG, com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils.getExceptionFullMessage(r0, -1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x02cb, code lost:
    
        r2 = r5;
        r16 = r8;
        r5 = r17;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0386 A[Catch: all -> 0x03c8, TryCatch #21 {all -> 0x03c8, blocks: (B:172:0x037b, B:174:0x0386, B:176:0x038b), top: B:244:0x037b }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x038b A[Catch: all -> 0x03c8, TRY_LEAVE, TryCatch #21 {all -> 0x03c8, blocks: (B:172:0x037b, B:174:0x0386, B:176:0x038b), top: B:244:0x037b }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x03aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x03b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x03d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x03e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0398 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:274:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:280:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v11, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8, types: [java.io.BufferedOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r10v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int downloadZip(java.io.File r27, java.util.ArrayList<java.lang.String> r28, java.util.ArrayList<java.lang.String> r29) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1043
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.download.zip.PLVZipDownloader.downloadZip(java.io.File, java.util.ArrayList, java.util.ArrayList):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ba, code lost:
    
        com.plv.foundationsdk.log.PLVCommonLog.d(com.plv.foundationsdk.download.zip.PLVZipDownloader.TAG, com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils.getExceptionFullMessage(r0, -1));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int unzipFile(java.io.File r23, java.io.File r24, java.util.ArrayList<java.lang.String> r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.download.zip.PLVZipDownloader.unzipFile(java.io.File, java.io.File, java.util.ArrayList):int");
    }

    private int unzipFile4j(File file, File file2, ArrayList<String> arrayList) throws Throwable {
        File file3 = new File(file.getAbsolutePath());
        File file4 = new File(file2.getAbsolutePath());
        try {
            ZipFile zipFile = new ZipFile(file3);
            zipFile.setFileNameCharset(CharsetUtil.GBK);
            if (!zipFile.isValidZipFile()) {
                PLVCommonLog.e(TAG, "压缩文件不合法,可能被损坏");
                return 2;
            }
            if (file4.isDirectory() && !file4.exists()) {
                file4.mkdirs();
            }
            zipFile.extractAll(file2.getAbsolutePath());
            File[] fileArrListFiles = file2.listFiles();
            if (fileArrListFiles != null) {
                for (File file5 : fileArrListFiles) {
                    if (this.isStop) {
                        return 3;
                    }
                    File parentFile = file5.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    if (file5.isFile() && file5.getAbsolutePath().endsWith(".ts")) {
                        file5.renameTo(new File(file5.getParent(), file5.getName().replace(StrPool.DOT, StrPool.UNDERLINE)));
                    }
                }
            }
            IPLVDownloaderUnzipListener iPLVDownloaderUnzipListener = this.unzipListener;
            if (iPLVDownloaderUnzipListener == null) {
                return 1;
            }
            iPLVDownloaderUnzipListener.onProgress(100);
            return 1;
        } catch (Exception e2) {
            String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
            PLVCommonLog.d(TAG, exceptionFullMessage);
            if (arrayList != null) {
                arrayList.add(exceptionFullMessage);
            }
            return 2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00c4, code lost:
    
        com.plv.foundationsdk.log.PLVCommonLog.d(com.plv.foundationsdk.download.zip.PLVZipDownloader.TAG, com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils.getExceptionFullMessage(r0, -1));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int unzipFileApache(java.io.File r23, java.io.File r24, java.util.ArrayList<java.lang.String> r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.download.zip.PLVZipDownloader.unzipFileApache(java.io.File, java.io.File, java.util.ArrayList):int");
    }

    public void addDownloadListener(IPLVDownloaderSDKListener iPLVDownloaderSDKListener) {
        this.listener = iPLVDownloaderSDKListener;
    }

    public void addUnzipListener(IPLVDownloaderUnzipListener iPLVDownloaderUnzipListener) {
        this.unzipListener = iPLVDownloaderUnzipListener;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void deleteDownloadContent() {
        if (isDownloading()) {
            stop();
        }
        try {
            FileUtils.deleteDir(this.multimedia.getFileDir());
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    public void destroy() {
        this.listener = null;
        this.unzipListener = null;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public boolean isDownloading() {
        if (this.task == null) {
            return false;
        }
        return !r0.isDone();
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void start() {
        FutureTask futureTask = this.task;
        if (futureTask != null) {
            futureTask.cancel(true);
            this.task = null;
        }
        FutureTask futureTask2 = new FutureTask(new Runnable() { // from class: com.plv.foundationsdk.download.zip.PLVZipDownloader.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                PLVCommonLog.d(PLVZipDownloader.TAG, "start zip download");
                File file = new File(PLVZipDownloader.this.multimedia.getFileDir(), PLVZipDownloader.this.multimedia.getFileName());
                if (file.exists()) {
                    if (PLVZipDownloader.this.doUnzip(file)) {
                        if (PLVZipDownloader.this.unzipListener != null) {
                            PLVZipDownloader.this.unzipListener.onDone();
                        }
                        file.delete();
                        if (PLVZipDownloader.this.listener != null) {
                            PLVZipDownloader.this.listener.onDownloadProgress(100L, 100L);
                            PLVZipDownloader.this.listener.onDownloadSuccess();
                            return;
                        }
                        return;
                    }
                    return;
                }
                File file2 = new File(PLVZipDownloader.this.multimedia.getFileDir());
                if (!file2.exists() && !PLVDownloadDirUtil.mkdirs(file2)) {
                    try {
                        if (!file2.mkdirs()) {
                            String str = "解压目录创建失败:" + file2.getAbsolutePath();
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(str);
                            arrayList.add(PLVZipDownloader.this.multimedia.toString());
                            PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_ZIP_ERROR, PLVDownloaderErrorReason.CREATE_UNZIP_DIR_ERROR, null, arrayList, PLVZipDownloader.this.listener);
                            return;
                        }
                    } catch (Exception e2) {
                        String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(exceptionFullMessage);
                        ArrayList arrayList3 = new ArrayList();
                        arrayList3.add(PLVZipDownloader.this.multimedia.toString());
                        PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_ZIP_ERROR, PLVDownloaderErrorReason.CREATE_UNZIP_DIR_ERROR, arrayList2, arrayList3, PLVZipDownloader.this.listener);
                        return;
                    }
                }
                if (PLVZipDownloader.this.doDownload(file) && PLVZipDownloader.this.doUnzip(file)) {
                    if (PLVZipDownloader.this.unzipListener != null) {
                        PLVZipDownloader.this.unzipListener.onDone();
                    }
                    file.delete();
                    if (PLVZipDownloader.this.listener != null) {
                        PLVZipDownloader.this.listener.onDownloadSuccess();
                    }
                }
            }
        }, Boolean.TRUE);
        this.task = futureTask2;
        futureTask2.run();
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void stop() {
        this.isStop = true;
        FutureTask futureTask = this.task;
        if (futureTask != null) {
            futureTask.cancel(true);
            this.task = null;
        }
    }
}
