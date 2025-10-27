package com.scwang.smartrefresh.layout.api;

import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/* loaded from: classes6.dex */
public interface RefreshKernel {
    ValueAnimator animSpinner(int i2);

    RefreshKernel finishTwoLevel();

    @NonNull
    RefreshContent getRefreshContent();

    @NonNull
    RefreshLayout getRefreshLayout();

    RefreshKernel moveSpinner(int i2, boolean z2);

    RefreshKernel requestDefaultTranslationContentFor(@NonNull RefreshInternal refreshInternal, boolean z2);

    RefreshKernel requestDrawBackgroundFor(@NonNull RefreshInternal refreshInternal, int i2);

    RefreshKernel requestFloorDuration(int i2);

    RefreshKernel requestNeedTouchEventFor(@NonNull RefreshInternal refreshInternal, boolean z2);

    RefreshKernel requestRemeasureHeightFor(@NonNull RefreshInternal refreshInternal);

    RefreshKernel setState(@NonNull RefreshState refreshState);

    RefreshKernel startTwoLevel(boolean z2);
}
