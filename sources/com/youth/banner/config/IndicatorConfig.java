package com.youth.banner.config;

import androidx.annotation.ColorInt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes8.dex */
public class IndicatorConfig {
    private int currentPosition;
    private int indicatorSize;
    private Margins margins;
    private int gravity = 1;
    private int indicatorSpace = BannerConfig.INDICATOR_SPACE;
    private int normalWidth = BannerConfig.INDICATOR_NORMAL_WIDTH;
    private int selectedWidth = BannerConfig.INDICATOR_SELECTED_WIDTH;

    @ColorInt
    private int normalColor = BannerConfig.INDICATOR_NORMAL_COLOR;

    @ColorInt
    private int selectedColor = BannerConfig.INDICATOR_SELECTED_COLOR;
    private int radius = BannerConfig.INDICATOR_RADIUS;
    private int height = BannerConfig.INDICATOR_HEIGHT;
    private boolean attachToBanner = true;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int CENTER = 1;
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    public static class Margins {
        public int bottomMargin;
        public int leftMargin;
        public int rightMargin;
        public int topMargin;

        public Margins() {
            this(BannerConfig.INDICATOR_MARGIN);
        }

        public Margins(int i2) {
            this(i2, i2, i2, i2);
        }

        public Margins(int i2, int i3, int i4, int i5) {
            this.leftMargin = i2;
            this.topMargin = i3;
            this.rightMargin = i4;
            this.bottomMargin = i5;
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public int getGravity() {
        return this.gravity;
    }

    public int getHeight() {
        return this.height;
    }

    public int getIndicatorSize() {
        return this.indicatorSize;
    }

    public int getIndicatorSpace() {
        return this.indicatorSpace;
    }

    public Margins getMargins() {
        if (this.margins == null) {
            setMargins(new Margins());
        }
        return this.margins;
    }

    public int getNormalColor() {
        return this.normalColor;
    }

    public int getNormalWidth() {
        return this.normalWidth;
    }

    public int getRadius() {
        return this.radius;
    }

    public int getSelectedColor() {
        return this.selectedColor;
    }

    public int getSelectedWidth() {
        return this.selectedWidth;
    }

    public boolean isAttachToBanner() {
        return this.attachToBanner;
    }

    public IndicatorConfig setAttachToBanner(boolean z2) {
        this.attachToBanner = z2;
        return this;
    }

    public IndicatorConfig setCurrentPosition(int i2) {
        this.currentPosition = i2;
        return this;
    }

    public IndicatorConfig setGravity(int i2) {
        this.gravity = i2;
        return this;
    }

    public IndicatorConfig setHeight(int i2) {
        this.height = i2;
        return this;
    }

    public IndicatorConfig setIndicatorSize(int i2) {
        this.indicatorSize = i2;
        return this;
    }

    public IndicatorConfig setIndicatorSpace(int i2) {
        this.indicatorSpace = i2;
        return this;
    }

    public IndicatorConfig setMargins(Margins margins) {
        this.margins = margins;
        return this;
    }

    public IndicatorConfig setNormalColor(int i2) {
        this.normalColor = i2;
        return this;
    }

    public IndicatorConfig setNormalWidth(int i2) {
        this.normalWidth = i2;
        return this;
    }

    public IndicatorConfig setRadius(int i2) {
        this.radius = i2;
        return this;
    }

    public IndicatorConfig setSelectedColor(int i2) {
        this.selectedColor = i2;
        return this;
    }

    public IndicatorConfig setSelectedWidth(int i2) {
        this.selectedWidth = i2;
        return this;
    }
}
