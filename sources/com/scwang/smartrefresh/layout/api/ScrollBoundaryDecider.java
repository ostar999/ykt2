package com.scwang.smartrefresh.layout.api;

import android.view.View;

/* loaded from: classes6.dex */
public interface ScrollBoundaryDecider {
    boolean canLoadMore(View view);

    boolean canRefresh(View view);
}
