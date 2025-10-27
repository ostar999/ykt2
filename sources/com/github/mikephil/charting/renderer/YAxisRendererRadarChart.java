package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* loaded from: classes3.dex */
public class YAxisRendererRadarChart extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer;

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart radarChart) {
        super(viewPortHandler, yAxis, null);
        this.mRenderLimitLinesPathBuffer = new Path();
        this.mChart = radarChart;
    }

    @Override // com.github.mikephil.charting.renderer.AxisRenderer
    public void computeAxisValues(float f2, float f3) {
        int i2;
        float f4 = f2;
        int labelCount = this.mAxis.getLabelCount();
        double dAbs = Math.abs(f3 - f4);
        if (labelCount == 0 || dAbs <= 0.0d || Double.isInfinite(dAbs)) {
            AxisBase axisBase = this.mAxis;
            axisBase.mEntries = new float[0];
            axisBase.mCenteredEntries = new float[0];
            axisBase.mEntryCount = 0;
            return;
        }
        double dRoundToNextSignificant = Utils.roundToNextSignificant(dAbs / labelCount);
        if (this.mAxis.isGranularityEnabled() && dRoundToNextSignificant < this.mAxis.getGranularity()) {
            dRoundToNextSignificant = this.mAxis.getGranularity();
        }
        double dRoundToNextSignificant2 = Utils.roundToNextSignificant(Math.pow(10.0d, (int) Math.log10(dRoundToNextSignificant)));
        if (((int) (dRoundToNextSignificant / dRoundToNextSignificant2)) > 5) {
            dRoundToNextSignificant = Math.floor(dRoundToNextSignificant2 * 10.0d);
        }
        boolean zIsCenterAxisLabelsEnabled = this.mAxis.isCenterAxisLabelsEnabled();
        if (this.mAxis.isForceLabelsEnabled()) {
            float f5 = ((float) dAbs) / (labelCount - 1);
            AxisBase axisBase2 = this.mAxis;
            axisBase2.mEntryCount = labelCount;
            if (axisBase2.mEntries.length < labelCount) {
                axisBase2.mEntries = new float[labelCount];
            }
            for (int i3 = 0; i3 < labelCount; i3++) {
                this.mAxis.mEntries[i3] = f4;
                f4 += f5;
            }
        } else {
            double dCeil = dRoundToNextSignificant == 0.0d ? 0.0d : Math.ceil(f4 / dRoundToNextSignificant) * dRoundToNextSignificant;
            if (zIsCenterAxisLabelsEnabled) {
                dCeil -= dRoundToNextSignificant;
            }
            double dNextUp = dRoundToNextSignificant == 0.0d ? 0.0d : Utils.nextUp(Math.floor(f3 / dRoundToNextSignificant) * dRoundToNextSignificant);
            if (dRoundToNextSignificant != 0.0d) {
                i2 = zIsCenterAxisLabelsEnabled ? 1 : 0;
                for (double d3 = dCeil; d3 <= dNextUp; d3 += dRoundToNextSignificant) {
                    i2++;
                }
            } else {
                i2 = zIsCenterAxisLabelsEnabled ? 1 : 0;
            }
            int i4 = i2 + 1;
            AxisBase axisBase3 = this.mAxis;
            axisBase3.mEntryCount = i4;
            if (axisBase3.mEntries.length < i4) {
                axisBase3.mEntries = new float[i4];
            }
            for (int i5 = 0; i5 < i4; i5++) {
                if (dCeil == 0.0d) {
                    dCeil = 0.0d;
                }
                this.mAxis.mEntries[i5] = (float) dCeil;
                dCeil += dRoundToNextSignificant;
            }
            labelCount = i4;
        }
        if (dRoundToNextSignificant < 1.0d) {
            this.mAxis.mDecimals = (int) Math.ceil(-Math.log10(dRoundToNextSignificant));
        } else {
            this.mAxis.mDecimals = 0;
        }
        if (zIsCenterAxisLabelsEnabled) {
            AxisBase axisBase4 = this.mAxis;
            if (axisBase4.mCenteredEntries.length < labelCount) {
                axisBase4.mCenteredEntries = new float[labelCount];
            }
            float[] fArr = axisBase4.mEntries;
            float f6 = (fArr[1] - fArr[0]) / 2.0f;
            for (int i6 = 0; i6 < labelCount; i6++) {
                AxisBase axisBase5 = this.mAxis;
                axisBase5.mCenteredEntries[i6] = axisBase5.mEntries[i6] + f6;
            }
        }
        AxisBase axisBase6 = this.mAxis;
        float[] fArr2 = axisBase6.mEntries;
        float f7 = fArr2[0];
        axisBase6.mAxisMinimum = f7;
        float f8 = fArr2[labelCount - 1];
        axisBase6.mAxisMaximum = f8;
        axisBase6.mAxisRange = Math.abs(f8 - f7);
    }

    @Override // com.github.mikephil.charting.renderer.YAxisRenderer, com.github.mikephil.charting.renderer.AxisRenderer
    public void renderAxisLabels(Canvas canvas) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            MPPointF centerOffsets = this.mChart.getCenterOffsets();
            MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
            float factor = this.mChart.getFactor();
            int i2 = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
            for (int i3 = !this.mYAxis.isDrawBottomYLabelEntryEnabled() ? 1 : 0; i3 < i2; i3++) {
                YAxis yAxis = this.mYAxis;
                Utils.getPosition(centerOffsets, (yAxis.mEntries[i3] - yAxis.mAxisMinimum) * factor, this.mChart.getRotationAngle(), mPPointF);
                canvas.drawText(this.mYAxis.getFormattedLabel(i3), mPPointF.f6566x + 10.0f, mPPointF.f6567y, this.mAxisLabelPaint);
            }
            MPPointF.recycleInstance(centerOffsets);
            MPPointF.recycleInstance(mPPointF);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.YAxisRenderer, com.github.mikephil.charting.renderer.AxisRenderer
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines == null) {
            return;
        }
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        MPPointF centerOffsets = this.mChart.getCenterOffsets();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        for (int i2 = 0; i2 < limitLines.size(); i2++) {
            LimitLine limitLine = limitLines.get(i2);
            if (limitLine.isEnabled()) {
                this.mLimitLinePaint.setColor(limitLine.getLineColor());
                this.mLimitLinePaint.setPathEffect(limitLine.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                float limit = (limitLine.getLimit() - this.mChart.getYChartMin()) * factor;
                Path path = this.mRenderLimitLinesPathBuffer;
                path.reset();
                for (int i3 = 0; i3 < ((RadarData) this.mChart.getData()).getMaxEntryCountSet().getEntryCount(); i3++) {
                    Utils.getPosition(centerOffsets, limit, (i3 * sliceAngle) + this.mChart.getRotationAngle(), mPPointF);
                    if (i3 == 0) {
                        path.moveTo(mPPointF.f6566x, mPPointF.f6567y);
                    } else {
                        path.lineTo(mPPointF.f6566x, mPPointF.f6567y);
                    }
                }
                path.close();
                canvas.drawPath(path, this.mLimitLinePaint);
            }
        }
        MPPointF.recycleInstance(centerOffsets);
        MPPointF.recycleInstance(mPPointF);
    }
}
