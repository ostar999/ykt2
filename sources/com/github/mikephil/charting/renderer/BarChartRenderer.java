package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tencent.connect.common.Constants;
import java.util.List;

/* loaded from: classes3.dex */
public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect;
    private RectF mBarShadowRectBuffer;
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    public BarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mBarRect = new RectF();
        this.mBarShadowRectBuffer = new RectF();
        this.mChart = barDataProvider;
        Paint paint = new Paint(1);
        this.mHighlightPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        this.mHighlightPaint.setAlpha(120);
        Paint paint2 = new Paint(1);
        this.mShadowPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint(1);
        this.mBarBorderPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        BarData barData = this.mChart.getBarData();
        for (int i2 = 0; i2 < barData.getDataSetCount(); i2++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i2);
            if (iBarDataSet.isVisible()) {
                drawDataSet(canvas, iBarDataSet, i2);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int i2) {
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        boolean z2 = iBarDataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            float barWidth = this.mChart.getBarData().getBarWidth() / 2.0f;
            int iMin = Math.min((int) Math.ceil(iBarDataSet.getEntryCount() * phaseX), iBarDataSet.getEntryCount());
            for (int i3 = 0; i3 < iMin; i3++) {
                float x2 = ((BarEntry) iBarDataSet.getEntryForIndex(i3)).getX();
                RectF rectF = this.mBarShadowRectBuffer;
                rectF.left = x2 - barWidth;
                rectF.right = x2 + barWidth;
                transformer.rectValueToPixel(rectF);
                if (this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
                    if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                    this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                    canvas.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
                }
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[i2];
        barBuffer.setPhases(phaseX, phaseY);
        barBuffer.setDataSet(i2);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet);
        transformer.pointValuesToPixel(barBuffer.buffer);
        boolean z3 = iBarDataSet.getColors().size() == 1;
        if (z3) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
        }
        for (int i4 = 0; i4 < barBuffer.size(); i4 += 4) {
            int i5 = i4 + 2;
            if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i5])) {
                if (!this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i4])) {
                    return;
                }
                if (!z3) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(i4 / 4));
                }
                if (iBarDataSet.getGradientColor() != null) {
                    GradientColor gradientColor = iBarDataSet.getGradientColor();
                    Paint paint = this.mRenderPaint;
                    float[] fArr = barBuffer.buffer;
                    float f2 = fArr[i4];
                    paint.setShader(new LinearGradient(f2, fArr[i4 + 3], f2, fArr[i4 + 1], gradientColor.getStartColor(), gradientColor.getEndColor(), Shader.TileMode.MIRROR));
                }
                if (iBarDataSet.getGradientColors() != null) {
                    Paint paint2 = this.mRenderPaint;
                    float[] fArr2 = barBuffer.buffer;
                    float f3 = fArr2[i4];
                    float f4 = fArr2[i4 + 3];
                    float f5 = fArr2[i4 + 1];
                    int i6 = i4 / 4;
                    paint2.setShader(new LinearGradient(f3, f4, f3, f5, iBarDataSet.getGradientColor(i6).getStartColor(), iBarDataSet.getGradientColor(i6).getEndColor(), Shader.TileMode.MIRROR));
                }
                float[] fArr3 = barBuffer.buffer;
                int i7 = i4 + 1;
                int i8 = i4 + 3;
                canvas.drawRect(fArr3[i4], fArr3[i7], fArr3[i5], fArr3[i8], this.mRenderPaint);
                if (z2) {
                    float[] fArr4 = barBuffer.buffer;
                    canvas.drawRect(fArr4[i4], fArr4[i7], fArr4[i5], fArr4[i8], this.mBarBorderPaint);
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        float y2;
        float f2;
        float f3;
        float f4;
        BarData barData = this.mChart.getBarData();
        for (Highlight highlight : highlightArr) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iBarDataSet != null && iBarDataSet.isHighlightEnabled()) {
                BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForXValue(highlight.getX(), highlight.getY());
                if (isInBoundsX(barEntry, iBarDataSet)) {
                    Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                    this.mHighlightPaint.setColor(iBarDataSet.getHighLightColor());
                    this.mHighlightPaint.setAlpha(iBarDataSet.getHighLightAlpha());
                    if (!(highlight.getStackIndex() >= 0 && barEntry.isStacked())) {
                        y2 = barEntry.getY();
                        f2 = 0.0f;
                    } else if (this.mChart.isHighlightFullBarEnabled()) {
                        y2 = barEntry.getPositiveSum();
                        f2 = -barEntry.getNegativeSum();
                    } else {
                        Range range = barEntry.getRanges()[highlight.getStackIndex()];
                        f4 = range.from;
                        f3 = range.to;
                        prepareBarHighlight(barEntry.getX(), f4, f3, barData.getBarWidth() / 2.0f, transformer);
                        setHighlightDrawPos(highlight, this.mBarRect);
                        canvas.drawRect(this.mBarRect, this.mHighlightPaint);
                    }
                    f3 = f2;
                    f4 = y2;
                    prepareBarHighlight(barEntry.getX(), f4, f3, barData.getBarWidth() / 2.0f, transformer);
                    setHighlightDrawPos(highlight, this.mBarRect);
                    canvas.drawRect(this.mBarRect, this.mHighlightPaint);
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
        List list;
        MPPointF mPPointF;
        int i2;
        float f2;
        boolean z2;
        float[] fArr;
        Transformer transformer;
        int i3;
        float f3;
        int i4;
        BarEntry barEntry;
        float[] fArr2;
        float f4;
        float f5;
        float f6;
        BarEntry barEntry2;
        float f7;
        boolean z3;
        int i5;
        ValueFormatter valueFormatter;
        List list2;
        MPPointF mPPointF2;
        BarEntry barEntry3;
        float f8;
        if (isDrawingValuesAllowed(this.mChart)) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float fConvertDpToPixel = Utils.convertDpToPixel(4.5f);
            boolean zIsDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i6 = 0;
            while (i6 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i6);
                if (shouldDrawValues(iBarDataSet)) {
                    applyValueTextStyle(iBarDataSet);
                    boolean zIsInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
                    float f9 = zIsDrawValueAboveBarEnabled ? -fConvertDpToPixel : fCalcTextHeight + fConvertDpToPixel;
                    float f10 = zIsDrawValueAboveBarEnabled ? fCalcTextHeight + fConvertDpToPixel : -fConvertDpToPixel;
                    if (zIsInverted) {
                        f9 = (-f9) - fCalcTextHeight;
                        f10 = (-f10) - fCalcTextHeight;
                    }
                    float f11 = f9;
                    float f12 = f10;
                    BarBuffer barBuffer = this.mBarBuffers[i6];
                    float phaseY = this.mAnimator.getPhaseY();
                    ValueFormatter valueFormatter2 = iBarDataSet.getValueFormatter();
                    MPPointF mPPointF3 = MPPointF.getInstance(iBarDataSet.getIconsOffset());
                    mPPointF3.f6566x = Utils.convertDpToPixel(mPPointF3.f6566x);
                    mPPointF3.f6567y = Utils.convertDpToPixel(mPPointF3.f6567y);
                    if (iBarDataSet.isStacked()) {
                        list = dataSets;
                        mPPointF = mPPointF3;
                        Transformer transformer2 = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                        int i7 = 0;
                        int length = 0;
                        while (i7 < iBarDataSet.getEntryCount() * this.mAnimator.getPhaseX()) {
                            BarEntry barEntry4 = (BarEntry) iBarDataSet.getEntryForIndex(i7);
                            float[] yVals = barEntry4.getYVals();
                            float[] fArr3 = barBuffer.buffer;
                            float f13 = (fArr3[length] + fArr3[length + 2]) / 2.0f;
                            int valueTextColor = iBarDataSet.getValueTextColor(i7);
                            if (yVals != null) {
                                BarEntry barEntry5 = barEntry4;
                                i2 = i7;
                                f2 = fConvertDpToPixel;
                                z2 = zIsDrawValueAboveBarEnabled;
                                fArr = yVals;
                                transformer = transformer2;
                                float f14 = f13;
                                int length2 = fArr.length * 2;
                                float[] fArr4 = new float[length2];
                                float f15 = -barEntry5.getNegativeSum();
                                float f16 = 0.0f;
                                int i8 = 0;
                                int i9 = 0;
                                while (i8 < length2) {
                                    float f17 = fArr[i9];
                                    if (f17 == 0.0f && (f16 == 0.0f || f15 == 0.0f)) {
                                        float f18 = f15;
                                        f15 = f17;
                                        f5 = f18;
                                    } else if (f17 >= 0.0f) {
                                        f16 += f17;
                                        f5 = f15;
                                        f15 = f16;
                                    } else {
                                        f5 = f15 - f17;
                                    }
                                    fArr4[i8 + 1] = f15 * phaseY;
                                    i8 += 2;
                                    i9++;
                                    f15 = f5;
                                }
                                transformer.pointValuesToPixel(fArr4);
                                int i10 = 0;
                                while (i10 < length2) {
                                    float f19 = fArr[i10 / 2];
                                    float f20 = fArr4[i10 + 1] + (((f19 > 0.0f ? 1 : (f19 == 0.0f ? 0 : -1)) == 0 && (f15 > 0.0f ? 1 : (f15 == 0.0f ? 0 : -1)) == 0 && (f16 > 0.0f ? 1 : (f16 == 0.0f ? 0 : -1)) > 0) || (f19 > 0.0f ? 1 : (f19 == 0.0f ? 0 : -1)) < 0 ? f12 : f11);
                                    int i11 = i10;
                                    if (!this.mViewPortHandler.isInBoundsRight(f14)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsY(f20) && this.mViewPortHandler.isInBoundsLeft(f14)) {
                                        if (iBarDataSet.isDrawValuesEnabled()) {
                                            BarEntry barEntry6 = barEntry5;
                                            f4 = f20;
                                            i4 = i11;
                                            barEntry = barEntry6;
                                            fArr2 = fArr4;
                                            i3 = length2;
                                            f3 = f14;
                                            drawValue(canvas, valueFormatter2.getBarStackedLabel(f19, barEntry6), f14, f4, valueTextColor);
                                        } else {
                                            f4 = f20;
                                            i3 = length2;
                                            f3 = f14;
                                            i4 = i11;
                                            barEntry = barEntry5;
                                            fArr2 = fArr4;
                                        }
                                        if (barEntry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                            Drawable icon = barEntry.getIcon();
                                            Utils.drawImage(canvas, icon, (int) (f3 + mPPointF.f6566x), (int) (f4 + mPPointF.f6567y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                        }
                                    } else {
                                        i3 = length2;
                                        f3 = f14;
                                        i4 = i11;
                                        barEntry = barEntry5;
                                        fArr2 = fArr4;
                                    }
                                    i10 = i4 + 2;
                                    fArr4 = fArr2;
                                    barEntry5 = barEntry;
                                    length2 = i3;
                                    f14 = f3;
                                }
                            } else {
                                if (!this.mViewPortHandler.isInBoundsRight(f13)) {
                                    break;
                                }
                                int i12 = length + 1;
                                if (this.mViewPortHandler.isInBoundsY(barBuffer.buffer[i12]) && this.mViewPortHandler.isInBoundsLeft(f13)) {
                                    if (iBarDataSet.isDrawValuesEnabled()) {
                                        f6 = f13;
                                        f2 = fConvertDpToPixel;
                                        fArr = yVals;
                                        barEntry2 = barEntry4;
                                        i2 = i7;
                                        z2 = zIsDrawValueAboveBarEnabled;
                                        transformer = transformer2;
                                        drawValue(canvas, valueFormatter2.getBarLabel(barEntry4), f6, barBuffer.buffer[i12] + (barEntry4.getY() >= 0.0f ? f11 : f12), valueTextColor);
                                    } else {
                                        f6 = f13;
                                        i2 = i7;
                                        f2 = fConvertDpToPixel;
                                        z2 = zIsDrawValueAboveBarEnabled;
                                        fArr = yVals;
                                        barEntry2 = barEntry4;
                                        transformer = transformer2;
                                    }
                                    if (barEntry2.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                        Drawable icon2 = barEntry2.getIcon();
                                        Utils.drawImage(canvas, icon2, (int) (mPPointF.f6566x + f6), (int) (barBuffer.buffer[i12] + (barEntry2.getY() >= 0.0f ? f11 : f12) + mPPointF.f6567y), icon2.getIntrinsicWidth(), icon2.getIntrinsicHeight());
                                    }
                                } else {
                                    transformer2 = transformer2;
                                    zIsDrawValueAboveBarEnabled = zIsDrawValueAboveBarEnabled;
                                    fConvertDpToPixel = fConvertDpToPixel;
                                    i7 = i7;
                                }
                            }
                            length = fArr == null ? length + 4 : length + (fArr.length * 4);
                            i7 = i2 + 1;
                            transformer2 = transformer;
                            zIsDrawValueAboveBarEnabled = z2;
                            fConvertDpToPixel = f2;
                        }
                    } else {
                        int i13 = 0;
                        while (i13 < barBuffer.buffer.length * this.mAnimator.getPhaseX()) {
                            float[] fArr5 = barBuffer.buffer;
                            float f21 = (fArr5[i13] + fArr5[i13 + 2]) / 2.0f;
                            if (!this.mViewPortHandler.isInBoundsRight(f21)) {
                                break;
                            }
                            int i14 = i13 + 1;
                            if (this.mViewPortHandler.isInBoundsY(barBuffer.buffer[i14]) && this.mViewPortHandler.isInBoundsLeft(f21)) {
                                int i15 = i13 / 4;
                                BarEntry barEntry7 = (BarEntry) iBarDataSet.getEntryForIndex(i15);
                                float y2 = barEntry7.getY();
                                if (iBarDataSet.isDrawValuesEnabled()) {
                                    String barLabel = valueFormatter2.getBarLabel(barEntry7);
                                    float[] fArr6 = barBuffer.buffer;
                                    barEntry3 = barEntry7;
                                    f8 = f21;
                                    i5 = i13;
                                    list2 = dataSets;
                                    mPPointF2 = mPPointF3;
                                    float f22 = y2 >= 0.0f ? fArr6[i14] + f11 : fArr6[i13 + 3] + f12;
                                    valueFormatter = valueFormatter2;
                                    drawValue(canvas, barLabel, f8, f22, iBarDataSet.getValueTextColor(i15));
                                } else {
                                    barEntry3 = barEntry7;
                                    f8 = f21;
                                    i5 = i13;
                                    valueFormatter = valueFormatter2;
                                    list2 = dataSets;
                                    mPPointF2 = mPPointF3;
                                }
                                if (barEntry3.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                    Drawable icon3 = barEntry3.getIcon();
                                    Utils.drawImage(canvas, icon3, (int) (f8 + mPPointF2.f6566x), (int) ((y2 >= 0.0f ? barBuffer.buffer[i14] + f11 : barBuffer.buffer[i5 + 3] + f12) + mPPointF2.f6567y), icon3.getIntrinsicWidth(), icon3.getIntrinsicHeight());
                                }
                            } else {
                                i5 = i13;
                                valueFormatter = valueFormatter2;
                                list2 = dataSets;
                                mPPointF2 = mPPointF3;
                            }
                            i13 = i5 + 4;
                            mPPointF3 = mPPointF2;
                            valueFormatter2 = valueFormatter;
                            dataSets = list2;
                        }
                        list = dataSets;
                        mPPointF = mPPointF3;
                    }
                    f7 = fConvertDpToPixel;
                    z3 = zIsDrawValueAboveBarEnabled;
                    MPPointF.recycleInstance(mPPointF);
                } else {
                    list = dataSets;
                    f7 = fConvertDpToPixel;
                    z3 = zIsDrawValueAboveBarEnabled;
                }
                i6++;
                zIsDrawValueAboveBarEnabled = z3;
                dataSets = list;
                fConvertDpToPixel = f7;
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        for (int i2 = 0; i2 < this.mBarBuffers.length; i2++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i2);
            this.mBarBuffers[i2] = new BarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    public void prepareBarHighlight(float f2, float f3, float f4, float f5, Transformer transformer) {
        this.mBarRect.set(f2 - f5, f3, f2 + f5, f4);
        transformer.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
    }

    public void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerX(), rectF.top);
    }
}
