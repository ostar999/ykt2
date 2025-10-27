package com.zhpan.bannerview.manager;

import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.option.IndicatorOptions;

/* loaded from: classes8.dex */
public class BannerOptions {
    public static final int DEFAULT_REVEAL_WIDTH = -1000;
    private boolean disallowParentInterceptDownEvent;
    private int indicatorGravity;
    private int interval;
    private boolean isCanLoop;
    private IndicatorMargin mIndicatorMargin;
    private int roundRadius;
    private float[] roundRadiusArray;
    private boolean rtl;
    private int scrollDuration;
    private int offScreenPageLimit = -1;
    private boolean isAutoPlay = false;
    private int pageStyle = 0;
    private float pageScale = 0.85f;
    private int mIndicatorVisibility = 0;
    private boolean userInputEnabled = true;
    private int orientation = 0;
    private boolean stopLoopWhenDetachedFromWindow = true;
    private boolean autoScrollSmoothly = true;
    private final IndicatorOptions mIndicatorOptions = new IndicatorOptions();
    private int pageMargin = BannerUtils.dp2px(20.0f);
    private int rightRevealWidth = -1000;
    private int leftRevealWidth = -1000;

    public static class IndicatorMargin {
        private final int bottom;
        private final int left;
        private final int right;

        /* renamed from: top, reason: collision with root package name */
        private final int f26564top;

        public IndicatorMargin(int i2, int i3, int i4, int i5) {
            this.left = i2;
            this.right = i4;
            this.f26564top = i3;
            this.bottom = i5;
        }

        public int getBottom() {
            return this.bottom;
        }

        public int getLeft() {
            return this.left;
        }

        public int getRight() {
            return this.right;
        }

        public int getTop() {
            return this.f26564top;
        }
    }

    public int getCheckedIndicatorWidth() {
        return (int) this.mIndicatorOptions.getCheckedSliderWidth();
    }

    public int getIndicatorCheckedColor() {
        return this.mIndicatorOptions.getCheckedSliderColor();
    }

    public float getIndicatorGap() {
        return this.mIndicatorOptions.getSliderGap();
    }

    public int getIndicatorGravity() {
        return this.indicatorGravity;
    }

    public float getIndicatorHeight() {
        return this.mIndicatorOptions.getSliderHeight();
    }

    public IndicatorMargin getIndicatorMargin() {
        return this.mIndicatorMargin;
    }

    public int getIndicatorNormalColor() {
        return this.mIndicatorOptions.getNormalSliderColor();
    }

    public IndicatorOptions getIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public int getIndicatorSlideMode() {
        return this.mIndicatorOptions.getSlideMode();
    }

    public int getIndicatorStyle() {
        return this.mIndicatorOptions.getIndicatorStyle();
    }

    public int getIndicatorVisibility() {
        return this.mIndicatorVisibility;
    }

    public int getInterval() {
        return this.interval;
    }

    public int getLeftRevealWidth() {
        return this.leftRevealWidth;
    }

    public int getNormalIndicatorWidth() {
        return (int) this.mIndicatorOptions.getNormalSliderWidth();
    }

