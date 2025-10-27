package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.utils.Utils;

/* loaded from: classes3.dex */
public class YAxis extends AxisBase {
    private AxisDependency mAxisDependency;
    private boolean mDrawBottomYLabelEntry;
    private boolean mDrawTopYLabelEntry;
    protected boolean mDrawZeroLine;
    protected boolean mInverted;
    protected float mMaxWidth;
    protected float mMinWidth;
    private YAxisLabelPosition mPosition;
    protected float mSpacePercentBottom;
    protected float mSpacePercentTop;
    private boolean mUseAutoScaleRestrictionMax;
    private boolean mUseAutoScaleRestrictionMin;
    protected int mZeroLineColor;
    protected float mZeroLineWidth;

    public enum AxisDependency {
        LEFT,
        RIGHT
    }

    public enum YAxisLabelPosition {
        OUTSIDE_CHART,
        INSIDE_CHART
    }

    public YAxis() {
        this.mDrawBottomYLabelEntry = true;
        this.mDrawTopYLabelEntry = true;
        this.mInverted = false;
        this.mDrawZeroLine = false;
        this.mUseAutoScaleRestrictionMin = false;
        this.mUseAutoScaleRestrictionMax = false;
        this.mZeroLineColor = -7829368;
        this.mZeroLineWidth = 1.0f;
        this.mSpacePercentTop = 10.0f;
        this.mSpacePercentBottom = 10.0f;
        this.mPosition = YAxisLabelPosition.OUTSIDE_CHART;
        this.mMinWidth = 0.0f;
        this.mMaxWidth = Float.POSITIVE_INFINITY;
        this.mAxisDependency = AxisDependency.LEFT;
        this.mYOffset = 0.0f;
    }

    @Override // com.github.mikephil.charting.components.AxisBase
    public void calculate(float f2, float f3) {
        if (Math.abs(f3 - f2) == 0.0f) {
            f3 += 1.0f;
            f2 -= 1.0f;
        }
        float fAbs = Math.abs(f3 - f2);
        this.mAxisMinimum = this.mCustomAxisMin ? this.mAxisMinimum : f2 - ((fAbs / 100.0f) * getSpaceBottom());
        float spaceTop = this.mCustomAxisMax ? this.mAxisMaximum : f3 + ((fAbs / 100.0f) * getSpaceTop());
        this.mAxisMaximum = spaceTop;
        this.mAxisRange = Math.abs(this.mAxisMinimum - spaceTop);
    }

    public AxisDependency getAxisDependency() {
        return this.mAxisDependency;
    }

    public YAxisLabelPosition getLabelPosition() {
        return this.mPosition;
    }

    public float getMaxWidth() {
        return this.mMaxWidth;
    }

    public float getMinWidth() {
        return this.mMinWidth;
    }

    public float getRequiredHeightSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        return Utils.calcTextHeight(paint, getLongestLabel()) + (getYOffset() * 2.0f);
    }

    public float getRequiredWidthSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        float fCalcTextWidth = Utils.calcTextWidth(paint, getLongestLabel()) + (getXOffset() * 2.0f);
        float minWidth = getMinWidth();
        float maxWidth = getMaxWidth();
        if (minWidth > 0.0f) {
            minWidth = Utils.convertDpToPixel(minWidth);
        }
        if (maxWidth > 0.0f && maxWidth != Float.POSITIVE_INFINITY) {
            maxWidth = Utils.convertDpToPixel(maxWidth);
        }
        if (maxWidth <= 0.0d) {
            maxWidth = fCalcTextWidth;
        }
        return Math.max(minWidth, Math.min(fCalcTextWidth, maxWidth));
    }

    public float getSpaceBottom() {
        return this.mSpacePercentBottom;
    }

    public float getSpaceTop() {
        return this.mSpacePercentTop;
    }

    public int getZeroLineColor() {
        return this.mZeroLineColor;
    }

    public float getZeroLineWidth() {
        return this.mZeroLineWidth;
    }

    public boolean isDrawBottomYLabelEntryEnabled() {
        return this.mDrawBottomYLabelEntry;
    }

    public boolean isDrawTopYLabelEntryEnabled() {
        return this.mDrawTopYLabelEntry;
    }

    public boolean isDrawZeroLineEnabled() {
        return this.mDrawZeroLine;
    }

    public boolean isInverted() {
        return this.mInverted;
    }

    @Deprecated
    public boolean isUseAutoScaleMaxRestriction() {
        return this.mUseAutoScaleRestrictionMax;
    }

    @Deprecated
    public boolean isUseAutoScaleMinRestriction() {
        return this.mUseAutoScaleRestrictionMin;
    }

    public boolean needsOffset() {
        return isEnabled() && isDrawLabelsEnabled() && getLabelPosition() == YAxisLabelPosition.OUTSIDE_CHART;
    }

    public void setDrawTopYLabelEntry(boolean z2) {
        this.mDrawTopYLabelEntry = z2;
    }

    public void setDrawZeroLine(boolean z2) {
        this.mDrawZeroLine = z2;
    }

    public void setInverted(boolean z2) {
        this.mInverted = z2;
    }

    public void setMaxWidth(float f2) {
        this.mMaxWidth = f2;
    }

    public void setMinWidth(float f2) {
        this.mMinWidth = f2;
    }

    public void setPosition(YAxisLabelPosition yAxisLabelPosition) {
        this.mPosition = yAxisLabelPosition;
    }

    public void setSpaceBottom(float f2) {
        this.mSpacePercentBottom = f2;
    }

    public void setSpaceTop(float f2) {
        this.mSpacePercentTop = f2;
    }

    @Deprecated
    public void setStartAtZero(boolean z2) {
        if (z2) {
            setAxisMinimum(0.0f);
        } else {
            resetAxisMinimum();
        }
    }

    @Deprecated
    public void setUseAutoScaleMaxRestriction(boolean z2) {
        this.mUseAutoScaleRestrictionMax = z2;
    }

    @Deprecated
    public void setUseAutoScaleMinRestriction(boolean z2) {
        this.mUseAutoScaleRestrictionMin = z2;
    }

    public void setZeroLineColor(int i2) {
        this.mZeroLineColor = i2;
    }

    public void setZeroLineWidth(float f2) {
        this.mZeroLineWidth = Utils.convertDpToPixel(f2);
    }

    public YAxis(AxisDependency axisDependency) {
        this.mDrawBottomYLabelEntry = true;
        this.mDrawTopYLabelEntry = true;
        this.mInverted = false;
        this.mDrawZeroLine = false;
        this.mUseAutoScaleRestrictionMin = false;
        this.mUseAutoScaleRestrictionMax = false;
        this.mZeroLineColor = -7829368;
        this.mZeroLineWidth = 1.0f;
        this.mSpacePercentTop = 10.0f;
        this.mSpacePercentBottom = 10.0f;
        this.mPosition = YAxisLabelPosition.OUTSIDE_CHART;
        this.mMinWidth = 0.0f;
        this.mMaxWidth = Float.POSITIVE_INFINITY;
        this.mAxisDependency = axisDependency;
        this.mYOffset = 0.0f;
    }
}
