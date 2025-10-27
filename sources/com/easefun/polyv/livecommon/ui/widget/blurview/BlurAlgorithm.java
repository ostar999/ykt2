package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public interface BlurAlgorithm {
    Bitmap blur(Bitmap bitmap, float blurRadius);

    boolean canModifyBitmap();

    void destroy();

    @NonNull
    Bitmap.Config getSupportedBitmapConfig();
}
