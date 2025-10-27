package com.plv.foundationsdk.download;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public interface IPLVDownloader {

    public static abstract class Builder {
        @NonNull
        public abstract String createDwonloadKey();
    }

    void deleteDownloadContent();

    boolean isDownloading();

    void start();

    void stop();
}
