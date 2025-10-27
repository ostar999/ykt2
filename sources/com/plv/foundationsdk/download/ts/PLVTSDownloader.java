package com.plv.foundationsdk.download.ts;

import android.os.Process;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class PLVTSDownloader implements IPLVDownloader {
    private static final String TAG = "PLVTSDownloader";
    private final ExecutorService executorService;
    private final List<Future<?>> futureList;
    private final List<PLVMultimedia> multimedias;
    private final String playId;
    private final String videoId;
    private final Object lock = new Object();
    private boolean isCallbackProgressWhereExists = true;
    private long total = 0;
    private IPLVDownloaderSDKListener listener = null;
    private boolean isExecuteAccounting = false;
    private boolean isStop = false;
    private final AtomicInteger downloaded = new AtomicInteger(0);

    public enum TSDownloadReturn {
        NORMAL,
        STOP,
        EXCEPTION,
        DOWNLOAD_LENGTH_ERROR,
        HTTP_CODE_ERROR;

        private int downloadedCount = 0;

        TSDownloadReturn() {
        }

        public static boolean isFailure(TSDownloadReturn tSDownloadReturn) {
            return tSDownloadReturn == EXCEPTION || tSDownloadReturn == DOWNLOAD_LENGTH_ERROR || tSDownloadReturn == HTTP_CODE_ERROR;
        }

        public int getDownloadedCount() {
            return this.downloadedCount;
        }

        public void setDownloadedCount(int i2) {
            this.downloadedCount = i2;
        }
    }

    public class TSDownloadRunnable implements Runnable {
        private final PLVMultimedia multimedia;

        public TSDownloadRunnable(PLVMultimedia pLVMultimedia) {
            this.multimedia = pLVMultimedia;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0401 A[Catch: all -> 0x0443, TryCatch #15 {all -> 0x0443, blocks: (B:69:0x01e4, B:70:0x01ee, B:72:0x01f6, B:93:0x0236, B:97:0x0240, B:98:0x0243, B:118:0x0281, B:120:0x0287, B:160:0x037d, B:162:0x0385, B:182:0x03c3, B:184:0x03cb, B:185:0x03d4, B:203:0x03fb, B:205:0x0401, B:206:0x0404), top: B:268:0x012c }] */
        /* JADX WARN: Removed duplicated region for block: B:251:0x0435 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:259:0x0474 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:266:0x0456 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:279:0x0447 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:281:0x0408 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:283:0x0465 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:285:0x0426 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:299:0x0417 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:311:? A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r14v0 */
        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v2, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r14v3, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r14v4 */
        /* JADX WARN: Type inference failed for: r14v5 */
        /* JADX WARN: Type inference failed for: r14v6, types: [java.io.FileOutputStream] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private com.plv.foundationsdk.download.ts.PLVTSDownloader.TSDownloadReturn downloadM3U8TS(java.util.List<java.lang.String> r17, java.util.List<java.lang.String> r18) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 1154
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.plv.foundationsdk.download.ts.PLVTSDownloader.TSDownloadRunnable.downloadM3U8TS(java.util.List, java.util.List):com.plv.foundationsdk.download.ts.PLVTSDownloader$TSDownloadReturn");
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            if (PLVTSDownloader.this.isStop) {
                return;
            }
            Process.setThreadPriority(10);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            TSDownloadReturn tSDownloadReturnDownloadM3U8TS = null;
            for (int i2 = 0; i2 < 3; i2++) {
                if (PLVTSDownloader.this.isStop) {
                    return;
                }
                arrayList.clear();
                arrayList2.clear();
                tSDownloadReturnDownloadM3U8TS = downloadM3U8TS(arrayList, arrayList2);
                if (PLVTSDownloader.this.isStop) {
                    return;
                }
                if (tSDownloadReturnDownloadM3U8TS == TSDownloadReturn.NORMAL) {
                    break;
                }
                if (tSDownloadReturnDownloadM3U8TS == TSDownloadReturn.STOP) {
                    return;
                }
                if (TSDownloadReturn.isFailure(tSDownloadReturnDownloadM3U8TS)) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(300L);
                    } catch (InterruptedException e2) {
                        PLVCommonLog.e(PLVTSDownloader.TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1));
                    }
                    if (PLVTSDownloader.this.isStop) {
                        return;
                    }
                }
            }
            if (TSDownloadReturn.isFailure(tSDownloadReturnDownloadM3U8TS)) {
                PLVTSDownloader.this.stop();
                PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_TS_ERROR, PLVDownloaderErrorReason.DOWNLOAD_TS_ERROR, arrayList, arrayList2, PLVTSDownloader.this.listener);
                return;
            }
            synchronized (PLVTSDownloader.this.lock) {
                if (tSDownloadReturnDownloadM3U8TS.getDownloadedCount() == PLVTSDownloader.this.total && !PLVTSDownloader.this.isExecuteAccounting) {
                    PLVTSDownloader.this.isExecuteAccounting = true;
                    ArrayList arrayList3 = new ArrayList();
                    List list = PLVTSDownloader.this.multimedias;
                    int size = list.size();
                    boolean z2 = true;
                    for (int i3 = 0; i3 < size; i3++) {
                        if (PLVTSDownloader.this.isStop) {
                            return;
                        }
                        PLVMultimedia pLVMultimedia = (PLVMultimedia) list.get(i3);
                        File file = new File(new File(pLVMultimedia.getFileDir()), pLVMultimedia.getFileName());
                        if (!file.exists()) {
                            PLVCommonLog.e(PLVTSDownloader.TAG, String.format("%s没有下载成功", pLVMultimedia.toString()));
                            arrayList3.add(file.getAbsolutePath() + " is not exists");
                            z2 = false;
                        }
                    }
                    if (!z2) {
                        arrayList3.add("下载失败，ts没有下载完全");
                        arrayList3.add(this.multimedia.toString());
                        PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_TS_ERROR, PLVDownloaderErrorReason.DOWNLOAD_TS_ERROR, null, arrayList3, PLVTSDownloader.this.listener);
                    } else if (PLVTSDownloader.this.listener != null) {
                        PLVTSDownloader.this.listener.onDownloadSuccess();
                    }
                }
            }
        }
    }

    public PLVTSDownloader(String str, String str2, List<PLVMultimedia> list) {
        this.playId = str;
        this.videoId = str2;
        this.multimedias = list;
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(iAvailableProcessors == 0 ? 2 : iAvailableProcessors);
        this.futureList = new ArrayList();
    }

    public void addDownloadListener(IPLVDownloaderSDKListener iPLVDownloaderSDKListener) {
        this.listener = iPLVDownloaderSDKListener;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void deleteDownloadContent() {
        if (isDownloading()) {
            stop();
        }
        Iterator<PLVMultimedia> it = this.multimedias.iterator();
        while (it.hasNext()) {
            try {
                FileUtils.deleteDir(it.next().getFileDir());
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
            }
        }
    }

    public void destroy() {
        this.listener = null;
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public long getTotal() {
        return this.total;
    }

    public void isCallbackProgressWhereExists(boolean z2) {
        this.isCallbackProgressWhereExists = z2;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public boolean isDownloading() {
        Iterator<Future<?>> it = this.futureList.iterator();
        while (it.hasNext()) {
            if (!it.next().isDone()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void start() {
        List<PLVMultimedia> list = this.multimedias;
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("没有ts文件下载列表");
            PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_TS_ERROR, 1010100, null, arrayList, this.listener);
            return;
        }
        Iterator<PLVMultimedia> it = this.multimedias.iterator();
        while (it.hasNext()) {
            PLVCommonLog.d(TAG, it.next().toString());
        }
        File file = new File(this.multimedias.get(0).getFileDir());
        if (!PLVDownloadDirUtil.mkdirs(file)) {
            try {
                if (!file.mkdirs()) {
                    String str = "解压目录创建失败:" + file.getAbsolutePath();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(str);
                    arrayList2.add(file.getAbsolutePath());
                    PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_TS_ERROR, PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, null, arrayList2, this.listener);
                    return;
                }
            } catch (Exception e2) {
                String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(exceptionFullMessage);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(file.getAbsolutePath());
                PLVDownloaderSDKUtils.downloadError(PLVDownloadEvent.DOWNLOAD_TS_ERROR, PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, arrayList3, arrayList4, this.listener);
                return;
            }
        }
        this.isExecuteAccounting = false;
        this.total = this.multimedias.size();
        this.downloaded.set(0);
        Iterator<PLVMultimedia> it2 = this.multimedias.iterator();
        while (it2.hasNext()) {
            this.futureList.add(this.executorService.submit(new TSDownloadRunnable(it2.next())));
        }
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void stop() {
        this.isStop = true;
        Iterator<Future<?>> it = this.futureList.iterator();
        while (it.hasNext()) {
            it.next().cancel(false);
        }
    }
}
