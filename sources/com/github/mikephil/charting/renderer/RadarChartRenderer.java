package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes3.dex */
public class RadarChartRenderer extends LineRadarRenderer {
    protected RadarChart mChart;
    protected Path mDrawDataSetSurfacePathBuffer;
    protected Path mDrawHighlightCirclePathBuffer;
    protected Paint mHighlightCirclePaint;
    protected Paint mWebPaint;

    public RadarChartRenderer(RadarChart radarChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mDrawDataSetSurfacePathBuffer = new Path();
        this.mDrawHighlightCirclePathBuffer = new Path();
        this.mChart = radarChart;
        Paint paint = new Paint(1);
        this.mHighlightPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb(255, 187, 115));
        Paint paint2 = new Paint(1);
        this.mWebPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mHighlightCirclePaint = new Paint(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        RadarData radarData = (RadarData) this.mChart.getData();
        int entryCount = radarData.getMaxEntryCountSet().getEntryCount();
        for (IRadarDataSet iRadarDataSet : radarData.getDataSets()) {
            if (iRadarDataSet.isVisible()) {
                drawDataSet(canvas, iRadarDataSet, entryCount);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawDataSet(Canvas canvas, IRadarDataSet iRadarDataSet, int i2) {
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        MPPointF centerOffsets = this.mChart.getCenterOffsets();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        Path path = this.mDrawDataSetSurfacePathBuffer;
        path.reset();
        boolean z2 = false;
        for (int i3 = 0; i3 < iRadarDataSet.getEntryCount(); i3++) {
            this.mRenderPaint.setColor(iRadarDataSet.getColor(i3));
            Utils.getPosition(centerOffsets, (((RadarEntry) iRadarDataSet.getEntryForIndex(i3)).getY() - this.mChart.getYChartMin()) * factor * phaseY, (i3 * sliceAngle * phaseX) + this.mChart.getRotationAngle(), mPPointF);
            if (!Float.isNaN(mPPointF.f6566x)) {
                if (z2) {
                    path.lineTo(mPPointF.f6566x, mPPointF.f6567y);
                } else {
                    path.moveTo(mPPointF.f6566x, mPPointF.f6567y);
                    z2 = true;
                }
            }
        }
        if (iRadarDataSet.getEntryCount() > i2) {
            path.lineTo(centerOffsets.f6566x, centerOffsets.f6567y);
        }
        path.close();
        if (iRadarDataSet.isDrawFilledEnabled()) {
            Drawable fillDrawable = iRadarDataSet.getFillDrawable();
            if (fillDrawable != null) {
                drawFilledPath(canvas, path, fillDrawable);
            } else {
                drawFilledPath(canvas, path, iRadarDataSet.getFillColor(), iRadarDataSet.getFillAlpha());
            }
        }
        this.mRenderPaint.setStrokeWidth(iRadarDataSet.getLineWidth());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        if (!iRadarDataSet.isDrawFilledEnabled() || iRadarDataSet.getFillAlpha() < 255) {
            canvas.drawPath(path, this.mRenderPaint);
        }
        MPPointF.recycleInstance(centerOffsets);
        MPPointF.recycleInstance(mPPointF);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
        drawWeb(canvas);
    }

    public void drawHighlightCircle(Canvas canvas, MPPointF mPPointF, float f2, float f3, int i2, int i3, float f4) {
        canvas.save();
        float fConvertDpToPixel = Utils.convertDpToPixel(f3);
        float fConvertDpToPixel2 = Utils.convertDpToPixel(f2);
        if (i2 != 1122867) {
            Path path = this.mDrawHighlightCirclePathBuffer;
            path.reset();
            path.addCircle(mPPointF.f6566x, mPPointF.f6567y, fConvertDpToPixel, Path.Direction.CW);
            if (fConvertDpToPixel2 > 0.0f) {
                path.addCircle(mPPointF.f6566x, mPPointF.f6567y, fConvertDpToPixel2, Path.Direction.CCW);
            }
            this.mHighlightCirclePaint.setColor(i2);
            this.mHighlightCirclePaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, this.mHighlightCirclePaint);
        }
        if (i3 != 1122867) {
            this.mHighlightCirclePaint.setColor(i3);
            this.mHighlightCirclePaint.setStyle(Paint.Style.STROKE);
            this.mHighlightCirclePaint.setStrokeWidth(Utils.convertDpToPixel(f4));
            canvas.drawCircle(mPPointF.f6566x, mPPointF.f6567y, fConvertDpToPixel, this.mHighlightCirclePaint);
        }
        canvas.restore();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00e3  */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawHighlighted(android.graphics.Canvas r20, com.github.mikephil.charting.highlight.Highlight[] r21) {
        /*
            r19 = this;
            r8 = r19
            r9 = r21
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            float r10 = r0.getSliceAngle()
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            float r11 = r0.getFactor()
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            com.github.mikephil.charting.utils.MPPointF r12 = r0.getCenterOffsets()
            r0 = 0
            com.github.mikephil.charting.utils.MPPointF r13 = com.github.mikephil.charting.utils.MPPointF.getInstance(r0, r0)
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            com.github.mikephil.charting.data.ChartData r0 = r0.getData()
            r14 = r0
            com.github.mikephil.charting.data.RadarData r14 = (com.github.mikephil.charting.data.RadarData) r14
            int r15 = r9.length
            r7 = 0
            r6 = r7
        L27:
            if (r6 >= r15) goto Led
            r0 = r9[r6]
            int r1 = r0.getDataSetIndex()
            com.github.mikephil.charting.interfaces.datasets.IDataSet r1 = r14.getDataSetByIndex(r1)
            com.github.mikephil.charting.interfaces.datasets.IRadarDataSet r1 = (com.github.mikephil.charting.interfaces.datasets.IRadarDataSet) r1
            if (r1 == 0) goto Le3
            boolean r2 = r1.isHighlightEnabled()
            if (r2 != 0) goto L3f
            goto Le3
        L3f:
            float r2 = r0.getX()
            int r2 = (int) r2
            com.github.mikephil.charting.data.Entry r2 = r1.getEntryForIndex(r2)
            com.github.mikephil.charting.data.RadarEntry r2 = (com.github.mikephil.charting.data.RadarEntry) r2
            boolean r3 = r8.isInBoundsX(r2, r1)
            if (r3 != 0) goto L52
            goto Le3
        L52:
            float r2 = r2.getY()
            com.github.mikephil.charting.charts.RadarChart r3 = r8.mChart
            float r3 = r3.getYChartMin()
            float r2 = r2 - r3
            float r2 = r2 * r11
            com.github.mikephil.charting.animation.ChartAnimator r3 = r8.mAnimator
            float r3 = r3.getPhaseY()
            float r2 = r2 * r3
            float r3 = r0.getX()
            float r3 = r3 * r10
            com.github.mikephil.charting.animation.ChartAnimator r4 = r8.mAnimator
            float r4 = r4.getPhaseX()
            float r3 = r3 * r4
            com.github.mikephil.charting.charts.RadarChart r4 = r8.mChart
            float r4 = r4.getRotationAngle()
            float r3 = r3 + r4
            com.github.mikephil.charting.utils.Utils.getPosition(r12, r2, r3, r13)
            float r2 = r13.f6566x
            float r3 = r13.f6567y
            r0.setDraw(r2, r3)
            float r0 = r13.f6566x
            float r2 = r13.f6567y
            r5 = r20
            r8.drawHighlightLines(r5, r0, r2, r1)
            boolean r0 = r1.isDrawHighlightCircleEnabled()
            if (r0 == 0) goto Le3
            float r0 = r13.f6566x
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 != 0) goto Le3
            float r0 = r13.f6567y
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 != 0) goto Le3
            int r0 = r1.getHighlightCircleStrokeColor()
            r2 = 1122867(0x112233, float:1.573472E-39)
            if (r0 != r2) goto Lae
            int r0 = r1.getColor(r7)
        Lae:
            int r2 = r1.getHighlightCircleStrokeAlpha()
            r3 = 255(0xff, float:3.57E-43)
            if (r2 >= r3) goto Lbe
            int r2 = r1.getHighlightCircleStrokeAlpha()
            int r0 = com.github.mikephil.charting.utils.ColorTemplate.colorWithAlpha(r0, r2)
        Lbe:
            r16 = r0
            float r3 = r1.getHighlightCircleInnerRadius()
            float r4 = r1.getHighlightCircleOuterRadius()
            int r17 = r1.getHighlightCircleFillColor()
            float r18 = r1.getHighlightCircleStrokeWidth()
            r0 = r19
            r1 = r20
            r2 = r13
            r5 = r17
            r17 = r6
            r6 = r16
            r16 = r7
            r7 = r18
            r0.drawHighlightCircle(r1, r2, r3, r4, r5, r6, r7)
            goto Le7
        Le3:
            r17 = r6
            r16 = r7
        Le7:
            int r6 = r17 + 1
            r7 = r16
            goto L27
        Led:
            com.github.mikephil.charting.utils.MPPointF.recycleInstance(r12)
            com.github.mikephil.charting.utils.MPPointF.recycleInstance(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.RadarChartRenderer.drawHighlighted(android.graphics.Canvas, com.github.mikephil.charting.highlight.Highlight[]):void");
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValue(Canvas canvas, String str, float f2, float f3, int i2) {
        this.mValuePaint.setColor(i2);
        canvas.drawText(str, f2, f3, this.mValuePaint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        int i2;
        float f2;
        RadarEntry radarEntry;
        int i3;
        IRadarDataSet iRadarDataSet;
        int i4;
        float f3;
        MPPointF mPPointF;
        ValueFormatter valueFormatter;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        MPPointF centerOffsets = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        MPPointF mPPointF3 = MPPointF.getInstance(0.0f, 0.0f);
        float fConvertDpToPixel = Utils.convertDpToPixel(5.0f);
        int i5 = 0;
        while (i5 < ((RadarData) this.mChart.getData()).getDataSetCount()) {
            IRadarDataSet dataSetByIndex = ((RadarData) this.mChart.getData()).getDataSetByIndex(i5);
            if (shouldDrawValues(dataSetByIndex)) {
                applyValueTextStyle(dataSetByIndex);
                ValueFormatter valueFormatter2 = dataSetByIndex.getValueFormatter();
                MPPointF mPPointF4 = MPPointF.getInstance(dataSetByIndex.getIconsOffset());
                mPPointF4.f6566x = Utils.convertDpToPixel(mPPointF4.f6566x);
                mPPointF4.f6567y = Utils.convertDpToPixel(mPPointF4.f6567y);
                int i6 = 0;
                while (i6 < dataSetByIndex.getEntryCount()) {
                    RadarEntry radarEntry2 = (RadarEntry) dataSetByIndex.getEntryForIndex(i6);
                    MPPointF mPPointF5 = mPPointF4;
                    float f4 = i6 * sliceAngle * phaseX;
                    Utils.getPosition(centerOffsets, (radarEntry2.getY() - this.mChart.getYChartMin()) * factor * phaseY, f4 + this.mChart.getRotationAngle(), mPPointF2);
                    if (dataSetByIndex.isDrawValuesEnabled()) {
                        radarEntry = radarEntry2;
                        i3 = i6;
                        f3 = phaseX;
                        mPPointF = mPPointF5;
                        valueFormatter = valueFormatter2;
                        iRadarDataSet = dataSetByIndex;
                        i4 = i5;
                        drawValue(canvas, valueFormatter2.getRadarLabel(radarEntry2), mPPointF2.f6566x, mPPointF2.f6567y - fConvertDpToPixel, dataSetByIndex.getValueTextColor(i6));
                    } else {
                        radarEntry = radarEntry2;
                        i3 = i6;
                        iRadarDataSet = dataSetByIndex;
                        i4 = i5;
                        f3 = phaseX;
                        mPPointF = mPPointF5;
                        valueFormatter = valueFormatter2;
                    }
                    if (radarEntry.getIcon() != null && iRadarDataSet.isDrawIconsEnabled()) {
                        Drawable icon = radarEntry.getIcon();
                        Utils.getPosition(centerOffsets, (radarEntry.getY() * factor * phaseY) + mPPointF.f6567y, f4 + this.mChart.getRotationAngle(), mPPointF3);
                        float f5 = mPPointF3.f6567y + mPPointF.f6566x;
                        mPPointF3.f6567y = f5;
                        Utils.drawImage(canvas, icon, (int) mPPointF3.f6566x, (int) f5, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                    }
                    i6 = i3 + 1;
                    mPPointF4 = mPPointF;
                    dataSetByIndex = iRadarDataSet;
                    valueFormatter2 = valueFormatter;
                    i5 = i4;
                    phaseX = f3;
                }
                i2 = i5;
                f2 = phaseX;
                MPPointF.recycleInstance(mPPointF4);
            } else {
                i2 = i5;
                f2 = phaseX;
            }
            i5 = i2 + 1;
            phaseX = f2;
        }
        MPPointF.recycleInstance(centerOffsets);
        MPPointF.recycleInstance(mPPointF2);
        MPPointF.recycleInstance(mPPointF3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawWeb(Canvas canvas) {
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        float rotationAngle = this.mChart.getRotationAngle();
        MPPointF centerOffsets = this.mChart.getCenterOffsets();
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidth());
        this.mWebPaint.setColor(this.mChart.getWebColor());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        int skipWebLineCount = this.mChart.getSkipWebLineCount() + 1;
        int entryCount = ((RadarData) this.mChart.getData()).getMaxEntryCountSet().getEntryCount();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        for (int i2 = 0; i2 < entryCount; i2 += skipWebLineCount) {
            Utils.getPosition(centerOffsets, this.mChart.getYRange() * factor, (i2 * sliceAngle) + rotationAngle, mPPointF);
            canvas.drawLine(centerOffsets.f6566x, centerOffsets.f6567y, mPPointF.f6566x, mPPointF.f6567y, this.mWebPaint);
        }
        MPPointF.recycleInstance(mPPointF);
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidthInner());
        this.mWebPaint.setColor(this.mChart.getWebColorInner());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        int i3 = this.mChart.getYAxis().mEntryCount;
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        MPPointF mPPointF3 = MPPointF.getInstance(0.0f, 0.0f);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = 0;
            while (i5 < ((RadarData) this.mChart.getData()).getEntryCount()) {
                float yChartMin = (this.mChart.getYAxis().mEntries[i4] - this.mChart.getYChartMin()) * factor;
                Utils.getPosition(centerOffsets, yChartMin, (i5 * sliceAngle) + rotationAngle, mPPointF2);
                i5++;
                Utils.getPosition(centerOffsets, yChartMin, (i5 * sliceAngle) + rotationAngle, mPPointF3);
                canvas.drawLine(mPPointF2.f6566x, mPPointF2.f6567y, mPPointF3.f6566x, mPPointF3.f6567y, this.mWebPaint);
            }
        }
        MPPointF.recycleInstance(mPPointF2);
        MPPointF.recycleInstance(mPPointF3);
    }

    public Paint getWebPaint() {
        return this.mWebPaint;
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }
}
