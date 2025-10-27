package com.github.mikephil.charting.charts;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.jobs.AnimatedMoveViewJob;
import com.github.mikephil.charting.jobs.AnimatedZoomJob;
import com.github.mikephil.charting.jobs.MoveViewJob;
import com.github.mikephil.charting.jobs.ZoomJob;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

@SuppressLint({"RtlHardcoded"})
/* loaded from: classes3.dex */
public abstract class BarLineChartBase<T extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>> extends Chart<T> implements BarLineScatterCandleBubbleDataProvider {
    private long drawCycles;
    protected boolean mAutoScaleMinMaxEnabled;
    protected YAxis mAxisLeft;
    protected YAxisRenderer mAxisRendererLeft;
    protected YAxisRenderer mAxisRendererRight;
    protected YAxis mAxisRight;
    protected Paint mBorderPaint;
    protected boolean mClipValuesToContent;
    private boolean mCustomViewPortEnabled;
    protected boolean mDoubleTapToZoomEnabled;
    private boolean mDragXEnabled;
    private boolean mDragYEnabled;
    protected boolean mDrawBorders;
    protected boolean mDrawGridBackground;
    protected OnDrawListener mDrawListener;
    protected Matrix mFitScreenMatrixBuffer;
    protected float[] mGetPositionBuffer;
    protected Paint mGridBackgroundPaint;
    protected boolean mHighlightPerDragEnabled;
    protected boolean mKeepPositionOnRotation;
    protected Transformer mLeftAxisTransformer;
    protected int mMaxVisibleCount;
    protected float mMinOffset;
    private RectF mOffsetsBuffer;
    protected float[] mOnSizeChangedBuffer;
    protected boolean mPinchZoomEnabled;
    protected Transformer mRightAxisTransformer;
    private boolean mScaleXEnabled;
    private boolean mScaleYEnabled;
    protected XAxisRenderer mXAxisRenderer;
    protected Matrix mZoomMatrixBuffer;
    protected MPPointD posForGetHighestVisibleX;
    protected MPPointD posForGetLowestVisibleX;
    private long totalTime;

