package com.plv.livescenes.download;

import com.plv.foundationsdk.download.PLVDownloaderSpeed;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.livescenes.download.listener.IPLVDownloaderBeforeStartListener;
import com.plv.livescenes.download.listener.IPLVDownloaderSpeedListener;
import com.plv.livescenes.download.listener.IPLVDownloaderStopListener;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class IPLVDownloaderListenerEvent {
    IPLVDownloaderBeforeStartListener<PLVPlaybackCacheVO> beforeStartListener;
    IPLVDownloaderListener downloaderListener;
    PLVDownloaderSpeed downloaderSpeed;
    IPLVDownloaderSpeedListener speedListener;
    IPLVDownloaderStopListener stopListener;

    public void callBeforeStartListener(final PLVDownloader pLVDownloader, final PLVPlaybackCacheVO pLVPlaybackCacheVO) {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.livescenes.download.IPLVDownloaderListenerEvent.4
            @Override // java.lang.Runnable
            public void run() {
                IPLVDownloaderBeforeStartListener<PLVPlaybackCacheVO> iPLVDownloaderBeforeStartListener = IPLVDownloaderListenerEvent.this.beforeStartListener;
                if (iPLVDownloaderBeforeStartListener != null) {
                    iPLVDownloaderBeforeStartListener.onBeforeStart(pLVDownloader, pLVPlaybackCacheVO);
                }
            }
        });
    }

    public void callProgressListenerDownload(final long j2, final long j3) {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.livescenes.download.IPLVDownloaderListenerEvent.1
            @Override // java.lang.Runnable
            public void run() {
                IPLVDownloaderListener iPLVDownloaderListener = IPLVDownloaderListenerEvent.this.downloaderListener;
                if (iPLVDownloaderListener != null) {
                    iPLVDownloaderListener.onProgress(j2, j3);
                }
            }
        });
    }

    public void callProgressListenerFail(final int i2, String str, String str2, List<String> list, List<String> list2) {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.livescenes.download.IPLVDownloaderListenerEvent.2
            @Override // java.lang.Runnable
            public void run() {
                IPLVDownloaderListener iPLVDownloaderListener = IPLVDownloaderListenerEvent.this.downloaderListener;
                if (iPLVDownloaderListener != null) {
                    iPLVDownloaderListener.onFailure(i2);
                }
            }
        });
    }

    public void callProgressListenerSuccess(final PLVPlaybackCacheVO pLVPlaybackCacheVO) {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.livescenes.download.IPLVDownloaderListenerEvent.5
            @Override // java.lang.Runnable
            public void run() {
                IPLVDownloaderListener iPLVDownloaderListener = IPLVDownloaderListenerEvent.this.downloaderListener;
                if (iPLVDownloaderListener != null) {
                    iPLVDownloaderListener.onSuccess(pLVPlaybackCacheVO);
                }
            }
        });
    }

    public void callStopListener() {
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.livescenes.download.IPLVDownloaderListenerEvent.3
            @Override // java.lang.Runnable
            public void run() {
                IPLVDownloaderStopListener iPLVDownloaderStopListener = IPLVDownloaderListenerEvent.this.stopListener;
                if (iPLVDownloaderStopListener != null) {
                    iPLVDownloaderStopListener.onStop();
                }
            }
        });
    }

    public void setDownloadBeforeStartListener(IPLVDownloaderBeforeStartListener<PLVPlaybackCacheVO> iPLVDownloaderBeforeStartListener) {
        this.beforeStartListener = iPLVDownloaderBeforeStartListener;
    }

    public void setDownloadListener(IPLVDownloaderListener iPLVDownloaderListener) {
        this.downloaderListener = iPLVDownloaderListener;
    }

    public void setDownloadSpeedListener(IPLVDownloaderSpeedListener iPLVDownloaderSpeedListener) {
        this.speedListener = iPLVDownloaderSpeedListener;
    }

    public void setDownloadStopListener(IPLVDownloaderStopListener iPLVDownloaderStopListener) {
        this.stopListener = iPLVDownloaderStopListener;
    }

    public void callProgressListenerFail(int i2, String str, String str2) {
        callProgressListenerFail(i2, str, str2, null, null);
    }

    public void callProgressListenerFail(int i2, String str, String str2, List<String> list) {
        callProgressListenerFail(i2, str, str2, list, null);
    }
}
