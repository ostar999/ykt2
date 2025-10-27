package com.scwang.smart.refresh.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshContent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.DimensionStatus;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.kernel.R;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper;

@SuppressLint({"RestrictedApi"})
/* loaded from: classes6.dex */
public class SmartRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent {
    protected static ViewGroup.MarginLayoutParams sDefaultMarginLP = new ViewGroup.MarginLayoutParams(-1, -1);
    protected static DefaultRefreshFooterCreator sFooterCreator;
    protected static DefaultRefreshHeaderCreator sHeaderCreator;
    protected static DefaultRefreshInitializer sRefreshInitializer;
    protected Runnable animationRunnable;
    protected boolean mAttachedToWindow;
    protected int mCurrentVelocity;
    protected boolean mDisableContentWhenLoading;
    protected boolean mDisableContentWhenRefresh;
    protected char mDragDirection;
    protected float mDragRate;
    protected boolean mEnableAutoLoadMore;
    protected boolean mEnableClipFooterWhenFixedBehind;
    protected boolean mEnableClipHeaderWhenFixedBehind;
    protected boolean mEnableDisallowIntercept;
    protected boolean mEnableFooterFollowWhenNoMoreData;
    protected boolean mEnableFooterTranslationContent;
    protected boolean mEnableHeaderTranslationContent;
    protected boolean mEnableLoadMore;
    protected boolean mEnableLoadMoreWhenContentNotFull;
    protected boolean mEnableNestedScrolling;
    protected boolean mEnableOverScrollBounce;
    protected boolean mEnableOverScrollDrag;
    protected boolean mEnablePreviewInEditMode;
    protected boolean mEnablePureScrollMode;
    protected boolean mEnableRefresh;
    protected boolean mEnableScrollContentWhenLoaded;
    protected boolean mEnableScrollContentWhenRefreshed;
    protected MotionEvent mFalsifyEvent;
    protected int mFixedFooterViewId;
    protected int mFixedHeaderViewId;
    protected int mFloorDuration;
    protected int mFooterBackgroundColor;
    protected int mFooterHeight;
    protected DimensionStatus mFooterHeightStatus;
    protected int mFooterInsetStart;
    protected boolean mFooterLocked;
    protected float mFooterMaxDragRate;
    protected boolean mFooterNeedTouchEventWhenLoading;
    protected boolean mFooterNoMoreData;
    protected boolean mFooterNoMoreDataEffective;
    protected int mFooterTranslationViewId;
    protected float mFooterTriggerRate;
    protected Handler mHandler;
    protected int mHeaderBackgroundColor;
    protected int mHeaderHeight;
    protected DimensionStatus mHeaderHeightStatus;
    protected int mHeaderInsetStart;
    protected float mHeaderMaxDragRate;
    protected boolean mHeaderNeedTouchEventWhenRefreshing;
    protected int mHeaderTranslationViewId;
    protected float mHeaderTriggerRate;
    protected boolean mIsBeingDragged;
    protected RefreshKernel mKernel;
    protected long mLastOpenTime;
    protected int mLastSpinner;
    protected float mLastTouchX;
    protected float mLastTouchY;
    protected OnLoadMoreListener mLoadMoreListener;
    protected boolean mManualFooterTranslationContent;
    protected boolean mManualHeaderTranslationContent;
    protected boolean mManualLoadMore;
    protected int mMaximumVelocity;
    protected int mMinimumVelocity;
    protected NestedScrollingChildHelper mNestedChild;
    protected boolean mNestedInProgress;
    protected NestedScrollingParentHelper mNestedParent;
    protected OnMultiListener mOnMultiListener;
    protected Paint mPaint;
    protected int[] mParentOffsetInWindow;
    protected int[] mPrimaryColors;
    protected int mReboundDuration;
    protected Interpolator mReboundInterpolator;
    protected RefreshContent mRefreshContent;
    protected RefreshComponent mRefreshFooter;
    protected RefreshComponent mRefreshHeader;
    protected OnRefreshListener mRefreshListener;
    protected int mScreenHeightPixels;
    protected ScrollBoundaryDecider mScrollBoundaryDecider;
    protected Scroller mScroller;
    protected int mSpinner;
    protected RefreshState mState;
    protected boolean mSuperDispatchTouchEvent;
    protected int mTotalUnconsumed;
    protected int mTouchSlop;
    protected int mTouchSpinner;
    protected float mTouchX;
    protected float mTouchY;
    protected float mTwoLevelBottomPullUpToCloseRate;
    protected VelocityTracker mVelocityTracker;
    protected boolean mVerticalPermit;
    protected RefreshState mViceState;
    protected ValueAnimator reboundAnimator;

    /* renamed from: com.scwang.smart.refresh.layout.SmartRefreshLayout$10, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullDownCanceled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullUpCanceled.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToTwoLevel.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.RefreshReleased.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* renamed from: com.scwang.smart.refresh.layout.SmartRefreshLayout$7, reason: invalid class name */
    public class AnonymousClass7 implements Runnable {
        int count = 0;
        final /* synthetic */ int val$more;
        final /* synthetic */ boolean val$noMoreData;
        final /* synthetic */ boolean val$success;

        public AnonymousClass7(int i2, boolean z2, boolean z3) {
            this.val$more = i2;
            this.val$noMoreData = z2;
            this.val$success = z3;
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x00b3  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 329
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.AnonymousClass7.run():void");
        }
    }

    public class BounceRunnable implements Runnable {
        int mSmoothDistance;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mOffset = 0.0f;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        public BounceRunnable(float f2, int i2) {
            this.mVelocity = f2;
            this.mSmoothDistance = i2;
            SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
            if (f2 > 0.0f) {
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullDownToRefresh);
            } else {
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullUpToLoad);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            if (Math.abs(smartRefreshLayout.mSpinner) < Math.abs(this.mSmoothDistance)) {
                double d3 = this.mVelocity;
                this.mFrame = this.mFrame + 1;
                this.mVelocity = (float) (d3 * Math.pow(0.949999988079071d, r2 * 2));
            } else if (this.mSmoothDistance != 0) {
                double d4 = this.mVelocity;
                this.mFrame = this.mFrame + 1;
                this.mVelocity = (float) (d4 * Math.pow(0.44999998807907104d, r2 * 2));
            } else {
                double d5 = this.mVelocity;
                this.mFrame = this.mFrame + 1;
                this.mVelocity = (float) (d5 * Math.pow(0.8500000238418579d, r2 * 2));
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float f2 = this.mVelocity * (((jCurrentAnimationTimeMillis - this.mLastTime) * 1.0f) / 1000.0f);
            if (Math.abs(f2) >= 1.0f) {
                this.mLastTime = jCurrentAnimationTimeMillis;
                float f3 = this.mOffset + f2;
                this.mOffset = f3;
                SmartRefreshLayout.this.moveSpinnerInfinitely(f3);
                SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
                return;
            }
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            RefreshState refreshState = smartRefreshLayout2.mViceState;
            boolean z2 = refreshState.isDragging;
            if (z2 && refreshState.isHeader) {
                smartRefreshLayout2.mKernel.setState(RefreshState.PullDownCanceled);
            } else if (z2 && refreshState.isFooter) {
                smartRefreshLayout2.mKernel.setState(RefreshState.PullUpCanceled);
            }
            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
            smartRefreshLayout3.animationRunnable = null;
            if (Math.abs(smartRefreshLayout3.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                int iMin = Math.min(Math.max((int) SmartUtil.px2dp(Math.abs(SmartRefreshLayout.this.mSpinner - this.mSmoothDistance)), 30), 100) * 10;
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                smartRefreshLayout4.animSpinner(this.mSmoothDistance, 0, smartRefreshLayout4.mReboundInterpolator, iMin);
            }
        }
    }

    public class FlingRunnable implements Runnable {
        int mOffset;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mDamping = 0.98f;
        long mStartTime = 0;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        public FlingRunnable(float f2) {
            this.mVelocity = f2;
            this.mOffset = SmartRefreshLayout.this.mSpinner;
        }

