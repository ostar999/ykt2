package com.hjq.http.listener;

import java.io.File;

/* loaded from: classes4.dex */
public interface OnDownloadListener {
    void onByte(File file, long j2, long j3);

    void onComplete(File file);

    void onEnd(File file);

    void onError(File file, Exception exc);

    void onProgress(File file, int i2);

    void onStart(File file);
}
