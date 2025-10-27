package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
class NoOpBlurAlgorithm implements BlurAlgorithm {
    private static final String TAG = "NoOpBlurAlgorithm";

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    public Bitmap blur(Bitmap bitmap, float blurRadius) {
        return bitmap;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    public boolean canModifyBitmap() {
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    public void destroy() {
        PLVCommonLog.d(TAG, "destory");
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    @NonNull
    public Bitmap.Config getSupportedBitmapConfig() {
        return Bitmap.Config.ARGB_8888;
    }
}