    /* renamed from: com.github.mikephil.charting.charts.BarLineChartBase$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment;

        static {
            int[] iArr = new int[Legend.LegendOrientation.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation = iArr;
            try {
                iArr[Legend.LegendOrientation.VERTICAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[Legend.LegendOrientation.HORIZONTAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Legend.LegendHorizontalAlignment.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment = iArr2;
            try {
                iArr2[Legend.LegendHorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[Legend.LegendHorizontalAlignment.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[Legend.LegendHorizontalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr3 = new int[Legend.LegendVerticalAlignment.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment = iArr3;
            try {
                iArr3[Legend.LegendVerticalAlignment.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[Legend.LegendVerticalAlignment.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mPinchZoomEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mDrawBorders = false;
        this.mClipValuesToContent = false;
        this.mMinOffset = 15.0f;
        this.mKeepPositionOnRotation = false;
        this.totalTime = 0L;
        this.drawCycles = 0L;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mCustomViewPortEnabled = false;
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public void autoScale() {
        ((BarLineScatterCandleBubbleData) this.mData).calcMinMaxY(getLowestVisibleX(), getHighestVisibleX());
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData) this.mData).getXMin(), ((BarLineScatterCandleBubbleData) this.mData).getXMax());
        if (this.mAxisLeft.isEnabled()) {
            YAxis yAxis = this.mAxisLeft;
            BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = (BarLineScatterCandleBubbleData) this.mData;
            YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
            yAxis.calculate(barLineScatterCandleBubbleData.getYMin(axisDependency), ((BarLineScatterCandleBubbleData) this.mData).getYMax(axisDependency));
        }
        if (this.mAxisRight.isEnabled()) {
            YAxis yAxis2 = this.mAxisRight;
            BarLineScatterCandleBubbleData barLineScatterCandleBubbleData2 = (BarLineScatterCandleBubbleData) this.mData;
            YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
            yAxis2.calculate(barLineScatterCandleBubbleData2.getYMin(axisDependency2), ((BarLineScatterCandleBubbleData) this.mData).getYMax(axisDependency2));
        }
        calculateOffsets();
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public void calcMinMax() {
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData) this.mData).getXMin(), ((BarLineScatterCandleBubbleData) this.mData).getXMax());
        YAxis yAxis = this.mAxisLeft;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = (BarLineScatterCandleBubbleData) this.mData;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        yAxis.calculate(barLineScatterCandleBubbleData.getYMin(axisDependency), ((BarLineScatterCandleBubbleData) this.mData).getYMax(axisDependency));
        YAxis yAxis2 = this.mAxisRight;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData2 = (BarLineScatterCandleBubbleData) this.mData;
        YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
        yAxis2.calculate(barLineScatterCandleBubbleData2.getYMin(axisDependency2), ((BarLineScatterCandleBubbleData) this.mData).getYMax(axisDependency2));
    }

    public void calculateLegendOffsets(RectF rectF) {
        rectF.left = 0.0f;
        rectF.right = 0.0f;
        rectF.top = 0.0f;
        rectF.bottom = 0.0f;
        Legend legend = this.mLegend;
        if (legend == null || !legend.isEnabled() || this.mLegend.isDrawInsideEnabled()) {
            return;
        }
        int i2 = AnonymousClass2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mLegend.getOrientation().ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            int i3 = AnonymousClass2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
            if (i3 == 1) {
                rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                return;
            } else {
                if (i3 != 2) {
                    return;
                }
                rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                return;
            }
        }
        int i4 = AnonymousClass2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[this.mLegend.getHorizontalAlignment().ordinal()];
        if (i4 == 1) {
            rectF.left += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
            return;
        }
        if (i4 == 2) {
            rectF.right += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
            return;
        }
        if (i4 != 3) {
            return;
        }
        int i5 = AnonymousClass2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
        if (i5 == 1) {
            rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
        } else {
            if (i5 != 2) {
                return;
            }
            rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
        }
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public void calculateOffsets() {
        if (!this.mCustomViewPortEnabled) {
            calculateLegendOffsets(this.mOffsetsBuffer);
            RectF rectF = this.mOffsetsBuffer;
            float requiredWidthSpace = rectF.left + 0.0f;
            float f2 = rectF.top + 0.0f;
            float requiredWidthSpace2 = rectF.right + 0.0f;
            float f3 = rectF.bottom + 0.0f;
            if (this.mAxisLeft.needsOffset()) {
                requiredWidthSpace += this.mAxisLeft.getRequiredWidthSpace(this.mAxisRendererLeft.getPaintAxisLabels());
            }
            if (this.mAxisRight.needsOffset()) {
                requiredWidthSpace2 += this.mAxisRight.getRequiredWidthSpace(this.mAxisRendererRight.getPaintAxisLabels());
            }
            if (this.mXAxis.isEnabled() && this.mXAxis.isDrawLabelsEnabled()) {
                float yOffset = r2.mLabelRotatedHeight + this.mXAxis.getYOffset();
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
                    f3 += yOffset;
                } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
                    f2 += yOffset;
                } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                    f3 += yOffset;
                    f2 += yOffset;
                }
            }
            float extraTopOffset = f2 + getExtraTopOffset();
            float extraRightOffset = requiredWidthSpace2 + getExtraRightOffset();
            float extraBottomOffset = f3 + getExtraBottomOffset();
            float extraLeftOffset = requiredWidthSpace + getExtraLeftOffset();
            float fConvertDpToPixel = Utils.convertDpToPixel(this.mMinOffset);
            this.mViewPortHandler.restrainViewPort(Math.max(fConvertDpToPixel, extraLeftOffset), Math.max(fConvertDpToPixel, extraTopOffset), Math.max(fConvertDpToPixel, extraRightOffset), Math.max(fConvertDpToPixel, extraBottomOffset));
            if (this.mLogEnabled) {
                Log.i(Chart.LOG_TAG, "offsetLeft: " + extraLeftOffset + ", offsetTop: " + extraTopOffset + ", offsetRight: " + extraRightOffset + ", offsetBottom: " + extraBottomOffset);
                StringBuilder sb = new StringBuilder();
                sb.append("Content: ");
                sb.append(this.mViewPortHandler.getContentRect().toString());
                Log.i(Chart.LOG_TAG, sb.toString());
            }
        }
        prepareOffsetMatrix();
        prepareValuePxMatrix();
    }

    public void centerViewTo(float f2, float f3, YAxis.AxisDependency axisDependency) {
        float axisRange = getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f2 - ((getXAxis().mAxisRange / this.mViewPortHandler.getScaleX()) / 2.0f), f3 + (axisRange / 2.0f), getTransformer(axisDependency), this));
    }

    @TargetApi(11)
    public void centerViewToAnimated(float f2, float f3, YAxis.AxisDependency axisDependency, long j2) {
        MPPointD valuesByTouchPoint = getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        float axisRange = getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        addViewportJob(AnimatedMoveViewJob.getInstance(this.mViewPortHandler, f2 - ((getXAxis().mAxisRange / this.mViewPortHandler.getScaleX()) / 2.0f), f3 + (axisRange / 2.0f), getTransformer(axisDependency), this, (float) valuesByTouchPoint.f6564x, (float) valuesByTouchPoint.f6565y, j2));
        MPPointD.recycleInstance(valuesByTouchPoint);
    }

    public void centerViewToY(float f2, YAxis.AxisDependency axisDependency) {
        addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, 0.0f, f2 + ((getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY()) / 2.0f), getTransformer(axisDependency), this));
    }

    @Override // android.view.View
    public void computeScroll() {
        ChartTouchListener chartTouchListener = this.mChartTouchListener;
        if (chartTouchListener instanceof BarLineChartTouchListener) {
            ((BarLineChartTouchListener) chartTouchListener).computeScroll();
        }
    }

    public void drawGridBackground(Canvas canvas) {
        if (this.mDrawGridBackground) {
            canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mGridBackgroundPaint);
        }
        if (this.mDrawBorders) {
            canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mBorderPaint);
        }
    }

    public void fitScreen() {
        Matrix matrix = this.mFitScreenMatrixBuffer;
        this.mViewPortHandler.fitScreen(matrix);
        this.mViewPortHandler.refresh(matrix, this, false);
        calculateOffsets();
        postInvalidate();
    }

    public YAxis getAxis(YAxis.AxisDependency axisDependency) {
        return axisDependency == YAxis.AxisDependency.LEFT ? this.mAxisLeft : this.mAxisRight;
    }

    public YAxis getAxisLeft() {
        return this.mAxisLeft;
    }

    public float getAxisRange(YAxis.AxisDependency axisDependency) {
        return axisDependency == YAxis.AxisDependency.LEFT ? this.mAxisLeft.mAxisRange : this.mAxisRight.mAxisRange;
    }

    public YAxis getAxisRight() {
        return this.mAxisRight;
    }

    @Override // com.github.mikephil.charting.charts.Chart, com.github.mikephil.charting.interfaces.dataprovider.ChartInterface, com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public /* bridge */ /* synthetic */ BarLineScatterCandleBubbleData getData() {
        return (BarLineScatterCandleBubbleData) super.getData();
    }

