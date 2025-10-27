package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
class NoOpController implements BlurController {
    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public void destroy() {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public boolean draw(Canvas canvas) {
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurAlgorithm(BlurAlgorithm algorithm) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurAutoUpdate(boolean enabled) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurEnabled(boolean enabled) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setBlurRadius(float radius) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setFrameClearDrawable(@Nullable Drawable windowBackground) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setHasFixedTransformationMatrix(boolean hasFixedTransformationMatrix) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurViewFacade
    public BlurViewFacade setOverlayColor(int overlayColor) {
        return this;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurController
    public void updateBlurViewSize() {
    }
}
