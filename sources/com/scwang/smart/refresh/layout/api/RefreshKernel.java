package com.scwang.smart.refresh.layout.api;

import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/* loaded from: classes6.dex */
public interface RefreshKernel {
    ValueAnimator animSpinner(int i2);

    RefreshKernel finishTwoLevel();

    @NonNull
    RefreshContent getRefreshContent();

    @NonNull
    RefreshLayout getRefreshLayout();

    RefreshKernel moveSpinner(int i2, boolean z2);

    RefreshKernel requestDefaultTranslationContentFor(@NonNull RefreshComponent refreshComponent, boolean z2);

    RefreshKernel requestDrawBackgroundFor(@NonNull RefreshComponent refreshComponent, int i2);

    RefreshKernel requestFloorBottomPullUpToCloseRate(float f2);

    RefreshKernel requestFloorDuration(int i2);

    RefreshKernel requestNeedTouchEventFor(@NonNull RefreshComponent refreshComponent, boolean z2);

    RefreshKernel requestRemeasureHeightFor(@NonNull RefreshComponent refreshComponent);

    RefreshKernel setState(@NonNull RefreshState refreshState);

    RefreshKernel startTwoLevel(boolean z2);
}
