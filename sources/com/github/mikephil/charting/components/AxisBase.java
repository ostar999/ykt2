package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class AxisBase extends ComponentBase {
    protected ValueFormatter mAxisValueFormatter;
    public int mDecimals;
    public int mEntryCount;
    protected List<LimitLine> mLimitLines;
    private int mGridColor = -7829368;
    private float mGridLineWidth = 1.0f;
    private int mAxisLineColor = -7829368;
    private float mAxisLineWidth = 1.0f;
    public float[] mEntries = new float[0];
    public float[] mCenteredEntries = new float[0];
    private int mLabelCount = 6;
    protected float mGranularity = 1.0f;
    protected boolean mGranularityEnabled = false;
    protected boolean mForceLabels = false;
    protected boolean mDrawGridLines = true;
    protected boolean mDrawAxisLine = true;
    protected boolean mDrawLabels = true;
    protected boolean mCenterAxisLabels = false;
    private DashPathEffect mAxisLineDashPathEffect = null;
    private DashPathEffect mGridDashPathEffect = null;
    protected boolean mDrawLimitLineBehindData = false;
    protected boolean mDrawGridLinesBehindData = true;
    protected float mSpaceMin = 0.0f;
    protected float mSpaceMax = 0.0f;
    protected boolean mCustomAxisMin = false;
    protected boolean mCustomAxisMax = false;
    public float mAxisMaximum = 0.0f;
    public float mAxisMinimum = 0.0f;
    public float mAxisRange = 0.0f;

    public AxisBase() {
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList();
    }

    public void addLimitLine(LimitLine limitLine) {
        this.mLimitLines.add(limitLine);
        if (this.mLimitLines.size() > 6) {
            Log.e("MPAndroiChart", "Warning! You have more than 6 LimitLines on your axis, do you really want that?");
        }
    }

    public void calculate(float f2, float f3) {
        float f4 = this.mCustomAxisMin ? this.mAxisMinimum : f2 - this.mSpaceMin;
        float f5 = this.mCustomAxisMax ? this.mAxisMaximum : f3 + this.mSpaceMax;
        if (Math.abs(f5 - f4) == 0.0f) {
            f5 += 1.0f;
            f4 -= 1.0f;
        }
        this.mAxisMinimum = f4;
        this.mAxisMaximum = f5;
        this.mAxisRange = Math.abs(f5 - f4);
    }

    public void disableAxisLineDashedLine() {
        this.mAxisLineDashPathEffect = null;
    }

    public void disableGridDashedLine() {
        this.mGridDashPathEffect = null;
    }

    public void enableAxisLineDashedLine(float f2, float f3, float f4) {
        this.mAxisLineDashPathEffect = new DashPathEffect(new float[]{f2, f3}, f4);
    }

    public void enableGridDashedLine(float f2, float f3, float f4) {
        this.mGridDashPathEffect = new DashPathEffect(new float[]{f2, f3}, f4);
    }

    public int getAxisLineColor() {
        return this.mAxisLineColor;
    }

    public DashPathEffect getAxisLineDashPathEffect() {
        return this.mAxisLineDashPathEffect;
    }

    public float getAxisLineWidth() {
        return this.mAxisLineWidth;
    }

    public float getAxisMaximum() {
        return this.mAxisMaximum;
    }

    public float getAxisMinimum() {
        return this.mAxisMinimum;
    }

    public String getFormattedLabel(int i2) {
        return (i2 < 0 || i2 >= this.mEntries.length) ? "" : getValueFormatter().getAxisLabel(this.mEntries[i2], this);
    }

    public float getGranularity() {
        return this.mGranularity;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public DashPathEffect getGridDashPathEffect() {
        return this.mGridDashPathEffect;
    }

    public float getGridLineWidth() {
        return this.mGridLineWidth;
    }

    public int getLabelCount() {
        return this.mLabelCount;
    }

    public List<LimitLine> getLimitLines() {
        return this.mLimitLines;
    }

    public String getLongestLabel() {
        String str = "";
        for (int i2 = 0; i2 < this.mEntries.length; i2++) {
            String formattedLabel = getFormattedLabel(i2);
            if (formattedLabel != null && str.length() < formattedLabel.length()) {
                str = formattedLabel;
            }
        }
        return str;
    }

    public float getSpaceMax() {
        return this.mSpaceMax;
    }

    public float getSpaceMin() {
        return this.mSpaceMin;
    }

    public ValueFormatter getValueFormatter() {
        ValueFormatter valueFormatter = this.mAxisValueFormatter;
        if (valueFormatter == null || ((valueFormatter instanceof DefaultAxisValueFormatter) && ((DefaultAxisValueFormatter) valueFormatter).getDecimalDigits() != this.mDecimals)) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        }
        return this.mAxisValueFormatter;
    }

    public boolean isAxisLineDashedLineEnabled() {
        return this.mAxisLineDashPathEffect != null;
    }

    public boolean isAxisMaxCustom() {
        return this.mCustomAxisMax;
    }

    public boolean isAxisMinCustom() {
        return this.mCustomAxisMin;
    }

    public boolean isCenterAxisLabelsEnabled() {
        return this.mCenterAxisLabels && this.mEntryCount > 0;
    }

    public boolean isDrawAxisLineEnabled() {
        return this.mDrawAxisLine;
    }

    public boolean isDrawGridLinesBehindDataEnabled() {
        return this.mDrawGridLinesBehindData;
    }

    public boolean isDrawGridLinesEnabled() {
        return this.mDrawGridLines;
    }

    public boolean isDrawLabelsEnabled() {
        return this.mDrawLabels;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return this.mDrawLimitLineBehindData;
    }

    public boolean isForceLabelsEnabled() {
        return this.mForceLabels;
    }

    public boolean isGranularityEnabled() {
        return this.mGranularityEnabled;
    }

    public boolean isGridDashedLineEnabled() {
        return this.mGridDashPathEffect != null;
    }

    public void removeAllLimitLines() {
        this.mLimitLines.clear();
    }

    public void removeLimitLine(LimitLine limitLine) {
        this.mLimitLines.remove(limitLine);
    }

    public void resetAxisMaximum() {
        this.mCustomAxisMax = false;
    }

    public void resetAxisMinimum() {
        this.mCustomAxisMin = false;
    }

    public void setAxisLineColor(int i2) {
        this.mAxisLineColor = i2;
    }

    public void setAxisLineDashedLine(DashPathEffect dashPathEffect) {
        this.mAxisLineDashPathEffect = dashPathEffect;
    }

    public void setAxisLineWidth(float f2) {
        this.mAxisLineWidth = Utils.convertDpToPixel(f2);
    }

    @Deprecated
    public void setAxisMaxValue(float f2) {
        setAxisMaximum(f2);
    }

    public void setAxisMaximum(float f2) {
        this.mCustomAxisMax = true;
        this.mAxisMaximum = f2;
        this.mAxisRange = Math.abs(f2 - this.mAxisMinimum);
    }

    @Deprecated
    public void setAxisMinValue(float f2) {
        setAxisMinimum(f2);
    }

    public void setAxisMinimum(float f2) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = f2;
        this.mAxisRange = Math.abs(this.mAxisMaximum - f2);
    }

    public void setCenterAxisLabels(boolean z2) {
        this.mCenterAxisLabels = z2;
    }

    public void setDrawAxisLine(boolean z2) {
        this.mDrawAxisLine = z2;
    }

    public void setDrawGridLines(boolean z2) {
        this.mDrawGridLines = z2;
    }

    public void setDrawGridLinesBehindData(boolean z2) {
        this.mDrawGridLinesBehindData = z2;
    }

    public void setDrawLabels(boolean z2) {
        this.mDrawLabels = z2;
    }

    public void setDrawLimitLinesBehindData(boolean z2) {
        this.mDrawLimitLineBehindData = z2;
    }

    public void setGranularity(float f2) {
        this.mGranularity = f2;
        this.mGranularityEnabled = true;
    }

    public void setGranularityEnabled(boolean z2) {
        this.mGranularityEnabled = z2;
    }

    public void setGridColor(int i2) {
        this.mGridColor = i2;
    }

    public void setGridDashedLine(DashPathEffect dashPathEffect) {
        this.mGridDashPathEffect = dashPathEffect;
    }

    public void setGridLineWidth(float f2) {
        this.mGridLineWidth = Utils.convertDpToPixel(f2);
    }

    public void setLabelCount(int i2) {
        if (i2 > 25) {
            i2 = 25;
        }
        if (i2 < 2) {
            i2 = 2;
        }
        this.mLabelCount = i2;
        this.mForceLabels = false;
    }

    public void setSpaceMax(float f2) {
        this.mSpaceMax = f2;
    }

    public void setSpaceMin(float f2) {
        this.mSpaceMin = f2;
    }

    public void setValueFormatter(ValueFormatter valueFormatter) {
        if (valueFormatter == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        } else {
            this.mAxisValueFormatter = valueFormatter;
        }
    }

    public void setLabelCount(int i2, boolean z2) {
        setLabelCount(i2);
        this.mForceLabels = z2;
    }
}
