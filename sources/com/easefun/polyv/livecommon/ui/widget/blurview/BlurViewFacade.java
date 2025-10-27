package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public interface BlurViewFacade {
    BlurViewFacade setBlurAlgorithm(BlurAlgorithm algorithm);

    BlurViewFacade setBlurAutoUpdate(boolean enabled);

    BlurViewFacade setBlurEnabled(boolean enabled);

    BlurViewFacade setBlurRadius(float radius);

    BlurViewFacade setFrameClearDrawable(@Nullable Drawable frameClearDrawable);

    BlurViewFacade setHasFixedTransformationMatrix(boolean hasFixedTransformationMatrix);

    BlurViewFacade setOverlayColor(@ColorInt int overlayColor);
}
