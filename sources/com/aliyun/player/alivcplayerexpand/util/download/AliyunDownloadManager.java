package com.aliyun.player.alivcplayerexpand.util.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;
import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.downloader.DownloaderConfig;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.listener.RefreshStsCallback;
import com.aliyun.player.alivcplayerexpand.util.VidStsUtil;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.database.LoadDbDatasListener;
import com.aliyun.player.alivcplayerexpand.util.database.LoadDbTvListDatasListenerr;
import com.aliyun.player.alivcplayerexpand.util.database.LongVideoDatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.psychiatrygarden.utils.SdkConstant;
import com.yikaobang.yixue.BuildConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes2.dex */
public class AliyunDownloadManager {
    public static final int INTENT_STATE_ADD = 2;
    public static final int INTENT_STATE_START = 0;
    public static final int INTENT_STATE_STOP = 1;
    private static final int MAX_NUM = 5;
    public static final String MEMORY_LESS_MSG = "memory_less";
    private static final int MIN_NUM = 1;
    public static final String TAG = "AliyunDownloadManager";
    public static final int VID_AUTH = 1;
    public static final int VID_STS = 0;
    private static volatile AliyunDownloadManager mInstance;
    private String downloadDir;
    private String encryptFilePath;
    private Context mContext;
    private VidAuth mVidAuth;
    private VidSts mVidSts;
    private int mMaxNum = 3;
    private LinkedHashMap<AliyunDownloadMediaInfo, AliMediaDownloader> downloadInfos = new LinkedHashMap<>();
    private ConcurrentLinkedQueue<AliyunDownloadMediaInfo> preparedList = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<AliyunDownloadMediaInfo> downloadingList = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<AliyunDownloadMediaInfo> completedList = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<AliyunDownloadMediaInfo> waitedList = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<AliyunDownloadMediaInfo> stopedList = new ConcurrentLinkedQueue<>();
    private List<AliyunDownloadInfoListener> outListenerList = new ArrayList();
    private ArrayMap<String, List<TrackInfo>> trackInfosMap = new ArrayMap<>();
    private long freshStorageSizeTime = 0;
    private List<AliMediaDownloader> mJniDownloadLists = new ArrayList();
    private DownloaderConfig mDownloaderConfig = new DownloaderConfig();
    private AliyunDownloadInfoListener innerDownloadInfoListener = new AnonymousClass1();
    private DatabaseManager mDatabaseManager = DatabaseManager.getInstance();
    private LongVideoDatabaseManager mLongVideoDatabaseManager = LongVideoDatabaseManager.getInstance();

