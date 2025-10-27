package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tencent.connect.common.Constants;
import java.util.List;

/* loaded from: classes3.dex */
public class HorizontalBarChartRenderer extends BarChartRenderer {
    private RectF mBarShadowRectBuffer;

    public HorizontalBarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mBarShadowRectBuffer = new RectF();
        this.mValuePaint.setTextAlign(Paint.Align.LEFT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
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
                rectF.top = x2 - barWidth;
                rectF.bottom = x2 + barWidth;
                transformer.rectValueToPixel(rectF);
                if (this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) {
                    if (!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
                    this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
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
            int i5 = i4 + 3;
            if (!this.mViewPortHandler.isInBoundsTop(barBuffer.buffer[i5])) {
                return;
            }
            int i6 = i4 + 1;
            if (this.mViewPortHandler.isInBoundsBottom(barBuffer.buffer[i6])) {
                if (!z3) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(i4 / 4));
                }
                float[] fArr = barBuffer.buffer;
                int i7 = i4 + 2;
                canvas.drawRect(fArr[i4], fArr[i6], fArr[i7], fArr[i5], this.mRenderPaint);
                if (z2) {
                    float[] fArr2 = barBuffer.buffer;
                    canvas.drawRect(fArr2[i4], fArr2[i6], fArr2[i7], fArr2[i5], this.mBarBorderPaint);
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer, com.github.mikephil.charting.renderer.DataRenderer
    public void drawValue(Canvas canvas, String str, float f2, float f3, int i2) {
        this.mValuePaint.setColor(i2);
        canvas.drawText(str, f2, f3, this.mValuePaint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.BarChartRenderer, com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        List list;
        int i2;
        MPPointF mPPointF;
        int i3;
        float f2;
        float[] fArr;
        float f3;
        int i4;
        float[] fArr2;
        float f4;
        float f5;
        BarEntry barEntry;
        int i5;
        List list2;
        int i6;
        float f6;
        MPPointF mPPointF2;
        BarBuffer barBuffer;
        ValueFormatter valueFormatter;
        if (isDrawingValuesAllowed(this.mChart)) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float fConvertDpToPixel = Utils.convertDpToPixel(5.0f);
            boolean zIsDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i7 = 0;
            while (i7 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i7);
                if (shouldDrawValues(iBarDataSet)) {
                    boolean zIsInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    applyValueTextStyle(iBarDataSet);
                    float f7 = 2.0f;
                    float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) / 2.0f;
                    ValueFormatter valueFormatter2 = iBarDataSet.getValueFormatter();
                    BarBuffer barBuffer2 = this.mBarBuffers[i7];
                    float phaseY = this.mAnimator.getPhaseY();
                    MPPointF mPPointF3 = MPPointF.getInstance(iBarDataSet.getIconsOffset());
                    mPPointF3.f6566x = Utils.convertDpToPixel(mPPointF3.f6566x);
                    mPPointF3.f6567y = Utils.convertDpToPixel(mPPointF3.f6567y);
                    if (iBarDataSet.isStacked()) {
                        list = dataSets;
                        i2 = i7;
                        mPPointF = mPPointF3;
                        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                        int i8 = 0;
                        int length = 0;
                        while (i8 < iBarDataSet.getEntryCount() * this.mAnimator.getPhaseX()) {
                            BarEntry barEntry2 = (BarEntry) iBarDataSet.getEntryForIndex(i8);
                            int valueTextColor = iBarDataSet.getValueTextColor(i8);
                            float[] yVals = barEntry2.getYVals();
                            if (yVals == null) {
                                int i9 = length + 1;
                                if (!this.mViewPortHandler.isInBoundsTop(barBuffer2.buffer[i9])) {
                                    break;
                                }
                                if (this.mViewPortHandler.isInBoundsX(barBuffer2.buffer[length]) && this.mViewPortHandler.isInBoundsBottom(barBuffer2.buffer[i9])) {
                                    String barLabel = valueFormatter2.getBarLabel(barEntry2);
                                    float fCalcTextWidth = Utils.calcTextWidth(this.mValuePaint, barLabel);
                                    float f8 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth + fConvertDpToPixel);
                                    float f9 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth + fConvertDpToPixel) : fConvertDpToPixel;
                                    if (zIsInverted) {
                                        f8 = (-f8) - fCalcTextWidth;
                                        f9 = (-f9) - fCalcTextWidth;
                                    }
                                    float f10 = f8;
                                    float f11 = f9;
                                    if (iBarDataSet.isDrawValuesEnabled()) {
                                        float f12 = barBuffer2.buffer[length + 2] + (barEntry2.getY() >= 0.0f ? f10 : f11);
                                        float f13 = barBuffer2.buffer[i9] + fCalcTextHeight;
                                        f2 = fCalcTextHeight;
                                        fArr = yVals;
                                        barEntry = barEntry2;
                                        i3 = i8;
                                        drawValue(canvas, barLabel, f12, f13, valueTextColor);
                                    } else {
                                        i3 = i8;
                                        f2 = fCalcTextHeight;
                                        fArr = yVals;
                                        barEntry = barEntry2;
                                    }
                                    if (barEntry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                        Drawable icon = barEntry.getIcon();
                                        float f14 = barBuffer2.buffer[length + 2];
                                        if (barEntry.getY() < 0.0f) {
                                            f10 = f11;
                                        }
                                        Utils.drawImage(canvas, icon, (int) (f14 + f10 + mPPointF.f6566x), (int) (barBuffer2.buffer[i9] + mPPointF.f6567y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                    }
                                }
                            } else {
                                i3 = i8;
                                f2 = fCalcTextHeight;
                                fArr = yVals;
                                int length2 = fArr.length * 2;
                                float[] fArr3 = new float[length2];
                                float f15 = -barEntry2.getNegativeSum();
                                float f16 = 0.0f;
                                int i10 = 0;
                                int i11 = 0;
                                while (i10 < length2) {
                                    float f17 = fArr[i11];
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
                                    fArr3[i10] = f15 * phaseY;
                                    i10 += 2;
                                    i11++;
                                    f15 = f5;
                                }
                                transformer.pointValuesToPixel(fArr3);
                                int i12 = 0;
                                while (i12 < length2) {
                                    float f19 = fArr[i12 / 2];
                                    String barStackedLabel = valueFormatter2.getBarStackedLabel(f19, barEntry2);
                                    float fCalcTextWidth2 = Utils.calcTextWidth(this.mValuePaint, barStackedLabel);
                                    float f20 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth2 + fConvertDpToPixel);
                                    int i13 = length2;
                                    float f21 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth2 + fConvertDpToPixel) : fConvertDpToPixel;
                                    if (zIsInverted) {
                                        f20 = (-f20) - fCalcTextWidth2;
                                        f21 = (-f21) - fCalcTextWidth2;
                                    }
                                    boolean z2 = (f19 == 0.0f && f15 == 0.0f && f16 > 0.0f) || f19 < 0.0f;
                                    float f22 = fArr3[i12];
                                    if (z2) {
                                        f20 = f21;
                                    }
                                    float f23 = f22 + f20;
                                    float[] fArr4 = barBuffer2.buffer;
                                    float f24 = (fArr4[length + 1] + fArr4[length + 3]) / 2.0f;
                                    if (!this.mViewPortHandler.isInBoundsTop(f24)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsX(f23) && this.mViewPortHandler.isInBoundsBottom(f24)) {
                                        if (iBarDataSet.isDrawValuesEnabled()) {
                                            f3 = f24;
                                            i4 = i12;
                                            fArr2 = fArr3;
                                            f4 = f23;
                                            drawValue(canvas, barStackedLabel, f23, f24 + f2, valueTextColor);
                                        } else {
                                            f3 = f24;
                                            i4 = i12;
                                            fArr2 = fArr3;
                                            f4 = f23;
                                        }
                                        if (barEntry2.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                            Drawable icon2 = barEntry2.getIcon();
                                            Utils.drawImage(canvas, icon2, (int) (f4 + mPPointF.f6566x), (int) (f3 + mPPointF.f6567y), icon2.getIntrinsicWidth(), icon2.getIntrinsicHeight());
                                        }
                                    } else {
                                        i4 = i12;
                                        fArr2 = fArr3;
                                    }
                                    i12 = i4 + 2;
                                    length2 = i13;
                                    fArr3 = fArr2;
                                }
                            }
                            length = fArr == null ? length + 4 : length + (fArr.length * 4);
                            i8 = i3 + 1;
                            fCalcTextHeight = f2;
                        }
                    } else {
                        int i14 = 0;
                        while (i14 < barBuffer2.buffer.length * this.mAnimator.getPhaseX()) {
                            float[] fArr5 = barBuffer2.buffer;
                            int i15 = i14 + 1;
                            float f25 = fArr5[i15];
                            float f26 = (fArr5[i14 + 3] + f25) / f7;
                            if (!this.mViewPortHandler.isInBoundsTop(f25)) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsX(barBuffer2.buffer[i14]) && this.mViewPortHandler.isInBoundsBottom(barBuffer2.buffer[i15])) {
                                BarEntry barEntry3 = (BarEntry) iBarDataSet.getEntryForIndex(i14 / 4);
                                float y2 = barEntry3.getY();
                                String barLabel2 = valueFormatter2.getBarLabel(barEntry3);
                                float fCalcTextWidth3 = Utils.calcTextWidth(this.mValuePaint, barLabel2);
                                float f27 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth3 + fConvertDpToPixel);
                                float f28 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth3 + fConvertDpToPixel) : fConvertDpToPixel;
                                if (zIsInverted) {
                                    f27 = (-f27) - fCalcTextWidth3;
                                    f28 = (-f28) - fCalcTextWidth3;
                                }
                                float f29 = f27;
                                float f30 = f28;
                                if (iBarDataSet.isDrawValuesEnabled()) {
                                    float f31 = barBuffer2.buffer[i14 + 2];
                                    float f32 = y2 >= 0.0f ? f29 : f30;
                                    i5 = i14;
                                    list2 = dataSets;
                                    mPPointF2 = mPPointF3;
                                    f6 = f30;
                                    barBuffer = barBuffer2;
                                    i6 = i7;
                                    valueFormatter = valueFormatter2;
                                    drawValue(canvas, barLabel2, f31 + f32, f26 + fCalcTextHeight, iBarDataSet.getValueTextColor(i14 / 2));
                                } else {
                                    i5 = i14;
                                    list2 = dataSets;
                                    i6 = i7;
                                    f6 = f30;
                                    mPPointF2 = mPPointF3;
                                    barBuffer = barBuffer2;
                                    valueFormatter = valueFormatter2;
                                }
                                if (barEntry3.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                    Drawable icon3 = barEntry3.getIcon();
                                    float f33 = barBuffer.buffer[i5 + 2];
                                    if (y2 < 0.0f) {
                                        f29 = f6;
                                    }
                                    Utils.drawImage(canvas, icon3, (int) (f33 + f29 + mPPointF2.f6566x), (int) (f26 + mPPointF2.f6567y), icon3.getIntrinsicWidth(), icon3.getIntrinsicHeight());
                                }
                            } else {
                                i5 = i14;
                                barBuffer = barBuffer2;
                                list2 = dataSets;
                                i6 = i7;
                                mPPointF2 = mPPointF3;
                                valueFormatter = valueFormatter2;
                            }
                            i14 = i5 + 4;
                            mPPointF3 = mPPointF2;
                            valueFormatter2 = valueFormatter;
                            barBuffer2 = barBuffer;
                            dataSets = list2;
                            i7 = i6;
                            f7 = 2.0f;
                        }
                        list = dataSets;
                        i2 = i7;
                        mPPointF = mPPointF3;
                    }
                    MPPointF.recycleInstance(mPPointF);
                } else {
                    list = dataSets;
                    i2 = i7;
                }
                i7 = i2 + 1;
                dataSets = list;
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer, com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        for (int i2 = 0; i2 < this.mBarBuffers.length; i2++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i2);
            this.mBarBuffers[i2] = new HorizontalBarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        return ((float) chartInterface.getData().getEntryCount()) < ((float) chartInterface.getMaxVisibleCount()) * this.mViewPortHandler.getScaleY();
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    public void prepareBarHighlight(float f2, float f3, float f4, float f5, Transformer transformer) {
        this.mBarRect.set(f3, f2 - f5, f4, f2 + f5);
        transformer.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    public void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerY(), rectF.right);
    }
}
