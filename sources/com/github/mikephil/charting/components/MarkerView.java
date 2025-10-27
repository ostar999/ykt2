package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class MarkerView extends RelativeLayout implements IMarker {
    private MPPointF mOffset;
    private MPPointF mOffset2;
    private WeakReference<Chart> mWeakChart;

    public MarkerView(Context context, int i2) {
        super(context);
        this.mOffset = new MPPointF();
        this.mOffset2 = new MPPointF();
        setupLayoutResource(i2);
    }

    private void setupLayoutResource(int i2) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(i2, this);
        viewInflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        viewInflate.layout(0, 0, viewInflate.getMeasuredWidth(), viewInflate.getMeasuredHeight());
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void draw(Canvas canvas, float f2, float f3) {
        MPPointF offsetForDrawingAtPoint = getOffsetForDrawingAtPoint(f2, f3);
        int iSave = canvas.save();
        canvas.translate(f2 + offsetForDrawingAtPoint.f6566x, f3 + offsetForDrawingAtPoint.f6567y);
        draw(canvas);
        canvas.restoreToCount(iSave);
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
        MPPointF offset = getOffset();
        MPPointF mPPointF = this.mOffset2;
        mPPointF.f6566x = offset.f6566x;
        mPPointF.f6567y = offset.f6567y;
        Chart chartView = getChartView();
        float width = getWidth();
        float height = getHeight();
        MPPointF mPPointF2 = this.mOffset2;
        float f4 = mPPointF2.f6566x;
        if (f2 + f4 < 0.0f) {
            mPPointF2.f6566x = -f2;
        } else if (chartView != null && f2 + width + f4 > chartView.getWidth()) {
            this.mOffset2.f6566x = (chartView.getWidth() - f2) - width;
        }
        MPPointF mPPointF3 = this.mOffset2;
        float f5 = mPPointF3.f6567y;
        if (f3 + f5 < 0.0f) {
            mPPointF3.f6567y = -f3;
        } else if (chartView != null && f3 + height + f5 > chartView.getHeight()) {
            this.mOffset2.f6567y = (chartView.getHeight() - f3) - height;
        }
        return this.mOffset2;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
        measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
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

    public void setOffset(float f2, float f3) {
        MPPointF mPPointF = this.mOffset;
        mPPointF.f6566x = f2;
        mPPointF.f6567y = f3;
    }
}
