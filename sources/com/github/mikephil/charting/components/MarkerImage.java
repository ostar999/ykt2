package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class MarkerImage implements IMarker {
    private Context mContext;
    private Drawable mDrawable;
    private WeakReference<Chart> mWeakChart;
    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private FSize mSize = new FSize();
    private Rect mDrawableBoundsCache = new Rect();

    public MarkerImage(Context context, int i2) {
        this.mContext = context;
        this.mDrawable = context.getResources().getDrawable(i2, null);
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void draw(Canvas canvas, float f2, float f3) {
        if (this.mDrawable == null) {
            return;
        }
        MPPointF offsetForDrawingAtPoint = getOffsetForDrawingAtPoint(f2, f3);
        FSize fSize = this.mSize;
        float intrinsicWidth = fSize.width;
        float intrinsicHeight = fSize.height;
        if (intrinsicWidth == 0.0f) {
            intrinsicWidth = this.mDrawable.getIntrinsicWidth();
        }
        if (intrinsicHeight == 0.0f) {
            intrinsicHeight = this.mDrawable.getIntrinsicHeight();
        }
        this.mDrawable.copyBounds(this.mDrawableBoundsCache);
        Drawable drawable = this.mDrawable;
        Rect rect = this.mDrawableBoundsCache;
        int i2 = rect.left;
        int i3 = rect.top;
        drawable.setBounds(i2, i3, ((int) intrinsicWidth) + i2, ((int) intrinsicHeight) + i3);
        int iSave = canvas.save();
        canvas.translate(f2 + offsetForDrawingAtPoint.f6566x, f3 + offsetForDrawingAtPoint.f6567y);
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(iSave);
        this.mDrawable.setBounds(this.mDrawableBoundsCache);
    }

    public Chart getChartView() {
        WeakReference<Chart> weakReference = this.mWeakChart;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public MPPointF getOffset() {
        return this.mOffset;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public MPPointF getOffsetForDrawingAtPoint(float f2, float f3) {
        Drawable drawable;
        Drawable drawable2;
        MPPointF offset = getOffset();
        MPPointF mPPointF = this.mOffset2;
        mPPointF.f6566x = offset.f6566x;
        mPPointF.f6567y = offset.f6567y;
        Chart chartView = getChartView();
        FSize fSize = this.mSize;
        float intrinsicWidth = fSize.width;
        float intrinsicHeight = fSize.height;
        if (intrinsicWidth == 0.0f && (drawable2 = this.mDrawable) != null) {
            intrinsicWidth = drawable2.getIntrinsicWidth();
        }
        if (intrinsicHeight == 0.0f && (drawable = this.mDrawable) != null) {
            intrinsicHeight = drawable.getIntrinsicHeight();
        }
        MPPointF mPPointF2 = this.mOffset2;
        float f4 = mPPointF2.f6566x;
        if (f2 + f4 < 0.0f) {
            mPPointF2.f6566x = -f2;
        } else if (chartView != null && f2 + intrinsicWidth + f4 > chartView.getWidth()) {
            this.mOffset2.f6566x = (chartView.getWidth() - f2) - intrinsicWidth;
        }
        MPPointF mPPointF3 = this.mOffset2;
        float f5 = mPPointF3.f6567y;
        if (f3 + f5 < 0.0f) {
            mPPointF3.f6567y = -f3;
        } else if (chartView != null && f3 + intrinsicHeight + f5 > chartView.getHeight()) {
            this.mOffset2.f6567y = (chartView.getHeight() - f3) - intrinsicHeight;
        }
        return this.mOffset2;
    }

    public FSize getSize() {
        return this.mSize;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
    }

    public void setChartView(Chart chart) {
        this.mWeakChart = new WeakReference<>(chart);
    }

    public void setOffset(MPPointF mPPointF) {
        this.mOffset = mPPointF;
        if (mPPointF == null) {
            this.mOffset = new MPPointF();
        }
    }

    public void setSize(FSize fSize) {
        this.mSize = fSize;
        if (fSize == null) {
            this.mSize = new FSize();
        }
    }

    public void setOffset(float f2, float f3) {
        MPPointF mPPointF = this.mOffset;
        mPPointF.f6566x = f2;
        mPPointF.f6567y = f3;
    }
}