    public IBarLineScatterCandleBubbleDataSet getDataSetByTouchPoint(float f2, float f3) {
        Highlight highlightByTouchPoint = getHighlightByTouchPoint(f2, f3);
        if (highlightByTouchPoint != null) {
            return (IBarLineScatterCandleBubbleDataSet) ((BarLineScatterCandleBubbleData) this.mData).getDataSetByIndex(highlightByTouchPoint.getDataSetIndex());
        }
        return null;
    }

    public OnDrawListener getDrawListener() {
        return this.mDrawListener;
    }

    public Entry getEntryByTouchPoint(float f2, float f3) {
        Highlight highlightByTouchPoint = getHighlightByTouchPoint(f2, f3);
        if (highlightByTouchPoint != null) {
            return ((BarLineScatterCandleBubbleData) this.mData).getEntryForHighlight(highlightByTouchPoint);
        }
        return null;
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public float getHighestVisibleX() {
        getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.posForGetHighestVisibleX);
        return (float) Math.min(this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.f6564x);
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public float getLowestVisibleX() {
        getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float) Math.max(this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.f6564x);
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.ChartInterface
    public int getMaxVisibleCount() {
        return this.mMaxVisibleCount;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public Paint getPaint(int i2) {
        Paint paint = super.getPaint(i2);
        if (paint != null) {
            return paint;
        }
        if (i2 != 4) {
            return null;
        }
        return this.mGridBackgroundPaint;
    }

    public MPPointD getPixelForValues(float f2, float f3, YAxis.AxisDependency axisDependency) {
        return getTransformer(axisDependency).getPixelForValues(f2, f3);
    }

    public MPPointF getPosition(Entry entry, YAxis.AxisDependency axisDependency) {
        if (entry == null) {
            return null;
        }
        this.mGetPositionBuffer[0] = entry.getX();
        this.mGetPositionBuffer[1] = entry.getY();
        getTransformer(axisDependency).pointValuesToPixel(this.mGetPositionBuffer);
        float[] fArr = this.mGetPositionBuffer;
        return MPPointF.getInstance(fArr[0], fArr[1]);
    }

    public YAxisRenderer getRendererLeftYAxis() {
        return this.mAxisRendererLeft;
    }

    public YAxisRenderer getRendererRightYAxis() {
        return this.mAxisRendererRight;
    }

    public XAxisRenderer getRendererXAxis() {
        return this.mXAxisRenderer;
    }

    @Override // android.view.View
    public float getScaleX() {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler == null) {
            return 1.0f;
        }
        return viewPortHandler.getScaleX();
    }

    @Override // android.view.View
    public float getScaleY() {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler == null) {
            return 1.0f;
        }
        return viewPortHandler.getScaleY();
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public Transformer getTransformer(YAxis.AxisDependency axisDependency) {
        return axisDependency == YAxis.AxisDependency.LEFT ? this.mLeftAxisTransformer : this.mRightAxisTransformer;
    }

    public MPPointD getValuesByTouchPoint(float f2, float f3, YAxis.AxisDependency axisDependency) {
        MPPointD mPPointD = MPPointD.getInstance(0.0d, 0.0d);
        getValuesByTouchPoint(f2, f3, axisDependency, mPPointD);
        return mPPointD;
    }

    public float getVisibleXRange() {
        return Math.abs(getHighestVisibleX() - getLowestVisibleX());
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.ChartInterface
    public float getYChartMax() {
        return Math.max(this.mAxisLeft.mAxisMaximum, this.mAxisRight.mAxisMaximum);
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.ChartInterface
    public float getYChartMin() {
        return Math.min(this.mAxisLeft.mAxisMinimum, this.mAxisRight.mAxisMinimum);
    }

    public boolean hasNoDragOffset() {
        return this.mViewPortHandler.hasNoDragOffset();
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public void init() {
        super.init();
        this.mAxisLeft = new YAxis(YAxis.AxisDependency.LEFT);
        this.mAxisRight = new YAxis(YAxis.AxisDependency.RIGHT);
        this.mLeftAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mRightAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mAxisRendererLeft = new YAxisRenderer(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRenderer(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRenderer(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer);
        setHighlighter(new ChartHighlighter(this));
        this.mChartTouchListener = new BarLineChartTouchListener(this, this.mViewPortHandler.getMatrixTouch(), 3.0f);
        Paint paint = new Paint();
        this.mGridBackgroundPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mGridBackgroundPaint.setColor(Color.rgb(240, 240, 240));
        Paint paint2 = new Paint();
        this.mBorderPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(-16777216);
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
    }

    public boolean isAnyAxisInverted() {
        return this.mAxisLeft.isInverted() || this.mAxisRight.isInverted();
    }

    public boolean isAutoScaleMinMaxEnabled() {
        return this.mAutoScaleMinMaxEnabled;
    }

    public boolean isClipValuesToContentEnabled() {
        return this.mClipValuesToContent;
    }

    public boolean isDoubleTapToZoomEnabled() {
        return this.mDoubleTapToZoomEnabled;
    }

    public boolean isDragEnabled() {
        return this.mDragXEnabled || this.mDragYEnabled;
    }

    public boolean isDragXEnabled() {
        return this.mDragXEnabled;
    }

    public boolean isDragYEnabled() {
        return this.mDragYEnabled;
    }

    public boolean isDrawBordersEnabled() {
        return this.mDrawBorders;
    }

    public boolean isFullyZoomedOut() {
        return this.mViewPortHandler.isFullyZoomedOut();
    }

    public boolean isHighlightPerDragEnabled() {
        return this.mHighlightPerDragEnabled;
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public boolean isInverted(YAxis.AxisDependency axisDependency) {
        return getAxis(axisDependency).isInverted();
    }

    public boolean isKeepPositionOnRotation() {
        return this.mKeepPositionOnRotation;
    }

    public boolean isPinchZoomEnabled() {
        return this.mPinchZoomEnabled;
    }

    public boolean isScaleXEnabled() {
        return this.mScaleXEnabled;
    }

    public boolean isScaleYEnabled() {
        return this.mScaleYEnabled;
    }

    public void moveViewTo(float f2, float f3, YAxis.AxisDependency axisDependency) {
        addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f2, f3 + ((getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY()) / 2.0f), getTransformer(axisDependency), this));
    }

    @TargetApi(11)
    public void moveViewToAnimated(float f2, float f3, YAxis.AxisDependency axisDependency, long j2) {
        MPPointD valuesByTouchPoint = getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        addViewportJob(AnimatedMoveViewJob.getInstance(this.mViewPortHandler, f2, f3 + ((getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY()) / 2.0f), getTransformer(axisDependency), this, (float) valuesByTouchPoint.f6564x, (float) valuesByTouchPoint.f6565y, j2));
        MPPointD.recycleInstance(valuesByTouchPoint);
    }

    public void moveViewToX(float f2) {
        addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f2, 0.0f, getTransformer(YAxis.AxisDependency.LEFT), this));
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public void notifyDataSetChanged() {
        if (this.mData == 0) {
            if (this.mLogEnabled) {
                Log.i(Chart.LOG_TAG, "Preparing... DATA NOT SET.");
                return;
            }
            return;
        }
        if (this.mLogEnabled) {
            Log.i(Chart.LOG_TAG, "Preparing...");
        }
        DataRenderer dataRenderer = this.mRenderer;
        if (dataRenderer != null) {
            dataRenderer.initBuffers();
        }
        calcMinMax();
        YAxisRenderer yAxisRenderer = this.mAxisRendererLeft;
        YAxis yAxis = this.mAxisLeft;
        yAxisRenderer.computeAxis(yAxis.mAxisMinimum, yAxis.mAxisMaximum, yAxis.isInverted());
        YAxisRenderer yAxisRenderer2 = this.mAxisRendererRight;
        YAxis yAxis2 = this.mAxisRight;
        yAxisRenderer2.computeAxis(yAxis2.mAxisMinimum, yAxis2.mAxisMaximum, yAxis2.isInverted());
        XAxisRenderer xAxisRenderer = this.mXAxisRenderer;
        XAxis xAxis = this.mXAxis;
        xAxisRenderer.computeAxis(xAxis.mAxisMinimum, xAxis.mAxisMaximum, false);
        if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        calculateOffsets();
    }

    @Override // com.github.mikephil.charting.charts.Chart, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData == 0) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        drawGridBackground(canvas);
        if (this.mAutoScaleMinMaxEnabled) {
            autoScale();
        }
        if (this.mAxisLeft.isEnabled()) {
            YAxisRenderer yAxisRenderer = this.mAxisRendererLeft;
            YAxis yAxis = this.mAxisLeft;
            yAxisRenderer.computeAxis(yAxis.mAxisMinimum, yAxis.mAxisMaximum, yAxis.isInverted());
        }
        if (this.mAxisRight.isEnabled()) {
            YAxisRenderer yAxisRenderer2 = this.mAxisRendererRight;
            YAxis yAxis2 = this.mAxisRight;
            yAxisRenderer2.computeAxis(yAxis2.mAxisMinimum, yAxis2.mAxisMaximum, yAxis2.isInverted());
        }
        if (this.mXAxis.isEnabled()) {
            XAxisRenderer xAxisRenderer = this.mXAxisRenderer;
            XAxis xAxis = this.mXAxis;
            xAxisRenderer.computeAxis(xAxis.mAxisMinimum, xAxis.mAxisMaximum, false);
        }
        this.mXAxisRenderer.renderAxisLine(canvas);
        this.mAxisRendererLeft.renderAxisLine(canvas);
        this.mAxisRendererRight.renderAxisLine(canvas);
        if (this.mXAxis.isDrawGridLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderGridLines(canvas);
        }
        if (this.mAxisLeft.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderGridLines(canvas);
        }
        if (this.mAxisRight.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderGridLines(canvas);
        }
        if (this.mXAxis.isEnabled() && this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderLimitLines(canvas);
        }
        if (this.mAxisLeft.isEnabled() && this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderLimitLines(canvas);
        }
        if (this.mAxisRight.isEnabled() && this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderLimitLines(canvas);
        }
        int iSave = canvas.save();
        canvas.clipRect(this.mViewPortHandler.getContentRect());
        this.mRenderer.drawData(canvas);
        if (!this.mXAxis.isDrawGridLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderGridLines(canvas);
        }
        if (!this.mAxisLeft.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderGridLines(canvas);
        }
        if (!this.mAxisRight.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderGridLines(canvas);
        }
        if (valuesToHighlight()) {
            this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
        }
        canvas.restoreToCount(iSave);
        this.mRenderer.drawExtras(canvas);
        if (this.mXAxis.isEnabled() && !this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderLimitLines(canvas);
        }
        if (this.mAxisLeft.isEnabled() && !this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderLimitLines(canvas);
        }
        if (this.mAxisRight.isEnabled() && !this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderLimitLines(canvas);
        }
        this.mXAxisRenderer.renderAxisLabels(canvas);
        this.mAxisRendererLeft.renderAxisLabels(canvas);
        this.mAxisRendererRight.renderAxisLabels(canvas);
        if (isClipValuesToContentEnabled()) {
            int iSave2 = canvas.save();
            canvas.clipRect(this.mViewPortHandler.getContentRect());
            this.mRenderer.drawValues(canvas);
            canvas.restoreToCount(iSave2);
        } else {
            this.mRenderer.drawValues(canvas);
        }
        this.mLegendRenderer.renderLegend(canvas);
        drawDescription(canvas);
        drawMarkers(canvas);
        if (this.mLogEnabled) {
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            long j2 = this.totalTime + jCurrentTimeMillis2;
            this.totalTime = j2;
            long j3 = this.drawCycles + 1;
            this.drawCycles = j3;
            Log.i(Chart.LOG_TAG, "Drawtime: " + jCurrentTimeMillis2 + " ms, average: " + (j2 / j3) + " ms, cycles: " + this.drawCycles);
        }
    }

    @Override // com.github.mikephil.charting.charts.Chart, android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        float[] fArr = this.mOnSizeChangedBuffer;
        fArr[1] = 0.0f;
        fArr[0] = 0.0f;
        if (this.mKeepPositionOnRotation) {
            fArr[0] = this.mViewPortHandler.contentLeft();
            this.mOnSizeChangedBuffer[1] = this.mViewPortHandler.contentTop();
            getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(this.mOnSizeChangedBuffer);
        }
        super.onSizeChanged(i2, i3, i4, i5);
        if (this.mKeepPositionOnRotation) {
            getTransformer(YAxis.AxisDependency.LEFT).pointValuesToPixel(this.mOnSizeChangedBuffer);
            this.mViewPortHandler.centerViewPort(this.mOnSizeChangedBuffer, this);
        } else {
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            viewPortHandler.refresh(viewPortHandler.getMatrixTouch(), this, true);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        ChartTouchListener chartTouchListener = this.mChartTouchListener;
        if (chartTouchListener == null || this.mData == 0 || !this.mTouchEnabled) {
            return false;
        }
        return chartTouchListener.onTouch(this, motionEvent);
    }

    public void prepareOffsetMatrix() {
        this.mRightAxisTransformer.prepareMatrixOffset(this.mAxisRight.isInverted());
        this.mLeftAxisTransformer.prepareMatrixOffset(this.mAxisLeft.isInverted());
    }

    public void prepareValuePxMatrix() {
        if (this.mLogEnabled) {
            Log.i(Chart.LOG_TAG, "Preparing Value-Px Matrix, xmin: " + this.mXAxis.mAxisMinimum + ", xmax: " + this.mXAxis.mAxisMaximum + ", xdelta: " + this.mXAxis.mAxisRange);
        }
        Transformer transformer = this.mRightAxisTransformer;
        XAxis xAxis = this.mXAxis;
        float f2 = xAxis.mAxisMinimum;
        float f3 = xAxis.mAxisRange;
        YAxis yAxis = this.mAxisRight;
        transformer.prepareMatrixValuePx(f2, f3, yAxis.mAxisRange, yAxis.mAxisMinimum);
        Transformer transformer2 = this.mLeftAxisTransformer;
        XAxis xAxis2 = this.mXAxis;
        float f4 = xAxis2.mAxisMinimum;
        float f5 = xAxis2.mAxisRange;
        YAxis yAxis2 = this.mAxisLeft;
        transformer2.prepareMatrixValuePx(f4, f5, yAxis2.mAxisRange, yAxis2.mAxisMinimum);
    }

    public void resetTracking() {
        this.totalTime = 0L;
        this.drawCycles = 0L;
    }

    public void resetViewPortOffsets() {
        this.mCustomViewPortEnabled = false;
        calculateOffsets();
    }

    public void resetZoom() {
        this.mViewPortHandler.resetZoom(this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, this, false);
        calculateOffsets();
        postInvalidate();
    }

    public void setAutoScaleMinMaxEnabled(boolean z2) {
        this.mAutoScaleMinMaxEnabled = z2;
    }

    public void setBorderColor(int i2) {
        this.mBorderPaint.setColor(i2);
    }

    public void setBorderWidth(float f2) {
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(f2));
    }

    public void setClipValuesToContent(boolean z2) {
        this.mClipValuesToContent = z2;
    }

    public void setDoubleTapToZoomEnabled(boolean z2) {
        this.mDoubleTapToZoomEnabled = z2;
    }

    public void setDragEnabled(boolean z2) {
        this.mDragXEnabled = z2;
        this.mDragYEnabled = z2;
    }

    public void setDragOffsetX(float f2) {
        this.mViewPortHandler.setDragOffsetX(f2);
    }

    public void setDragOffsetY(float f2) {
        this.mViewPortHandler.setDragOffsetY(f2);
    }

    public void setDragXEnabled(boolean z2) {
        this.mDragXEnabled = z2;
    }

    public void setDragYEnabled(boolean z2) {
        this.mDragYEnabled = z2;
    }

    public void setDrawBorders(boolean z2) {
        this.mDrawBorders = z2;
    }

    public void setDrawGridBackground(boolean z2) {
        this.mDrawGridBackground = z2;
    }

    public void setGridBackgroundColor(int i2) {
        this.mGridBackgroundPaint.setColor(i2);
    }

    public void setHighlightPerDragEnabled(boolean z2) {
        this.mHighlightPerDragEnabled = z2;
    }

    public void setKeepPositionOnRotation(boolean z2) {
        this.mKeepPositionOnRotation = z2;
    }

    public void setMaxVisibleValueCount(int i2) {
        this.mMaxVisibleCount = i2;
    }

    public void setMinOffset(float f2) {
        this.mMinOffset = f2;
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.mDrawListener = onDrawListener;
    }

    @Override // com.github.mikephil.charting.charts.Chart
    public void setPaint(Paint paint, int i2) {
        super.setPaint(paint, i2);
        if (i2 != 4) {
            return;
        }
        this.mGridBackgroundPaint = paint;
    }

    public void setPinchZoom(boolean z2) {
        this.mPinchZoomEnabled = z2;
    }

    public void setRendererLeftYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererLeft = yAxisRenderer;
    }

    public void setRendererRightYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererRight = yAxisRenderer;
    }

