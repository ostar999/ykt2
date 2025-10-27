package com.github.mikephil.charting.components;

import android.graphics.Typeface;
import com.github.mikephil.charting.utils.Utils;

/* loaded from: classes3.dex */
public abstract class ComponentBase {
    protected boolean mEnabled = true;
    protected float mXOffset = 5.0f;
    protected float mYOffset = 5.0f;
    protected Typeface mTypeface = null;
    protected float mTextSize = Utils.convertDpToPixel(10.0f);
    protected int mTextColor = -16777216;

    public int getTextColor() {
        return this.mTextColor;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public float getXOffset() {
        return this.mXOffset;
    }

    public float getYOffset() {
        return this.mYOffset;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z2) {
        this.mEnabled = z2;
    }

    public void setTextColor(int i2) {
        this.mTextColor = i2;
    }

    public void setTextSize(float f2) {
        if (f2 > 24.0f) {
            f2 = 24.0f;
        }
        if (f2 < 6.0f) {
            f2 = 6.0f;
        }
        this.mTextSize = Utils.convertDpToPixel(f2);
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    public void setXOffset(float f2) {
        this.mXOffset = Utils.convertDpToPixel(f2);
    }

    public void setYOffset(float f2) {
        this.mYOffset = Utils.convertDpToPixel(f2);
    }
}
