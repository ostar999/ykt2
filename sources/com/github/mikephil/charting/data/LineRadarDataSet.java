package com.github.mikephil.charting.data;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class LineRadarDataSet<T extends Entry> extends LineScatterCandleRadarDataSet<T> implements ILineRadarDataSet<T> {
    private boolean mDrawFilled;
    private int mFillAlpha;
    private int mFillColor;
    protected Drawable mFillDrawable;
    private float mLineWidth;

    public LineRadarDataSet(List<T> list, String str) {
        super(list, str);
        this.mFillColor = Color.rgb(140, 234, 255);
        this.mFillAlpha = 85;
        this.mLineWidth = 2.5f;
        this.mDrawFilled = false;
    }

    public void copy(LineRadarDataSet lineRadarDataSet) {
        super.copy((LineScatterCandleRadarDataSet) lineRadarDataSet);
        lineRadarDataSet.mDrawFilled = this.mDrawFilled;
        lineRadarDataSet.mFillAlpha = this.mFillAlpha;
        lineRadarDataSet.mFillColor = this.mFillColor;
        lineRadarDataSet.mFillDrawable = this.mFillDrawable;
        lineRadarDataSet.mLineWidth = this.mLineWidth;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public int getFillAlpha() {
        return this.mFillAlpha;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public int getFillColor() {
        return this.mFillColor;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public Drawable getFillDrawable() {
        return this.mFillDrawable;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public float getLineWidth() {
        return this.mLineWidth;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public boolean isDrawFilledEnabled() {
        return this.mDrawFilled;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet
    public void setDrawFilled(boolean z2) {
        this.mDrawFilled = z2;
    }

    public void setFillAlpha(int i2) {
        this.mFillAlpha = i2;
    }

    public void setFillColor(int i2) {
        this.mFillColor = i2;
        this.mFillDrawable = null;
    }

    @TargetApi(18)
    public void setFillDrawable(Drawable drawable) {
        this.mFillDrawable = drawable;
    }

    public void setLineWidth(float f2) {
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 10.0f) {
            f2 = 10.0f;
        }
        this.mLineWidth = Utils.convertDpToPixel(f2);
    }
}
