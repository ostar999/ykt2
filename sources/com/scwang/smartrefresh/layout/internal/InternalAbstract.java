package com.scwang.smartrefresh.layout.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;

/* loaded from: classes6.dex */
public abstract class InternalAbstract extends RelativeLayout implements RefreshInternal {
    protected SpinnerStyle mSpinnerStyle;
    protected RefreshInternal mWrappedInternal;
    protected View mWrappedView;

    /* JADX WARN: Multi-variable type inference failed */
    public InternalAbstract(@NonNull View view) {
        this(view, view instanceof RefreshInternal ? (RefreshInternal) view : null);
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        return (obj instanceof RefreshInternal) && getView() == ((RefreshInternal) obj).getView();
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    @NonNull
    public SpinnerStyle getSpinnerStyle() {
        int i2;
        SpinnerStyle spinnerStyle = this.mSpinnerStyle;
        if (spinnerStyle != null) {
            return spinnerStyle;
        }
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal != null && refreshInternal != this) {
            return refreshInternal.getSpinnerStyle();
        }
        View view = this.mWrappedView;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                SpinnerStyle spinnerStyle2 = ((SmartRefreshLayout.LayoutParams) layoutParams).spinnerStyle;
                this.mSpinnerStyle = spinnerStyle2;
                if (spinnerStyle2 != null) {
                    return spinnerStyle2;
                }
            }
            if (layoutParams != null && ((i2 = layoutParams.height) == 0 || i2 == -1)) {
                for (SpinnerStyle spinnerStyle3 : SpinnerStyle.values) {
                    if (spinnerStyle3.scale) {
                        this.mSpinnerStyle = spinnerStyle3;
                        return spinnerStyle3;
                    }
                }
            }
        }
        SpinnerStyle spinnerStyle4 = SpinnerStyle.Translate;
        this.mSpinnerStyle = spinnerStyle4;
        return spinnerStyle4;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    @NonNull
    public View getView() {
        View view = this.mWrappedView;
        return view == null ? this : view;
    }

    public boolean isSupportHorizontalDrag() {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        return (refreshInternal == null || refreshInternal == this || !refreshInternal.isSupportHorizontalDrag()) ? false : true;
    }

    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return 0;
        }
        return refreshInternal.onFinish(refreshLayout, z2);
    }

    public void onHorizontalDrag(float f2, int i2, int i3) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.onHorizontalDrag(f2, i2, i3);
    }

    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i2, int i3) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal != null && refreshInternal != this) {
            refreshInternal.onInitialized(refreshKernel, i2, i3);
            return;
        }
        View view = this.mWrappedView;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                refreshKernel.requestDrawBackgroundFor(this, ((SmartRefreshLayout.LayoutParams) layoutParams).backgroundColor);
            }
        }
    }

    public void onMoving(boolean z2, float f2, int i2, int i3, int i4) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.onMoving(z2, f2, i2, i3, i4);
    }

    public void onReleased(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.onReleased(refreshLayout, i2, i3);
    }

    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.onStartAnimator(refreshLayout, i2, i3);
    }

    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        if ((this instanceof RefreshFooterWrapper) && (refreshInternal instanceof RefreshHeader)) {
            if (refreshState.isFooter) {
                refreshState = refreshState.toHeader();
            }
            if (refreshState2.isFooter) {
                refreshState2 = refreshState2.toHeader();
            }
        } else if ((this instanceof RefreshHeaderWrapper) && (refreshInternal instanceof RefreshFooter)) {
            if (refreshState.isHeader) {
                refreshState = refreshState.toFooter();
            }
            if (refreshState2.isHeader) {
                refreshState2 = refreshState2.toFooter();
            }
        }
        RefreshInternal refreshInternal2 = this.mWrappedInternal;
        if (refreshInternal2 != null) {
            refreshInternal2.onStateChanged(refreshLayout, refreshState, refreshState2);
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean setNoMoreData(boolean z2) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        return (refreshInternal instanceof RefreshFooter) && ((RefreshFooter) refreshInternal).setNoMoreData(z2);
    }

    public void setPrimaryColors(@ColorInt int... iArr) {
        RefreshInternal refreshInternal = this.mWrappedInternal;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.setPrimaryColors(iArr);
    }

    public InternalAbstract(@NonNull View view, @Nullable RefreshInternal refreshInternal) {
        super(view.getContext(), null, 0);
        this.mWrappedView = view;
        this.mWrappedInternal = refreshInternal;
        if ((this instanceof RefreshFooterWrapper) && (refreshInternal instanceof RefreshHeader) && refreshInternal.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
            refreshInternal.getView().setScaleY(-1.0f);
            return;
        }
        if (this instanceof RefreshHeaderWrapper) {
            RefreshInternal refreshInternal2 = this.mWrappedInternal;
            if ((refreshInternal2 instanceof RefreshFooter) && refreshInternal2.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                refreshInternal.getView().setScaleY(-1.0f);
            }
        }
    }

    public InternalAbstract(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
