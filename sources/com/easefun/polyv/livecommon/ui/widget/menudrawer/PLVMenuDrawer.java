package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.compat.ActionBarHelper;

/* loaded from: classes3.dex */
public abstract class PLVMenuDrawer extends ViewGroup {
    protected static final int ANIMATION_DELAY = 16;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ANIMATION_DURATION = 600;
    private static final int DEFAULT_DRAG_BEZEL_DP = 24;
    private static final int DEFAULT_DROP_SHADOW_DP = 6;
    static final int INDICATOR_ANIM_DURATION = 800;
    public static final int MENU_DRAG_CONTAINER = 2;
    public static final int MENU_DRAG_CONTENT = 0;
    public static final int MENU_DRAG_WINDOW = 1;
    public static final int STATE_CLOSED = 0;
    public static final int STATE_CLOSING = 1;
    public static final int STATE_DRAGGING = 2;
    public static final int STATE_OPEN = 8;
    public static final int STATE_OPENING = 4;
    private static final String TAG = "MenuDrawer";
    public static final int TOUCH_MODE_BEZEL = 1;
    public static final int TOUCH_MODE_FULLSCREEN = 2;
    public static final int TOUCH_MODE_NONE = 0;
    private ActionBarHelper mActionBarHelper;
    protected Bitmap mActiveIndicator;
    protected int mActivePosition;
    protected final Rect mActiveRect;
    protected View mActiveView;
    private Activity mActivity;
    private boolean mAllowIndicatorAnimation;
    protected BuildLayerFrameLayout mContentContainer;
    private int mCurrentUpContentDesc;
    private boolean mCustomDropShadow;
    private int mDragMode;
    protected boolean mDrawOverlay;
    private int mDrawerClosedContentDesc;
    protected boolean mDrawerIndicatorEnabled;
    private int mDrawerOpenContentDesc;
    protected int mDrawerState;
    protected int mDropShadowColor;
    protected Drawable mDropShadowDrawable;
    protected boolean mDropShadowEnabled;
    protected final Rect mDropShadowRect;
    protected int mDropShadowSize;
    protected boolean mHardwareLayersEnabled;
    protected boolean mIndicatorAnimating;
    private final Rect mIndicatorClipRect;
    protected float mIndicatorOffset;
    private Runnable mIndicatorRunnable;
    private FloatScroller mIndicatorScroller;
    protected int mIndicatorStartPos;
    protected boolean mIsStatic;
    protected ViewGroup mLayoutContainer;
    protected int mMaxAnimationDuration;
    protected BuildLayerFrameLayout mMenuContainer;
    protected Drawable mMenuOverlay;
    protected int mMenuSize;
    private View mMenuView;
    protected boolean mMenuVisible;
    protected float mOffsetPixels;
    private OnDrawerStateChangeListener mOnDrawerStateChangeListener;
    protected OnInterceptMoveEventListener mOnInterceptMoveEventListener;
    private Position mPosition;
    private Position mResolvedPosition;
    private ViewTreeObserver.OnScrollChangedListener mScrollListener;
    protected SlideDrawable mSlideDrawable;
    protected Bundle mState;
    private final Rect mTempRect;
    protected Drawable mThemeUpIndicator;
    protected int mTouchBezelSize;
    protected int mTouchMode;
    protected int mTouchSize;
    static final boolean USE_TRANSLATIONS = true;
    protected static final Interpolator SMOOTH_INTERPOLATOR = new SmoothInterpolator();
    protected static final Interpolator INDICATOR_INTERPOLATOR = new AccelerateInterpolator();

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position;

