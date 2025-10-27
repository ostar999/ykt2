package com.easefun.polyv.livecloudclass.modules.linkmic.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.plv.foundationsdk.utils.PLVScreenUtils;

/* loaded from: classes3.dex */
public class PLVLinkMicRvLandscapeItemDecoration extends RecyclerView.ItemDecoration {
    private int bottomOffset = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (view.getVisibility() == 0) {
            rect.bottom = this.bottomOffset;
        }
    }

    public void setLandscape() {
        this.bottomOffset = PLVScreenUtils.dip2px(8.0f);
    }

    public void setPortrait() {
        this.bottomOffset = 0;
    }
}
