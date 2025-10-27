package com.aliyun.svideo.common.baseAdapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/* loaded from: classes2.dex */
public abstract class LoadMoreView {
    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_END = 4;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_LOADING = 2;
    private int mLoadMoreStatus = 1;
    private boolean mLoadMoreEndGone = false;

    private void visibleLoadEnd(BaseViewHolder baseViewHolder, boolean z2) {
        int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            baseViewHolder.setGone(loadEndViewId, z2);
        }
    }

    private void visibleLoadFail(BaseViewHolder baseViewHolder, boolean z2) {
        baseViewHolder.setGone(getLoadFailViewId(), z2);
    }

    private void visibleLoading(BaseViewHolder baseViewHolder, boolean z2) {
        baseViewHolder.setGone(getLoadingViewId(), z2);
    }

    public void convert(BaseViewHolder baseViewHolder) {
        int i2 = this.mLoadMoreStatus;
        if (i2 == 1) {
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, false);
            return;
        }
        if (i2 == 2) {
            visibleLoading(baseViewHolder, true);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, false);
        } else if (i2 == 3) {
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, true);
            visibleLoadEnd(baseViewHolder, false);
        } else {
            if (i2 != 4) {
                return;
            }
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, true);
        }
    }

    @LayoutRes
    public abstract int getLayoutId();

    @IdRes
    public abstract int getLoadEndViewId();

    @IdRes
    public abstract int getLoadFailViewId();

    public int getLoadMoreStatus() {
        return this.mLoadMoreStatus;
    }

    @IdRes
    public abstract int getLoadingViewId();

    @Deprecated
    public boolean isLoadEndGone() {
        return this.mLoadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return this.mLoadMoreEndGone;
    }

    public final void setLoadMoreEndGone(boolean z2) {
        this.mLoadMoreEndGone = z2;
    }

    public void setLoadMoreStatus(int i2) {
        this.mLoadMoreStatus = i2;
    }
}