        static {
            int[] iArr = new int[Position.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position = iArr;
            try {
                iArr[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.START.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.END.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public interface OnDrawerStateChangeListener {
        void onDrawerSlide(float openRatio, int offsetPixels);

        void onDrawerStateChange(int oldState, int newState);
    }

    public interface OnInterceptMoveEventListener {
        boolean isViewDraggable(View v2, int delta, int x2, int y2);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Bundle mState;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.mState);
        }

        public SavedState(Parcel in) {
            super(in);
            this.mState = in.readBundle();
        }
    }

    public enum Type {
        BEHIND,
        STATIC,
        OVERLAY
    }

    public PLVMenuDrawer(Activity activity, int dragMode) {
        this(activity);
        this.mActivity = activity;
        this.mDragMode = dragMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateIndicatorInvalidate() {
        if (this.mIndicatorScroller.computeScrollOffset()) {
            this.mIndicatorOffset = this.mIndicatorScroller.getCurr();
            invalidate();
            if (!this.mIndicatorScroller.isFinished()) {
                postOnAnimation(this.mIndicatorRunnable);
                return;
            }
        }
        completeAnimatingIndicator();
    }

    public static PLVMenuDrawer attach(Activity activity) {
        return attach(activity, Type.BEHIND);
    }

    private static void attachToContainer(Activity activity, PLVMenuDrawer menuDrawer, ViewGroup container) {
        container.removeAllViews();
        container.addView(menuDrawer);
        menuDrawer.mContentContainer.setVisibility(4);
        menuDrawer.mLayoutContainer = container;
    }

    private static void attachToContent(Activity activity, PLVMenuDrawer menuDrawer) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(menuDrawer, -1, -1);
    }

    private static void attachToDecor(Activity activity, PLVMenuDrawer menuDrawer) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
        viewGroup.removeAllViews();
        viewGroup.addView(menuDrawer, -1, -1);
        menuDrawer.mContentContainer.addView(viewGroup2, viewGroup2.getLayoutParams());
    }

    private void completeAnimatingIndicator() {
        this.mIndicatorOffset = 1.0f;
        this.mIndicatorAnimating = false;
        invalidate();
    }

    private static PLVMenuDrawer createMenuDrawer(Activity activity, int dragMode, Position position, Type type) {
        PLVMenuDrawer slidingDrawer;
        if (type == Type.STATIC) {
            slidingDrawer = new StaticDrawer(activity);
        } else if (type == Type.OVERLAY) {
            slidingDrawer = new OverlayDrawer(activity, dragMode);
            if (position == Position.LEFT || position == Position.START) {
                slidingDrawer.setupUpIndicator(activity);
            }
        } else {
            slidingDrawer = new SlidingDrawer(activity, dragMode);
            if (position == Position.LEFT || position == Position.START) {
                slidingDrawer.setupUpIndicator(activity);
            }
        }
        slidingDrawer.mDragMode = dragMode;
        slidingDrawer.setPosition(position);
        return slidingDrawer;
    }

    private void drawDropShadow(Canvas canvas) {
        if (this.mDropShadowDrawable == null) {
            setDropShadowColor(this.mDropShadowColor);
        }
        updateDropShadowRect();
        this.mDropShadowDrawable.setBounds(this.mDropShadowRect);
        this.mDropShadowDrawable.draw(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        int height;
        Integer num = (Integer) this.mActiveView.getTag(com.easefun.polyv.livecommon.R.id.mdActiveViewPosition);
        int width = 0;
        if ((num == null ? 0 : num.intValue()) == this.mActivePosition) {
            updateIndicatorClipRect();
            canvas.save();
            canvas.clipRect(this.mIndicatorClipRect);
            int i2 = AnonymousClass3.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
            if (i2 == 1 || i2 == 2) {
                Rect rect = this.mIndicatorClipRect;
                width = rect.left;
                height = rect.top;
            } else if (i2 == 3) {
                width = this.mIndicatorClipRect.right - this.mActiveIndicator.getWidth();
                height = this.mIndicatorClipRect.top;
            } else if (i2 != 4) {
                height = 0;
            } else {
                Rect rect2 = this.mIndicatorClipRect;
                width = rect2.left;
                height = rect2.bottom - this.mActiveIndicator.getHeight();
            }
            canvas.drawBitmap(this.mActiveIndicator, width, height, (Paint) null);
            canvas.restore();
        }
    }

    private int getIndicatorStartPos() {
        int i2 = AnonymousClass3.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? this.mIndicatorClipRect.top : this.mIndicatorClipRect.left : this.mIndicatorClipRect.top : this.mIndicatorClipRect.left;
    }

    private boolean shouldDrawIndicator() {
        View view = this.mActiveView;
        return (view == null || this.mActiveIndicator == null || !isViewDescendant(view)) ? false : true;
    }

    private void startAnimatingIndicator() {
        this.mIndicatorStartPos = getIndicatorStartPos();
        this.mIndicatorAnimating = true;
        this.mIndicatorScroller.startScroll(0.0f, 1.0f, 800);
        animateIndicatorInvalidate();
    }

    public void closeMenu() {
        closeMenu(false);
    }

    public abstract void closeMenu(boolean animate);

    public void detachToContainer() {
        ViewGroup viewGroup = this.mLayoutContainer;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int i2 = (int) this.mOffsetPixels;
        if (this.mDrawOverlay && i2 != 0) {
            drawOverlay(canvas);
        }
        if (this.mDropShadowEnabled && (i2 != 0 || this.mIsStatic)) {
            drawDropShadow(canvas);
        }
        if (shouldDrawIndicator()) {
            if (i2 != 0 || this.mIsStatic) {
                drawIndicator(canvas);
            }
        }
    }

    public void dispatchOnDrawerSlide(float openRatio, int offsetPixels) {
        OnDrawerStateChangeListener onDrawerStateChangeListener = this.mOnDrawerStateChangeListener;
        if (onDrawerStateChangeListener != null) {
            onDrawerStateChangeListener.onDrawerSlide(openRatio, offsetPixels);
        }
    }

    public int dpToPx(int dp) {
        return (int) ((getResources().getDisplayMetrics().density * dp) + 0.5f);
    }

    public abstract void drawOverlay(Canvas canvas);

    @Override // android.view.View
    public boolean fitSystemWindows(Rect insets) {
        if (this.mDragMode == 1 && this.mPosition != Position.BOTTOM) {
            this.mMenuContainer.setPadding(0, insets.top, 0, 0);
        }
        return super.fitSystemWindows(insets);
    }

    public boolean getAllowIndicatorAnimation() {
        return this.mAllowIndicatorAnimation;
    }

    public ViewGroup getContentContainer() {
        return this.mDragMode == 0 ? this.mContentContainer : (ViewGroup) findViewById(R.id.content);
    }

    public boolean getDrawOverlay() {
        return this.mDrawOverlay;
    }

    public int getDrawerState() {
        return this.mDrawerState;
    }

    public Drawable getDropShadow() {
        return this.mDropShadowDrawable;
    }

    public GradientDrawable.Orientation getDropShadowOrientation() {
        int i2 = AnonymousClass3.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? GradientDrawable.Orientation.RIGHT_LEFT : GradientDrawable.Orientation.TOP_BOTTOM : GradientDrawable.Orientation.LEFT_RIGHT : GradientDrawable.Orientation.BOTTOM_TOP;
    }

    public ViewGroup getLayoutContainer() {
        return this.mLayoutContainer;
    }

    public ViewGroup getMenuContainer() {
        return this.mMenuContainer;
    }

    public int getMenuSize() {
        return this.mMenuSize;
    }

    public View getMenuView() {
        return this.mMenuView;
    }

    public abstract boolean getOffsetMenuEnabled();

    public Position getPosition() {
        int layoutDirection = ViewHelper.getLayoutDirection(this);
        int i2 = AnonymousClass3.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[this.mPosition.ordinal()];
        return i2 != 5 ? i2 != 6 ? this.mPosition : layoutDirection == 1 ? Position.LEFT : Position.RIGHT : layoutDirection == 1 ? Position.RIGHT : Position.LEFT;
    }

    public abstract int getTouchBezelSize();

    public abstract int getTouchMode();

    public void initDrawer(Context context, AttributeSet attrs, int defStyle) {
        setWillNotDraw(false);
        setFocusable(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer, com.easefun.polyv.livecommon.R.attr.menuDrawerStyle, com.easefun.polyv.livecommon.R.style.Widget_MenuDrawer);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdContentBackground);
        typedArrayObtainStyledAttributes.getDrawable(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdMenuBackground);
        this.mMenuSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdMenuSize, dpToPx(240));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdActiveIndicator, 0);
        if (resourceId != 0) {
            this.mActiveIndicator = BitmapFactory.decodeResource(getResources(), resourceId);
        }
        this.mDropShadowEnabled = typedArrayObtainStyledAttributes.getBoolean(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDropShadowEnabled, true);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDropShadow);
        this.mDropShadowDrawable = drawable2;
        if (drawable2 == null) {
            this.mDropShadowColor = typedArrayObtainStyledAttributes.getColor(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDropShadowColor, -16777216);
        } else {
            this.mCustomDropShadow = true;
        }
        this.mDropShadowSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDropShadowSize, dpToPx(6));
        this.mTouchBezelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdTouchBezelSize, dpToPx(24));
        this.mAllowIndicatorAnimation = typedArrayObtainStyledAttributes.getBoolean(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdAllowIndicatorAnimation, false);
        this.mMaxAnimationDuration = typedArrayObtainStyledAttributes.getInt(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdMaxAnimationDuration, 600);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdSlideDrawable, -1);
        if (resourceId2 != -1) {
            setSlideDrawable(resourceId2);
        }
        this.mDrawerOpenContentDesc = typedArrayObtainStyledAttributes.getResourceId(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDrawerOpenUpContentDescription, 0);
        this.mDrawerClosedContentDesc = typedArrayObtainStyledAttributes.getResourceId(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDrawerClosedUpContentDescription, 0);
        this.mDrawOverlay = typedArrayObtainStyledAttributes.getBoolean(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdDrawOverlay, true);
        setPosition(Position.fromValue(typedArrayObtainStyledAttributes.getInt(com.easefun.polyv.livecommon.R.styleable.PLVMenuDrawer_mdPosition, 0)));
        typedArrayObtainStyledAttributes.recycle();
        NoClickThroughFrameLayout noClickThroughFrameLayout = new NoClickThroughFrameLayout(context);
        this.mMenuContainer = noClickThroughFrameLayout;
        noClickThroughFrameLayout.setId(com.easefun.polyv.livecommon.R.id.md__menu);
        NoClickThroughFrameLayout noClickThroughFrameLayout2 = new NoClickThroughFrameLayout(context);
        this.mContentContainer = noClickThroughFrameLayout2;
        noClickThroughFrameLayout2.setId(com.easefun.polyv.livecommon.R.id.md__content);
        this.mContentContainer.setBackgroundDrawable(drawable);
        this.mMenuOverlay = new ColorDrawable(-16777216);
        this.mIndicatorScroller = new FloatScroller(SMOOTH_INTERPOLATOR);
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public abstract boolean isMenuVisible();

    public boolean isViewDescendant(View v2) {
        for (ViewParent parent = v2.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this) {
                return true;
            }
        }
        return false;
    }

    public void logDrawerState(int state) {
        if (state == 0) {
            Log.d(TAG, "[DrawerState] STATE_CLOSED");
            return;
        }
        if (state == 1) {
            Log.d(TAG, "[DrawerState] STATE_CLOSING");
            return;
        }
        if (state == 2) {
            Log.d(TAG, "[DrawerState] STATE_DRAGGING");
            return;
        }
        if (state == 4) {
            Log.d(TAG, "[DrawerState] STATE_OPENING");
            return;
        }
        if (state == 8) {
            Log.d(TAG, "[DrawerState] STATE_OPEN");
            return;
        }
        Log.d(TAG, "[DrawerState] Unknown: " + state);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this.mScrollListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        getViewTreeObserver().removeOnScrollChangedListener(this.mScrollListener);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View viewFindViewById = findViewById(com.easefun.polyv.livecommon.R.id.mdMenu);
        if (viewFindViewById != null) {
            removeView(viewFindViewById);
            setMenuView(viewFindViewById);
        }
        View viewFindViewById2 = findViewById(com.easefun.polyv.livecommon.R.id.mdContent);
        if (viewFindViewById2 != null) {
            removeView(viewFindViewById2);
            setContentView(viewFindViewById2);
        }
        if (getChildCount() > 2) {
            throw new IllegalStateException("Menu and content view added in xml must have id's @id/mdMenu and @id/mdContent");
        }
    }

    public abstract void onOffsetPixelsChanged(int offsetPixels);

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        restoreState(savedState.mState);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (!this.mCustomDropShadow) {
            setDropShadowColor(this.mDropShadowColor);
        }
        if (getPosition() != this.mResolvedPosition) {
            this.mResolvedPosition = getPosition();
            setOffsetPixels(this.mOffsetPixels * (-1.0f));
        }
        SlideDrawable slideDrawable = this.mSlideDrawable;
        if (slideDrawable != null) {
            slideDrawable.setIsRtl(layoutDirection == 1);
        }
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mState == null) {
            this.mState = new Bundle();
        }
        saveState(this.mState);
        savedState.mState = this.mState;
        return savedState;
    }

    public void openMenu() {
        openMenu(false);
    }

    public abstract void openMenu(boolean animate);

    public abstract void peekDrawer();

    public abstract void peekDrawer(long delay);

    public abstract void peekDrawer(long startDelay, long delay);

    @Override // android.view.View
    public void postOnAnimation(Runnable action) {
        super.postOnAnimation(action);
    }

    public void restoreState(Parcelable in) {
        this.mState = (Bundle) in;
    }

    public final Parcelable saveState() {
        if (this.mState == null) {
            this.mState = new Bundle();
        }
        saveState(this.mState);
        return this.mState;
    }

    public void saveState(Bundle state) {
    }

    public void setActiveView(View v2) {
        setActiveView(v2, 0);
    }

    public void setAllowIndicatorAnimation(boolean animate) {
        if (animate != this.mAllowIndicatorAnimation) {
            this.mAllowIndicatorAnimation = animate;
            completeAnimatingIndicator();
        }
    }

    public void setContentView(int layoutResId) {
        int i2 = this.mDragMode;
        if (i2 == 0) {
            this.mContentContainer.removeAllViews();
            LayoutInflater.from(getContext()).inflate(layoutResId, (ViewGroup) this.mContentContainer, true);
        } else {
            if (i2 != 1) {
                return;
            }
            this.mActivity.setContentView(layoutResId);
        }
    }

    public abstract void setDragAreaMenuBottom(int bottom);

    public void setDrawOverlay(boolean drawOverlay) {
        this.mDrawOverlay = drawOverlay;
    }

    public void setDrawerIndicatorEnabled(boolean enabled) {
        ActionBarHelper actionBarHelper = this.mActionBarHelper;
        if (actionBarHelper == null) {
            throw new IllegalStateException("setupUpIndicator(Activity) has not been called");
        }
        this.mDrawerIndicatorEnabled = enabled;
        if (enabled) {
            actionBarHelper.setActionBarUpIndicator(this.mSlideDrawable, isMenuVisible() ? this.mDrawerOpenContentDesc : this.mDrawerClosedContentDesc);
        } else {
            actionBarHelper.setActionBarUpIndicator(this.mThemeUpIndicator, 0);
        }
    }

    public void setDrawerState(int state) {
        int i2 = this.mDrawerState;
        if (state != i2) {
            this.mDrawerState = state;
            OnDrawerStateChangeListener onDrawerStateChangeListener = this.mOnDrawerStateChangeListener;
            if (onDrawerStateChangeListener != null) {
                onDrawerStateChangeListener.onDrawerStateChange(i2, state);
            }
        }
    }

    public void setDropShadow(Drawable drawable) {
        this.mDropShadowDrawable = drawable;
        this.mCustomDropShadow = drawable != null;
        invalidate();
    }

    public void setDropShadowColor(int color) {
        this.mDropShadowDrawable = new GradientDrawable(getDropShadowOrientation(), new int[]{color, 16777215 & color});
        invalidate();
    }

    public void setDropShadowEnabled(boolean enabled) {
        this.mDropShadowEnabled = enabled;
        invalidate();
    }

    public void setDropShadowSize(int size) {
        this.mDropShadowSize = size;
        invalidate();
    }

    public abstract void setHardwareLayerEnabled(boolean enabled);

    public void setMaxAnimationDuration(int duration) {
        this.mMaxAnimationDuration = duration;
    }

    public abstract void setMenuSize(int size);

    public void setMenuView(int layoutResId) {
        this.mMenuContainer.removeAllViews();
        View viewInflate = LayoutInflater.from(getContext()).inflate(layoutResId, (ViewGroup) this.mMenuContainer, false);
        this.mMenuView = viewInflate;
        this.mMenuContainer.addView(viewInflate);
    }

    public abstract void setOffsetMenuEnabled(boolean offsetMenu);

    public void setOffsetPixels(float offsetPixels) {
        int i2 = (int) this.mOffsetPixels;
        int i3 = (int) offsetPixels;
        this.mOffsetPixels = offsetPixels;
        if (this.mSlideDrawable != null) {
            this.mSlideDrawable.setOffset(Math.abs(offsetPixels) / this.mMenuSize);
            updateUpContentDescription();
        }
        if (i3 != i2) {
            onOffsetPixelsChanged(i3);
            this.mMenuVisible = i3 != 0;
            dispatchOnDrawerSlide(Math.abs(i3) / this.mMenuSize, i3);
        }
    }

    public void setOnDrawerStateChangeListener(OnDrawerStateChangeListener listener) {
        this.mOnDrawerStateChangeListener = listener;
    }

    public void setOnInterceptMoveEventListener(OnInterceptMoveEventListener listener) {
        this.mOnInterceptMoveEventListener = listener;
    }

    public void setPosition(Position position) {
        this.mPosition = position;
        this.mResolvedPosition = getPosition();
    }

    public void setSlideDrawable(int drawableRes) {
        setSlideDrawable(getResources().getDrawable(drawableRes));
    }

    public abstract void setTouchBezelSize(int size);

    public abstract void setTouchMode(int mode);

    public void setupUpIndicator(Activity activity) {
        if (this.mActionBarHelper == null) {
            ActionBarHelper actionBarHelper = new ActionBarHelper(activity);
            this.mActionBarHelper = actionBarHelper;
            this.mThemeUpIndicator = actionBarHelper.getThemeUpIndicator();
            if (this.mDrawerIndicatorEnabled) {
                this.mActionBarHelper.setActionBarUpIndicator(this.mSlideDrawable, isMenuVisible() ? this.mDrawerOpenContentDesc : this.mDrawerClosedContentDesc);
            }
        }
    }

    public void toggleMenu() {
        toggleMenu(true);
    }

    public abstract void toggleMenu(boolean animate);

    public void updateDropShadowRect() {
        int i2 = AnonymousClass3.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1) {
            Rect rect = this.mDropShadowRect;
            rect.top = 0;
            rect.bottom = getHeight();
            this.mDropShadowRect.right = ViewHelper.getLeft(this.mContentContainer);
            Rect rect2 = this.mDropShadowRect;
            rect2.left = rect2.right - this.mDropShadowSize;
            return;
        }
        if (i2 == 2) {
            Rect rect3 = this.mDropShadowRect;
            rect3.left = 0;
            rect3.right = getWidth();
            this.mDropShadowRect.bottom = ViewHelper.getTop(this.mContentContainer);
            Rect rect4 = this.mDropShadowRect;
            rect4.top = rect4.bottom - this.mDropShadowSize;
            return;
        }
        if (i2 == 3) {
            Rect rect5 = this.mDropShadowRect;
            rect5.top = 0;
            rect5.bottom = getHeight();
            this.mDropShadowRect.left = ViewHelper.getRight(this.mContentContainer);
            Rect rect6 = this.mDropShadowRect;
            rect6.right = rect6.left + this.mDropShadowSize;
            return;
        }
        if (i2 != 4) {
            return;
        }
        Rect rect7 = this.mDropShadowRect;
        rect7.left = 0;
        rect7.right = getWidth();
        this.mDropShadowRect.top = ViewHelper.getBottom(this.mContentContainer);
        Rect rect8 = this.mDropShadowRect;
        rect8.bottom = rect8.top + this.mDropShadowSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateIndicatorClipRect() {
        /*
            Method dump skipped, instructions count: 207
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.updateIndicatorClipRect():void");
    }

    public void updateTouchAreaSize() {
        int i2 = this.mTouchMode;
        if (i2 == 1) {
            this.mTouchSize = this.mTouchBezelSize;
        } else if (i2 == 2) {
            this.mTouchSize = getMeasuredWidth();
        } else {
            this.mTouchSize = 0;
        }
    }

    public void updateUpContentDescription() {
        ActionBarHelper actionBarHelper;
        int i2 = isMenuVisible() ? this.mDrawerOpenContentDesc : this.mDrawerClosedContentDesc;
        if (!this.mDrawerIndicatorEnabled || (actionBarHelper = this.mActionBarHelper) == null || i2 == this.mCurrentUpContentDesc) {
            return;
        }
        this.mCurrentUpContentDesc = i2;
        actionBarHelper.setActionBarDescription(i2);
    }

    public static PLVMenuDrawer attach(Activity activity, Type type) {
        return attach(activity, type, Position.START);
    }

    public void setActiveView(View v2, int position) {
        View view = this.mActiveView;
        this.mActiveView = v2;
        this.mActivePosition = position;
        if (this.mAllowIndicatorAnimation && view != null) {
            startAnimatingIndicator();
        }
        invalidate();
    }

    public void setSlideDrawable(Drawable drawable) {
        SlideDrawable slideDrawable = new SlideDrawable(drawable);
        this.mSlideDrawable = slideDrawable;
        slideDrawable.setIsRtl(ViewHelper.getLayoutDirection(this) == 1);
        ActionBarHelper actionBarHelper = this.mActionBarHelper;
        if (actionBarHelper != null) {
            actionBarHelper.setDisplayShowHomeAsUpEnabled(true);
            if (this.mDrawerIndicatorEnabled) {
                this.mActionBarHelper.setActionBarUpIndicator(this.mSlideDrawable, isMenuVisible() ? this.mDrawerOpenContentDesc : this.mDrawerClosedContentDesc);
            }
        }
    }

    public static PLVMenuDrawer attach(Activity activity, Position position) {
        return attach(activity, Type.BEHIND, position);
    }

    public PLVMenuDrawer(Context context) {
        this(context, (AttributeSet) null);
    }

    public static PLVMenuDrawer attach(Activity activity, Type type, Position position) {
        return attach(activity, type, position, 0, null);
    }

    public void setDropShadow(int resId) {
        setDropShadow(getResources().getDrawable(resId));
    }

    public void setMenuView(View view) {
        setMenuView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    public PLVMenuDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, com.easefun.polyv.livecommon.R.attr.menuDrawerStyle);
    }

    public static PLVMenuDrawer attach(Activity activity, Type type, Position position, int dragMode, ViewGroup container) {
        PLVMenuDrawer pLVMenuDrawerCreateMenuDrawer = createMenuDrawer(activity, dragMode, position, type);
        pLVMenuDrawerCreateMenuDrawer.setId(com.easefun.polyv.livecommon.R.id.md__drawer);
        if (dragMode == 0) {
            attachToContent(activity, pLVMenuDrawerCreateMenuDrawer);
        } else if (dragMode == 1) {
            attachToDecor(activity, pLVMenuDrawerCreateMenuDrawer);
        } else if (dragMode == 2) {
            attachToContainer(activity, pLVMenuDrawerCreateMenuDrawer, container);
        } else {
            throw new RuntimeException("Unknown menu mode: " + dragMode);
        }
        return pLVMenuDrawerCreateMenuDrawer;
    }

    public void attachToContainer() {
        ViewGroup viewGroup = this.mLayoutContainer;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            this.mLayoutContainer.addView(this);
        }
    }

    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    public void setMenuView(View view, ViewGroup.LayoutParams params) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        this.mMenuView = view;
        this.mMenuContainer.removeAllViews();
        this.mMenuContainer.addView(view, params);
    }

    public PLVMenuDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mActiveRect = new Rect();
        this.mTempRect = new Rect();
        this.mDragMode = 0;
        this.mDrawerState = 0;
        this.mTouchMode = 1;
        this.mHardwareLayersEnabled = true;
        this.mIndicatorRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                PLVMenuDrawer.this.animateIndicatorInvalidate();
            }
        };
        this.mMaxAnimationDuration = 600;
        this.mIndicatorClipRect = new Rect();
        this.mDropShadowRect = new Rect();
        this.mScrollListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.2
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                PLVMenuDrawer pLVMenuDrawer = PLVMenuDrawer.this;
                View view = pLVMenuDrawer.mActiveView;
                if (view == null || !pLVMenuDrawer.isViewDescendant(view)) {
                    return;
                }
                PLVMenuDrawer pLVMenuDrawer2 = PLVMenuDrawer.this;
                pLVMenuDrawer2.mActiveView.getDrawingRect(pLVMenuDrawer2.mTempRect);
                PLVMenuDrawer pLVMenuDrawer3 = PLVMenuDrawer.this;
                pLVMenuDrawer3.offsetDescendantRectToMyCoords(pLVMenuDrawer3.mActiveView, pLVMenuDrawer3.mTempRect);
                int i2 = PLVMenuDrawer.this.mTempRect.left;
                PLVMenuDrawer pLVMenuDrawer4 = PLVMenuDrawer.this;
                if (i2 == pLVMenuDrawer4.mActiveRect.left) {
                    int i3 = pLVMenuDrawer4.mTempRect.top;
                    PLVMenuDrawer pLVMenuDrawer5 = PLVMenuDrawer.this;
                    if (i3 == pLVMenuDrawer5.mActiveRect.top) {
                        int i4 = pLVMenuDrawer5.mTempRect.right;
                        PLVMenuDrawer pLVMenuDrawer6 = PLVMenuDrawer.this;
                        if (i4 == pLVMenuDrawer6.mActiveRect.right && pLVMenuDrawer6.mTempRect.bottom == PLVMenuDrawer.this.mActiveRect.bottom) {
                            return;
                        }
                    }
                }
                PLVMenuDrawer.this.invalidate();
            }
        };
        initDrawer(context, attrs, defStyle);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        int i2 = this.mDragMode;
        if (i2 == 0) {
            this.mContentContainer.removeAllViews();
            this.mContentContainer.addView(view, params);
        } else {
            if (i2 != 1) {
                return;
            }
            this.mActivity.setContentView(view, params);
        }
    }
}
