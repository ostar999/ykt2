package com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress;

import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public abstract class PLVOnProgressListener {
    private PLVOnProgressListener listener;
    private String url;

    public PLVOnProgressListener(@NonNull String url) {
        this.url = url;
    }

    public PLVOnProgressListener getTransListener() {
        return this.listener;
    }

    public String getUrl() {
        return this.url;
    }

    public abstract void onProgress(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes);

    public abstract void onStart(String url);

    public PLVOnProgressListener transListener(PLVOnProgressListener listener) {
        this.listener = listener;
        return this;
    }
}
