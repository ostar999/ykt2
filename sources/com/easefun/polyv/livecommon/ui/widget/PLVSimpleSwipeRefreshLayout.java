package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/* loaded from: classes3.dex */
public class PLVSimpleSwipeRefreshLayout extends SwipeRefreshLayout {
    public PLVSimpleSwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public PLVSimpleSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(false);
    }
}
