package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class PieChartRenderer extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds;
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mDrawCenterTextPathBuffer;
    protected RectF mDrawHighlightedRectF;
    private Paint mEntryLabelsPaint;
    private Path mHoleCirclePath;
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer;
    private Path mPathBuffer;
    private RectF[] mRectBuffer;
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    public PieChartRenderer(PieChart pieChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mCenterTextLastBounds = new RectF();
        this.mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
        this.mPathBuffer = new Path();
        this.mInnerRectBuffer = new RectF();
        this.mHoleCirclePath = new Path();
        this.mDrawCenterTextPathBuffer = new Path();
        this.mDrawHighlightedRectF = new RectF();
        this.mChart = pieChart;
        Paint paint = new Paint(1);
        this.mHolePaint = paint;
        paint.setColor(-1);
        this.mHolePaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mTransparentCirclePaint = paint2;
        paint2.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint.setAlpha(105);
        TextPaint textPaint = new TextPaint(1);
        this.mCenterTextPaint = textPaint;
        textPaint.setColor(-16777216);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        Paint paint3 = new Paint(1);
        this.mEntryLabelsPaint = paint3;
        paint3.setColor(-1);
        this.mEntryLabelsPaint.setTextAlign(Paint.Align.CENTER);
        this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0f));
        Paint paint4 = new Paint(1);
        this.mValueLinePaint = paint4;
        paint4.setStyle(Paint.Style.STROKE);
    }

    public float calculateMinimumRadiusForSpacedSlice(MPPointF mPPointF, float f2, float f3, float f4, float f5, float f6, float f7) {
        double d3 = (f6 + f7) * 0.017453292f;
        float fCos = mPPointF.f6566x + (((float) Math.cos(d3)) * f2);
        float fSin = mPPointF.f6567y + (((float) Math.sin(d3)) * f2);
        double d4 = (f6 + (f7 / 2.0f)) * 0.017453292f;
        return (float) ((f2 - ((float) ((Math.sqrt(Math.pow(fCos - f4, 2.0d) + Math.pow(fSin - f5, 2.0d)) / 2.0d) * Math.tan(((180.0d - f3) / 2.0d) * 0.017453292519943295d)))) - Math.sqrt(Math.pow((mPPointF.f6566x + (((float) Math.cos(d4)) * f2)) - ((fCos + f4) / 2.0f), 2.0d) + Math.pow((mPPointF.f6567y + (((float) Math.sin(d4)) * f2)) - ((fSin + f5) / 2.0f), 2.0d)));
    }

    public void drawCenterText(Canvas canvas) {
        MPPointF mPPointF;
        CharSequence centerText = this.mChart.getCenterText();
        if (!this.mChart.isDrawCenterTextEnabled() || centerText == null) {
            return;
        }
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        MPPointF centerTextOffset = this.mChart.getCenterTextOffset();
        float f2 = centerCircleBox.f6566x + centerTextOffset.f6566x;
        float f3 = centerCircleBox.f6567y + centerTextOffset.f6567y;
        float radius = (!this.mChart.isDrawHoleEnabled() || this.mChart.isDrawSlicesUnderHoleEnabled()) ? this.mChart.getRadius() : this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f);
        RectF[] rectFArr = this.mRectBuffer;
        RectF rectF = rectFArr[0];
        rectF.left = f2 - radius;
        rectF.top = f3 - radius;
        rectF.right = f2 + radius;
        rectF.bottom = f3 + radius;
        RectF rectF2 = rectFArr[1];
        rectF2.set(rectF);
        float centerTextRadiusPercent = this.mChart.getCenterTextRadiusPercent() / 100.0f;
        if (centerTextRadiusPercent > 0.0d) {
            rectF2.inset((rectF2.width() - (rectF2.width() * centerTextRadiusPercent)) / 2.0f, (rectF2.height() - (rectF2.height() * centerTextRadiusPercent)) / 2.0f);
        }
        if (centerText.equals(this.mCenterTextLastValue) && rectF2.equals(this.mCenterTextLastBounds)) {
            mPPointF = centerTextOffset;
        } else {
            this.mCenterTextLastBounds.set(rectF2);
            this.mCenterTextLastValue = centerText;
            mPPointF = centerTextOffset;
            this.mCenterTextLayout = new StaticLayout(centerText, 0, centerText.length(), this.mCenterTextPaint, (int) Math.max(Math.ceil(this.mCenterTextLastBounds.width()), 1.0d), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        }
        float height = this.mCenterTextLayout.getHeight();
        canvas.save();
        Path path = this.mDrawCenterTextPathBuffer;
        path.reset();
        path.addOval(rectF, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.translate(rectF2.left, rectF2.top + ((rectF2.height() - height) / 2.0f));
        this.mCenterTextLayout.draw(canvas);
        canvas.restore();
        MPPointF.recycleInstance(centerCircleBox);
        MPPointF.recycleInstance(mPPointF);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        int chartWidth = (int) this.mViewPortHandler.getChartWidth();
        int chartHeight = (int) this.mViewPortHandler.getChartHeight();
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        Bitmap bitmapCreateBitmap = weakReference == null ? null : weakReference.get();
        if (bitmapCreateBitmap == null || bitmapCreateBitmap.getWidth() != chartWidth || bitmapCreateBitmap.getHeight() != chartHeight) {
            if (chartWidth <= 0 || chartHeight <= 0) {
                return;
            }
            bitmapCreateBitmap = Bitmap.createBitmap(chartWidth, chartHeight, Bitmap.Config.ARGB_4444);
            this.mDrawBitmap = new WeakReference<>(bitmapCreateBitmap);
            this.mBitmapCanvas = new Canvas(bitmapCreateBitmap);
        }
        bitmapCreateBitmap.eraseColor(0);
        for (IPieDataSet iPieDataSet : ((PieData) this.mChart.getData()).getDataSets()) {
            if (iPieDataSet.isVisible() && iPieDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iPieDataSet);
            }
        }
    }

    public void drawDataSet(Canvas canvas, IPieDataSet iPieDataSet) {
        int i2;
        int i3;
        int i4;
        float f2;
        float f3;
        float[] fArr;
        float f4;
        float f5;
        int i5;
        RectF rectF;
        RectF rectF2;
        MPPointF mPPointF;
        float f6;
        MPPointF mPPointF2;
        int i6;
        float fMax;
        MPPointF mPPointF3;
        IPieDataSet iPieDataSet2 = iPieDataSet;
        float rotationAngle = this.mChart.getRotationAngle();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        RectF circleBox = this.mChart.getCircleBox();
        int entryCount = iPieDataSet.getEntryCount();
        float[] drawAngles = this.mChart.getDrawAngles();
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean z2 = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float holeRadius = z2 ? (this.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        float holeRadius2 = (radius - ((this.mChart.getHoleRadius() * radius) / 100.0f)) / 2.0f;
        RectF rectF3 = new RectF();
        boolean z3 = z2 && this.mChart.isDrawRoundedSlicesEnabled();
        int i7 = 0;
        for (int i8 = 0; i8 < entryCount; i8++) {
            if (Math.abs(iPieDataSet2.getEntryForIndex(i8).getY()) > Utils.FLOAT_EPSILON) {
                i7++;
            }
        }
        float sliceSpace = i7 <= 1 ? 0.0f : getSliceSpace(iPieDataSet2);
        int i9 = 0;
        float f7 = 0.0f;
        while (i9 < entryCount) {
            float f8 = drawAngles[i9];
            float fAbs = Math.abs(iPieDataSet2.getEntryForIndex(i9).getY());
            float f9 = Utils.FLOAT_EPSILON;
            if (fAbs > f9 && (!this.mChart.needsHighlight(i9) || z3)) {
                boolean z4 = sliceSpace > 0.0f && f8 <= 180.0f;
                i2 = entryCount;
                this.mRenderPaint.setColor(iPieDataSet2.getColor(i9));
                float f10 = i7 == 1 ? 0.0f : sliceSpace / (radius * 0.017453292f);
                float f11 = rotationAngle + ((f7 + (f10 / 2.0f)) * phaseY);
                float f12 = (f8 - f10) * phaseY;
                float f13 = f12 < 0.0f ? 0.0f : f12;
                this.mPathBuffer.reset();
                if (z3) {
                    float f14 = radius - holeRadius2;
                    i3 = i9;
                    i4 = i7;
                    double d3 = f11 * 0.017453292f;
                    f2 = rotationAngle;
                    f3 = phaseX;
                    float fCos = centerCircleBox.f6566x + (((float) Math.cos(d3)) * f14);
                    float fSin = centerCircleBox.f6567y + (f14 * ((float) Math.sin(d3)));
                    rectF3.set(fCos - holeRadius2, fSin - holeRadius2, fCos + holeRadius2, fSin + holeRadius2);
                } else {
                    i3 = i9;
                    i4 = i7;
                    f2 = rotationAngle;
                    f3 = phaseX;
                }
                double d4 = f11 * 0.017453292f;
                float f15 = holeRadius;
                float fCos2 = centerCircleBox.f6566x + (((float) Math.cos(d4)) * radius);
                float fSin2 = centerCircleBox.f6567y + (((float) Math.sin(d4)) * radius);
                if (f13 < 360.0f || f13 % 360.0f > f9) {
                    fArr = drawAngles;
                    if (z3) {
                        this.mPathBuffer.arcTo(rectF3, f11 + 180.0f, -180.0f);
                    }
                    this.mPathBuffer.arcTo(circleBox, f11, f13);
                } else {
                    fArr = drawAngles;
                    this.mPathBuffer.addCircle(centerCircleBox.f6566x, centerCircleBox.f6567y, radius, Path.Direction.CW);
                }
                RectF rectF4 = this.mInnerRectBuffer;
                float f16 = centerCircleBox.f6566x;
                float f17 = centerCircleBox.f6567y;
                RectF rectF5 = rectF3;
                rectF4.set(f16 - f15, f17 - f15, f16 + f15, f17 + f15);
                if (!z2) {
                    f4 = radius;
                    f5 = f15;
                    i5 = i4;
                    rectF = rectF5;
                    rectF2 = circleBox;
                    mPPointF = centerCircleBox;
                    f6 = 360.0f;
                } else if (f15 > 0.0f || z4) {
                    if (z4) {
                        i5 = i4;
                        rectF2 = circleBox;
                        f5 = f15;
                        i6 = 1;
                        f4 = radius;
                        mPPointF2 = centerCircleBox;
                        float fCalculateMinimumRadiusForSpacedSlice = calculateMinimumRadiusForSpacedSlice(centerCircleBox, radius, f8 * phaseY, fCos2, fSin2, f11, f13);
                        if (fCalculateMinimumRadiusForSpacedSlice < 0.0f) {
                            fCalculateMinimumRadiusForSpacedSlice = -fCalculateMinimumRadiusForSpacedSlice;
                        }
                        fMax = Math.max(f5, fCalculateMinimumRadiusForSpacedSlice);
                    } else {
                        f4 = radius;
                        mPPointF2 = centerCircleBox;
                        f5 = f15;
                        i5 = i4;
                        rectF2 = circleBox;
                        i6 = 1;
                        fMax = f5;
                    }
                    float f18 = (i5 == i6 || fMax == 0.0f) ? 0.0f : sliceSpace / (fMax * 0.017453292f);
                    float f19 = f2 + ((f7 + (f18 / 2.0f)) * phaseY);
                    float f20 = (f8 - f18) * phaseY;
                    if (f20 < 0.0f) {
                        f20 = 0.0f;
                    }
                    float f21 = f19 + f20;
                    if (f13 < 360.0f || f13 % 360.0f > f9) {
                        if (z3) {
                            float f22 = f4 - holeRadius2;
                            double d5 = 0.017453292f * f21;
                            mPPointF3 = mPPointF2;
                            float fCos3 = mPPointF2.f6566x + (((float) Math.cos(d5)) * f22);
                            float fSin3 = mPPointF3.f6567y + (f22 * ((float) Math.sin(d5)));
                            rectF = rectF5;
                            rectF.set(fCos3 - holeRadius2, fSin3 - holeRadius2, fCos3 + holeRadius2, fSin3 + holeRadius2);
                            this.mPathBuffer.arcTo(rectF, f21, 180.0f);
                        } else {
                            mPPointF3 = mPPointF2;
                            rectF = rectF5;
                            double d6 = f21 * 0.017453292f;
                            this.mPathBuffer.lineTo(mPPointF3.f6566x + (((float) Math.cos(d6)) * fMax), mPPointF3.f6567y + (fMax * ((float) Math.sin(d6))));
                        }
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f21, -f20);
                    } else {
                        this.mPathBuffer.addCircle(mPPointF2.f6566x, mPPointF2.f6567y, fMax, Path.Direction.CCW);
                        mPPointF3 = mPPointF2;
                        rectF = rectF5;
                    }
                    mPPointF = mPPointF3;
                    this.mPathBuffer.close();
                    this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
                    f7 += f8 * f3;
                } else {
                    f4 = radius;
                    f5 = f15;
                    i5 = i4;
                    rectF = rectF5;
                    f6 = 360.0f;
                    rectF2 = circleBox;
                    mPPointF = centerCircleBox;
                }
                if (f13 % f6 > f9) {
                    if (z4) {
                        float fCalculateMinimumRadiusForSpacedSlice2 = calculateMinimumRadiusForSpacedSlice(mPPointF, f4, f8 * phaseY, fCos2, fSin2, f11, f13);
                        double d7 = 0.017453292f * (f11 + (f13 / 2.0f));
                        this.mPathBuffer.lineTo(mPPointF.f6566x + (((float) Math.cos(d7)) * fCalculateMinimumRadiusForSpacedSlice2), mPPointF.f6567y + (fCalculateMinimumRadiusForSpacedSlice2 * ((float) Math.sin(d7))));
                    } else {
                        this.mPathBuffer.lineTo(mPPointF.f6566x, mPPointF.f6567y);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
                f7 += f8 * f3;
            } else {
                f7 += f8 * phaseX;
                i3 = i9;
                f4 = radius;
                f2 = rotationAngle;
                f3 = phaseX;
                rectF2 = circleBox;
                i2 = entryCount;
                fArr = drawAngles;
                i5 = i7;
                rectF = rectF3;
                f5 = holeRadius;
                mPPointF = centerCircleBox;
            }
            i9 = i3 + 1;
            iPieDataSet2 = iPieDataSet;
            holeRadius = f5;
            rectF3 = rectF;
            centerCircleBox = mPPointF;
            i7 = i5;
            radius = f4;
            entryCount = i2;
            circleBox = rectF2;
            rotationAngle = f2;
            phaseX = f3;
            drawAngles = fArr;
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    public void drawEntryLabel(Canvas canvas, String str, float f2, float f3) {
        canvas.drawText(str, f2, f3, this.mEntryLabelsPaint);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
        drawHole(canvas);
        canvas.drawBitmap(this.mDrawBitmap.get(), 0.0f, 0.0f, (Paint) null);
        drawCenterText(canvas);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i2;
        RectF rectF;
        float f2;
        float[] fArr;
        boolean z2;
        float f3;
        float f4;
        MPPointF mPPointF;
        IPieDataSet dataSetByIndex;
        float f5;
        int i3;
        float[] fArr2;
        float f6;
        int i4;
        float fCalculateMinimumRadiusForSpacedSlice;
        float fMax;
        Highlight[] highlightArr2 = highlightArr;
        boolean z3 = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        if (z3 && this.mChart.isDrawRoundedSlicesEnabled()) {
            return;
        }
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        float holeRadius = z3 ? (this.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        RectF rectF2 = this.mDrawHighlightedRectF;
        rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
        int i5 = 0;
        while (i5 < highlightArr2.length) {
            int x2 = (int) highlightArr2[i5].getX();
            if (x2 < drawAngles.length && (dataSetByIndex = ((PieData) this.mChart.getData()).getDataSetByIndex(highlightArr2[i5].getDataSetIndex())) != null && dataSetByIndex.isHighlightEnabled()) {
                int entryCount = dataSetByIndex.getEntryCount();
                int i6 = 0;
                for (int i7 = 0; i7 < entryCount; i7++) {
                    if (Math.abs(dataSetByIndex.getEntryForIndex(i7).getY()) > Utils.FLOAT_EPSILON) {
                        i6++;
                    }
                }
                if (x2 == 0) {
                    i3 = 1;
                    f5 = 0.0f;
                } else {
                    f5 = absoluteAngles[x2 - 1] * phaseX;
                    i3 = 1;
                }
                float sliceSpace = i6 <= i3 ? 0.0f : dataSetByIndex.getSliceSpace();
                float f7 = drawAngles[x2];
                float selectionShift = dataSetByIndex.getSelectionShift();
                int i8 = i5;
                float f8 = radius + selectionShift;
                float f9 = holeRadius;
                rectF2.set(this.mChart.getCircleBox());
                float f10 = -selectionShift;
                rectF2.inset(f10, f10);
                boolean z4 = sliceSpace > 0.0f && f7 <= 180.0f;
                this.mRenderPaint.setColor(dataSetByIndex.getColor(x2));
                float f11 = i6 == 1 ? 0.0f : sliceSpace / (radius * 0.017453292f);
                float f12 = i6 == 1 ? 0.0f : sliceSpace / (f8 * 0.017453292f);
                float f13 = rotationAngle + (((f11 / 2.0f) + f5) * phaseY);
                float f14 = (f7 - f11) * phaseY;
                float f15 = f14 < 0.0f ? 0.0f : f14;
                float f16 = (((f12 / 2.0f) + f5) * phaseY) + rotationAngle;
                float f17 = (f7 - f12) * phaseY;
                if (f17 < 0.0f) {
                    f17 = 0.0f;
                }
                this.mPathBuffer.reset();
                if (f15 < 360.0f || f15 % 360.0f > Utils.FLOAT_EPSILON) {
                    fArr2 = drawAngles;
                    f6 = f5;
                    double d3 = f16 * 0.017453292f;
                    i4 = i6;
                    z2 = z3;
                    this.mPathBuffer.moveTo(centerCircleBox.f6566x + (((float) Math.cos(d3)) * f8), centerCircleBox.f6567y + (f8 * ((float) Math.sin(d3))));
                    this.mPathBuffer.arcTo(rectF2, f16, f17);
                } else {
                    this.mPathBuffer.addCircle(centerCircleBox.f6566x, centerCircleBox.f6567y, f8, Path.Direction.CW);
                    fArr2 = drawAngles;
                    f6 = f5;
                    i4 = i6;
                    z2 = z3;
                }
                if (z4) {
                    double d4 = f13 * 0.017453292f;
                    i2 = i8;
                    rectF = rectF2;
                    f2 = f9;
                    mPPointF = centerCircleBox;
                    fArr = fArr2;
                    fCalculateMinimumRadiusForSpacedSlice = calculateMinimumRadiusForSpacedSlice(centerCircleBox, radius, f7 * phaseY, (((float) Math.cos(d4)) * radius) + centerCircleBox.f6566x, centerCircleBox.f6567y + (((float) Math.sin(d4)) * radius), f13, f15);
                } else {
                    rectF = rectF2;
                    mPPointF = centerCircleBox;
                    i2 = i8;
                    f2 = f9;
                    fArr = fArr2;
                    fCalculateMinimumRadiusForSpacedSlice = 0.0f;
                }
                RectF rectF3 = this.mInnerRectBuffer;
                float f18 = mPPointF.f6566x;
                float f19 = mPPointF.f6567y;
                rectF3.set(f18 - f2, f19 - f2, f18 + f2, f19 + f2);
                if (!z2 || (f2 <= 0.0f && !z4)) {
                    f3 = phaseX;
                    f4 = phaseY;
                    if (f15 % 360.0f > Utils.FLOAT_EPSILON) {
                        if (z4) {
                            double d5 = (f13 + (f15 / 2.0f)) * 0.017453292f;
                            this.mPathBuffer.lineTo(mPPointF.f6566x + (((float) Math.cos(d5)) * fCalculateMinimumRadiusForSpacedSlice), mPPointF.f6567y + (fCalculateMinimumRadiusForSpacedSlice * ((float) Math.sin(d5))));
                        } else {
                            this.mPathBuffer.lineTo(mPPointF.f6566x, mPPointF.f6567y);
                        }
                    }
                } else {
                    if (z4) {
                        if (fCalculateMinimumRadiusForSpacedSlice < 0.0f) {
                            fCalculateMinimumRadiusForSpacedSlice = -fCalculateMinimumRadiusForSpacedSlice;
                        }
                        fMax = Math.max(f2, fCalculateMinimumRadiusForSpacedSlice);
                    } else {
                        fMax = f2;
                    }
                    float f20 = (i4 == 1 || fMax == 0.0f) ? 0.0f : sliceSpace / (fMax * 0.017453292f);
                    float f21 = ((f6 + (f20 / 2.0f)) * phaseY) + rotationAngle;
                    float f22 = (f7 - f20) * phaseY;
                    if (f22 < 0.0f) {
                        f22 = 0.0f;
                    }
                    float f23 = f21 + f22;
                    if (f15 < 360.0f || f15 % 360.0f > Utils.FLOAT_EPSILON) {
                        double d6 = f23 * 0.017453292f;
                        f3 = phaseX;
                        f4 = phaseY;
                        this.mPathBuffer.lineTo(mPPointF.f6566x + (((float) Math.cos(d6)) * fMax), mPPointF.f6567y + (fMax * ((float) Math.sin(d6))));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f23, -f22);
                    } else {
                        this.mPathBuffer.addCircle(mPPointF.f6566x, mPPointF.f6567y, fMax, Path.Direction.CCW);
                        f3 = phaseX;
                        f4 = phaseY;
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            } else {
                i2 = i5;
                rectF = rectF2;
                f2 = holeRadius;
                fArr = drawAngles;
                z2 = z3;
                f3 = phaseX;
                f4 = phaseY;
                mPPointF = centerCircleBox;
            }
            i5 = i2 + 1;
            phaseX = f3;
            rectF2 = rectF;
            holeRadius = f2;
            centerCircleBox = mPPointF;
            phaseY = f4;
            drawAngles = fArr;
            z3 = z2;
            highlightArr2 = highlightArr;
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    public void drawHole(Canvas canvas) {
        if (!this.mChart.isDrawHoleEnabled() || this.mBitmapCanvas == null) {
            return;
        }
        float radius = this.mChart.getRadius();
        float holeRadius = (this.mChart.getHoleRadius() / 100.0f) * radius;
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        if (Color.alpha(this.mHolePaint.getColor()) > 0) {
            this.mBitmapCanvas.drawCircle(centerCircleBox.f6566x, centerCircleBox.f6567y, holeRadius, this.mHolePaint);
        }
        if (Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
            int alpha = this.mTransparentCirclePaint.getAlpha();
            float transparentCircleRadius = radius * (this.mChart.getTransparentCircleRadius() / 100.0f);
            this.mTransparentCirclePaint.setAlpha((int) (alpha * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
            this.mHoleCirclePath.reset();
            this.mHoleCirclePath.addCircle(centerCircleBox.f6566x, centerCircleBox.f6567y, transparentCircleRadius, Path.Direction.CW);
            this.mHoleCirclePath.addCircle(centerCircleBox.f6566x, centerCircleBox.f6567y, holeRadius, Path.Direction.CCW);
            this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
            this.mTransparentCirclePaint.setAlpha(alpha);
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawRoundedSlices(Canvas canvas) {
        float f2;
        float[] fArr;
        float f3;
        if (this.mChart.isDrawRoundedSlicesEnabled()) {
            IPieDataSet dataSet = ((PieData) this.mChart.getData()).getDataSet();
            if (dataSet.isVisible()) {
                float phaseX = this.mAnimator.getPhaseX();
                float phaseY = this.mAnimator.getPhaseY();
                MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
                float radius = this.mChart.getRadius();
                float holeRadius = (radius - ((this.mChart.getHoleRadius() * radius) / 100.0f)) / 2.0f;
                float[] drawAngles = this.mChart.getDrawAngles();
                float rotationAngle = this.mChart.getRotationAngle();
                int i2 = 0;
                while (i2 < dataSet.getEntryCount()) {
                    float f4 = drawAngles[i2];
                    if (Math.abs(dataSet.getEntryForIndex(i2).getY()) > Utils.FLOAT_EPSILON) {
                        double d3 = radius - holeRadius;
                        double d4 = (rotationAngle + f4) * phaseY;
                        f2 = phaseY;
                        fArr = drawAngles;
                        f3 = rotationAngle;
                        float fCos = (float) (centerCircleBox.f6566x + (Math.cos(Math.toRadians(d4)) * d3));
                        float fSin = (float) ((d3 * Math.sin(Math.toRadians(d4))) + centerCircleBox.f6567y);
                        this.mRenderPaint.setColor(dataSet.getColor(i2));
                        this.mBitmapCanvas.drawCircle(fCos, fSin, holeRadius, this.mRenderPaint);
                    } else {
                        f2 = phaseY;
                        fArr = drawAngles;
                        f3 = rotationAngle;
                    }
                    rotationAngle = f3 + (f4 * phaseX);
                    i2++;
                    phaseY = f2;
                    drawAngles = fArr;
                }
                MPPointF.recycleInstance(centerCircleBox);
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValue(Canvas canvas, String str, float f2, float f3, int i2) {
        this.mValuePaint.setColor(i2);
        canvas.drawText(str, f2, f3, this.mValuePaint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x03da  */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawValues(android.graphics.Canvas r54) {
        /*
            Method dump skipped, instructions count: 1079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.PieChartRenderer.drawValues(android.graphics.Canvas):void");
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public Paint getPaintEntryLabels() {
        return this.mEntryLabelsPaint;
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public float getSliceSpace(IPieDataSet iPieDataSet) {
        if (!iPieDataSet.isAutomaticallyDisableSliceSpacingEnabled()) {
            return iPieDataSet.getSliceSpace();
        }
        if (iPieDataSet.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > (iPieDataSet.getYMin() / ((PieData) this.mChart.getData()).getYValueSum()) * 2.0f) {
            return 0.0f;
        }
        return iPieDataSet.getSliceSpace();
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }

    public void releaseBitmap() {
        Canvas canvas = this.mBitmapCanvas;
        if (canvas != null) {
            canvas.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (weakReference != null) {
            Bitmap bitmap = weakReference.get();
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}
