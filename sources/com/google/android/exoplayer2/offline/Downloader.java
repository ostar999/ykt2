package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes3.dex */
public interface Downloader {

    public interface ProgressListener {
        void onProgress(long j2, long j3, float f2);
    }

    void cancel();

    void download(@Nullable ProgressListener progressListener) throws InterruptedException, IOException;

    void remove();
}
