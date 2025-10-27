package com.plv.livescenes.download;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.hjq.permissions.Permission;
import com.just.agentweb.DefaultWebClient;
import com.plv.foundationsdk.download.PLVDownloaderErrorReason;
import com.plv.foundationsdk.download.PLVDownloaderSpeed;
import com.plv.foundationsdk.download.alone.PLVAloneDownloader;
import com.plv.foundationsdk.download.bean.PLVMultimedia;
import com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener;
import com.plv.foundationsdk.download.ts.PLVTSDownloader;
import com.plv.foundationsdk.download.utils.PLVDownloadDirUtil;
import com.plv.foundationsdk.download.utils.PLVDownloadErrorMessageUtils;
import com.plv.foundationsdk.download.zip.IPLVDownloaderUnzipListener;
import com.plv.foundationsdk.download.zip.PLVZipDownloader;
import com.plv.foundationsdk.download.zip.PLVZipMultimedia;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.utils.PLVDnsUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.download.IPLVDownloader;
import com.plv.livescenes.download.api.PLVPlaybackDownloadApiManager;
import com.plv.livescenes.download.listener.IPLVDownloaderSpeedListener;
import com.plv.livescenes.model.PLVPlaybackVideoVO;
import com.plv.livescenes.model.PLVTempStorePlaybackVideoVO;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import com.plv.thirdpart.blankj.utilcode.util.NetworkUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.ResponseBody;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes4.dex */
public class PLVDownloader extends IPLVDownloaderListenerEvent implements IPLVDownloader {
    private static final String TAG = "PLVDownloader";
    private final String channelId;
    private final File downloadDir;
    private final String key;
    PLVPlaybackCacheVO playbackCacheVO;
    private final PLVPlaybackListType playbackListType;
    private PLVProgressGetter progressGetter;
    private final String videoPoolId;
    private final String viewerId;
    private long videoProgress = 0;
    private long videoTotal = 0;
    private int downloadTotal = 0;
    private int zipTotal = 0;
    private List<com.plv.foundationsdk.download.IPLVDownloader> downloaders = new ArrayList();
    private final DownloadDataSourceParam downloadDataSourceParam = new DownloadDataSourceParam();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int mBitrate = 1;
    private final String playId = PLVUtils.getPid();

    public static class DownloadDataSourceParam {
        private boolean enableCache;
        private String offlineJsUrl;

        @Nullable
        private Long videoSize;
        private String videoUrl;

        @Nullable
        private Long zipSize;
        private String zipUrl;

        private DownloadDataSourceParam() {
            this.enableCache = false;
        }

        public DownloadDataSourceParam fillByPlaybackVideoVO(PLVPlaybackVideoVO pLVPlaybackVideoVO) {
            this.enableCache = pLVPlaybackVideoVO.getData().isPlaybackCacheEnable();
            this.videoUrl = pLVPlaybackVideoVO.getData().getVideoCache().getVideoUrl();
            this.zipUrl = pLVPlaybackVideoVO.getData().getVideoCache().getZipUrl();
            this.offlineJsUrl = pLVPlaybackVideoVO.getData().getOfflineJSUrl();
            this.zipSize = pLVPlaybackVideoVO.getData().getVideoCache().getZipSize();
            this.videoSize = pLVPlaybackVideoVO.getData().getVideoCache().getVideoSize();
            return this;
        }

        public DownloadDataSourceParam fillByTempStorePlaybackVideoVO(PLVTempStorePlaybackVideoVO pLVTempStorePlaybackVideoVO) {
            this.enableCache = pLVTempStorePlaybackVideoVO.getData().isPlaybackCacheEnable();
            this.videoUrl = pLVTempStorePlaybackVideoVO.getData().getVideoCache().getVideoUrl();
            this.zipUrl = pLVTempStorePlaybackVideoVO.getData().getVideoCache().getZipUrl();
            this.offlineJsUrl = pLVTempStorePlaybackVideoVO.getData().getVideoCache().getOfflineJSUrl();
            this.zipSize = pLVTempStorePlaybackVideoVO.getData().getVideoCache().getZipSize();
            this.videoSize = pLVTempStorePlaybackVideoVO.getData().getVideoCache().getVideoSize();
            return this;
        }
    }

