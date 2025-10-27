package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;

/* loaded from: classes3.dex */
public class BarChart extends BarLineChartBase<BarData> implements BarDataProvider {
    private boolean mDrawBarShadow;
    private boolean mDrawValueAboveBar;
    private boolean mFitBars;
    protected boolean mHighlightFullBarEnabled;

    public BarChart(Context context) {
        super(context);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
        this.mDrawBarShadow = false;
        this.mFitBars = false;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.charts.Chart
    public void calcMinMax() {
        if (this.mFitBars) {
            this.mXAxis.calculate(((BarData) this.mData).getXMin() - (((BarData) this.mData).getBarWidth() / 2.0f), ((BarData) this.mData).getXMax() + (((BarData) this.mData).getBarWidth() / 2.0f));
        } else {
            this.mXAxis.calculate(((BarData) this.mData).getXMin(), ((BarData) this.mData).getXMax());
        }
        YAxis yAxis = this.mAxisLeft;
        BarData barData = (BarData) this.mData;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        yAxis.calculate(barData.getYMin(axisDependency), ((BarData) this.mData).getYMax(axisDependency));
        YAxis yAxis2 = this.mAxisRight;
        BarData barData2 = (BarData) this.mData;
        YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
        yAxis2.calculate(barData2.getYMin(axisDependency2), ((BarData) this.mData).getYMax(axisDependency2));
    }

    public RectF getBarBounds(BarEntry barEntry) {
        RectF rectF = new RectF();
        getBarBounds(barEntry, rectF);
        return rectF;
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
    public BarData getBarData() {
        return (BarData) this.mData;
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public Highlight getHighlightByTouchPoint(float f2, float f3) {
        if (this.mData == 0) {
            Log.e(Chart.LOG_TAG, "Can't select by touch. No data set.");
            return null;
        }
        Highlight highlight = getHighlighter().getHighlight(f2, f3);
        return (highlight == null || !isHighlightFullBarEnabled()) ? highlight : new Highlight(highlight.getX(), highlight.getY(), highlight.getXPx(), highlight.getYPx(), highlight.getDataSetIndex(), -1, highlight.getAxis());
    }

    public void groupBars(float f2, float f3, float f4) {
        if (getBarData() == null) {
            throw new RuntimeException("You need to set data for the chart before grouping bars.");
        }
        getBarData().groupBars(f2, f3, f4);
        notifyDataSetChanged();
    }

    public void highlightValue(float f2, int i2, int i3) {
        highlightValue(new Highlight(f2, i2, i3), false);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.charts.Chart
    public void init() {
        super.init();
        this.mRenderer = new BarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        setHighlighter(new BarHighlighter(this));
        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
    public boolean isDrawBarShadowEnabled() {
        return this.mDrawBarShadow;
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
    public boolean isDrawValueAboveBarEnabled() {
        return this.mDrawValueAboveBar;
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
    public boolean isHighlightFullBarEnabled() {
        return this.mHighlightFullBarEnabled;
    }

    public void setDrawBarShadow(boolean z2) {
        this.mDrawBarShadow = z2;
    }

    public void setDrawValueAboveBar(boolean z2) {
        this.mDrawValueAboveBar = z2;
    }

    public void setFitBars(boolean z2) {
        this.mFitBars = z2;
    }

    public void setHighlightFullBarEnabled(boolean z2) {
        this.mHighlightFullBarEnabled = z2;
    }

    public void getBarBounds(BarEntry barEntry, RectF rectF) {
        IBarDataSet iBarDataSet = (IBarDataSet) ((BarData) this.mData).getDataSetForEntry(barEntry);
        if (iBarDataSet == null) {
            rectF.set(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
            return;
        }
        float y2 = barEntry.getY();
        float x2 = barEntry.getX();
        float barWidth = ((BarData) this.mData).getBarWidth() / 2.0f;
        float f2 = x2 - barWidth;
        float f3 = x2 + barWidth;
        float f4 = y2 >= 0.0f ? y2 : 0.0f;
        if (y2 > 0.0f) {
            y2 = 0.0f;
        }
        rectF.set(f2, f4, f3, y2);
        getTransformer(iBarDataSet.getAxisDependency()).rectValueToPixel(rectF);
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
        this.mDrawBarShadow = false;
        this.mFitBars = false;
    }

    public BarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHighlightFullBarEnabled = false;
        this.mDrawValueAboveBar = true;
        this.mDrawBarShadow = false;
        this.mFitBars = false;
    }
}
