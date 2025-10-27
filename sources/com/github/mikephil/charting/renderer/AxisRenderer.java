package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes3.dex */
public abstract class AxisRenderer extends Renderer {
    protected AxisBase mAxis;
    protected Paint mAxisLabelPaint;
    protected Paint mAxisLinePaint;
    protected Paint mGridPaint;
    protected Paint mLimitLinePaint;
    protected Transformer mTrans;

    public AxisRenderer(ViewPortHandler viewPortHandler, Transformer transformer, AxisBase axisBase) {
        super(viewPortHandler);
        this.mTrans = transformer;
        this.mAxis = axisBase;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint = new Paint(1);
            Paint paint = new Paint();
            this.mGridPaint = paint;
            paint.setColor(-7829368);
            this.mGridPaint.setStrokeWidth(1.0f);
            this.mGridPaint.setStyle(Paint.Style.STROKE);
            this.mGridPaint.setAlpha(90);
            Paint paint2 = new Paint();
            this.mAxisLinePaint = paint2;
            paint2.setColor(-16777216);
            this.mAxisLinePaint.setStrokeWidth(1.0f);
            this.mAxisLinePaint.setStyle(Paint.Style.STROKE);
            Paint paint3 = new Paint(1);
            this.mLimitLinePaint = paint3;
            paint3.setStyle(Paint.Style.STROKE);
        }
    }

    public void computeAxis(float f2, float f3, boolean z2) {
        float f4;
        double d3;
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler != null && viewPortHandler.contentWidth() > 10.0f && !this.mViewPortHandler.isFullyZoomedOutY()) {
            MPPointD valuesByTouchPoint = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD valuesByTouchPoint2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
            if (z2) {
                f4 = (float) valuesByTouchPoint.f6565y;
                d3 = valuesByTouchPoint2.f6565y;
            } else {
                f4 = (float) valuesByTouchPoint2.f6565y;
                d3 = valuesByTouchPoint.f6565y;
            }
            MPPointD.recycleInstance(valuesByTouchPoint);
            MPPointD.recycleInstance(valuesByTouchPoint2);
            f2 = f4;
            f3 = (float) d3;
        }
        computeAxisValues(f2, f3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v5, types: [int] */
    public void computeAxisValues(float f2, float f3) {
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
        int iIsCenterAxisLabelsEnabled = this.mAxis.isCenterAxisLabelsEnabled();
        if (this.mAxis.isForceLabelsEnabled()) {
            dRoundToNextSignificant = ((float) dAbs) / (labelCount - 1);
            AxisBase axisBase2 = this.mAxis;
            axisBase2.mEntryCount = labelCount;
            if (axisBase2.mEntries.length < labelCount) {
                axisBase2.mEntries = new float[labelCount];
            }
            for (int i2 = 0; i2 < labelCount; i2++) {
                this.mAxis.mEntries[i2] = f4;
                f4 = (float) (f4 + dRoundToNextSignificant);
            }
        } else {
            double dCeil = dRoundToNextSignificant == 0.0d ? 0.0d : Math.ceil(f4 / dRoundToNextSignificant) * dRoundToNextSignificant;
            if (this.mAxis.isCenterAxisLabelsEnabled()) {
                dCeil -= dRoundToNextSignificant;
            }
            double dNextUp = dRoundToNextSignificant == 0.0d ? 0.0d : Utils.nextUp(Math.floor(f3 / dRoundToNextSignificant) * dRoundToNextSignificant);
            if (dRoundToNextSignificant != 0.0d) {
                double d3 = dCeil;
                iIsCenterAxisLabelsEnabled = iIsCenterAxisLabelsEnabled;
                while (d3 <= dNextUp) {
                    d3 += dRoundToNextSignificant;
                    iIsCenterAxisLabelsEnabled++;
                }
            }
            AxisBase axisBase3 = this.mAxis;
            axisBase3.mEntryCount = iIsCenterAxisLabelsEnabled;
            if (axisBase3.mEntries.length < iIsCenterAxisLabelsEnabled) {
                axisBase3.mEntries = new float[iIsCenterAxisLabelsEnabled];
            }
            for (int i3 = 0; i3 < iIsCenterAxisLabelsEnabled; i3++) {
                if (dCeil == 0.0d) {
                    dCeil = 0.0d;
                }
                this.mAxis.mEntries[i3] = (float) dCeil;
                dCeil += dRoundToNextSignificant;
            }
            labelCount = iIsCenterAxisLabelsEnabled;
        }
        if (dRoundToNextSignificant < 1.0d) {
            this.mAxis.mDecimals = (int) Math.ceil(-Math.log10(dRoundToNextSignificant));
        } else {
            this.mAxis.mDecimals = 0;
        }
        if (this.mAxis.isCenterAxisLabelsEnabled()) {
            AxisBase axisBase4 = this.mAxis;
            if (axisBase4.mCenteredEntries.length < labelCount) {
                axisBase4.mCenteredEntries = new float[labelCount];
            }
            float f5 = ((float) dRoundToNextSignificant) / 2.0f;
            for (int i4 = 0; i4 < labelCount; i4++) {
                AxisBase axisBase5 = this.mAxis;
                axisBase5.mCenteredEntries[i4] = axisBase5.mEntries[i4] + f5;
            }
        }
    }

    public Paint getPaintAxisLabels() {
        return this.mAxisLabelPaint;
    }

    public Paint getPaintAxisLine() {
        return this.mAxisLinePaint;
    }

    public Paint getPaintGrid() {
        return this.mGridPaint;
    }

    public Transformer getTransformer() {
        return this.mTrans;
    }

    public abstract void renderAxisLabels(Canvas canvas);

    public abstract void renderAxisLine(Canvas canvas);

    public abstract void renderGridLines(Canvas canvas);

    public abstract void renderLimitLines(Canvas canvas);
}
