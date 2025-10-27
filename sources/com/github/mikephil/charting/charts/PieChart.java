package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

/* loaded from: classes3.dex */
public class PieChart extends PieRadarChartBase<PieData> {
    private float[] mAbsoluteAngles;
    private CharSequence mCenterText;
    private MPPointF mCenterTextOffset;
    private float mCenterTextRadiusPercent;
    private RectF mCircleBox;
    private float[] mDrawAngles;
    private boolean mDrawCenterText;
    private boolean mDrawEntryLabels;
    private boolean mDrawHole;
    private boolean mDrawRoundedSlices;
    private boolean mDrawSlicesUnderHole;
    private float mHoleRadiusPercent;
    protected float mMaxAngle;
    private float mMinAngleForSlices;
    protected float mTransparentCircleRadiusPercent;
    private boolean mUsePercentValues;

    public PieChart(Context context) {
        super(context);
        this.mCircleBox = new RectF();
        this.mDrawEntryLabels = true;
        this.mDrawAngles = new float[1];
        this.mAbsoluteAngles = new float[1];
        this.mDrawHole = true;
        this.mDrawSlicesUnderHole = false;
        this.mUsePercentValues = false;
        this.mDrawRoundedSlices = false;
        this.mCenterText = "";
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
        this.mHoleRadiusPercent = 50.0f;
        this.mTransparentCircleRadiusPercent = 55.0f;
        this.mDrawCenterText = true;
        this.mCenterTextRadiusPercent = 100.0f;
        this.mMaxAngle = 360.0f;
        this.mMinAngleForSlices = 0.0f;
    }

    private float calcAngle(float f2) {
        return calcAngle(f2, ((PieData) this.mData).getYValueSum());
    }

