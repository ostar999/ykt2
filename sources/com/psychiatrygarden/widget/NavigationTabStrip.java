package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import androidx.viewpager.widget.ViewPager;
import com.yikaobang.yixue.R;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

/* loaded from: classes6.dex */
public class NavigationTabStrip extends View implements ViewPager.OnPageChangeListener {
    private static final int DEFAULT_ACTIVE_COLOR = -1;
    private static final int DEFAULT_ANIMATION_DURATION = 350;
    private static final float DEFAULT_CORNER_RADIUS = 5.0f;
    private static final int DEFAULT_INACTIVE_COLOR = -7829368;
    private static final int DEFAULT_STRIP_COLOR = -65536;
    private static final float DEFAULT_STRIP_FACTOR = 2.5f;
    private static final float DEFAULT_STRIP_WEIGHT = 10.0f;
    private static final int DEFAULT_TITLE_SIZE = 0;
    private static final int INVALID_INDEX = -1;
    private static final float MAX_FRACTION = 1.0f;
    private static final float MIN_FRACTION = 0.0f;
    private static final String PREVIEW_TITLE = "Title";
    private static final float TITLE_SIZE_FRACTION = 0.35f;
    private int mActiveColor;
    private int mAnimationDuration;
    private final ValueAnimator mAnimator;
    private Animator.AnimatorListener mAnimatorListener;
    private final RectF mBounds;
    private final ArgbEvaluator mColorEvaluator;
    private float mCornersRadius;
    private float mEndStripX;
    private float mFraction;
    private int mInactiveColor;
    private int mIndex;
    private boolean mIsActionDown;
    private boolean mIsResizeIn;
    private boolean mIsSetIndexFromTabBar;
    private boolean mIsTabActionDown;
    private boolean mIsViewPagerMode;
    private int mLastIndex;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private OnTabStripSelectedIndexListener mOnTabStripSelectedIndexListener;
    private final ResizeInterpolator mResizeInterpolator;
    private int mScrollState;
    private float mStartStripX;
    private final RectF mStripBounds;
    private StripGravity mStripGravity;
    private float mStripLeft;
    private final Paint mStripPaint;
    private float mStripRight;
    private StripType mStripType;
    private float mStripWeight;
    private float mTabSize;
    private final Rect mTitleBounds;
    private final Paint mTitlePaint;
    private float mTitleSize;
    private String[] mTitles;
    private Typeface mTypeface;
    private ViewPager mViewPager;

    public interface OnTabStripSelectedIndexListener {
        void onEndTabSelected(final String title, final int index);

        void onStartTabSelected(final String title, final int index);
    }

    public static class ResizeInterpolator implements Interpolator {
        private float mFactor;
        private boolean mResizeIn;

        private ResizeInterpolator() {
        }

        public float getFactor() {
            return this.mFactor;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(final float input) {
            return this.mResizeIn ? (float) (1.0d - Math.pow(1.0f - input, this.mFactor * 2.0f)) : (float) Math.pow(input, this.mFactor * 2.0f);
        }

        public float getResizeInterpolation(final float input, final boolean resizeIn) {
            this.mResizeIn = resizeIn;
            return getInterpolation(input);
        }

        public void setFactor(final float factor) {
            this.mFactor = factor;
        }
    }

    public class ResizeViewPagerScroller extends Scroller {
        public ResizeViewPagerScroller(Context context) {
            super(context, new AccelerateDecelerateInterpolator());
        }

        @Override // android.widget.Scroller
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, NavigationTabStrip.this.mAnimationDuration);
        }

