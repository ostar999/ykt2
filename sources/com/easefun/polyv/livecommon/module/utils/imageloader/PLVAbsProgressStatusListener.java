package com.easefun.polyv.livecommon.module.utils.imageloader;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public abstract class PLVAbsProgressStatusListener extends PLVAbsProgressListener {
    private static final int STATUS_FAILED = 2;
    private static final int STATUS_IDLE = 0;
    private static final int STATUS_READY = 1;
    private int progress;
    private int status;

    public PLVAbsProgressStatusListener(String url) {
        super(url);
        this.status = 0;
        this.progress = -1;
    }

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressListener
    public void onFailed(@Nullable Exception e2, Object model) {
        if (this.status == 1) {
            return;
        }
        this.status = 2;
        onFailedStatus(e2, model);
    }

    public abstract void onFailedStatus(@Nullable Exception e2, Object model);

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener
    public void onProgress(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes) {
        int i2 = this.status;
        if (i2 == 1 || i2 == 2 || percentage <= this.progress) {
            return;
        }
        this.progress = percentage;
        onProgressStatus(url, isComplete, percentage, bytesRead, totalBytes);
    }

    public abstract void onProgressStatus(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes);

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.PLVAbsProgressListener
    public void onResourceReady(Drawable drawable) {
        this.status = 1;
        onResourceReadyStatus(drawable);
    }

    public abstract void onResourceReadyStatus(Drawable drawable);

    @Override // com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener
    public void onStart(String url) {
        int i2 = this.status;
        if (i2 == 1 || i2 == 2) {
            return;
        }
        onStartStatus(url);
    }

    public abstract void onStartStatus(String url);
}