    public int getOffScreenPageLimit() {
        return this.offScreenPageLimit;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getPageMargin() {
        return this.pageMargin;
    }

    public float getPageScale() {
        return this.pageScale;
    }

    public int getPageStyle() {
        return this.pageStyle;
    }

    public int getRightRevealWidth() {
        return this.rightRevealWidth;
    }

    public int getRoundRectRadius() {
        return this.roundRadius;
    }

    public float[] getRoundRectRadiusArray() {
        return this.roundRadiusArray;
    }

    public int getScrollDuration() {
        return this.scrollDuration;
    }

    public boolean isAutoPlay() {
        return this.isAutoPlay;
    }

    public boolean isAutoScrollSmoothly() {
        return this.autoScrollSmoothly;
    }

    public boolean isCanLoop() {
        return this.isCanLoop;
    }

    public boolean isDisallowParentInterceptDownEvent() {
        return this.disallowParentInterceptDownEvent;
    }

    public boolean isRtl() {
        return this.rtl;
    }

    public boolean isStopLoopWhenDetachedFromWindow() {
        return this.stopLoopWhenDetachedFromWindow;
    }

    public boolean isUserInputEnabled() {
        return this.userInputEnabled;
    }

    public void resetIndicatorOptions() {
        this.mIndicatorOptions.setCurrentPosition(0);
        this.mIndicatorOptions.setSlideProgress(0.0f);
    }

    public void setAutoPlay(boolean z2) {
        this.isAutoPlay = z2;
    }

    public void setAutoScrollSmoothly(boolean z2) {
        this.autoScrollSmoothly = z2;
    }

    public void setCanLoop(boolean z2) {
        this.isCanLoop = z2;
    }

    public void setDisallowParentInterceptDownEvent(boolean z2) {
        this.disallowParentInterceptDownEvent = z2;
    }

    public void setIndicatorGap(float f2) {
        this.mIndicatorOptions.setSliderGap(f2);
    }

    public void setIndicatorGravity(int i2) {
        this.indicatorGravity = i2;
    }

    public void setIndicatorHeight(int i2) {
        this.mIndicatorOptions.setSliderHeight(i2);
    }

    public void setIndicatorMargin(int i2, int i3, int i4, int i5) {
        this.mIndicatorMargin = new IndicatorMargin(i2, i3, i4, i5);
    }

    public void setIndicatorSlideMode(int i2) {
        this.mIndicatorOptions.setSlideMode(i2);
    }

    public void setIndicatorSliderColor(int i2, int i3) {
        this.mIndicatorOptions.setSliderColor(i2, i3);
    }

    public void setIndicatorSliderWidth(int i2, int i3) {
        this.mIndicatorOptions.setSliderWidth(i2, i3);
    }

    public void setIndicatorStyle(int i2) {
        this.mIndicatorOptions.setIndicatorStyle(i2);
    }

    public void setIndicatorVisibility(int i2) {
        this.mIndicatorVisibility = i2;
    }

    public void setInterval(int i2) {
        this.interval = i2;
    }

    public void setLeftRevealWidth(int i2) {
        this.leftRevealWidth = i2;
    }

    public void setOffScreenPageLimit(int i2) {
        this.offScreenPageLimit = i2;
    }

    public void setOrientation(int i2) {
        this.orientation = i2;
        this.mIndicatorOptions.setOrientation(i2);
    }

    public void setPageMargin(int i2) {
        this.pageMargin = i2;
    }

    public void setPageScale(float f2) {
        this.pageScale = f2;
    }

    public void setPageStyle(int i2) {
        this.pageStyle = i2;
    }

    public void setRightRevealWidth(int i2) {
        this.rightRevealWidth = i2;
    }

    public void setRoundRectRadius(int i2) {
        this.roundRadius = i2;
    }

    public void setRtl(boolean z2) {
        this.rtl = z2;
        this.mIndicatorOptions.setOrientation(z2 ? 3 : 0);
    }

    public void setScrollDuration(int i2) {
        this.scrollDuration = i2;
    }

    public void setStopLoopWhenDetachedFromWindow(boolean z2) {
        this.stopLoopWhenDetachedFromWindow = z2;
    }

    public void setUserInputEnabled(boolean z2) {
        this.userInputEnabled = z2;
    }

    public void showIndicatorWhenOneItem(boolean z2) {
        this.mIndicatorOptions.setShowIndicatorOneItem(z2);
    }

    public void setRoundRectRadius(int i2, int i3, int i4, int i5) {
        this.roundRadiusArray = new float[]{f, f, f, f, f, f, f, f};
        float f2 = i2;
        float f3 = i3;
        float f4 = i5;
        float f5 = i4;
    }
}
