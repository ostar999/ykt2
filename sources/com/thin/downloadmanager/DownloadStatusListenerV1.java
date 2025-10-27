package com.thin.downloadmanager;

/* loaded from: classes6.dex */
public interface DownloadStatusListenerV1 {
    void onDownloadComplete(DownloadRequest downloadRequest);

    void onDownloadFailed(DownloadRequest downloadRequest, int i2, String str);

    void onProgress(DownloadRequest downloadRequest, long j2, long j3, int i2);
}
