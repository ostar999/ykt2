package com.plv.foundationsdk.download.alone;

import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.download.IPLVDownloader;
import com.plv.foundationsdk.download.PLVDownloadEvent;
import com.plv.foundationsdk.download.PLVDownloaderErrorReason;
import com.plv.foundationsdk.download.bean.PLVMultimedia;
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

/* loaded from: classes4.dex */
public class PLVAloneDownloader implements IPLVDownloader {
    private static final int DOWNLOAD_ALONE_FAIL = 2;
    private static final int DOWNLOAD_ALONE_STOP = 3;
    private static final int DOWNLOAD_ALONE_SUCCESS = 1;
    private static final String TAG = "PLVAloneDownloader";
    private final PLVMultimedia multimedia;
    private final String playId;
    private FutureTask task;
    private final String videoId;
    private IPLVDownloaderSDKListener listener = null;
    private volatile boolean isStop = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadVideoResult {
    }

    public PLVAloneDownloader(String str, String str2, PLVMultimedia pLVMultimedia) {
        this.playId = str;
        this.videoId = str2;
        this.multimedia = pLVMultimedia;
    }

    private File createTempDownloadFile(PLVMultimedia pLVMultimedia) {
        File file = new File(pLVMultimedia.getFileDir(), pLVMultimedia.getFileName().substring(0, pLVMultimedia.getFileName().indexOf(StrPool.DOT)) + "temp");
        PLVCommonLog.d(TAG, "创建下载临时文件：" + file.getAbsolutePath());
        return file;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x01f1, code lost:
    
        r28.add(r0);
        r28.add(r4.getAbsolutePath());
        r28.add("下载的长度:" + r11);
        r28.add("需要下载的长度:" + r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0224, code lost:
    
        r20.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0227, code lost:
    
        r3 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0229, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x022a, code lost:
    
        r3 = -1;
        com.plv.foundationsdk.log.PLVCommonLog.e(r14, com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils.getExceptionFullMessage(r0, -1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0256, code lost:
    
        r2 = r5;
        r16 = r8;
        r5 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01cc, code lost:
    
        r20 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01d0, code lost:
    
        if (r11 == r9) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01d2, code lost:
    
        r0 = "下载长度不正确，下载的长度:" + r11 + "，需要下载的长度:" + r9;
        com.plv.foundationsdk.log.PLVCommonLog.d(r14, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01ef, code lost:
    
        if (r28 == null) goto L244;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0318 A[Catch: all -> 0x034d, TRY_LEAVE, TryCatch #19 {all -> 0x034d, blocks: (B:170:0x030f, B:172:0x0318), top: B:240:0x030f }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0372 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x032f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x033e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0351 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0363 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x031d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:268:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:273:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v6, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int downloadVideo(java.io.File r26, java.util.ArrayList<java.lang.String> r27, java.util.ArrayList<java.lang.String> r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 914
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.download.alone.PLVAloneDownloader.downloadVideo(java.io.File, java.util.ArrayList, java.util.ArrayList):int");
    }

    private String getUserAgent() {
        return PLVUAClient.getUserAgent();
    }

    public void addDownloadListener(IPLVDownloaderSDKListener iPLVDownloaderSDKListener) {
        this.listener = iPLVDownloaderSDKListener;
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
        FutureTask futureTask = new FutureTask(new Runnable() { // from class: com.plv.foundationsdk.download.alone.PLVAloneDownloader.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                if (PLVAloneDownloader.this.multimedia == null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("没有视频文件下载");
                    arrayList.add(PLVAloneDownloader.this.videoId);
                    PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_VIDEO_ERROR, 1010100, null, arrayList, PLVAloneDownloader.this.listener);
                    return;
                }
                File file = new File(PLVAloneDownloader.this.multimedia.getFileDir(), PLVAloneDownloader.this.multimedia.getFileName());
                if (file.exists()) {
                    if (PLVAloneDownloader.this.listener != null) {
                        PLVAloneDownloader.this.listener.onDownloadProgress(100L, 100L);
                        PLVAloneDownloader.this.listener.onDownloadSuccess();
                        return;
                    }
                    return;
                }
                File file2 = new File(PLVAloneDownloader.this.multimedia.getFileDir());
                if (!PLVDownloadDirUtil.mkdirs(file2)) {
                    try {
                        if (!file2.mkdirs()) {
                            String str = "下载目录创建失败:" + file2.getAbsolutePath();
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(str);
                            arrayList2.add(file2.getAbsolutePath());
                            PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_VIDEO_ERROR, PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, null, arrayList2, PLVAloneDownloader.this.listener);
                            return;
                        }
                    } catch (Exception e2) {
                        String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                        ArrayList arrayList3 = new ArrayList();
                        arrayList3.add(exceptionFullMessage);
                        ArrayList arrayList4 = new ArrayList();
                        arrayList4.add(file2.getAbsolutePath());
                        PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_VIDEO_ERROR, PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, arrayList3, arrayList4, PLVAloneDownloader.this.listener);
                        return;
                    }
                }
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                int iDownloadVideo = 3;
                for (int i2 = 0; i2 < 3; i2++) {
                    arrayList5.clear();
                    arrayList6.clear();
                    iDownloadVideo = PLVAloneDownloader.this.downloadVideo(file, arrayList5, arrayList6);
                    if (iDownloadVideo != 2) {
                        if (iDownloadVideo == 3 || iDownloadVideo == 1) {
                            break;
                        }
                    } else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(300L);
                        } catch (InterruptedException e3) {
                            PLVCommonLog.e(PLVAloneDownloader.TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e3, -1));
                            return;
                        }
                    }
                }
                if (iDownloadVideo == 2) {
                    arrayList6.add("video file download error");
                    PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_VIDEO_ERROR, PLVDownloaderErrorReason.VIDEO_DOWNLOAD_ERROR, arrayList5, arrayList6, PLVAloneDownloader.this.listener);
                } else {
                    if (iDownloadVideo == 3 || PLVAloneDownloader.this.listener == null) {
                        return;
                    }
                    PLVAloneDownloader.this.listener.onDownloadSuccess();
                }
            }
        }, Boolean.TRUE);
        this.task = futureTask;
        futureTask.run();
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void stop() {
        this.isStop = true;
        FutureTask futureTask = this.task;
        if (futureTask != null) {
            futureTask.cancel(true);
        }
    }
}
