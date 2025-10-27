package com.easefun.polyv.livecommon.module.utils.imageloader;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVOnProgressListener;

/* loaded from: classes3.dex */
public abstract class PLVAbsProgressListener extends PLVOnProgressListener {
    public PLVAbsProgressListener(String url) {
        super(url);
    }

    public abstract void onFailed(@Nullable Exception e2, Object model);

    public abstract void onResourceReady(Drawable drawable);
}