    public void setScaleEnabled(boolean z2) {
        this.mScaleXEnabled = z2;
        this.mScaleYEnabled = z2;
    }

    public void setScaleMinima(float f2, float f3) {
        this.mViewPortHandler.setMinimumScaleX(f2);
        this.mViewPortHandler.setMinimumScaleY(f3);
    }

    public void setScaleXEnabled(boolean z2) {
        this.mScaleXEnabled = z2;
    }

    public void setScaleYEnabled(boolean z2) {
        this.mScaleYEnabled = z2;
    }

    public void setViewPortOffsets(final float f2, final float f3, final float f4, final float f5) {
        this.mCustomViewPortEnabled = true;
        post(new Runnable() { // from class: com.github.mikephil.charting.charts.BarLineChartBase.1
            @Override // java.lang.Runnable
            public void run() {
                BarLineChartBase.this.mViewPortHandler.restrainViewPort(f2, f3, f4, f5);
                BarLineChartBase.this.prepareOffsetMatrix();
                BarLineChartBase.this.prepareValuePxMatrix();
            }
        });
    }

    public void setVisibleXRange(float f2, float f3) {
        float f4 = this.mXAxis.mAxisRange;
        this.mViewPortHandler.setMinMaxScaleX(f4 / f2, f4 / f3);
    }

