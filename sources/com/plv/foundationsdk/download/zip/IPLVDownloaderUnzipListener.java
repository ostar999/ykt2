package com.plv.foundationsdk.download.zip;

import androidx.annotation.MainThread;

/* loaded from: classes4.dex */
public interface IPLVDownloaderUnzipListener {
    @MainThread
    void onDone();

    @MainThread
    void onProgress(int i2);
}