        @Override // java.lang.Runnable
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j2 = jCurrentAnimationTimeMillis - this.mLastTime;
            float fPow = (float) (this.mVelocity * Math.pow(this.mDamping, (jCurrentAnimationTimeMillis - this.mStartTime) / (1000.0f / this.mFrameDelay)));
            this.mVelocity = fPow;
            float f2 = fPow * ((j2 * 1.0f) / 1000.0f);
            if (Math.abs(f2) <= 1.0f) {
                SmartRefreshLayout.this.animationRunnable = null;
                return;
            }
            this.mLastTime = jCurrentAnimationTimeMillis;
            int i2 = (int) (this.mOffset + f2);
            this.mOffset = i2;
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            if (smartRefreshLayout2.mSpinner * i2 > 0) {
                smartRefreshLayout2.mKernel.moveSpinner(i2, true);
                SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
                return;
            }
            smartRefreshLayout2.animationRunnable = null;
            smartRefreshLayout2.mKernel.moveSpinner(0, true);
            SmartUtil.fling(SmartRefreshLayout.this.mRefreshContent.getScrollableView(), (int) (-this.mVelocity));
            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
            if (!smartRefreshLayout3.mFooterLocked || f2 <= 0.0f) {
                return;
            }
            smartRefreshLayout3.mFooterLocked = false;
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x004b  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0059  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Runnable start() {
            /*
                r11 = this;
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                boolean r2 = r1.isFinishing
                r3 = 0
                if (r2 == 0) goto La
                return r3
            La:
                int r2 = r0.mSpinner
                if (r2 == 0) goto La7
                boolean r1 = r1.isOpening
                if (r1 != 0) goto L26
                boolean r1 = r0.mFooterNoMoreData
                if (r1 == 0) goto L59
                boolean r1 = r0.mEnableFooterFollowWhenNoMoreData
                if (r1 == 0) goto L59
                boolean r1 = r0.mFooterNoMoreDataEffective
                if (r1 == 0) goto L59
                boolean r1 = r0.mEnableLoadMore
                boolean r0 = r0.isEnableRefreshOrLoadMore(r1)
                if (r0 == 0) goto L59
            L26:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Loading
                if (r1 == r2) goto L42
                boolean r1 = r0.mFooterNoMoreData
                if (r1 == 0) goto L4b
                boolean r1 = r0.mEnableFooterFollowWhenNoMoreData
                if (r1 == 0) goto L4b
                boolean r1 = r0.mFooterNoMoreDataEffective
                if (r1 == 0) goto L4b
                boolean r1 = r0.mEnableLoadMore
                boolean r0 = r0.isEnableRefreshOrLoadMore(r1)
                if (r0 == 0) goto L4b
            L42:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r1 = r0.mSpinner
                int r0 = r0.mFooterHeight
                int r0 = -r0
                if (r1 < r0) goto L59
            L4b:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Refreshing
                if (r1 != r2) goto La7
                int r1 = r0.mSpinner
                int r0 = r0.mHeaderHeight
                if (r1 <= r0) goto La7
            L59:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r0 = r0.mSpinner
                float r1 = r11.mVelocity
                r2 = 0
                r4 = r0
            L61:
                int r5 = r0 * r4
                if (r5 <= 0) goto La7
                double r5 = (double) r1
                float r1 = r11.mDamping
                double r7 = (double) r1
                int r2 = r2 + 1
                int r1 = r11.mFrameDelay
                int r1 = r1 * r2
                float r1 = (float) r1
                r9 = 1092616192(0x41200000, float:10.0)
                float r1 = r1 / r9
                double r9 = (double) r1
                double r7 = java.lang.Math.pow(r7, r9)
                double r5 = r5 * r7
                float r1 = (float) r5
                int r5 = r11.mFrameDelay
                float r5 = (float) r5
                r6 = 1065353216(0x3f800000, float:1.0)
                float r5 = r5 * r6
                r7 = 1148846080(0x447a0000, float:1000.0)
                float r5 = r5 / r7
                float r5 = r5 * r1
                float r7 = java.lang.Math.abs(r5)
                int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                if (r6 >= 0) goto La3
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                boolean r2 = r1.isOpening
                if (r2 == 0) goto La2
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Refreshing
                if (r1 != r2) goto L9b
                int r5 = r0.mHeaderHeight
                if (r4 > r5) goto La2
            L9b:
                if (r1 == r2) goto La7
                int r0 = r0.mFooterHeight
                int r0 = -r0
                if (r4 >= r0) goto La7
            La2:
                return r3
            La3:
                float r4 = (float) r4
                float r4 = r4 + r5
                int r4 = (int) r4
                goto L61
            La7:
                long r0 = android.view.animation.AnimationUtils.currentAnimationTimeMillis()
                r11.mStartTime = r0
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.os.Handler r0 = r0.mHandler
                int r1 = r11.mFrameDelay
                long r1 = (long) r1
                r0.postDelayed(r11, r1)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.FlingRunnable.start():java.lang.Runnable");
        }
    }

    public class RefreshKernelImpl implements RefreshKernel {
        public RefreshKernelImpl() {
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public ValueAnimator animSpinner(int i2) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            return smartRefreshLayout.animSpinner(i2, 0, smartRefreshLayout.mReboundInterpolator, smartRefreshLayout.mReboundDuration);
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel finishTwoLevel() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mState == RefreshState.TwoLevel) {
                smartRefreshLayout.mKernel.setState(RefreshState.TwoLevelFinish);
                if (SmartRefreshLayout.this.mSpinner == 0) {
                    moveSpinner(0, false);
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                } else {
                    animSpinner(0).setDuration(SmartRefreshLayout.this.mFloorDuration);
                }
            }
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        @NonNull
        public RefreshContent getRefreshContent() {
            return SmartRefreshLayout.this.mRefreshContent;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        @NonNull
        public RefreshLayout getRefreshLayout() {
            return SmartRefreshLayout.this;
        }

        /* JADX WARN: Removed duplicated region for block: B:52:0x00a3  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00b8  */
        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.scwang.smart.refresh.layout.api.RefreshKernel moveSpinner(int r19, boolean r20) {
            /*
                Method dump skipped, instructions count: 913
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.RefreshKernelImpl.moveSpinner(int, boolean):com.scwang.smart.refresh.layout.api.RefreshKernel");
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestDefaultTranslationContentFor(@NonNull RefreshComponent refreshComponent, boolean z2) {
            if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (!smartRefreshLayout.mManualHeaderTranslationContent) {
                    smartRefreshLayout.mManualHeaderTranslationContent = true;
                    smartRefreshLayout.mEnableHeaderTranslationContent = z2;
                }
            } else if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                if (!smartRefreshLayout2.mManualFooterTranslationContent) {
                    smartRefreshLayout2.mManualFooterTranslationContent = true;
                    smartRefreshLayout2.mEnableFooterTranslationContent = z2;
                }
            }
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestDrawBackgroundFor(@NonNull RefreshComponent refreshComponent, int i2) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mPaint == null && i2 != 0) {
                smartRefreshLayout.mPaint = new Paint();
            }
            if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout.this.mHeaderBackgroundColor = i2;
            } else if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout.this.mFooterBackgroundColor = i2;
            }
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestFloorBottomPullUpToCloseRate(float f2) {
            SmartRefreshLayout.this.mTwoLevelBottomPullUpToCloseRate = f2;
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestFloorDuration(int i2) {
            SmartRefreshLayout.this.mFloorDuration = i2;
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestNeedTouchEventFor(@NonNull RefreshComponent refreshComponent, boolean z2) {
            if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout.this.mHeaderNeedTouchEventWhenRefreshing = z2;
            } else if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout.this.mFooterNeedTouchEventWhenLoading = z2;
            }
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestRemeasureHeightFor(@NonNull RefreshComponent refreshComponent) {
            if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                DimensionStatus dimensionStatus = smartRefreshLayout.mHeaderHeightStatus;
                if (dimensionStatus.notified) {
                    smartRefreshLayout.mHeaderHeightStatus = dimensionStatus.unNotify();
                }
            } else if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                DimensionStatus dimensionStatus2 = smartRefreshLayout2.mFooterHeightStatus;
                if (dimensionStatus2.notified) {
                    smartRefreshLayout2.mFooterHeightStatus = dimensionStatus2.unNotify();
                }
            }
            return this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel setState(@NonNull RefreshState refreshState) {
            switch (AnonymousClass10.$SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[refreshState.ordinal()]) {
                case 1:
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    RefreshState refreshState2 = smartRefreshLayout.mState;
                    RefreshState refreshState3 = RefreshState.None;
                    if (refreshState2 != refreshState3 && smartRefreshLayout.mSpinner == 0) {
                        smartRefreshLayout.notifyStateChanged(refreshState3);
                        break;
                    } else if (smartRefreshLayout.mSpinner != 0) {
                        animSpinner(0);
                        break;
                    }
                    break;
                case 2:
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout2.mState.isOpening && smartRefreshLayout2.isEnableRefreshOrLoadMore(smartRefreshLayout2.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownToRefresh);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownToRefresh);
                        break;
                    }
                case 3:
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    if (smartRefreshLayout3.isEnableRefreshOrLoadMore(smartRefreshLayout3.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                        RefreshState refreshState4 = smartRefreshLayout4.mState;
                        if (!refreshState4.isOpening && !refreshState4.isFinishing && (!smartRefreshLayout4.mFooterNoMoreData || !smartRefreshLayout4.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout4.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout4.notifyStateChanged(RefreshState.PullUpToLoad);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpToLoad);
                    break;
                case 4:
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout5.mState.isOpening && smartRefreshLayout5.isEnableRefreshOrLoadMore(smartRefreshLayout5.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                        setState(RefreshState.None);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownCanceled);
                        break;
                    }
                    break;
                case 5:
                    SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                    if (smartRefreshLayout6.isEnableRefreshOrLoadMore(smartRefreshLayout6.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                        if (!smartRefreshLayout7.mState.isOpening && (!smartRefreshLayout7.mFooterNoMoreData || !smartRefreshLayout7.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout7.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout7.notifyStateChanged(RefreshState.PullUpCanceled);
                            setState(RefreshState.None);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpCanceled);
                    break;
                case 6:
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout8.mState.isOpening && smartRefreshLayout8.isEnableRefreshOrLoadMore(smartRefreshLayout8.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToRefresh);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToRefresh);
                        break;
                    }
                    break;
                case 7:
                    SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                    if (smartRefreshLayout9.isEnableRefreshOrLoadMore(smartRefreshLayout9.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                        RefreshState refreshState5 = smartRefreshLayout10.mState;
                        if (!refreshState5.isOpening && !refreshState5.isFinishing && (!smartRefreshLayout10.mFooterNoMoreData || !smartRefreshLayout10.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout10.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout10.notifyStateChanged(RefreshState.ReleaseToLoad);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToLoad);
                    break;
                case 8:
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout11.mState.isOpening && smartRefreshLayout11.isEnableRefreshOrLoadMore(smartRefreshLayout11.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToTwoLevel);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToTwoLevel);
                        break;
                    }
                    break;
                case 9:
                    SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout12.mState.isOpening && smartRefreshLayout12.isEnableRefreshOrLoadMore(smartRefreshLayout12.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshReleased);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.RefreshReleased);
                        break;
                    }
                case 10:
                    SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout13.mState.isOpening && smartRefreshLayout13.isEnableRefreshOrLoadMore(smartRefreshLayout13.mEnableLoadMore)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadReleased);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.LoadReleased);
                        break;
                    }
                    break;
                case 11:
                    SmartRefreshLayout.this.setStateRefreshing(true);
                    break;
                case 12:
                    SmartRefreshLayout.this.setStateLoading(true);
                    break;
                default:
                    SmartRefreshLayout.this.notifyStateChanged(refreshState);
                    break;
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.scwang.smart.refresh.layout.api.RefreshKernel startTwoLevel(boolean r4) {
            /*
                r3 = this;
                if (r4 == 0) goto L28
                com.scwang.smart.refresh.layout.SmartRefreshLayout$RefreshKernelImpl$1 r4 = new com.scwang.smart.refresh.layout.SmartRefreshLayout$RefreshKernelImpl$1
                r4.<init>()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r0 = r0.getMeasuredHeight()
                android.animation.ValueAnimator r0 = r3.animSpinner(r0)
                if (r0 == 0) goto L23
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.animation.ValueAnimator r2 = r1.reboundAnimator
                if (r0 != r2) goto L23
                int r1 = r1.mFloorDuration
                long r1 = (long) r1
                r0.setDuration(r1)
                r0.addListener(r4)
                goto L36
            L23:
                r0 = 0
                r4.onAnimationEnd(r0)
                goto L36
            L28:
                r4 = 0
                android.animation.ValueAnimator r4 = r3.animSpinner(r4)
                if (r4 != 0) goto L36
                com.scwang.smart.refresh.layout.SmartRefreshLayout r4 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r0 = com.scwang.smart.refresh.layout.constant.RefreshState.None
                r4.notifyStateChanged(r0)
            L36:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.RefreshKernelImpl.startTwoLevel(boolean):com.scwang.smart.refresh.layout.api.RefreshKernel");
        }
    }

    public SmartRefreshLayout(Context context) {
        this(context, null);
    }

    public static void setDefaultRefreshFooterCreator(@NonNull DefaultRefreshFooterCreator defaultRefreshFooterCreator) {
        sFooterCreator = defaultRefreshFooterCreator;
    }

    public static void setDefaultRefreshHeaderCreator(@NonNull DefaultRefreshHeaderCreator defaultRefreshHeaderCreator) {
        sHeaderCreator = defaultRefreshHeaderCreator;
    }

    public static void setDefaultRefreshInitializer(@NonNull DefaultRefreshInitializer defaultRefreshInitializer) {
        sRefreshInitializer = defaultRefreshInitializer;
    }

    public ValueAnimator animSpinner(int i2, int i3, Interpolator interpolator, int i4) {
        if (this.mSpinner == i2) {
            return null;
        }
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.setDuration(0L);
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
        this.animationRunnable = null;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mSpinner, i2);
        this.reboundAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(i4);
        this.reboundAnimator.setInterpolator(interpolator);
        this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                RefreshState refreshState;
                RefreshState refreshState2;
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    smartRefreshLayout.reboundAnimator = null;
                    if (smartRefreshLayout.mSpinner == 0 && (refreshState = smartRefreshLayout.mState) != (refreshState2 = RefreshState.None) && !refreshState.isOpening && !refreshState.isDragging) {
                        smartRefreshLayout.notifyStateChanged(refreshState2);
                        return;
                    }
                    RefreshState refreshState3 = smartRefreshLayout.mState;
                    if (refreshState3 != smartRefreshLayout.mViceState) {
                        smartRefreshLayout.setViceState(refreshState3);
                    }
                }
            }
        });
        this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SmartRefreshLayout.this.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
            }
        });
        this.reboundAnimator.setStartDelay(i3);
        this.reboundAnimator.start();
        return this.reboundAnimator;
    }

    public void animSpinnerBounce(float f2) {
        RefreshState refreshState;
        if (this.reboundAnimator == null) {
            if (f2 > 0.0f && ((refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.TwoLevel)) {
                this.animationRunnable = new BounceRunnable(f2, this.mHeaderHeight);
                return;
            }
            if (f2 < 0.0f && (this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && this.mState != RefreshState.Refreshing)))) {
                this.animationRunnable = new BounceRunnable(f2, -this.mFooterHeight);
            } else if (this.mSpinner == 0 && this.mEnableOverScrollBounce) {
                this.animationRunnable = new BounceRunnable(f2, 0);
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoLoadMore() {
        return autoLoadMore(0, this.mReboundDuration, (this.mFooterMaxDragRate + this.mFooterTriggerRate) / 2.0f, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoLoadMoreAnimationOnly() {
        return autoLoadMore(0, this.mReboundDuration, (this.mFooterMaxDragRate + this.mFooterTriggerRate) / 2.0f, true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoRefresh() {
        return autoRefresh(this.mAttachedToWindow ? 0 : 400, this.mReboundDuration, (this.mHeaderMaxDragRate + this.mHeaderTriggerRate) / 2.0f, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoRefreshAnimationOnly() {
        return autoRefresh(this.mAttachedToWindow ? 0 : 400, this.mReboundDuration, (this.mHeaderMaxDragRate + this.mHeaderTriggerRate) / 2.0f, true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout closeHeaderOrFooter() {
        RefreshState refreshState;
        RefreshState refreshState2 = this.mState;
        RefreshState refreshState3 = RefreshState.None;
        if (refreshState2 == refreshState3 && ((refreshState = this.mViceState) == RefreshState.Refreshing || refreshState == RefreshState.Loading)) {
            this.mViceState = refreshState3;
        }
        if (refreshState2 == RefreshState.Refreshing) {
            finishRefresh();
        } else if (refreshState2 == RefreshState.Loading) {
            finishLoadMore();
        } else if (this.mKernel.animSpinner(0) == null) {
            notifyStateChanged(refreshState3);
        } else if (this.mState.isHeader) {
            notifyStateChanged(RefreshState.PullDownCanceled);
        } else {
            notifyStateChanged(RefreshState.PullUpCanceled);
        }
        return this;
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mScroller.getCurrY();
        if (this.mScroller.computeScrollOffset()) {
            int finalY = this.mScroller.getFinalY();
            if ((finalY >= 0 || !((this.mEnableRefresh || this.mEnableOverScrollDrag) && this.mRefreshContent.canRefresh())) && (finalY <= 0 || !((this.mEnableLoadMore || this.mEnableOverScrollDrag) && this.mRefreshContent.canLoadMore()))) {
                this.mVerticalPermit = true;
                invalidate();
            } else {
                if (this.mVerticalPermit) {
                    animSpinnerBounce(finalY > 0 ? -this.mScroller.getCurrVelocity() : this.mScroller.getCurrVelocity());
                }
                this.mScroller.forceFinished(true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x00dc, code lost:
    
        if (r2.isFooter == false) goto L78;
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00cc  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r24) {
        /*
            Method dump skipped, instructions count: 867
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j2) {
        Paint paint;
        Paint paint2;
        RefreshContent refreshContent = this.mRefreshContent;
        View view2 = refreshContent != null ? refreshContent.getView() : null;
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null && refreshComponent.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableRefresh) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int iMax = Math.max(view2.getTop() + view2.getPaddingTop() + this.mSpinner, view.getTop());
                int i2 = this.mHeaderBackgroundColor;
                if (i2 != 0 && (paint2 = this.mPaint) != null) {
                    paint2.setColor(i2);
                    if (this.mRefreshHeader.getSpinnerStyle().scale) {
                        iMax = view.getBottom();
                    } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        iMax = view.getBottom() + this.mSpinner;
                    }
                    canvas.drawRect(0.0f, view.getTop(), getWidth(), iMax, this.mPaint);
                }
                if ((this.mEnableClipHeaderWhenFixedBehind && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) || this.mRefreshHeader.getSpinnerStyle().scale) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), iMax);
                    boolean zDrawChild = super.drawChild(canvas, view, j2);
                    canvas.restore();
                    return zDrawChild;
                }
            }
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null && refreshComponent2.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableLoadMore) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int iMin = Math.min((view2.getBottom() - view2.getPaddingBottom()) + this.mSpinner, view.getBottom());
                int i3 = this.mFooterBackgroundColor;
                if (i3 != 0 && (paint = this.mPaint) != null) {
                    paint.setColor(i3);
                    if (this.mRefreshFooter.getSpinnerStyle().scale) {
                        iMin = view.getTop();
                    } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                        iMin = view.getTop() + this.mSpinner;
                    }
                    canvas.drawRect(0.0f, iMin, getWidth(), view.getBottom(), this.mPaint);
                }
                if ((this.mEnableClipFooterWhenFixedBehind && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) || this.mRefreshFooter.getSpinnerStyle().scale) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), iMin, view.getRight(), view.getBottom());
                    boolean zDrawChild2 = super.drawChild(canvas, view, j2);
                    canvas.restore();
                    return zDrawChild2;
                }
            }
        }
        return super.drawChild(canvas, view, j2);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMore() {
        return finishLoadMore(true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMoreWithNoMoreData() {
        return finishLoadMore(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefresh() {
        return finishRefresh(true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefreshWithNoMoreData() {
        return finishRefresh(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, Boolean.TRUE);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    @NonNull
    public ViewGroup getLayout() {
        return this;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mNestedParent.getNestedScrollAxes();
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    @Nullable
    public RefreshFooter getRefreshFooter() {
        RefreshComponent refreshComponent = this.mRefreshFooter;
        if (refreshComponent instanceof RefreshFooter) {
            return (RefreshFooter) refreshComponent;
        }
        return null;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    @Nullable
    public RefreshHeader getRefreshHeader() {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent instanceof RefreshHeader) {
            return (RefreshHeader) refreshComponent;
        }
        return null;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    @NonNull
    public RefreshState getState() {
        return this.mState;
    }

    public boolean interceptAnimatorByAction(int i2) {
        if (i2 == 0) {
            if (this.reboundAnimator != null) {
                RefreshState refreshState = this.mState;
                if (refreshState.isFinishing || refreshState == RefreshState.TwoLevelReleased || refreshState == RefreshState.RefreshReleased || refreshState == RefreshState.LoadReleased) {
                    return true;
                }
                if (refreshState == RefreshState.PullDownCanceled) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else if (refreshState == RefreshState.PullUpCanceled) {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
                this.reboundAnimator.setDuration(0L);
                this.reboundAnimator.cancel();
                this.reboundAnimator = null;
            }
            this.animationRunnable = null;
        }
        return this.reboundAnimator != null;
    }

    public boolean isEnableRefreshOrLoadMore(boolean z2) {
        return z2 && !this.mEnablePureScrollMode;
    }

    public boolean isEnableTranslationContent(boolean z2, @Nullable RefreshComponent refreshComponent) {
        return z2 || this.mEnablePureScrollMode || refreshComponent == null || refreshComponent.getSpinnerStyle() == SpinnerStyle.FixedBehind;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean isLoading() {
        return this.mState == RefreshState.Loading;
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.mEnableNestedScrolling && (this.mEnableOverScrollDrag || this.mEnableRefresh || this.mEnableLoadMore);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean isRefreshing() {
        return this.mState == RefreshState.Refreshing;
    }

    public void moveSpinnerInfinitely(float f2) {
        RefreshState refreshState;
        float f3 = (!this.mNestedInProgress || this.mEnableLoadMoreWhenContentNotFull || f2 >= 0.0f || this.mRefreshContent.canLoadMore()) ? f2 : 0.0f;
        if (f3 > this.mScreenHeightPixels * 5 && getTag() == null) {
            int i2 = R.id.srl_tag;
            if (getTag(i2) == null) {
                float f4 = this.mLastTouchY;
                int i3 = this.mScreenHeightPixels;
                if (f4 < i3 / 6.0f && this.mLastTouchX < i3 / 16.0f) {
                    Toast.makeText(getContext(), "你这么死拉，臣妾做不到啊！", 0).show();
                    setTag(i2, "你这么死拉，臣妾做不到啊！");
                }
            }
        }
        RefreshState refreshState2 = this.mState;
        if (refreshState2 == RefreshState.TwoLevel && f3 > 0.0f) {
            this.mKernel.moveSpinner(Math.min((int) f3, getMeasuredHeight()), true);
        } else if (refreshState2 == RefreshState.Refreshing && f3 >= 0.0f) {
            int i4 = this.mHeaderHeight;
            if (f3 < i4) {
                this.mKernel.moveSpinner((int) f3, true);
            } else {
                float f5 = this.mHeaderMaxDragRate;
                if (f5 < 10.0f) {
                    f5 *= i4;
                }
                double d3 = f5 - i4;
                int iMax = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i5 = this.mHeaderHeight;
                double d4 = iMax - i5;
                double dMax = Math.max(0.0f, (f3 - i5) * this.mDragRate);
                double d5 = -dMax;
                if (d4 == 0.0d) {
                    d4 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) Math.min(d3 * (1.0d - Math.pow(100.0d, d5 / d4)), dMax)) + this.mHeaderHeight, true);
            }
        } else if (f3 < 0.0f && (refreshState2 == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) {
            int i6 = this.mFooterHeight;
            if (f3 > (-i6)) {
                this.mKernel.moveSpinner((int) f3, true);
            } else {
                float f6 = this.mFooterMaxDragRate;
                if (f6 < 10.0f) {
                    f6 *= i6;
                }
                double d6 = f6 - i6;
                int iMax2 = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i7 = this.mFooterHeight;
                double d7 = iMax2 - i7;
                double d8 = -Math.min(0.0f, (i7 + f3) * this.mDragRate);
                double d9 = -d8;
                if (d7 == 0.0d) {
                    d7 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) (-Math.min(d6 * (1.0d - Math.pow(100.0d, d9 / d7)), d8))) - this.mFooterHeight, true);
            }
        } else if (f3 >= 0.0f) {
            float f7 = this.mHeaderMaxDragRate;
            double d10 = f7 < 10.0f ? this.mHeaderHeight * f7 : f7;
            double dMax2 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double dMax3 = Math.max(0.0f, this.mDragRate * f3);
            double d11 = -dMax3;
            if (dMax2 == 0.0d) {
                dMax2 = 1.0d;
            }
            this.mKernel.moveSpinner((int) Math.min(d10 * (1.0d - Math.pow(100.0d, d11 / dMax2)), dMax3), true);
        } else {
            float f8 = this.mFooterMaxDragRate;
            double d12 = f8 < 10.0f ? this.mFooterHeight * f8 : f8;
            double dMax4 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double d13 = -Math.min(0.0f, this.mDragRate * f3);
            this.mKernel.moveSpinner((int) (-Math.min(d12 * (1.0d - Math.pow(100.0d, (-d13) / (dMax4 == 0.0d ? 1.0d : dMax4))), d13)), true);
        }
        if (!this.mEnableAutoLoadMore || this.mFooterNoMoreData || !isEnableRefreshOrLoadMore(this.mEnableLoadMore) || f3 >= 0.0f || (refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.Loading || refreshState == RefreshState.LoadFinish) {
            return;
        }
        if (this.mDisableContentWhenLoading) {
            this.animationRunnable = null;
            this.mKernel.animSpinner(-this.mFooterHeight);
        }
        setStateDirectLoading(false);
        this.mHandler.postDelayed(new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.5
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                OnLoadMoreListener onLoadMoreListener = smartRefreshLayout.mLoadMoreListener;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore(smartRefreshLayout);
                } else if (smartRefreshLayout.mOnMultiListener == null) {
                    smartRefreshLayout.finishLoadMore(2000);
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                OnMultiListener onMultiListener = smartRefreshLayout2.mOnMultiListener;
                if (onMultiListener != null) {
                    onMultiListener.onLoadMore(smartRefreshLayout2);
                }
            }
        }, this.mReboundDuration);
    }

    public void notifyStateChanged(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2 == refreshState) {
            if (this.mViceState != refreshState2) {
                this.mViceState = refreshState2;
                return;
            }
            return;
        }
        this.mState = refreshState;
        this.mViceState = refreshState;
        RefreshComponent refreshComponent = this.mRefreshHeader;
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        OnMultiListener onMultiListener = this.mOnMultiListener;
        if (refreshComponent != null) {
            refreshComponent.onStateChanged(this, refreshState2, refreshState);
        }
        if (refreshComponent2 != null) {
            refreshComponent2.onStateChanged(this, refreshState2, refreshState);
        }
        if (onMultiListener != null) {
            onMultiListener.onStateChanged(this, refreshState2, refreshState);
        }
        if (refreshState == RefreshState.LoadFinish) {
            this.mFooterLocked = false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        RefreshComponent refreshComponent;
        DefaultRefreshHeaderCreator defaultRefreshHeaderCreator;
        super.onAttachedToWindow();
        boolean z2 = true;
        this.mAttachedToWindow = true;
        if (!isInEditMode()) {
            if (this.mRefreshHeader == null && (defaultRefreshHeaderCreator = sHeaderCreator) != null) {
                RefreshHeader refreshHeaderCreateRefreshHeader = defaultRefreshHeaderCreator.createRefreshHeader(getContext(), this);
                if (refreshHeaderCreateRefreshHeader == null) {
                    throw new RuntimeException("DefaultRefreshHeaderCreator can not return null");
                }
                setRefreshHeader(refreshHeaderCreateRefreshHeader);
            }
            if (this.mRefreshFooter == null) {
                DefaultRefreshFooterCreator defaultRefreshFooterCreator = sFooterCreator;
                if (defaultRefreshFooterCreator != null) {
                    RefreshFooter refreshFooterCreateRefreshFooter = defaultRefreshFooterCreator.createRefreshFooter(getContext(), this);
                    if (refreshFooterCreateRefreshFooter == null) {
                        throw new RuntimeException("DefaultRefreshFooterCreator can not return null");
                    }
                    setRefreshFooter(refreshFooterCreateRefreshFooter);
                }
            } else {
                if (!this.mEnableLoadMore && this.mManualLoadMore) {
                    z2 = false;
                }
                this.mEnableLoadMore = z2;
            }
            if (this.mRefreshContent == null) {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    RefreshComponent refreshComponent2 = this.mRefreshHeader;
                    if ((refreshComponent2 == null || childAt != refreshComponent2.getView()) && ((refreshComponent = this.mRefreshFooter) == null || childAt != refreshComponent.getView())) {
                        this.mRefreshContent = new RefreshContentWrapper(childAt);
                    }
                }
            }
            if (this.mRefreshContent == null) {
                int iDp2px = SmartUtil.dp2px(20.0f);
                TextView textView = new TextView(getContext());
                textView.setTextColor(-39424);
                textView.setGravity(17);
                textView.setTextSize(20.0f);
                textView.setText(R.string.srl_content_empty);
                super.addView(textView, 0, new LayoutParams(-1, -1));
                RefreshContentWrapper refreshContentWrapper = new RefreshContentWrapper(textView);
                this.mRefreshContent = refreshContentWrapper;
                refreshContentWrapper.getView().setPadding(iDp2px, iDp2px, iDp2px, iDp2px);
            }
            View viewFindViewById = findViewById(this.mFixedHeaderViewId);
            View viewFindViewById2 = findViewById(this.mFixedFooterViewId);
            this.mRefreshContent.setScrollBoundaryDecider(this.mScrollBoundaryDecider);
            this.mRefreshContent.setEnableLoadMoreWhenContentNotFull(this.mEnableLoadMoreWhenContentNotFull);
            this.mRefreshContent.setUpComponent(this.mKernel, viewFindViewById, viewFindViewById2);
            if (this.mSpinner != 0) {
                notifyStateChanged(RefreshState.None);
                RefreshContent refreshContent = this.mRefreshContent;
                this.mSpinner = 0;
                refreshContent.moveSpinner(0, this.mHeaderTranslationViewId, this.mFooterTranslationViewId);
            }
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null) {
            RefreshComponent refreshComponent3 = this.mRefreshHeader;
            if (refreshComponent3 != null) {
                refreshComponent3.setPrimaryColors(iArr);
            }
            RefreshComponent refreshComponent4 = this.mRefreshFooter;
            if (refreshComponent4 != null) {
                refreshComponent4.setPrimaryColors(this.mPrimaryColors);
            }
        }
        RefreshContent refreshContent2 = this.mRefreshContent;
        if (refreshContent2 != null) {
            super.bringChildToFront(refreshContent2.getView());
        }
        RefreshComponent refreshComponent5 = this.mRefreshHeader;
        if (refreshComponent5 != null && refreshComponent5.getSpinnerStyle().front) {
            super.bringChildToFront(this.mRefreshHeader.getView());
        }
        RefreshComponent refreshComponent6 = this.mRefreshFooter;
        if (refreshComponent6 == null || !refreshComponent6.getSpinnerStyle().front) {
            return;
        }
        super.bringChildToFront(this.mRefreshFooter.getView());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mManualLoadMore = true;
        this.animationRunnable = null;
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.reboundAnimator.removeAllUpdateListeners();
            this.reboundAnimator.setDuration(0L);
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null && this.mState == RefreshState.Refreshing) {
            refreshComponent.onFinish(this, false);
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null && this.mState == RefreshState.Loading) {
            refreshComponent2.onFinish(this, false);
        }
        if (this.mSpinner != 0) {
            this.mKernel.moveSpinner(0, true);
        }
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.None;
        if (refreshState != refreshState2) {
            notifyStateChanged(refreshState2);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mFooterLocked = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onFinishInflate() {
        /*
            r11 = this;
            super.onFinishInflate()
            int r0 = super.getChildCount()
            r1 = 3
            if (r0 > r1) goto L9e
            r2 = -1
            r3 = 0
            r5 = r2
            r4 = r3
            r6 = r4
        Lf:
            r7 = 2
            r8 = 1
            if (r4 >= r0) goto L33
            android.view.View r9 = super.getChildAt(r4)
            boolean r10 = com.scwang.smart.refresh.layout.util.SmartUtil.isContentView(r9)
            if (r10 == 0) goto L24
            if (r6 < r7) goto L21
            if (r4 != r8) goto L24
        L21:
            r5 = r4
            r6 = r7
            goto L30
        L24:
            boolean r7 = r9 instanceof com.scwang.smart.refresh.layout.api.RefreshComponent
            if (r7 != 0) goto L30
            if (r6 >= r8) goto L30
            if (r4 <= 0) goto L2e
            r6 = r8
            goto L2f
        L2e:
            r6 = r3
        L2f:
            r5 = r4
        L30:
            int r4 = r4 + 1
            goto Lf
        L33:
            if (r5 < 0) goto L4d
            com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper r4 = new com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper
            android.view.View r6 = super.getChildAt(r5)
            r4.<init>(r6)
            r11.mRefreshContent = r4
            if (r5 != r8) goto L48
            if (r0 != r1) goto L45
            goto L46
        L45:
            r7 = r2
        L46:
            r1 = r3
            goto L4f
        L48:
            if (r0 != r7) goto L4d
            r1 = r2
            r7 = r8
            goto L4f
        L4d:
            r1 = r2
            r7 = r1
        L4f:
            r4 = r3
        L50:
            if (r4 >= r0) goto L9d
            android.view.View r5 = super.getChildAt(r4)
            if (r4 == r1) goto L8b
            if (r4 == r7) goto L65
            if (r1 != r2) goto L65
            com.scwang.smart.refresh.layout.api.RefreshComponent r6 = r11.mRefreshHeader
            if (r6 != 0) goto L65
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshHeader
            if (r6 == 0) goto L65
            goto L8b
        L65:
            if (r4 == r7) goto L6d
            if (r7 != r2) goto L9a
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshFooter
            if (r6 == 0) goto L9a
        L6d:
            boolean r6 = r11.mEnableLoadMore
            if (r6 != 0) goto L78
            boolean r6 = r11.mManualLoadMore
            if (r6 != 0) goto L76
            goto L78
        L76:
            r6 = r3
            goto L79
        L78:
            r6 = r8
        L79:
            r11.mEnableLoadMore = r6
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshFooter
            if (r6 == 0) goto L82
            com.scwang.smart.refresh.layout.api.RefreshFooter r5 = (com.scwang.smart.refresh.layout.api.RefreshFooter) r5
            goto L88
        L82:
            com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper r6 = new com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper
            r6.<init>(r5)
            r5 = r6
        L88:
            r11.mRefreshFooter = r5
            goto L9a
        L8b:
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshHeader
            if (r6 == 0) goto L92
            com.scwang.smart.refresh.layout.api.RefreshHeader r5 = (com.scwang.smart.refresh.layout.api.RefreshHeader) r5
            goto L98
        L92:
            com.scwang.smart.refresh.layout.wrapper.RefreshHeaderWrapper r6 = new com.scwang.smart.refresh.layout.wrapper.RefreshHeaderWrapper
            r6.<init>(r5)
            r5 = r6
        L98:
            r11.mRefreshHeader = r5
        L9a:
            int r4 = r4 + 1
            goto L50
        L9d:
            return
        L9e:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "最多只支持3个子View，Most only support three sub view"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.onFinishInflate():void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int iMax;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        int childCount = super.getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = super.getChildAt(i6);
            if (childAt.getVisibility() != 8 && !"GONE".equals(childAt.getTag(R.id.srl_tag))) {
                RefreshContent refreshContent = this.mRefreshContent;
                if (refreshContent != null && refreshContent.getView() == childAt) {
                    boolean z3 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh) && this.mRefreshHeader != null;
                    View view = this.mRefreshContent.getView();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : sDefaultMarginLP;
                    int i7 = marginLayoutParams.leftMargin + paddingLeft;
                    int i8 = marginLayoutParams.topMargin + paddingTop;
                    int measuredWidth = view.getMeasuredWidth() + i7;
                    int measuredHeight = view.getMeasuredHeight() + i8;
                    if (z3 && isEnableTranslationContent(this.mEnableHeaderTranslationContent, this.mRefreshHeader)) {
                        int i9 = this.mHeaderHeight;
                        i8 += i9;
                        measuredHeight += i9;
                    }
                    view.layout(i7, i8, measuredWidth, measuredHeight);
                }
                RefreshComponent refreshComponent = this.mRefreshHeader;
                if (refreshComponent != null && refreshComponent.getView() == childAt) {
                    boolean z4 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh);
                    View view2 = this.mRefreshHeader.getView();
                    ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : sDefaultMarginLP;
                    int i10 = marginLayoutParams2.leftMargin;
                    int i11 = marginLayoutParams2.topMargin + this.mHeaderInsetStart;
                    int measuredWidth2 = view2.getMeasuredWidth() + i10;
                    int measuredHeight2 = view2.getMeasuredHeight() + i11;
                    if (!z4 && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        int i12 = this.mHeaderHeight;
                        i11 -= i12;
                        measuredHeight2 -= i12;
                    }
                    view2.layout(i10, i11, measuredWidth2, measuredHeight2);
                }
                RefreshComponent refreshComponent2 = this.mRefreshFooter;
                if (refreshComponent2 != null && refreshComponent2.getView() == childAt) {
                    boolean z5 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableLoadMore);
                    View view3 = this.mRefreshFooter.getView();
                    ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : sDefaultMarginLP;
                    SpinnerStyle spinnerStyle = this.mRefreshFooter.getSpinnerStyle();
                    int i13 = marginLayoutParams3.leftMargin;
                    int measuredHeight3 = (marginLayoutParams3.topMargin + getMeasuredHeight()) - this.mFooterInsetStart;
                    if (this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && this.mEnableFooterFollowWhenNoMoreData && this.mRefreshContent != null && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) {
                        View view4 = this.mRefreshContent.getView();
                        ViewGroup.LayoutParams layoutParams4 = view4.getLayoutParams();
                        measuredHeight3 = view4.getMeasuredHeight() + paddingTop + paddingTop + (layoutParams4 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin : 0);
                    }
                    if (spinnerStyle == SpinnerStyle.MatchLayout) {
                        measuredHeight3 = marginLayoutParams3.topMargin - this.mFooterInsetStart;
                    } else {
                        if (z5 || spinnerStyle == SpinnerStyle.FixedFront || spinnerStyle == SpinnerStyle.FixedBehind) {
                            iMax = this.mFooterHeight;
                        } else if (spinnerStyle.scale && this.mSpinner < 0) {
                            iMax = Math.max(isEnableRefreshOrLoadMore(this.mEnableLoadMore) ? -this.mSpinner : 0, 0);
                        }
                        measuredHeight3 -= iMax;
                    }
                    view3.layout(i13, measuredHeight3, view3.getMeasuredWidth() + i13, view3.getMeasuredHeight() + measuredHeight3);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x025c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f2, float f3, boolean z2) {
        return this.mNestedChild.dispatchNestedFling(f2, f3, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f2, float f3) {
        return (this.mFooterLocked && f3 > 0.0f) || startFlingIfNeed(-f3) || this.mNestedChild.dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(@NonNull View view, int i2, int i3, @NonNull int[] iArr) {
        int i4 = this.mTotalUnconsumed;
        int i5 = 0;
        if (i3 * i4 > 0) {
            if (Math.abs(i3) > Math.abs(this.mTotalUnconsumed)) {
                int i6 = this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0;
                i5 = i6;
            } else {
                this.mTotalUnconsumed -= i3;
                i5 = i3;
            }
            moveSpinnerInfinitely(this.mTotalUnconsumed);
        } else if (i3 > 0 && this.mFooterLocked) {
            int i7 = i4 - i3;
            this.mTotalUnconsumed = i7;
            moveSpinnerInfinitely(i7);
            i5 = i3;
        }
        this.mNestedChild.dispatchNestedPreScroll(i2, i3 - i5, iArr, null);
        iArr[1] = iArr[1] + i5;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5) {
        ScrollBoundaryDecider scrollBoundaryDecider;
        ViewParent parent;
        ScrollBoundaryDecider scrollBoundaryDecider2;
        boolean zDispatchNestedScroll = this.mNestedChild.dispatchNestedScroll(i2, i3, i4, i5, this.mParentOffsetInWindow);
        int i6 = i5 + this.mParentOffsetInWindow[1];
        if ((i6 < 0 && ((this.mEnableRefresh || this.mEnableOverScrollDrag) && (this.mTotalUnconsumed != 0 || (scrollBoundaryDecider2 = this.mScrollBoundaryDecider) == null || scrollBoundaryDecider2.canRefresh(this.mRefreshContent.getView())))) || (i6 > 0 && ((this.mEnableLoadMore || this.mEnableOverScrollDrag) && (this.mTotalUnconsumed != 0 || (scrollBoundaryDecider = this.mScrollBoundaryDecider) == null || scrollBoundaryDecider.canLoadMore(this.mRefreshContent.getView()))))) {
            RefreshState refreshState = this.mViceState;
            if (refreshState == RefreshState.None || refreshState.isOpening) {
                this.mKernel.setState(i6 > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
                if (!zDispatchNestedScroll && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            int i7 = this.mTotalUnconsumed - i6;
            this.mTotalUnconsumed = i7;
            moveSpinnerInfinitely(i7);
        }
        if (!this.mFooterLocked || i3 >= 0) {
            return;
        }
        this.mFooterLocked = false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2) {
        this.mNestedParent.onNestedScrollAccepted(view, view2, i2);
        this.mNestedChild.startNestedScroll(i2 & 2);
        this.mTotalUnconsumed = this.mSpinner;
        this.mNestedInProgress = true;
        interceptAnimatorByAction(0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2) {
        return (isEnabled() && isNestedScrollingEnabled() && (i2 & 2) != 0) && (this.mEnableOverScrollDrag || this.mEnableRefresh || this.mEnableLoadMore);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(@NonNull View view) {
        this.mNestedParent.onStopNestedScroll(view);
        this.mNestedInProgress = false;
        this.mTotalUnconsumed = 0;
        overSpinner();
        this.mNestedChild.stopNestedScroll();
    }

    public void overSpinner() {
        RefreshState refreshState = this.mState;
        if (refreshState == RefreshState.TwoLevel) {
            if (this.mCurrentVelocity <= -1000 || this.mSpinner <= getHeight() / 2) {
                if (this.mIsBeingDragged) {
                    this.mKernel.finishTwoLevel();
                    return;
                }
                return;
            } else {
                ValueAnimator valueAnimatorAnimSpinner = this.mKernel.animSpinner(getHeight());
                if (valueAnimatorAnimSpinner != null) {
                    valueAnimatorAnimSpinner.setDuration(this.mFloorDuration);
                    return;
                }
                return;
            }
        }
        RefreshState refreshState2 = RefreshState.Loading;
        if (refreshState == refreshState2 || (this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && this.mSpinner < 0 && isEnableRefreshOrLoadMore(this.mEnableLoadMore))) {
            int i2 = this.mSpinner;
            int i3 = this.mFooterHeight;
            if (i2 < (-i3)) {
                this.mKernel.animSpinner(-i3);
                return;
            } else {
                if (i2 > 0) {
                    this.mKernel.animSpinner(0);
                    return;
                }
                return;
            }
        }
        RefreshState refreshState3 = this.mState;
        RefreshState refreshState4 = RefreshState.Refreshing;
        if (refreshState3 == refreshState4) {
            int i4 = this.mSpinner;
            int i5 = this.mHeaderHeight;
            if (i4 > i5) {
                this.mKernel.animSpinner(i5);
                return;
            } else {
                if (i4 < 0) {
                    this.mKernel.animSpinner(0);
                    return;
                }
                return;
            }
        }
        if (refreshState3 == RefreshState.PullDownToRefresh) {
            this.mKernel.setState(RefreshState.PullDownCanceled);
            return;
        }
        if (refreshState3 == RefreshState.PullUpToLoad) {
            this.mKernel.setState(RefreshState.PullUpCanceled);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToRefresh) {
            this.mKernel.setState(refreshState4);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToLoad) {
            this.mKernel.setState(refreshState2);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToTwoLevel) {
            this.mKernel.setState(RefreshState.TwoLevelReleased);
            return;
        }
        if (refreshState3 == RefreshState.RefreshReleased) {
            if (this.reboundAnimator == null) {
                this.mKernel.animSpinner(this.mHeaderHeight);
            }
        } else if (refreshState3 == RefreshState.LoadReleased) {
            if (this.reboundAnimator == null) {
                this.mKernel.animSpinner(-this.mFooterHeight);
            }
        } else {
            if (refreshState3 == RefreshState.LoadFinish || this.mSpinner == 0) {
                return;
            }
            this.mKernel.animSpinner(0);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (ViewCompat.isNestedScrollingEnabled(this.mRefreshContent.getScrollableView())) {
            this.mEnableDisallowIntercept = z2;
            super.requestDisallowInterceptTouchEvent(z2);
        }
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout resetNoMoreData() {
        return setNoMoreData(false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setDisableContentWhenLoading(boolean z2) {
        this.mDisableContentWhenLoading = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setDisableContentWhenRefresh(boolean z2) {
        this.mDisableContentWhenRefresh = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setDragRate(float f2) {
        this.mDragRate = f2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableAutoLoadMore(boolean z2) {
        this.mEnableAutoLoadMore = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableClipFooterWhenFixedBehind(boolean z2) {
        this.mEnableClipFooterWhenFixedBehind = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableClipHeaderWhenFixedBehind(boolean z2) {
        this.mEnableClipHeaderWhenFixedBehind = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableFooterFollowWhenNoMoreData(boolean z2) {
        this.mEnableFooterFollowWhenNoMoreData = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableFooterTranslationContent(boolean z2) {
        this.mEnableFooterTranslationContent = z2;
        this.mManualFooterTranslationContent = true;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableHeaderTranslationContent(boolean z2) {
        this.mEnableHeaderTranslationContent = z2;
        this.mManualHeaderTranslationContent = true;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableLoadMore(boolean z2) {
        this.mManualLoadMore = true;
        this.mEnableLoadMore = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableLoadMoreWhenContentNotFull(boolean z2) {
        this.mEnableLoadMoreWhenContentNotFull = z2;
        RefreshContent refreshContent = this.mRefreshContent;
        if (refreshContent != null) {
            refreshContent.setEnableLoadMoreWhenContentNotFull(z2);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableNestedScroll(boolean z2) {
        setNestedScrollingEnabled(z2);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableOverScrollBounce(boolean z2) {
        this.mEnableOverScrollBounce = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableOverScrollDrag(boolean z2) {
        this.mEnableOverScrollDrag = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnablePureScrollMode(boolean z2) {
        this.mEnablePureScrollMode = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableRefresh(boolean z2) {
        this.mEnableRefresh = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableScrollContentWhenLoaded(boolean z2) {
        this.mEnableScrollContentWhenLoaded = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableScrollContentWhenRefreshed(boolean z2) {
        this.mEnableScrollContentWhenRefreshed = z2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFixedFooterViewId(int i2) {
        this.mFixedFooterViewId = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFixedHeaderViewId(int i2) {
        this.mFixedHeaderViewId = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterHeight(float f2) {
        return setFooterHeightPx(SmartUtil.dp2px(f2));
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterHeightPx(int i2) {
        if (i2 == this.mFooterHeight) {
            return this;
        }
        DimensionStatus dimensionStatus = this.mFooterHeightStatus;
        DimensionStatus dimensionStatus2 = DimensionStatus.CodeExact;
        if (dimensionStatus.canReplaceWith(dimensionStatus2)) {
            this.mFooterHeight = i2;
            RefreshComponent refreshComponent = this.mRefreshFooter;
            if (refreshComponent != null && this.mAttachedToWindow && this.mFooterHeightStatus.notified) {
                SpinnerStyle spinnerStyle = refreshComponent.getSpinnerStyle();
                if (spinnerStyle != SpinnerStyle.MatchLayout && !spinnerStyle.scale) {
                    View view = this.mRefreshFooter.getView();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : sDefaultMarginLP;
                    view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(Math.max((this.mFooterHeight - marginLayoutParams.bottomMargin) - marginLayoutParams.topMargin, 0), 1073741824));
                    int i3 = marginLayoutParams.leftMargin;
                    int measuredHeight = ((marginLayoutParams.topMargin + getMeasuredHeight()) - this.mFooterInsetStart) - (spinnerStyle != SpinnerStyle.Translate ? this.mFooterHeight : 0);
                    view.layout(i3, measuredHeight, view.getMeasuredWidth() + i3, view.getMeasuredHeight() + measuredHeight);
                }
                float f2 = this.mFooterMaxDragRate;
                if (f2 < 10.0f) {
                    f2 *= this.mFooterHeight;
                }
                this.mFooterHeightStatus = dimensionStatus2;
                this.mRefreshFooter.onInitialized(this.mKernel, this.mFooterHeight, (int) f2);
            } else {
                this.mFooterHeightStatus = DimensionStatus.CodeExactUnNotify;
            }
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterInsetStart(float f2) {
        this.mFooterInsetStart = SmartUtil.dp2px(f2);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterInsetStartPx(int i2) {
        this.mFooterInsetStart = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterMaxDragRate(float f2) {
        this.mFooterMaxDragRate = f2;
        RefreshComponent refreshComponent = this.mRefreshFooter;
        if (refreshComponent == null || !this.mAttachedToWindow) {
            this.mFooterHeightStatus = this.mFooterHeightStatus.unNotify();
        } else {
            if (f2 < 10.0f) {
                f2 *= this.mFooterHeight;
            }
            refreshComponent.onInitialized(this.mKernel, this.mFooterHeight, (int) f2);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterTranslationViewId(int i2) {
        this.mFooterTranslationViewId = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setFooterTriggerRate(float f2) {
        this.mFooterTriggerRate = f2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderHeight(float f2) {
        return setHeaderHeightPx(SmartUtil.dp2px(f2));
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderHeightPx(int i2) {
        if (i2 == this.mHeaderHeight) {
            return this;
        }
        DimensionStatus dimensionStatus = this.mHeaderHeightStatus;
        DimensionStatus dimensionStatus2 = DimensionStatus.CodeExact;
        if (dimensionStatus.canReplaceWith(dimensionStatus2)) {
            this.mHeaderHeight = i2;
            RefreshComponent refreshComponent = this.mRefreshHeader;
            if (refreshComponent != null && this.mAttachedToWindow && this.mHeaderHeightStatus.notified) {
                SpinnerStyle spinnerStyle = refreshComponent.getSpinnerStyle();
                if (spinnerStyle != SpinnerStyle.MatchLayout && !spinnerStyle.scale) {
                    View view = this.mRefreshHeader.getView();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : sDefaultMarginLP;
                    view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(Math.max((this.mHeaderHeight - marginLayoutParams.bottomMargin) - marginLayoutParams.topMargin, 0), 1073741824));
                    int i3 = marginLayoutParams.leftMargin;
                    int i4 = (marginLayoutParams.topMargin + this.mHeaderInsetStart) - (spinnerStyle == SpinnerStyle.Translate ? this.mHeaderHeight : 0);
                    view.layout(i3, i4, view.getMeasuredWidth() + i3, view.getMeasuredHeight() + i4);
                }
                float f2 = this.mHeaderMaxDragRate;
                if (f2 < 10.0f) {
                    f2 *= this.mHeaderHeight;
                }
                this.mHeaderHeightStatus = dimensionStatus2;
                this.mRefreshHeader.onInitialized(this.mKernel, this.mHeaderHeight, (int) f2);
            } else {
                this.mHeaderHeightStatus = DimensionStatus.CodeExactUnNotify;
            }
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderInsetStart(float f2) {
        this.mHeaderInsetStart = SmartUtil.dp2px(f2);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderInsetStartPx(int i2) {
        this.mHeaderInsetStart = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderMaxDragRate(float f2) {
        this.mHeaderMaxDragRate = f2;
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent == null || !this.mAttachedToWindow) {
            this.mHeaderHeightStatus = this.mHeaderHeightStatus.unNotify();
        } else {
            if (f2 < 10.0f) {
                f2 *= this.mHeaderHeight;
            }
            refreshComponent.onInitialized(this.mKernel, this.mHeaderHeight, (int) f2);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderTranslationViewId(int i2) {
        this.mHeaderTranslationViewId = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setHeaderTriggerRate(float f2) {
        this.mHeaderTriggerRate = f2;
        return this;
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z2) {
        this.mEnableNestedScrolling = z2;
        this.mNestedChild.setNestedScrollingEnabled(z2);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setNoMoreData(boolean z2) {
        RefreshState refreshState = this.mState;
        if (refreshState == RefreshState.Refreshing && z2) {
            finishRefreshWithNoMoreData();
        } else if (refreshState == RefreshState.Loading && z2) {
            finishLoadMoreWithNoMoreData();
        } else if (this.mFooterNoMoreData != z2) {
            this.mFooterNoMoreData = z2;
            RefreshComponent refreshComponent = this.mRefreshFooter;
            if (refreshComponent instanceof RefreshFooter) {
                if (((RefreshFooter) refreshComponent).setNoMoreData(z2)) {
                    this.mFooterNoMoreDataEffective = true;
                    if (this.mFooterNoMoreData && this.mEnableFooterFollowWhenNoMoreData && this.mSpinner > 0 && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && isEnableTranslationContent(this.mEnableRefresh, this.mRefreshHeader)) {
                        this.mRefreshFooter.getView().setTranslationY(this.mSpinner);
                    }
                } else {
                    this.mFooterNoMoreDataEffective = false;
                    new RuntimeException("Footer:" + this.mRefreshFooter + " NoMoreData is not supported.(不支持NoMoreData，请使用[ClassicsFooter]或者[自定义Footer并实现setNoMoreData方法且返回true])").printStackTrace();
                }
            }
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
        this.mEnableLoadMore = this.mEnableLoadMore || !(this.mManualLoadMore || onLoadMoreListener == null);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnMultiListener(OnMultiListener onMultiListener) {
        this.mOnMultiListener = onMultiListener;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mRefreshListener = onRefreshListener;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener onRefreshLoadMoreListener) {
        this.mRefreshListener = onRefreshLoadMoreListener;
        this.mLoadMoreListener = onRefreshLoadMoreListener;
        this.mEnableLoadMore = this.mEnableLoadMore || !(this.mManualLoadMore || onRefreshLoadMoreListener == null);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setPrimaryColors(@ColorInt int... iArr) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null) {
            refreshComponent.setPrimaryColors(iArr);
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null) {
            refreshComponent2.setPrimaryColors(iArr);
        }
        this.mPrimaryColors = iArr;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setPrimaryColorsId(@ColorRes int... iArr) {
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = ContextCompat.getColor(getContext(), iArr[i2]);
        }
        setPrimaryColors(iArr2);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setReboundDuration(int i2) {
        this.mReboundDuration = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setReboundInterpolator(@NonNull Interpolator interpolator) {
        this.mReboundInterpolator = interpolator;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshContent(@NonNull View view) {
        return setRefreshContent(view, 0, 0);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter) {
        return setRefreshFooter(refreshFooter, 0, 0);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader) {
        return setRefreshHeader(refreshHeader, 0, 0);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider) {
        this.mScrollBoundaryDecider = scrollBoundaryDecider;
        RefreshContent refreshContent = this.mRefreshContent;
        if (refreshContent != null) {
            refreshContent.setScrollBoundaryDecider(scrollBoundaryDecider);
        }
        return this;
    }

    public void setStateDirectLoading(boolean z2) {
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.Loading;
        if (refreshState != refreshState2) {
            this.mLastOpenTime = System.currentTimeMillis();
            this.mFooterLocked = true;
            notifyStateChanged(refreshState2);
            OnLoadMoreListener onLoadMoreListener = this.mLoadMoreListener;
            if (onLoadMoreListener != null) {
                if (z2) {
                    onLoadMoreListener.onLoadMore(this);
                }
            } else if (this.mOnMultiListener == null) {
                finishLoadMore(2000);
            }
            RefreshComponent refreshComponent = this.mRefreshFooter;
            if (refreshComponent != null) {
                float f2 = this.mFooterMaxDragRate;
                if (f2 < 10.0f) {
                    f2 *= this.mFooterHeight;
                }
                refreshComponent.onStartAnimator(this, this.mFooterHeight, (int) f2);
            }
            OnMultiListener onMultiListener = this.mOnMultiListener;
            if (onMultiListener == null || !(this.mRefreshFooter instanceof RefreshFooter)) {
                return;
            }
            if (z2) {
                onMultiListener.onLoadMore(this);
            }
            float f3 = this.mFooterMaxDragRate;
            if (f3 < 10.0f) {
                f3 *= this.mFooterHeight;
            }
            this.mOnMultiListener.onFooterStartAnimator((RefreshFooter) this.mRefreshFooter, this.mFooterHeight, (int) f3);
        }
    }

    public void setStateLoading(final boolean z2) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout.this.setStateDirectLoading(z2);
                }
            }
        };
        notifyStateChanged(RefreshState.LoadReleased);
        ValueAnimator valueAnimatorAnimSpinner = this.mKernel.animSpinner(-this.mFooterHeight);
        if (valueAnimatorAnimSpinner != null) {
            valueAnimatorAnimSpinner.addListener(animatorListenerAdapter);
        }
        RefreshComponent refreshComponent = this.mRefreshFooter;
        if (refreshComponent != null) {
            float f2 = this.mFooterMaxDragRate;
            if (f2 < 10.0f) {
                f2 *= this.mFooterHeight;
            }
            refreshComponent.onReleased(this, this.mFooterHeight, (int) f2);
        }
        OnMultiListener onMultiListener = this.mOnMultiListener;
        if (onMultiListener != null) {
            RefreshComponent refreshComponent2 = this.mRefreshFooter;
            if (refreshComponent2 instanceof RefreshFooter) {
                float f3 = this.mFooterMaxDragRate;
                if (f3 < 10.0f) {
                    f3 *= this.mFooterHeight;
                }
                onMultiListener.onFooterReleased((RefreshFooter) refreshComponent2, this.mFooterHeight, (int) f3);
            }
        }
        if (valueAnimatorAnimSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    public void setStateRefreshing(final boolean z2) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout.this.mLastOpenTime = System.currentTimeMillis();
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.Refreshing);
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    OnRefreshListener onRefreshListener = smartRefreshLayout.mRefreshListener;
                    if (onRefreshListener != null) {
                        if (z2) {
                            onRefreshListener.onRefresh(smartRefreshLayout);
                        }
                    } else if (smartRefreshLayout.mOnMultiListener == null) {
                        smartRefreshLayout.finishRefresh(3000);
                    }
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    RefreshComponent refreshComponent = smartRefreshLayout2.mRefreshHeader;
                    if (refreshComponent != null) {
                        float f2 = smartRefreshLayout2.mHeaderMaxDragRate;
                        if (f2 < 10.0f) {
                            f2 *= smartRefreshLayout2.mHeaderHeight;
                        }
                        refreshComponent.onStartAnimator(smartRefreshLayout2, smartRefreshLayout2.mHeaderHeight, (int) f2);
                    }
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    OnMultiListener onMultiListener = smartRefreshLayout3.mOnMultiListener;
                    if (onMultiListener == null || !(smartRefreshLayout3.mRefreshHeader instanceof RefreshHeader)) {
                        return;
                    }
                    if (z2) {
                        onMultiListener.onRefresh(smartRefreshLayout3);
                    }
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    float f3 = smartRefreshLayout4.mHeaderMaxDragRate;
                    if (f3 < 10.0f) {
                        f3 *= smartRefreshLayout4.mHeaderHeight;
                    }
                    smartRefreshLayout4.mOnMultiListener.onHeaderStartAnimator((RefreshHeader) smartRefreshLayout4.mRefreshHeader, smartRefreshLayout4.mHeaderHeight, (int) f3);
                }
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator valueAnimatorAnimSpinner = this.mKernel.animSpinner(this.mHeaderHeight);
        if (valueAnimatorAnimSpinner != null) {
            valueAnimatorAnimSpinner.addListener(animatorListenerAdapter);
        }
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null) {
            float f2 = this.mHeaderMaxDragRate;
            if (f2 < 10.0f) {
                f2 *= this.mHeaderHeight;
            }
            refreshComponent.onReleased(this, this.mHeaderHeight, (int) f2);
        }
        OnMultiListener onMultiListener = this.mOnMultiListener;
        if (onMultiListener != null) {
            RefreshComponent refreshComponent2 = this.mRefreshHeader;
            if (refreshComponent2 instanceof RefreshHeader) {
                float f3 = this.mHeaderMaxDragRate;
                if (f3 < 10.0f) {
                    f3 *= this.mHeaderHeight;
                }
                onMultiListener.onHeaderReleased((RefreshHeader) refreshComponent2, this.mHeaderHeight, (int) f3);
            }
        }
        if (valueAnimatorAnimSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    public void setViceState(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2.isDragging && refreshState2.isHeader != refreshState.isHeader) {
            notifyStateChanged(RefreshState.None);
        }
        if (this.mViceState != refreshState) {
            this.mViceState = refreshState;
        }
    }

    public boolean startFlingIfNeed(float f2) {
        if (f2 == 0.0f) {
            f2 = this.mCurrentVelocity;
        }
        if (Math.abs(f2) > this.mMinimumVelocity) {
            int i2 = this.mSpinner;
            if (i2 * f2 < 0.0f) {
                RefreshState refreshState = this.mState;
                if (refreshState == RefreshState.Refreshing || refreshState == RefreshState.Loading || (i2 < 0 && this.mFooterNoMoreData)) {
                    this.animationRunnable = new FlingRunnable(f2).start();
                    return true;
                }
                if (refreshState.isReleaseToOpening) {
                    return true;
                }
            }
            if ((f2 < 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableLoadMore || this.mEnableOverScrollDrag)) || ((this.mState == RefreshState.Loading && i2 >= 0) || (this.mEnableAutoLoadMore && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) || (f2 > 0.0f && ((this.mEnableOverScrollBounce && this.mEnableRefresh) || this.mEnableOverScrollDrag || (this.mState == RefreshState.Refreshing && this.mSpinner <= 0)))) {
                this.mVerticalPermit = false;
                this.mScroller.fling(0, 0, 0, (int) (-f2), 0, 0, -2147483647, Integer.MAX_VALUE);
                this.mScroller.computeScrollOffset();
                invalidate();
            }
        }
        return false;
    }

    public SmartRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFloorDuration = 300;
        this.mReboundDuration = 300;
        this.mDragRate = 0.5f;
        this.mDragDirection = 'n';
        this.mFixedHeaderViewId = -1;
        this.mFixedFooterViewId = -1;
        this.mHeaderTranslationViewId = -1;
        this.mFooterTranslationViewId = -1;
        this.mEnableRefresh = true;
        this.mEnableLoadMore = false;
        this.mEnableClipHeaderWhenFixedBehind = true;
        this.mEnableClipFooterWhenFixedBehind = true;
        this.mEnableHeaderTranslationContent = true;
        this.mEnableFooterTranslationContent = true;
        this.mEnableFooterFollowWhenNoMoreData = false;
        this.mEnablePreviewInEditMode = true;
        this.mEnableOverScrollBounce = true;
        this.mEnableOverScrollDrag = false;
        this.mEnableAutoLoadMore = true;
        this.mEnablePureScrollMode = false;
        this.mEnableScrollContentWhenLoaded = true;
        this.mEnableScrollContentWhenRefreshed = true;
        this.mEnableLoadMoreWhenContentNotFull = true;
        this.mEnableNestedScrolling = true;
        this.mDisableContentWhenRefresh = false;
        this.mDisableContentWhenLoading = false;
        this.mFooterNoMoreData = false;
        this.mFooterNoMoreDataEffective = false;
        this.mManualLoadMore = false;
        this.mManualHeaderTranslationContent = false;
        this.mManualFooterTranslationContent = false;
        this.mParentOffsetInWindow = new int[2];
        this.mNestedChild = new NestedScrollingChildHelper(this);
        this.mNestedParent = new NestedScrollingParentHelper(this);
        DimensionStatus dimensionStatus = DimensionStatus.DefaultUnNotify;
        this.mHeaderHeightStatus = dimensionStatus;
        this.mFooterHeightStatus = dimensionStatus;
        this.mHeaderMaxDragRate = 2.5f;
        this.mFooterMaxDragRate = 2.5f;
        this.mHeaderTriggerRate = 1.0f;
        this.mFooterTriggerRate = 1.0f;
        this.mTwoLevelBottomPullUpToCloseRate = 0.16666667f;
        this.mKernel = new RefreshKernelImpl();
        RefreshState refreshState = RefreshState.None;
        this.mState = refreshState;
        this.mViceState = refreshState;
        this.mLastOpenTime = 0L;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mFooterLocked = false;
        this.mVerticalPermit = false;
        this.mFalsifyEvent = null;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mScroller = new Scroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        this.mReboundInterpolator = new SmartUtil(SmartUtil.INTERPOLATOR_VISCOUS_FLUID);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFooterHeight = SmartUtil.dp2px(60.0f);
        this.mHeaderHeight = SmartUtil.dp2px(100.0f);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmartRefreshLayout);
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_android_clipToPadding)) {
            super.setClipToPadding(false);
        }
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_android_clipChildren)) {
            super.setClipChildren(false);
        }
        DefaultRefreshInitializer defaultRefreshInitializer = sRefreshInitializer;
        if (defaultRefreshInitializer != null) {
            defaultRefreshInitializer.initialize(context, this);
        }
        this.mDragRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlDragRate, this.mDragRate);
        this.mHeaderMaxDragRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.mHeaderMaxDragRate);
        this.mFooterMaxDragRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlFooterMaxDragRate, this.mFooterMaxDragRate);
        this.mHeaderTriggerRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlHeaderTriggerRate, this.mHeaderTriggerRate);
        this.mFooterTriggerRate = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlFooterTriggerRate, this.mFooterTriggerRate);
        this.mEnableRefresh = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableRefresh, this.mEnableRefresh);
        this.mReboundDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.SmartRefreshLayout_srlReboundDuration, this.mReboundDuration);
        int i2 = R.styleable.SmartRefreshLayout_srlEnableLoadMore;
        this.mEnableLoadMore = typedArrayObtainStyledAttributes.getBoolean(i2, this.mEnableLoadMore);
        int i3 = R.styleable.SmartRefreshLayout_srlHeaderHeight;
        this.mHeaderHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(i3, this.mHeaderHeight);
        int i4 = R.styleable.SmartRefreshLayout_srlFooterHeight;
        this.mFooterHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(i4, this.mFooterHeight);
        this.mHeaderInsetStart = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlHeaderInsetStart, this.mHeaderInsetStart);
        this.mFooterInsetStart = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlFooterInsetStart, this.mFooterInsetStart);
        this.mDisableContentWhenRefresh = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, this.mDisableContentWhenRefresh);
        this.mDisableContentWhenLoading = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenLoading, this.mDisableContentWhenLoading);
        int i5 = R.styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent;
        this.mEnableHeaderTranslationContent = typedArrayObtainStyledAttributes.getBoolean(i5, this.mEnableHeaderTranslationContent);
        int i6 = R.styleable.SmartRefreshLayout_srlEnableFooterTranslationContent;
        this.mEnableFooterTranslationContent = typedArrayObtainStyledAttributes.getBoolean(i6, this.mEnableFooterTranslationContent);
        this.mEnablePreviewInEditMode = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, this.mEnablePreviewInEditMode);
        this.mEnableAutoLoadMore = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.mEnableAutoLoadMore);
        this.mEnableOverScrollBounce = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollBounce, this.mEnableOverScrollBounce);
        this.mEnablePureScrollMode = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePureScrollMode, this.mEnablePureScrollMode);
        this.mEnableScrollContentWhenLoaded = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, this.mEnableScrollContentWhenLoaded);
        this.mEnableScrollContentWhenRefreshed = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, this.mEnableScrollContentWhenRefreshed);
        this.mEnableLoadMoreWhenContentNotFull = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, this.mEnableLoadMoreWhenContentNotFull);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, this.mEnableFooterFollowWhenNoMoreData);
        this.mEnableFooterFollowWhenNoMoreData = z2;
        this.mEnableFooterFollowWhenNoMoreData = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterFollowWhenNoMoreData, z2);
        this.mEnableClipHeaderWhenFixedBehind = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, this.mEnableClipHeaderWhenFixedBehind);
        this.mEnableClipFooterWhenFixedBehind = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, this.mEnableClipFooterWhenFixedBehind);
        this.mEnableOverScrollDrag = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollDrag, this.mEnableOverScrollDrag);
        this.mFixedHeaderViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFixedHeaderViewId, this.mFixedHeaderViewId);
        this.mFixedFooterViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFixedFooterViewId, this.mFixedFooterViewId);
        this.mHeaderTranslationViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlHeaderTranslationViewId, this.mHeaderTranslationViewId);
        this.mFooterTranslationViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFooterTranslationViewId, this.mFooterTranslationViewId);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableNestedScrolling, this.mEnableNestedScrolling);
        this.mEnableNestedScrolling = z3;
        this.mNestedChild.setNestedScrollingEnabled(z3);
        this.mManualLoadMore = this.mManualLoadMore || typedArrayObtainStyledAttributes.hasValue(i2);
        this.mManualHeaderTranslationContent = this.mManualHeaderTranslationContent || typedArrayObtainStyledAttributes.hasValue(i5);
        this.mManualFooterTranslationContent = this.mManualFooterTranslationContent || typedArrayObtainStyledAttributes.hasValue(i6);
        this.mHeaderHeightStatus = typedArrayObtainStyledAttributes.hasValue(i3) ? DimensionStatus.XmlLayoutUnNotify : this.mHeaderHeightStatus;
        this.mFooterHeightStatus = typedArrayObtainStyledAttributes.hasValue(i4) ? DimensionStatus.XmlLayoutUnNotify : this.mFooterHeightStatus;
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_srlAccentColor, 0);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (color2 != 0) {
            if (color != 0) {
                this.mPrimaryColors = new int[]{color2, color};
            } else {
                this.mPrimaryColors = new int[]{color2};
            }
        } else if (color != 0) {
            this.mPrimaryColors = new int[]{0, color};
        }
        if (this.mEnablePureScrollMode && !this.mManualLoadMore && !this.mEnableLoadMore) {
            this.mEnableLoadMore = true;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoLoadMore(int i2) {
        return autoLoadMore(i2, this.mReboundDuration, (this.mFooterMaxDragRate + this.mFooterTriggerRate) / 2.0f, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoRefresh(int i2) {
        return autoRefresh(i2, this.mReboundDuration, (this.mHeaderMaxDragRate + this.mHeaderTriggerRate) / 2.0f, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMore(int i2) {
        return finishLoadMore(i2, true, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefresh(int i2) {
        return finishRefresh(i2, true, Boolean.FALSE);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshContent(@NonNull View view, int i2, int i3) {
        RefreshContent refreshContent = this.mRefreshContent;
        if (refreshContent != null) {
            super.removeView(refreshContent.getView());
        }
        if (i2 == 0) {
            i2 = -1;
        }
        if (i3 == 0) {
            i3 = -1;
        }
        LayoutParams layoutParams = new LayoutParams(i2, i3);
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        super.addView(view, getChildCount(), layoutParams);
        this.mRefreshContent = new RefreshContentWrapper(view);
        if (this.mAttachedToWindow) {
            View viewFindViewById = findViewById(this.mFixedHeaderViewId);
            View viewFindViewById2 = findViewById(this.mFixedFooterViewId);
            this.mRefreshContent.setScrollBoundaryDecider(this.mScrollBoundaryDecider);
            this.mRefreshContent.setEnableLoadMoreWhenContentNotFull(this.mEnableLoadMoreWhenContentNotFull);
            this.mRefreshContent.setUpComponent(this.mKernel, viewFindViewById, viewFindViewById2);
        }
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null && refreshComponent.getSpinnerStyle().front) {
            super.bringChildToFront(this.mRefreshHeader.getView());
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null && refreshComponent2.getSpinnerStyle().front) {
            super.bringChildToFront(this.mRefreshFooter.getView());
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter, int i2, int i3) {
        RefreshComponent refreshComponent;
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null) {
            super.removeView(refreshComponent2.getView());
        }
        this.mRefreshFooter = refreshFooter;
        this.mFooterLocked = false;
        this.mFooterBackgroundColor = 0;
        this.mFooterNoMoreDataEffective = false;
        this.mFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = DimensionStatus.DefaultUnNotify;
        this.mEnableLoadMore = !this.mManualLoadMore || this.mEnableLoadMore;
        if (i2 == 0) {
            i2 = -1;
        }
        if (i3 == 0) {
            i3 = -2;
        }
        LayoutParams layoutParams = new LayoutParams(i2, i3);
        ViewGroup.LayoutParams layoutParams2 = refreshFooter.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        if (this.mRefreshFooter.getSpinnerStyle().front) {
            super.addView(this.mRefreshFooter.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(this.mRefreshFooter.getView(), 0, layoutParams);
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null && (refreshComponent = this.mRefreshFooter) != null) {
            refreshComponent.setPrimaryColors(iArr);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader, int i2, int i3) {
        RefreshComponent refreshComponent;
        RefreshComponent refreshComponent2 = this.mRefreshHeader;
        if (refreshComponent2 != null) {
            super.removeView(refreshComponent2.getView());
        }
        this.mRefreshHeader = refreshHeader;
        this.mHeaderBackgroundColor = 0;
        this.mHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = DimensionStatus.DefaultUnNotify;
        if (i2 == 0) {
            i2 = -1;
        }
        if (i3 == 0) {
            i3 = -2;
        }
        LayoutParams layoutParams = new LayoutParams(i2, i3);
        ViewGroup.LayoutParams layoutParams2 = refreshHeader.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        if (this.mRefreshHeader.getSpinnerStyle().front) {
            super.addView(this.mRefreshHeader.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(this.mRefreshHeader.getView(), 0, layoutParams);
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null && (refreshComponent = this.mRefreshHeader) != null) {
            refreshComponent.setPrimaryColors(iArr);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoLoadMore(int i2, final int i3, final float f2, final boolean z2) {
        if (this.mState != RefreshState.None || !isEnableRefreshOrLoadMore(this.mEnableLoadMore) || this.mFooterNoMoreData) {
            return false;
        }
        Runnable runnable = new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.9
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.mViceState != RefreshState.Loading) {
                    return;
                }
                ValueAnimator valueAnimator = smartRefreshLayout.reboundAnimator;
                if (valueAnimator != null) {
                    valueAnimator.setDuration(0L);
                    SmartRefreshLayout.this.reboundAnimator.cancel();
                    SmartRefreshLayout.this.reboundAnimator = null;
                }
                SmartRefreshLayout.this.mLastTouchX = r0.getMeasuredWidth() / 2.0f;
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullUpToLoad);
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int i4 = smartRefreshLayout2.mFooterHeight;
                float f3 = i4 == 0 ? smartRefreshLayout2.mFooterTriggerRate : i4;
                float f4 = f2;
                if (f4 < 10.0f) {
                    f4 *= f3;
                }
                smartRefreshLayout2.reboundAnimator = ValueAnimator.ofInt(smartRefreshLayout2.mSpinner, -((int) f4));
                SmartRefreshLayout.this.reboundAnimator.setDuration(i3);
                SmartRefreshLayout.this.reboundAnimator.setInterpolator(new SmartUtil(SmartUtil.INTERPOLATOR_VISCOUS_FLUID));
                SmartRefreshLayout.this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.9.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                        if (smartRefreshLayout3.reboundAnimator == null || smartRefreshLayout3.mRefreshFooter == null) {
                            return;
                        }
                        smartRefreshLayout3.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), true);
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.9.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (animator == null || animator.getDuration() != 0) {
                            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                            smartRefreshLayout3.reboundAnimator = null;
                            if (smartRefreshLayout3.mRefreshFooter == null) {
                                smartRefreshLayout3.mKernel.setState(RefreshState.None);
                                return;
                            }
                            RefreshState refreshState = smartRefreshLayout3.mState;
                            RefreshState refreshState2 = RefreshState.ReleaseToLoad;
                            if (refreshState != refreshState2) {
                                smartRefreshLayout3.mKernel.setState(refreshState2);
                            }
                            SmartRefreshLayout.this.setStateLoading(!z2);
                        }
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.start();
            }
        };
        setViceState(RefreshState.Loading);
        if (i2 > 0) {
            this.mHandler.postDelayed(runnable, i2);
            return true;
        }
        runnable.run();
        return true;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public boolean autoRefresh(int i2, final int i3, final float f2, final boolean z2) {
        if (this.mState != RefreshState.None || !isEnableRefreshOrLoadMore(this.mEnableRefresh)) {
            return false;
        }
        Runnable runnable = new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.8
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.mViceState != RefreshState.Refreshing) {
                    return;
                }
                ValueAnimator valueAnimator = smartRefreshLayout.reboundAnimator;
                if (valueAnimator != null) {
                    valueAnimator.setDuration(0L);
                    SmartRefreshLayout.this.reboundAnimator.cancel();
                    SmartRefreshLayout.this.reboundAnimator = null;
                }
                SmartRefreshLayout.this.mLastTouchX = r0.getMeasuredWidth() / 2.0f;
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullDownToRefresh);
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int i4 = smartRefreshLayout2.mHeaderHeight;
                float f3 = i4 == 0 ? smartRefreshLayout2.mHeaderTriggerRate : i4;
                float f4 = f2;
                if (f4 < 10.0f) {
                    f4 *= f3;
                }
                smartRefreshLayout2.reboundAnimator = ValueAnimator.ofInt(smartRefreshLayout2.mSpinner, (int) f4);
                SmartRefreshLayout.this.reboundAnimator.setDuration(i3);
                SmartRefreshLayout.this.reboundAnimator.setInterpolator(new SmartUtil(SmartUtil.INTERPOLATOR_VISCOUS_FLUID));
                SmartRefreshLayout.this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.8.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                        if (smartRefreshLayout3.reboundAnimator == null || smartRefreshLayout3.mRefreshHeader == null) {
                            return;
                        }
                        smartRefreshLayout3.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), true);
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.8.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (animator == null || animator.getDuration() != 0) {
                            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                            smartRefreshLayout3.reboundAnimator = null;
                            if (smartRefreshLayout3.mRefreshHeader == null) {
                                smartRefreshLayout3.mKernel.setState(RefreshState.None);
                                return;
                            }
                            RefreshState refreshState = smartRefreshLayout3.mState;
                            RefreshState refreshState2 = RefreshState.ReleaseToRefresh;
                            if (refreshState != refreshState2) {
                                smartRefreshLayout3.mKernel.setState(refreshState2);
                            }
                            SmartRefreshLayout.this.setStateRefreshing(!z2);
                        }
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.start();
            }
        };
        setViceState(RefreshState.Refreshing);
        if (i2 > 0) {
            this.mHandler.postDelayed(runnable, i2);
            return true;
        }
        runnable.run();
        return true;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMore(boolean z2) {
        return finishLoadMore(z2 ? Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16 : 0, z2, false);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefresh(boolean z2) {
        if (z2) {
            return finishRefresh(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, Boolean.FALSE);
        }
        return finishRefresh(0, false, null);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMore(int i2, boolean z2, boolean z3) {
        int i3 = i2 >> 16;
        int i4 = (i2 << 16) >> 16;
        AnonymousClass7 anonymousClass7 = new AnonymousClass7(i3, z3, z2);
        if (i4 > 0) {
            this.mHandler.postDelayed(anonymousClass7, i4);
        } else {
            anonymousClass7.run();
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefresh(int i2, final boolean z2, final Boolean bool) {
        final int i3 = i2 >> 16;
        int i4 = (i2 << 16) >> 16;
        Runnable runnable = new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.6
            int count = 0;

            @Override // java.lang.Runnable
            public void run() {
                int i5 = this.count;
                if (i5 == 0) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    RefreshState refreshState = smartRefreshLayout.mState;
                    RefreshState refreshState2 = RefreshState.None;
                    if (refreshState == refreshState2 && smartRefreshLayout.mViceState == RefreshState.Refreshing) {
                        smartRefreshLayout.mViceState = refreshState2;
                    } else {
                        ValueAnimator valueAnimator = smartRefreshLayout.reboundAnimator;
                        if (valueAnimator != null && refreshState.isHeader && (refreshState.isDragging || refreshState == RefreshState.RefreshReleased)) {
                            valueAnimator.setDuration(0L);
                            SmartRefreshLayout.this.reboundAnimator.cancel();
                            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                            smartRefreshLayout2.reboundAnimator = null;
                            if (smartRefreshLayout2.mKernel.animSpinner(0) == null) {
                                SmartRefreshLayout.this.notifyStateChanged(refreshState2);
                            } else {
                                SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                            }
                        } else if (refreshState == RefreshState.Refreshing && smartRefreshLayout.mRefreshHeader != null && smartRefreshLayout.mRefreshContent != null) {
                            this.count = i5 + 1;
                            smartRefreshLayout.mHandler.postDelayed(this, i3);
                            SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshFinish);
                            if (bool == Boolean.FALSE) {
                                SmartRefreshLayout.this.setNoMoreData(false);
                            }
                        }
                    }
                    if (bool == Boolean.TRUE) {
                        SmartRefreshLayout.this.setNoMoreData(true);
                        return;
                    }
                    return;
                }
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                int iOnFinish = smartRefreshLayout3.mRefreshHeader.onFinish(smartRefreshLayout3, z2);
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                OnMultiListener onMultiListener = smartRefreshLayout4.mOnMultiListener;
                if (onMultiListener != null) {
                    RefreshComponent refreshComponent = smartRefreshLayout4.mRefreshHeader;
                    if (refreshComponent instanceof RefreshHeader) {
                        onMultiListener.onHeaderFinish((RefreshHeader) refreshComponent, z2);
                    }
                }
                if (iOnFinish < Integer.MAX_VALUE) {
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (smartRefreshLayout5.mIsBeingDragged || smartRefreshLayout5.mNestedInProgress) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                        if (smartRefreshLayout6.mIsBeingDragged) {
                            float f2 = smartRefreshLayout6.mLastTouchY;
                            smartRefreshLayout6.mTouchY = f2;
                            smartRefreshLayout6.mTouchSpinner = 0;
                            smartRefreshLayout6.mIsBeingDragged = false;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 0, smartRefreshLayout6.mLastTouchX, (f2 + smartRefreshLayout6.mSpinner) - (smartRefreshLayout6.mTouchSlop * 2), 0));
                            SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 2, smartRefreshLayout7.mLastTouchX, smartRefreshLayout7.mLastTouchY + smartRefreshLayout7.mSpinner, 0));
                        }
                        SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                        if (smartRefreshLayout8.mNestedInProgress) {
                            smartRefreshLayout8.mTotalUnconsumed = 0;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 1, smartRefreshLayout8.mLastTouchX, smartRefreshLayout8.mLastTouchY, 0));
                            SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                            smartRefreshLayout9.mNestedInProgress = false;
                            smartRefreshLayout9.mTouchSpinner = 0;
                        }
                    }
                    SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                    int i6 = smartRefreshLayout10.mSpinner;
                    if (i6 <= 0) {
                        if (i6 < 0) {
                            smartRefreshLayout10.animSpinner(0, iOnFinish, smartRefreshLayout10.mReboundInterpolator, smartRefreshLayout10.mReboundDuration);
                            return;
                        } else {
                            smartRefreshLayout10.mKernel.moveSpinner(0, false);
                            SmartRefreshLayout.this.mKernel.setState(RefreshState.None);
                            return;
                        }
                    }
                    ValueAnimator valueAnimatorAnimSpinner = smartRefreshLayout10.animSpinner(0, iOnFinish, smartRefreshLayout10.mReboundInterpolator, smartRefreshLayout10.mReboundDuration);
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    ValueAnimator.AnimatorUpdateListener animatorUpdateListenerScrollContentWhenFinished = smartRefreshLayout11.mEnableScrollContentWhenRefreshed ? smartRefreshLayout11.mRefreshContent.scrollContentWhenFinished(smartRefreshLayout11.mSpinner) : null;
                    if (valueAnimatorAnimSpinner == null || animatorUpdateListenerScrollContentWhenFinished == null) {
                        return;
                    }
                    valueAnimatorAnimSpinner.addUpdateListener(animatorUpdateListenerScrollContentWhenFinished);
                }
            }
        };
        if (i4 > 0) {
            this.mHandler.postDelayed(runnable, i4);
        } else {
            runnable.run();
        }
        return this;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int backgroundColor;
        public SpinnerStyle spinnerStyle;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmartRefreshLayout_Layout);
            this.backgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, this.backgroundColor);
            int i2 = R.styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle;
            if (typedArrayObtainStyledAttributes.hasValue(i2)) {
                this.spinnerStyle = SpinnerStyle.values[typedArrayObtainStyledAttributes.getInt(i2, SpinnerStyle.Translate.ordinal)];
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }
    }
}
