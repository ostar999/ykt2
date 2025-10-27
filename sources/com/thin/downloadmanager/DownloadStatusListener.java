package com.thin.downloadmanager;

@Deprecated
/* loaded from: classes6.dex */
public interface DownloadStatusListener {
    void onDownloadComplete(int i2);

    void onDownloadFailed(int i2, int i3, String str);

    void onProgress(int i2, long j2, long j3, int i3);
}
