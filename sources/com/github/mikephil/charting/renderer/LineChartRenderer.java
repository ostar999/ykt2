package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class LineChartRenderer extends LineRadarRenderer {
    protected Path cubicFillPath;
    protected Path cubicPath;
    protected Canvas mBitmapCanvas;
    protected Bitmap.Config mBitmapConfig;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private float[] mCirclesBuffer;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mGenerateFilledPathBuffer;
    private HashMap<IDataSet, DataSetImageCache> mImageCaches;
    private float[] mLineBuffer;

    /* renamed from: com.github.mikephil.charting.renderer.LineChartRenderer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode;

        static {
            int[] iArr = new int[LineDataSet.Mode.values().length];
            $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode = iArr;
            try {
                iArr[LineDataSet.Mode.LINEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[LineDataSet.Mode.STEPPED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[LineDataSet.Mode.CUBIC_BEZIER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[LineDataSet.Mode.HORIZONTAL_BEZIER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public LineChartRenderer(LineDataProvider lineDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mBitmapConfig = Bitmap.Config.ARGB_8888;
        this.cubicPath = new Path();
        this.cubicFillPath = new Path();
        this.mLineBuffer = new float[4];
        this.mGenerateFilledPathBuffer = new Path();
        this.mImageCaches = new HashMap<>();
        this.mCirclesBuffer = new float[2];
        this.mChart = lineDataProvider;
        Paint paint = new Paint(1);
        this.mCirclePaintInner = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    private void generateFilledPath(ILineDataSet iLineDataSet, int i2, int i3, Path path) {
        float fillLinePosition = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        float phaseY = this.mAnimator.getPhaseY();
        boolean z2 = iLineDataSet.getMode() == LineDataSet.Mode.STEPPED;
        path.reset();
        ?? entryForIndex = iLineDataSet.getEntryForIndex(i2);
        path.moveTo(entryForIndex.getX(), fillLinePosition);
        path.lineTo(entryForIndex.getX(), entryForIndex.getY() * phaseY);
        int i4 = i2 + 1;
        Entry entry = null;
        BaseEntry baseEntry = entryForIndex;
        while (i4 <= i3) {
            ?? entryForIndex2 = iLineDataSet.getEntryForIndex(i4);
            if (z2) {
                path.lineTo(entryForIndex2.getX(), baseEntry.getY() * phaseY);
            }
            path.lineTo(entryForIndex2.getX(), entryForIndex2.getY() * phaseY);
            i4++;
            baseEntry = entryForIndex2;
            entry = entryForIndex2;
        }
        if (entry != null) {
            path.lineTo(entry.getX(), fillLinePosition);
        }
        path.close();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v8 */
    public void drawCircles(Canvas canvas) {
        DataSetImageCache dataSetImageCache;
        Bitmap bitmap;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        float phaseY = this.mAnimator.getPhaseY();
        float[] fArr = this.mCirclesBuffer;
        boolean z2 = false;
        float f2 = 0.0f;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        List dataSets = this.mChart.getLineData().getDataSets();
        int i2 = 0;
        while (i2 < dataSets.size()) {
            ILineDataSet iLineDataSet = (ILineDataSet) dataSets.get(i2);
            if (iLineDataSet.isVisible() && iLineDataSet.isDrawCirclesEnabled() && iLineDataSet.getEntryCount() != 0) {
                this.mCirclePaintInner.setColor(iLineDataSet.getCircleHoleColor());
                Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                this.mXBounds.set(this.mChart, iLineDataSet);
                float circleRadius = iLineDataSet.getCircleRadius();
                float circleHoleRadius = iLineDataSet.getCircleHoleRadius();
                boolean z3 = (!iLineDataSet.isDrawCircleHoleEnabled() || circleHoleRadius >= circleRadius || circleHoleRadius <= f2) ? z2 ? 1 : 0 : true;
                boolean z4 = (z3 && iLineDataSet.getCircleHoleColor() == 1122867) ? true : z2 ? 1 : 0;
                AnonymousClass1 anonymousClass1 = null;
                if (this.mImageCaches.containsKey(iLineDataSet)) {
                    dataSetImageCache = this.mImageCaches.get(iLineDataSet);
                } else {
                    dataSetImageCache = new DataSetImageCache(this, anonymousClass1);
                    this.mImageCaches.put(iLineDataSet, dataSetImageCache);
                }
                if (dataSetImageCache.init(iLineDataSet)) {
                    dataSetImageCache.fill(iLineDataSet, z3, z4);
                }
                BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                int i3 = xBounds.range;
                int i4 = xBounds.min;
                int i5 = i3 + i4;
                ?? r3 = z2;
                while (i4 <= i5) {
                    ?? entryForIndex = iLineDataSet.getEntryForIndex(i4);
                    if (entryForIndex == 0) {
                        break;
                    }
                    this.mCirclesBuffer[r3] = entryForIndex.getX();
                    this.mCirclesBuffer[1] = entryForIndex.getY() * phaseY;
                    transformer.pointValuesToPixel(this.mCirclesBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mCirclesBuffer[r3])) {
                        break;
                    }
                    if (this.mViewPortHandler.isInBoundsLeft(this.mCirclesBuffer[r3]) && this.mViewPortHandler.isInBoundsY(this.mCirclesBuffer[1]) && (bitmap = dataSetImageCache.getBitmap(i4)) != null) {
                        float[] fArr2 = this.mCirclesBuffer;
                        canvas.drawBitmap(bitmap, fArr2[r3] - circleRadius, fArr2[1] - circleRadius, (Paint) null);
                    }
                    i4++;
                    r3 = 0;
                }
            }
            i2++;
            z2 = false;
            f2 = 0.0f;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v5, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    public void drawCubicBezier(ILineDataSet iLineDataSet) {
        float phaseY = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        float cubicIntensity = iLineDataSet.getCubicIntensity();
        this.cubicPath.reset();
        BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
        if (xBounds.range >= 1) {
            int i2 = xBounds.min + 1;
            T entryForIndex = iLineDataSet.getEntryForIndex(Math.max(i2 - 2, 0));
            ?? entryForIndex2 = iLineDataSet.getEntryForIndex(Math.max(i2 - 1, 0));
            if (entryForIndex2 != 0) {
                this.cubicPath.moveTo(entryForIndex2.getX(), entryForIndex2.getY() * phaseY);
                int i3 = this.mXBounds.min + 1;
                int i4 = -1;
                Entry entry = entryForIndex2;
                Entry entry2 = entryForIndex2;
                Entry entry3 = entryForIndex;
                while (true) {
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds2 = this.mXBounds;
                    Entry entryForIndex3 = entry2;
                    if (i3 > xBounds2.range + xBounds2.min) {
                        break;
                    }
                    if (i4 != i3) {
                        entryForIndex3 = iLineDataSet.getEntryForIndex(i3);
                    }
                    int i5 = i3 + 1;
                    if (i5 < iLineDataSet.getEntryCount()) {
                        i3 = i5;
                    }
                    ?? entryForIndex4 = iLineDataSet.getEntryForIndex(i3);
                    this.cubicPath.cubicTo(entry.getX() + ((entryForIndex3.getX() - entry3.getX()) * cubicIntensity), (entry.getY() + ((entryForIndex3.getY() - entry3.getY()) * cubicIntensity)) * phaseY, entryForIndex3.getX() - ((entryForIndex4.getX() - entry.getX()) * cubicIntensity), (entryForIndex3.getY() - ((entryForIndex4.getY() - entry.getY()) * cubicIntensity)) * phaseY, entryForIndex3.getX(), entryForIndex3.getY() * phaseY);
                    entry3 = entry;
                    entry = entryForIndex3;
                    entry2 = entryForIndex4;
                    int i6 = i3;
                    i3 = i5;
                    i4 = i6;
                }
            } else {
                return;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.github.mikephil.charting.data.Entry] */
    public void drawCubicFill(Canvas canvas, ILineDataSet iLineDataSet, Path path, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        float fillLinePosition = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        path.lineTo(iLineDataSet.getEntryForIndex(xBounds.min + xBounds.range).getX(), fillLinePosition);
        path.lineTo(iLineDataSet.getEntryForIndex(xBounds.min).getX(), fillLinePosition);
        path.close();
        transformer.pathValueToPixel(path);
        Drawable fillDrawable = iLineDataSet.getFillDrawable();
        if (fillDrawable != null) {
            drawFilledPath(canvas, path, fillDrawable);
        } else {
            drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
        }
    }

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
            bitmapCreateBitmap = Bitmap.createBitmap(chartWidth, chartHeight, this.mBitmapConfig);
            this.mDrawBitmap = new WeakReference<>(bitmapCreateBitmap);
            this.mBitmapCanvas = new Canvas(bitmapCreateBitmap);
        }
        bitmapCreateBitmap.eraseColor(0);
        for (T t2 : this.mChart.getLineData().getDataSets()) {
            if (t2.isVisible()) {
                drawDataSet(canvas, t2);
            }
        }
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, this.mRenderPaint);
    }

    public void drawDataSet(Canvas canvas, ILineDataSet iLineDataSet) {
        if (iLineDataSet.getEntryCount() < 1) {
            return;
        }
        this.mRenderPaint.setStrokeWidth(iLineDataSet.getLineWidth());
        this.mRenderPaint.setPathEffect(iLineDataSet.getDashPathEffect());
        int i2 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[iLineDataSet.getMode().ordinal()];
        if (i2 == 3) {
            drawCubicBezier(iLineDataSet);
        } else if (i2 != 4) {
            drawLinear(canvas, iLineDataSet);
        } else {
            drawHorizontalBezier(iLineDataSet);
        }
        this.mRenderPaint.setPathEffect(null);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
        drawCircles(canvas);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        LineData lineData = this.mChart.getLineData();
        for (Highlight highlight : highlightArr) {
            ILineDataSet iLineDataSet = (ILineDataSet) lineData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iLineDataSet != null && iLineDataSet.isHighlightEnabled()) {
                ?? entryForXValue = iLineDataSet.getEntryForXValue(highlight.getX(), highlight.getY());
                if (isInBoundsX(entryForXValue, iLineDataSet)) {
                    MPPointD pixelForValues = this.mChart.getTransformer(iLineDataSet.getAxisDependency()).getPixelForValues(entryForXValue.getX(), entryForXValue.getY() * this.mAnimator.getPhaseY());
                    highlight.setDraw((float) pixelForValues.f6564x, (float) pixelForValues.f6565y);
                    drawHighlightLines(canvas, (float) pixelForValues.f6564x, (float) pixelForValues.f6565y, iLineDataSet);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    public void drawHorizontalBezier(ILineDataSet iLineDataSet) {
        float phaseY = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        this.cubicPath.reset();
        BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
        if (xBounds.range >= 1) {
            ?? entryForIndex = iLineDataSet.getEntryForIndex(xBounds.min);
            this.cubicPath.moveTo(entryForIndex.getX(), entryForIndex.getY() * phaseY);
            int i2 = this.mXBounds.min + 1;
            Entry entry = entryForIndex;
            while (true) {
                BarLineScatterCandleBubbleRenderer.XBounds xBounds2 = this.mXBounds;
                if (i2 > xBounds2.range + xBounds2.min) {
                    break;
                }
                ?? entryForIndex2 = iLineDataSet.getEntryForIndex(i2);
                float x2 = entry.getX() + ((entryForIndex2.getX() - entry.getX()) / 2.0f);
                this.cubicPath.cubicTo(x2, entry.getY() * phaseY, x2, entryForIndex2.getY() * phaseY, entryForIndex2.getX(), entryForIndex2.getY() * phaseY);
                i2++;
                entry = entryForIndex2;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    /* JADX WARN: Type inference failed for: r12v11, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r13v5, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r8v22, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    public void drawLinear(Canvas canvas, ILineDataSet iLineDataSet) {
        int entryCount = iLineDataSet.getEntryCount();
        boolean z2 = iLineDataSet.getMode() == LineDataSet.Mode.STEPPED;
        int i2 = z2 ? 4 : 2;
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        Canvas canvas2 = iLineDataSet.isDashedLineEnabled() ? this.mBitmapCanvas : canvas;
        this.mXBounds.set(this.mChart, iLineDataSet);
        if (iLineDataSet.isDrawFilledEnabled() && entryCount > 0) {
            drawLinearFill(canvas, iLineDataSet, transformer, this.mXBounds);
        }
        if (iLineDataSet.getColors().size() > 1) {
            int i3 = i2 * 2;
            if (this.mLineBuffer.length <= i3) {
                this.mLineBuffer = new float[i2 * 4];
            }
            int i4 = this.mXBounds.min;
            while (true) {
                BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                if (i4 > xBounds.range + xBounds.min) {
                    break;
                }
                ?? entryForIndex = iLineDataSet.getEntryForIndex(i4);
                if (entryForIndex != 0) {
                    this.mLineBuffer[0] = entryForIndex.getX();
                    this.mLineBuffer[1] = entryForIndex.getY() * phaseY;
                    if (i4 < this.mXBounds.max) {
                        ?? entryForIndex2 = iLineDataSet.getEntryForIndex(i4 + 1);
                        if (entryForIndex2 == 0) {
                            break;
                        }
                        if (z2) {
                            this.mLineBuffer[2] = entryForIndex2.getX();
                            float[] fArr = this.mLineBuffer;
                            float f2 = fArr[1];
                            fArr[3] = f2;
                            fArr[4] = fArr[2];
                            fArr[5] = f2;
                            fArr[6] = entryForIndex2.getX();
                            this.mLineBuffer[7] = entryForIndex2.getY() * phaseY;
                        } else {
                            this.mLineBuffer[2] = entryForIndex2.getX();
                            this.mLineBuffer[3] = entryForIndex2.getY() * phaseY;
                        }
                    } else {
                        float[] fArr2 = this.mLineBuffer;
                        fArr2[2] = fArr2[0];
                        fArr2[3] = fArr2[1];
                    }
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                        break;
                    }
                    if (this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) && (this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) || this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3]))) {
                        this.mRenderPaint.setColor(iLineDataSet.getColor(i4));
                        canvas2.drawLines(this.mLineBuffer, 0, i3, this.mRenderPaint);
                    }
                }
                i4++;
            }
        } else {
            int i5 = entryCount * i2;
            if (this.mLineBuffer.length < Math.max(i5, i2) * 2) {
                this.mLineBuffer = new float[Math.max(i5, i2) * 4];
            }
            if (iLineDataSet.getEntryForIndex(this.mXBounds.min) != 0) {
                int i6 = this.mXBounds.min;
                int i7 = 0;
                while (true) {
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds2 = this.mXBounds;
                    if (i6 > xBounds2.range + xBounds2.min) {
                        break;
                    }
                    ?? entryForIndex3 = iLineDataSet.getEntryForIndex(i6 == 0 ? 0 : i6 - 1);
                    ?? entryForIndex4 = iLineDataSet.getEntryForIndex(i6);
                    if (entryForIndex3 != 0 && entryForIndex4 != 0) {
                        int i8 = i7 + 1;
                        this.mLineBuffer[i7] = entryForIndex3.getX();
                        int i9 = i8 + 1;
                        this.mLineBuffer[i8] = entryForIndex3.getY() * phaseY;
                        if (z2) {
                            int i10 = i9 + 1;
                            this.mLineBuffer[i9] = entryForIndex4.getX();
                            int i11 = i10 + 1;
                            this.mLineBuffer[i10] = entryForIndex3.getY() * phaseY;
                            int i12 = i11 + 1;
                            this.mLineBuffer[i11] = entryForIndex4.getX();
                            i9 = i12 + 1;
                            this.mLineBuffer[i12] = entryForIndex3.getY() * phaseY;
                        }
                        int i13 = i9 + 1;
                        this.mLineBuffer[i9] = entryForIndex4.getX();
                        this.mLineBuffer[i13] = entryForIndex4.getY() * phaseY;
                        i7 = i13 + 1;
                    }
                    i6++;
                }
                if (i7 > 0) {
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    int iMax = Math.max((this.mXBounds.range + 1) * i2, i2) * 2;
                    this.mRenderPaint.setColor(iLineDataSet.getColor());
                    canvas2.drawLines(this.mLineBuffer, 0, iMax, this.mRenderPaint);
                }
            }
        }
        this.mRenderPaint.setPathEffect(null);
    }

    public void drawLinearFill(Canvas canvas, ILineDataSet iLineDataSet, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        int i2;
        int i3;
        Path path = this.mGenerateFilledPathBuffer;
        int i4 = xBounds.min;
        int i5 = xBounds.range + i4;
        int i6 = 0;
        do {
            i2 = (i6 * 128) + i4;
            i3 = i2 + 128;
            if (i3 > i5) {
                i3 = i5;
            }
            if (i2 <= i3) {
                generateFilledPath(iLineDataSet, i2, i3, path);
                transformer.pathValueToPixel(path);
                Drawable fillDrawable = iLineDataSet.getFillDrawable();
                if (fillDrawable != null) {
                    drawFilledPath(canvas, path, fillDrawable);
                } else {
                    drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
                }
            }
            i6++;
        } while (i2 <= i3);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValue(Canvas canvas, String str, float f2, float f3, int i2) {
        this.mValuePaint.setColor(i2);
        canvas.drawText(str, f2, f3, this.mValuePaint);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        int i2;
        ILineDataSet iLineDataSet;
        Entry entry;
        if (isDrawingValuesAllowed(this.mChart)) {
            List<T> dataSets = this.mChart.getLineData().getDataSets();
            for (int i3 = 0; i3 < dataSets.size(); i3++) {
                ILineDataSet iLineDataSet2 = (ILineDataSet) dataSets.get(i3);
                if (shouldDrawValues(iLineDataSet2) && iLineDataSet2.getEntryCount() >= 1) {
                    applyValueTextStyle(iLineDataSet2);
                    Transformer transformer = this.mChart.getTransformer(iLineDataSet2.getAxisDependency());
                    int circleRadius = (int) (iLineDataSet2.getCircleRadius() * 1.75f);
                    if (!iLineDataSet2.isDrawCirclesEnabled()) {
                        circleRadius /= 2;
                    }
                    int i4 = circleRadius;
                    this.mXBounds.set(this.mChart, iLineDataSet2);
                    float phaseX = this.mAnimator.getPhaseX();
                    float phaseY = this.mAnimator.getPhaseY();
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                    float[] fArrGenerateTransformedValuesLine = transformer.generateTransformedValuesLine(iLineDataSet2, phaseX, phaseY, xBounds.min, xBounds.max);
                    ValueFormatter valueFormatter = iLineDataSet2.getValueFormatter();
                    MPPointF mPPointF = MPPointF.getInstance(iLineDataSet2.getIconsOffset());
                    mPPointF.f6566x = Utils.convertDpToPixel(mPPointF.f6566x);
                    mPPointF.f6567y = Utils.convertDpToPixel(mPPointF.f6567y);
                    int i5 = 0;
                    while (i5 < fArrGenerateTransformedValuesLine.length) {
                        float f2 = fArrGenerateTransformedValuesLine[i5];
                        float f3 = fArrGenerateTransformedValuesLine[i5 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f2)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(f2) && this.mViewPortHandler.isInBoundsY(f3)) {
                            int i6 = i5 / 2;
                            Entry entryForIndex = iLineDataSet2.getEntryForIndex(this.mXBounds.min + i6);
                            if (iLineDataSet2.isDrawValuesEnabled()) {
                                entry = entryForIndex;
                                i2 = i4;
                                iLineDataSet = iLineDataSet2;
                                drawValue(canvas, valueFormatter.getPointLabel(entryForIndex), f2, f3 - i4, iLineDataSet2.getValueTextColor(i6));
                            } else {
                                entry = entryForIndex;
                                i2 = i4;
                                iLineDataSet = iLineDataSet2;
                            }
                            if (entry.getIcon() != null && iLineDataSet.isDrawIconsEnabled()) {
                                Drawable icon = entry.getIcon();
                                Utils.drawImage(canvas, icon, (int) (f2 + mPPointF.f6566x), (int) (f3 + mPPointF.f6567y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                            }
                        } else {
                            i2 = i4;
                            iLineDataSet = iLineDataSet2;
                        }
                        i5 += 2;
                        iLineDataSet2 = iLineDataSet;
                        i4 = i2;
                    }
                    MPPointF.recycleInstance(mPPointF);
                }
            }
        }
    }

    public Bitmap.Config getBitmapConfig() {
        return this.mBitmapConfig;
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

    public void setBitmapConfig(Bitmap.Config config) {
        this.mBitmapConfig = config;
        releaseBitmap();
    }

    public class DataSetImageCache {
        private Bitmap[] circleBitmaps;
        private Path mCirclePathBuffer;

        private DataSetImageCache() {
            this.mCirclePathBuffer = new Path();
        }

        public void fill(ILineDataSet iLineDataSet, boolean z2, boolean z3) {
            int circleColorCount = iLineDataSet.getCircleColorCount();
            float circleRadius = iLineDataSet.getCircleRadius();
            float circleHoleRadius = iLineDataSet.getCircleHoleRadius();
            for (int i2 = 0; i2 < circleColorCount; i2++) {
                int i3 = (int) (circleRadius * 2.1d);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                this.circleBitmaps[i2] = bitmapCreateBitmap;
                LineChartRenderer.this.mRenderPaint.setColor(iLineDataSet.getCircleColor(i2));
                if (z3) {
                    this.mCirclePathBuffer.reset();
                    this.mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleRadius, Path.Direction.CW);
                    this.mCirclePathBuffer.addCircle(circleRadius, circleRadius, circleHoleRadius, Path.Direction.CCW);
                    canvas.drawPath(this.mCirclePathBuffer, LineChartRenderer.this.mRenderPaint);
                } else {
                    canvas.drawCircle(circleRadius, circleRadius, circleRadius, LineChartRenderer.this.mRenderPaint);
                    if (z2) {
                        canvas.drawCircle(circleRadius, circleRadius, circleHoleRadius, LineChartRenderer.this.mCirclePaintInner);
                    }
                }
            }
        }

        public Bitmap getBitmap(int i2) {
            Bitmap[] bitmapArr = this.circleBitmaps;
            return bitmapArr[i2 % bitmapArr.length];
        }

        public boolean init(ILineDataSet iLineDataSet) {
            int circleColorCount = iLineDataSet.getCircleColorCount();
            Bitmap[] bitmapArr = this.circleBitmaps;
            if (bitmapArr == null) {
                this.circleBitmaps = new Bitmap[circleColorCount];
                return true;
            }
            if (bitmapArr.length == circleColorCount) {
                return false;
            }
            this.circleBitmaps = new Bitmap[circleColorCount];
            return true;
        }

        public /* synthetic */ DataSetImageCache(LineChartRenderer lineChartRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }
    }
}