    public void setVisibleXRangeMaximum(float f2) {
        this.mViewPortHandler.setMinimumScaleX(this.mXAxis.mAxisRange / f2);
    }

    public void setVisibleXRangeMinimum(float f2) {
        this.mViewPortHandler.setMaximumScaleX(this.mXAxis.mAxisRange / f2);
    }

    public void setVisibleYRange(float f2, float f3, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMinMaxScaleY(getAxisRange(axisDependency) / f2, getAxisRange(axisDependency) / f3);
    }

    public void setVisibleYRangeMaximum(float f2, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMinimumScaleY(getAxisRange(axisDependency) / f2);
    }

    public void setVisibleYRangeMinimum(float f2, YAxis.AxisDependency axisDependency) {
        this.mViewPortHandler.setMaximumScaleY(getAxisRange(axisDependency) / f2);
    }

    public void setXAxisRenderer(XAxisRenderer xAxisRenderer) {
        this.mXAxisRenderer = xAxisRenderer;
    }

    public void zoom(float f2, float f3, float f4, float f5) {
        this.mViewPortHandler.zoom(f2, f3, f4, -f5, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, this, false);
        calculateOffsets();
        postInvalidate();
    }

    @TargetApi(11)
    public void zoomAndCenterAnimated(float f2, float f3, float f4, float f5, YAxis.AxisDependency axisDependency, long j2) {
        MPPointD valuesByTouchPoint = getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        addViewportJob(AnimatedZoomJob.getInstance(this.mViewPortHandler, this, getTransformer(axisDependency), getAxis(axisDependency), this.mXAxis.mAxisRange, f2, f3, this.mViewPortHandler.getScaleX(), this.mViewPortHandler.getScaleY(), f4, f5, (float) valuesByTouchPoint.f6564x, (float) valuesByTouchPoint.f6565y, j2));
        MPPointD.recycleInstance(valuesByTouchPoint);
    }