    /* renamed from: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager$1, reason: invalid class name */
    public class AnonymousClass1 implements AliyunDownloadInfoListener {
        public AnonymousClass1() {
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onAdd(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.2
                @Override // java.lang.Runnable
                public void run() {
                    AliyunDownloadManager.this.prepareMediaInfo(aliyunDownloadMediaInfo);
                    if (AliyunDownloadManager.this.mDatabaseManager.selectAll().contains(aliyunDownloadMediaInfo)) {
                        AliyunDownloadManager.this.mDatabaseManager.update(aliyunDownloadMediaInfo);
                    } else {
                        AliyunDownloadManager.this.mDatabaseManager.insert(aliyunDownloadMediaInfo);
                    }
                }
            });
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onAdd(aliyunDownloadMediaInfo);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onCompletion(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            AliyunDownloadManager.this.completedMediaInfo(aliyunDownloadMediaInfo);
            AliMediaDownloader aliMediaDownloader = (AliMediaDownloader) AliyunDownloadManager.this.downloadInfos.get(aliyunDownloadMediaInfo);
            if (aliMediaDownloader == null) {
                return;
            }
            aliyunDownloadMediaInfo.setSavePath(aliMediaDownloader.getFilePath());
            AliyunDownloadManager.this.mDatabaseManager.update(aliyunDownloadMediaInfo);
            Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
            while (it.hasNext()) {
                ((AliyunDownloadInfoListener) it.next()).onCompletion(aliyunDownloadMediaInfo);
            }
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onDelete(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            AliyunDownloadManager.this.deleteMediaInfo(aliyunDownloadMediaInfo);
            AliyunDownloadManager.this.mDatabaseManager.delete(aliyunDownloadMediaInfo);
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.9
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onDelete(aliyunDownloadMediaInfo);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onDeleteAll() {
            AliyunDownloadManager.this.deleteAllMediaInfo();
            AliyunDownloadManager.this.mDatabaseManager.deleteAll();
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.10
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onDeleteAll();
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onError(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo, final ErrorCode errorCode, final String str, final String str2) {
            AliyunDownloadManager.this.errorMediaInfo(aliyunDownloadMediaInfo, errorCode, str);
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.14
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onError(aliyunDownloadMediaInfo, errorCode, str, str2);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onFileProgress(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.11
                @Override // java.lang.Runnable
                public void run() {
                    for (AliyunDownloadInfoListener aliyunDownloadInfoListener : AliyunDownloadManager.this.outListenerList) {
                        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.File);
                        aliyunDownloadInfoListener.onFileProgress(aliyunDownloadMediaInfo);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onPrepared(final List<AliyunDownloadMediaInfo> list) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onPrepared(list);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onProgress(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo, final int i2) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.6
                @Override // java.lang.Runnable
                public void run() {
                    if (AliyunDownloadManager.this.freshStorageSizeTime == 0 || new Date().getTime() - AliyunDownloadManager.this.freshStorageSizeTime > ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                        AliyunDownloadManager.this.mDatabaseManager.update(aliyunDownloadMediaInfo);
                        if (DownloadUtils.isStorageAlarm(AliyunDownloadManager.this.mContext)) {
                            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.6.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AliyunDownloadManager aliyunDownloadManager = AliyunDownloadManager.this;
                                    aliyunDownloadManager.stopDownloads(aliyunDownloadManager.downloadingList);
                                    AliyunDownloadManager aliyunDownloadManager2 = AliyunDownloadManager.this;
                                    aliyunDownloadManager2.stopDownloads(aliyunDownloadManager2.waitedList);
                                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                                    while (it.hasNext()) {
                                        ((AliyunDownloadInfoListener) it.next()).onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, AliyunDownloadManager.MEMORY_LESS_MSG, null);
                                    }
                                }
                            });
                        }
                        AliyunDownloadManager.this.freshStorageSizeTime = new Date().getTime();
                    }
                }
            });
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.7
                @Override // java.lang.Runnable
                public void run() {
                    for (AliyunDownloadInfoListener aliyunDownloadInfoListener : AliyunDownloadManager.this.outListenerList) {
                        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Start);
                        aliyunDownloadInfoListener.onProgress(aliyunDownloadMediaInfo, i2);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onStart(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            AliyunDownloadManager.this.startMediaInfo(aliyunDownloadMediaInfo);
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.4
                @Override // java.lang.Runnable
                public void run() {
                    boolean zContains;
                    List<AliyunDownloadMediaInfo> listSelectAll = AliyunDownloadManager.this.mDatabaseManager.selectAll();
                    if (TextUtils.isEmpty(aliyunDownloadMediaInfo.getTvId())) {
                        Iterator<AliyunDownloadMediaInfo> it = listSelectAll.iterator();
                        zContains = false;
                        while (it.hasNext()) {
                            zContains = AliyunDownloadManager.this.judgeEquals(it.next(), aliyunDownloadMediaInfo);
                            if (zContains) {
                                break;
                            }
                        }
                    } else {
                        zContains = listSelectAll.contains(aliyunDownloadMediaInfo);
                    }
                    if (zContains) {
                        AliyunDownloadManager.this.mDatabaseManager.update(aliyunDownloadMediaInfo);
                    } else {
                        AliyunDownloadManager.this.mDatabaseManager.insert(aliyunDownloadMediaInfo);
                    }
                }
            });
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.5
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onStart(aliyunDownloadMediaInfo);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onStop(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            AliyunDownloadManager.this.stopMediaInfo(aliyunDownloadMediaInfo);
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.12
                @Override // java.lang.Runnable
                public void run() {
                    AliyunDownloadManager.this.mDatabaseManager.update(aliyunDownloadMediaInfo);
                }
            });
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.13
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onStop(aliyunDownloadMediaInfo);
                    }
                }
            });
        }

        @Override // com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
        public void onWait(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
            AliyunDownloadManager.this.waitMediaInfo(aliyunDownloadMediaInfo);
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.1.8
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = AliyunDownloadManager.this.outListenerList.iterator();
                    while (it.hasNext()) {
                        ((AliyunDownloadInfoListener) it.next()).onWait(aliyunDownloadMediaInfo);
                    }
                }
            });
        }
    }

    private AliyunDownloadManager(Context context) {
        this.mContext = context;
        if (TextUtils.isEmpty(this.downloadDir)) {
            return;
        }
        File file = new File(this.downloadDir);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    private void autoDownload() {
        if (this.downloadingList.size() >= this.mMaxNum || this.waitedList.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoPeek = this.waitedList.peek();
        if (aliyunDownloadMediaInfoPeek.getStatus() == AliyunDownloadMediaInfo.Status.Wait) {
            startDownload(aliyunDownloadMediaInfoPeek);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completedMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.completedList.contains(aliyunDownloadMediaInfo) && aliyunDownloadMediaInfo != null) {
            this.completedList.add(aliyunDownloadMediaInfo);
        }
        this.downloadingList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Complete);
        autoDownload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteAllMediaInfo() {
        this.preparedList.clear();
        this.waitedList.clear();
        this.downloadingList.clear();
        this.stopedList.clear();
        this.completedList.clear();
        this.downloadInfos.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Iterator<AliyunDownloadMediaInfo> it = this.preparedList.iterator();
        while (it.hasNext()) {
            if (it.next().equals(aliyunDownloadMediaInfo)) {
                it.remove();
            }
        }
        Iterator<AliyunDownloadMediaInfo> it2 = this.waitedList.iterator();
        while (it2.hasNext()) {
            if (it2.next().equals(aliyunDownloadMediaInfo)) {
                it2.remove();
            }
        }
        Iterator<AliyunDownloadMediaInfo> it3 = this.downloadingList.iterator();
        while (it3.hasNext()) {
            if (it3.next().equals(aliyunDownloadMediaInfo)) {
                it3.remove();
            }
        }
        Iterator<AliyunDownloadMediaInfo> it4 = this.stopedList.iterator();
        while (it4.hasNext()) {
            if (it4.next().equals(aliyunDownloadMediaInfo)) {
                it4.remove();
            }
        }
        Iterator<AliyunDownloadMediaInfo> it5 = this.completedList.iterator();
        while (it5.hasNext()) {
            if (it5.next().equals(aliyunDownloadMediaInfo)) {
                it5.remove();
            }
        }
        this.downloadInfos.remove(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void errorMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ErrorCode errorCode, String str) {
        if (aliyunDownloadMediaInfo == null) {
            return;
        }
        if (!this.stopedList.contains(aliyunDownloadMediaInfo)) {
            this.stopedList.add(aliyunDownloadMediaInfo);
        }
        this.preparedList.remove(aliyunDownloadMediaInfo);
        this.downloadingList.remove(aliyunDownloadMediaInfo);
        this.completedList.remove(aliyunDownloadMediaInfo);
        this.waitedList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Error);
        aliyunDownloadMediaInfo.setErrorCode(errorCode);
        aliyunDownloadMediaInfo.setErrorMsg(str);
        autoDownload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeDelete(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AliMediaDownloader aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo);
        if (aliyunDownloadMediaInfo == null) {
            AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
            if (aliyunDownloadInfoListener != null) {
                aliyunDownloadInfoListener.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, this.mContext.getResources().getString(R.string.alivc_player_delete_failed), null);
                return;
            }
            return;
        }
        String downloadDir = getDownloadDir();
        String vid = aliyunDownloadMediaInfo.getVid();
        String format = aliyunDownloadMediaInfo.getFormat();
        int qualityIndex = aliyunDownloadMediaInfo.getQualityIndex();
        if (aliMediaDownloader != null) {
            aliMediaDownloader.stop();
            releaseJniDownloader(aliyunDownloadMediaInfo);
        }
        int iDeleteFile = AliDownloaderFactory.deleteFile(downloadDir, vid, format, qualityIndex);
        if (iDeleteFile == 12 || iDeleteFile == 11) {
            Log.e(TAG, "deleteFile warning  ret = " + iDeleteFile);
            AliyunDownloadInfoListener aliyunDownloadInfoListener2 = this.innerDownloadInfoListener;
            if (aliyunDownloadInfoListener2 != null) {
                aliyunDownloadInfoListener2.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, this.mContext.getResources().getString(R.string.alivc_player_delete_failed), null);
            }
        }
        AliyunDownloadInfoListener aliyunDownloadInfoListener3 = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener3 != null) {
            aliyunDownloadInfoListener3.onDelete(aliyunDownloadMediaInfo);
        }
        autoDownload();
    }

    public static AliyunDownloadManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AliyunDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new AliyunDownloadManager(context);
                }
            }
        }
        return mInstance;
    }

    private void getVidSts(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo, final int i2) {
        VidStsUtil.getVidSts(GlobalPlayerConfig.mVid, new VidStsUtil.OnStsResultListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.9
            @Override // com.aliyun.player.alivcplayerexpand.util.VidStsUtil.OnStsResultListener
            public void onFail() {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Context applicationContext = AliyunDownloadManager.this.mContext.getApplicationContext();
                        Resources resources = AliyunDownloadManager.this.mContext.getResources();
                        int i3 = R.string.alivc_player_get_sts_failed;
                        Toast.makeText(applicationContext, resources.getString(i3), 0).show();
                        if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                            AliyunDownloadInfoListener aliyunDownloadInfoListener = AliyunDownloadManager.this.innerDownloadInfoListener;
                            AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                            aliyunDownloadInfoListener.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, AliyunDownloadManager.this.mContext.getResources().getString(i3), null);
                        }
                    }
                });
            }

            @Override // com.aliyun.player.alivcplayerexpand.util.VidStsUtil.OnStsResultListener
            public void onSuccess(String str, String str2, String str3, String str4) {
                VidSts vidSts = new VidSts();
                vidSts.setVid(aliyunDownloadMediaInfo.getVid());
                vidSts.setRegion("cn-shanghai");
                vidSts.setAccessKeyId(str2);
                vidSts.setSecurityToken(str4);
                vidSts.setAccessKeySecret(str3);
                vidSts.setQuality(aliyunDownloadMediaInfo.getQuality(), false);
                aliyunDownloadMediaInfo.setVidSts(vidSts);
                AliyunDownloadManager.this.prepareDownloadByQuality(aliyunDownloadMediaInfo, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean judgeEquals(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, AliyunDownloadMediaInfo aliyunDownloadMediaInfo2) {
        return (aliyunDownloadMediaInfo == null || aliyunDownloadMediaInfo2 == null || TextUtils.isEmpty(aliyunDownloadMediaInfo.getVid()) || !aliyunDownloadMediaInfo.getVid().equals(aliyunDownloadMediaInfo2.getVid()) || TextUtils.isEmpty(aliyunDownloadMediaInfo.getQuality()) || !aliyunDownloadMediaInfo.getQuality().equals(aliyunDownloadMediaInfo2.getQuality()) || TextUtils.isEmpty(aliyunDownloadMediaInfo.getFormat()) || !aliyunDownloadMediaInfo.getFormat().equals(aliyunDownloadMediaInfo2.getFormat())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPreparedCallback(VidSts vidSts, MediaInfo mediaInfo, List<AliyunDownloadMediaInfo> list, LongVideoBean longVideoBean) {
        for (TrackInfo trackInfo : mediaInfo.getTrackInfos()) {
            if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
                VidSts vidSts2 = new VidSts();
                vidSts2.setVid(mediaInfo.getVideoId());
                vidSts2.setAccessKeyId(vidSts.getAccessKeyId());
                vidSts2.setSecurityToken(vidSts.getSecurityToken());
                vidSts2.setAccessKeySecret(vidSts.getAccessKeySecret());
                AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
                aliyunDownloadMediaInfo.setVid(mediaInfo.getVideoId());
                aliyunDownloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                aliyunDownloadMediaInfo.setTitle(mediaInfo.getTitle());
                aliyunDownloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                aliyunDownloadMediaInfo.setDuration(mediaInfo.getDuration());
                aliyunDownloadMediaInfo.setTrackInfo(trackInfo);
                aliyunDownloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                aliyunDownloadMediaInfo.setFormat(trackInfo.getVodFormat());
                aliyunDownloadMediaInfo.setSize(trackInfo.getVodFileSize());
                aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
                aliyunDownloadMediaInfo.setVidType(0);
                aliyunDownloadMediaInfo.setVidSts(vidSts2);
                if (longVideoBean != null) {
                    aliyunDownloadMediaInfo.setTvId(longVideoBean.getTvId());
                    aliyunDownloadMediaInfo.setTvName(longVideoBean.getTvName());
                    aliyunDownloadMediaInfo.setTvCoverUrl(longVideoBean.getTvCoverUrl());
                }
                list.add(aliyunDownloadMediaInfo);
                AliMediaDownloader aliMediaDownloaderCreate = this.downloadInfos.get(aliyunDownloadMediaInfo);
                if (aliMediaDownloaderCreate == null) {
                    aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
                }
                aliMediaDownloaderCreate.setSaveDir(this.downloadDir);
                this.downloadInfos.put(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareDownload(VidSts vidSts, List<AliyunDownloadMediaInfo> list) {
        if (vidSts == null || list == null) {
            return;
        }
        for (final AliyunDownloadMediaInfo aliyunDownloadMediaInfo : list) {
            vidSts.setVid(aliyunDownloadMediaInfo.getVid());
            aliyunDownloadMediaInfo.setVidSts(vidSts);
            if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Start || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Prepare) {
                aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
            }
            final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
            aliMediaDownloaderCreate.setSaveDir(this.downloadDir);
            aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.2
                @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
                public void onPrepared(MediaInfo mediaInfo) {
                    if (AliyunDownloadManager.this.downloadInfos == null || !mediaInfo.getVideoId().equals(aliyunDownloadMediaInfo.getVid())) {
                        return;
                    }
                    for (TrackInfo trackInfo : mediaInfo.getTrackInfos()) {
                        if (trackInfo != null && trackInfo.getVodDefinition().equals(aliyunDownloadMediaInfo.getQuality())) {
                            aliyunDownloadMediaInfo.setTrackInfo(trackInfo);
                            AliyunDownloadManager.this.downloadInfos.put(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
                        }
                    }
                }
            });
            setErrorListener(aliMediaDownloaderCreate, aliyunDownloadMediaInfo);
            aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
            aliMediaDownloaderCreate.prepare(vidSts);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.preparedList.contains(aliyunDownloadMediaInfo) && aliyunDownloadMediaInfo != null) {
            this.preparedList.add(aliyunDownloadMediaInfo);
        }
        this.downloadingList.remove(aliyunDownloadMediaInfo);
        this.completedList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseJniDownloader(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        LinkedHashMap<AliyunDownloadMediaInfo, AliMediaDownloader> linkedHashMap = this.downloadInfos;
        if (linkedHashMap == null || !linkedHashMap.containsKey(aliyunDownloadMediaInfo)) {
            return;
        }
        AliMediaDownloader aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo);
        if (aliMediaDownloader != null) {
            aliMediaDownloader.release();
        }
        this.downloadInfos.remove(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AliyunDownloadMediaInfo> removeDuplicate(List<AliyunDownloadMediaInfo> list, List<AliyunDownloadMediaInfo> list2) {
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : list2) {
            Iterator<AliyunDownloadMediaInfo> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                    aliyunDownloadMediaInfo.setWatchNumber(1);
                }
            }
        }
        for (int i2 = 0; i2 < list2.size(); i2++) {
            long size = list2.get(i2).getSize();
            int watchNumber = list2.get(i2).getWatchNumber();
            int number = list2.get(i2).getNumber();
            for (int size2 = list2.size() - 1; size2 > i2; size2--) {
                if (!TextUtils.isEmpty(list2.get(size2).getTvId()) && !TextUtils.isEmpty(list2.get(i2).getTvId()) && list2.get(size2).getTvId().equals(list2.get(i2).getTvId())) {
                    size += list2.get(size2).getSize();
                    watchNumber += list2.get(size2).getWatchNumber();
                    number += list2.get(size2).getNumber();
                    list2.remove(size2);
                }
            }
            list2.get(i2).setSize(size);
            list2.get(i2).setWatchNumber(watchNumber);
            list2.get(i2).setNumber(number);
        }
        return list2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorListener(final AliMediaDownloader aliMediaDownloader, final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (aliMediaDownloader == null) {
            return;
        }
        aliMediaDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.16
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onError(aliyunDownloadMediaInfo, errorInfo.getCode(), errorInfo.getMsg(), errorInfo.getExtra());
                }
                AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 = aliyunDownloadMediaInfo;
                if (aliyunDownloadMediaInfo2 == null) {
                    aliMediaDownloader.release();
                } else {
                    AliyunDownloadManager.this.releaseJniDownloader(aliyunDownloadMediaInfo2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListener(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo, AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.setOnProgressListener(new AliMediaDownloader.OnProgressListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.10
            @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
            public void onDownloadingProgress(int i2) {
                Log.e(AliyunDownloadManager.TAG, "onDownloadingProgress内部下载 : " + i2);
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    aliyunDownloadMediaInfo.setProgress(i2);
                    AliyunDownloadManager.this.innerDownloadInfoListener.onProgress(aliyunDownloadMediaInfo, i2);
                }
            }

            @Override // com.aliyun.downloader.AliMediaDownloader.OnProgressListener
            public void onProcessingProgress(int i2) {
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    aliyunDownloadMediaInfo.setmFileHandleProgress(i2);
                    AliyunDownloadManager.this.innerDownloadInfoListener.onFileProgress(aliyunDownloadMediaInfo);
                }
            }
        });
        aliMediaDownloader.setOnCompletionListener(new AliMediaDownloader.OnCompletionListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.11
            @Override // com.aliyun.downloader.AliMediaDownloader.OnCompletionListener
            public void onCompletion() {
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onCompletion(aliyunDownloadMediaInfo);
                }
                AliyunDownloadManager.this.releaseJniDownloader(aliyunDownloadMediaInfo);
            }
        });
        setErrorListener(aliMediaDownloader, aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.downloadingList.contains(aliyunDownloadMediaInfo) && aliyunDownloadMediaInfo != null) {
            this.downloadingList.add(aliyunDownloadMediaInfo);
        }
        this.preparedList.remove(aliyunDownloadMediaInfo);
        this.stopedList.remove(aliyunDownloadMediaInfo);
        this.completedList.remove(aliyunDownloadMediaInfo);
        this.waitedList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.stopedList.contains(aliyunDownloadMediaInfo) && aliyunDownloadMediaInfo != null) {
            this.stopedList.add(aliyunDownloadMediaInfo);
        }
        this.downloadingList.remove(aliyunDownloadMediaInfo);
        this.preparedList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.waitedList.contains(aliyunDownloadMediaInfo) && aliyunDownloadMediaInfo != null) {
            this.waitedList.add(aliyunDownloadMediaInfo);
        }
        this.preparedList.remove(aliyunDownloadMediaInfo);
        this.downloadingList.remove(aliyunDownloadMediaInfo);
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Wait);
    }

    public void addDownload(VidSts vidSts, AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (vidSts == null || aliyunDownloadMediaInfo == null || this.preparedList.contains(aliyunDownloadMediaInfo) || this.stopedList.contains(aliyunDownloadMediaInfo) || this.waitedList.contains(aliyunDownloadMediaInfo) || this.downloadingList.contains(aliyunDownloadMediaInfo) || this.completedList.contains(aliyunDownloadMediaInfo)) {
            return;
        }
        vidSts.setVid(aliyunDownloadMediaInfo.getVid());
        aliyunDownloadMediaInfo.setVidSts(vidSts);
        AliMediaDownloader aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo);
        if (aliMediaDownloader == null || aliyunDownloadMediaInfo.getTrackInfo() == null) {
            prepareDownloadByQuality(aliyunDownloadMediaInfo, 2);
            return;
        }
        aliMediaDownloader.setSaveDir(this.downloadDir);
        aliMediaDownloader.selectItem(aliyunDownloadMediaInfo.getTrackInfo().getIndex());
        AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener != null) {
            aliyunDownloadInfoListener.onAdd(aliyunDownloadMediaInfo);
        }
        setErrorListener(aliMediaDownloader, aliyunDownloadMediaInfo);
    }

    public void addDownloadInfoListener(AliyunDownloadInfoListener aliyunDownloadInfoListener) {
        if (this.outListenerList == null) {
            this.outListenerList = new ArrayList();
        }
        if (aliyunDownloadInfoListener == null || this.outListenerList.contains(aliyunDownloadInfoListener)) {
            return;
        }
        this.outListenerList.add(aliyunDownloadInfoListener);
    }

    public void clearList() {
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue = this.preparedList;
        if (concurrentLinkedQueue != null) {
            concurrentLinkedQueue.clear();
        }
        if (!this.downloadInfos.isEmpty()) {
            Iterator<Map.Entry<AliyunDownloadMediaInfo, AliMediaDownloader>> it = this.downloadInfos.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().stop();
            }
            this.downloadInfos.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue2 = this.downloadingList;
        if (concurrentLinkedQueue2 != null) {
            concurrentLinkedQueue2.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue3 = this.completedList;
        if (concurrentLinkedQueue3 != null) {
            concurrentLinkedQueue3.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue4 = this.waitedList;
        if (concurrentLinkedQueue4 != null) {
            concurrentLinkedQueue4.clear();
        }
        List<AliyunDownloadInfoListener> list = this.outListenerList;
        if (list != null) {
            list.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue5 = this.stopedList;
        if (concurrentLinkedQueue5 != null) {
            concurrentLinkedQueue5.clear();
        }
    }

    public void deleteAllFile() {
        Iterator<AliyunDownloadMediaInfo> it = this.preparedList.iterator();
        while (it.hasNext()) {
            deleteFile(it.next());
        }
        Iterator<AliyunDownloadMediaInfo> it2 = this.downloadingList.iterator();
        while (it2.hasNext()) {
            deleteFile(it2.next());
        }
        Iterator<AliyunDownloadMediaInfo> it3 = this.completedList.iterator();
        while (it3.hasNext()) {
            deleteFile(it3.next());
        }
        Iterator<AliyunDownloadMediaInfo> it4 = this.waitedList.iterator();
        while (it4.hasNext()) {
            deleteFile(it4.next());
        }
        Iterator<AliyunDownloadMediaInfo> it5 = this.stopedList.iterator();
        while (it5.hasNext()) {
            deleteFile(it5.next());
        }
    }

    public void deleteFile(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (aliyunDownloadMediaInfo == null || this.downloadInfos == null) {
            return;
        }
        AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo.getStatus();
        AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Delete;
        if (status == status2) {
            return;
        }
        aliyunDownloadMediaInfo.setStatus(status2);
        executeDelete(aliyunDownloadMediaInfo);
    }

    public void findDatasByDb(final VidSts vidSts, final LoadDbDatasListener loadDbDatasListener) {
        if (this.mDatabaseManager != null) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.12
                @Override // java.lang.Runnable
                public void run() {
                    List<AliyunDownloadMediaInfo> listSelectPreparedList = AliyunDownloadManager.this.mDatabaseManager.selectPreparedList();
                    List<AliyunDownloadMediaInfo> listSelectStopedList = AliyunDownloadManager.this.mDatabaseManager.selectStopedList();
                    List<AliyunDownloadMediaInfo> listSelectCompletedList = AliyunDownloadManager.this.mDatabaseManager.selectCompletedList();
                    List<AliyunDownloadMediaInfo> listSelectDownloadingList = AliyunDownloadManager.this.mDatabaseManager.selectDownloadingList();
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(listSelectCompletedList);
                    arrayList.addAll(listSelectStopedList);
                    arrayList.addAll(listSelectPreparedList);
                    Iterator<AliyunDownloadMediaInfo> it = listSelectPreparedList.iterator();
                    while (it.hasNext()) {
                        it.next().setStatus(AliyunDownloadMediaInfo.Status.Stop);
                    }
                    Iterator<AliyunDownloadMediaInfo> it2 = listSelectDownloadingList.iterator();
                    while (it2.hasNext()) {
                        it2.next().setStatus(AliyunDownloadMediaInfo.Status.Stop);
                    }
                    arrayList.addAll(listSelectDownloadingList);
                    if (AliyunDownloadManager.this.stopedList != null) {
                        AliyunDownloadManager.this.stopedList.addAll(listSelectDownloadingList);
                        AliyunDownloadManager.this.stopedList.addAll(listSelectStopedList);
                        AliyunDownloadManager.this.stopedList.addAll(listSelectPreparedList);
                    }
                    if (AliyunDownloadManager.this.completedList != null) {
                        AliyunDownloadManager.this.completedList.addAll(listSelectCompletedList);
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass12 anonymousClass12 = AnonymousClass12.this;
                            AliyunDownloadManager.this.prepareDownload(vidSts, arrayList);
                            LoadDbDatasListener loadDbDatasListener2 = loadDbDatasListener;
                            if (loadDbDatasListener2 != null) {
                                loadDbDatasListener2.onLoadSuccess(arrayList);
                            }
                        }
                    });
                }
            });
        }
    }

    public void findDatasByDbTv(final LoadDbDatasListener loadDbDatasListener) {
        if (this.mDatabaseManager != null) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.14
                @Override // java.lang.Runnable
                public void run() {
                    List<AliyunDownloadMediaInfo> listSelectPreparedList = AliyunDownloadManager.this.mDatabaseManager.selectPreparedList();
                    List<AliyunDownloadMediaInfo> listSelectStopedList = AliyunDownloadManager.this.mDatabaseManager.selectStopedList();
                    List<AliyunDownloadMediaInfo> listSelectCompletedList = AliyunDownloadManager.this.mDatabaseManager.selectCompletedList();
                    List<AliyunDownloadMediaInfo> listSelectDownloadingList = AliyunDownloadManager.this.mDatabaseManager.selectDownloadingList();
                    List<AliyunDownloadMediaInfo> listSelectWaitList = AliyunDownloadManager.this.mDatabaseManager.selectWaitList();
                    List<AliyunDownloadMediaInfo> listSelectWatchedList = AliyunDownloadManager.this.mDatabaseManager.selectWatchedList();
                    ArrayList arrayList = new ArrayList();
                    if (listSelectCompletedList != null) {
                        arrayList.addAll(listSelectCompletedList);
                    }
                    if (listSelectStopedList != null) {
                        arrayList.addAll(listSelectStopedList);
                    }
                    if (listSelectPreparedList != null) {
                        arrayList.addAll(listSelectPreparedList);
                    }
                    if (listSelectDownloadingList != null) {
                        arrayList.addAll(listSelectDownloadingList);
                    }
                    if (listSelectWaitList != null) {
                        arrayList.addAll(listSelectWaitList);
                    }
                    if (AliyunDownloadManager.this.stopedList != null && listSelectStopedList != null) {
                        AliyunDownloadManager.this.stopedList.addAll(listSelectStopedList);
                    }
                    if (AliyunDownloadManager.this.completedList != null) {
                        AliyunDownloadManager.this.completedList.addAll(listSelectCompletedList);
                    }
                    final List listRemoveDuplicate = AliyunDownloadManager.this.removeDuplicate(listSelectWatchedList, arrayList);
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.14.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LoadDbDatasListener loadDbDatasListener2 = loadDbDatasListener;
                            if (loadDbDatasListener2 != null) {
                                loadDbDatasListener2.onLoadSuccess(listRemoveDuplicate);
                            }
                        }
                    });
                }
            });
        }
    }

    public ConcurrentLinkedQueue<AliyunDownloadMediaInfo> getCompletedList() {
        return this.completedList;
    }

    public String getDownloadDir() {
        return this.downloadDir;
    }

    public void getDownloadMediaInfoWithTvId(final String str, final LoadDbTvListDatasListenerr loadDbTvListDatasListenerr) {
        if (this.mDatabaseManager != null) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.15
                @Override // java.lang.Runnable
                public void run() {
                    List<LongVideoBean> listSelectAllWatchHistory = AliyunDownloadManager.this.mLongVideoDatabaseManager.selectAllWatchHistory();
                    final List<AliyunDownloadMediaInfo> listSelectAllByTvId = AliyunDownloadManager.this.mDatabaseManager.selectAllByTvId(str);
                    if (listSelectAllByTvId != null) {
                        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : listSelectAllByTvId) {
                            Iterator<LongVideoBean> it = listSelectAllWatchHistory.iterator();
                            while (it.hasNext()) {
                                if (it.next().getVideoId().equals(aliyunDownloadMediaInfo.getVid())) {
                                    aliyunDownloadMediaInfo.setWatchNumber(1);
                                }
                            }
                        }
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.15.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LoadDbTvListDatasListenerr loadDbTvListDatasListenerr2 = loadDbTvListDatasListenerr;
                            if (loadDbTvListDatasListenerr2 != null) {
                                loadDbTvListDatasListenerr2.onLoadTvListSuccess(listSelectAllByTvId);
                            }
                        }
                    });
                }
            });
        }
    }

    public DownloaderConfig getDownloaderConfig() {
        return this.mDownloaderConfig;
    }

    public ConcurrentLinkedQueue<AliyunDownloadMediaInfo> getDownloadingList() {
        return this.downloadingList;
    }

    public String getEncryptFilePath() {
        return this.encryptFilePath;
    }

    public int getMaxNum() {
        return this.mMaxNum;
    }

    public ConcurrentLinkedQueue<AliyunDownloadMediaInfo> getPreparedList() {
        return this.preparedList;
    }

    public ConcurrentLinkedQueue<AliyunDownloadMediaInfo> getStopedList() {
        return this.stopedList;
    }

    public ConcurrentLinkedQueue<AliyunDownloadMediaInfo> getWaitedList() {
        return this.waitedList;
    }

    public VidAuth getmVidAuth() {
        return this.mVidAuth;
    }

    public VidSts getmVidSts() {
        return this.mVidSts;
    }

    public void initCompleted(LinkedList<AliyunDownloadMediaInfo> linkedList) {
        if (this.completedList.size() != 0) {
            this.completedList.clear();
        }
        this.completedList.addAll(linkedList);
    }

    public void initDownloading(LinkedList<AliyunDownloadMediaInfo> linkedList) {
        if (this.downloadingList.size() != 0) {
            this.downloadingList.clear();
        }
        this.downloadingList.addAll(linkedList);
    }

    public void insertDb(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (aliyunDownloadMediaInfo == null || this.mDatabaseManager == null) {
            return;
        }
        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
        if (this.mDatabaseManager.selectItemExist(aliyunDownloadMediaInfo) <= 0) {
            this.mDatabaseManager.insert(aliyunDownloadMediaInfo);
        }
    }

    public void pauseDownload(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AliMediaDownloader aliMediaDownloader;
        if (aliyunDownloadMediaInfo == null || this.downloadInfos == null || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Error || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Stop || (aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo)) == null) {
            return;
        }
        aliMediaDownloader.stop();
        AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener != null) {
            aliyunDownloadInfoListener.onStop(aliyunDownloadMediaInfo);
        }
        autoDownload();
    }

    public void prepareDownloadByLongVideoBean(final VidSts vidSts, final LongVideoBean longVideoBean) {
        if (vidSts == null || TextUtils.isEmpty(vidSts.getVid())) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.3
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                AliyunDownloadManager.this.onPreparedCallback(vidSts, mediaInfo, arrayList, longVideoBean);
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onPrepared(arrayList);
                }
                AliyunDownloadManager.this.mJniDownloadLists.remove(aliMediaDownloaderCreate);
            }
        });
        aliMediaDownloaderCreate.setOnErrorListener(new AliMediaDownloader.OnErrorListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.4
            @Override // com.aliyun.downloader.AliMediaDownloader.OnErrorListener
            public void onError(ErrorInfo errorInfo) {
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
                    aliyunDownloadMediaInfo.setVidSts(vidSts);
                    AliyunDownloadManager.this.innerDownloadInfoListener.onError(aliyunDownloadMediaInfo, errorInfo.getCode(), errorInfo.getMsg(), null);
                    AliyunDownloadManager.this.mJniDownloadLists.remove(aliMediaDownloaderCreate);
                    aliMediaDownloaderCreate.release();
                }
            }
        });
        aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
        aliMediaDownloaderCreate.prepare(vidSts);
        this.mJniDownloadLists.add(aliMediaDownloaderCreate);
    }

    public void prepareDownloadByQuality(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo, final int i2) {
        if (aliyunDownloadMediaInfo == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        aliMediaDownloaderCreate.setSaveDir(this.downloadDir);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.8
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                for (TrackInfo trackInfo : mediaInfo.getTrackInfos()) {
                    if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD && trackInfo.getVodDefinition().equals(aliyunDownloadMediaInfo.getQuality())) {
                        aliyunDownloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                        aliyunDownloadMediaInfo.setTitle(mediaInfo.getTitle());
                        aliyunDownloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                        aliyunDownloadMediaInfo.setDuration(mediaInfo.getDuration());
                        aliyunDownloadMediaInfo.setTrackInfo(trackInfo);
                        aliyunDownloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                        aliyunDownloadMediaInfo.setFormat(trackInfo.getVodFormat());
                        aliyunDownloadMediaInfo.setSize(trackInfo.getVodFileSize());
                        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
                        arrayList.add(aliyunDownloadMediaInfo);
                        AliyunDownloadManager.this.downloadInfos.put(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
                        aliMediaDownloaderCreate.selectItem(trackInfo.getIndex());
                        int i3 = i2;
                        if (i3 == 0) {
                            if (AliyunDownloadManager.this.downloadingList.size() <= AliyunDownloadManager.this.mMaxNum) {
                                AliyunDownloadManager.this.setListener(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
                                aliMediaDownloaderCreate.start();
                                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                                    AliyunDownloadManager.this.innerDownloadInfoListener.onStart(aliyunDownloadMediaInfo);
                                }
                            } else if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                                AliyunDownloadManager.this.innerDownloadInfoListener.onWait(aliyunDownloadMediaInfo);
                            }
                        } else if (i3 == 1) {
                            AliyunDownloadManager.this.executeDelete(aliyunDownloadMediaInfo);
                        } else {
                            aliMediaDownloaderCreate.setSaveDir(AliyunDownloadManager.this.downloadDir);
                            aliMediaDownloaderCreate.selectItem(aliyunDownloadMediaInfo.getTrackInfo().getIndex());
                            if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                                AliyunDownloadManager.this.innerDownloadInfoListener.onAdd(aliyunDownloadMediaInfo);
                            }
                            AliyunDownloadManager.this.setErrorListener(aliMediaDownloaderCreate, aliyunDownloadMediaInfo);
                        }
                    }
                }
            }
        });
        setErrorListener(aliMediaDownloaderCreate, null);
        aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
        if (aliyunDownloadMediaInfo.getVidType() == 0) {
            if (aliyunDownloadMediaInfo.getVidSts() != null) {
                aliMediaDownloaderCreate.prepare(aliyunDownloadMediaInfo.getVidSts());
                return;
            } else {
                this.mVidSts.setVid(aliyunDownloadMediaInfo.getVid());
                aliMediaDownloaderCreate.prepare(this.mVidSts);
                return;
            }
        }
        if (aliyunDownloadMediaInfo.getVidAuth() != null) {
            aliMediaDownloaderCreate.prepare(aliyunDownloadMediaInfo.getVidAuth());
        } else {
            this.mVidAuth.setVid(aliyunDownloadMediaInfo.getVid());
            aliMediaDownloaderCreate.prepare(this.mVidAuth);
        }
    }

    public void prepareDownloadLists(final VidSts vidSts, AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (vidSts == null || TextUtils.isEmpty(vidSts.getVid())) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        if (this.downloadInfos.get(aliyunDownloadMediaInfo) != null) {
            return;
        }
        AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.5
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                AliyunDownloadManager.this.onPreparedCallback(vidSts, mediaInfo, arrayList, null);
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onPrepared(arrayList);
                }
            }
        });
        aliyunDownloadMediaInfo.setVidSts(vidSts);
        setErrorListener(aliMediaDownloaderCreate, aliyunDownloadMediaInfo);
        aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
        aliMediaDownloaderCreate.prepare(vidSts);
    }

    public void release() {
        DatabaseManager databaseManager = this.mDatabaseManager;
        if (databaseManager != null) {
            databaseManager.close();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue = this.preparedList;
        if (concurrentLinkedQueue != null) {
            concurrentLinkedQueue.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue2 = this.downloadingList;
        if (concurrentLinkedQueue2 != null) {
            concurrentLinkedQueue2.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue3 = this.completedList;
        if (concurrentLinkedQueue3 != null) {
            concurrentLinkedQueue3.clear();
        }
        ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue4 = this.waitedList;
        if (concurrentLinkedQueue4 != null) {
            concurrentLinkedQueue4.clear();
        }
        List<AliyunDownloadInfoListener> list = this.outListenerList;
        if (list != null) {
            list.clear();
        }
        List<AliMediaDownloader> list2 = this.mJniDownloadLists;
        if (list2 != null) {
            list2.clear();
        }
    }

    public void removeDownloadInfoListener(AliyunDownloadInfoListener aliyunDownloadInfoListener) {
        List<AliyunDownloadInfoListener> list;
        if (aliyunDownloadInfoListener == null || (list = this.outListenerList) == null) {
            return;
        }
        list.remove(aliyunDownloadInfoListener);
    }

    public void setDownloadDir(String str) {
        this.downloadDir = str;
    }

    public void setDownloadInfoListener(AliyunDownloadInfoListener aliyunDownloadInfoListener) {
        this.outListenerList.clear();
        if (aliyunDownloadInfoListener != null) {
            this.outListenerList.add(aliyunDownloadInfoListener);
        }
    }

    public void setDownloaderConfig(DownloaderConfig downloaderConfig) {
        this.mDownloaderConfig = downloaderConfig;
    }

    public void setEncryptFilePath(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.encryptFilePath = str;
    }

    public void setMaxNum(int i2) {
        if (i2 <= 1) {
            i2 = 1;
        }
        if (i2 > 5) {
            i2 = 5;
        }
        this.mMaxNum = i2;
    }

    public void setRefreshStsCallback(RefreshStsCallback refreshStsCallback) {
    }

    public void setmVidAuth(VidAuth vidAuth) {
        this.mVidAuth = vidAuth;
    }

    public void setmVidSts(VidSts vidSts) {
        this.mVidSts = vidSts;
    }

    public void startDownload(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AliyunDownloadInfoListener aliyunDownloadInfoListener;
        boolean z2;
        if (aliyunDownloadMediaInfo == null || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Start || this.downloadingList.contains(aliyunDownloadMediaInfo)) {
            return;
        }
        if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete && new File(aliyunDownloadMediaInfo.getSavePath()).exists()) {
            Toast.makeText(this.mContext.getApplicationContext(), this.mContext.getResources().getString(R.string.alivc_video_download_finish_tips), 0).show();
            return;
        }
        if (!DownloadUtils.isStorageAlarm(this.mContext, aliyunDownloadMediaInfo)) {
            AliyunDownloadInfoListener aliyunDownloadInfoListener2 = this.innerDownloadInfoListener;
            if (aliyunDownloadInfoListener2 != null) {
                aliyunDownloadInfoListener2.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, MEMORY_LESS_MSG, null);
                return;
            }
            return;
        }
        if (this.downloadingList.size() >= this.mMaxNum) {
            if (this.waitedList.contains(aliyunDownloadMediaInfo) || (aliyunDownloadInfoListener = this.innerDownloadInfoListener) == null) {
                return;
            }
            aliyunDownloadInfoListener.onWait(aliyunDownloadMediaInfo);
            return;
        }
        AliMediaDownloader aliMediaDownloaderCreate = this.downloadInfos.get(aliyunDownloadMediaInfo);
        if (aliMediaDownloaderCreate == null) {
            aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
            aliMediaDownloaderCreate.setSaveDir(this.downloadDir);
            this.downloadInfos.put(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(BuildConfig.APPLICATION_ID.equals(this.mContext.getPackageName()) ? SdkConstant.UMENG_ALIS : "hukaobang", 0);
        String string = sharedPreferences.getString(sharedPreferences.getString("app_id218", "") + "_download_definition", QualityValue.QUALITY_FLUENT);
        List<TrackInfo> list = this.trackInfosMap.get(aliyunDownloadMediaInfo.getVid());
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(QualityValue.QUALITY_FLUENT, 0);
        arrayMap.put(QualityValue.QUALITY_LOW, 1);
        arrayMap.put(QualityValue.QUALITY_STAND, 2);
        arrayMap.put(QualityValue.QUALITY_HIGH, 3);
        arrayMap.put(QualityValue.QUALITY_2K, 4);
        arrayMap.put(QualityValue.QUALITY_4K, 5);
        arrayMap.put(QualityValue.QUALITY_ORIGINAL, 6);
        Integer num = (Integer) arrayMap.get(string);
        if (num == null) {
            num = 0;
        }
        if (list == null || list.isEmpty()) {
            aliMediaDownloaderCreate.selectItem(num.intValue());
        } else {
            int index = list.get(list.size() - 1).getIndex();
            if (num.intValue() >= index) {
                aliMediaDownloaderCreate.selectItem(Integer.valueOf(index).intValue());
            } else {
                Iterator<TrackInfo> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    TrackInfo next = it.next();
                    if (TextUtils.equals(next.getVodDefinition(), string)) {
                        aliMediaDownloaderCreate.selectItem(next.getIndex());
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    aliMediaDownloaderCreate.selectItem(0);
                }
            }
        }
        setListener(aliyunDownloadMediaInfo, aliMediaDownloaderCreate);
        if (aliyunDownloadMediaInfo.getVidType() != 1) {
            VidSts vidSts = this.mVidSts;
            if (vidSts == null) {
                AliyunDownloadInfoListener aliyunDownloadInfoListener3 = this.innerDownloadInfoListener;
                if (aliyunDownloadInfoListener3 != null) {
                    aliyunDownloadInfoListener3.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN, this.mContext.getString(R.string.alivc_player_video_download_sts_and_auth_is_empty), "");
                    return;
                }
                return;
            }
            vidSts.setVid(aliyunDownloadMediaInfo.getVid());
            aliMediaDownloaderCreate.updateSource(this.mVidSts);
        } else {
            if (aliyunDownloadMediaInfo.getVidAuth() == null) {
                AliyunDownloadInfoListener aliyunDownloadInfoListener4 = this.innerDownloadInfoListener;
                if (aliyunDownloadInfoListener4 != null) {
                    aliyunDownloadInfoListener4.onError(aliyunDownloadMediaInfo, ErrorCode.ERROR_UNKNOWN, this.mContext.getString(R.string.alivc_player_video_download_sts_and_auth_is_empty), "");
                    return;
                }
                return;
            }
            aliMediaDownloaderCreate.updateSource(aliyunDownloadMediaInfo.getVidAuth());
        }
        aliMediaDownloaderCreate.start();
        AliyunDownloadInfoListener aliyunDownloadInfoListener5 = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener5 != null) {
            aliyunDownloadInfoListener5.onStart(aliyunDownloadMediaInfo);
        }
    }

    public void stopDownload(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        AliMediaDownloader aliMediaDownloader;
        if (aliyunDownloadMediaInfo == null || this.downloadInfos == null || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Error || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Stop || (aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo)) == null) {
            return;
        }
        aliMediaDownloader.stop();
        releaseJniDownloader(aliyunDownloadMediaInfo);
        AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener != null) {
            aliyunDownloadInfoListener.onStop(aliyunDownloadMediaInfo);
        }
        autoDownload();
    }

    public void stopDownloads(ConcurrentLinkedQueue<AliyunDownloadMediaInfo> concurrentLinkedQueue) {
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.size() == 0 || this.downloadInfos == null) {
            return;
        }
        Iterator<AliyunDownloadMediaInfo> it = concurrentLinkedQueue.iterator();
        while (it.hasNext()) {
            AliyunDownloadMediaInfo next = it.next();
            AliyunDownloadMediaInfo.Status status = next.getStatus();
            AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Start;
            if (status == status2 || next.getStatus() == AliyunDownloadMediaInfo.Status.Wait) {
                AliMediaDownloader aliMediaDownloader = this.downloadInfos.get(next);
                if (aliMediaDownloader != null && next.getStatus() == status2) {
                    aliMediaDownloader.stop();
                    releaseJniDownloader(next);
                    AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
                    if (aliyunDownloadInfoListener != null) {
                        aliyunDownloadInfoListener.onStop(next);
                    }
                }
            }
        }
    }

    public void updateDb(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        DatabaseManager databaseManager;
        if (aliyunDownloadMediaInfo == null || (databaseManager = this.mDatabaseManager) == null) {
            return;
        }
        databaseManager.update(aliyunDownloadMediaInfo);
    }

    public void findDatasByDb(final LoadDbDatasListener loadDbDatasListener) {
        if (this.mDatabaseManager != null) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.13
                @Override // java.lang.Runnable
                public void run() {
                    List<AliyunDownloadMediaInfo> listSelectPreparedList = AliyunDownloadManager.this.mDatabaseManager.selectPreparedList();
                    List<AliyunDownloadMediaInfo> listSelectStopedList = AliyunDownloadManager.this.mDatabaseManager.selectStopedList();
                    List<AliyunDownloadMediaInfo> listSelectCompletedList = AliyunDownloadManager.this.mDatabaseManager.selectCompletedList();
                    List<AliyunDownloadMediaInfo> listSelectDownloadingList = AliyunDownloadManager.this.mDatabaseManager.selectDownloadingList();
                    List<AliyunDownloadMediaInfo> listSelectWaitList = AliyunDownloadManager.this.mDatabaseManager.selectWaitList();
                    final ArrayList arrayList = new ArrayList();
                    if (listSelectPreparedList != null) {
                        Iterator<AliyunDownloadMediaInfo> it = listSelectPreparedList.iterator();
                        while (it.hasNext()) {
                            it.next().setStatus(AliyunDownloadMediaInfo.Status.Stop);
                        }
                        arrayList.addAll(listSelectPreparedList);
                    }
                    if (listSelectStopedList != null) {
                        arrayList.addAll(listSelectStopedList);
                    }
                    if (listSelectCompletedList != null) {
                        arrayList.addAll(listSelectCompletedList);
                    }
                    if (listSelectDownloadingList != null) {
                        Iterator<AliyunDownloadMediaInfo> it2 = listSelectDownloadingList.iterator();
                        while (it2.hasNext()) {
                            it2.next().setStatus(AliyunDownloadMediaInfo.Status.Stop);
                        }
                        arrayList.addAll(listSelectDownloadingList);
                    }
                    if (listSelectWaitList != null) {
                        arrayList.addAll(listSelectWaitList);
                    }
                    if (AliyunDownloadManager.this.stopedList != null) {
                        if (listSelectDownloadingList != null) {
                            AliyunDownloadManager.this.stopedList.addAll(listSelectDownloadingList);
                        }
                        if (listSelectStopedList != null) {
                            AliyunDownloadManager.this.stopedList.addAll(listSelectStopedList);
                        }
                        if (listSelectPreparedList != null) {
                            AliyunDownloadManager.this.stopedList.addAll(listSelectPreparedList);
                        }
                    }
                    if (AliyunDownloadManager.this.completedList != null && listSelectCompletedList != null) {
                        AliyunDownloadManager.this.completedList.addAll(listSelectCompletedList);
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.13.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LoadDbDatasListener loadDbDatasListener2 = loadDbDatasListener;
                            if (loadDbDatasListener2 != null) {
                                loadDbDatasListener2.onLoadSuccess(arrayList);
                            }
                        }
                    });
                }
            });
        }
    }

    public void pauseDownload(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, boolean z2) {
        AliMediaDownloader aliMediaDownloader;
        if (aliyunDownloadMediaInfo == null || this.downloadInfos == null || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Error || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Stop || (aliMediaDownloader = this.downloadInfos.get(aliyunDownloadMediaInfo)) == null) {
            return;
        }
        aliMediaDownloader.stop();
        AliyunDownloadInfoListener aliyunDownloadInfoListener = this.innerDownloadInfoListener;
        if (aliyunDownloadInfoListener != null) {
            aliyunDownloadInfoListener.onStop(aliyunDownloadMediaInfo);
        }
        if (z2) {
            autoDownload();
        }
    }

    public void prepareDownload(final VidSts vidSts) {
        if (vidSts == null || TextUtils.isEmpty(vidSts.getVid())) {
            return;
        }
        this.mVidSts = vidSts;
        final ArrayList arrayList = new ArrayList();
        final AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.6
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                AliyunDownloadManager.this.trackInfosMap.put(vidSts.getVid(), mediaInfo.getTrackInfos());
                AliyunDownloadManager.this.onPreparedCallback(vidSts, mediaInfo, arrayList, null);
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onPrepared(arrayList);
                }
                AliyunDownloadManager.this.mJniDownloadLists.remove(aliMediaDownloaderCreate);
            }
        });
        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
        aliyunDownloadMediaInfo.setVidSts(vidSts);
        setErrorListener(aliMediaDownloaderCreate, aliyunDownloadMediaInfo);
        aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
        aliMediaDownloaderCreate.prepare(vidSts);
        this.mJniDownloadLists.add(aliMediaDownloaderCreate);
    }

    public void prepareDownload(final VidAuth vidAuth) {
        if (vidAuth == null || TextUtils.isEmpty(vidAuth.getVid())) {
            return;
        }
        this.mVidAuth = vidAuth;
        final ArrayList arrayList = new ArrayList();
        AliMediaDownloader aliMediaDownloaderCreate = AliDownloaderFactory.create(this.mContext);
        aliMediaDownloaderCreate.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() { // from class: com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager.7
            @Override // com.aliyun.downloader.AliMediaDownloader.OnPreparedListener
            public void onPrepared(MediaInfo mediaInfo) {
                for (TrackInfo trackInfo : mediaInfo.getTrackInfos()) {
                    if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
                        aliyunDownloadMediaInfo.setVid(vidAuth.getVid());
                        aliyunDownloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                        aliyunDownloadMediaInfo.setTitle(mediaInfo.getTitle());
                        aliyunDownloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                        aliyunDownloadMediaInfo.setDuration(mediaInfo.getDuration());
                        aliyunDownloadMediaInfo.setTrackInfo(trackInfo);
                        aliyunDownloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                        aliyunDownloadMediaInfo.setFormat(trackInfo.getVodFormat());
                        aliyunDownloadMediaInfo.setSize(trackInfo.getVodFileSize());
                        aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
                        aliyunDownloadMediaInfo.setVidAuth(vidAuth);
                        aliyunDownloadMediaInfo.setVidType(1);
                        arrayList.add(aliyunDownloadMediaInfo);
                        AliMediaDownloader aliMediaDownloaderCreate2 = AliDownloaderFactory.create(AliyunDownloadManager.this.mContext);
                        aliMediaDownloaderCreate2.setSaveDir(AliyunDownloadManager.this.downloadDir);
                        AliyunDownloadManager.this.downloadInfos.put(aliyunDownloadMediaInfo, aliMediaDownloaderCreate2);
                    }
                }
                if (AliyunDownloadManager.this.innerDownloadInfoListener != null) {
                    AliyunDownloadManager.this.innerDownloadInfoListener.onPrepared(arrayList);
                }
            }
        });
        setErrorListener(aliMediaDownloaderCreate, null);
        aliMediaDownloaderCreate.setDownloaderConfig(this.mDownloaderConfig);
        aliMediaDownloaderCreate.prepare(vidAuth);
    }
}
