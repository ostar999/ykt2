package com.ykb.ebook.util;

import android.content.Context;
import androidx.recyclerview.widget.LinearSmoothScroller;

/* loaded from: classes7.dex */
public class TopSmoothScroller extends LinearSmoothScroller {
    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    public int getHorizontalSnapPreference() {
        return -1;
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    public int getVerticalSnapPreference() {
        return -1;
    }
}
