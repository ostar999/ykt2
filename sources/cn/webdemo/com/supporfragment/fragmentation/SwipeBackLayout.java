package cn.webdemo.com.supporfragment.fragmentation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentationMagician;
import cn.webdemo.com.supporfragment.ISupportFragment;
import cn.webdemo.com.supporfragment.R;
import cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SwipeBackLayout extends FrameLayout {
    private static final float DEFAULT_PARALLAX = 0.33f;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.4f;
    public static final int EDGE_ALL = 3;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int FULL_ALPHA = 255;
    private static final int OVERSCROLL_DISTANCE = 10;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_FINISHED = 3;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private FragmentActivity mActivity;
    private boolean mCallOnDestroyView;
    private int mContentLeft;
    private int mContentTop;
    private View mContentView;
    private Context mContext;
    private int mCurrentSwipeOrientation;
    private int mEdgeFlag;
    private boolean mEnable;
    private ISupportFragment mFragment;
    private ViewDragHelper mHelper;
    private boolean mInLayout;
    private List<OnSwipeListener> mListeners;
    private float mParallaxOffset;
    private Fragment mPreFragment;
    private float mScrimOpacity;
    private float mScrollFinishThreshold;
    private float mScrollPercent;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private Rect mTmpRect;

    public enum EdgeLevel {
        MAX,
        MIN,
        MED
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeOrientation {
    }

    public interface OnSwipeListener {
        void onDragScrolled(float f2);

        void onDragStateChange(int i2);

        void onEdgeTouch(int i2);
    }

    public class ViewDragCallback extends ViewDragHelper.Callback {
        private ViewDragCallback() {
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i2, int i3) {
            if ((SwipeBackLayout.this.mCurrentSwipeOrientation & 1) != 0) {
                return Math.min(view.getWidth(), Math.max(i2, 0));
            }
            if ((SwipeBackLayout.this.mCurrentSwipeOrientation & 2) != 0) {
                return Math.min(0, Math.max(i2, -view.getWidth()));
            }
            return 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            if (SwipeBackLayout.this.mFragment != null) {
                return 1;
            }
            return ((SwipeBackLayout.this.mActivity instanceof ISwipeBackActivity) && ((ISwipeBackActivity) SwipeBackLayout.this.mActivity).swipeBackPriority()) ? 1 : 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeTouched(int i2, int i3) {
            super.onEdgeTouched(i2, i3);
            if ((SwipeBackLayout.this.mEdgeFlag & i2) != 0) {
                SwipeBackLayout.this.mCurrentSwipeOrientation = i2;
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i2) {
            super.onViewDragStateChanged(i2);
            if (SwipeBackLayout.this.mListeners != null) {
                Iterator it = SwipeBackLayout.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((OnSwipeListener) it.next()).onDragStateChange(i2);
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i2, int i3, int i4, int i5) {
            super.onViewPositionChanged(view, i2, i3, i4, i5);
            if ((SwipeBackLayout.this.mCurrentSwipeOrientation & 1) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(i2 / (r3.mContentView.getWidth() + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth()));
            } else if ((SwipeBackLayout.this.mCurrentSwipeOrientation & 2) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(i2 / (r3.mContentView.getWidth() + SwipeBackLayout.this.mShadowRight.getIntrinsicWidth()));
            }
            SwipeBackLayout.this.mContentLeft = i2;
            SwipeBackLayout.this.mContentTop = i3;
            SwipeBackLayout.this.invalidate();
            if (SwipeBackLayout.this.mListeners != null && SwipeBackLayout.this.mHelper.getViewDragState() == 1 && SwipeBackLayout.this.mScrollPercent <= 1.0f && SwipeBackLayout.this.mScrollPercent > 0.0f) {
                Iterator it = SwipeBackLayout.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((OnSwipeListener) it.next()).onDragScrolled(SwipeBackLayout.this.mScrollPercent);
                }
            }
            if (SwipeBackLayout.this.mScrollPercent > 1.0f) {
                if (SwipeBackLayout.this.mFragment != null) {
                    if (SwipeBackLayout.this.mCallOnDestroyView || ((Fragment) SwipeBackLayout.this.mFragment).isDetached()) {
                        return;
                    }
                    SwipeBackLayout.this.onDragFinished();
                    SwipeBackLayout.this.mFragment.getSupportDelegate().popQuiet();
                    return;
                }
                if (SwipeBackLayout.this.mActivity.isFinishing()) {
                    return;
                }
                SwipeBackLayout.this.onDragFinished();
                SwipeBackLayout.this.mActivity.finish();
                SwipeBackLayout.this.mActivity.overridePendingTransition(0, 0);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0065  */
        @Override // androidx.customview.widget.ViewDragHelper.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onViewReleased(android.view.View r3, float r4, float r5) {
            /*
                r2 = this;
                int r3 = r3.getWidth()
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                int r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$300(r5)
                r5 = r5 & 1
                r0 = 0
                r1 = 0
                if (r5 == 0) goto L34
                int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r4 > 0) goto L26
                if (r4 != 0) goto L65
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                float r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$700(r4)
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                float r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$1600(r5)
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 <= 0) goto L65
            L26:
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                android.graphics.drawable.Drawable r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$900(r4)
                int r4 = r4.getIntrinsicWidth()
                int r3 = r3 + r4
                int r3 = r3 + 10
                goto L66
            L34:
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                int r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$300(r5)
                r5 = r5 & 2
                if (r5 == 0) goto L65
                int r5 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r5 < 0) goto L56
                int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r4 != 0) goto L65
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                float r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$700(r4)
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                float r5 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$1600(r5)
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 <= 0) goto L65
            L56:
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                android.graphics.drawable.Drawable r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$1000(r4)
                int r4 = r4.getIntrinsicWidth()
                int r3 = r3 + r4
                int r3 = r3 + 10
                int r3 = -r3
                goto L66
            L65:
                r3 = r1
            L66:
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                androidx.customview.widget.ViewDragHelper r4 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.access$200(r4)
                r4.settleCapturedViewAt(r3, r1)
                cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout r3 = cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.this
                r3.invalidate()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout.ViewDragCallback.onViewReleased(android.view.View, float, float):void");
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i2) {
            List<Fragment> activeFragments;
            boolean zIsEdgeTouched = SwipeBackLayout.this.mHelper.isEdgeTouched(SwipeBackLayout.this.mEdgeFlag, i2);
            if (zIsEdgeTouched) {
                if (SwipeBackLayout.this.mHelper.isEdgeTouched(1, i2)) {
                    SwipeBackLayout.this.mCurrentSwipeOrientation = 1;
                } else if (SwipeBackLayout.this.mHelper.isEdgeTouched(2, i2)) {
                    SwipeBackLayout.this.mCurrentSwipeOrientation = 2;
                }
                if (SwipeBackLayout.this.mListeners != null) {
                    Iterator it = SwipeBackLayout.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((OnSwipeListener) it.next()).onEdgeTouch(SwipeBackLayout.this.mCurrentSwipeOrientation);
                    }
                }
                if (SwipeBackLayout.this.mPreFragment != null) {
                    View view2 = SwipeBackLayout.this.mPreFragment.getView();
                    if (view2 != null && view2.getVisibility() != 0) {
                        view2.setVisibility(0);
                    }
                } else if (SwipeBackLayout.this.mFragment != null && (activeFragments = FragmentationMagician.getActiveFragments(((Fragment) SwipeBackLayout.this.mFragment).getFragmentManager())) != null && activeFragments.size() > 1) {
                    int iIndexOf = activeFragments.indexOf(SwipeBackLayout.this.mFragment) - 1;
                    while (true) {
                        if (iIndexOf >= 0) {
                            Fragment fragment = activeFragments.get(iIndexOf);
                            if (fragment != null && fragment.getView() != null) {
                                fragment.getView().setVisibility(0);
                                SwipeBackLayout.this.mPreFragment = fragment;
                                break;
                            }
                            iIndexOf--;
                        } else {
                            break;
                        }
                    }
                }
            }
            return zIsEdgeTouched;
        }
    }

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    private void drawScrim(Canvas canvas, View view) {
        int i2 = ((int) (this.mScrimOpacity * 153.0f)) << 24;
        int i3 = this.mCurrentSwipeOrientation;
        if ((i3 & 1) != 0) {
            canvas.clipRect(0, 0, view.getLeft(), getHeight());
        } else if ((i3 & 2) != 0) {
            canvas.clipRect(view.getRight(), 0, getRight(), getHeight());
        }
        canvas.drawColor(i2);
    }

    private void drawShadow(Canvas canvas, View view) {
        Rect rect = this.mTmpRect;
        view.getHitRect(rect);
        int i2 = this.mCurrentSwipeOrientation;
        if ((i2 & 1) != 0) {
            Drawable drawable = this.mShadowLeft;
            drawable.setBounds(rect.left - drawable.getIntrinsicWidth(), rect.top, rect.left, rect.bottom);
            this.mShadowLeft.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowLeft.draw(canvas);
            return;
        }
        if ((i2 & 2) != 0) {
            Drawable drawable2 = this.mShadowRight;
            int i3 = rect.right;
            drawable2.setBounds(i3, rect.top, drawable2.getIntrinsicWidth() + i3, rect.bottom);
            this.mShadowRight.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowRight.draw(canvas);
        }
    }

    private void init() {
        this.mHelper = ViewDragHelper.create(this, new ViewDragCallback());
        setShadow(R.drawable.shadow_left, 1);
        setEdgeOrientation(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDragFinished() {
        List<OnSwipeListener> list = this.mListeners;
        if (list != null) {
            Iterator<OnSwipeListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onDragStateChange(3);
            }
        }
    }

    private void setContentView(View view) {
        this.mContentView = view;
    }

    private void validateEdgeLevel(int i2, EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            Field declaredField = this.mHelper.getClass().getDeclaredField("mEdgeSize");
            declaredField.setAccessible(true);
            if (i2 >= 0) {
                declaredField.setInt(this.mHelper, i2);
            } else if (edgeLevel == EdgeLevel.MAX) {
                declaredField.setInt(this.mHelper, displayMetrics.widthPixels);
            } else if (edgeLevel == EdgeLevel.MED) {
                declaredField.setInt(this.mHelper, displayMetrics.widthPixels / 2);
            } else {
                declaredField.setInt(this.mHelper, (int) ((displayMetrics.density * 20.0f) + 0.5f));
            }
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    public void addSwipeListener(OnSwipeListener onSwipeListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(onSwipeListener);
    }

    public void attachToActivity(FragmentActivity fragmentActivity) {
        this.mActivity = fragmentActivity;
        TypedArray typedArrayObtainStyledAttributes = fragmentActivity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        ViewGroup viewGroup = (ViewGroup) fragmentActivity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
        viewGroup2.setBackgroundResource(resourceId);
        viewGroup.removeView(viewGroup2);
        addView(viewGroup2);
        setContentView(viewGroup2);
        viewGroup.addView(this);
    }

    public void attachToFragment(ISupportFragment iSupportFragment, View view) {
        addView(view);
        setFragment(iSupportFragment, view);
    }

    @Override // android.view.View
    public void computeScroll() {
        float f2 = 1.0f - this.mScrollPercent;
        this.mScrimOpacity = f2;
        if (f2 >= 0.0f) {
            if (this.mHelper.continueSettling(true)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
            Fragment fragment = this.mPreFragment;
            if (fragment == null || fragment.getView() == null) {
                return;
            }
            if (this.mCallOnDestroyView) {
                this.mPreFragment.getView().setX(0.0f);
            } else if (this.mHelper.getCapturedView() != null) {
                int left = (int) ((this.mHelper.getCapturedView().getLeft() - getWidth()) * this.mParallaxOffset * this.mScrimOpacity);
                this.mPreFragment.getView().setX(left <= 0 ? left : 0.0f);
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2 = view == this.mContentView;
        boolean zDrawChild = super.drawChild(canvas, view, j2);
        if (z2 && this.mScrimOpacity > 0.0f && this.mHelper.getViewDragState() != 0) {
            drawShadow(canvas, view);
            drawScrim(canvas, view);
        }
        return zDrawChild;
    }

    public ViewDragHelper getViewDragHelper() {
        return this.mHelper;
    }

    public void hiddenFragment() {
        Fragment fragment = this.mPreFragment;
        if (fragment == null || fragment.getView() == null) {
            return;
        }
        this.mPreFragment.getView().setVisibility(8);
    }

    public void internalCallOnDestroyView() {
        this.mCallOnDestroyView = true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnable) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        try {
            return this.mHelper.shouldInterceptTouchEvent(motionEvent);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.mInLayout = true;
        View view = this.mContentView;
        if (view != null) {
            int i6 = this.mContentLeft;
            view.layout(i6, this.mContentTop, view.getMeasuredWidth() + i6, this.mContentTop + this.mContentView.getMeasuredHeight());
        }
        this.mInLayout = false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnable) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.mHelper.processTouchEvent(motionEvent);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void removeSwipeListener(OnSwipeListener onSwipeListener) {
        List<OnSwipeListener> list = this.mListeners;
        if (list == null) {
            return;
        }
        list.remove(onSwipeListener);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInLayout) {
            return;
        }
        super.requestLayout();
    }

    public void setEdgeLevel(EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        validateEdgeLevel(-1, edgeLevel);
    }

    public void setEdgeOrientation(int i2) {
        this.mEdgeFlag = i2;
        this.mHelper.setEdgeTrackingEnabled(i2);
        if (i2 == 2 || i2 == 3) {
            setShadow(R.drawable.shadow_right, 2);
        }
    }

    public void setEnableGesture(boolean z2) {
        this.mEnable = z2;
    }

    public void setFragment(ISupportFragment iSupportFragment, View view) {
        this.mFragment = iSupportFragment;
        this.mContentView = view;
    }

    public void setParallaxOffset(float f2) {
        this.mParallaxOffset = f2;
    }

    public void setScrollThresHold(float f2) {
        if (f2 >= 1.0f || f2 <= 0.0f) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        this.mScrollFinishThreshold = f2;
    }

    public void setShadow(Drawable drawable, int i2) {
        if ((i2 & 1) != 0) {
            this.mShadowLeft = drawable;
        } else if ((i2 & 2) != 0) {
            this.mShadowRight = drawable;
        }
        invalidate();
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setEdgeLevel(int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        validateEdgeLevel(i2, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mScrollFinishThreshold = DEFAULT_SCROLL_THRESHOLD;
        this.mTmpRect = new Rect();
        this.mEnable = true;
        this.mParallaxOffset = DEFAULT_PARALLAX;
        this.mContext = context;
        init();
    }

    public void setShadow(int i2, int i3) {
        setShadow(getResources().getDrawable(i2), i3);
    }
}
