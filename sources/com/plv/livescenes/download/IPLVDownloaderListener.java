package com.plv.livescenes.download;

import androidx.annotation.MainThread;

/* loaded from: classes4.dex */
public interface IPLVDownloaderListener {
    @MainThread
    void onFailure(int i2);

    @MainThread
    void onProgress(long j2, long j3);

    @MainThread
    void onSuccess(PLVPlaybackCacheVO pLVPlaybackCacheVO);
}