    public void zoomIn() {
        MPPointF contentCenter = this.mViewPortHandler.getContentCenter();
        this.mViewPortHandler.zoomIn(contentCenter.f6566x, -contentCenter.f6567y, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, this, false);
        MPPointF.recycleInstance(contentCenter);
        calculateOffsets();
        postInvalidate();
    }

    public void zoomOut() {
        MPPointF contentCenter = this.mViewPortHandler.getContentCenter();
        this.mViewPortHandler.zoomOut(contentCenter.f6566x, -contentCenter.f6567y, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, this, false);
        MPPointF.recycleInstance(contentCenter);
        calculateOffsets();
        postInvalidate();
    }

    public void zoomToCenter(float f2, float f3) {
        MPPointF centerOffsets = getCenterOffsets();
        Matrix matrix = this.mZoomMatrixBuffer;
        this.mViewPortHandler.zoom(f2, f3, centerOffsets.f6566x, -centerOffsets.f6567y, matrix);
        this.mViewPortHandler.refresh(matrix, this, false);
    }

    public void getValuesByTouchPoint(float f2, float f3, YAxis.AxisDependency axisDependency, MPPointD mPPointD) {
        getTransformer(axisDependency).getValuesByTouchPoint(f2, f3, mPPointD);
    }

    public void zoom(float f2, float f3, float f4, float f5, YAxis.AxisDependency axisDependency) {
        addViewportJob(ZoomJob.getInstance(this.mViewPortHandler, f2, f3, f4, f5, getTransformer(axisDependency), axisDependency, this));
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mPinchZoomEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mDrawBorders = false;
        this.mClipValuesToContent = false;
        this.mMinOffset = 15.0f;
        this.mKeepPositionOnRotation = false;
        this.totalTime = 0L;
        this.drawCycles = 0L;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mCustomViewPortEnabled = false;
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public BarLineChartBase(Context context) {
        super(context);
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mPinchZoomEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mDrawBorders = false;
        this.mClipValuesToContent = false;
        this.mMinOffset = 15.0f;
        this.mKeepPositionOnRotation = false;
        this.totalTime = 0L;
        this.drawCycles = 0L;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.mCustomViewPortEnabled = false;
        this.mGetPositionBuffer = new float[2];
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }
}
