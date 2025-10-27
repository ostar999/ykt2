package me.dkzwm.widget.srl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import com.google.android.material.badge.BadgeDrawable;
import com.heytap.mcssdk.constant.a;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.indicator.DefaultIndicator;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.indicator.IIndicatorSetter;
import me.dkzwm.widget.srl.utils.AppBarUtil;
import me.dkzwm.widget.srl.utils.BoundaryUtil;
import me.dkzwm.widget.srl.utils.SRReflectUtil;
import me.dkzwm.widget.srl.utils.ScrollCompat;

/* loaded from: classes9.dex */
public class SmoothRefreshLayout extends ViewGroup implements NestedScrollingChild2, NestedScrollingParent2 {
    private static final byte FLAG_AUTO_REFRESH = 1;
    private static final int FLAG_DISABLE_LOAD_MORE = 4096;
    private static final int FLAG_DISABLE_LOAD_MORE_WHEN_CONTENT_NOT_FULL = 4194304;
    private static final int FLAG_DISABLE_PERFORM_LOAD_MORE = 1024;
    private static final int FLAG_DISABLE_PERFORM_REFRESH = 8192;
    private static final int FLAG_DISABLE_REFRESH = 16384;
    private static final int FLAG_DISABLE_WHEN_ANOTHER_DIRECTION_MOVE = 262144;
    private static final int FLAG_ENABLE_AUTO_PERFORM_LOAD_MORE = 32768;
    private static final int FLAG_ENABLE_AUTO_PERFORM_REFRESH = 65536;
    private static final int FLAG_ENABLE_CHECK_FINGER_INSIDE = 524288;
    private static final int FLAG_ENABLE_COMPAT_SYNC_SCROLL = 8388608;
    private static final int FLAG_ENABLE_DYNAMIC_ENSURE_TARGET_VIEW = 16777216;
    private static final int FLAG_ENABLE_FOOTER_DRAWER_STYLE = 512;
    private static final int FLAG_ENABLE_HEADER_DRAWER_STYLE = 256;
    private static final int FLAG_ENABLE_INTERCEPT_EVENT_WHILE_LOADING = 131072;
    private static final byte FLAG_ENABLE_KEEP_REFRESH_VIEW = 16;
    private static final byte FLAG_ENABLE_NEXT_AT_ONCE = 4;
    private static final int FLAG_ENABLE_NO_MORE_DATA = 2048;
    private static final int FLAG_ENABLE_NO_MORE_DATA_NO_BACK = 1048576;
    private static final int FLAG_ENABLE_OLD_TOUCH_HANDLING = 67108864;
    private static final byte FLAG_ENABLE_OVER_SCROLL = 8;
    private static final int FLAG_ENABLE_PERFORM_FRESH_WHEN_FLING = 33554432;
    private static final byte FLAG_ENABLE_PIN_CONTENT_VIEW = 32;
    private static final int FLAG_ENABLE_PIN_REFRESH_VIEW_WHILE_LOADING = 128;
    private static final byte FLAG_ENABLE_PULL_TO_REFRESH = 64;
    private static final int FLAG_ENABLE_SMOOTH_ROLLBACK_WHEN_COMPLETED = 2097152;
    private static final int MASK_DISABLE_PERFORM_LOAD_MORE = 7168;
    private static final int MASK_DISABLE_PERFORM_REFRESH = 24576;
    public static final byte SR_STATUS_COMPLETE = 5;
    public static final byte SR_STATUS_INIT = 1;
    public static final byte SR_STATUS_LOADING_MORE = 4;
    public static final byte SR_STATUS_PREPARE = 2;
    public static final byte SR_STATUS_REFRESHING = 3;
    public static final byte SR_VIEW_STATUS_FOOTER_IN_PROCESSING = 23;
    public static final byte SR_VIEW_STATUS_HEADER_IN_PROCESSING = 22;
    public static final byte SR_VIEW_STATUS_INIT = 21;
    private static IRefreshViewCreator sCreator;
    protected final String TAG;
    protected AppBarUtil mAppBarUtil;
    protected View mAutoFoundScrollTargetView;
    protected OnPerformAutoLoadMoreCallBack mAutoLoadMoreCallBack;
    protected OnPerformAutoRefreshCallBack mAutoRefreshCallBack;
    protected int mAutomaticAction;
    protected boolean mAutomaticActionTriggered;
    protected boolean mAutomaticActionUseSmoothScroll;
    protected Paint mBackgroundPaint;
    private float[] mCachedPoint;
    private int[] mCachedSpec;
    private final List<View> mCachedViews;
    protected int mContentResId;
    protected boolean mDealAnotherDirectionMove;
    private DelayToDispatchNestedFling mDelayToDispatchNestedFling;
    private DelayToRefreshComplete mDelayToRefreshComplete;
    protected int mDurationOfBackToFooterHeight;
    protected int mDurationOfBackToHeaderHeight;
    protected int mDurationToCloseFooter;
    protected int mDurationToCloseHeader;
    private int mFlag;
    protected float mFlingBackFactor;
    protected int mFooterBackgroundColor;
    private RefreshCompleteHook mFooterRefreshCompleteHook;
    protected IRefreshView<IIndicator> mFooterView;
    private boolean mHasSendCancelEvent;
    private boolean mHasSendDownEvent;
    protected int mHeaderBackgroundColor;
    private RefreshCompleteHook mHeaderRefreshCompleteHook;
    protected IRefreshView<IIndicator> mHeaderView;
    protected OnFooterEdgeDetectCallBack mInEdgeCanMoveFooterCallBack;
    protected OnHeaderEdgeDetectCallBack mInEdgeCanMoveHeaderCallBack;
    protected IIndicator mIndicator;
    protected IIndicatorSetter mIndicatorSetter;
    protected OnInsideAnotherDirectionViewCallback mInsideAnotherDirectionViewCallback;
    protected boolean mIsFingerInsideAnotherDirectionView;
    protected boolean mIsInterceptTouchEventInOnceTouch;
    protected boolean mIsLastOverScrollCanNotAbort;
    private boolean mIsLastRefreshSuccessful;
    protected boolean mIsSpringBackCanNotBeInterrupted;
    protected MotionEvent mLastMoveEvent;
    protected int mLastNestedType;
    private ArrayList<ILifecycleObserver> mLifecycleObservers;
    protected OnLoadMoreScrollCallback mLoadMoreScrollCallback;
    protected long mLoadingMinTime;
    protected long mLoadingStartTime;
    private int mMaxOverScrollDuration;
    protected int mMaximumFlingVelocity;
    protected int mMinFlingBackDuration;
    private int mMinOverScrollDuration;
    protected int mMinimumFlingVelocity;
    protected int mMode;
    private boolean mNeedFilterScrollEvent;
    private ArrayList<OnNestedScrollChangedListener> mNestedScrollChangedListeners;
    protected boolean mNestedScrolling;
    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    protected boolean mNestedTouchScrolling;
    private float mOffsetConsumed;
    private int mOffsetRemaining;
    private float mOffsetTotal;
    protected final int[] mParentOffsetInWindow;
    protected final int[] mParentScrollConsumed;
    protected boolean mPreventForAnotherDirection;
    protected OnRefreshListener mRefreshListener;
    protected ScrollChecker mScrollChecker;
    protected View mScrollTargetView;
    private Interpolator mSpringBackInterpolator;
    private Interpolator mSpringInterpolator;
    protected byte mStatus;
    private ArrayList<OnStatusChangedListener> mStatusChangedListeners;
    protected int mStickyFooterResId;
    protected View mStickyFooterView;
    protected int mStickyHeaderResId;
    protected View mStickyHeaderView;
    protected View mTargetView;
    protected int mTouchPointerId;
    protected int mTouchSlop;
    private ArrayList<OnUIPositionChangedListener> mUIPositionChangedListeners;
    protected VelocityTracker mVelocityTracker;
    protected byte mViewStatus;
    private boolean mViewsZAxisNeedReset;
    protected static final Interpolator sSpringInterpolator = new Interpolator() { // from class: me.dkzwm.widget.srl.SmoothRefreshLayout.1
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (f3 * f3 * f3 * f3 * f3) + 1.0f;
        }
    };
    protected static final Interpolator sFlingInterpolator = new DecelerateInterpolator(0.95f);
    protected static final Interpolator sSpringBackInterpolator = new DecelerateInterpolator(0.92f);
    private static final int[] LAYOUT_ATTRS = {android.R.attr.enabled};
    public static boolean sDebug = false;
    private static int sId = 0;

    public static class DelayToDispatchNestedFling implements Runnable {
        private WeakReference<SmoothRefreshLayout> mLayoutWeakRf;
        private int mVelocity;

        private DelayToDispatchNestedFling() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mLayoutWeakRf.get() != null) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(this.mLayoutWeakRf.get().TAG, "DelayToDispatchNestedFling: run()");
                }
                this.mLayoutWeakRf.get().dispatchNestedFling(this.mVelocity);
            }
        }
    }

    public static class DelayToRefreshComplete implements Runnable {
        private WeakReference<SmoothRefreshLayout> mLayoutWeakRf;
        private boolean mNotifyViews;

        private DelayToRefreshComplete() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mLayoutWeakRf.get() != null) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(this.mLayoutWeakRf.get().TAG, "DelayToRefreshComplete: run()");
                }
                this.mLayoutWeakRf.get().performRefreshComplete(true, this.mNotifyViews);
            }
        }
    }

    public interface OnFooterEdgeDetectCallBack {
        boolean isNotYetInEdgeCannotMoveFooter(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view, @Nullable IRefreshView iRefreshView);
    }

    public interface OnHeaderEdgeDetectCallBack {
        boolean isNotYetInEdgeCannotMoveHeader(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view, @Nullable IRefreshView iRefreshView);
    }

    public interface OnHookUIRefreshCompleteCallBack {
        @MainThread
        void onHook(RefreshCompleteHook refreshCompleteHook);
    }

    public interface OnInsideAnotherDirectionViewCallback {
        boolean isInside(float f2, float f3, View view);
    }

    public interface OnLoadMoreScrollCallback {
        void onScroll(View view, float f2);
    }

    public interface OnNestedScrollChangedListener {
        void onNestedScrollChanged();
    }

    public interface OnPerformAutoLoadMoreCallBack {
        boolean canAutoLoadMore(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view);
    }

    public interface OnPerformAutoRefreshCallBack {
        boolean canAutoRefresh(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view);
    }

    public interface OnRefreshListener {
        void onLoadingMore();

        void onRefreshing();
    }

    public interface OnStatusChangedListener {
        void onStatusChanged(byte b3, byte b4);
    }

    public interface OnUIPositionChangedListener {
        void onChanged(byte b3, IIndicator iIndicator);
    }

    public static class RefreshCompleteHook {
        private OnHookUIRefreshCompleteCallBack mCallBack;
        private SmoothRefreshLayout mLayout;
        private boolean mNotifyViews;

        /* JADX INFO: Access modifiers changed from: private */
        public void doHook() {
            if (this.mCallBack != null) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(this.mLayout.TAG, "RefreshCompleteHook: doHook()");
                }
                this.mCallBack.onHook(this);
            }
        }

        public void onHookComplete() {
            SmoothRefreshLayout smoothRefreshLayout = this.mLayout;
            if (smoothRefreshLayout != null) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(smoothRefreshLayout.TAG, "RefreshCompleteHook: onHookComplete()");
                }
                this.mLayout.performRefreshComplete(false, this.mNotifyViews);
            }
        }
    }

    public class ScrollChecker implements Runnable {
        private int[] $CachedPair;
        int $Duration;
        Interpolator $Interpolator;
        int $LastStart;
        int $LastTo;
        int $LastY;
        private final int $MaxDistance;
        private final float $Physical;
        Scroller $Scroller;
        float $Velocity;
        byte $Mode = -1;
        boolean $IsScrolling = false;
        private float $CalcFactor = 0.0f;
        private float $CalcPart = 0.0f;
        private float $LastCalcPart = 1.0f;

        public ScrollChecker() {
            DisplayMetrics displayMetrics = SmoothRefreshLayout.this.getResources().getDisplayMetrics();
            this.$MaxDistance = (int) (displayMetrics.heightPixels / 8.0f);
            this.$Interpolator = SmoothRefreshLayout.this.mSpringInterpolator;
            this.$Physical = displayMetrics.density * 386.0878f * 160.0f * 0.84f;
            this.$Scroller = new Scroller(SmoothRefreshLayout.this.getContext(), this.$Interpolator);
        }

        public int[] computeScroll(float f2) {
            float f3 = f2 * 0.65f;
            if (this.$CachedPair == null) {
                this.$CachedPair = new int[2];
            }
            float fLog = (float) Math.log(Math.abs(f3 / 4.5f) / (ViewConfiguration.getScrollFriction() * this.$Physical));
            float fExp = (float) (Math.exp((-Math.log10(f3)) / 1.2d) * 2.0d);
            this.$CachedPair[0] = Math.max(Math.min((int) (ViewConfiguration.getScrollFriction() * this.$Physical * Math.exp(fLog) * fExp), this.$MaxDistance), SmoothRefreshLayout.this.mTouchSlop);
            this.$CachedPair[1] = Math.min(Math.max((int) (fExp * 1000.0f), SmoothRefreshLayout.this.mMinOverScrollDuration), SmoothRefreshLayout.this.mMaxOverScrollDuration);
            return this.$CachedPair;
        }

        public void computeScrollOffset() {
            if (this.$Scroller.computeScrollOffset()) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(SmoothRefreshLayout.this.TAG, "ScrollChecker: computeScrollOffset()");
                }
                if (this.$Mode == 1) {
                    this.$LastY = this.$Scroller.getCurrY();
                    if (this.$Velocity > 0.0f && SmoothRefreshLayout.this.mIndicator.isAlreadyHere(0) && !SmoothRefreshLayout.this.isNotYetInEdgeCannotMoveHeader()) {
                        float fAbs = Math.abs(getCurrVelocity());
                        stop();
                        SmoothRefreshLayout.this.mIndicatorSetter.setMovingStatus(2);
                        int[] iArrComputeScroll = computeScroll(fAbs);
                        startBounce(iArrComputeScroll[0], iArrComputeScroll[1]);
                        return;
                    }
                    if (this.$Velocity < 0.0f && SmoothRefreshLayout.this.mIndicator.isAlreadyHere(0) && !SmoothRefreshLayout.this.isNotYetInEdgeCannotMoveFooter()) {
                        float fAbs2 = Math.abs(getCurrVelocity());
                        stop();
                        SmoothRefreshLayout.this.mIndicatorSetter.setMovingStatus(1);
                        if (!SmoothRefreshLayout.this.isEnabledNoMoreData() || SmoothRefreshLayout.this.getFooterHeight() <= 0) {
                            int[] iArrComputeScroll2 = computeScroll(fAbs2);
                            startBounce(iArrComputeScroll2[0], iArrComputeScroll2[1]);
                            return;
                        } else {
                            int[] iArrComputeScroll3 = computeScroll(fAbs2);
                            startBounce(Math.min(iArrComputeScroll3[0] * 3, SmoothRefreshLayout.this.getFooterHeight()), Math.min(Math.max(iArrComputeScroll3[1] * 2, SmoothRefreshLayout.this.mMinOverScrollDuration), SmoothRefreshLayout.this.mMaxOverScrollDuration));
                            return;
                        }
                    }
                }
                SmoothRefreshLayout.this.invalidate();
            }
        }

        public float getCurrVelocity() {
            float currVelocity = this.$Scroller.getCurrVelocity() * (this.$Velocity > 0.0f ? 1 : -1);
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: getCurrVelocity(): v: %s", Float.valueOf(currVelocity)));
            }
            return currVelocity;
        }

        public int getFinalY(float f2) {
            stop();
            this.$Scroller.fling(0, 0, 0, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            int iAbs = Math.abs(this.$Scroller.getFinalY());
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: getFinalY(): v: %s, finalY: %s, currentY: %s", Float.valueOf(f2), Integer.valueOf(iAbs), Integer.valueOf(SmoothRefreshLayout.this.mIndicator.getCurrentPos())));
            }
            this.$Scroller.forceFinished(true);
            return iAbs;
        }

        public boolean isFling() {
            return this.$Mode == 2;
        }

        public boolean isFlingBack() {
            return this.$Mode == 3;
        }

        public boolean isOverScrolling() {
            byte b3 = this.$Mode;
            return b3 == 2 || b3 == 3 || b3 == 0;
        }

        public boolean isPreFling() {
            return this.$Mode == 0;
        }

        @Override // java.lang.Runnable
        public void run() {
            int iCeil;
            byte b3 = this.$Mode;
            if (b3 == -1 || b3 == 1) {
                return;
            }
            boolean z2 = b3 != 2 ? !(this.$Scroller.computeScrollOffset() || this.$Scroller.getCurrY() != this.$LastY) : this.$LastTo <= this.$LastY;
            if (this.$Mode != 2) {
                iCeil = this.$Scroller.getCurrY();
            } else {
                iCeil = (int) Math.ceil(this.$LastY + (this.$CalcPart * this.$LastCalcPart));
                this.$LastCalcPart *= this.$CalcFactor;
                int i2 = this.$LastTo;
                if (iCeil > i2) {
                    iCeil = i2;
                }
            }
            int i3 = iCeil - this.$LastY;
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: run(): finished: %s, mode: %s, start: %s, to: %s, curPos: %s, curY:%s, last: %s, delta: %s", Boolean.valueOf(z2), Byte.valueOf(this.$Mode), Integer.valueOf(this.$LastStart), Integer.valueOf(this.$LastTo), Integer.valueOf(SmoothRefreshLayout.this.mIndicator.getCurrentPos()), Integer.valueOf(iCeil), Integer.valueOf(this.$LastY), Integer.valueOf(i3)));
            }
            if (!z2) {
                this.$LastY = iCeil;
                if (SmoothRefreshLayout.this.isMovingHeader()) {
                    SmoothRefreshLayout.this.moveHeaderPos(i3);
                } else if (SmoothRefreshLayout.this.isMovingFooter()) {
                    if (isPreFling()) {
                        SmoothRefreshLayout.this.moveFooterPos(i3);
                    } else {
                        SmoothRefreshLayout.this.moveFooterPos(-i3);
                    }
                }
                ViewCompat.postOnAnimation(SmoothRefreshLayout.this, this);
                SmoothRefreshLayout.this.tryToDispatchNestedFling();
                return;
            }
            byte b4 = this.$Mode;
            if (b4 != 0 && b4 != 2) {
                if (b4 == 3 || b4 == 4 || b4 == 5) {
                    stop();
                    if (SmoothRefreshLayout.this.mIndicator.isAlreadyHere(0)) {
                        return;
                    }
                    SmoothRefreshLayout.this.onRelease();
                    return;
                }
                return;
            }
            stop();
            this.$Mode = (byte) 3;
            if (SmoothRefreshLayout.this.isEnabledPerformFreshWhenFling() || SmoothRefreshLayout.this.isRefreshing() || SmoothRefreshLayout.this.isLoadingMore() || ((SmoothRefreshLayout.this.isEnabledAutoLoadMore() && SmoothRefreshLayout.this.isMovingFooter()) || (SmoothRefreshLayout.this.isEnabledAutoRefresh() && SmoothRefreshLayout.this.isMovingHeader()))) {
                SmoothRefreshLayout.this.onRelease();
            } else {
                SmoothRefreshLayout.this.tryScrollBackToTopByPercentDuration();
            }
        }

        public void scrollTo(int i2, int i3) {
            int currentPos = SmoothRefreshLayout.this.mIndicator.getCurrentPos();
            if (i2 > currentPos) {
                stop();
                setInterpolator(SmoothRefreshLayout.this.mSpringInterpolator);
                this.$Mode = (byte) 4;
            } else {
                if (i2 >= currentPos) {
                    this.$Mode = (byte) -1;
                    return;
                }
                if (!SmoothRefreshLayout.this.mScrollChecker.isFlingBack()) {
                    stop();
                    this.$Mode = (byte) 5;
                }
                setInterpolator(SmoothRefreshLayout.this.mSpringBackInterpolator);
            }
            this.$LastStart = currentPos;
            this.$LastTo = i2;
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: scrollTo(): to:%s, duration:%s", Integer.valueOf(i2), Integer.valueOf(i3)));
            }
            int i4 = this.$LastTo - this.$LastStart;
            this.$LastY = 0;
            this.$Duration = i3;
            this.$IsScrolling = true;
            this.$Scroller.startScroll(0, 0, 0, i4, i3);
            SmoothRefreshLayout.this.removeCallbacks(this);
            run();
        }

        public void setInterpolator(Interpolator interpolator) {
            if (this.$Interpolator == interpolator) {
                return;
            }
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: updateInterpolator(): interpolator: %s", interpolator.getClass().getSimpleName()));
            }
            this.$Interpolator = interpolator;
            if (this.$Scroller.isFinished()) {
                this.$Scroller = new Scroller(SmoothRefreshLayout.this.getContext(), interpolator);
                return;
            }
            byte b3 = this.$Mode;
            if (b3 == -1) {
                this.$Scroller = new Scroller(SmoothRefreshLayout.this.getContext(), interpolator);
                return;
            }
            if (b3 == 0 || b3 == 1) {
                float currVelocity = getCurrVelocity();
                this.$Scroller = new Scroller(SmoothRefreshLayout.this.getContext(), interpolator);
                if (this.$Mode == 1) {
                    startFling(currVelocity);
                    return;
                } else {
                    startPreFling(currVelocity);
                    return;
                }
            }
            if (b3 != 3 && b3 != 4 && b3 != 5) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(SmoothRefreshLayout.this.TAG, "SCROLLER_MODE_FLING does not use Scroller, so we ignored it.");
                    return;
                }
                return;
            }
            int currentPos = SmoothRefreshLayout.this.mIndicator.getCurrentPos();
            this.$LastStart = currentPos;
            int i2 = this.$LastTo - currentPos;
            int iTimePassed = this.$Scroller.timePassed();
            Scroller scroller = new Scroller(SmoothRefreshLayout.this.getContext(), interpolator);
            this.$Scroller = scroller;
            scroller.startScroll(0, 0, 0, i2, this.$Duration - iTimePassed);
            run();
        }

        public void startBounce(int i2, int i3) {
            int iFloor = (int) Math.floor((i3 * 60.0f) / 1000.0f);
            float fPow = (float) Math.pow(0.26d, 1.0f / iFloor);
            float f2 = 1.0f;
            float f3 = 1.0f;
            for (int i4 = 1; i4 < iFloor; i4++) {
                f3 *= fPow;
                f2 += f3;
            }
            this.$CalcFactor = fPow;
            this.$LastCalcPart = 1.0f;
            this.$CalcPart = i2 / f2;
            this.$LastTo = i2;
            this.$LastStart = SmoothRefreshLayout.this.mIndicator.getCurrentPos();
            this.$Mode = (byte) 2;
            this.$IsScrolling = true;
            run();
        }

        public void startFling(float f2) {
            stop();
            this.$Mode = (byte) 1;
            setInterpolator(SmoothRefreshLayout.sFlingInterpolator);
            this.$Velocity = f2;
            this.$Scroller.fling(0, 0, 0, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: startFling(): v: %s", Float.valueOf(f2)));
            }
        }

        public void startPreFling(float f2) {
            stop();
            this.$Mode = (byte) 0;
            setInterpolator(SmoothRefreshLayout.sFlingInterpolator);
            this.$Velocity = f2;
            this.$Scroller.fling(0, 0, 0, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (SmoothRefreshLayout.sDebug) {
                Log.d(SmoothRefreshLayout.this.TAG, String.format("ScrollChecker: startPreFling(): v: %s", Float.valueOf(f2)));
            }
            run();
        }

        public void stop() {
            if (this.$Mode != -1) {
                if (SmoothRefreshLayout.sDebug) {
                    Log.d(SmoothRefreshLayout.this.TAG, "ScrollChecker: stop()");
                }
                SmoothRefreshLayout smoothRefreshLayout = SmoothRefreshLayout.this;
                if (smoothRefreshLayout.mNestedScrolling && this.$Mode == 1) {
                    smoothRefreshLayout.stopNestedScroll(1);
                }
                this.$Mode = (byte) -1;
                SmoothRefreshLayout.this.mAutomaticActionUseSmoothScroll = false;
                this.$IsScrolling = false;
                this.$Scroller.forceFinished(true);
                this.$Duration = 0;
                this.$LastCalcPart = 1.0f;
                this.$LastY = 0;
                this.$LastTo = -1;
                this.$LastStart = 0;
                SmoothRefreshLayout.this.removeCallbacks(this);
            }
        }
    }

    public SmoothRefreshLayout(Context context) {
        super(context);
        StringBuilder sb = new StringBuilder();
        sb.append("SmoothRefreshLayout-");
        int i2 = sId;
        sId = i2 + 1;
        sb.append(i2);
        this.TAG = sb.toString();
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mCachedViews = new ArrayList(1);
        this.mMode = 0;
        this.mAutomaticActionUseSmoothScroll = false;
        this.mAutomaticActionTriggered = true;
        this.mIsSpringBackCanNotBeInterrupted = false;
        this.mDealAnotherDirectionMove = false;
        this.mPreventForAnotherDirection = false;
        this.mIsInterceptTouchEventInOnceTouch = false;
        this.mIsLastOverScrollCanNotAbort = false;
        this.mIsFingerInsideAnotherDirectionView = false;
        this.mNestedScrolling = false;
        this.mNestedTouchScrolling = false;
        this.mFlingBackFactor = 1.1f;
        this.mStatus = (byte) 1;
        this.mViewStatus = (byte) 21;
        this.mLoadingMinTime = 500L;
        this.mLoadingStartTime = 0L;
        this.mAutomaticAction = 0;
        this.mLastNestedType = 1;
        this.mDurationToCloseHeader = R2.attr.arcLabelPaddingBottom;
        this.mDurationToCloseFooter = R2.attr.arcLabelPaddingBottom;
        this.mDurationOfBackToHeaderHeight = 200;
        this.mDurationOfBackToFooterHeight = 200;
        this.mMinFlingBackDuration = 300;
        this.mContentResId = -1;
        this.mStickyHeaderResId = -1;
        this.mStickyFooterResId = -1;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mIsLastRefreshSuccessful = true;
        this.mViewsZAxisNeedReset = true;
        this.mNeedFilterScrollEvent = false;
        this.mHasSendCancelEvent = false;
        this.mHasSendDownEvent = false;
        this.mCachedPoint = new float[2];
        this.mCachedSpec = new int[2];
        this.mOffsetConsumed = 0.0f;
        this.mOffsetTotal = 0.0f;
        this.mFlag = 109056000;
        this.mMaxOverScrollDuration = R2.attr.arcLabelPaddingBottom;
        this.mMinOverScrollDuration = 100;
        this.mOffsetRemaining = 0;
        init(context, null, 0, 0);
    }

    private void ensureTargetView() {
        int i2;
        int i3;
        View viewEnsureScrollTargetView;
        View viewFoundViewInViewGroupById;
        AppBarUtil appBarUtil;
        if (this.mTargetView == null) {
            int childCount = getChildCount();
            boolean z2 = isEnabledDynamicEnsureTargetView() || ((appBarUtil = this.mAppBarUtil) != null && appBarUtil.hasFound());
            if (this.mContentResId == -1) {
                int i4 = childCount - 1;
                while (true) {
                    if (i4 < 0) {
                        break;
                    }
                    View childAt = getChildAt(i4);
                    if (childAt.getVisibility() == 0 && !(childAt instanceof IRefreshView)) {
                        if (!z2) {
                            this.mTargetView = childAt;
                            break;
                        }
                        View viewEnsureScrollTargetView2 = ensureScrollTargetView(childAt, true, 0.0f, 0.0f);
                        if (viewEnsureScrollTargetView2 != null) {
                            this.mTargetView = childAt;
                            if (viewEnsureScrollTargetView2 != childAt) {
                                this.mAutoFoundScrollTargetView = viewEnsureScrollTargetView2;
                            }
                        }
                    }
                    i4--;
                }
            } else {
                int i5 = childCount - 1;
                while (true) {
                    if (i5 < 0) {
                        break;
                    }
                    View childAt2 = getChildAt(i5);
                    if (this.mContentResId != childAt2.getId()) {
                        if ((childAt2 instanceof ViewGroup) && (viewFoundViewInViewGroupById = foundViewInViewGroupById((ViewGroup) childAt2, this.mContentResId)) != null) {
                            this.mTargetView = childAt2;
                            this.mScrollTargetView = viewFoundViewInViewGroupById;
                            break;
                        }
                        i5--;
                    } else {
                        this.mTargetView = childAt2;
                        if (z2 && (viewEnsureScrollTargetView = ensureScrollTargetView(childAt2, true, 0.0f, 0.0f)) != null && viewEnsureScrollTargetView != childAt2) {
                            this.mAutoFoundScrollTargetView = viewEnsureScrollTargetView;
                        }
                    }
                }
            }
            AppBarUtil appBarUtil2 = this.mAppBarUtil;
            if (appBarUtil2 != null && appBarUtil2.hasFound()) {
                if (this.mInEdgeCanMoveHeaderCallBack == null) {
                    this.mInEdgeCanMoveHeaderCallBack = this.mAppBarUtil;
                }
                if (this.mInEdgeCanMoveFooterCallBack == null) {
                    this.mInEdgeCanMoveFooterCallBack = this.mAppBarUtil;
                }
            }
        }
        if (this.mStickyHeaderView == null && (i3 = this.mStickyHeaderResId) != -1) {
            this.mStickyHeaderView = findViewById(i3);
        }
        if (this.mStickyFooterView == null && (i2 = this.mStickyFooterResId) != -1) {
            this.mStickyFooterView = findViewById(i2);
        }
        this.mHeaderView = getHeaderView();
        this.mFooterView = getFooterView();
    }

    private View foundViewInViewGroupById(ViewGroup viewGroup, int i2) {
        View viewFoundViewInViewGroupById;
        int childCount = viewGroup.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if (childAt.getId() == i2) {
                return childAt;
            }
            if ((childAt instanceof ViewGroup) && (viewFoundViewInViewGroupById = foundViewInViewGroupById((ViewGroup) childAt, i2)) != null) {
                return viewFoundViewInViewGroupById;
            }
        }
        return null;
    }

    private boolean isTransformedTouchPointInView(float f2, float f3, View view, View view2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (view2.getVisibility() != 0 || view2.getAnimation() != null) {
            return false;
        }
        float[] fArr = this.mCachedPoint;
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[0] = (view.getScrollX() - view2.getLeft()) + f2;
        float[] fArr2 = this.mCachedPoint;
        fArr2[1] = fArr2[1] + (view.getScrollY() - view2.getTop());
        SRReflectUtil.compatMapTheInverseMatrix(view2, this.mCachedPoint);
        float[] fArr3 = this.mCachedPoint;
        float f4 = fArr3[0];
        boolean z2 = f4 >= 0.0f && fArr3[1] >= 0.0f && f4 < ((float) view2.getWidth()) && this.mCachedPoint[1] < ((float) view2.getHeight());
        if (z2) {
            float[] fArr4 = this.mCachedPoint;
            fArr4[0] = fArr4[0] - f2;
            fArr4[1] = fArr4[1] - f3;
        }
        return z2;
    }

    private boolean isVerticalOrientation() {
        int supportScrollAxis = getSupportScrollAxis();
        if (supportScrollAxis != 0) {
            return supportScrollAxis == 2;
        }
        throw new IllegalArgumentException("Unsupported operation , Support scroll axis must be SCROLL_AXIS_HORIZONTAL or SCROLL_AXIS_VERTICAL !!");
    }

    private int[] measureChildAgain(LayoutParams layoutParams, int i2, int i3) {
        if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
            this.mCachedSpec[0] = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin), 1073741824);
        } else {
            this.mCachedSpec[0] = ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width);
        }
        if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
            this.mCachedSpec[1] = View.MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin), 1073741824);
        } else {
            this.mCachedSpec[1] = ViewGroup.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        }
        return this.mCachedSpec;
    }

    private void notifyNestedScrollChanged() {
        ArrayList<OnNestedScrollChangedListener> arrayList = this.mNestedScrollChangedListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        Iterator<OnNestedScrollChangedListener> it = this.mNestedScrollChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onNestedScrollChanged();
        }
    }

    private void notifyStatusChanged(byte b3, byte b4) {
        ArrayList<OnStatusChangedListener> arrayList = this.mStatusChangedListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        Iterator<OnStatusChangedListener> it = this.mStatusChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusChanged(b3, b4);
        }
    }

    private void notifyUIPositionChanged() {
        ArrayList<OnUIPositionChangedListener> arrayList = this.mUIPositionChangedListeners;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<OnUIPositionChangedListener> it = this.mUIPositionChangedListeners.iterator();
            while (it.hasNext()) {
                it.next().onChanged(this.mStatus, this.mIndicator);
            }
        }
        notifyNestedScrollChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0047 A[PHI: r3
      0x0047: PHI (r3v3 int) = (r3v1 int), (r3v5 int) binds: [B:29:0x0062, B:22:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void scrollToTriggeredAutomatic(boolean r5) {
        /*
            r4 = this;
            boolean r0 = me.dkzwm.widget.srl.SmoothRefreshLayout.sDebug
            if (r0 == 0) goto Lb
            java.lang.String r0 = r4.TAG
            java.lang.String r1 = "scrollToTriggeredAutomatic()"
            android.util.Log.d(r0, r1)
        Lb:
            int r0 = r4.mAutomaticAction
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L2b
            if (r0 == r2) goto L21
            r3 = 2
            if (r0 == r3) goto L17
            goto L30
        L17:
            if (r5 == 0) goto L1d
            r4.triggeredRefresh(r1)
            goto L30
        L1d:
            r4.triggeredLoadMore(r1)
            goto L30
        L21:
            if (r5 == 0) goto L27
            r4.triggeredRefresh(r2)
            goto L30
        L27:
            r4.triggeredLoadMore(r2)
            goto L30
        L2b:
            int r0 = r4.mFlag
            r0 = r0 | r2
            r4.mFlag = r0
        L30:
            if (r5 == 0) goto L50
            boolean r0 = r4.isEnabledKeepRefreshView()
            if (r0 == 0) goto L49
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r0 = r0.getOffsetToKeepHeaderWhileLoading()
            me.dkzwm.widget.srl.indicator.IIndicator r3 = r4.mIndicator
            int r3 = r3.getOffsetToRefresh()
            if (r0 < r3) goto L47
            goto L6b
        L47:
            r0 = r3
            goto L6b
        L49:
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r0 = r0.getOffsetToRefresh()
            goto L6b
        L50:
            boolean r0 = r4.isEnabledKeepRefreshView()
            if (r0 == 0) goto L65
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r0 = r0.getOffsetToKeepFooterWhileLoading()
            me.dkzwm.widget.srl.indicator.IIndicator r3 = r4.mIndicator
            int r3 = r3.getOffsetToLoadMore()
            if (r0 < r3) goto L47
            goto L6b
        L65:
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r0 = r0.getOffsetToLoadMore()
        L6b:
            r4.mAutomaticActionTriggered = r2
            me.dkzwm.widget.srl.SmoothRefreshLayout$ScrollChecker r2 = r4.mScrollChecker
            boolean r3 = r4.mAutomaticActionUseSmoothScroll
            if (r3 == 0) goto L7a
            if (r5 == 0) goto L78
            int r1 = r4.mDurationToCloseHeader
            goto L7a
        L78:
            int r1 = r4.mDurationToCloseFooter
        L7a:
            r2.scrollTo(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: me.dkzwm.widget.srl.SmoothRefreshLayout.scrollToTriggeredAutomatic(boolean):void");
    }

    public static void setDefaultCreator(IRefreshViewCreator iRefreshViewCreator) {
        sCreator = iRefreshViewCreator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToDispatchNestedFling() {
        if (this.mScrollChecker.isPreFling() && this.mIndicator.isAlreadyHere(0)) {
            if (sDebug) {
                Log.d(this.TAG, "tryToDispatchNestedFling()");
            }
            int currVelocity = (int) (this.mScrollChecker.getCurrVelocity() + 0.5f);
            this.mIndicatorSetter.setMovingStatus(0);
            if (isEnabledOverScroll() && (!isDisabledLoadMoreWhenContentNotFull() || isNotYetInEdgeCannotMoveHeader() || isNotYetInEdgeCannotMoveFooter())) {
                this.mScrollChecker.startFling(currVelocity);
            } else {
                this.mScrollChecker.stop();
            }
            dispatchNestedFling(currVelocity);
            postInvalidateDelayed(30L);
        }
    }

    public void addLifecycleObserver(@NonNull ILifecycleObserver iLifecycleObserver) {
        ArrayList<ILifecycleObserver> arrayList = this.mLifecycleObservers;
        if (arrayList == null) {
            ArrayList<ILifecycleObserver> arrayList2 = new ArrayList<>();
            this.mLifecycleObservers = arrayList2;
            arrayList2.add(iLifecycleObserver);
        } else {
            if (arrayList.contains(iLifecycleObserver)) {
                return;
            }
            this.mLifecycleObservers.add(iLifecycleObserver);
        }
    }

    public void addOnNestedScrollChangedListener(@NonNull OnNestedScrollChangedListener onNestedScrollChangedListener) {
        ArrayList<OnNestedScrollChangedListener> arrayList = this.mNestedScrollChangedListeners;
        if (arrayList == null) {
            ArrayList<OnNestedScrollChangedListener> arrayList2 = new ArrayList<>();
            this.mNestedScrollChangedListeners = arrayList2;
            arrayList2.add(onNestedScrollChangedListener);
        } else {
            if (arrayList.contains(onNestedScrollChangedListener)) {
                return;
            }
            this.mNestedScrollChangedListeners.add(onNestedScrollChangedListener);
        }
    }

    public void addOnStatusChangedListener(@NonNull OnStatusChangedListener onStatusChangedListener) {
        ArrayList<OnStatusChangedListener> arrayList = this.mStatusChangedListeners;
        if (arrayList == null) {
            ArrayList<OnStatusChangedListener> arrayList2 = new ArrayList<>();
            this.mStatusChangedListeners = arrayList2;
            arrayList2.add(onStatusChangedListener);
        } else {
            if (arrayList.contains(onStatusChangedListener)) {
                return;
            }
            this.mStatusChangedListeners.add(onStatusChangedListener);
        }
    }

    public void addOnUIPositionChangedListener(@NonNull OnUIPositionChangedListener onUIPositionChangedListener) {
        ArrayList<OnUIPositionChangedListener> arrayList = this.mUIPositionChangedListeners;
        if (arrayList == null) {
            ArrayList<OnUIPositionChangedListener> arrayList2 = new ArrayList<>();
            this.mUIPositionChangedListeners = arrayList2;
            arrayList2.add(onUIPositionChangedListener);
        } else {
            if (arrayList.contains(onUIPositionChangedListener)) {
                return;
            }
            this.mUIPositionChangedListeners.add(onUIPositionChangedListener);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    @CallSuper
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        if (view instanceof IRefreshView) {
            IRefreshView<IIndicator> iRefreshView = (IRefreshView) view;
            int type = iRefreshView.getType();
            if (type != 0) {
                if (type == 1) {
                    if (this.mFooterView != null) {
                        throw new IllegalArgumentException("Unsupported operation , FooterView only can be add once !!");
                    }
                    this.mFooterView = iRefreshView;
                }
            } else {
                if (this.mHeaderView != null) {
                    throw new IllegalArgumentException("Unsupported operation , HeaderView only can be add once !!");
                }
                this.mHeaderView = iRefreshView;
            }
        }
        super.addView(view, i2, layoutParams);
    }

    public boolean autoLoadMore() {
        return autoLoadMore(0, true);
    }

    public boolean autoRefresh() {
        return autoRefresh(0, true);
    }

    public float calculateScale() {
        return this.mIndicator.getCurrentPos() >= 0 ? ((float) Math.min(0.20000000298023224d, Math.pow(this.mIndicator.getCurrentPos(), 0.7200000286102295d) / 1000.0d)) + 1.0f : 1.0f - ((float) Math.min(0.20000000298023224d, Math.pow(-this.mIndicator.getCurrentPos(), 0.7200000286102295d) / 1000.0d));
    }

    public boolean canAutoLoadMore(View view) {
        OnPerformAutoLoadMoreCallBack onPerformAutoLoadMoreCallBack = this.mAutoLoadMoreCallBack;
        return onPerformAutoLoadMoreCallBack != null ? onPerformAutoLoadMoreCallBack.canAutoLoadMore(this, view) : ScrollCompat.canAutoLoadMore(view);
    }

    public boolean canAutoRefresh(View view) {
        OnPerformAutoRefreshCallBack onPerformAutoRefreshCallBack = this.mAutoRefreshCallBack;
        return onPerformAutoRefreshCallBack != null ? onPerformAutoRefreshCallBack.canAutoRefresh(this, view) : ScrollCompat.canAutoRefresh(view);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void checkViewsZAxisNeedReset() {
        int childCount = getChildCount();
        if (this.mViewsZAxisNeedReset && childCount > 0) {
            this.mCachedViews.clear();
            boolean zIsEnabledHeaderDrawerStyle = isEnabledHeaderDrawerStyle();
            boolean zIsEnabledFooterDrawerStyle = isEnabledFooterDrawerStyle();
            if (zIsEnabledHeaderDrawerStyle && zIsEnabledFooterDrawerStyle) {
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    View childAt = getChildAt(i2);
                    if (childAt != this.mHeaderView.getView() && childAt != this.mFooterView.getView()) {
                        this.mCachedViews.add(childAt);
                    }
                }
            } else if (zIsEnabledHeaderDrawerStyle) {
                for (int i3 = childCount - 1; i3 >= 0; i3--) {
                    View childAt2 = getChildAt(i3);
                    if (childAt2 != this.mHeaderView.getView()) {
                        this.mCachedViews.add(childAt2);
                    }
                }
            } else if (zIsEnabledFooterDrawerStyle) {
                for (int i4 = childCount - 1; i4 >= 0; i4--) {
                    View childAt3 = getChildAt(i4);
                    if (childAt3 != this.mFooterView.getView()) {
                        this.mCachedViews.add(childAt3);
                    }
                }
            } else {
                for (int i5 = childCount - 1; i5 >= 0; i5--) {
                    View childAt4 = getChildAt(i5);
                    if (childAt4 != this.mTargetView) {
                        this.mCachedViews.add(childAt4);
                    }
                }
            }
            int size = this.mCachedViews.size();
            if (size > 0) {
                for (int i6 = size - 1; i6 >= 0; i6--) {
                    bringChildToFront(this.mCachedViews.get(i6));
                }
            }
            this.mCachedViews.clear();
        }
        this.mViewsZAxisNeedReset = false;
    }

    public void compatLoadMoreScroll(View view, float f2) {
        OnLoadMoreScrollCallback onLoadMoreScrollCallback = this.mLoadMoreScrollCallback;
        if (onLoadMoreScrollCallback != null) {
            onLoadMoreScrollCallback.onScroll(view, f2);
        } else {
            ScrollCompat.scrollCompat(this, view, f2);
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mNestedScrolling || !isMovingContent()) {
            return;
        }
        onNestedScrollChanged();
    }

    public void createIndicator() {
        DefaultIndicator defaultIndicator = new DefaultIndicator();
        this.mIndicator = defaultIndicator;
        this.mIndicatorSetter = defaultIndicator;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f2, f3, z2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return (!isEnabled() || this.mTargetView == null || (isDisabledLoadMore() && isDisabledRefresh()) || ((isEnabledPinRefreshViewWhileLoading() && ((isRefreshing() && isMovingHeader()) || (isLoadingMore() && isMovingFooter()))) || this.mNestedTouchScrolling)) ? super.dispatchTouchEvent(motionEvent) : processDispatchTouchEvent(motionEvent);
    }

    public final boolean dispatchTouchEventSuper(MotionEvent motionEvent) {
        int currentPos;
        int lastPos;
        if (!isEnabledOldTouchHandling()) {
            if (motionEvent.findPointerIndex(this.mTouchPointerId) < 0) {
                return super.dispatchTouchEvent(motionEvent);
            }
            if (motionEvent.getActionMasked() == 0) {
                this.mOffsetConsumed = 0.0f;
                this.mOffsetTotal = 0.0f;
                this.mOffsetRemaining = this.mTouchSlop * 2;
            } else {
                if (!this.mIndicator.isAlreadyHere(0) && this.mIndicator.getRawOffset() != 0.0f) {
                    int i2 = this.mOffsetRemaining;
                    if (i2 > 0) {
                        this.mOffsetRemaining = i2 - this.mTouchSlop;
                        if (isMovingHeader()) {
                            this.mOffsetTotal -= this.mOffsetRemaining;
                        } else if (isMovingFooter()) {
                            this.mOffsetTotal += this.mOffsetRemaining;
                        }
                    }
                    float f2 = this.mOffsetConsumed;
                    if (this.mIndicator.getRawOffset() < 0.0f) {
                        currentPos = this.mIndicator.getLastPos();
                        lastPos = this.mIndicator.getCurrentPos();
                    } else {
                        currentPos = this.mIndicator.getCurrentPos();
                        lastPos = this.mIndicator.getLastPos();
                    }
                    this.mOffsetConsumed = f2 + (currentPos - lastPos);
                    this.mOffsetTotal += this.mIndicator.getRawOffset();
                }
                if (isVerticalOrientation()) {
                    motionEvent.offsetLocation(0.0f, this.mOffsetConsumed - this.mOffsetTotal);
                } else {
                    motionEvent.offsetLocation(this.mOffsetConsumed - this.mOffsetTotal, 0.0f);
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void drawFooterBackground(Canvas canvas) {
        int iMax;
        int height;
        View view = this.mTargetView;
        if (view != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            height = getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + this.mTargetView.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            iMax = height - this.mIndicator.getCurrentPos();
        } else {
            iMax = Math.max((getHeight() - getPaddingBottom()) - this.mIndicator.getCurrentPos(), getPaddingTop());
            height = getHeight() - getPaddingBottom();
        }
        canvas.drawRect(getPaddingLeft(), iMax, getWidth() - getPaddingRight(), height, this.mBackgroundPaint);
    }

    public void drawHeaderBackground(Canvas canvas) {
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), Math.min(getPaddingTop() + this.mIndicator.getCurrentPos(), getHeight() - getPaddingTop()), this.mBackgroundPaint);
    }

    public View ensureScrollTargetView(View view, boolean z2, float f2, float f3) {
        if (!(view instanceof IRefreshView) && view.getVisibility() == 0 && view.getAnimation() == null) {
            if (isScrollingView(view)) {
                return view;
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt = viewGroup.getChildAt(childCount);
                    if (z2 || isTransformedTouchPointInView(f2, f3, viewGroup, childAt)) {
                        float[] fArr = this.mCachedPoint;
                        View viewEnsureScrollTargetView = ensureScrollTargetView(childAt, z2, fArr[0] + f2, fArr[1] + f3);
                        if (viewEnsureScrollTargetView != null) {
                            return viewEnsureScrollTargetView;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getDurationToCloseFooter() {
        return this.mDurationToCloseFooter;
    }

    public int getDurationToCloseHeader() {
        return this.mDurationToCloseHeader;
    }

    public int getFooterHeight() {
        return this.mIndicator.getFooterHeight();
    }

    @Nullable
    public IRefreshView<IIndicator> getFooterView() {
        IRefreshViewCreator iRefreshViewCreator;
        IRefreshView<IIndicator> iRefreshViewCreateFooter;
        if (!isDisabledLoadMore() && this.mFooterView == null && (iRefreshViewCreator = sCreator) != null && this.mMode == 0 && (iRefreshViewCreateFooter = iRefreshViewCreator.createFooter(this)) != null) {
            setFooterView(iRefreshViewCreateFooter);
        }
        return this.mFooterView;
    }

    public int getHeaderHeight() {
        return this.mIndicator.getHeaderHeight();
    }

    @Nullable
    public IRefreshView<IIndicator> getHeaderView() {
        IRefreshViewCreator iRefreshViewCreator;
        IRefreshView<IIndicator> iRefreshViewCreateHeader;
        if (!isDisabledRefresh() && this.mHeaderView == null && (iRefreshViewCreator = sCreator) != null && this.mMode == 0 && (iRefreshViewCreateHeader = iRefreshViewCreator.createHeader(this)) != null) {
            setHeaderView(iRefreshViewCreateHeader);
        }
        return this.mHeaderView;
    }

    public final IIndicator getIndicator() {
        return this.mIndicator;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public byte getScrollMode() {
        return this.mScrollChecker.$Mode;
    }

    @Nullable
    public View getScrollTargetView() {
        View view = this.mScrollTargetView;
        if (view != null) {
            return view;
        }
        View view2 = this.mAutoFoundScrollTargetView;
        if (view2 != null) {
            return view2;
        }
        return null;
    }

    public int getSupportScrollAxis() {
        return 2;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @CallSuper
    public void init(Context context, AttributeSet attributeSet, int i2, int i3) {
        sId++;
        createIndicator();
        if (this.mIndicator == null || this.mIndicatorSetter == null) {
            throw new IllegalArgumentException("You must create a IIndicator, current indicator is null");
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScrollChecker = new ScrollChecker();
        this.mSpringInterpolator = sSpringInterpolator;
        this.mSpringBackInterpolator = sSpringBackInterpolator;
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mAppBarUtil = new AppBarUtil();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmoothRefreshLayout, i2, i3);
        if (typedArrayObtainStyledAttributes != null) {
            try {
                this.mContentResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmoothRefreshLayout_sr_content, this.mContentResId);
                float f2 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_resistance, 1.65f);
                this.mIndicatorSetter.setResistance(f2);
                this.mIndicatorSetter.setResistanceOfHeader(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_resistanceOfHeader, f2));
                this.mIndicatorSetter.setResistanceOfFooter(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_resistanceOfFooter, f2));
                int i4 = R.styleable.SmoothRefreshLayout_sr_backToKeepDuration;
                this.mDurationOfBackToHeaderHeight = typedArrayObtainStyledAttributes.getInt(i4, this.mDurationOfBackToHeaderHeight);
                this.mDurationOfBackToFooterHeight = typedArrayObtainStyledAttributes.getInt(i4, this.mDurationOfBackToFooterHeight);
                this.mDurationOfBackToHeaderHeight = typedArrayObtainStyledAttributes.getInt(R.styleable.SmoothRefreshLayout_sr_backToKeepHeaderDuration, this.mDurationOfBackToHeaderHeight);
                this.mDurationOfBackToFooterHeight = typedArrayObtainStyledAttributes.getInt(R.styleable.SmoothRefreshLayout_sr_backToKeepFooterDuration, this.mDurationOfBackToFooterHeight);
                int i5 = R.styleable.SmoothRefreshLayout_sr_closeDuration;
                this.mDurationToCloseHeader = typedArrayObtainStyledAttributes.getInt(i5, this.mDurationToCloseHeader);
                this.mDurationToCloseFooter = typedArrayObtainStyledAttributes.getInt(i5, this.mDurationToCloseFooter);
                this.mDurationToCloseHeader = typedArrayObtainStyledAttributes.getInt(R.styleable.SmoothRefreshLayout_sr_closeHeaderDuration, this.mDurationToCloseHeader);
                this.mDurationToCloseFooter = typedArrayObtainStyledAttributes.getInt(R.styleable.SmoothRefreshLayout_sr_closeFooterDuration, this.mDurationToCloseFooter);
                float f3 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioToRefresh, 1.0f);
                this.mIndicatorSetter.setRatioToRefresh(f3);
                this.mIndicatorSetter.setRatioOfHeaderToRefresh(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioOfHeaderToRefresh, f3));
                this.mIndicatorSetter.setRatioOfFooterToRefresh(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioOfFooterToRefresh, f3));
                float f4 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioToKeep, 1.0f);
                this.mIndicatorSetter.setRatioToKeepHeader(f4);
                this.mIndicatorSetter.setRatioToKeepFooter(f4);
                this.mIndicatorSetter.setRatioToKeepHeader(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioToKeepHeader, f4));
                this.mIndicatorSetter.setRatioToKeepFooter(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_ratioToKeepFooter, f4));
                float f5 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_maxMoveRatio, 0.0f);
                this.mIndicatorSetter.setMaxMoveRatio(f5);
                this.mIndicatorSetter.setMaxMoveRatioOfHeader(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_maxMoveRatioOfHeader, f5));
                this.mIndicatorSetter.setMaxMoveRatioOfFooter(typedArrayObtainStyledAttributes.getFloat(R.styleable.SmoothRefreshLayout_sr_maxMoveRatioOfFooter, f5));
                this.mStickyHeaderResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmoothRefreshLayout_sr_stickyHeader, -1);
                this.mStickyFooterResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmoothRefreshLayout_sr_stickyFooter, -1);
                this.mHeaderBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothRefreshLayout_sr_headerBackgroundColor, 0);
                this.mFooterBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothRefreshLayout_sr_footerBackgroundColor, 0);
                setEnableKeepRefreshView(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enableKeep, true));
                setEnablePinContentView(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enablePinContent, false));
                setEnableOverScroll(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enableOverScroll, true));
                setEnablePullToRefresh(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enablePullToRefresh, false));
                setDisableRefresh(!typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enableRefresh, true));
                setDisableLoadMore(!typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmoothRefreshLayout_sr_enableLoadMore, false));
                this.mMode = typedArrayObtainStyledAttributes.getInt(R.styleable.SmoothRefreshLayout_sr_mode, 0);
                preparePaint();
                typedArrayObtainStyledAttributes.recycle();
                try {
                    typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS, i2, i3);
                    setEnabled(typedArrayObtainStyledAttributes.getBoolean(0, true));
                } finally {
                }
            } finally {
            }
        } else {
            setWillNotDraw(true);
            setEnablePullToRefresh(true);
            setEnableKeepRefreshView(true);
        }
        setNestedScrollingEnabled(true);
    }

    public boolean isAutoRefresh() {
        return (this.mFlag & 1) > 0;
    }

    public boolean isCanNotAbortOverScrolling() {
        return this.mScrollChecker.isOverScrolling() && ((isMovingHeader() && isDisabledRefresh()) || (isMovingFooter() && isDisabledLoadMore()));
    }

    public boolean isDisabledLoadMore() {
        return (this.mFlag & 4096) > 0;
    }

    public boolean isDisabledLoadMoreWhenContentNotFull() {
        return (this.mFlag & 4194304) > 0;
    }

    public boolean isDisabledPerformLoadMore() {
        return (this.mFlag & 7168) > 0;
    }

    public boolean isDisabledPerformRefresh() {
        return (this.mFlag & 24576) > 0;
    }

    public boolean isDisabledRefresh() {
        return (this.mFlag & 16384) > 0;
    }

    public boolean isDisabledWhenAnotherDirectionMove() {
        return (this.mFlag & 262144) > 0;
    }

    public boolean isEnableCheckInsideAnotherDirectionView() {
        return (this.mFlag & 524288) > 0;
    }

    public boolean isEnabledAutoLoadMore() {
        return (this.mFlag & 32768) > 0;
    }

    public boolean isEnabledAutoRefresh() {
        return (this.mFlag & 65536) > 0;
    }

    public boolean isEnabledDynamicEnsureTargetView() {
        return (this.mFlag & 16777216) > 0;
    }

    public boolean isEnabledFooterDrawerStyle() {
        return (this.mFlag & 512) > 0;
    }

    public boolean isEnabledHeaderDrawerStyle() {
        return (this.mFlag & 256) > 0;
    }

    public boolean isEnabledInterceptEventWhileLoading() {
        return (this.mFlag & 131072) > 0;
    }

    public boolean isEnabledKeepRefreshView() {
        return (this.mFlag & 16) > 0;
    }

    public boolean isEnabledNextPtrAtOnce() {
        return (this.mFlag & 4) > 0;
    }

    public boolean isEnabledNoMoreData() {
        return (this.mFlag & 2048) > 0;
    }

    public boolean isEnabledNoSpringBackWhenNoMoreData() {
        return (this.mFlag & 1048576) > 0;
    }

    public boolean isEnabledOldTouchHandling() {
        return (this.mFlag & FLAG_ENABLE_OLD_TOUCH_HANDLING) > 0;
    }

    public boolean isEnabledOverScroll() {
        return (this.mFlag & 8) > 0;
    }

    public boolean isEnabledPerformFreshWhenFling() {
        return (this.mFlag & 33554432) > 0;
    }

    public boolean isEnabledPinContentView() {
        return (this.mFlag & 32) > 0;
    }

    public boolean isEnabledPinRefreshViewWhileLoading() {
        return (this.mFlag & 128) > 0;
    }

    public boolean isEnabledPullToRefresh() {
        return (this.mFlag & 64) > 0;
    }

    public boolean isEnabledSmoothRollbackWhenCompleted() {
        return (this.mFlag & 2097152) > 0;
    }

    public boolean isFooterInProcessing() {
        return this.mViewStatus == 23;
    }

    public boolean isHeaderInProcessing() {
        return this.mViewStatus == 22;
    }

    public boolean isInsideAnotherDirectionView(float f2, float f3) {
        OnInsideAnotherDirectionViewCallback onInsideAnotherDirectionViewCallback = this.mInsideAnotherDirectionViewCallback;
        return onInsideAnotherDirectionViewCallback != null ? onInsideAnotherDirectionViewCallback.isInside(f2, f3, this.mTargetView) : BoundaryUtil.isInsideHorizontalView(f2, f3, this.mTargetView);
    }

    public boolean isLoadingMore() {
        return this.mStatus == 4;
    }

    public boolean isMovingContent() {
        return this.mIndicator.getMovingStatus() == 0;
    }

    public boolean isMovingFooter() {
        return this.mIndicator.getMovingStatus() == 1;
    }

    public boolean isMovingHeader() {
        return this.mIndicator.getMovingStatus() == 2;
    }

    public boolean isNeedFilterTouchEvent() {
        return this.mIsLastOverScrollCanNotAbort || this.mIsSpringBackCanNotBeInterrupted || this.mIsInterceptTouchEventInOnceTouch;
    }

    public boolean isNeedInterceptTouchEvent() {
        return (isEnabledInterceptEventWhileLoading() && (isRefreshing() || isLoadingMore())) || this.mAutomaticActionUseSmoothScroll;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean isNotYetInEdgeCannotMoveFooter() {
        View view = this.mScrollTargetView;
        if (view != null) {
            return isNotYetInEdgeCannotMoveFooter(view);
        }
        View view2 = this.mAutoFoundScrollTargetView;
        return view2 != null ? isNotYetInEdgeCannotMoveFooter(view2) : isNotYetInEdgeCannotMoveFooter(this.mTargetView);
    }

    public boolean isNotYetInEdgeCannotMoveHeader() {
        View view = this.mScrollTargetView;
        if (view != null) {
            return isNotYetInEdgeCannotMoveHeader(view);
        }
        View view2 = this.mAutoFoundScrollTargetView;
        return view2 != null ? isNotYetInEdgeCannotMoveHeader(view2) : isNotYetInEdgeCannotMoveHeader(this.mTargetView);
    }

    public boolean isRefreshSuccessful() {
        return this.mIsLastRefreshSuccessful;
    }

    public boolean isRefreshing() {
        return this.mStatus == 3;
    }

    public boolean isScrollingView(View view) {
        return ScrollCompat.isScrollingView(view);
    }

    public int layoutContentView(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        int measuredWidth = view.getMeasuredWidth() + paddingLeft;
        int paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        int measuredHeight = view.getMeasuredHeight() + paddingTop;
        view.layout(paddingLeft, paddingTop, measuredWidth, measuredHeight);
        if (sDebug) {
            Log.d(this.TAG, String.format("onLayout(): content: %s %s %s %s", Integer.valueOf(paddingLeft), Integer.valueOf(paddingTop), Integer.valueOf(measuredWidth), Integer.valueOf(measuredHeight)));
        }
        return measuredHeight + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void layoutFooterView(android.view.View r11, int r12) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: me.dkzwm.widget.srl.SmoothRefreshLayout.layoutFooterView(android.view.View, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void layoutHeaderView(android.view.View r11) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: me.dkzwm.widget.srl.SmoothRefreshLayout.layoutHeaderView(android.view.View):void");
    }

    @SuppressLint({"RtlHardcpded", "RtlHardcoded"})
    public void layoutOtherView(View view, int i2, int i3) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i4 = layoutParams.gravity;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i4, ViewCompat.getLayoutDirection(this));
        int i5 = i4 & 112;
        int i6 = absoluteGravity & 7;
        int paddingLeft = i6 != 1 ? i6 != 5 ? getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin : (i2 - measuredWidth) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : ((getPaddingLeft() + (((i2 - getPaddingLeft()) - measuredWidth) / 2)) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int paddingTop = i5 != 16 ? i5 != 80 ? getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin : (i3 - measuredHeight) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin : ((getPaddingTop() + (((i3 - getPaddingTop()) - measuredHeight) / 2)) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i7 = measuredWidth + paddingLeft;
        int i8 = measuredHeight + paddingTop;
        view.layout(paddingLeft, paddingTop, i7, i8);
        if (sDebug) {
            Log.d(this.TAG, String.format("onLayout(): child: %s %s %s %s", Integer.valueOf(paddingLeft), Integer.valueOf(paddingTop), Integer.valueOf(i7), Integer.valueOf(i8)));
        }
    }

    public void layoutStickyFooterView(@NonNull View view, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        int measuredWidth = view.getMeasuredWidth() + paddingLeft;
        int i3 = i2 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int measuredHeight = i3 - view.getMeasuredHeight();
        view.layout(paddingLeft, measuredHeight, measuredWidth, i3);
        if (sDebug) {
            Log.d(this.TAG, String.format("onLayout(): stickyFooter: %s %s %s %s", Integer.valueOf(paddingLeft), Integer.valueOf(measuredHeight), Integer.valueOf(measuredWidth), Integer.valueOf(i3)));
        }
    }

    public void layoutStickyHeaderView(@NonNull View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        int measuredWidth = view.getMeasuredWidth() + paddingLeft;
        int paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        int measuredHeight = view.getMeasuredHeight() + paddingTop;
        view.layout(paddingLeft, paddingTop, measuredWidth, measuredHeight);
        if (sDebug) {
            Log.d(this.TAG, String.format("onLayout(): stickyHeader: %s %s %s %s", Integer.valueOf(paddingLeft), Integer.valueOf(paddingTop), Integer.valueOf(measuredWidth), Integer.valueOf(measuredHeight)));
        }
    }

    public void makeNewTouchDownEvent(MotionEvent motionEvent) {
        if (sDebug) {
            Log.d(this.TAG, "makeNewTouchDownEvent()");
        }
        sendCancelEvent(motionEvent);
        sendDownEvent(motionEvent);
        this.mIndicatorSetter.onFingerUp();
        this.mIndicatorSetter.onFingerDown(motionEvent.getX(), motionEvent.getY());
    }

    public void measureFooter(View view, LayoutParams layoutParams, int i2, int i3) {
        int iMakeMeasureSpec;
        if (isDisabledLoadMore()) {
            return;
        }
        int customHeight = this.mFooterView.getCustomHeight();
        if (this.mFooterView.getStyle() == 0 || this.mFooterView.getStyle() == 2 || this.mFooterView.getStyle() == 5 || this.mFooterView.getStyle() == 4) {
            if (customHeight > 0) {
                ((ViewGroup.MarginLayoutParams) layoutParams).height = customHeight;
            } else if (customHeight == -1) {
                ((ViewGroup.MarginLayoutParams) layoutParams).height = -1;
            }
            measureChildWithMargins(view, i2, 0, i3, 0);
            this.mIndicatorSetter.setFooterHeight(view.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            return;
        }
        if (customHeight <= 0 && customHeight != -1) {
            throw new IllegalArgumentException("If footer view type is STYLE_SCALE or STYLE_FOLLOW_SCALE, you must set a accurate height");
        }
        if (customHeight == -1) {
            customHeight = Math.max(0, View.MeasureSpec.getSize(i3) - (((getPaddingTop() + getPaddingBottom()) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
            this.mIndicatorSetter.setFooterHeight(customHeight);
        } else {
            this.mIndicatorSetter.setFooterHeight(((ViewGroup.MarginLayoutParams) layoutParams).topMargin + customHeight + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }
        if (this.mFooterView.getStyle() == 3 && this.mIndicator.getCurrentPos() <= this.mIndicator.getFooterHeight()) {
            ((ViewGroup.MarginLayoutParams) layoutParams).height = customHeight;
            measureChildWithMargins(view, i2, 0, i3, 0);
            return;
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width);
        if (isMovingFooter()) {
            int iMin = Math.min((this.mIndicator.getCurrentPos() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, (((View.MeasureSpec.getSize(i3) - getPaddingTop()) - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin > 0 ? iMin : 0, 1073741824);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
        }
        view.measure(childMeasureSpec, iMakeMeasureSpec);
    }

    public void measureHeader(View view, LayoutParams layoutParams, int i2, int i3) {
        int iMakeMeasureSpec;
        if (isDisabledRefresh()) {
            return;
        }
        int customHeight = this.mHeaderView.getCustomHeight();
        if (this.mHeaderView.getStyle() == 0 || this.mHeaderView.getStyle() == 2 || this.mHeaderView.getStyle() == 5 || this.mHeaderView.getStyle() == 4) {
            if (customHeight > 0) {
                ((ViewGroup.MarginLayoutParams) layoutParams).height = customHeight;
            } else if (customHeight == -1) {
                ((ViewGroup.MarginLayoutParams) layoutParams).height = -1;
            }
            measureChildWithMargins(view, i2, 0, i3, 0);
            this.mIndicatorSetter.setHeaderHeight(view.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            return;
        }
        if (customHeight <= 0 && customHeight != -1) {
            throw new IllegalArgumentException("If header view type is STYLE_SCALE or STYLE_FOLLOW_SCALE, you must set a accurate height");
        }
        if (customHeight == -1) {
            customHeight = Math.max(0, View.MeasureSpec.getSize(i3) - (((getPaddingTop() + getPaddingBottom()) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
            this.mIndicatorSetter.setHeaderHeight(customHeight);
        } else {
            this.mIndicatorSetter.setHeaderHeight(((ViewGroup.MarginLayoutParams) layoutParams).topMargin + customHeight + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }
        if (this.mHeaderView.getStyle() == 3 && this.mIndicator.getCurrentPos() <= this.mIndicator.getHeaderHeight()) {
            ((ViewGroup.MarginLayoutParams) layoutParams).height = customHeight;
            measureChildWithMargins(view, i2, 0, i3, 0);
            return;
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width);
        if (isMovingHeader()) {
            int iMin = Math.min((this.mIndicator.getCurrentPos() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, (((View.MeasureSpec.getSize(i3) - getPaddingTop()) - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin > 0 ? iMin : 0, 1073741824);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
        }
        view.measure(childMeasureSpec, iMakeMeasureSpec);
    }

    public void moveFooterPos(float f2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("moveFooterPos(): delta: %s", Float.valueOf(f2)));
        }
        if (!this.mNestedScrolling && !this.mHasSendCancelEvent && isEnabledOldTouchHandling() && this.mIndicator.hasTouched() && !this.mIndicator.isAlreadyHere(0)) {
            sendCancelEvent(null);
        }
        this.mIndicatorSetter.setMovingStatus(1);
        if (f2 < 0.0f) {
            float canMoveTheMaxDistanceOfFooter = this.mIndicator.getCanMoveTheMaxDistanceOfFooter();
            int currentPos = this.mIndicator.getCurrentPos();
            boolean z2 = this.mScrollChecker.isFling() || this.mScrollChecker.isPreFling();
            if (canMoveTheMaxDistanceOfFooter > 0.0f) {
                float f3 = currentPos;
                if (f3 >= canMoveTheMaxDistanceOfFooter) {
                    if ((this.mIndicator.hasTouched() && !this.mScrollChecker.$IsScrolling) || z2) {
                        updateAnotherDirectionPos();
                        return;
                    }
                } else if (f3 - f2 > canMoveTheMaxDistanceOfFooter && ((this.mIndicator.hasTouched() && !this.mScrollChecker.$IsScrolling) || z2)) {
                    f2 = f3 - canMoveTheMaxDistanceOfFooter;
                    if (z2) {
                        this.mScrollChecker.$Scroller.forceFinished(true);
                    }
                }
            }
        } else if ((this.mFlag & 8388608) > 0 && !isEnabledPinContentView() && this.mIsLastRefreshSuccessful && ((!this.mIndicator.hasTouched() || this.mNestedScrolling || isEnabledSmoothRollbackWhenCompleted()) && this.mStatus == 5)) {
            if (sDebug) {
                Log.d(this.TAG, String.format("moveFooterPos(): compatible scroll delta: %s", Float.valueOf(f2)));
            }
            this.mNeedFilterScrollEvent = true;
            View view = this.mScrollTargetView;
            if (view != null) {
                compatLoadMoreScroll(view, f2);
            }
            View view2 = this.mAutoFoundScrollTargetView;
            if (view2 != null) {
                compatLoadMoreScroll(view2, f2);
            } else {
                View view3 = this.mTargetView;
                if (view3 != null) {
                    compatLoadMoreScroll(view3, f2);
                }
            }
        }
        movePos(-f2);
    }

    public void moveHeaderPos(float f2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("moveHeaderPos(): delta: %s", Float.valueOf(f2)));
        }
        if (!this.mNestedScrolling && !this.mHasSendCancelEvent && isEnabledOldTouchHandling() && this.mIndicator.hasTouched() && !this.mIndicator.isAlreadyHere(0)) {
            sendCancelEvent(null);
        }
        this.mIndicatorSetter.setMovingStatus(2);
        float canMoveTheMaxDistanceOfHeader = this.mIndicator.getCanMoveTheMaxDistanceOfHeader();
        int currentPos = this.mIndicator.getCurrentPos();
        boolean z2 = this.mScrollChecker.isFling() || this.mScrollChecker.isPreFling();
        if (canMoveTheMaxDistanceOfHeader > 0.0f && f2 > 0.0f) {
            float f3 = currentPos;
            if (f3 >= canMoveTheMaxDistanceOfHeader) {
                if ((this.mIndicator.hasTouched() && !this.mScrollChecker.$IsScrolling) || z2) {
                    updateAnotherDirectionPos();
                    return;
                }
            } else if (f3 + f2 > canMoveTheMaxDistanceOfHeader && ((this.mIndicator.hasTouched() && !this.mScrollChecker.$IsScrolling) || z2)) {
                f2 = canMoveTheMaxDistanceOfHeader - f3;
                if (z2) {
                    this.mScrollChecker.$Scroller.forceFinished(true);
                }
            }
        }
        movePos(f2);
    }

    public void movePos(float f2) {
        if (f2 == 0.0f) {
            if (sDebug) {
                Log.d(this.TAG, "movePos(): delta is zero");
            }
            this.mIndicatorSetter.setCurrentPos(this.mIndicator.getCurrentPos());
            return;
        }
        if (f2 <= 0.0f || this.mMode != 1 || calculateScale() < 1.2f) {
            int currentPos = this.mIndicator.getCurrentPos() + Math.round(f2);
            if (!this.mScrollChecker.$IsScrolling && currentPos < 0) {
                if (sDebug) {
                    Log.d(this.TAG, "movePos(): over top");
                }
                currentPos = 0;
            }
            this.mIndicatorSetter.setCurrentPos(currentPos);
            int lastPos = currentPos - this.mIndicator.getLastPos();
            if (getParent() != null && this.mIndicator.hasTouched()) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (isMovingHeader()) {
                updatePos(lastPos);
            } else if (isMovingFooter()) {
                updatePos(-lastPos);
            }
        }
    }

    public void notifyFingerUp() {
        if (this.mHeaderView != null && isHeaderInProcessing() && !isDisabledRefresh()) {
            this.mHeaderView.onFingerUp(this, this.mIndicator);
        } else {
            if (this.mFooterView == null || !isFooterInProcessing() || isDisabledLoadMore()) {
                return;
            }
            this.mFooterView.onFingerUp(this, this.mIndicator);
        }
    }

    public void notifyUIRefreshComplete(boolean z2, boolean z3) {
        IRefreshView<IIndicator> iRefreshView;
        IRefreshView<IIndicator> iRefreshView2;
        this.mIsSpringBackCanNotBeInterrupted = isEnabledSmoothRollbackWhenCompleted();
        if (z3) {
            if (isHeaderInProcessing() && (iRefreshView2 = this.mHeaderView) != null) {
                iRefreshView2.onRefreshComplete(this, this.mIsLastRefreshSuccessful);
            } else if (isFooterInProcessing() && (iRefreshView = this.mFooterView) != null) {
                iRefreshView.onRefreshComplete(this, this.mIsLastRefreshSuccessful);
            }
        }
        if (z2) {
            tryScrollBackToTopByPercentDuration();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0137  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean offsetChild(int r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 609
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: me.dkzwm.widget.srl.SmoothRefreshLayout.offsetChild(int, boolean, boolean):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (sDebug) {
            Log.d(this.TAG, "onAttachedToWindow()");
        }
        ArrayList<ILifecycleObserver> arrayList = this.mLifecycleObservers;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<ILifecycleObserver> it = this.mLifecycleObservers.iterator();
            while (it.hasNext()) {
                it.next().onAttached(this);
            }
        }
        AppBarUtil appBarUtil = this.mAppBarUtil;
        if (appBarUtil != null) {
            appBarUtil.onAttached(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ArrayList<ILifecycleObserver> arrayList = this.mLifecycleObservers;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<ILifecycleObserver> it = this.mLifecycleObservers.iterator();
            while (it.hasNext()) {
                it.next().onDetached(this);
            }
        }
        AppBarUtil appBarUtil = this.mAppBarUtil;
        if (appBarUtil != null && appBarUtil.hasFound()) {
            OnHeaderEdgeDetectCallBack onHeaderEdgeDetectCallBack = this.mInEdgeCanMoveHeaderCallBack;
            AppBarUtil appBarUtil2 = this.mAppBarUtil;
            if (onHeaderEdgeDetectCallBack == appBarUtil2) {
                this.mInEdgeCanMoveHeaderCallBack = null;
            }
            if (this.mInEdgeCanMoveFooterCallBack == appBarUtil2) {
                this.mInEdgeCanMoveFooterCallBack = null;
            }
            appBarUtil2.onDetached(this);
        }
        reset();
        RefreshCompleteHook refreshCompleteHook = this.mHeaderRefreshCompleteHook;
        if (refreshCompleteHook != null) {
            refreshCompleteHook.mLayout = null;
        }
        RefreshCompleteHook refreshCompleteHook2 = this.mFooterRefreshCompleteHook;
        if (refreshCompleteHook2 != null) {
            refreshCompleteHook2.mLayout = null;
        }
        if (sDebug) {
            Log.d(this.TAG, "onDetachedFromWindow()");
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int i2;
        int i3;
        if (this.mMode != 0 || this.mBackgroundPaint == null || isEnabledPinContentView() || this.mIndicator.isAlreadyHere(0)) {
            return;
        }
        if (!isDisabledRefresh() && isMovingHeader() && (i3 = this.mHeaderBackgroundColor) != 0) {
            this.mBackgroundPaint.setColor(i3);
            drawHeaderBackground(canvas);
        } else {
            if (isDisabledLoadMore() || !isMovingFooter() || (i2 = this.mFooterBackgroundColor) == 0) {
                return;
            }
            this.mBackgroundPaint.setColor(i2);
            drawFooterBackground(canvas);
        }
    }

    public void onFingerUp() {
        if (sDebug) {
            Log.d(this.TAG, "onFingerUp()");
        }
        notifyFingerUp();
        if (this.mMode == 0 && ((!isEnabledNoMoreData() || !isMovingFooter()) && !this.mScrollChecker.isPreFling() && isEnabledKeepRefreshView() && this.mStatus != 5)) {
            if (isHeaderInProcessing() && !isDisabledPerformRefresh() && isMovingHeader() && this.mIndicator.isOverOffsetToKeepHeaderWhileLoading()) {
                IIndicator iIndicator = this.mIndicator;
                if (!iIndicator.isAlreadyHere(iIndicator.getOffsetToKeepHeaderWhileLoading())) {
                    this.mScrollChecker.scrollTo(this.mIndicator.getOffsetToKeepHeaderWhileLoading(), this.mDurationOfBackToHeaderHeight);
                    return;
                }
            } else if (isFooterInProcessing() && !isDisabledPerformLoadMore() && isMovingFooter() && this.mIndicator.isOverOffsetToKeepFooterWhileLoading()) {
                IIndicator iIndicator2 = this.mIndicator;
                if (!iIndicator2.isAlreadyHere(iIndicator2.getOffsetToKeepFooterWhileLoading())) {
                    this.mScrollChecker.scrollTo(this.mIndicator.getOffsetToKeepFooterWhileLoading(), this.mDurationOfBackToFooterHeight);
                    return;
                }
            }
        }
        if (this.mScrollChecker.isPreFling()) {
            return;
        }
        onRelease();
    }

    public boolean onFling(float f2, float f3, boolean z2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("onFling() velocityX: %s, velocityY: %s, nested: %s", Float.valueOf(f2), Float.valueOf(f3), Boolean.valueOf(z2)));
        }
        if (isNeedInterceptTouchEvent() || isCanNotAbortOverScrolling()) {
            return true;
        }
        if (this.mPreventForAnotherDirection) {
            return z2 && dispatchNestedPreFling(-f2, -f3);
        }
        float f4 = isVerticalOrientation() ? f3 : f2;
        boolean z3 = !isNotYetInEdgeCannotMoveFooter();
        boolean z4 = !isNotYetInEdgeCannotMoveHeader();
        if (this.mIndicator.isAlreadyHere(0)) {
            tryToResetMovingStatus();
            if (isEnabledOverScroll() && (!isEnabledPinRefreshViewWhileLoading() || ((f4 >= 0.0f || !isDisabledLoadMore()) && (f4 <= 0.0f || !isDisabledRefresh())))) {
                if (isDisabledLoadMoreWhenContentNotFull() && f4 < 0.0f && z3 && z4) {
                    return z2 && dispatchNestedPreFling(-f2, -f3);
                }
                this.mScrollChecker.startFling(f4);
                if (!z2 && isEnabledOldTouchHandling()) {
                    if (this.mDelayToDispatchNestedFling == null) {
                        this.mDelayToDispatchNestedFling = new DelayToDispatchNestedFling();
                    }
                    this.mDelayToDispatchNestedFling.mLayoutWeakRf = new WeakReference(this);
                    this.mDelayToDispatchNestedFling.mVelocity = (int) f4;
                    ViewCompat.postOnAnimation(this, this.mDelayToDispatchNestedFling);
                    invalidate();
                    return true;
                }
            }
            invalidate();
            return z2 && dispatchNestedPreFling(-f2, -f3);
        }
        if (isEnabledPinRefreshViewWhileLoading()) {
            if (z2) {
                return dispatchNestedPreFling(-f2, -f3);
            }
            return true;
        }
        if (Math.abs(f4) > 2000.0f) {
            if ((f4 <= 0.0f || !isMovingHeader()) && (f4 >= 0.0f || !isMovingFooter())) {
                if (this.mScrollChecker.getFinalY(f4) > this.mIndicator.getCurrentPos()) {
                    if (this.mMode != 0 || !isEnabledPerformFreshWhenFling()) {
                        this.mScrollChecker.startPreFling(f4);
                    } else if (isMovingHeader() && (isDisabledPerformRefresh() || this.mIndicator.getCurrentPos() < this.mIndicator.getOffsetToRefresh())) {
                        this.mScrollChecker.startPreFling(f4);
                    } else if (isMovingFooter() && (isDisabledPerformLoadMore() || this.mIndicator.getCurrentPos() < this.mIndicator.getOffsetToLoadMore())) {
                        this.mScrollChecker.startPreFling(f4);
                    }
                }
            } else {
                if (!isEnabledOverScroll() || (isDisabledLoadMoreWhenContentNotFull() && z3 && z4)) {
                    return true;
                }
                boolean z5 = f4 < 0.0f;
                float fPow = (float) Math.pow(Math.abs(f4), 0.5d);
                ScrollChecker scrollChecker = this.mScrollChecker;
                if (z5) {
                    fPow = -fPow;
                }
                scrollChecker.startPreFling(fPow);
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        View view;
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        checkViewsZAxisNeedReset();
        this.mIndicator.checkConfig();
        int paddingRight = (i4 - i2) - getPaddingRight();
        int paddingBottom = (i5 - i3) - getPaddingBottom();
        int iLayoutContentView = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
                if (iRefreshView == null || childAt != iRefreshView.getView()) {
                    View view2 = this.mTargetView;
                    if (view2 == null || childAt != view2) {
                        View view3 = this.mStickyHeaderView;
                        if (view3 == null || childAt != view3) {
                            IRefreshView<IIndicator> iRefreshView2 = this.mFooterView;
                            if ((iRefreshView2 == null || iRefreshView2.getView() != childAt) && ((view = this.mStickyFooterView) == null || view != childAt)) {
                                layoutOtherView(childAt, paddingRight, paddingBottom);
                            }
                        } else {
                            layoutStickyHeaderView(childAt);
                        }
                    } else {
                        iLayoutContentView = layoutContentView(childAt);
                    }
                } else {
                    layoutHeaderView(childAt);
                }
            }
        }
        IRefreshView<IIndicator> iRefreshView3 = this.mFooterView;
        if (iRefreshView3 != null && iRefreshView3.getView().getVisibility() != 8) {
            layoutFooterView(this.mFooterView.getView(), iLayoutContentView);
        }
        View view4 = this.mStickyFooterView;
        if (view4 != null && view4.getVisibility() != 8) {
            layoutStickyFooterView(this.mStickyFooterView, iLayoutContentView);
        }
        tryToPerformAutoRefresh();
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        LayoutParams layoutParams;
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        ensureTargetView();
        this.mCachedViews.clear();
        int iMax = 0;
        int iMax2 = 0;
        int iCombineMeasuredStates = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
                if (iRefreshView == null || childAt != iRefreshView.getView()) {
                    IRefreshView<IIndicator> iRefreshView2 = this.mFooterView;
                    if (iRefreshView2 == null || childAt != iRefreshView2.getView()) {
                        layoutParams = layoutParams2;
                        measureChildWithMargins(childAt, i2, 0, i3, 0);
                        if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1 || ((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
                            this.mCachedViews.add(childAt);
                        }
                        iMax = Math.max(iMax, childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                        iMax2 = Math.max(iMax2, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
                    } else {
                        measureFooter(childAt, layoutParams2, i2, i3);
                    }
                } else {
                    measureHeader(childAt, layoutParams2, i2, i3);
                }
                layoutParams = layoutParams2;
                iMax = Math.max(iMax, childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                iMax2 = Math.max(iMax2, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i2, iCombineMeasuredStates), View.resolveSizeAndState(Math.max(iMax2 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i3, iCombineMeasuredStates << 16));
        int size = this.mCachedViews.size();
        if (size > 1) {
            for (int i5 = 0; i5 < size; i5++) {
                View view = this.mCachedViews.get(i5);
                int[] iArrMeasureChildAgain = measureChildAgain((LayoutParams) view.getLayoutParams(), i2, i3);
                view.measure(iArrMeasureChildAgain[0], iArrMeasureChildAgain[1]);
            }
        }
        this.mCachedViews.clear();
        if (View.MeasureSpec.getMode(i2) == 1073741824 && View.MeasureSpec.getMode(i3) == 1073741824) {
            return;
        }
        IRefreshView<IIndicator> iRefreshView3 = this.mHeaderView;
        if (iRefreshView3 != null && iRefreshView3.getView().getVisibility() != 8) {
            View view2 = this.mHeaderView.getView();
            LayoutParams layoutParams3 = (LayoutParams) view2.getLayoutParams();
            int[] iArrMeasureChildAgain2 = measureChildAgain(layoutParams3, i2, i3);
            measureHeader(view2, layoutParams3, iArrMeasureChildAgain2[0], iArrMeasureChildAgain2[1]);
        }
        IRefreshView<IIndicator> iRefreshView4 = this.mFooterView;
        if (iRefreshView4 == null || iRefreshView4.getView().getVisibility() == 8) {
            return;
        }
        View view3 = this.mFooterView.getView();
        LayoutParams layoutParams4 = (LayoutParams) view3.getLayoutParams();
        int[] iArrMeasureChildAgain3 = measureChildAgain(layoutParams4, i2, i3);
        measureFooter(view3, layoutParams4, iArrMeasureChildAgain3[0], iArrMeasureChildAgain3[1]);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f2, float f3, boolean z2) {
        return dispatchNestedFling(f2, f3, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f2, float f3) {
        return onFling(-f2, -f3, true);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(@NonNull View view, int i2, int i3, @NonNull int[] iArr) {
        onNestedPreScroll(view, i2, i3, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i2, i3, i4, i5, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2) {
        onNestedScrollAccepted(view, view2, i2, 0);
    }

    public void onNestedScrollChanged() {
        if (this.mNeedFilterScrollEvent) {
            this.mNeedFilterScrollEvent = false;
            return;
        }
        tryScrollToPerformAutoRefresh();
        notifyNestedScrollChanged();
        this.mScrollChecker.computeScrollOffset();
    }

    public void onRelease() {
        if (sDebug) {
            Log.d(this.TAG, "onRelease()");
        }
        if (this.mMode == 0) {
            if (isEnabledNoMoreData() && isMovingFooter() && isEnabledNoSpringBackWhenNoMoreData()) {
                this.mScrollChecker.stop();
                return;
            }
            tryToPerformRefresh();
            if (this.mStatus == 5) {
                notifyUIRefreshComplete(true, false);
                return;
            }
            if (isEnabledKeepRefreshView()) {
                if (isHeaderInProcessing() && this.mHeaderView != null && !isDisabledPerformRefresh()) {
                    if (isRefreshing() && isMovingHeader()) {
                        IIndicator iIndicator = this.mIndicator;
                        if (iIndicator.isAlreadyHere(iIndicator.getOffsetToKeepHeaderWhileLoading())) {
                            return;
                        }
                    }
                    if (isMovingHeader() && this.mIndicator.isOverOffsetToKeepHeaderWhileLoading()) {
                        this.mScrollChecker.scrollTo(this.mIndicator.getOffsetToKeepHeaderWhileLoading(), this.mDurationOfBackToHeaderHeight);
                        return;
                    } else if (isRefreshing() && !isMovingFooter()) {
                        return;
                    }
                } else if (isFooterInProcessing() && this.mFooterView != null && !isDisabledPerformLoadMore()) {
                    if (isLoadingMore() && isMovingFooter()) {
                        IIndicator iIndicator2 = this.mIndicator;
                        if (iIndicator2.isAlreadyHere(iIndicator2.getOffsetToKeepFooterWhileLoading())) {
                            return;
                        }
                    }
                    if (isMovingFooter() && this.mIndicator.isOverOffsetToKeepFooterWhileLoading()) {
                        this.mScrollChecker.scrollTo(this.mIndicator.getOffsetToKeepFooterWhileLoading(), this.mDurationOfBackToFooterHeight);
                        return;
                    } else if (isLoadingMore() && !isMovingHeader()) {
                        return;
                    }
                }
            }
        }
        tryScrollBackToTopByPercentDuration();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2) {
        return onStartNestedScroll(view, view2, i2, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(@NonNull View view) {
        onStopNestedScroll(view, 0);
    }

    public void performRefresh(boolean z2) {
        IRefreshView<IIndicator> iRefreshView;
        long jUptimeMillis = SystemClock.uptimeMillis();
        this.mLoadingStartTime = jUptimeMillis;
        if (sDebug) {
            Log.d(this.TAG, String.format("onRefreshBegin systemTime: %s", Long.valueOf(jUptimeMillis)));
        }
        if (isRefreshing()) {
            IRefreshView<IIndicator> iRefreshView2 = this.mHeaderView;
            if (iRefreshView2 != null) {
                iRefreshView2.onRefreshBegin(this, this.mIndicator);
            }
        } else if (isLoadingMore() && (iRefreshView = this.mFooterView) != null) {
            iRefreshView.onRefreshBegin(this, this.mIndicator);
        }
        if (!z2 || this.mRefreshListener == null) {
            return;
        }
        if (isRefreshing()) {
            this.mRefreshListener.onRefreshing();
        } else {
            this.mRefreshListener.onLoadingMore();
        }
    }

    public void performRefreshComplete(boolean z2, boolean z3) {
        RefreshCompleteHook refreshCompleteHook;
        RefreshCompleteHook refreshCompleteHook2;
        if (isRefreshing() && z2 && (refreshCompleteHook2 = this.mHeaderRefreshCompleteHook) != null && refreshCompleteHook2.mCallBack != null) {
            this.mHeaderRefreshCompleteHook.mLayout = this;
            this.mHeaderRefreshCompleteHook.mNotifyViews = z3;
            this.mHeaderRefreshCompleteHook.doHook();
        } else if (isLoadingMore() && z2 && (refreshCompleteHook = this.mFooterRefreshCompleteHook) != null && refreshCompleteHook.mCallBack != null) {
            this.mFooterRefreshCompleteHook.mLayout = this;
            this.mFooterRefreshCompleteHook.mNotifyViews = z3;
            this.mFooterRefreshCompleteHook.doHook();
        } else {
            byte b3 = this.mStatus;
            this.mStatus = (byte) 5;
            notifyStatusChanged(b3, (byte) 5);
            notifyUIRefreshComplete((isMovingFooter() && isEnabledNoMoreData() && isEnabledNoSpringBackWhenNoMoreData()) ? false : true, z3);
        }
    }

    public void preparePaint() {
        if (this.mBackgroundPaint != null || this.mMode == 1 || (this.mHeaderBackgroundColor == 0 && this.mFooterBackgroundColor == 0)) {
            this.mBackgroundPaint = null;
            setWillNotDraw(true);
        } else {
            Paint paint = new Paint(1);
            this.mBackgroundPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            setWillNotDraw(false);
        }
    }

    public boolean processDispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        int i2 = 0;
        if (sDebug) {
            Log.d(this.TAG, String.format("processDispatchTouchEvent(): action: %s", Integer.valueOf(action)));
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        boolean zIsEnabledOldTouchHandling = isEnabledOldTouchHandling();
        if (action == 0) {
            this.mIndicatorSetter.onFingerUp();
            this.mTouchPointerId = motionEvent.getPointerId(0);
            this.mIndicatorSetter.onFingerDown(motionEvent.getX(), motionEvent.getY());
            this.mIsFingerInsideAnotherDirectionView = isDisabledWhenAnotherDirectionMove() && (!isEnableCheckInsideAnotherDirectionView() || isInsideAnotherDirectionView(motionEvent.getRawX(), motionEvent.getRawY()));
            this.mIsInterceptTouchEventInOnceTouch = isNeedInterceptTouchEvent();
            this.mIsLastOverScrollCanNotAbort = isCanNotAbortOverScrolling();
            if (!isNeedFilterTouchEvent()) {
                this.mScrollChecker.stop();
            }
            this.mHasSendDownEvent = false;
            this.mPreventForAnotherDirection = false;
            if (this.mScrollTargetView == null && isEnabledDynamicEnsureTargetView()) {
                View viewEnsureScrollTargetView = ensureScrollTargetView(this, false, motionEvent.getX(), motionEvent.getY());
                if (viewEnsureScrollTargetView != null && this.mTargetView != viewEnsureScrollTargetView && this.mAutoFoundScrollTargetView != viewEnsureScrollTargetView) {
                    this.mAutoFoundScrollTargetView = viewEnsureScrollTargetView;
                }
            } else {
                AppBarUtil appBarUtil = this.mAppBarUtil;
                if (appBarUtil == null || !appBarUtil.hasFound()) {
                    this.mAutoFoundScrollTargetView = null;
                }
            }
            removeCallbacks(this.mDelayToDispatchNestedFling);
            dispatchTouchEventSuper(motionEvent);
            return true;
        }
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    if (action == 5) {
                        this.mTouchPointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                        this.mIndicatorSetter.onFingerMove(motionEvent.getX(motionEvent.getActionIndex()), motionEvent.getY(motionEvent.getActionIndex()));
                    } else if (action == 6) {
                        int action2 = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                        if (motionEvent.getPointerId(action2) == this.mTouchPointerId) {
                            int i3 = action2 != 0 ? 0 : 1;
                            this.mTouchPointerId = motionEvent.getPointerId(i3);
                            this.mIndicatorSetter.onFingerMove(motionEvent.getX(i3), motionEvent.getY(i3));
                        }
                        int pointerCount = motionEvent.getPointerCount();
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.addMovement(motionEvent);
                            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                            int actionIndex = motionEvent.getActionIndex();
                            int pointerId = motionEvent.getPointerId(actionIndex);
                            float xVelocity = this.mVelocityTracker.getXVelocity(pointerId);
                            float yVelocity = this.mVelocityTracker.getYVelocity(pointerId);
                            while (true) {
                                if (i2 >= pointerCount) {
                                    break;
                                }
                                if (i2 != actionIndex) {
                                    int pointerId2 = motionEvent.getPointerId(i2);
                                    if ((this.mVelocityTracker.getXVelocity(pointerId2) * xVelocity) + (this.mVelocityTracker.getYVelocity(pointerId2) * yVelocity) < 0.0f) {
                                        this.mVelocityTracker.clear();
                                        break;
                                    }
                                }
                                i2++;
                            }
                        }
                    }
                }
            } else {
                if (!this.mIndicator.hasTouched()) {
                    return super.dispatchTouchEvent(motionEvent);
                }
                int iFindPointerIndex = motionEvent.findPointerIndex(this.mTouchPointerId);
                if (iFindPointerIndex < 0) {
                    Log.e(this.TAG, "Error processing scroll; pointer index for id " + this.mTouchPointerId + " not found. Did any MotionEvents get skipped?");
                    return super.dispatchTouchEvent(motionEvent);
                }
                this.mLastMoveEvent = motionEvent;
                if (tryToFilterTouchEvent(motionEvent)) {
                    return true;
                }
                tryToResetMovingStatus();
                if (!this.mDealAnotherDirectionMove) {
                    float[] fingerDownPoint = this.mIndicator.getFingerDownPoint();
                    float x2 = motionEvent.getX(iFindPointerIndex) - fingerDownPoint[0];
                    float y2 = motionEvent.getY(iFindPointerIndex) - fingerDownPoint[1];
                    tryToDealAnotherDirectionMove(x2, y2);
                    if (this.mDealAnotherDirectionMove) {
                        this.mIndicatorSetter.onFingerDown(motionEvent.getX(iFindPointerIndex) + (x2 / 10.0f), motionEvent.getY(iFindPointerIndex) + (y2 / 10.0f));
                    }
                }
                boolean z2 = !isNotYetInEdgeCannotMoveFooter();
                boolean z3 = !isNotYetInEdgeCannotMoveHeader();
                if (this.mPreventForAnotherDirection) {
                    if (this.mDealAnotherDirectionMove && isMovingHeader() && !z3) {
                        this.mPreventForAnotherDirection = false;
                    } else {
                        if (!this.mDealAnotherDirectionMove || !isMovingFooter() || z2) {
                            return super.dispatchTouchEvent(motionEvent);
                        }
                        this.mPreventForAnotherDirection = false;
                    }
                }
                this.mIndicatorSetter.onFingerMove(motionEvent.getX(iFindPointerIndex), motionEvent.getY(iFindPointerIndex));
                float offset = this.mIndicator.getOffset();
                boolean z4 = offset > 0.0f;
                if (isMovingFooter() && isFooterInProcessing() && this.mStatus == 5 && this.mIndicator.hasLeftStartPosition() && !z2) {
                    this.mScrollChecker.scrollTo(0, 0);
                    if (zIsEnabledOldTouchHandling) {
                        return true;
                    }
                    return dispatchTouchEventSuper(motionEvent);
                }
                if (!z4 && isDisabledLoadMoreWhenContentNotFull() && this.mIndicator.isAlreadyHere(0) && z2 && z3) {
                    return dispatchTouchEventSuper(motionEvent);
                }
                boolean z5 = isMovingHeader() && this.mIndicator.hasLeftStartPosition();
                boolean z6 = isMovingFooter() && this.mIndicator.hasLeftStartPosition();
                boolean z7 = z3 && !isDisabledRefresh();
                if (z2 && !isDisabledLoadMore()) {
                    i2 = 1;
                }
                if (z5 || z6) {
                    if (z5) {
                        if (isDisabledRefresh()) {
                            return dispatchTouchEventSuper(motionEvent);
                        }
                        if (!z7 && z4) {
                            if (zIsEnabledOldTouchHandling) {
                                sendDownEvent(motionEvent);
                            }
                            return dispatchTouchEventSuper(motionEvent);
                        }
                        moveHeaderPos(offset);
                        if (zIsEnabledOldTouchHandling) {
                            return true;
                        }
                    } else {
                        if (isDisabledLoadMore()) {
                            return dispatchTouchEventSuper(motionEvent);
                        }
                        if (i2 == 0 && !z4) {
                            if (zIsEnabledOldTouchHandling) {
                                sendDownEvent(motionEvent);
                            }
                            return dispatchTouchEventSuper(motionEvent);
                        }
                        moveFooterPos(offset);
                        if (zIsEnabledOldTouchHandling) {
                            return true;
                        }
                    }
                } else if ((!z4 || z7) && (z4 || i2 != 0)) {
                    if (z4) {
                        if (!isDisabledRefresh()) {
                            moveHeaderPos(offset);
                            if (zIsEnabledOldTouchHandling) {
                                return true;
                            }
                        }
                    } else if (!isDisabledLoadMore()) {
                        moveFooterPos(offset);
                        if (zIsEnabledOldTouchHandling) {
                            return true;
                        }
                    }
                } else if (isLoadingMore() && this.mIndicator.hasLeftStartPosition()) {
                    moveFooterPos(offset);
                    if (zIsEnabledOldTouchHandling) {
                        return true;
                    }
                } else if (isRefreshing() && this.mIndicator.hasLeftStartPosition()) {
                    moveHeaderPos(offset);
                    if (zIsEnabledOldTouchHandling) {
                        return true;
                    }
                }
            }
            return dispatchTouchEventSuper(motionEvent);
        }
        int pointerId3 = motionEvent.getPointerId(0);
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
        float yVelocity2 = this.mVelocityTracker.getYVelocity(pointerId3);
        float xVelocity2 = this.mVelocityTracker.getXVelocity(pointerId3);
        if ((Math.abs(xVelocity2) >= this.mMinimumFlingVelocity || Math.abs(yVelocity2) >= this.mMinimumFlingVelocity) && onFling(xVelocity2, yVelocity2, false)) {
            motionEvent.setAction(3);
        }
        this.mIsFingerInsideAnotherDirectionView = false;
        this.mIndicatorSetter.onFingerUp();
        this.mPreventForAnotherDirection = false;
        this.mDealAnotherDirectionMove = false;
        if (isNeedFilterTouchEvent()) {
            this.mIsInterceptTouchEventInOnceTouch = false;
            if (this.mIsLastOverScrollCanNotAbort && this.mIndicator.isAlreadyHere(0)) {
                this.mScrollChecker.stop();
            }
            this.mIsLastOverScrollCanNotAbort = false;
        } else {
            this.mIsInterceptTouchEventInOnceTouch = false;
            this.mIsLastOverScrollCanNotAbort = false;
            if (this.mIndicator.hasLeftStartPosition()) {
                onFingerUp();
            } else {
                notifyFingerUp();
            }
        }
        this.mHasSendCancelEvent = false;
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
        }
        this.mVelocityTracker = null;
        return dispatchTouchEventSuper(motionEvent);
    }

    public final void refreshComplete() {
        refreshComplete(true);
    }

    public void removeLifecycleObserver(@NonNull ILifecycleObserver iLifecycleObserver) {
        ArrayList<ILifecycleObserver> arrayList = this.mLifecycleObservers;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.mLifecycleObservers.remove(iLifecycleObserver);
    }

    public void removeOnNestedScrollChangedListener(@NonNull OnNestedScrollChangedListener onNestedScrollChangedListener) {
        ArrayList<OnNestedScrollChangedListener> arrayList = this.mNestedScrollChangedListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.mNestedScrollChangedListeners.remove(onNestedScrollChangedListener);
    }

    public void removeOnStatusChangedListener(@NonNull OnStatusChangedListener onStatusChangedListener) {
        ArrayList<OnStatusChangedListener> arrayList = this.mStatusChangedListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.mStatusChangedListeners.remove(onStatusChangedListener);
    }

    public void removeOnUIPositionChangedListener(@NonNull OnUIPositionChangedListener onUIPositionChangedListener) {
        ArrayList<OnUIPositionChangedListener> arrayList = this.mUIPositionChangedListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.mUIPositionChangedListeners.remove(onUIPositionChangedListener);
    }

    public void reset() {
        if (isRefreshing() || isLoadingMore()) {
            notifyUIRefreshComplete(false, true);
        }
        if (!this.mIndicator.isAlreadyHere(0)) {
            this.mScrollChecker.scrollTo(0, 0);
        }
        this.mScrollChecker.setInterpolator(this.mSpringInterpolator);
        byte b3 = this.mStatus;
        this.mStatus = (byte) 1;
        notifyStatusChanged(b3, (byte) 1);
        this.mAutomaticActionTriggered = true;
        this.mScrollChecker.stop();
        DelayToRefreshComplete delayToRefreshComplete = this.mDelayToRefreshComplete;
        if (delayToRefreshComplete != null) {
            removeCallbacks(delayToRefreshComplete);
        }
        if (sDebug) {
            Log.d(this.TAG, "reset()");
        }
    }

    public void resetScrollerInterpolator() {
        Interpolator interpolator = this.mSpringInterpolator;
        Interpolator interpolator2 = sSpringInterpolator;
        if (interpolator != interpolator2) {
            setSpringInterpolator(interpolator2);
        }
        Interpolator interpolator3 = this.mSpringBackInterpolator;
        Interpolator interpolator4 = sSpringBackInterpolator;
        if (interpolator3 != interpolator4) {
            setSpringBackInterpolator(interpolator4);
        }
    }

    public void resetViewScale(View view) {
        if (!ScrollCompat.canScaleInternal(view)) {
            view.setPivotY(0.0f);
            view.setScaleY(1.0f);
        } else {
            View childAt = ((ViewGroup) view).getChildAt(0);
            childAt.setPivotY(0.0f);
            childAt.setScaleY(1.0f);
        }
    }

    public void sendCancelEvent(MotionEvent motionEvent) {
        if (this.mHasSendCancelEvent) {
            return;
        }
        if (motionEvent == null && this.mLastMoveEvent == null) {
            return;
        }
        if (sDebug) {
            Log.d(this.TAG, "sendCancelEvent()");
        }
        if (motionEvent == null) {
            motionEvent = this.mLastMoveEvent;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), 3, motionEvent.getX(), motionEvent.getY(), motionEvent.getMetaState());
        this.mHasSendCancelEvent = true;
        this.mHasSendDownEvent = false;
        super.dispatchTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public void sendDownEvent(MotionEvent motionEvent) {
        if (this.mHasSendDownEvent) {
            return;
        }
        if (motionEvent == null && this.mLastMoveEvent == null) {
            return;
        }
        if (sDebug) {
            Log.d(this.TAG, "sendDownEvent()");
        }
        if (motionEvent == null) {
            motionEvent = this.mLastMoveEvent;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), 0, motionEvent.getX(), motionEvent.getY(), motionEvent.getMetaState());
        this.mHasSendCancelEvent = false;
        this.mHasSendDownEvent = true;
        super.dispatchTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public void setContentView(View view) {
        View view2 = this.mTargetView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mContentResId = -1;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        this.mTargetView = view;
        this.mViewsZAxisNeedReset = true;
        addView(view, layoutParams);
    }

    public void setDisableLoadMore(boolean z2) {
        if (!z2) {
            this.mFlag &= -4097;
        } else {
            this.mFlag |= 4096;
            reset();
        }
    }

    public void setDisableLoadMoreWhenContentNotFull(boolean z2) {
        if (z2) {
            this.mFlag |= 4194304;
        } else {
            this.mFlag &= -4194305;
        }
    }

    public void setDisablePerformLoadMore(boolean z2) {
        if (z2) {
            this.mFlag |= 1024;
        } else {
            this.mFlag &= -1025;
        }
    }

    public void setDisablePerformRefresh(boolean z2) {
        if (z2) {
            this.mFlag |= 8192;
        } else {
            this.mFlag &= -8193;
        }
    }

    public void setDisableRefresh(boolean z2) {
        if (!z2) {
            this.mFlag &= -16385;
        } else {
            this.mFlag |= 16384;
            reset();
        }
    }

    public void setDisableWhenAnotherDirectionMove(boolean z2) {
        if (z2) {
            this.mFlag |= 262144;
        } else {
            this.mFlag &= -262145;
        }
    }

    public void setDurationOfBackToKeep(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationOfBackToHeaderHeight = i2;
        this.mDurationOfBackToFooterHeight = i2;
    }

    public void setDurationOfBackToKeepFooter(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationOfBackToFooterHeight = i2;
    }

    public void setDurationOfBackToKeepHeader(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationOfBackToHeaderHeight = i2;
    }

    public void setDurationToClose(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationToCloseHeader = i2;
        this.mDurationToCloseFooter = i2;
    }

    public void setDurationToCloseFooter(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationToCloseFooter = i2;
    }

    public void setDurationToCloseHeader(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mDurationToCloseHeader = i2;
    }

    public void setEnableAutoLoadMore(boolean z2) {
        if (z2) {
            this.mFlag |= 32768;
        } else {
            this.mFlag &= -32769;
        }
    }

    public void setEnableAutoRefresh(boolean z2) {
        if (z2) {
            this.mFlag |= 65536;
        } else {
            this.mFlag &= -65537;
        }
    }

    public void setEnableCheckInsideAnotherDirectionView(boolean z2) {
        if (z2) {
            this.mFlag |= 524288;
        } else {
            this.mFlag &= -524289;
        }
    }

    public void setEnableCompatSyncScroll(boolean z2) {
        if (z2) {
            this.mFlag |= 8388608;
        } else {
            this.mFlag &= -8388609;
        }
    }

    public void setEnableDynamicEnsureTargetView(boolean z2) {
        if (z2) {
            this.mFlag |= 16777216;
        } else {
            this.mFlag &= -16777217;
        }
    }

    public void setEnableFooterDrawerStyle(boolean z2) {
        if (z2) {
            this.mFlag |= 512;
        } else {
            this.mFlag &= -513;
        }
        this.mViewsZAxisNeedReset = true;
        checkViewsZAxisNeedReset();
    }

    public void setEnableHeaderDrawerStyle(boolean z2) {
        if (z2) {
            this.mFlag |= 256;
        } else {
            this.mFlag &= -257;
        }
        this.mViewsZAxisNeedReset = true;
        checkViewsZAxisNeedReset();
    }

    public void setEnableInterceptEventWhileLoading(boolean z2) {
        if (z2) {
            this.mFlag |= 131072;
        } else {
            this.mFlag &= -131073;
        }
    }

    public void setEnableKeepRefreshView(boolean z2) {
        if (z2) {
            this.mFlag |= 16;
        } else {
            this.mFlag &= -17;
            setEnablePinRefreshViewWhileLoading(false);
        }
    }

    public void setEnableNextPtrAtOnce(boolean z2) {
        if (z2) {
            this.mFlag |= 4;
        } else {
            this.mFlag &= -5;
        }
    }

    public void setEnableNoMoreData(boolean z2) {
        if (z2) {
            this.mFlag |= 2048;
        } else {
            this.mFlag &= -2049;
        }
    }

    public void setEnableNoSpringBackWhenNoMoreData(boolean z2) {
        if (z2) {
            this.mFlag |= 1048576;
        } else {
            this.mFlag &= -1048577;
        }
    }

    public void setEnableOldTouchHandling(boolean z2) {
        if (this.mIndicator.hasTouched()) {
            throw new IllegalArgumentException("This method cannot be called during touch event handling");
        }
        if (z2) {
            this.mFlag |= FLAG_ENABLE_OLD_TOUCH_HANDLING;
        } else {
            this.mFlag &= -67108865;
        }
    }

    public void setEnableOverScroll(boolean z2) {
        if (z2) {
            this.mFlag |= 8;
        } else {
            this.mFlag &= -9;
        }
    }

    public void setEnablePerformFreshWhenFling(boolean z2) {
        if (z2) {
            this.mFlag |= 33554432;
        } else {
            this.mFlag &= -33554433;
        }
    }

    public void setEnablePinContentView(boolean z2) {
        if (z2) {
            this.mFlag |= 32;
        } else {
            this.mFlag &= -33;
            setEnablePinRefreshViewWhileLoading(false);
        }
    }

    public void setEnablePinRefreshViewWhileLoading(boolean z2) {
        if (!z2) {
            this.mFlag &= -129;
        } else {
            if (!isEnabledPinContentView() || !isEnabledKeepRefreshView()) {
                throw new IllegalArgumentException("This method can only be enabled if setEnablePinContentView and setEnableKeepRefreshView are set be true");
            }
            this.mFlag |= 128;
        }
    }

    public void setEnablePullToRefresh(boolean z2) {
        if (z2) {
            this.mFlag |= 64;
        } else {
            this.mFlag &= -65;
        }
    }

    public void setEnableSmoothRollbackWhenCompleted(boolean z2) {
        if (z2) {
            this.mFlag |= 2097152;
        } else {
            this.mFlag &= -2097153;
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (z2) {
            return;
        }
        reset();
    }

    public void setFooterBackgroundColor(@ColorInt int i2) {
        this.mFooterBackgroundColor = i2;
        preparePaint();
    }

    public void setFooterView(@NonNull IRefreshView iRefreshView) {
        IRefreshView<IIndicator> iRefreshView2 = this.mFooterView;
        if (iRefreshView2 != null) {
            removeView(iRefreshView2.getView());
            this.mFooterView = null;
        }
        if (iRefreshView.getType() != 1) {
            throw new IllegalArgumentException("Wrong type, FooterView type must be TYPE_FOOTER");
        }
        View view = iRefreshView.getView();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        this.mViewsZAxisNeedReset = true;
        addView(view, layoutParams);
    }

    public void setHeaderBackgroundColor(@ColorInt int i2) {
        this.mHeaderBackgroundColor = i2;
        preparePaint();
    }

    public void setHeaderView(@NonNull IRefreshView iRefreshView) {
        IRefreshView<IIndicator> iRefreshView2 = this.mHeaderView;
        if (iRefreshView2 != null) {
            removeView(iRefreshView2.getView());
            this.mHeaderView = null;
        }
        if (iRefreshView.getType() != 0) {
            throw new IllegalArgumentException("Wrong type, HeaderView type must be TYPE_HEADER");
        }
        View view = iRefreshView.getView();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        this.mViewsZAxisNeedReset = true;
        addView(view, layoutParams);
    }

    public void setIndicatorOffsetCalculator(IIndicator.IOffsetCalculator iOffsetCalculator) {
        this.mIndicatorSetter.setOffsetCalculator(iOffsetCalculator);
    }

    public void setLoadingMinTime(long j2) {
        this.mLoadingMinTime = j2;
    }

    public void setMaxMoveRatio(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setMaxMoveRatio(f2);
    }

    public void setMaxMoveRatioOfFooter(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setMaxMoveRatioOfFooter(f2);
    }

    public void setMaxMoveRatioOfHeader(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setMaxMoveRatioOfHeader(f2);
    }

    public void setMaxOverScrollDuration(@IntRange(from = 0, to = a.f7153q) int i2) {
        this.mMaxOverScrollDuration = i2;
    }

    public void setMinOverScrollDuration(@IntRange(from = 0, to = a.f7153q) int i2) {
        this.mMinOverScrollDuration = i2;
    }

    public void setMode(int i2) {
        this.mMode = i2;
        reset();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z2) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z2);
    }

    public void setOnFooterEdgeDetectCallBack(OnFooterEdgeDetectCallBack onFooterEdgeDetectCallBack) {
        AppBarUtil appBarUtil;
        this.mInEdgeCanMoveFooterCallBack = onFooterEdgeDetectCallBack;
        if (onFooterEdgeDetectCallBack == null || (appBarUtil = this.mAppBarUtil) == null || onFooterEdgeDetectCallBack == appBarUtil) {
            return;
        }
        appBarUtil.onDetached(this);
        this.mAppBarUtil = null;
    }

    public void setOnHeaderEdgeDetectCallBack(OnHeaderEdgeDetectCallBack onHeaderEdgeDetectCallBack) {
        AppBarUtil appBarUtil;
        this.mInEdgeCanMoveHeaderCallBack = onHeaderEdgeDetectCallBack;
        if (onHeaderEdgeDetectCallBack == null || (appBarUtil = this.mAppBarUtil) == null || onHeaderEdgeDetectCallBack == appBarUtil) {
            return;
        }
        appBarUtil.onDetached(this);
        this.mAppBarUtil = null;
    }

    public void setOnHookFooterRefreshCompleteCallback(OnHookUIRefreshCompleteCallBack onHookUIRefreshCompleteCallBack) {
        if (this.mFooterRefreshCompleteHook == null) {
            this.mFooterRefreshCompleteHook = new RefreshCompleteHook();
        }
        this.mFooterRefreshCompleteHook.mCallBack = onHookUIRefreshCompleteCallBack;
    }

    public void setOnHookHeaderRefreshCompleteCallback(OnHookUIRefreshCompleteCallBack onHookUIRefreshCompleteCallBack) {
        if (this.mHeaderRefreshCompleteHook == null) {
            this.mHeaderRefreshCompleteHook = new RefreshCompleteHook();
        }
        this.mHeaderRefreshCompleteHook.mCallBack = onHookUIRefreshCompleteCallBack;
    }

    public void setOnInsideAnotherDirectionViewCallback(OnInsideAnotherDirectionViewCallback onInsideAnotherDirectionViewCallback) {
        this.mInsideAnotherDirectionViewCallback = onInsideAnotherDirectionViewCallback;
    }

    public void setOnLoadMoreScrollCallback(OnLoadMoreScrollCallback onLoadMoreScrollCallback) {
        this.mLoadMoreScrollCallback = onLoadMoreScrollCallback;
    }

    public void setOnPerformAutoLoadMoreCallBack(OnPerformAutoLoadMoreCallBack onPerformAutoLoadMoreCallBack) {
        this.mAutoLoadMoreCallBack = onPerformAutoLoadMoreCallBack;
    }

    public void setOnPerformAutoRefreshCallBack(OnPerformAutoRefreshCallBack onPerformAutoRefreshCallBack) {
        this.mAutoRefreshCallBack = onPerformAutoRefreshCallBack;
    }

    public <T extends OnRefreshListener> void setOnRefreshListener(T t2) {
        this.mRefreshListener = t2;
    }

    public void setRatioOfFooterToRefresh(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioOfFooterToRefresh(f2);
    }

    public void setRatioOfHeaderToRefresh(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioOfHeaderToRefresh(f2);
    }

    public void setRatioToKeep(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioToKeepHeader(f2);
        this.mIndicatorSetter.setRatioToKeepFooter(f2);
    }

    public void setRatioToKeepFooter(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioToKeepFooter(f2);
    }

    public void setRatioToKeepHeader(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioToKeepHeader(f2);
    }

    public void setRatioToRefresh(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setRatioToRefresh(f2);
    }

    public void setResistance(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setResistance(f2);
    }

    public void setResistanceOfFooter(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setResistanceOfFooter(f2);
    }

    public void setResistanceOfHeader(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
        this.mIndicatorSetter.setResistanceOfHeader(f2);
    }

    public void setScrollTargetView(View view) {
        this.mScrollTargetView = view;
    }

    public void setSpringBackInterpolator(@NonNull Interpolator interpolator) {
        if (this.mSpringBackInterpolator == interpolator) {
            return;
        }
        this.mSpringBackInterpolator = interpolator;
        ScrollChecker scrollChecker = this.mScrollChecker;
        if (scrollChecker.$Mode == 5) {
            scrollChecker.setInterpolator(interpolator);
        }
    }

    public void setSpringInterpolator(@NonNull Interpolator interpolator) {
        if (this.mSpringInterpolator == interpolator) {
            return;
        }
        this.mSpringInterpolator = interpolator;
        ScrollChecker scrollChecker = this.mScrollChecker;
        if (scrollChecker.$Mode == 4) {
            scrollChecker.setInterpolator(interpolator);
        }
    }

    public void setStickyFooterResId(@IdRes int i2) {
        if (this.mStickyFooterResId != i2) {
            this.mStickyFooterResId = i2;
            this.mStickyFooterView = null;
            ensureTargetView();
        }
    }

    public void setStickyHeaderResId(@IdRes int i2) {
        if (this.mStickyHeaderResId != i2) {
            this.mStickyHeaderResId = i2;
            this.mStickyHeaderView = null;
            ensureTargetView();
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i2) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public void triggeredLoadMore(boolean z2) {
        if (sDebug) {
            Log.d(this.TAG, "triggeredLoadMore()");
        }
        byte b3 = this.mStatus;
        this.mStatus = (byte) 4;
        notifyStatusChanged(b3, (byte) 4);
        this.mViewStatus = (byte) 23;
        this.mFlag &= -2;
        this.mIsSpringBackCanNotBeInterrupted = false;
        performRefresh(z2);
    }

    public void triggeredRefresh(boolean z2) {
        if (sDebug) {
            Log.d(this.TAG, "triggeredRefresh()");
        }
        byte b3 = this.mStatus;
        this.mStatus = (byte) 3;
        notifyStatusChanged(b3, (byte) 3);
        this.mViewStatus = (byte) 22;
        this.mFlag &= -2050;
        this.mIsSpringBackCanNotBeInterrupted = false;
        performRefresh(z2);
    }

    public void tryScrollBackToTop(int i2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("tryScrollBackToTop(): duration: %s", Integer.valueOf(i2)));
        }
        if (this.mIndicator.hasLeftStartPosition() && (!this.mIndicator.hasTouched() || !this.mIndicator.hasMoved())) {
            this.mScrollChecker.scrollTo(0, i2);
            return;
        }
        if (isNeedFilterTouchEvent() && this.mIndicator.hasLeftStartPosition()) {
            this.mScrollChecker.scrollTo(0, i2);
        } else if (isMovingFooter() && this.mStatus == 5 && this.mIndicator.hasJustBackToStartPosition()) {
            this.mScrollChecker.scrollTo(0, i2);
        } else {
            tryToNotifyReset();
        }
    }

    public void tryScrollBackToTopByPercentDuration() {
        if (this.mScrollChecker.isFlingBack()) {
            tryScrollBackToTop(this.mIndicator.getCurrentPos() > this.mScrollChecker.$MaxDistance ? Math.max((int) (Math.sqrt((this.mScrollChecker.$MaxDistance * 2.0f) / 2000.0f) * 1000.0d * this.mFlingBackFactor), this.mMinFlingBackDuration) : Math.max((int) (Math.sqrt((r0 * 3.0f) / 2000.0f) * 1000.0d * this.mFlingBackFactor), this.mMinFlingBackDuration));
            return;
        }
        float f2 = 1.0f;
        if (isMovingHeader()) {
            float currentPercentOfRefreshOffset = this.mIndicator.getCurrentPercentOfRefreshOffset();
            if (currentPercentOfRefreshOffset <= 1.0f && currentPercentOfRefreshOffset > 0.0f) {
                f2 = currentPercentOfRefreshOffset;
            }
            tryScrollBackToTop(Math.round(this.mDurationToCloseHeader * f2));
            return;
        }
        if (!isMovingFooter()) {
            tryToNotifyReset();
            return;
        }
        float currentPercentOfLoadMoreOffset = this.mIndicator.getCurrentPercentOfLoadMoreOffset();
        if (currentPercentOfLoadMoreOffset <= 1.0f && currentPercentOfLoadMoreOffset > 0.0f) {
            f2 = currentPercentOfLoadMoreOffset;
        }
        tryScrollBackToTop(Math.round(this.mDurationToCloseFooter * f2));
    }

    public void tryScrollToPerformAutoRefresh() {
        if (this.mMode == 0 && isMovingContent()) {
            byte b3 = this.mStatus;
            if (b3 == 1 || b3 == 2) {
                if ((!isEnabledAutoLoadMore() || isDisabledPerformLoadMore()) && (!isEnabledAutoRefresh() || isDisabledPerformRefresh())) {
                    return;
                }
                if (sDebug) {
                    Log.d(this.TAG, "tryScrollToPerformAutoRefresh()");
                }
                if (this.mScrollTargetView != null) {
                    if (!isEnabledAutoLoadMore() || !canAutoLoadMore(this.mScrollTargetView)) {
                        if (isEnabledAutoRefresh() && canAutoRefresh(this.mScrollTargetView)) {
                            triggeredRefresh(true);
                            return;
                        }
                        return;
                    }
                    if (!isDisabledLoadMoreWhenContentNotFull() || isNotYetInEdgeCannotMoveHeader(this.mScrollTargetView) || isNotYetInEdgeCannotMoveFooter(this.mScrollTargetView)) {
                        triggeredLoadMore(true);
                        return;
                    }
                    return;
                }
                if (this.mAutoFoundScrollTargetView != null) {
                    if (!isEnabledAutoLoadMore() || !canAutoLoadMore(this.mAutoFoundScrollTargetView)) {
                        if (isEnabledAutoRefresh() && canAutoRefresh(this.mAutoFoundScrollTargetView)) {
                            triggeredRefresh(true);
                            return;
                        }
                        return;
                    }
                    if (!isDisabledLoadMoreWhenContentNotFull() || isNotYetInEdgeCannotMoveHeader(this.mAutoFoundScrollTargetView) || isNotYetInEdgeCannotMoveFooter(this.mAutoFoundScrollTargetView)) {
                        triggeredLoadMore(true);
                        return;
                    }
                    return;
                }
                if (this.mTargetView != null) {
                    if (!isEnabledAutoLoadMore() || !canAutoLoadMore(this.mTargetView)) {
                        if (isEnabledAutoRefresh() && canAutoRefresh(this.mTargetView)) {
                            triggeredRefresh(true);
                            return;
                        }
                        return;
                    }
                    if (!isDisabledLoadMoreWhenContentNotFull() || isNotYetInEdgeCannotMoveHeader(this.mTargetView) || isNotYetInEdgeCannotMoveFooter(this.mTargetView)) {
                        triggeredLoadMore(true);
                    }
                }
            }
        }
    }

    public void tryToDealAnotherDirectionMove(float f2, float f3) {
        boolean z2 = false;
        if (!isDisabledWhenAnotherDirectionMove() || !this.mIsFingerInsideAnotherDirectionView) {
            if (Math.abs(f2) < this.mTouchSlop && Math.abs(f3) < this.mTouchSlop) {
                z2 = true;
            }
            this.mPreventForAnotherDirection = z2;
            if (z2) {
                return;
            }
            this.mDealAnotherDirectionMove = true;
            return;
        }
        if (Math.abs(f2) >= this.mTouchSlop && Math.abs(f2) > Math.abs(f3)) {
            this.mPreventForAnotherDirection = true;
            this.mDealAnotherDirectionMove = true;
        } else if (Math.abs(f2) >= this.mTouchSlop || Math.abs(f3) >= this.mTouchSlop) {
            this.mDealAnotherDirectionMove = true;
            this.mPreventForAnotherDirection = false;
        } else {
            this.mDealAnotherDirectionMove = false;
            this.mPreventForAnotherDirection = true;
        }
    }

    public boolean tryToFilterTouchEvent(MotionEvent motionEvent) {
        if (this.mIsInterceptTouchEventInOnceTouch) {
            if ((!isAutoRefresh() && this.mIndicator.isAlreadyHere(0) && !this.mScrollChecker.$IsScrolling) || (isAutoRefresh() && (isRefreshing() || isLoadingMore()))) {
                this.mScrollChecker.stop();
                if (motionEvent != null) {
                    makeNewTouchDownEvent(motionEvent);
                }
                this.mIsInterceptTouchEventInOnceTouch = false;
            }
            return true;
        }
        if (this.mIsLastOverScrollCanNotAbort) {
            if (this.mIndicator.isAlreadyHere(0) && !this.mScrollChecker.$IsScrolling) {
                if (motionEvent != null) {
                    makeNewTouchDownEvent(motionEvent);
                }
                this.mIsLastOverScrollCanNotAbort = false;
            }
            return true;
        }
        if (!this.mIsSpringBackCanNotBeInterrupted) {
            return false;
        }
        if (isEnabledNoMoreData()) {
            this.mIsSpringBackCanNotBeInterrupted = false;
            return false;
        }
        if (this.mIndicator.isAlreadyHere(0) && !this.mScrollChecker.$IsScrolling) {
            if (motionEvent != null) {
                makeNewTouchDownEvent(motionEvent);
            }
            this.mIsSpringBackCanNotBeInterrupted = false;
        }
        return true;
    }

    public boolean tryToNotifyReset() {
        View view;
        byte b3 = this.mStatus;
        if ((b3 != 5 && b3 != 2) || !this.mIndicator.isAlreadyHere(0)) {
            return false;
        }
        if (sDebug) {
            Log.d(this.TAG, "tryToNotifyReset()");
        }
        IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
        if (iRefreshView != null) {
            iRefreshView.onReset(this);
        }
        IRefreshView<IIndicator> iRefreshView2 = this.mFooterView;
        if (iRefreshView2 != null) {
            iRefreshView2.onReset(this);
        }
        byte b4 = this.mStatus;
        this.mStatus = (byte) 1;
        notifyStatusChanged(b4, (byte) 1);
        this.mViewStatus = (byte) 21;
        if (this.mScrollChecker.$Scroller.isFinished()) {
            this.mScrollChecker.stop();
            this.mScrollChecker.setInterpolator(this.mSpringInterpolator);
        }
        this.mAutomaticActionTriggered = true;
        tryToResetMovingStatus();
        if (this.mMode == 1 && (view = this.mTargetView) != null) {
            resetViewScale(view);
            View view2 = this.mScrollTargetView;
            if (view2 != null) {
                resetViewScale(view2);
            } else {
                View view3 = this.mAutoFoundScrollTargetView;
                if (view3 != null) {
                    resetViewScale(ScrollCompat.isViewPager(view3.getParent()) ? (View) this.mAutoFoundScrollTargetView.getParent() : this.mAutoFoundScrollTargetView);
                }
            }
        }
        if (!this.mIndicator.hasTouched()) {
            this.mIsSpringBackCanNotBeInterrupted = false;
        }
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return true;
    }

    public void tryToPerformAutoRefresh() {
        if (this.mAutomaticActionTriggered) {
            return;
        }
        if (sDebug) {
            Log.d(this.TAG, "tryToPerformAutoRefresh()");
        }
        if (isHeaderInProcessing()) {
            if (this.mHeaderView == null || this.mIndicator.getHeaderHeight() <= 0) {
                return;
            }
            scrollToTriggeredAutomatic(true);
            return;
        }
        if (!isFooterInProcessing() || this.mFooterView == null || this.mIndicator.getFooterHeight() <= 0) {
            return;
        }
        scrollToTriggeredAutomatic(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0044, code lost:
    
        if (r1.isAlreadyHere(r1.getOffsetToKeepHeaderWhileLoading()) != false) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void tryToPerformRefresh() {
        /*
            r4 = this;
            byte r0 = r4.mStatus
            r1 = 2
            if (r0 != r1) goto L77
            boolean r0 = r4.isMovingContent()
            if (r0 == 0) goto Lc
            goto L77
        Lc:
            boolean r0 = me.dkzwm.widget.srl.SmoothRefreshLayout.sDebug
            if (r0 == 0) goto L17
            java.lang.String r0 = r4.TAG
            java.lang.String r1 = "tryToPerformRefresh()"
            android.util.Log.d(r0, r1)
        L17:
            boolean r0 = r4.isEnabledKeepRefreshView()
            boolean r1 = r4.isHeaderInProcessing()
            r2 = 1
            if (r1 == 0) goto L4a
            boolean r1 = r4.isDisabledPerformRefresh()
            if (r1 != 0) goto L4a
            me.dkzwm.widget.srl.extra.IRefreshView<me.dkzwm.widget.srl.indicator.IIndicator> r1 = r4.mHeaderView
            if (r1 == 0) goto L4a
            if (r0 == 0) goto L3a
            me.dkzwm.widget.srl.indicator.IIndicator r1 = r4.mIndicator
            int r3 = r1.getOffsetToRefresh()
            boolean r1 = r1.isAlreadyHere(r3)
            if (r1 != 0) goto L46
        L3a:
            me.dkzwm.widget.srl.indicator.IIndicator r1 = r4.mIndicator
            int r3 = r1.getOffsetToKeepHeaderWhileLoading()
            boolean r1 = r1.isAlreadyHere(r3)
            if (r1 == 0) goto L4a
        L46:
            r4.triggeredRefresh(r2)
            return
        L4a:
            boolean r1 = r4.isFooterInProcessing()
            if (r1 == 0) goto L77
            boolean r1 = r4.isDisabledPerformLoadMore()
            if (r1 != 0) goto L77
            me.dkzwm.widget.srl.extra.IRefreshView<me.dkzwm.widget.srl.indicator.IIndicator> r1 = r4.mFooterView
            if (r1 == 0) goto L77
            if (r0 == 0) goto L68
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r1 = r0.getOffsetToLoadMore()
            boolean r0 = r0.isAlreadyHere(r1)
            if (r0 != 0) goto L74
        L68:
            me.dkzwm.widget.srl.indicator.IIndicator r0 = r4.mIndicator
            int r1 = r0.getOffsetToKeepFooterWhileLoading()
            boolean r0 = r0.isAlreadyHere(r1)
            if (r0 == 0) goto L77
        L74:
            r4.triggeredLoadMore(r2)
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: me.dkzwm.widget.srl.SmoothRefreshLayout.tryToPerformRefresh():void");
    }

    public void tryToPerformRefreshWhenMoved() {
        if (this.mMode == 0 && this.mStatus == 2 && !isAutoRefresh()) {
            if (isHeaderInProcessing() && isMovingHeader() && !isDisabledPerformRefresh()) {
                if (isEnabledPullToRefresh() && this.mIndicator.isOverOffsetToRefresh()) {
                    triggeredRefresh(true);
                    return;
                }
                if (!isEnabledPerformFreshWhenFling() || this.mIndicator.hasTouched() || this.mScrollChecker.isPreFling() || this.mScrollChecker.isFling() || !this.mIndicator.isJustReturnedOffsetToRefresh()) {
                    return;
                }
                triggeredRefresh(true);
                this.mScrollChecker.stop();
                return;
            }
            if (isFooterInProcessing() && isMovingFooter() && !isDisabledPerformLoadMore()) {
                if (isEnabledPullToRefresh() && this.mIndicator.isOverOffsetToLoadMore()) {
                    triggeredLoadMore(true);
                    return;
                }
                if (!isEnabledPerformFreshWhenFling() || this.mIndicator.hasTouched() || this.mScrollChecker.isPreFling() || this.mScrollChecker.isFling() || !this.mIndicator.isJustReturnedOffsetToLoadMore()) {
                    return;
                }
                triggeredLoadMore(true);
                this.mScrollChecker.stop();
            }
        }
    }

    public void tryToResetMovingStatus() {
        if (!this.mIndicator.isAlreadyHere(0) || isMovingContent()) {
            return;
        }
        this.mIndicatorSetter.setMovingStatus(0);
        notifyUIPositionChanged();
    }

    public void updateAnotherDirectionPos() {
        if (this.mMode == 0) {
            if (this.mHeaderView != null && !isDisabledRefresh() && isMovingHeader() && this.mHeaderView.getView().getVisibility() == 0) {
                if (isHeaderInProcessing()) {
                    this.mHeaderView.onRefreshPositionChanged(this, this.mStatus, this.mIndicator);
                    return;
                } else {
                    this.mHeaderView.onPureScrollPositionChanged(this, this.mStatus, this.mIndicator);
                    return;
                }
            }
            if (this.mFooterView == null || isDisabledLoadMore() || !isMovingFooter() || this.mFooterView.getView().getVisibility() != 0) {
                return;
            }
            if (isFooterInProcessing()) {
                this.mFooterView.onRefreshPositionChanged(this, this.mStatus, this.mIndicator);
            } else {
                this.mFooterView.onPureScrollPositionChanged(this, this.mStatus, this.mIndicator);
            }
        }
    }

    public void updatePos(int i2) {
        boolean zIsMovingHeader = isMovingHeader();
        boolean zIsMovingFooter = isMovingFooter();
        if ((this.mMode == 0 && ((this.mIndicator.hasJustLeftStartPosition() || this.mViewStatus == 21) && this.mStatus == 1)) || (this.mStatus == 5 && isEnabledNextPtrAtOnce() && ((isHeaderInProcessing() && zIsMovingHeader && i2 > 0) || (isFooterInProcessing() && zIsMovingFooter && i2 < 0)))) {
            byte b3 = this.mStatus;
            this.mStatus = (byte) 2;
            notifyStatusChanged(b3, (byte) 2);
            if (isMovingHeader()) {
                this.mViewStatus = (byte) 22;
                IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
                if (iRefreshView != null) {
                    iRefreshView.onRefreshPrepare(this);
                }
            } else if (isMovingFooter()) {
                this.mViewStatus = (byte) 23;
                IRefreshView<IIndicator> iRefreshView2 = this.mFooterView;
                if (iRefreshView2 != null) {
                    iRefreshView2.onRefreshPrepare(this);
                }
            }
        }
        if ((!isAutoRefresh() || this.mStatus == 5) && this.mIndicator.hasJustBackToStartPosition()) {
            tryToNotifyReset();
            if (isEnabledOldTouchHandling() && this.mIndicator.hasTouched() && !this.mNestedScrolling && !this.mHasSendDownEvent) {
                sendDownEvent(null);
            }
        }
        tryToPerformRefreshWhenMoved();
        if (sDebug) {
            Log.d(this.TAG, String.format("updatePos(): change: %s, current: %s last: %s", Integer.valueOf(i2), Integer.valueOf(this.mIndicator.getCurrentPos()), Integer.valueOf(this.mIndicator.getLastPos())));
        }
        notifyUIPositionChanged();
        if (offsetChild(i2, zIsMovingHeader, zIsMovingFooter)) {
            requestLayout();
        } else if (this.mBackgroundPaint != null || this.mIndicator.isAlreadyHere(0)) {
            invalidate();
        }
    }

    @Deprecated
    public boolean autoLoadMore(boolean z2) {
        return autoLoadMore(z2 ? 1 : 0, true);
    }

    @Deprecated
    public boolean autoRefresh(boolean z2) {
        return autoRefresh(z2 ? 1 : 0, true);
    }

    public void dispatchNestedFling(int i2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("dispatchNestedFling() : velocity: %s", Integer.valueOf(i2)));
        }
        View view = this.mScrollTargetView;
        if (view != null) {
            ScrollCompat.flingCompat(view, -i2);
            return;
        }
        View view2 = this.mAutoFoundScrollTargetView;
        if (view2 != null) {
            ScrollCompat.flingCompat(view2, -i2);
            return;
        }
        View view3 = this.mTargetView;
        if (view3 != null) {
            ScrollCompat.flingCompat(view3, -i2);
        }
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2, int i4) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr, int i6) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i2) {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent(i2);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NonNull View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        boolean zIsVerticalOrientation = isVerticalOrientation();
        if (i4 == 0) {
            if (!tryToFilterTouchEvent(null)) {
                this.mScrollChecker.stop();
                boolean z2 = !isNotYetInEdgeCannotMoveFooter();
                boolean z3 = !isNotYetInEdgeCannotMoveHeader();
                int i5 = zIsVerticalOrientation ? i3 : i2;
                if (i5 > 0 && !isDisabledRefresh() && z3 && (!isEnabledPinRefreshViewWhileLoading() || !isRefreshing() || !this.mIndicator.isOverOffsetToKeepHeaderWhileLoading())) {
                    if (!this.mIndicator.isAlreadyHere(0) && isMovingHeader()) {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i2, this.mIndicator.getLastMovePoint()[1] - i3);
                        moveHeaderPos(this.mIndicator.getOffset());
                        if (zIsVerticalOrientation) {
                            iArr[1] = i3;
                        } else {
                            iArr[0] = i2;
                        }
                    } else if (zIsVerticalOrientation) {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i2, this.mIndicator.getLastMovePoint()[1]);
                    } else {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0], this.mIndicator.getLastMovePoint()[1] - i3);
                    }
                }
                if (i5 < 0 && !isDisabledLoadMore() && z2 && (!isEnabledPinRefreshViewWhileLoading() || !isLoadingMore() || !this.mIndicator.isOverOffsetToKeepFooterWhileLoading())) {
                    if (!this.mIndicator.isAlreadyHere(0) && isMovingFooter()) {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i2, this.mIndicator.getLastMovePoint()[1] - i3);
                        moveFooterPos(this.mIndicator.getOffset());
                        if (zIsVerticalOrientation) {
                            iArr[1] = i3;
                        } else {
                            iArr[0] = i2;
                        }
                    } else if (zIsVerticalOrientation) {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i2, this.mIndicator.getLastMovePoint()[1]);
                    } else {
                        this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0], this.mIndicator.getLastMovePoint()[1] - i3);
                    }
                }
                if (isMovingFooter() && isFooterInProcessing() && this.mStatus == 5 && this.mIndicator.hasLeftStartPosition() && !z2) {
                    this.mScrollChecker.scrollTo(0, 0);
                    if (zIsVerticalOrientation) {
                        iArr[1] = i3;
                    } else {
                        iArr[0] = i2;
                    }
                }
            } else if (zIsVerticalOrientation) {
                iArr[1] = i3;
            } else {
                iArr[0] = i2;
            }
            tryToResetMovingStatus();
        }
        int[] iArr2 = this.mParentScrollConsumed;
        iArr2[0] = 0;
        iArr2[1] = 0;
        if (dispatchNestedPreScroll(i2 - iArr[0], i3 - iArr[1], iArr2, null, i4)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        } else if (i4 == 1 && !isMovingContent() && !isEnabledPinRefreshViewWhileLoading()) {
            if (zIsVerticalOrientation) {
                iArr2[1] = i3;
            } else {
                iArr2[0] = i2;
            }
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
        if (iArr[0] != 0 || iArr[1] != 0) {
            onNestedScrollChanged();
        }
        if (sDebug) {
            Log.d(this.TAG, String.format("onNestedPreScroll(): dx: %s, dy: %s, consumed: %s, type: %s", Integer.valueOf(i2), Integer.valueOf(i3), Arrays.toString(iArr), Integer.valueOf(i4)));
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5, int i6) {
        if (sDebug) {
            Log.d(this.TAG, String.format("onNestedScroll(): dxConsumed: %s, dyConsumed: %s, dxUnconsumed: %s dyUnconsumed: %s, type: %s", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)));
        }
        dispatchNestedScroll(i2, i3, i4, i5, this.mParentOffsetInWindow, i6);
        if (i6 == 0) {
            if (tryToFilterTouchEvent(null)) {
                return;
            }
            int[] iArr = this.mParentOffsetInWindow;
            int i7 = iArr[0] + i4;
            int i8 = i5 + iArr[1];
            boolean z2 = !isNotYetInEdgeCannotMoveFooter();
            boolean z3 = !isNotYetInEdgeCannotMoveHeader();
            int i9 = isVerticalOrientation() ? i8 : i7;
            if (i9 < 0 && !isDisabledRefresh() && z3 && (!isEnabledPinRefreshViewWhileLoading() || !isRefreshing() || !this.mIndicator.isOverOffsetToKeepHeaderWhileLoading())) {
                this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i7, this.mIndicator.getLastMovePoint()[1] - i8);
                moveHeaderPos(this.mIndicator.getOffset());
            } else if (i9 > 0 && !isDisabledLoadMore() && z2 && ((!isDisabledLoadMoreWhenContentNotFull() || !z3 || !this.mIndicator.isAlreadyHere(0)) && (!isEnabledPinRefreshViewWhileLoading() || !isLoadingMore() || !this.mIndicator.isOverOffsetToKeepFooterWhileLoading()))) {
                this.mIndicatorSetter.onFingerMove(this.mIndicator.getLastMovePoint()[0] - i7, this.mIndicator.getLastMovePoint()[1] - i8);
                moveFooterPos(this.mIndicator.getOffset());
            }
            tryToResetMovingStatus();
        }
        if (i2 != 0 || i3 != 0) {
            onNestedScrollChanged();
        } else if (i6 == 1) {
            stopNestedScroll(i6);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2, int i3) {
        if (sDebug) {
            Log.d(this.TAG, String.format("onNestedScrollAccepted(): axes: %s, type: %s", Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i2, i3);
        startNestedScroll(getSupportScrollAxis() & i2, i3);
        if (i3 == 0) {
            this.mIndicatorSetter.onFingerDown();
            this.mNestedTouchScrolling = true;
        }
        this.mLastNestedType = i3;
        this.mNestedScrolling = true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2, int i3) {
        if (sDebug) {
            Log.d(this.TAG, String.format("onStartNestedScroll(): axes: %s, type: %s", Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        return isEnabled() && isNestedScrollingEnabled() && this.mTargetView != null && (getSupportScrollAxis() & i2) != 0;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(@NonNull View view, int i2) {
        if (sDebug) {
            Log.d(this.TAG, String.format("onStopNestedScroll() type: %s", Integer.valueOf(i2)));
        }
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, i2);
        if (this.mLastNestedType == i2) {
            this.mNestedScrolling = false;
        }
        this.mNestedTouchScrolling = false;
        this.mIsInterceptTouchEventInOnceTouch = isNeedInterceptTouchEvent();
        this.mIsLastOverScrollCanNotAbort = isCanNotAbortOverScrolling();
        this.mNestedScrollingChildHelper.stopNestedScroll(i2);
        if (isAutoRefresh() || i2 != 0) {
            return;
        }
        this.mIndicatorSetter.onFingerUp();
        onFingerUp();
    }

    public final void refreshComplete(boolean z2) {
        refreshComplete(z2, 0L);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int i2, int i3) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i2, i3);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public void stopNestedScroll(int i2) {
        View view = this.mScrollTargetView;
        if (view == null && (view = this.mAutoFoundScrollTargetView) == null) {
            view = this.mTargetView;
        }
        if (view != null) {
            ViewCompat.stopNestedScroll(view, i2);
        } else {
            this.mNestedScrollingChildHelper.stopNestedScroll(i2);
        }
    }

    @Deprecated
    public boolean autoLoadMore(boolean z2, boolean z3) {
        return autoLoadMore(z2 ? 1 : 0, z3);
    }

    @Deprecated
    public boolean autoRefresh(boolean z2, boolean z3) {
        return autoRefresh(z2 ? 1 : 0, z3);
    }

    public final void refreshComplete(long j2) {
        refreshComplete(true, j2);
    }

    public boolean autoLoadMore(int i2, boolean z2) {
        if (this.mStatus != 1 || this.mMode != 0 || isDisabledPerformLoadMore()) {
            return false;
        }
        if (sDebug) {
            Log.d(this.TAG, String.format("autoLoadMore(): action: %s, smoothScroll: %s", Integer.valueOf(i2), Boolean.valueOf(z2)));
        }
        byte b3 = this.mStatus;
        this.mStatus = (byte) 2;
        notifyStatusChanged(b3, (byte) 2);
        IRefreshView<IIndicator> iRefreshView = this.mFooterView;
        if (iRefreshView != null) {
            iRefreshView.onRefreshPrepare(this);
        }
        this.mIndicatorSetter.setMovingStatus(1);
        this.mViewStatus = (byte) 23;
        this.mAutomaticActionUseSmoothScroll = z2;
        if (this.mIndicator.getFooterHeight() <= 0) {
            this.mAutomaticActionTriggered = false;
        } else {
            scrollToTriggeredAutomatic(false);
        }
        return true;
    }

    public boolean autoRefresh(int i2, boolean z2) {
        if (this.mStatus != 1 || this.mMode != 0 || isDisabledPerformRefresh()) {
            return false;
        }
        if (sDebug) {
            Log.d(this.TAG, String.format("autoRefresh(): action: %s, smoothScroll: %s", Integer.valueOf(i2), Boolean.valueOf(z2)));
        }
        byte b3 = this.mStatus;
        this.mStatus = (byte) 2;
        notifyStatusChanged(b3, (byte) 2);
        IRefreshView<IIndicator> iRefreshView = this.mHeaderView;
        if (iRefreshView != null) {
            iRefreshView.onRefreshPrepare(this);
        }
        this.mIndicatorSetter.setMovingStatus(2);
        this.mViewStatus = (byte) 22;
        this.mAutomaticActionUseSmoothScroll = z2;
        this.mAutomaticAction = i2;
        if (this.mIndicator.getHeaderHeight() <= 0) {
            this.mAutomaticActionTriggered = false;
        } else {
            scrollToTriggeredAutomatic(true);
        }
        return true;
    }

    public final void refreshComplete(boolean z2, long j2) {
        IRefreshView<IIndicator> iRefreshView;
        IRefreshView<IIndicator> iRefreshView2;
        if (sDebug) {
            Log.d(this.TAG, String.format("refreshComplete(): isSuccessful: %s", Boolean.valueOf(z2)));
        }
        this.mIsLastRefreshSuccessful = z2;
        if (isRefreshing() || isLoadingMore()) {
            long jUptimeMillis = this.mLoadingMinTime - (SystemClock.uptimeMillis() - this.mLoadingStartTime);
            if (j2 <= 0) {
                if (jUptimeMillis <= 0) {
                    performRefreshComplete(true, true);
                    return;
                }
                if (this.mDelayToRefreshComplete == null) {
                    this.mDelayToRefreshComplete = new DelayToRefreshComplete();
                }
                this.mDelayToRefreshComplete.mLayoutWeakRf = new WeakReference(this);
                this.mDelayToRefreshComplete.mNotifyViews = true;
                postDelayed(this.mDelayToRefreshComplete, jUptimeMillis);
                return;
            }
            if (isRefreshing() && (iRefreshView2 = this.mHeaderView) != null) {
                iRefreshView2.onRefreshComplete(this, z2);
            } else if (isLoadingMore() && (iRefreshView = this.mFooterView) != null) {
                iRefreshView.onRefreshComplete(this, z2);
            }
            if (j2 < jUptimeMillis) {
                j2 = jUptimeMillis;
            }
            if (this.mDelayToRefreshComplete == null) {
                this.mDelayToRefreshComplete = new DelayToRefreshComplete();
            }
            this.mDelayToRefreshComplete.mLayoutWeakRf = new WeakReference(this);
            this.mDelayToRefreshComplete.mNotifyViews = false;
            postDelayed(this.mDelayToRefreshComplete, j2);
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static final int[] LAYOUT_ATTRS = {android.R.attr.layout_gravity};
        public int gravity;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = BadgeDrawable.TOP_START;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, this.gravity);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i2, int i3, int i4) {
            super(i2, i3);
            this.gravity = i4;
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.gravity = BadgeDrawable.TOP_START;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = BadgeDrawable.TOP_START;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = BadgeDrawable.TOP_START;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = BadgeDrawable.TOP_START;
            this.gravity = layoutParams.gravity;
        }
    }

    public boolean isNotYetInEdgeCannotMoveFooter(View view) {
        OnFooterEdgeDetectCallBack onFooterEdgeDetectCallBack = this.mInEdgeCanMoveFooterCallBack;
        if (onFooterEdgeDetectCallBack != null) {
            return onFooterEdgeDetectCallBack.isNotYetInEdgeCannotMoveFooter(this, view, this.mFooterView);
        }
        return ScrollCompat.canChildScrollDown(view);
    }

    public boolean isNotYetInEdgeCannotMoveHeader(View view) {
        OnHeaderEdgeDetectCallBack onHeaderEdgeDetectCallBack = this.mInEdgeCanMoveHeaderCallBack;
        if (onHeaderEdgeDetectCallBack != null) {
            return onHeaderEdgeDetectCallBack.isNotYetInEdgeCannotMoveHeader(this, view, this.mHeaderView);
        }
        return ScrollCompat.canChildScrollUp(view);
    }

    public SmoothRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        StringBuilder sb = new StringBuilder();
        sb.append("SmoothRefreshLayout-");
        int i2 = sId;
        sId = i2 + 1;
        sb.append(i2);
        this.TAG = sb.toString();
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mCachedViews = new ArrayList(1);
        this.mMode = 0;
        this.mAutomaticActionUseSmoothScroll = false;
        this.mAutomaticActionTriggered = true;
        this.mIsSpringBackCanNotBeInterrupted = false;
        this.mDealAnotherDirectionMove = false;
        this.mPreventForAnotherDirection = false;
        this.mIsInterceptTouchEventInOnceTouch = false;
        this.mIsLastOverScrollCanNotAbort = false;
        this.mIsFingerInsideAnotherDirectionView = false;
        this.mNestedScrolling = false;
        this.mNestedTouchScrolling = false;
        this.mFlingBackFactor = 1.1f;
        this.mStatus = (byte) 1;
        this.mViewStatus = (byte) 21;
        this.mLoadingMinTime = 500L;
        this.mLoadingStartTime = 0L;
        this.mAutomaticAction = 0;
        this.mLastNestedType = 1;
        this.mDurationToCloseHeader = R2.attr.arcLabelPaddingBottom;
        this.mDurationToCloseFooter = R2.attr.arcLabelPaddingBottom;
        this.mDurationOfBackToHeaderHeight = 200;
        this.mDurationOfBackToFooterHeight = 200;
        this.mMinFlingBackDuration = 300;
        this.mContentResId = -1;
        this.mStickyHeaderResId = -1;
        this.mStickyFooterResId = -1;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mIsLastRefreshSuccessful = true;
        this.mViewsZAxisNeedReset = true;
        this.mNeedFilterScrollEvent = false;
        this.mHasSendCancelEvent = false;
        this.mHasSendDownEvent = false;
        this.mCachedPoint = new float[2];
        this.mCachedSpec = new int[2];
        this.mOffsetConsumed = 0.0f;
        this.mOffsetTotal = 0.0f;
        this.mFlag = 109056000;
        this.mMaxOverScrollDuration = R2.attr.arcLabelPaddingBottom;
        this.mMinOverScrollDuration = 100;
        this.mOffsetRemaining = 0;
        init(context, attributeSet, 0, 0);
    }

    public SmoothRefreshLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        StringBuilder sb = new StringBuilder();
        sb.append("SmoothRefreshLayout-");
        int i3 = sId;
        sId = i3 + 1;
        sb.append(i3);
        this.TAG = sb.toString();
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mCachedViews = new ArrayList(1);
        this.mMode = 0;
        this.mAutomaticActionUseSmoothScroll = false;
        this.mAutomaticActionTriggered = true;
        this.mIsSpringBackCanNotBeInterrupted = false;
        this.mDealAnotherDirectionMove = false;
        this.mPreventForAnotherDirection = false;
        this.mIsInterceptTouchEventInOnceTouch = false;
        this.mIsLastOverScrollCanNotAbort = false;
        this.mIsFingerInsideAnotherDirectionView = false;
        this.mNestedScrolling = false;
        this.mNestedTouchScrolling = false;
        this.mFlingBackFactor = 1.1f;
        this.mStatus = (byte) 1;
        this.mViewStatus = (byte) 21;
        this.mLoadingMinTime = 500L;
        this.mLoadingStartTime = 0L;
        this.mAutomaticAction = 0;
        this.mLastNestedType = 1;
        this.mDurationToCloseHeader = R2.attr.arcLabelPaddingBottom;
        this.mDurationToCloseFooter = R2.attr.arcLabelPaddingBottom;
        this.mDurationOfBackToHeaderHeight = 200;
        this.mDurationOfBackToFooterHeight = 200;
        this.mMinFlingBackDuration = 300;
        this.mContentResId = -1;
        this.mStickyHeaderResId = -1;
        this.mStickyFooterResId = -1;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mIsLastRefreshSuccessful = true;
        this.mViewsZAxisNeedReset = true;
        this.mNeedFilterScrollEvent = false;
        this.mHasSendCancelEvent = false;
        this.mHasSendDownEvent = false;
        this.mCachedPoint = new float[2];
        this.mCachedSpec = new int[2];
        this.mOffsetConsumed = 0.0f;
        this.mOffsetTotal = 0.0f;
        this.mFlag = 109056000;
        this.mMaxOverScrollDuration = R2.attr.arcLabelPaddingBottom;
        this.mMinOverScrollDuration = 100;
        this.mOffsetRemaining = 0;
        init(context, attributeSet, i2, 0);
    }
}
