package com.actionbarsherlock.widget.swipeback;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.actionbarsherlock.widget.swipeback.ViewDragHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SwipeBackLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;
    public static final int EDGE_ALL = 11;
    public static final int EDGE_BOTTOM = 8;
    private static final int[] EDGE_FLAGS = {1, 2, 8, 11};
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int FULL_ALPHA = 255;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int OVERSCROLL_DISTANCE = 10;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private Activity mActivity;
    private int mContentLeft;
    private int mContentTop;
    private View mContentView;
    private ViewDragHelper mDragHelper;
    private int mEdgeFlag;
    private boolean mEnable;
    private boolean mInLayout;
    private List<SwipeListener> mListeners;
    private int mScrimColor;
    private float mScrimOpacity;
    private float mScrollPercent;
    private float mScrollThreshold;
    private Drawable mShadowBottom;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private Rect mTmpRect;
    private int mTrackingEdge;

    public interface SwipeListener {
        void onEdgeTouch(int i2);

        void onScrollOverThreshold();

        void onScrollStateChange(int i2, float f2);
    }

    public class ViewDragCallback extends ViewDragHelper.Callback {
        private boolean mIsScrollOverValid;

        private ViewDragCallback() {
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i2, int i3) {
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                return Math.min(view.getWidth(), Math.max(i2, 0));
            }
            if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                return Math.min(0, Math.max(i2, -view.getWidth()));
            }
            return 0;
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i2, int i3) {
            if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                return Math.min(0, Math.max(i2, -view.getHeight()));
            }
            return 0;
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            return SwipeBackLayout.this.mEdgeFlag & 3;
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public int getViewVerticalDragRange(View view) {
            return SwipeBackLayout.this.mEdgeFlag & 8;
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i2) {
            super.onViewDragStateChanged(i2);
            if (SwipeBackLayout.this.mListeners == null || SwipeBackLayout.this.mListeners.isEmpty()) {
                return;
            }
            Iterator it = SwipeBackLayout.this.mListeners.iterator();
            while (it.hasNext()) {
                ((SwipeListener) it.next()).onScrollStateChange(i2, SwipeBackLayout.this.mScrollPercent);
            }
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i2, int i3, int i4, int i5) {
            super.onViewPositionChanged(view, i2, i3, i4, i5);
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(i2 / (r3.mContentView.getWidth() + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth()));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(i2 / (r3.mContentView.getWidth() + SwipeBackLayout.this.mShadowRight.getIntrinsicWidth()));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(i3 / (r3.mContentView.getHeight() + SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight()));
            }
            SwipeBackLayout.this.mContentLeft = i2;
            SwipeBackLayout.this.mContentTop = i3;
            SwipeBackLayout.this.invalidate();
            if (SwipeBackLayout.this.mScrollPercent < SwipeBackLayout.this.mScrollThreshold && !this.mIsScrollOverValid) {
                this.mIsScrollOverValid = true;
            }
            if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty() && SwipeBackLayout.this.mDragHelper.getViewDragState() == 1 && SwipeBackLayout.this.mScrollPercent >= SwipeBackLayout.this.mScrollThreshold && this.mIsScrollOverValid) {
                this.mIsScrollOverValid = false;
                Iterator it = SwipeBackLayout.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((SwipeListener) it.next()).onScrollOverThreshold();
                }
            }
            if (SwipeBackLayout.this.mScrollPercent < 1.0f || SwipeBackLayout.this.mActivity.isFinishing()) {
                return;
            }
            SwipeBackLayout.this.mActivity.finish();
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public void onViewReleased(View view, float f2, float f3) {
            int i2;
            int width = view.getWidth();
            int height = view.getHeight();
            int intrinsicWidth = 0;
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                i2 = 0;
                intrinsicWidth = (f2 > 0.0f || (f2 == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? width + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth() + 10 : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                intrinsicWidth = (f2 < 0.0f || (f2 == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? -(width + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth() + 10) : 0;
                i2 = 0;
            } else {
                i2 = ((SwipeBackLayout.this.mTrackingEdge & 8) == 0 || (f3 >= 0.0f && (f3 != 0.0f || SwipeBackLayout.this.mScrollPercent <= SwipeBackLayout.this.mScrollThreshold))) ? 0 : -(height + SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight() + 10);
            }
            SwipeBackLayout.this.mDragHelper.settleCapturedViewAt(intrinsicWidth, i2);
            SwipeBackLayout.this.invalidate();
        }

        @Override // com.actionbarsherlock.widget.swipeback.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i2) {
            boolean zIsEdgeTouched = SwipeBackLayout.this.mDragHelper.isEdgeTouched(SwipeBackLayout.this.mEdgeFlag, i2);
            if (zIsEdgeTouched) {
                if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(1, i2)) {
                    SwipeBackLayout.this.mTrackingEdge = 1;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(2, i2)) {
                    SwipeBackLayout.this.mTrackingEdge = 2;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(8, i2)) {
                    SwipeBackLayout.this.mTrackingEdge = 8;
                }
                if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
                    Iterator it = SwipeBackLayout.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((SwipeListener) it.next()).onEdgeTouch(SwipeBackLayout.this.mTrackingEdge);
                    }
                }
                this.mIsScrollOverValid = true;
            }
            return zIsEdgeTouched;
        }
    }

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    private void drawScrim(Canvas canvas, View view) {
        int i2 = (this.mScrimColor & 16777215) | (((int) ((((-16777216) & r0) >>> 24) * this.mScrimOpacity)) << 24);
        int i3 = this.mTrackingEdge;
        if ((i3 & 1) != 0) {
            canvas.clipRect(0, 0, view.getLeft(), getHeight());
        } else if ((i3 & 2) != 0) {
            canvas.clipRect(view.getRight(), 0, getRight(), getHeight());
        } else if ((i3 & 8) != 0) {
            canvas.clipRect(view.getLeft(), view.getBottom(), getRight(), getHeight());
        }
        canvas.drawColor(i2);
    }

    private void drawShadow(Canvas canvas, View view) {
        Rect rect = this.mTmpRect;
        view.getHitRect(rect);
        if ((this.mEdgeFlag & 1) != 0) {
            Drawable drawable = this.mShadowLeft;
            drawable.setBounds(rect.left - drawable.getIntrinsicWidth(), rect.top, rect.left, rect.bottom);
            this.mShadowLeft.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowLeft.draw(canvas);
        }
        if ((this.mEdgeFlag & 2) != 0) {
            Drawable drawable2 = this.mShadowRight;
            int i2 = rect.right;
            drawable2.setBounds(i2, rect.top, drawable2.getIntrinsicWidth() + i2, rect.bottom);
            this.mShadowRight.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowRight.draw(canvas);
        }
        if ((this.mEdgeFlag & 8) != 0) {
            Drawable drawable3 = this.mShadowBottom;
            int i3 = rect.left;
            int i4 = rect.bottom;
            drawable3.setBounds(i3, i4, rect.right, drawable3.getIntrinsicHeight() + i4);
            this.mShadowBottom.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowBottom.draw(canvas);
        }
    }

    private void setContentView(View view) {
        this.mContentView = view;
    }

    public void addSwipeListener(SwipeListener swipeListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(swipeListener);
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{R.attr.windowBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
        viewGroup2.setBackgroundResource(resourceId);
        viewGroup.removeView(viewGroup2);
        addView(viewGroup2);
        setContentView(viewGroup2);
        viewGroup.addView(this);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mScrimOpacity = 1.0f - this.mScrollPercent;
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2 = view == this.mContentView;
        boolean zDrawChild = super.drawChild(canvas, view, j2);
        if (this.mScrimOpacity > 0.0f && z2 && this.mDragHelper.getViewDragState() != 0) {
            drawShadow(canvas, view);
            drawScrim(canvas, view);
        }
        return zDrawChild;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnable) {
            return false;
        }
        try {
            return this.mDragHelper.shouldInterceptTouchEvent(motionEvent);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        try {
            this.mInLayout = true;
            View view = this.mContentView;
            if (view != null) {
                int i6 = this.mContentLeft;
                view.layout(i6, this.mContentTop, view.getMeasuredWidth() + i6, this.mContentTop + this.mContentView.getMeasuredHeight());
            }
            this.mInLayout = false;
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnable) {
            return false;
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        return true;
    }

    public void removeSwipeListener(SwipeListener swipeListener) {
        List<SwipeListener> list = this.mListeners;
        if (list == null) {
            return;
        }
        list.remove(swipeListener);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInLayout) {
            return;
        }
        super.requestLayout();
    }

    public void scrollToFinishActivity() {
        int intrinsicHeight;
        int intrinsicWidth;
        int width = this.mContentView.getWidth();
        int height = this.mContentView.getHeight();
        int i2 = this.mEdgeFlag;
        int i3 = 0;
        if ((i2 & 1) != 0) {
            intrinsicWidth = width + this.mShadowLeft.getIntrinsicWidth() + 10;
            this.mTrackingEdge = 1;
        } else {
            if ((i2 & 2) == 0) {
                if ((i2 & 8) != 0) {
                    intrinsicHeight = ((-height) - this.mShadowBottom.getIntrinsicHeight()) - 10;
                    this.mTrackingEdge = 8;
                } else {
                    intrinsicHeight = 0;
                }
                this.mDragHelper.smoothSlideViewTo(this.mContentView, i3, intrinsicHeight);
                invalidate();
            }
            intrinsicWidth = ((-width) - this.mShadowRight.getIntrinsicWidth()) - 10;
            this.mTrackingEdge = 2;
        }
        intrinsicHeight = 0;
        i3 = intrinsicWidth;
        this.mDragHelper.smoothSlideViewTo(this.mContentView, i3, intrinsicHeight);
        invalidate();
    }

    public void setEdgeSize(int i2) {
        this.mDragHelper.setEdgeSize(i2);
    }

    public void setEdgeTrackingEnabled(int i2) {
        this.mEdgeFlag = i2;
        this.mDragHelper.setEdgeTrackingEnabled(i2);
    }

    public void setEnableGesture(boolean z2) {
        this.mEnable = z2;
    }

    public void setScrimColor(int i2) {
        this.mScrimColor = i2;
        invalidate();
    }

    public void setScrollThresHold(float f2) {
        if (f2 >= 1.0f || f2 <= 0.0f) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        this.mScrollThreshold = f2;
    }

    public void setSensitivity(Context context, float f2) {
        this.mDragHelper.setSensitivity(context, f2);
    }

    public void setShadow(Drawable drawable, int i2) {
        if ((i2 & 1) != 0) {
            this.mShadowLeft = drawable;
        } else if ((i2 & 2) != 0) {
            this.mShadowRight = drawable;
        } else if ((i2 & 8) != 0) {
            this.mShadowBottom = drawable;
        }
        invalidate();
    }

    @Deprecated
    public void setSwipeListener(SwipeListener swipeListener) {
        addSwipeListener(swipeListener);
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.actionbarsherlock.R.attr.SwipeBackLayoutStyle);
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        this.mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;
        this.mEnable = true;
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mTmpRect = new Rect();
        this.mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.actionbarsherlock.R.styleable.SwipeBackLayout, i2, com.actionbarsherlock.R.style.SwipeBackLayout);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.actionbarsherlock.R.styleable.SwipeBackLayout_edge_size, -1);
        if (dimensionPixelSize > 0) {
            setEdgeSize(dimensionPixelSize);
        }
        setEdgeTrackingEnabled(EDGE_FLAGS[typedArrayObtainStyledAttributes.getInt(com.actionbarsherlock.R.styleable.SwipeBackLayout_edge_flag, 0)]);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(com.actionbarsherlock.R.styleable.SwipeBackLayout_shadow_left, com.actionbarsherlock.R.drawable.shadow_left);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(com.actionbarsherlock.R.styleable.SwipeBackLayout_shadow_right, com.actionbarsherlock.R.drawable.shadow_right);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(com.actionbarsherlock.R.styleable.SwipeBackLayout_shadow_bottom, com.actionbarsherlock.R.drawable.shadow_bottom);
        setShadow(resourceId, 1);
        setShadow(resourceId2, 2);
        setShadow(resourceId3, 8);
        typedArrayObtainStyledAttributes.recycle();
        float f2 = getResources().getDisplayMetrics().density * 400.0f;
        this.mDragHelper.setMinVelocity(f2);
        this.mDragHelper.setMaxVelocity(f2 * 2.0f);
    }

    public void setShadow(int i2, int i3) {
        setShadow(getResources().getDrawable(i2), i3);
    }
}
