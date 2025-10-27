package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.HorizontalBarHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.renderer.YAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.utils.HorizontalViewPortHandler;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.TransformerHorizontalBarChart;
import com.github.mikephil.charting.utils.Utils;

/* loaded from: classes3.dex */
public class HorizontalBarChart extends BarChart {
    protected float[] mGetPositionBuffer;
    private RectF mOffsetsBuffer;

    public HorizontalBarChart(Context context) {
        super(context);
        this.mOffsetsBuffer = new RectF();
        this.mGetPositionBuffer = new float[2];
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.charts.Chart
    public void calculateOffsets() {
        calculateLegendOffsets(this.mOffsetsBuffer);
        RectF rectF = this.mOffsetsBuffer;
        float f2 = rectF.left + 0.0f;
        float requiredHeightSpace = rectF.top + 0.0f;
        float f3 = rectF.right + 0.0f;
        float requiredHeightSpace2 = rectF.bottom + 0.0f;
        if (this.mAxisLeft.needsOffset()) {
            requiredHeightSpace += this.mAxisLeft.getRequiredHeightSpace(this.mAxisRendererLeft.getPaintAxisLabels());
        }
        if (this.mAxisRight.needsOffset()) {
            requiredHeightSpace2 += this.mAxisRight.getRequiredHeightSpace(this.mAxisRendererRight.getPaintAxisLabels());
        }
        XAxis xAxis = this.mXAxis;
        float f4 = xAxis.mLabelRotatedWidth;
        if (xAxis.isEnabled()) {
            if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
                f2 += f4;
            } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
                f3 += f4;
            } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                f2 += f4;
                f3 += f4;
            }
        }
        float extraTopOffset = requiredHeightSpace + getExtraTopOffset();
        float extraRightOffset = f3 + getExtraRightOffset();
        float extraBottomOffset = requiredHeightSpace2 + getExtraBottomOffset();
        float extraLeftOffset = f2 + getExtraLeftOffset();
        float fConvertDpToPixel = Utils.convertDpToPixel(this.mMinOffset);
        this.mViewPortHandler.restrainViewPort(Math.max(fConvertDpToPixel, extraLeftOffset), Math.max(fConvertDpToPixel, extraTopOffset), Math.max(fConvertDpToPixel, extraRightOffset), Math.max(fConvertDpToPixel, extraBottomOffset));
        if (this.mLogEnabled) {
            Log.i(Chart.LOG_TAG, "offsetLeft: " + extraLeftOffset + ", offsetTop: " + extraTopOffset + ", offsetRight: " + extraRightOffset + ", offsetBottom: " + extraBottomOffset);
            StringBuilder sb = new StringBuilder();
            sb.append("Content: ");
            sb.append(this.mViewPortHandler.getContentRect().toString());
            Log.i(Chart.LOG_TAG, sb.toString());
        }
        prepareOffsetMatrix();
        prepareValuePxMatrix();
    }

    @Override // com.github.mikephil.charting.charts.BarChart
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
        rectF.set(f4, f2, y2, f3);
        getTransformer(iBarDataSet.getAxisDependency()).rectValueToPixel(rectF);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public float getHighestVisibleX() {
        getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.posForGetHighestVisibleX);
        return (float) Math.min(this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.f6565y);
    }

    @Override // com.github.mikephil.charting.charts.BarChart, com.github.mikephil.charting.charts.Chart
    public Highlight getHighlightByTouchPoint(float f2, float f3) {
        if (this.mData != 0) {
            return getHighlighter().getHighlight(f3, f2);
        }
        if (!this.mLogEnabled) {
            return null;
        }
        Log.e(Chart.LOG_TAG, "Can't select by touch. No data set.");
        return null;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public float getLowestVisibleX() {
        getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float) Math.max(this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.f6565y);
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public float[] getMarkerPosition(Highlight highlight) {
        return new float[]{highlight.getDrawY(), highlight.getDrawX()};
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public MPPointF getPosition(Entry entry, YAxis.AxisDependency axisDependency) {
        if (entry == null) {
            return null;
        }
        float[] fArr = this.mGetPositionBuffer;
        fArr[0] = entry.getY();
        fArr[1] = entry.getX();
        getTransformer(axisDependency).pointValuesToPixel(fArr);
        return MPPointF.getInstance(fArr[0], fArr[1]);
    }

    @Override // com.github.mikephil.charting.charts.BarChart, com.github.mikephil.charting.charts.BarLineChartBase, com.github.mikephil.charting.charts.Chart
    public void init() {
        this.mViewPortHandler = new HorizontalViewPortHandler();
        super.init();
        this.mLeftAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRightAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRenderer = new HorizontalBarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        setHighlighter(new HorizontalBarHighlighter(this));
        this.mAxisRendererLeft = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer, this);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void prepareValuePxMatrix() {
        Transformer transformer = this.mRightAxisTransformer;
        YAxis yAxis = this.mAxisRight;
        float f2 = yAxis.mAxisMinimum;
        float f3 = yAxis.mAxisRange;
        XAxis xAxis = this.mXAxis;
        transformer.prepareMatrixValuePx(f2, f3, xAxis.mAxisRange, xAxis.mAxisMinimum);
        Transformer transformer2 = this.mLeftAxisTransformer;
        YAxis yAxis2 = this.mAxisLeft;
        float f4 = yAxis2.mAxisMinimum;
        float f5 = yAxis2.mAxisRange;
        XAxis xAxis2 = this.mXAxis;
        transformer2.prepareMatrixValuePx(f4, f5, xAxis2.mAxisRange, xAxis2.mAxisMinimum);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleXRange(float f2, float f3) {
        float f4 = this.mXAxis.mAxisRange;
        this.mViewPortHandler.setMinMaxScaleY(f4 / f2, f4 / f3);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleXRangeMaximum(float f2) {
        this.mViewPortHandler.setMinimumScaleY(this.mXAxis.mAxisRange / f2);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleXRangeMinimum(float f2) {
        this.mViewPortHandler.setMaximumScaleY(this.mXAxis.mAxisRange / f2);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleYRange(float f2, float f3, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMinMaxScaleX(getAxisRange(axisDependency) / f2, getAxisRange(axisDependency) / f3);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleYRangeMaximum(float f2, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMinimumScaleX(getAxisRange(axisDependency) / f2);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase
    public void setVisibleYRangeMinimum(float f2, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMaximumScaleX(getAxisRange(axisDependency) / f2);
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOffsetsBuffer = new RectF();
        this.mGetPositionBuffer = new float[2];
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOffsetsBuffer = new RectF();
        this.mGetPositionBuffer = new float[2];
    }
}
