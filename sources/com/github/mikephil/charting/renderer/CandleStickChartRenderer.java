package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* loaded from: classes3.dex */
public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {
    private float[] mBodyBuffers;
    protected CandleDataProvider mChart;
    private float[] mCloseBuffers;
    private float[] mOpenBuffers;
    private float[] mRangeBuffers;
    private float[] mShadowBuffers;

    public CandleStickChartRenderer(CandleDataProvider candleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mShadowBuffers = new float[8];
        this.mBodyBuffers = new float[4];
        this.mRangeBuffers = new float[4];
        this.mOpenBuffers = new float[4];
        this.mCloseBuffers = new float[4];
        this.mChart = candleDataProvider;
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        for (T t2 : this.mChart.getCandleData().getDataSets()) {
            if (t2.isVisible()) {
                drawDataSet(canvas, t2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawDataSet(Canvas canvas, ICandleDataSet iCandleDataSet) {
        Transformer transformer = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        float barSpace = iCandleDataSet.getBarSpace();
        boolean showCandleBar = iCandleDataSet.getShowCandleBar();
        this.mXBounds.set(this.mChart, iCandleDataSet);
        this.mRenderPaint.setStrokeWidth(iCandleDataSet.getShadowWidth());
        int i2 = this.mXBounds.min;
        while (true) {
            BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
            if (i2 > xBounds.range + xBounds.min) {
                return;
            }
            CandleEntry candleEntry = (CandleEntry) iCandleDataSet.getEntryForIndex(i2);
            if (candleEntry != null) {
                float x2 = candleEntry.getX();
                float open = candleEntry.getOpen();
                float close = candleEntry.getClose();
                float high = candleEntry.getHigh();
                float low = candleEntry.getLow();
                if (showCandleBar) {
                    float[] fArr = this.mShadowBuffers;
                    fArr[0] = x2;
                    fArr[2] = x2;
                    fArr[4] = x2;
                    fArr[6] = x2;
                    if (open > close) {
                        fArr[1] = high * phaseY;
                        fArr[3] = open * phaseY;
                        fArr[5] = low * phaseY;
                        fArr[7] = close * phaseY;
                    } else if (open < close) {
                        fArr[1] = high * phaseY;
                        fArr[3] = close * phaseY;
                        fArr[5] = low * phaseY;
                        fArr[7] = open * phaseY;
                    } else {
                        fArr[1] = high * phaseY;
                        float f2 = open * phaseY;
                        fArr[3] = f2;
                        fArr[5] = low * phaseY;
                        fArr[7] = f2;
                    }
                    transformer.pointValuesToPixel(fArr);
                    if (!iCandleDataSet.getShadowColorSameAsCandle()) {
                        this.mRenderPaint.setColor(iCandleDataSet.getShadowColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getShadowColor());
                    } else if (open > close) {
                        this.mRenderPaint.setColor(iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getDecreasingColor());
                    } else if (open < close) {
                        this.mRenderPaint.setColor(iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getIncreasingColor());
                    } else {
                        this.mRenderPaint.setColor(iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getNeutralColor());
                    }
                    this.mRenderPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawLines(this.mShadowBuffers, this.mRenderPaint);
                    float[] fArr2 = this.mBodyBuffers;
                    fArr2[0] = (x2 - 0.5f) + barSpace;
                    fArr2[1] = close * phaseY;
                    fArr2[2] = (x2 + 0.5f) - barSpace;
                    fArr2[3] = open * phaseY;
                    transformer.pointValuesToPixel(fArr2);
                    if (open > close) {
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(i2));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getDecreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getDecreasingPaintStyle());
                        float[] fArr3 = this.mBodyBuffers;
                        canvas.drawRect(fArr3[0], fArr3[3], fArr3[2], fArr3[1], this.mRenderPaint);
                    } else if (open < close) {
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(i2));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getIncreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getIncreasingPaintStyle());
                        float[] fArr4 = this.mBodyBuffers;
                        canvas.drawRect(fArr4[0], fArr4[1], fArr4[2], fArr4[3], this.mRenderPaint);
                    } else {
                        if (iCandleDataSet.getNeutralColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(i2));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getNeutralColor());
                        }
                        float[] fArr5 = this.mBodyBuffers;
                        canvas.drawLine(fArr5[0], fArr5[1], fArr5[2], fArr5[3], this.mRenderPaint);
                    }
                } else {
                    float[] fArr6 = this.mRangeBuffers;
                    fArr6[0] = x2;
                    fArr6[1] = high * phaseY;
                    fArr6[2] = x2;
                    fArr6[3] = low * phaseY;
                    float[] fArr7 = this.mOpenBuffers;
                    fArr7[0] = (x2 - 0.5f) + barSpace;
                    float f3 = open * phaseY;
                    fArr7[1] = f3;
                    fArr7[2] = x2;
                    fArr7[3] = f3;
                    float[] fArr8 = this.mCloseBuffers;
                    fArr8[0] = (0.5f + x2) - barSpace;
                    float f4 = close * phaseY;
                    fArr8[1] = f4;
                    fArr8[2] = x2;
                    fArr8[3] = f4;
                    transformer.pointValuesToPixel(fArr6);
                    transformer.pointValuesToPixel(this.mOpenBuffers);
                    transformer.pointValuesToPixel(this.mCloseBuffers);
                    this.mRenderPaint.setColor(open > close ? iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getDecreasingColor() : open < close ? iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getIncreasingColor() : iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(i2) : iCandleDataSet.getNeutralColor());
                    float[] fArr9 = this.mRangeBuffers;
                    canvas.drawLine(fArr9[0], fArr9[1], fArr9[2], fArr9[3], this.mRenderPaint);
                    float[] fArr10 = this.mOpenBuffers;
                    canvas.drawLine(fArr10[0], fArr10[1], fArr10[2], fArr10[3], this.mRenderPaint);
                    float[] fArr11 = this.mCloseBuffers;
                    canvas.drawLine(fArr11[0], fArr11[1], fArr11[2], fArr11[3], this.mRenderPaint);
                }
            }
            i2++;
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        CandleData candleData = this.mChart.getCandleData();
        for (Highlight highlight : highlightArr) {
            ILineScatterCandleRadarDataSet iLineScatterCandleRadarDataSet = (ICandleDataSet) candleData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iLineScatterCandleRadarDataSet != null && iLineScatterCandleRadarDataSet.isHighlightEnabled()) {
                CandleEntry candleEntry = (CandleEntry) iLineScatterCandleRadarDataSet.getEntryForXValue(highlight.getX(), highlight.getY());
                if (isInBoundsX(candleEntry, iLineScatterCandleRadarDataSet)) {
                    MPPointD pixelForValues = this.mChart.getTransformer(iLineScatterCandleRadarDataSet.getAxisDependency()).getPixelForValues(candleEntry.getX(), ((candleEntry.getLow() * this.mAnimator.getPhaseY()) + (candleEntry.getHigh() * this.mAnimator.getPhaseY())) / 2.0f);
                    highlight.setDraw((float) pixelForValues.f6564x, (float) pixelForValues.f6565y);
                    drawHighlightLines(canvas, (float) pixelForValues.f6564x, (float) pixelForValues.f6565y, iLineScatterCandleRadarDataSet);
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValue(Canvas canvas, String str, float f2, float f3, int i2) {
        this.mValuePaint.setColor(i2);
        canvas.drawText(str, f2, f3, this.mValuePaint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        ICandleDataSet iCandleDataSet;
        CandleEntry candleEntry;
        float f2;
        if (isDrawingValuesAllowed(this.mChart)) {
            List<T> dataSets = this.mChart.getCandleData().getDataSets();
            for (int i2 = 0; i2 < dataSets.size(); i2++) {
                ICandleDataSet iCandleDataSet2 = (ICandleDataSet) dataSets.get(i2);
                if (shouldDrawValues(iCandleDataSet2) && iCandleDataSet2.getEntryCount() >= 1) {
                    applyValueTextStyle(iCandleDataSet2);
                    Transformer transformer = this.mChart.getTransformer(iCandleDataSet2.getAxisDependency());
                    this.mXBounds.set(this.mChart, iCandleDataSet2);
                    float phaseX = this.mAnimator.getPhaseX();
                    float phaseY = this.mAnimator.getPhaseY();
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                    float[] fArrGenerateTransformedValuesCandle = transformer.generateTransformedValuesCandle(iCandleDataSet2, phaseX, phaseY, xBounds.min, xBounds.max);
                    float fConvertDpToPixel = Utils.convertDpToPixel(5.0f);
                    ValueFormatter valueFormatter = iCandleDataSet2.getValueFormatter();
                    MPPointF mPPointF = MPPointF.getInstance(iCandleDataSet2.getIconsOffset());
                    mPPointF.f6566x = Utils.convertDpToPixel(mPPointF.f6566x);
                    mPPointF.f6567y = Utils.convertDpToPixel(mPPointF.f6567y);
                    int i3 = 0;
                    while (i3 < fArrGenerateTransformedValuesCandle.length) {
                        float f3 = fArrGenerateTransformedValuesCandle[i3];
                        float f4 = fArrGenerateTransformedValuesCandle[i3 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f3)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(f3) && this.mViewPortHandler.isInBoundsY(f4)) {
                            int i4 = i3 / 2;
                            CandleEntry candleEntry2 = (CandleEntry) iCandleDataSet2.getEntryForIndex(this.mXBounds.min + i4);
                            if (iCandleDataSet2.isDrawValuesEnabled()) {
                                candleEntry = candleEntry2;
                                f2 = f4;
                                iCandleDataSet = iCandleDataSet2;
                                drawValue(canvas, valueFormatter.getCandleLabel(candleEntry2), f3, f4 - fConvertDpToPixel, iCandleDataSet2.getValueTextColor(i4));
                            } else {
                                candleEntry = candleEntry2;
                                f2 = f4;
                                iCandleDataSet = iCandleDataSet2;
                            }
                            if (candleEntry.getIcon() != null && iCandleDataSet.isDrawIconsEnabled()) {
                                Drawable icon = candleEntry.getIcon();
                                Utils.drawImage(canvas, icon, (int) (f3 + mPPointF.f6566x), (int) (f2 + mPPointF.f6567y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                            }
                        } else {
                            iCandleDataSet = iCandleDataSet2;
                        }
                        i3 += 2;
                        iCandleDataSet2 = iCandleDataSet;
                    }
                    MPPointF.recycleInstance(mPPointF);
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }
}
