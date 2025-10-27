package com.scwang.smartrefresh.layout.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/* loaded from: classes6.dex */
public class TwoLevelHeader extends InternalAbstract implements RefreshHeader {
    protected boolean mEnablePullToCloseTwoLevel;
    protected boolean mEnableRefresh;
    protected boolean mEnableTwoLevel;
    protected int mFloorDuration;
    protected float mFloorRate;
    protected int mHeaderHeight;
    protected float mMaxRate;
    protected float mPercent;
    protected RefreshInternal mRefreshHeader;
    protected RefreshKernel mRefreshKernel;
    protected float mRefreshRate;
    protected int mSpinner;
    protected OnTwoLevelListener mTwoLevelListener;

    /* renamed from: com.scwang.smartrefresh.layout.header.TwoLevelHeader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.TwoLevelReleased.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.TwoLevel.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.TwoLevelFinish.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public TwoLevelHeader(Context context) {
        this(context, null);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract
    public boolean equals(Object obj) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        return (refreshInternal != null && refreshInternal.equals(obj)) || super.equals(obj);
    }

    public TwoLevelHeader finishTwoLevel() {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.finishTwoLevel();
        }
        return this;
    }

    public void moveSpinner(int i2) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (this.mSpinner == i2 || refreshInternal == null) {
            return;
        }
        this.mSpinner = i2;
        SpinnerStyle spinnerStyle = refreshInternal.getSpinnerStyle();
        if (spinnerStyle == SpinnerStyle.Translate) {
            refreshInternal.getView().setTranslationY(i2);
        } else if (spinnerStyle.scale) {
            View view = refreshInternal.getView();
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + Math.max(0, i2));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSpinnerStyle = SpinnerStyle.MatchLayout;
        if (this.mRefreshHeader == null) {
            setRefreshHeader(new ClassicsHeader(getContext()));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSpinnerStyle = SpinnerStyle.FixedBehind;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof RefreshHeader) {
                this.mRefreshHeader = (RefreshHeader) childAt;
                this.mWrappedInternal = (RefreshInternal) childAt;
                bringChildToFront(childAt);
                return;
            }
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i2, int i3) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal == null) {
            return;
        }
        if (((i3 + i2) * 1.0f) / i2 != this.mMaxRate && this.mHeaderHeight == 0) {
            this.mHeaderHeight = i2;
            this.mRefreshHeader = null;
            refreshKernel.getRefreshLayout().setHeaderMaxDragRate(this.mMaxRate);
            this.mRefreshHeader = refreshInternal;
        }
        if (this.mRefreshKernel == null && refreshInternal.getSpinnerStyle() == SpinnerStyle.Translate && !isInEditMode()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) refreshInternal.getView().getLayoutParams();
            marginLayoutParams.topMargin -= i2;
            refreshInternal.getView().setLayoutParams(marginLayoutParams);
        }
        this.mHeaderHeight = i2;
        this.mRefreshKernel = refreshKernel;
        refreshKernel.requestFloorDuration(this.mFloorDuration);
        refreshKernel.requestNeedTouchEventFor(this, !this.mEnablePullToCloseTwoLevel);
        refreshInternal.onInitialized(refreshKernel, i2, i3);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal == null) {
            super.onMeasure(i2, i3);
        } else {
            if (View.MeasureSpec.getMode(i3) != Integer.MIN_VALUE) {
                super.onMeasure(i2, i3);
                return;
            }
            refreshInternal.getView().measure(i2, i3);
            super.setMeasuredDimension(View.resolveSize(super.getSuggestedMinimumWidth(), i2), refreshInternal.getView().getMeasuredHeight());
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onMoving(boolean z2, float f2, int i2, int i3, int i4) {
        moveSpinner(i2);
        RefreshInternal refreshInternal = this.mRefreshHeader;
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshInternal != null) {
            refreshInternal.onMoving(z2, f2, i2, i3, i4);
        }
        if (z2) {
            float f3 = this.mPercent;
            float f4 = this.mFloorRate;
            if (f3 < f4 && f2 >= f4 && this.mEnableTwoLevel) {
                refreshKernel.setState(RefreshState.ReleaseToTwoLevel);
            } else if (f3 >= f4 && f2 < this.mRefreshRate) {
                refreshKernel.setState(RefreshState.PullDownToRefresh);
            } else if (f3 >= f4 && f2 < f4 && this.mEnableRefresh) {
                refreshKernel.setState(RefreshState.ReleaseToRefresh);
            } else if (!this.mEnableRefresh && refreshKernel.getRefreshLayout().getState() != RefreshState.ReleaseToTwoLevel) {
                refreshKernel.setState(RefreshState.PullDownToRefresh);
            }
            this.mPercent = f2;
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal != null) {
            if (refreshState2 == RefreshState.ReleaseToRefresh && !this.mEnableRefresh) {
                refreshState2 = RefreshState.PullDownToRefresh;
            }
            refreshInternal.onStateChanged(refreshLayout, refreshState, refreshState2);
            int i2 = AnonymousClass1.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState2.ordinal()];
            boolean z2 = true;
            if (i2 != 1) {
                if (i2 == 3) {
                    if (refreshInternal.getView() != this) {
                        refreshInternal.getView().animate().alpha(1.0f).setDuration(this.mFloorDuration / 2);
                        return;
                    }
                    return;
                } else {
                    if (i2 == 4 && refreshInternal.getView().getAlpha() == 0.0f && refreshInternal.getView() != this) {
                        refreshInternal.getView().setAlpha(1.0f);
                        return;
                    }
                    return;
                }
            }
            if (refreshInternal.getView() != this) {
                refreshInternal.getView().animate().alpha(0.0f).setDuration(this.mFloorDuration / 2);
            }
            RefreshKernel refreshKernel = this.mRefreshKernel;
            if (refreshKernel != null) {
                OnTwoLevelListener onTwoLevelListener = this.mTwoLevelListener;
                if (onTwoLevelListener != null && !onTwoLevelListener.onTwoLevel(refreshLayout)) {
                    z2 = false;
                }
                refreshKernel.startTwoLevel(z2);
            }
        }
    }

    public TwoLevelHeader openTwoLevel(boolean z2) {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            OnTwoLevelListener onTwoLevelListener = this.mTwoLevelListener;
            refreshKernel.startTwoLevel(!z2 || onTwoLevelListener == null || onTwoLevelListener.onTwoLevel(refreshKernel.getRefreshLayout()));
        }
        return this;
    }

    public TwoLevelHeader setEnablePullToCloseTwoLevel(boolean z2) {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        this.mEnablePullToCloseTwoLevel = z2;
        if (refreshKernel != null) {
            refreshKernel.requestNeedTouchEventFor(this, !z2);
        }
        return this;
    }

    public TwoLevelHeader setEnableTwoLevel(boolean z2) {
        this.mEnableTwoLevel = z2;
        return this;
    }

    public TwoLevelHeader setFloorDuration(int i2) {
        this.mFloorDuration = i2;
        return this;
    }

    public TwoLevelHeader setFloorRate(float f2) {
        this.mFloorRate = f2;
        return this;
    }

    public TwoLevelHeader setMaxRate(float f2) {
        if (this.mMaxRate != f2) {
            this.mMaxRate = f2;
            RefreshKernel refreshKernel = this.mRefreshKernel;
            if (refreshKernel != null) {
                this.mHeaderHeight = 0;
                refreshKernel.getRefreshLayout().setHeaderMaxDragRate(this.mMaxRate);
            }
        }
        return this;
    }

    public TwoLevelHeader setOnTwoLevelListener(OnTwoLevelListener onTwoLevelListener) {
        this.mTwoLevelListener = onTwoLevelListener;
        return this;
    }

    public TwoLevelHeader setRefreshHeader(RefreshHeader refreshHeader) {
        return setRefreshHeader(refreshHeader, -1, -2);
    }

    public TwoLevelHeader setRefreshRate(float f2) {
        this.mRefreshRate = f2;
        return this;
    }

    public TwoLevelHeader(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mPercent = 0.0f;
        this.mMaxRate = 2.5f;
        this.mFloorRate = 1.9f;
        this.mRefreshRate = 1.0f;
        this.mEnableTwoLevel = true;
        this.mEnablePullToCloseTwoLevel = true;
        this.mEnableRefresh = true;
        this.mFloorDuration = 1000;
        this.mSpinnerStyle = SpinnerStyle.FixedBehind;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TwoLevelHeader);
        this.mMaxRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlMaxRage, this.mMaxRate);
        this.mFloorRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlFloorRage, this.mFloorRate);
        this.mRefreshRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlRefreshRage, this.mRefreshRate);
        this.mMaxRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlMaxRate, this.mMaxRate);
        this.mFloorRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlFloorRate, this.mFloorRate);
        this.mRefreshRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlRefreshRate, this.mRefreshRate);
        this.mFloorDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.TwoLevelHeader_srlFloorDuration, this.mFloorDuration);
        this.mEnableTwoLevel = typedArrayObtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnableTwoLevel, this.mEnableTwoLevel);
        this.mEnableRefresh = typedArrayObtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnableRefresh, this.mEnableRefresh);
        this.mEnablePullToCloseTwoLevel = typedArrayObtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnablePullToCloseTwoLevel, this.mEnablePullToCloseTwoLevel);
        typedArrayObtainStyledAttributes.recycle();
    }

    public TwoLevelHeader setRefreshHeader(RefreshHeader refreshHeader, int i2, int i3) {
        if (refreshHeader != null) {
            if (i2 == 0) {
                i2 = -1;
            }
            if (i3 == 0) {
                i3 = -2;
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i2, i3);
            ViewGroup.LayoutParams layoutParams2 = refreshHeader.getView().getLayoutParams();
            if (layoutParams2 instanceof RelativeLayout.LayoutParams) {
                layoutParams = (RelativeLayout.LayoutParams) layoutParams2;
            }
            RefreshInternal refreshInternal = this.mRefreshHeader;
            if (refreshInternal != null) {
                removeView(refreshInternal.getView());
            }
            if (refreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                addView(refreshHeader.getView(), 0, layoutParams);
            } else {
                addView(refreshHeader.getView(), getChildCount(), layoutParams);
            }
            this.mRefreshHeader = refreshHeader;
            this.mWrappedInternal = refreshHeader;
        }
        return this;
    }
}