        @Override // android.widget.Scroller
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, NavigationTabStrip.this.mAnimationDuration);
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.psychiatrygarden.widget.NavigationTabStrip.SavedState.1
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
        int index;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.index);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.index = in.readInt();
        }
    }

    public enum StripGravity {
        BOTTOM,
        TOP;

        private static final int BOTTOM_INDEX = 0;
        private static final int TOP_INDEX = 1;
    }

    public enum StripType {
        LINE,
        POINT;

        private static final int LINE_INDEX = 0;
        private static final int POINT_INDEX = 1;
    }

    public NavigationTabStrip(final Context context) {
        this(context, null);
    }

    private void notifyDataSetChanged() {
        postInvalidate();
    }

    private void resetScroller() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (this.mViewPager == null) {
            return;
        }
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this.mViewPager, new ResizeViewPagerScroller(getContext()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void setStripGravity(final int index) {
        if (index != 1) {
            setStripGravity(StripGravity.BOTTOM);
        } else {
            setStripGravity(StripGravity.TOP);
        }
    }

    private void setStripType(final int index) {
        if (index != 1) {
            setStripType(StripType.LINE);
        } else {
            setStripType(StripType.POINT);
        }
    }

    private void updateCurrentTitle(final float interpolation) {
        this.mTitlePaint.setColor(((Integer) this.mColorEvaluator.evaluate(interpolation, Integer.valueOf(this.mInactiveColor), Integer.valueOf(this.mActiveColor))).intValue());
    }

    private void updateInactiveTitle() {
        this.mTitlePaint.setColor(this.mInactiveColor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIndicatorPosition(final float fraction) {
        this.mFraction = fraction;
        float f2 = this.mStartStripX;
        float resizeInterpolation = this.mResizeInterpolator.getResizeInterpolation(fraction, this.mIsResizeIn);
        float f3 = this.mEndStripX;
        float f4 = this.mStartStripX;
        this.mStripLeft = f2 + (resizeInterpolation * (f3 - f4));
        this.mStripRight = f4 + (this.mStripType == StripType.LINE ? this.mTabSize : this.mStripWeight) + (this.mResizeInterpolator.getResizeInterpolation(fraction, !this.mIsResizeIn) * (this.mEndStripX - this.mStartStripX));
        postInvalidate();
    }

    private void updateLastTitle(final float lastInterpolation) {
        this.mTitlePaint.setColor(((Integer) this.mColorEvaluator.evaluate(lastInterpolation, Integer.valueOf(this.mActiveColor), Integer.valueOf(this.mInactiveColor))).intValue());
    }

    public int getActiveColor() {
        return this.mActiveColor;
    }

    public int getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public float getCornersRadius() {
        return this.mCornersRadius;
    }

    public int getInactiveColor() {
        return this.mInactiveColor;
    }

    public OnTabStripSelectedIndexListener getOnTabStripSelectedIndexListener() {
        return this.mOnTabStripSelectedIndexListener;
    }

    public int getStripColor() {
        return this.mStripPaint.getColor();
    }

    public float getStripFactor() {
        return this.mResizeInterpolator.getFactor();
    }

    public StripGravity getStripGravity() {
        return this.mStripGravity;
    }

    public StripType getStripType() {
        return this.mStripType;
    }

    public int getTabIndex() {
        return this.mIndex;
    }

    public float getTitleSize() {
        return this.mTitleSize;
    }

    public String[] getTitles() {
        return this.mTitles;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    @Override // android.view.View
    public void onConfigurationChanged(final Configuration newConfig) throws Resources.NotFoundException {
        super.onConfigurationChanged(newConfig);
        requestLayout();
        final int i2 = this.mIndex;
        setTabIndex(-1, true);
        post(new Runnable() { // from class: com.psychiatrygarden.widget.NavigationTabStrip.5
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                NavigationTabStrip.this.setTabIndex(i2, true);
            }
        });
    }

    @Override // android.view.View
    public void onDraw(final Canvas canvas) {
        RectF rectF = this.mStripBounds;
        float f2 = this.mStripLeft;
        StripType stripType = this.mStripType;
        StripType stripType2 = StripType.POINT;
        float f3 = f2 - (stripType == stripType2 ? this.mStripWeight * 0.5f : 0.0f);
        StripGravity stripGravity = this.mStripGravity;
        StripGravity stripGravity2 = StripGravity.BOTTOM;
        rectF.set(f3, stripGravity == stripGravity2 ? this.mBounds.height() - this.mStripWeight : 0.0f, this.mStripRight - (this.mStripType == stripType2 ? this.mStripWeight * 0.5f : 0.0f), this.mStripGravity == stripGravity2 ? this.mBounds.height() : this.mStripWeight);
        float f4 = this.mCornersRadius;
        if (f4 == 0.0f) {
            canvas.drawRect(this.mStripBounds, this.mStripPaint);
        } else {
            canvas.drawRoundRect(this.mStripBounds, f4, f4, this.mStripPaint);
        }
        int i2 = 0;
        while (true) {
            String[] strArr = this.mTitles;
            if (i2 >= strArr.length) {
                return;
            }
            String str = strArr[i2];
            float f5 = this.mTabSize;
            float f6 = (i2 * f5) + (f5 * 0.5f);
            this.mTitlePaint.getTextBounds(str, 0, str.length(), this.mTitleBounds);
            float fHeight = (((this.mBounds.height() - this.mStripWeight) * 0.5f) + (this.mTitleBounds.height() * 0.5f)) - this.mTitleBounds.bottom;
            float resizeInterpolation = this.mResizeInterpolator.getResizeInterpolation(this.mFraction, true);
            float resizeInterpolation2 = this.mResizeInterpolator.getResizeInterpolation(this.mFraction, false);
            if (!this.mIsSetIndexFromTabBar) {
                int i3 = this.mIndex;
                if (i2 != i3 && i2 != i3 + 1) {
                    updateInactiveTitle();
                } else if (i2 == i3 + 1) {
                    updateCurrentTitle(resizeInterpolation);
                } else if (i2 == i3) {
                    updateLastTitle(resizeInterpolation2);
                }
            } else if (this.mIndex == i2) {
                updateCurrentTitle(resizeInterpolation);
            } else if (this.mLastIndex == i2) {
                updateLastTitle(resizeInterpolation2);
            } else {
                updateInactiveTitle();
            }
            canvas.drawText(str, f6, fHeight + (this.mStripGravity == StripGravity.TOP ? this.mStripWeight : 0.0f), this.mTitlePaint);
            i2++;
        }
    }

    @Override // android.view.View
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float size = View.MeasureSpec.getSize(widthMeasureSpec);
        float size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        this.mBounds.set(0.0f, 0.0f, size, size2);
        if (this.mTitles.length == 0 || size == 0.0f || size2 == 0.0f) {
            return;
        }
        this.mTabSize = size / r0.length;
        if (((int) this.mTitleSize) == 0) {
            setTitleSize((size2 - this.mStripWeight) * TITLE_SIZE_FRACTION);
        }
        if (isInEditMode() || !this.mIsViewPagerMode) {
            this.mIsSetIndexFromTabBar = true;
            if (isInEditMode()) {
                this.mIndex = new Random().nextInt(this.mTitles.length);
            }
            float f2 = this.mIndex;
            float f3 = this.mTabSize;
            float f4 = (f2 * f3) + (this.mStripType == StripType.POINT ? f3 * 0.5f : 0.0f);
            this.mStartStripX = f4;
            this.mEndStripX = f4;
            updateIndicatorPosition(1.0f);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(final int state) {
        if (state == 0) {
            this.mFraction = 0.0f;
            this.mIsSetIndexFromTabBar = false;
            ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(this.mIndex);
            } else {
                OnTabStripSelectedIndexListener onTabStripSelectedIndexListener = this.mOnTabStripSelectedIndexListener;
                if (onTabStripSelectedIndexListener != null) {
                    String[] strArr = this.mTitles;
                    int i2 = this.mIndex;
                    onTabStripSelectedIndexListener.onEndTabSelected(strArr[i2], i2);
                }
            }
        }
        this.mScrollState = state;
        ViewPager.OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
        if (onPageChangeListener2 != null) {
            onPageChangeListener2.onPageScrollStateChanged(state);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, final int positionOffsetPixels) {
        if (!this.mIsSetIndexFromTabBar) {
            int i2 = this.mIndex;
            this.mIsResizeIn = position < i2;
            this.mLastIndex = i2;
            this.mIndex = position;
            float f2 = this.mTabSize;
            float f3 = (position * f2) + (this.mStripType == StripType.POINT ? 0.5f * f2 : 0.0f);
            this.mStartStripX = f3;
            this.mEndStripX = f3 + f2;
            updateIndicatorPosition(positionOffset);
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(final int position) {
        if (this.mScrollState == 0) {
            int i2 = this.mIndex;
            this.mIsResizeIn = position < i2;
            this.mLastIndex = i2;
            this.mIndex = position;
            postInvalidate();
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mIndex = savedState.index;
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.index = this.mIndex;
        return savedState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0030, code lost:
    
        if (r4.mIsActionDown != false) goto L31;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(final android.view.MotionEvent r5) throws android.content.res.Resources.NotFoundException {
        /*
            r4 = this;
            android.animation.ValueAnimator r0 = r4.mAnimator
            boolean r0 = r0.isRunning()
            r1 = 1
            if (r0 == 0) goto La
            return r1
        La:
            int r0 = r4.mScrollState
            if (r0 == 0) goto Lf
            return r1
        Lf:
            int r0 = r5.getAction()
            r2 = 0
            if (r0 == 0) goto L47
            if (r0 == r1) goto L33
            r3 = 2
            if (r0 == r3) goto L1c
            goto L42
        L1c:
            boolean r0 = r4.mIsTabActionDown
            if (r0 == 0) goto L2e
            androidx.viewpager.widget.ViewPager r0 = r4.mViewPager
            float r5 = r5.getX()
            float r2 = r4.mTabSize
            float r5 = r5 / r2
            int r5 = (int) r5
            r0.setCurrentItem(r5, r1)
            goto L5d
        L2e:
            boolean r0 = r4.mIsActionDown
            if (r0 == 0) goto L33
            goto L5d
        L33:
            boolean r0 = r4.mIsActionDown
            if (r0 == 0) goto L42
            float r5 = r5.getX()
            float r0 = r4.mTabSize
            float r5 = r5 / r0
            int r5 = (int) r5
            r4.setTabIndex(r5)
        L42:
            r4.mIsTabActionDown = r2
            r4.mIsActionDown = r2
            goto L5d
        L47:
            r4.mIsActionDown = r1
            boolean r0 = r4.mIsViewPagerMode
            if (r0 != 0) goto L4e
            goto L5d
        L4e:
            float r5 = r5.getX()
            float r0 = r4.mTabSize
            float r5 = r5 / r0
            int r5 = (int) r5
            int r0 = r4.mIndex
            if (r5 != r0) goto L5b
            r2 = r1
        L5b:
            r4.mIsTabActionDown = r2
        L5d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.NavigationTabStrip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setActiveColor(final int activeColor) {
        this.mActiveColor = activeColor;
        postInvalidate();
    }

    public void setAnimationDuration(final int animationDuration) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mAnimationDuration = animationDuration;
        this.mAnimator.setDuration(animationDuration);
        resetScroller();
    }

    public void setCornersRadius(final float cornersRadius) {
        this.mCornersRadius = cornersRadius;
        postInvalidate();
    }

    public void setInactiveColor(final int inactiveColor) {
        this.mInactiveColor = inactiveColor;
        postInvalidate();
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void setOnTabStripSelectedIndexListener(final OnTabStripSelectedIndexListener onTabStripSelectedIndexListener) {
        this.mOnTabStripSelectedIndexListener = onTabStripSelectedIndexListener;
        if (this.mAnimatorListener == null) {
            this.mAnimatorListener = new Animator.AnimatorListener() { // from class: com.psychiatrygarden.widget.NavigationTabStrip.4
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(final Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(final Animator animation) {
                    if (NavigationTabStrip.this.mIsViewPagerMode) {
                        return;
                    }
                    animation.removeListener(this);
                    animation.addListener(this);
                    if (NavigationTabStrip.this.mOnTabStripSelectedIndexListener != null) {
                        NavigationTabStrip.this.mOnTabStripSelectedIndexListener.onEndTabSelected(NavigationTabStrip.this.mTitles[NavigationTabStrip.this.mIndex], NavigationTabStrip.this.mIndex);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(final Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(final Animator animation) {
                    if (NavigationTabStrip.this.mOnTabStripSelectedIndexListener != null) {
                        NavigationTabStrip.this.mOnTabStripSelectedIndexListener.onStartTabSelected(NavigationTabStrip.this.mTitles[NavigationTabStrip.this.mIndex], NavigationTabStrip.this.mIndex);
                    }
                    animation.removeListener(this);
                    animation.addListener(this);
                }
            };
        }
        this.mAnimator.removeListener(this.mAnimatorListener);
        this.mAnimator.addListener(this.mAnimatorListener);
    }

    public void setStripColor(final int color) {
        this.mStripPaint.setColor(color);
        postInvalidate();
    }

    public void setStripFactor(final float factor) {
        this.mResizeInterpolator.setFactor(factor);
    }

    public void setStripWeight(final float stripWeight) {
        this.mStripWeight = stripWeight;
        requestLayout();
    }

    public void setTabIndex(int index) throws Resources.NotFoundException {
        setTabIndex(index, false);
    }

    public void setTitleSize(final float titleSize) {
        this.mTitleSize = titleSize;
        this.mTitlePaint.setTextSize(titleSize);
        postInvalidate();
    }

    public void setTitles(final int... titleResIds) {
        String[] strArr = new String[titleResIds.length];
        for (int i2 = 0; i2 < titleResIds.length; i2++) {
            strArr[i2] = getResources().getString(titleResIds[i2]);
        }
        setTitles(strArr);
    }

    public void setTypeface(final String typeface) {
        Typeface typefaceCreate;
        try {
            typefaceCreate = Typeface.create(Typeface.DEFAULT, 0);
        } catch (Exception e2) {
            typefaceCreate = Typeface.create(Typeface.DEFAULT, 0);
            e2.printStackTrace();
        }
        setTypeface(typefaceCreate);
    }

    public void setViewPager(final ViewPager viewPager) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (viewPager == null) {
            this.mIsViewPagerMode = false;
            return;
        }
        ViewPager viewPager2 = this.mViewPager;
        if (viewPager2 == viewPager) {
            return;
        }
        if (viewPager2 != null) {
            viewPager2.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not provide adapter instance.");
        }
        this.mIsViewPagerMode = true;
        this.mViewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        resetScroller();
        postInvalidate();
    }

    public NavigationTabStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setTabIndex(int index, boolean force) throws Resources.NotFoundException {
        if (this.mAnimator.isRunning()) {
            return;
        }
        String[] strArr = this.mTitles;
        if (strArr.length == 0) {
            return;
        }
        int i2 = this.mIndex;
        if (i2 == -1) {
            force = true;
        }
        if (index == i2) {
            return;
        }
        int iMax = Math.max(0, Math.min(index, strArr.length - 1));
        int i3 = this.mIndex;
        this.mIsResizeIn = iMax < i3;
        this.mLastIndex = i3;
        this.mIndex = iMax;
        this.mIsSetIndexFromTabBar = true;
        if (this.mIsViewPagerMode) {
            ViewPager viewPager = this.mViewPager;
            if (viewPager == null) {
                throw new IllegalStateException("ViewPager is null.");
            }
            viewPager.setCurrentItem(iMax, true);
        }
        this.mStartStripX = this.mStripLeft;
        float f2 = this.mIndex;
        float f3 = this.mTabSize;
        this.mEndStripX = (f2 * f3) + (this.mStripType == StripType.POINT ? f3 * 0.5f : 0.0f);
        if (force) {
            updateIndicatorPosition(1.0f);
        } else {
            this.mAnimator.start();
        }
    }

    public NavigationTabStrip(Context context, AttributeSet attributeSet, int i2) {
        String[] strArr;
        String[] strArr2;
        super(context, attributeSet, i2);
        this.mBounds = new RectF();
        this.mStripBounds = new RectF();
        this.mTitleBounds = new Rect();
        int i3 = 5;
        this.mStripPaint = new Paint(i3) { // from class: com.psychiatrygarden.widget.NavigationTabStrip.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.mTitlePaint = new TextPaint(i3) { // from class: com.psychiatrygarden.widget.NavigationTabStrip.2
            {
                setTextAlign(Paint.Align.CENTER);
            }
        };
        this.mAnimator = new ValueAnimator();
        this.mColorEvaluator = new ArgbEvaluator();
        String[] stringArray = null;
        this.mResizeInterpolator = new ResizeInterpolator();
        this.mLastIndex = -1;
        this.mIndex = -1;
        setWillNotDraw(false);
        setLayerType(2, null);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NavigationTabStrip);
        try {
            setStripColor(typedArrayObtainStyledAttributes.getColor(2, -65536));
            setTitleSize(typedArrayObtainStyledAttributes.getDimension(7, 0.0f));
            setStripWeight(typedArrayObtainStyledAttributes.getDimension(11, DEFAULT_STRIP_WEIGHT));
            setStripFactor(typedArrayObtainStyledAttributes.getFloat(4, DEFAULT_STRIP_FACTOR));
            setStripType(typedArrayObtainStyledAttributes.getInt(9, 0));
            setStripGravity(typedArrayObtainStyledAttributes.getInt(5, 0));
            setTypeface(typedArrayObtainStyledAttributes.getString(10));
            setInactiveColor(typedArrayObtainStyledAttributes.getColor(6, DEFAULT_INACTIVE_COLOR));
            setActiveColor(typedArrayObtainStyledAttributes.getColor(0, -1));
            setAnimationDuration(typedArrayObtainStyledAttributes.getInteger(1, 350));
            setCornersRadius(typedArrayObtainStyledAttributes.getDimension(3, DEFAULT_CORNER_RADIUS));
            try {
                try {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(8, 0);
                    if (resourceId != 0) {
                        stringArray = typedArrayObtainStyledAttributes.getResources().getStringArray(resourceId);
                    }
                    if (stringArray == null) {
                        if (isInEditMode()) {
                            stringArray = new String[new Random().nextInt(5) + 1];
                            Arrays.fill(stringArray, "Title");
                        } else {
                            stringArray = new String[0];
                        }
                    }
                    setTitles(stringArray);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (isInEditMode()) {
                        strArr2 = new String[new Random().nextInt(5) + 1];
                        Arrays.fill(strArr2, "Title");
                    } else {
                        strArr2 = new String[0];
                    }
                    setTitles(strArr2);
                }
                this.mAnimator.setFloatValues(0.0f, 1.0f);
                this.mAnimator.setInterpolator(new LinearInterpolator());
                this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.NavigationTabStrip.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(final ValueAnimator animation) {
                        NavigationTabStrip.this.updateIndicatorPosition(((Float) animation.getAnimatedValue()).floatValue());
                    }
                });
            } catch (Throwable th) {
                if (isInEditMode()) {
                    strArr = new String[new Random().nextInt(5) + 1];
                    Arrays.fill(strArr, "Title");
                } else {
                    strArr = new String[0];
                }
                setTitles(strArr);
                throw th;
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void setStripGravity(final StripGravity stripGravity) {
        this.mStripGravity = stripGravity;
        requestLayout();
    }

    public void setStripType(final StripType stripType) {
        this.mStripType = stripType;
        requestLayout();
    }

    public void setTitles(final String... titles) {
        for (int i2 = 0; i2 < titles.length; i2++) {
            titles[i2] = titles[i2].toUpperCase();
        }
        this.mTitles = titles;
        requestLayout();
    }

    public void setTypeface(final Typeface typeface) {
        this.mTypeface = typeface;
        this.mTitlePaint.setTypeface(typeface);
        postInvalidate();
    }

    public void setViewPager(final ViewPager viewPager, int index) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        setViewPager(viewPager);
        this.mIndex = index;
        if (this.mIsViewPagerMode) {
            this.mViewPager.setCurrentItem(index, true);
        }
        postInvalidate();
    }
}
