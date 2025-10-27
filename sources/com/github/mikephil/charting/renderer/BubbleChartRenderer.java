package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* loaded from: classes3.dex */
public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer;
    protected BubbleDataProvider mChart;
    private float[] pointBuffer;
    private float[] sizeBuffer;

    public BubbleChartRenderer(BubbleDataProvider bubbleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.sizeBuffer = new float[4];
        this.pointBuffer = new float[2];
        this._hsvBuffer = new float[3];
        this.mChart = bubbleDataProvider;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        for (T t2 : this.mChart.getBubbleData().getDataSets()) {
            if (t2.isVisible()) {
                drawDataSet(canvas, t2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawDataSet(Canvas canvas, IBubbleDataSet iBubbleDataSet) {
        if (iBubbleDataSet.getEntryCount() < 1) {
            return;
        }
        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mXBounds.set(this.mChart, iBubbleDataSet);
        float[] fArr = this.sizeBuffer;
        fArr[0] = 0.0f;
        fArr[2] = 1.0f;
        transformer.pointValuesToPixel(fArr);
        boolean zIsNormalizeSizeEnabled = iBubbleDataSet.isNormalizeSizeEnabled();
        float[] fArr2 = this.sizeBuffer;
        float fMin = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(fArr2[2] - fArr2[0]));
        int i2 = this.mXBounds.min;
        while (true) {
            BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
            if (i2 > xBounds.range + xBounds.min) {
                return;
            }
            BubbleEntry bubbleEntry = (BubbleEntry) iBubbleDataSet.getEntryForIndex(i2);
            this.pointBuffer[0] = bubbleEntry.getX();
            this.pointBuffer[1] = bubbleEntry.getY() * phaseY;
            transformer.pointValuesToPixel(this.pointBuffer);
            float shapeSize = getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), fMin, zIsNormalizeSizeEnabled) / 2.0f;
            if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeSize) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeSize) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeSize)) {
                if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeSize)) {
                    return;
                }
                this.mRenderPaint.setColor(iBubbleDataSet.getColor((int) bubbleEntry.getX()));
                float[] fArr3 = this.pointBuffer;
                canvas.drawCircle(fArr3[0], fArr3[1], shapeSize, this.mRenderPaint);
            }
            i2++;
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x013a  */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawHighlighted(android.graphics.Canvas r18, com.github.mikephil.charting.highlight.Highlight[] r19) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.BubbleChartRenderer.drawHighlighted(android.graphics.Canvas, com.github.mikephil.charting.highlight.Highlight[]):void");
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
        BubbleEntry bubbleEntry;
        float f2;
        float f3;
        BubbleData bubbleData = this.mChart.getBubbleData();
        if (bubbleData != null && isDrawingValuesAllowed(this.mChart)) {
            List<T> dataSets = bubbleData.getDataSets();
            float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, "1");
            for (int i3 = 0; i3 < dataSets.size(); i3++) {
                IBubbleDataSet iBubbleDataSet = (IBubbleDataSet) dataSets.get(i3);
                if (shouldDrawValues(iBubbleDataSet) && iBubbleDataSet.getEntryCount() >= 1) {
                    applyValueTextStyle(iBubbleDataSet);
                    float fMax = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                    float phaseY = this.mAnimator.getPhaseY();
                    this.mXBounds.set(this.mChart, iBubbleDataSet);
                    Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                    float[] fArrGenerateTransformedValuesBubble = transformer.generateTransformedValuesBubble(iBubbleDataSet, phaseY, xBounds.min, xBounds.max);
                    float f4 = fMax == 1.0f ? phaseY : fMax;
                    ValueFormatter valueFormatter = iBubbleDataSet.getValueFormatter();
                    MPPointF mPPointF = MPPointF.getInstance(iBubbleDataSet.getIconsOffset());
                    mPPointF.f6566x = Utils.convertDpToPixel(mPPointF.f6566x);
                    mPPointF.f6567y = Utils.convertDpToPixel(mPPointF.f6567y);
                    for (int i4 = 0; i4 < fArrGenerateTransformedValuesBubble.length; i4 = i2 + 2) {
                        int i5 = i4 / 2;
                        int valueTextColor = iBubbleDataSet.getValueTextColor(this.mXBounds.min + i5);
                        int iArgb = Color.argb(Math.round(255.0f * f4), Color.red(valueTextColor), Color.green(valueTextColor), Color.blue(valueTextColor));
                        float f5 = fArrGenerateTransformedValuesBubble[i4];
                        float f6 = fArrGenerateTransformedValuesBubble[i4 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f5)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(f5) && this.mViewPortHandler.isInBoundsY(f6)) {
                            BubbleEntry bubbleEntry2 = (BubbleEntry) iBubbleDataSet.getEntryForIndex(i5 + this.mXBounds.min);
                            if (iBubbleDataSet.isDrawValuesEnabled()) {
                                bubbleEntry = bubbleEntry2;
                                f2 = f6;
                                f3 = f5;
                                i2 = i4;
                                drawValue(canvas, valueFormatter.getBubbleLabel(bubbleEntry2), f5, f6 + (0.5f * fCalcTextHeight), iArgb);
                            } else {
                                bubbleEntry = bubbleEntry2;
                                f2 = f6;
                                f3 = f5;
                                i2 = i4;
                            }
                            if (bubbleEntry.getIcon() != null && iBubbleDataSet.isDrawIconsEnabled()) {
                                Drawable icon = bubbleEntry.getIcon();
                                Utils.drawImage(canvas, icon, (int) (f3 + mPPointF.f6566x), (int) (f2 + mPPointF.f6567y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                            }
                        } else {
                            i2 = i4;
                        }
                    }
                    MPPointF.recycleInstance(mPPointF);
                }
            }
        }
    }

    public float getShapeSize(float f2, float f3, float f4, boolean z2) {
        if (z2) {
            f2 = f3 == 0.0f ? 1.0f : (float) Math.sqrt(f2 / f3);
        }
        return f4 * f2;
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }
}