    public PLVDownloader(IPLVDownloader.Builder builder) {
        this.videoPoolId = builder.videoPoolId;
        this.channelId = builder.channelId;
        this.downloadDir = builder.downloadDir;
        this.playbackListType = builder.playbackListType;
        this.viewerId = builder.viewerId;
        this.key = builder.createDownloadKey();
        this.downloaderSpeed = new PLVDownloaderSpeed();
    }

    public static /* synthetic */ long access$1108(PLVDownloader pLVDownloader) {
        long j2 = pLVDownloader.videoTotal;
        pLVDownloader.videoTotal = 1 + j2;
        return j2;
    }

    private boolean checkDirStatus() throws Throwable {
        File file = this.downloadDir;
        if (file == null) {
            PLVCommonLog.e(TAG, "下载文件目录未设置");
            callProgressListenerFail(PLVDownloaderErrorReason.DOWNLOAD_DIR_IS_NULL, this.playId, this.videoPoolId);
            return false;
        }
        if (!PLVDownloadDirUtil.mkdirs(file)) {
            try {
                if (!this.downloadDir.mkdirs()) {
                    String str = "不能创建下载目录" + this.downloadDir.getAbsolutePath();
                    PLVCommonLog.e(TAG, str);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str);
                    callProgressListenerFail(PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, this.playId, this.videoPoolId, arrayList, null);
                    return true;
                }
            } catch (Exception e2) {
                String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                PLVCommonLog.e(TAG, exceptionFullMessage);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(exceptionFullMessage);
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(this.downloadDir.getAbsolutePath());
                callProgressListenerFail(PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, this.playId, this.videoPoolId, arrayList2, arrayList3);
                return true;
            }
        }
        return false;
    }

    private boolean checkPermission() {
        if (!NetworkUtils.isConnected()) {
            PLVCommonLog.e(TAG, "没有打开网络");
            callProgressListenerFail(PLVDownloaderErrorReason.NETWORK_DENIED, this.playId, this.videoPoolId);
            return true;
        }
        if (ContextCompat.checkSelfPermission(Utils.getApp(), Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            return false;
        }
        PLVCommonLog.e(TAG, "拒绝写入SD卡");
        callProgressListenerFail(PLVDownloaderErrorReason.WRITE_EXTERNAL_STORAGE_DENIED, this.playId, this.videoPoolId);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkSDCardStatus() {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.livescenes.download.PLVDownloader.checkSDCardStatus():boolean");
    }

    private void createDownloadTask() {
        if (this.playbackListType == PLVPlaybackListType.TEMP_STORE) {
            createTempStorePlaybackDownloadTask();
        } else {
            createPlaybackDownloadTask();
        }
    }

    private String createFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean createM3u8File(String str, String str2) throws Throwable {
        PLVCommonLog.d(TAG, "createM3u8File");
        File file = new File(str);
        if (!PLVDownloadDirUtil.mkdirs(file)) {
            try {
                if (!file.mkdirs()) {
                    String str3 = "不能创建保存目录" + file.getAbsolutePath();
                    PLVCommonLog.e(TAG, str3);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str3);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(file.getAbsolutePath());
                    callProgressListenerFail(PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, this.playId, this.videoPoolId, arrayList, arrayList2);
                    return false;
                }
            } catch (Exception e2) {
                String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                PLVCommonLog.e(TAG, exceptionFullMessage);
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(exceptionFullMessage);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(file.getAbsolutePath());
                callProgressListenerFail(PLVDownloaderErrorReason.CAN_NOT_CREATE_DIRECTORY, this.playId, this.videoPoolId, arrayList3, arrayList4);
                return false;
            }
        }
        File file2 = new File(file, this.videoPoolId + ".json");
        if (!file2.exists()) {
            try {
                file2.getParentFile().mkdirs();
                if (!file2.createNewFile()) {
                    String str4 = "创建m3u8文件失败:" + file2.getAbsolutePath();
                    PLVCommonLog.e(TAG, str4);
                    ArrayList arrayList5 = new ArrayList();
                    arrayList5.add(str4);
                    ArrayList arrayList6 = new ArrayList();
                    arrayList6.add(file2.getAbsolutePath());
                    callProgressListenerFail(PLVDownloaderErrorReason.CREATE_M3U8_FILE_ERROR, this.playId, this.videoPoolId, arrayList5, arrayList6);
                    return false;
                }
            } catch (Exception e3) {
                String exceptionFullMessage2 = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e3, -1);
                PLVCommonLog.e(TAG, exceptionFullMessage2);
                ArrayList arrayList7 = new ArrayList();
                arrayList7.add(exceptionFullMessage2);
                ArrayList arrayList8 = new ArrayList();
                arrayList8.add(file2.getAbsolutePath());
                callProgressListenerFail(PLVDownloaderErrorReason.CREATE_M3U8_FILE_ERROR, this.playId, this.videoPoolId, arrayList7, arrayList8);
                return false;
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                try {
                    fileOutputStream2.write(str2.getBytes());
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e4) {
                        PLVCommonLog.e(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e4, -1));
                    }
                    this.videoProgress++;
                    return true;
                } catch (Exception e5) {
                    e = e5;
                    fileOutputStream = fileOutputStream2;
                    PLVCommonLog.e(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e, -1));
                    callProgressListenerFail(PLVDownloaderErrorReason.WRITE_M3U8_FILE_ERROR, this.playId, this.videoPoolId, PLVSugarUtil.listOf(Log.getStackTraceString(e)));
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            PLVCommonLog.e(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e6, -1));
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e7) {
                            PLVCommonLog.e(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e7, -1));
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e8) {
            e = e8;
        }
    }

    private void createPlaybackDownloadTask() {
        this.compositeDisposable.add(PLVPlaybackDownloadApiManager.getInstance().requestPlaybackVideoDetail(this.channelId, this.videoPoolId).subscribeOn(Schedulers.io()).subscribe(new Consumer<PLVPlaybackVideoVO>() { // from class: com.plv.livescenes.download.PLVDownloader.3
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackVideoVO pLVPlaybackVideoVO) throws Exception {
                if (pLVPlaybackVideoVO == null || pLVPlaybackVideoVO.getData() == null || pLVPlaybackVideoVO.getData().getVideoCache() == null) {
                    PLVDownloader pLVDownloader = PLVDownloader.this;
                    pLVDownloader.callProgressListenerFail(PLVDownloaderErrorReason.FETCH_VIDEO_INFO_FAILURE, pLVDownloader.playId, PLVDownloader.this.videoPoolId, null, null);
                    return;
                }
                PLVDownloader.this.downloadDataSourceParam.fillByPlaybackVideoVO(pLVPlaybackVideoVO);
                if (pLVPlaybackVideoVO.getData().isPlaybackCacheEnable()) {
                    PLVDownloader pLVDownloader2 = PLVDownloader.this;
                    if (pLVDownloader2.beforeStartListener != null) {
                        pLVDownloader2.playbackCacheVO = new PLVPlaybackCacheVO().fillDataByPlaybackVideoVO(pLVPlaybackVideoVO);
                        PLVDownloader pLVDownloader3 = PLVDownloader.this;
                        pLVDownloader3.callBeforeStartListener(pLVDownloader3, pLVDownloader3.playbackCacheVO);
                    }
                    PLVDownloader.this.progressGetter = new PLVProgressGetter(!TextUtils.isEmpty(PLVDownloader.this.downloadDataSourceParam.zipUrl));
                    PLVDownloader.this.videoDownload();
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.download.PLVDownloader.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(PLVDownloaderErrorReason.FETCH_VIDEO_INFO_FAILURE, pLVDownloader.playId, PLVDownloader.this.videoPoolId, PLVSugarUtil.listOf(Log.getStackTraceString(th)));
            }
        }));
    }

    private void createTempStorePlaybackDownloadTask() {
        this.compositeDisposable.add(PLVPlaybackDownloadApiManager.getInstance().requestTempStorePlaybackVideoDetail(this.channelId, this.videoPoolId).subscribeOn(Schedulers.io()).subscribe(new Consumer<PLVTempStorePlaybackVideoVO>() { // from class: com.plv.livescenes.download.PLVDownloader.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVTempStorePlaybackVideoVO pLVTempStorePlaybackVideoVO) throws Exception {
                if (pLVTempStorePlaybackVideoVO == null || pLVTempStorePlaybackVideoVO.getData() == null || pLVTempStorePlaybackVideoVO.getData().getVideoCache() == null) {
                    PLVDownloader pLVDownloader = PLVDownloader.this;
                    pLVDownloader.callProgressListenerFail(PLVDownloaderErrorReason.FETCH_VIDEO_INFO_FAILURE, pLVDownloader.playId, PLVDownloader.this.videoPoolId, null, null);
                    return;
                }
                PLVDownloader.this.downloadDataSourceParam.fillByTempStorePlaybackVideoVO(pLVTempStorePlaybackVideoVO);
                if (pLVTempStorePlaybackVideoVO.getData().isPlaybackCacheEnable()) {
                    PLVDownloader pLVDownloader2 = PLVDownloader.this;
                    if (pLVDownloader2.beforeStartListener != null) {
                        pLVDownloader2.playbackCacheVO = new PLVPlaybackCacheVO().fillDataByPlaybackVideoVO(pLVTempStorePlaybackVideoVO);
                        PLVDownloader pLVDownloader3 = PLVDownloader.this;
                        pLVDownloader3.callBeforeStartListener(pLVDownloader3, pLVDownloader3.playbackCacheVO);
                    }
                    PLVDownloader.this.progressGetter = new PLVProgressGetter(!TextUtils.isEmpty(PLVDownloader.this.downloadDataSourceParam.zipUrl));
                    PLVDownloader.this.videoDownload();
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.download.PLVDownloader.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(PLVDownloaderErrorReason.FETCH_VIDEO_INFO_FAILURE, pLVDownloader.playId, PLVDownloader.this.videoPoolId, PLVSugarUtil.listOf(Log.getStackTraceString(th)));
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadJs() {
        String strFixUrlScheme = fixUrlScheme(this.downloadDataSourceParam.offlineJsUrl);
        PLVCommonLog.d(TAG, "downloadJs:" + strFixUrlScheme);
        final String jsStoragePath = getJsStoragePath();
        final PLVZipDownloader pLVZipDownloader = new PLVZipDownloader(new PLVZipMultimedia(1, strFixUrlScheme, jsStoragePath, createFileName(strFixUrlScheme)));
        this.downloaders.add(pLVZipDownloader);
        pLVZipDownloader.addDownloadListener(new IPLVDownloaderSDKListener() { // from class: com.plv.livescenes.download.PLVDownloader.11
            private void stopDownloader() {
                PLVZipDownloader pLVZipDownloader2 = pLVZipDownloader;
                if (pLVZipDownloader2 != null) {
                    pLVZipDownloader2.destroy();
                }
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onByte(int i2) {
                PLVDownloader.this.downloaderSpeed.add(i2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadError(@NonNull String str, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2) {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadJs onDownloadError");
                stopDownloader();
                PLVDownloader.this.stopSpeedRefresh();
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(i2, pLVDownloader.playId, PLVDownloader.this.videoPoolId, arrayList, arrayList2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadProgress(long j2, long j3) {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadJs onDownloadProgress:" + j2 + " total:" + j3);
                PLVDownloader.this.callProgressListenerDownload((long) PLVDownloader.this.progressGetter.updateJsProgressAndGetInTotal((int) ((j2 * 100) / j3)), 100L);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadSuccess() {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadJs onDownloadSuccess");
                stopDownloader();
                PLVDownloader.this.stopSpeedRefresh();
                PLVDownloader.this.setJsPath(jsStoragePath);
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerSuccess(pLVDownloader.playbackCacheVO);
            }
        });
        pLVZipDownloader.addUnzipListener(new IPLVDownloaderUnzipListener() { // from class: com.plv.livescenes.download.PLVDownloader.12
            @Override // com.plv.foundationsdk.download.zip.IPLVDownloaderUnzipListener
            public void onDone() {
            }

            @Override // com.plv.foundationsdk.download.zip.IPLVDownloaderUnzipListener
            public void onProgress(int i2) {
            }
        });
        pLVZipDownloader.start();
    }

    private void downloadMP4(String str) {
        PLVCommonLog.d(TAG, "downloadMP4: " + str);
        this.videoProgress = 0L;
        this.videoTotal = 100L;
        calculateTotal();
        final String strCreateFileName = createFileName(str);
        final String videoStoragePath = getVideoStoragePath();
        final PLVAloneDownloader pLVAloneDownloader = new PLVAloneDownloader(this.playId, this.videoPoolId, new PLVMultimedia(str, videoStoragePath, strCreateFileName));
        this.downloaders.add(pLVAloneDownloader);
        pLVAloneDownloader.addDownloadListener(new IPLVDownloaderSDKListener() { // from class: com.plv.livescenes.download.PLVDownloader.7
            private void stopDownloader() {
                PLVAloneDownloader pLVAloneDownloader2 = pLVAloneDownloader;
                if (pLVAloneDownloader2 != null) {
                    pLVAloneDownloader2.destroy();
                }
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onByte(int i2) {
                PLVDownloader.this.downloaderSpeed.add(i2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadError(@NonNull String str2, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2) {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadMP4:onDownloadError: " + str2);
                stopDownloader();
                PLVDownloader.this.stopSpeedRefresh();
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(i2, pLVDownloader.playId, PLVDownloader.this.videoPoolId, null, null);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadProgress(long j2, long j3) {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadMP4:onDownloadProgress: downloaded:" + j2 + " total :" + j3);
                PLVDownloader.this.videoProgress = j2;
                PLVDownloader.this.callProgressListenerDownload((long) PLVDownloader.this.progressGetter.updateVideoProgressAndGetInTotal((int) ((j2 * 100) / j3)), 100L);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadSuccess() {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadMP4:onDownloadSuccess ");
                PLVDownloader.this.playbackCacheVO.setVideoPath(videoStoragePath + File.separator + strCreateFileName);
                stopDownloader();
                PLVDownloader.this.zipDownload();
            }
        });
        this.downloaderSpeed.start();
        pLVAloneDownloader.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadTs(List<PLVMultimedia> list, final String str, final String str2) {
        PLVCommonLog.d(TAG, "downloadTs:" + str);
        final PLVTSDownloader pLVTSDownloader = new PLVTSDownloader(this.playId, this.videoPoolId, list);
        this.downloaders.add(pLVTSDownloader);
        pLVTSDownloader.addDownloadListener(new IPLVDownloaderSDKListener() { // from class: com.plv.livescenes.download.PLVDownloader.8
            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onByte(int i2) {
                PLVDownloader.this.downloaderSpeed.add(i2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadError(@NonNull String str3, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2) {
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(i2, pLVDownloader.playId, PLVDownloader.this.videoPoolId, null, null);
                PLVTSDownloader pLVTSDownloader2 = pLVTSDownloader;
                if (pLVTSDownloader2 != null) {
                    pLVTSDownloader2.destroy();
                }
                PLVDownloader.this.stopSpeedRefresh();
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadProgress(long j2, long j3) {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadTs: onDownloadProgress" + j2 + " total :" + j3);
                PLVDownloader.this.videoProgress = j2;
                PLVDownloader.this.callProgressListenerDownload((long) PLVDownloader.this.progressGetter.updateVideoProgressAndGetInTotal((int) ((j2 * 100) / j3)), 100L);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadSuccess() {
                PLVCommonLog.d(PLVDownloader.TAG, "downloadTs: onDownloadSuccess" + str);
                PLVTSDownloader pLVTSDownloader2 = pLVTSDownloader;
                if (pLVTSDownloader2 != null) {
                    pLVTSDownloader2.stop();
                }
                if (PLVDownloader.this.createM3u8File(str, str2)) {
                    PLVDownloader.this.zipDownload();
                }
            }
        });
        this.downloaderSpeed.start();
        pLVTSDownloader.start();
    }

    private static String fixUrlScheme(String str) {
        if (str == null) {
            return null;
        }
        if (!str.startsWith("//")) {
            return str.startsWith(DefaultWebClient.HTTP_SCHEME) ? str.replaceFirst(DefaultWebClient.HTTP_SCHEME, DefaultWebClient.HTTPS_SCHEME) : str;
        }
        return URIUtil.HTTPS_COLON + str;
    }

    private String getJsStoragePath() {
        return PLVDownloadDirManager.getJSPath(this.videoPoolId, this.downloadDir.getAbsolutePath());
    }

    private String getPptStoragePath() {
        return PLVDownloadDirManager.getPPTPath(this.videoPoolId, this.downloadDir.getAbsolutePath());
    }

    private String getRootStoragePath() {
        return PLVDownloadDirManager.getRootDir(this.videoPoolId, this.downloadDir.getAbsolutePath());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVMultimedia> getTSFileList(String str, String str2, int i2, String str3) {
        int i3;
        Pattern patternCompile;
        String strTsReplaceInM3U8 = PLVDnsUtil.tsReplaceInM3U8(str);
        if (str.equals(strTsReplaceInM3U8)) {
            patternCompile = Pattern.compile("(https?://[^/]*)(.*\\.ts)");
            i3 = 2;
        } else {
            i3 = 4;
            patternCompile = Pattern.compile("(https?://(([0-9]{1,3}\\.){3}?[0-9]{1,3})?/[^/]*)(.*\\.ts)");
            str = strTsReplaceInM3U8;
        }
        Matcher matcher = Pattern.compile("https?://.*ts").matcher(str);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        String strGroup = "";
        while (matcher.find()) {
            String strGroup2 = matcher.group();
            Matcher matcher2 = patternCompile.matcher(strGroup2);
            if (matcher2.find()) {
                strGroup = matcher2.group(i3);
            }
            String strReplace = strGroup.substring(strGroup.lastIndexOf("/") + 1, strGroup.length()).replace(StrPool.DOT, StrPool.UNDERLINE);
            sb.delete(0, sb.length());
            sb.append(str3);
            String str4 = File.separator;
            sb.append(str4);
            sb.append(str2);
            sb.append(str4);
            sb.append(i2);
            arrayList.add(new PLVMultimedia(strGroup2, sb.toString(), strReplace));
        }
        return arrayList;
    }

    public static long getVideoDownloadSize(@Nullable PLVPlaybackVideoVO pLVPlaybackVideoVO) {
        long jLongValue = 0;
        if (pLVPlaybackVideoVO == null || pLVPlaybackVideoVO.getData() == null || pLVPlaybackVideoVO.getData().getVideoCache() == null || !pLVPlaybackVideoVO.getData().isPlaybackCacheEnable()) {
            return 0L;
        }
        PLVPlaybackVideoVO.Data.VideoCache videoCache = pLVPlaybackVideoVO.getData().getVideoCache();
        if (videoCache.getZipUrl() != null && videoCache.getZipSize() != null) {
            jLongValue = 0 + videoCache.getZipSize().longValue();
        }
        return (videoCache.getVideoUrl() == null || videoCache.getVideoSize() == null) ? jLongValue : jLongValue + videoCache.getVideoSize().longValue();
    }

    private String getVideoStoragePath() {
        return PLVDownloadDirManager.getVideoPath(this.videoPoolId, this.downloadDir.getAbsolutePath()) + File.separator + this.mBitrate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSpeedRefresh() {
        PLVDownloaderSpeed pLVDownloaderSpeed = this.downloaderSpeed;
        if (pLVDownloaderSpeed != null) {
            pLVDownloaderSpeed.stop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void videoDownload() {
        String strFixUrlScheme = fixUrlScheme(this.downloadDataSourceParam.videoUrl);
        PLVCommonLog.d(TAG, "开始下载视频：" + strFixUrlScheme);
        final String videoStoragePath = getVideoStoragePath();
        this.playbackCacheVO.setVideoPath(videoStoragePath);
        if (strFixUrlScheme.endsWith(".m3u8")) {
            this.compositeDisposable.add(PLVFoundationApiManager.getPlvUrlApi().requestUrl(strFixUrlScheme).subscribeOn(Schedulers.io()).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.livescenes.download.PLVDownloader.5
                @Override // io.reactivex.functions.Consumer
                public void accept(ResponseBody responseBody) throws Exception {
                    String strString = responseBody.string();
                    PLVCommonLog.d(PLVDownloader.TAG, "download m3u8 data :" + strString);
                    if (TextUtils.isEmpty(strString)) {
                        PLVDownloader pLVDownloader = PLVDownloader.this;
                        pLVDownloader.callProgressListenerFail(1010606, pLVDownloader.playId, PLVDownloader.this.videoPoolId, null, null);
                        return;
                    }
                    PLVDownloader pLVDownloader2 = PLVDownloader.this;
                    List tSFileList = pLVDownloader2.getTSFileList(strString, pLVDownloader2.videoPoolId, 1, PLVDownloader.this.downloadDir.getAbsolutePath());
                    PLVDownloader.this.videoProgress = 0L;
                    PLVDownloader.this.videoTotal = 100L;
                    PLVDownloader.access$1108(PLVDownloader.this);
                    PLVDownloader.this.calculateTotal();
                    PLVDownloader.this.downloadTs(tSFileList, videoStoragePath, strString);
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.download.PLVDownloader.6
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    PLVCommonLog.exception(th);
                    PLVDownloader pLVDownloader = PLVDownloader.this;
                    pLVDownloader.callProgressListenerFail(1010606, pLVDownloader.playId, PLVDownloader.this.videoPoolId, PLVSugarUtil.listOf(Log.getStackTraceString(th)));
                }
            }));
        } else if (strFixUrlScheme.endsWith(".mp4")) {
            downloadMP4(strFixUrlScheme);
        }
    }

    public void calculateTotal() {
        long jLongValue = ((Long) PLVSugarUtil.getOrDefault(this.downloadDataSourceParam.zipSize, 0L)).longValue();
        long jLongValue2 = ((Long) PLVSugarUtil.getOrDefault(this.downloadDataSourceParam.videoSize, 0L)).longValue();
        long j2 = this.videoTotal;
        int i2 = (int) (((jLongValue * j2) * 2) / jLongValue2);
        this.zipTotal = (int) (i2 - j2);
        this.downloadTotal = i2 + 1;
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void deleteDownloadContent() {
        if (isDownloading()) {
            stop();
        }
        Iterator<com.plv.foundationsdk.download.IPLVDownloader> it = this.downloaders.iterator();
        while (it.hasNext()) {
            it.next().deleteDownloadContent();
        }
        try {
            FileUtils.deleteDir(getRootStoragePath());
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    @Override // com.plv.livescenes.download.IPLVDownloader
    public String getKey() {
        return this.key;
    }

    @Override // com.plv.livescenes.download.IPLVDownloader
    public int getSpeedCallbackInterval() {
        return 0;
    }

    @Override // com.plv.livescenes.download.IPLVDownloader
    public void isCallbackProgressWhereExists(boolean z2) {
    }

    @Override // com.plv.livescenes.download.IPLVDownloader
    public boolean isCallbackProgressWhereExists() {
        return false;
    }

    @Override // com.plv.livescenes.download.IPLVDownloader, com.plv.foundationsdk.download.IPLVDownloader
    public boolean isDownloading() {
        List<com.plv.foundationsdk.download.IPLVDownloader> list = this.downloaders;
        if (list == null) {
            return false;
        }
        Iterator<com.plv.foundationsdk.download.IPLVDownloader> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isDownloading()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.plv.livescenes.download.IPLVDownloaderListenerEvent
    public void setDownloadSpeedListener(IPLVDownloaderSpeedListener iPLVDownloaderSpeedListener) {
        this.downloaderSpeed.setSpeedListener(iPLVDownloaderSpeedListener);
    }

    public void setJsPath(String str) {
        for (File file : new File(str).listFiles()) {
            String absolutePath = file.getAbsolutePath();
            if (absolutePath.endsWith(".html")) {
                PLVCommonLog.d(TAG, "find html :" + absolutePath);
                this.playbackCacheVO.setJsPath(absolutePath);
                return;
            }
        }
    }

    @Override // com.plv.livescenes.download.IPLVDownloader
    public void setSpeedCallbackInterval(int i2) {
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void start() throws Throwable {
        if (TextUtils.isEmpty(this.videoPoolId)) {
            PLVCommonLog.e(TAG, "vid is null");
            callProgressListenerFail(PLVDownloaderErrorReason.VID_IS_NULL, this.playId, this.videoPoolId);
            return;
        }
        if (checkPermission() || checkSDCardStatus() || checkDirStatus()) {
            return;
        }
        File file = new File(this.downloadDir, ".nomedia");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    String str = "创建nomedia文件失败" + file.getAbsolutePath();
                    PLVCommonLog.e(TAG, str);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(str);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(file.getAbsolutePath());
                    callProgressListenerFail(PLVDownloaderErrorReason.CREATE_NOMEDIA_ERROR, this.playId, this.videoPoolId, arrayList, arrayList2);
                    return;
                }
            } catch (IOException e2) {
                String exceptionFullMessage = PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1);
                PLVCommonLog.e(TAG, exceptionFullMessage);
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(exceptionFullMessage);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(file.getAbsolutePath());
                callProgressListenerFail(PLVDownloaderErrorReason.CREATE_NOMEDIA_ERROR, this.playId, this.videoPoolId, arrayList3, arrayList4);
                return;
            }
        }
        createDownloadTask();
    }

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    public void stop() {
        List<com.plv.foundationsdk.download.IPLVDownloader> list = this.downloaders;
        if (list != null) {
            Iterator<com.plv.foundationsdk.download.IPLVDownloader> it = list.iterator();
            while (it.hasNext()) {
                it.next().stop();
            }
        }
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        PLVDownloaderSpeed pLVDownloaderSpeed = this.downloaderSpeed;
        if (pLVDownloaderSpeed != null) {
            pLVDownloaderSpeed.stop();
        }
        callStopListener();
    }

    public void zipDownload() {
        String strFixUrlScheme = fixUrlScheme(this.downloadDataSourceParam.zipUrl);
        if (TextUtils.isEmpty(strFixUrlScheme) || "null".equals(strFixUrlScheme)) {
            callProgressListenerSuccess(this.playbackCacheVO);
            return;
        }
        PLVCommonLog.d(TAG, "zipDownload:" + strFixUrlScheme);
        String strCreateFileName = createFileName(strFixUrlScheme);
        final String pptStoragePath = getPptStoragePath();
        final PLVZipDownloader pLVZipDownloader = new PLVZipDownloader(new PLVZipMultimedia(this.zipTotal, strFixUrlScheme, pptStoragePath, strCreateFileName));
        this.downloaders.add(pLVZipDownloader);
        pLVZipDownloader.addDownloadListener(new IPLVDownloaderSDKListener() { // from class: com.plv.livescenes.download.PLVDownloader.9
            private void stopDownloader() {
                PLVZipDownloader pLVZipDownloader2 = pLVZipDownloader;
                if (pLVZipDownloader2 != null) {
                    pLVZipDownloader2.destroy();
                }
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onByte(int i2) {
                PLVDownloader.this.downloaderSpeed.add(i2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadError(@NonNull String str, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2) {
                PLVCommonLog.d(PLVDownloader.TAG, "zipDownload onDownloadError：" + i2);
                stopDownloader();
                PLVDownloader.this.stopSpeedRefresh();
                PLVDownloader pLVDownloader = PLVDownloader.this;
                pLVDownloader.callProgressListenerFail(i2, pLVDownloader.playId, PLVDownloader.this.videoPoolId, arrayList, arrayList2);
                PLVCommonLog.e(PLVDownloader.TAG, "errorReason=" + i2 + "\n" + arrayList + "\n" + arrayList2);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadProgress(long j2, long j3) {
                PLVCommonLog.d(PLVDownloader.TAG, "zipDownload onDownloadProgress：" + j2 + " total:" + j3);
                PLVDownloader.this.callProgressListenerDownload((long) PLVDownloader.this.progressGetter.updateZipProgressAndGetInTotal((int) ((j2 * 100) / j3)), 100L);
            }

            @Override // com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener
            public void onDownloadSuccess() {
                PLVCommonLog.d(PLVDownloader.TAG, "zipDownload onDownloadSuccess");
                PLVDownloader.this.playbackCacheVO.setPptDir(pptStoragePath);
                stopDownloader();
                PLVDownloader.this.downloadJs();
            }
        });
        pLVZipDownloader.addUnzipListener(new IPLVDownloaderUnzipListener() { // from class: com.plv.livescenes.download.PLVDownloader.10
            @Override // com.plv.foundationsdk.download.zip.IPLVDownloaderUnzipListener
            public void onDone() {
            }

            @Override // com.plv.foundationsdk.download.zip.IPLVDownloaderUnzipListener
            public void onProgress(int i2) {
            }
        });
        this.downloaderSpeed.start();
        pLVZipDownloader.start();
    }

    public static long getVideoDownloadSize(@Nullable PLVTempStorePlaybackVideoVO pLVTempStorePlaybackVideoVO) {
        long jLongValue = 0;
        if (pLVTempStorePlaybackVideoVO == null || pLVTempStorePlaybackVideoVO.getData() == null || pLVTempStorePlaybackVideoVO.getData().getVideoCache() == null || !pLVTempStorePlaybackVideoVO.getData().isPlaybackCacheEnable()) {
            return 0L;
        }
        PLVTempStorePlaybackVideoVO.Data.VideoCache videoCache = pLVTempStorePlaybackVideoVO.getData().getVideoCache();
        if (videoCache.getZipUrl() != null && videoCache.getZipSize() != null) {
            jLongValue = 0 + videoCache.getZipSize().longValue();
        }
        return (videoCache.getVideoUrl() == null || videoCache.getVideoSize() == null) ? jLongValue : jLongValue + videoCache.getVideoSize().longValue();
    }
}
