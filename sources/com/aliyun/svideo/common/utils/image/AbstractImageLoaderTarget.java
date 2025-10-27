package com.aliyun.svideo.common.utils.image;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public abstract class AbstractImageLoaderTarget<R> {
    public void onLoadCleared(@Nullable Drawable drawable) {
    }

    public void onLoadFailed(@Nullable Drawable drawable) {
    }

    public void onLoadStarted() {
    }

    public abstract void onResourceReady(@NonNull R r2);
}