    private void calcAngles() {
        int entryCount = ((PieData) this.mData).getEntryCount();
        if (this.mDrawAngles.length != entryCount) {
            this.mDrawAngles = new float[entryCount];
        } else {
            for (int i2 = 0; i2 < entryCount; i2++) {
                this.mDrawAngles[i2] = 0.0f;
            }
        }
        if (this.mAbsoluteAngles.length != entryCount) {
            this.mAbsoluteAngles = new float[entryCount];
        } else {
            for (int i3 = 0; i3 < entryCount; i3++) {
                this.mAbsoluteAngles[i3] = 0.0f;
            }
        }
        float yValueSum = ((PieData) this.mData).getYValueSum();
        List<IPieDataSet> dataSets = ((PieData) this.mData).getDataSets();
        float f2 = this.mMinAngleForSlices;
        boolean z2 = f2 != 0.0f && ((float) entryCount) * f2 <= this.mMaxAngle;
        float[] fArr = new float[entryCount];
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i4 = 0;
        for (int i5 = 0; i5 < ((PieData) this.mData).getDataSetCount(); i5++) {
            IPieDataSet iPieDataSet = dataSets.get(i5);
            for (int i6 = 0; i6 < iPieDataSet.getEntryCount(); i6++) {
                float fCalcAngle = calcAngle(Math.abs(iPieDataSet.getEntryForIndex(i6).getY()), yValueSum);
                if (z2) {
                    float f5 = this.mMinAngleForSlices;
                    float f6 = fCalcAngle - f5;
                    if (f6 <= 0.0f) {
                        fArr[i4] = f5;
                        f3 += -f6;
                    } else {
                        fArr[i4] = fCalcAngle;
                        f4 += f6;
                    }
                }
                this.mDrawAngles[i4] = fCalcAngle;
                if (i4 == 0) {
                    this.mAbsoluteAngles[i4] = fCalcAngle;
                } else {
                    float[] fArr2 = this.mAbsoluteAngles;
                    fArr2[i4] = fArr2[i4 - 1] + fCalcAngle;
                }
                i4++;
            }
        }
        if (z2) {
            for (int i7 = 0; i7 < entryCount; i7++) {
                float f7 = fArr[i7];
                float f8 = f7 - (((f7 - this.mMinAngleForSlices) / f4) * f3);
                fArr[i7] = f8;
                if (i7 == 0) {
                    this.mAbsoluteAngles[0] = fArr[0];
                } else {
                    float[] fArr3 = this.mAbsoluteAngles;
                    fArr3[i7] = fArr3[i7 - 1] + f8;
                }
            }
            this.mDrawAngles = fArr;
        }
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void calcMinMax() {
        calcAngles();
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void calculateOffsets() {
        super.calculateOffsets();
        if (this.mData == 0) {
            return;
        }
        float diameter = getDiameter() / 2.0f;
        MPPointF centerOffsets = getCenterOffsets();
        float selectionShift = ((PieData) this.mData).getDataSet().getSelectionShift();
        RectF rectF = this.mCircleBox;
        float f2 = centerOffsets.f6566x;
        float f3 = centerOffsets.f6567y;
        rectF.set((f2 - diameter) + selectionShift, (f3 - diameter) + selectionShift, (f2 + diameter) - selectionShift, (f3 + diameter) - selectionShift);
        MPPointF.recycleInstance(centerOffsets);
    }

    public float[] getAbsoluteAngles() {
        return this.mAbsoluteAngles;
    }

    public MPPointF getCenterCircleBox() {
        return MPPointF.getInstance(this.mCircleBox.centerX(), this.mCircleBox.centerY());
    }

    public CharSequence getCenterText() {
        return this.mCenterText;
    }

    public MPPointF getCenterTextOffset() {
        MPPointF mPPointF = this.mCenterTextOffset;
        return MPPointF.getInstance(mPPointF.f6566x, mPPointF.f6567y);
    }

    public float getCenterTextRadiusPercent() {
        return this.mCenterTextRadiusPercent;
    }

    public RectF getCircleBox() {
        return this.mCircleBox;
    }

    public int getDataSetIndexForIndex(int i2) {
        List<IPieDataSet> dataSets = ((PieData) this.mData).getDataSets();
        for (int i3 = 0; i3 < dataSets.size(); i3++) {
            if (dataSets.get(i3).getEntryForXValue(i2, Float.NaN) != null) {
                return i3;
            }
        }
        return -1;
    }

    public float[] getDrawAngles() {
        return this.mDrawAngles;
    }

    public float getHoleRadius() {
        return this.mHoleRadiusPercent;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public int getIndexForAngle(float f2) {
        float normalizedAngle = Utils.getNormalizedAngle(f2 - getRotationAngle());
        int i2 = 0;
        while (true) {
            float[] fArr = this.mAbsoluteAngles;
            if (i2 >= fArr.length) {
                return -1;
            }
            if (fArr[i2] > normalizedAngle) {
                return i2;
            }
            i2++;
        }
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public float[] getMarkerPosition(Highlight highlight) {
        MPPointF centerCircleBox = getCenterCircleBox();
        float radius = getRadius();
        float holeRadius = (radius / 10.0f) * 3.6f;
        if (isDrawHoleEnabled()) {
            holeRadius = (radius - ((radius / 100.0f) * getHoleRadius())) / 2.0f;
        }
        float f2 = radius - holeRadius;
        float rotationAngle = getRotationAngle();
        float f3 = this.mDrawAngles[(int) highlight.getX()] / 2.0f;
        double d3 = f2;
        float fCos = (float) ((Math.cos(Math.toRadians(((this.mAbsoluteAngles[r11] + rotationAngle) - f3) * this.mAnimator.getPhaseY())) * d3) + centerCircleBox.f6566x);
        float fSin = (float) ((d3 * Math.sin(Math.toRadians(((rotationAngle + this.mAbsoluteAngles[r11]) - f3) * this.mAnimator.getPhaseY()))) + centerCircleBox.f6567y);
        MPPointF.recycleInstance(centerCircleBox);
        return new float[]{fCos, fSin};
    }

    public float getMaxAngle() {
        return this.mMaxAngle;
    }

    public float getMinAngleForSlices() {
        return this.mMinAngleForSlices;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRadius() {
        RectF rectF = this.mCircleBox;
        if (rectF == null) {
            return 0.0f;
        }
        return Math.min(rectF.width() / 2.0f, this.mCircleBox.height() / 2.0f);
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRequiredBaseOffset() {
        return 0.0f;
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase
    public float getRequiredLegendOffset() {
        return this.mLegendRenderer.getLabelPaint().getTextSize() * 2.0f;
    }

    public float getTransparentCircleRadius() {
        return this.mTransparentCircleRadiusPercent;
    }

    @Override // com.github.mikephil.charting.charts.Chart
    @Deprecated
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    @Override // com.github.mikephil.charting.charts.PieRadarChartBase, com.github.mikephil.charting.charts.Chart
    public void init() {
        super.init();
        this.mRenderer = new PieChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mXAxis = null;
        this.mHighlighter = new PieHighlighter(this);
    }

    public boolean isDrawCenterTextEnabled() {
        return this.mDrawCenterText;
    }

    public boolean isDrawEntryLabelsEnabled() {
        return this.mDrawEntryLabels;
    }

    public boolean isDrawHoleEnabled() {
        return this.mDrawHole;
    }

    public boolean isDrawRoundedSlicesEnabled() {
        return this.mDrawRoundedSlices;
    }

    public boolean isDrawSlicesUnderHoleEnabled() {
        return this.mDrawSlicesUnderHole;
    }

    public boolean isUsePercentValuesEnabled() {
        return this.mUsePercentValues;
    }

    public boolean needsHighlight(int i2) {
        if (!valuesToHighlight()) {
            return false;
        }
        int i3 = 0;
        while (true) {
            Highlight[] highlightArr = this.mIndicesToHighlight;
            if (i3 >= highlightArr.length) {
                return false;
            }
            if (((int) highlightArr[i3].getX()) == i2) {
                return true;
            }
            i3++;
        }
    }

    @Override // com.github.mikephil.charting.charts.Chart, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        DataRenderer dataRenderer = this.mRenderer;
        if (dataRenderer != null && (dataRenderer instanceof PieChartRenderer)) {
            ((PieChartRenderer) dataRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }

    @Override // com.github.mikephil.charting.charts.Chart, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData == 0) {
            return;
        }
        this.mRenderer.drawData(canvas);
        if (valuesToHighlight()) {
            this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
        }
        this.mRenderer.drawExtras(canvas);
        this.mRenderer.drawValues(canvas);
        this.mLegendRenderer.renderLegend(canvas);
        drawDescription(canvas);
        drawMarkers(canvas);
    }

    public void setCenterText(CharSequence charSequence) {
        if (charSequence == null) {
            this.mCenterText = "";
        } else {
            this.mCenterText = charSequence;
        }
    }

    public void setCenterTextColor(int i2) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setColor(i2);
    }

    public void setCenterTextOffset(float f2, float f3) {
        this.mCenterTextOffset.f6566x = Utils.convertDpToPixel(f2);
        this.mCenterTextOffset.f6567y = Utils.convertDpToPixel(f3);
    }

    public void setCenterTextRadiusPercent(float f2) {
        this.mCenterTextRadiusPercent = f2;
    }

    public void setCenterTextSize(float f2) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTextSize(Utils.convertDpToPixel(f2));
    }

    public void setCenterTextSizePixels(float f2) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTextSize(f2);
    }

    public void setCenterTextTypeface(Typeface typeface) {
        ((PieChartRenderer) this.mRenderer).getPaintCenterText().setTypeface(typeface);
    }

    public void setDrawCenterText(boolean z2) {
        this.mDrawCenterText = z2;
    }

    public void setDrawEntryLabels(boolean z2) {
        this.mDrawEntryLabels = z2;
    }

    public void setDrawHoleEnabled(boolean z2) {
        this.mDrawHole = z2;
    }

    public void setDrawRoundedSlices(boolean z2) {
        this.mDrawRoundedSlices = z2;
    }

    @Deprecated
    public void setDrawSliceText(boolean z2) {
        this.mDrawEntryLabels = z2;
    }

    public void setDrawSlicesUnderHole(boolean z2) {
        this.mDrawSlicesUnderHole = z2;
    }

    public void setEntryLabelColor(int i2) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setColor(i2);
    }

    public void setEntryLabelTextSize(float f2) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setTextSize(Utils.convertDpToPixel(f2));
    }

    public void setEntryLabelTypeface(Typeface typeface) {
        ((PieChartRenderer) this.mRenderer).getPaintEntryLabels().setTypeface(typeface);
    }

    public void setHoleColor(int i2) {
        ((PieChartRenderer) this.mRenderer).getPaintHole().setColor(i2);
    }

    public void setHoleRadius(float f2) {
        this.mHoleRadiusPercent = f2;
    }

    public void setMaxAngle(float f2) {
        if (f2 > 360.0f) {
            f2 = 360.0f;
        }
        if (f2 < 90.0f) {
            f2 = 90.0f;
        }
        this.mMaxAngle = f2;
    }

    public void setMinAngleForSlices(float f2) {
        float f3 = this.mMaxAngle;
        if (f2 > f3 / 2.0f) {
            f2 = f3 / 2.0f;
        } else if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        this.mMinAngleForSlices = f2;
    }

    public void setTransparentCircleAlpha(int i2) {
        ((PieChartRenderer) this.mRenderer).getPaintTransparentCircle().setAlpha(i2);
    }

    public void setTransparentCircleColor(int i2) {
        Paint paintTransparentCircle = ((PieChartRenderer) this.mRenderer).getPaintTransparentCircle();
        int alpha = paintTransparentCircle.getAlpha();
        paintTransparentCircle.setColor(i2);
        paintTransparentCircle.setAlpha(alpha);
    }

    public void setTransparentCircleRadius(float f2) {
        this.mTransparentCircleRadiusPercent = f2;
    }

    public void setUsePercentValues(boolean z2) {
        this.mUsePercentValues = z2;
    }

    private float calcAngle(float f2, float f3) {
        return (f2 / f3) * this.mMaxAngle;
    }

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCircleBox = new RectF();
        this.mDrawEntryLabels = true;
        this.mDrawAngles = new float[1];
        this.mAbsoluteAngles = new float[1];
        this.mDrawHole = true;
        this.mDrawSlicesUnderHole = false;
        this.mUsePercentValues = false;
        this.mDrawRoundedSlices = false;
        this.mCenterText = "";
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
        this.mHoleRadiusPercent = 50.0f;
        this.mTransparentCircleRadiusPercent = 55.0f;
        this.mDrawCenterText = true;
        this.mCenterTextRadiusPercent = 100.0f;
        this.mMaxAngle = 360.0f;
        this.mMinAngleForSlices = 0.0f;
    }

    public PieChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mCircleBox = new RectF();
        this.mDrawEntryLabels = true;
        this.mDrawAngles = new float[1];
        this.mAbsoluteAngles = new float[1];
        this.mDrawHole = true;
        this.mDrawSlicesUnderHole = false;
        this.mUsePercentValues = false;
        this.mDrawRoundedSlices = false;
        this.mCenterText = "";
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
        this.mHoleRadiusPercent = 50.0f;
        this.mTransparentCircleRadiusPercent = 55.0f;
        this.mDrawCenterText = true;
        this.mCenterTextRadiusPercent = 100.0f;
        this.mMaxAngle = 360.0f;
        this.mMinAngleForSlices = 0.0f;
    }
}
