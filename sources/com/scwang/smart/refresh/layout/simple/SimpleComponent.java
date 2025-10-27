package com.scwang.smart.refresh.layout.simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

/* loaded from: classes6.dex */
public abstract class SimpleComponent extends RelativeLayout implements RefreshComponent {
    protected SpinnerStyle mSpinnerStyle;
    protected RefreshComponent mWrappedInternal;
    protected View mWrappedView;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleComponent(@NonNull View view) {
        this(view, view instanceof RefreshComponent ? (RefreshComponent) view : null);
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        return (obj instanceof RefreshComponent) && getView() == ((RefreshComponent) obj).getView();
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    @NonNull
    public SpinnerStyle getSpinnerStyle() {
        int i2;
        SpinnerStyle spinnerStyle = this.mSpinnerStyle;
        if (spinnerStyle != null) {
            return spinnerStyle;
        }
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            return refreshComponent.getSpinnerStyle();
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

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    @NonNull
    public View getView() {
        View view = this.mWrappedView;
        return view == null ? this : view;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    public boolean isSupportHorizontalDrag() {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        return (refreshComponent == null || refreshComponent == this || !refreshComponent.isSupportHorizontalDrag()) ? false : true;
    }

    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return 0;
        }
        return refreshComponent.onFinish(refreshLayout, z2);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onHorizontalDrag(float f2, int i2, int i3) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        refreshComponent.onHorizontalDrag(f2, i2, i3);
    }

    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i2, int i3) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.onInitialized(refreshKernel, i2, i3);
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
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        refreshComponent.onMoving(z2, f2, i2, i3, i4);
    }

    public void onReleased(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        refreshComponent.onReleased(refreshLayout, i2, i3);
    }

    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        refreshComponent.onStartAnimator(refreshLayout, i2, i3);
    }

    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        if ((this instanceof RefreshFooter) && (refreshComponent instanceof RefreshHeader)) {
            if (refreshState.isFooter) {
                refreshState = refreshState.toHeader();
            }
            if (refreshState2.isFooter) {
                refreshState2 = refreshState2.toHeader();
            }
        } else if ((this instanceof RefreshHeader) && (refreshComponent instanceof RefreshFooter)) {
            if (refreshState.isHeader) {
                refreshState = refreshState.toFooter();
            }
            if (refreshState2.isHeader) {
                refreshState2 = refreshState2.toFooter();
            }
        }
        RefreshComponent refreshComponent2 = this.mWrappedInternal;
        if (refreshComponent2 != null) {
            refreshComponent2.onStateChanged(refreshLayout, refreshState, refreshState2);
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean setNoMoreData(boolean z2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        return (refreshComponent instanceof RefreshFooter) && ((RefreshFooter) refreshComponent).setNoMoreData(z2);
    }

    public void setPrimaryColors(@ColorInt int... iArr) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return;
        }
        refreshComponent.setPrimaryColors(iArr);
    }

    public SimpleComponent(@NonNull View view, @Nullable RefreshComponent refreshComponent) {
        super(view.getContext(), null, 0);
        this.mWrappedView = view;
        this.mWrappedInternal = refreshComponent;
        if ((this instanceof RefreshFooter) && (refreshComponent instanceof RefreshHeader) && refreshComponent.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
            refreshComponent.getView().setScaleY(-1.0f);
            return;
        }
        if (this instanceof RefreshHeader) {
            RefreshComponent refreshComponent2 = this.mWrappedInternal;
            if ((refreshComponent2 instanceof RefreshFooter) && refreshComponent2.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                refreshComponent.getView().setScaleY(-1.0f);
            }
        }
    }

    public SimpleComponent(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
