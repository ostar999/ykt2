package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.highlight.RadarHighlighter;
import com.github.mikephil.charting.renderer.RadarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererRadarChart;
import com.github.mikephil.charting.renderer.YAxisRendererRadarChart;
import com.github.mikephil.charting.utils.Utils;

/* loaded from: classes3.dex */
public class RadarChart extends PieRadarChartBase<RadarData> {
    private boolean mDrawWeb;
    private float mInnerWebLineWidth;
    private int mSkipWebLineCount;
    private int mWebAlpha;
    private int mWebColor;
    private int mWebColorInner;
    private float mWebLineWidth;
    protected XAxisRendererRadarChart mXAxisRenderer;
    private YAxis mYAxis;
    protected YAxisRendererRadarChart mYAxisRenderer;

    public RadarChart(Context context) {
        super(context);
        this.mWebLineWidth = 2.5f;
        this.mInnerWebLineWidth = 1.5f;
        this.mWebColor = Color.rgb(122, 122, 122);
        this.mWebColorInner = Color.rgb(122, 122, 122);
        this.mWebAlpha = 150;
        this.mDrawWeb = true;
        this.mSkipWebLineCount = 0;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void calcMinMax() {
        super.calcMinMax();
        YAxis yAxis = this.mYAxis;
        RadarData radarData = (RadarData) this.mData;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        yAxis.calculate(radarData.getYMin(axisDependency), ((RadarData) this.mData).getYMax(axisDependency));
        this.mXAxis.calculate(0.0f, ((RadarData) this.mData).getMaxEntryCountSet().getEntryCount());
    }

    public float getFactor() {
        RectF contentRect = this.mViewPortHandler.getContentRect();
        return Math.min(contentRect.width() / 2.0f, contentRect.height() / 2.0f) / this.mYAxis.mAxisRange;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public int getIndexForAngle(float f2) {
        float normalizedAngle = Utils.getNormalizedAngle(f2 - getRotationAngle());
        float sliceAngle = getSliceAngle();
        int entryCount = ((RadarData) this.mData).getMaxEntryCountSet().getEntryCount();
        int i2 = 0;
        while (i2 < entryCount) {
            int i3 = i2 + 1;
            if ((i3 * sliceAngle) - (sliceAngle / 2.0f) > normalizedAngle) {
                return i2;
            }
            i2 = i3;
        }
        return 0;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRadius() {
        RectF contentRect = this.mViewPortHandler.getContentRect();
        return Math.min(contentRect.width() / 2.0f, contentRect.height() / 2.0f);
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRequiredBaseOffset() {
        return (this.mXAxis.isEnabled() && this.mXAxis.isDrawLabelsEnabled()) ? this.mXAxis.mLabelRotatedWidth : Utils.convertDpToPixel(10.0f);
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRequiredLegendOffset() {
        return this.mLegendRenderer.getLabelPaint().getTextSize() * 4.0f;
    }

    public int getSkipWebLineCount() {
        return this.mSkipWebLineCount;
    }

    public float getSliceAngle() {
        return 360.0f / ((RadarData) this.mData).getMaxEntryCountSet().getEntryCount();
    }

    public int getWebAlpha() {
        return this.mWebAlpha;
    }

    public int getWebColor() {
        return this.mWebColor;
    }

    public int getWebColorInner() {
        return this.mWebColorInner;
    }

    public float getWebLineWidth() {
        return this.mWebLineWidth;
    }

    public float getWebLineWidthInner() {
        return this.mInnerWebLineWidth;
    }

    public YAxis getYAxis() {
        return this.mYAxis;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.interfaces.dataprovider.ChartInterface
    public float getYChartMax() {
        return this.mYAxis.mAxisMaximum;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.interfaces.dataprovider.ChartInterface
    public float getYChartMin() {
        return this.mYAxis.mAxisMinimum;
    }

    public float getYRange() {
        return this.mYAxis.mAxisRange;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void init() {
        super.init();
        this.mYAxis = new YAxis(YAxis.AxisDependency.LEFT);
        this.mWebLineWidth = Utils.convertDpToPixel(1.5f);
        this.mInnerWebLineWidth = Utils.convertDpToPixel(0.75f);
        this.mRenderer = new RadarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mYAxisRenderer = new YAxisRendererRadarChart(this.mViewPortHandler, this.mYAxis, this);
        this.mXAxisRenderer = new XAxisRendererRadarChart(this.mViewPortHandler, this.mXAxis, this);
        this.mHighlighter = new RadarHighlighter(this);
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void notifyDataSetChanged() {
        if (this.mData == 0) {
            return;
        }
        calcMinMax();
        YAxisRendererRadarChart yAxisRendererRadarChart = this.mYAxisRenderer;
        YAxis yAxis = this.mYAxis;
        yAxisRendererRadarChart.computeAxis(yAxis.mAxisMinimum, yAxis.mAxisMaximum, yAxis.isInverted());
        XAxisRendererRadarChart xAxisRendererRadarChart = this.mXAxisRenderer;
        XAxis xAxis = this.mXAxis;
        xAxisRendererRadarChart.computeAxis(xAxis.mAxisMinimum, xAxis.mAxisMaximum, false);
        Legend legend = this.mLegend;
        if (legend != null && !legend.isLegendCustom()) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        calculateOffsets();
    }

    @Override // com.github.mikephil.charting.charts.Chart, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData == 0) {
            return;
        }
        if (this.mXAxis.isEnabled()) {
            XAxisRendererRadarChart xAxisRendererRadarChart = this.mXAxisRenderer;
            XAxis xAxis = this.mXAxis;
            xAxisRendererRadarChart.computeAxis(xAxis.mAxisMinimum, xAxis.mAxisMaximum, false);
        }
        this.mXAxisRenderer.renderAxisLabels(canvas);
        if (this.mDrawWeb) {
            this.mRenderer.drawExtras(canvas);
        }
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mYAxisRenderer.renderLimitLines(canvas);
        }
        this.mRenderer.drawData(canvas);
        if (valuesToHighlight()) {
            this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
        }
        if (this.mYAxis.isEnabled() && !this.mYAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mYAxisRenderer.renderLimitLines(canvas);
        }
        this.mYAxisRenderer.renderAxisLabels(canvas);
        this.mRenderer.drawValues(canvas);
        this.mLegendRenderer.renderLegend(canvas);
        drawDescription(canvas);
        drawMarkers(canvas);
    }

    public void setDrawWeb(boolean z2) {
        this.mDrawWeb = z2;
    }

    public void setSkipWebLineCount(int i2) {
        this.mSkipWebLineCount = Math.max(0, i2);
    }

    public void setWebAlpha(int i2) {
        this.mWebAlpha = i2;
    }

    public void setWebColor(int i2) {
        this.mWebColor = i2;
    }

    public void setWebColorInner(int i2) {
        this.mWebColorInner = i2;
    }

    public void setWebLineWidth(float f2) {
        this.mWebLineWidth = Utils.convertDpToPixel(f2);
    }

    public void setWebLineWidthInner(float f2) {
        this.mInnerWebLineWidth = Utils.convertDpToPixel(f2);
    }

    public RadarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWebLineWidth = 2.5f;
        this.mInnerWebLineWidth = 1.5f;
        this.mWebColor = Color.rgb(122, 122, 122);
        this.mWebColorInner = Color.rgb(122, 122, 122);
        this.mWebAlpha = 150;
        this.mDrawWeb = true;
        this.mSkipWebLineCount = 0;
    }

    public RadarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mWebLineWidth = 2.5f;
        this.mInnerWebLineWidth = 1.5f;
        this.mWebColor = Color.rgb(122, 122, 122);
        this.mWebColorInner = Color.rgb(122, 122, 122);
        this.mWebAlpha = 150;
        this.mDrawWeb = true;
        this.mSkipWebLineCount = 0;
    }
}
